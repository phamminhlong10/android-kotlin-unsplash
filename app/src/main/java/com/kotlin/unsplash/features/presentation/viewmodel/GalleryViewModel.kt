package com.kotlin.unsplash.features.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.kotlin.unsplash.features.data.models.PhotoDB
import com.kotlin.unsplash.features.domain.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val repository: Repository, application: Application): AndroidViewModel(application){
    private val _photos = repository.getPhotosLocal()
    val photos: LiveData<List<PhotoDB>>
        get() = _photos

    private val _navigateToSelectedPhoto = MutableLiveData<PhotoDB?>()
    val navigateToSelectedPhoto: LiveData<PhotoDB?>
        get() = _navigateToSelectedPhoto

    fun photoDetails(photoDB: PhotoDB){
        _navigateToSelectedPhoto.value = photoDB
    }

    fun onNavigateComplete(){
        _navigateToSelectedPhoto.value = null
    }
}