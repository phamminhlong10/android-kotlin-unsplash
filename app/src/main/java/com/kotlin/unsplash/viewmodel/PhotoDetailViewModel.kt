package com.kotlin.unsplash.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.kotlin.unsplash.domain.Photo
import com.kotlin.unsplash.service.CLIENT_ID
import com.kotlin.unsplash.service.UnsplashService
import kotlinx.coroutines.launch
import java.lang.Exception

class PhotoDetailViewModel(private val item: Photo, private val unsplashService: UnsplashService,
                           application: Application): AndroidViewModel(Application()) {
    private val _photo = MutableLiveData<Photo?>()
    val photo: LiveData<Photo?>
    get() = _photo

    init {
        responsePhoto()
    }

    private fun responsePhoto(){
        viewModelScope.launch {
           try {
               val result = unsplashService.getASinglePhoto(item.id, CLIENT_ID)
               _photo.value = result
           }catch (e: Exception){
                _photo.value = null
           }
        }
    }
}

class PhotoDetailViewModelFactory(private val item: Photo, private val unsplashService: UnsplashService,
                                  val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PhotoDetailViewModel::class.java)){
            return PhotoDetailViewModel(item, unsplashService, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}