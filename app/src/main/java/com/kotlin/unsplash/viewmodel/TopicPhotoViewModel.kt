package com.kotlin.unsplash.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kotlin.unsplash.data.PhotoPagingSource
import com.kotlin.unsplash.data.TOPIC_PHOTO_SCREEN
import com.kotlin.unsplash.domain.Photo
import com.kotlin.unsplash.domain.Topic
import com.kotlin.unsplash.service.UnsplashService
import kotlinx.coroutines.flow.Flow

class TopicPhotoViewModel(private val topic: Topic, private val unsplashService: UnsplashService, application: Application)
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
        PhotoPagingSource(unsplashService, TOPIC_PHOTO_SCREEN, topic)
    }.flow
        .cachedIn(viewModelScope)
}

class TopicPhotoViewModelFactory(private val topic: Topic, private val unsplashService: UnsplashService, val application: Application)
    :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TopicPhotoViewModel::class.java)){
            return TopicPhotoViewModel(topic, unsplashService, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}