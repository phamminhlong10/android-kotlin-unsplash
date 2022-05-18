package com.kotlin.unsplash.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.kotlin.unsplash.database.PhotoDB
import com.kotlin.unsplash.database.PhotoDao
import com.kotlin.unsplash.domain.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GalleryViewModel(private val database: PhotoDao, application: Application): AndroidViewModel(application){
    private val _photos = database.getPhotos()
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

class GalleryViewModelFactory(private val database: PhotoDao, val application: Application): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GalleryViewModel::class.java)){
            return GalleryViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}