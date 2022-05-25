package com.kotlin.unsplash.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kotlin.unsplash.data.MAIN_SCREEN
import com.kotlin.unsplash.data.PhotoPagingSource
import com.kotlin.unsplash.models.Photo
import com.kotlin.unsplash.service.CLIENT_ID
import com.kotlin.unsplash.service.UnsplashApi
import com.kotlin.unsplash.service.UnsplashService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val unsplashService: UnsplashService, application: Application) : AndroidViewModel(application) {

    private val _listPhoto = MutableLiveData<List<Photo>>()
    val listPhoto: LiveData<List<Photo>>
    get() = _listPhoto


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
        PhotoPagingSource(unsplashService, MAIN_SCREEN, null)
    }.flow
        .cachedIn(viewModelScope)


    private fun getListPhotos(){
        viewModelScope.launch {
            try {
                var listResult = UnsplashApi.retrofitService.getListPhoto(CLIENT_ID, 1)
                if(listResult. isNotEmpty()){
                    _listPhoto.value = listResult
                }
            }catch (e: Exception){
                _listPhoto.value = ArrayList()
            }
        }
    }
}

class MainViewModelFactory(private val unsplashService: UnsplashService, private val application: Application): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(unsplashService, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}