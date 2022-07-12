package com.kotlin.unsplash.features.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kotlin.unsplash.core.paging.PhotoPagingSource
import com.kotlin.unsplash.core.paging.TOPIC_PHOTO_SCREEN
import com.kotlin.unsplash.features.domain.entities.Photo
import com.kotlin.unsplash.features.domain.entities.Topic
import com.kotlin.unsplash.features.domain.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TopicPhotoViewModel @Inject constructor(private val topic: SavedStateHandle, private val repository: Repository, application: Application)
    : AndroidViewModel(application) {

    private val _navigateToSelectedPhoto = MutableLiveData<Photo?>()
    val navigateToSelectedPhoto: LiveData<Photo?>
        get() = _navigateToSelectedPhoto

    fun photoDetails(photo: Photo){
        _navigateToSelectedPhoto.value = photo
    }

    fun navigatePhotoDetailsComplete(){
        _navigateToSelectedPhoto.value = null
    }

    val photo: Flow<PagingData<Photo>> = Pager(PagingConfig(pageSize = 10)) {
        PhotoPagingSource(repository, TOPIC_PHOTO_SCREEN, topic.get<Topic>("topic"))
    }.flow
        .cachedIn(viewModelScope)
}
