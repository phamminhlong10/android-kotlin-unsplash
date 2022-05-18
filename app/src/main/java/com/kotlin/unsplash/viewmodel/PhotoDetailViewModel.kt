package com.kotlin.unsplash.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.kotlin.unsplash.database.PhotoDB
import com.kotlin.unsplash.database.PhotoDao
import com.kotlin.unsplash.domain.Photo
import com.kotlin.unsplash.domain.asDatabaseModel
import com.kotlin.unsplash.service.CLIENT_ID
import com.kotlin.unsplash.service.UnsplashService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class PhotoDetailViewModel(private val database: PhotoDao, private val item: Photo, private val unsplashService: UnsplashService,
                           application: Application): AndroidViewModel(Application()) {
    private val _photo = MutableLiveData<Photo?>()
    val photo: LiveData<Photo?>
    get() = _photo

    private val _showToast  = MutableLiveData<Boolean>()
    val showToast: LiveData<Boolean>
    get() = _showToast

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

    private suspend fun insert(){
        withContext(Dispatchers.IO){
            if(photo != null){
                val item = photo.value?.asDatabaseModel()
                if (item != null) {
                    database.insertPhoto(item)
                }
            }
        }
    }

    fun onFavoritesClicked(){
        viewModelScope.launch {
            insert()
            _showToast.value = true
        }
    }

    fun showToastComplete(){
        _showToast.value = false
    }
}

class PhotoDetailViewModelFactory(private val database: PhotoDao, private val item: Photo, private val unsplashService: UnsplashService,
                                  val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PhotoDetailViewModel::class.java)){
            return PhotoDetailViewModel(database, item, unsplashService, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}