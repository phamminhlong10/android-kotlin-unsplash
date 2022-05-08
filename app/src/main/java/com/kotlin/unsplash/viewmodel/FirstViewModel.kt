package com.kotlin.unsplash.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kotlin.unsplash.data.PhotoPagingSource
import com.kotlin.unsplash.domain.Photo
import com.kotlin.unsplash.service.CLIENT_ID
import com.kotlin.unsplash.service.UnsplashApi
import com.kotlin.unsplash.service.UnsplashService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FirstViewModel(private val unsplashService: UnsplashService, application: Application) : AndroidViewModel(application) {

    private val _listPhoto = MutableLiveData<List<Photo>>()
    val listPhoto: LiveData<List<Photo>>
    get() = _listPhoto

    val photo: Flow<PagingData<Photo>> = Pager(PagingConfig(pageSize = 40)) {
        PhotoPagingSource(unsplashService)
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

class FirstViewModelFactory(private val unsplashService: UnsplashService, private val application: Application): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FirstViewModel::class.java)){
            return FirstViewModel(unsplashService, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}