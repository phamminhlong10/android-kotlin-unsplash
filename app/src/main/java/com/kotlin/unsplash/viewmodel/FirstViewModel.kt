package com.kotlin.unsplash.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kotlin.unsplash.domain.Photo
import com.kotlin.unsplash.service.CLIENT_ID
import com.kotlin.unsplash.service.UnsplashApi
import kotlinx.coroutines.launch

class FirstViewModel(application: Application) : AndroidViewModel(application) {

    private val _listPhoto = MutableLiveData<List<Photo>>()
    val listPhoto: LiveData<List<Photo>>
    get() = _listPhoto

    init {
        getListPhotos()
    }

    private fun getListPhotos(){
        viewModelScope.launch {
            try {
                var listResult = UnsplashApi.retrofitService.getListPhoto(CLIENT_ID)
                if(listResult. isNotEmpty()){
                    _listPhoto.value = listResult
                }
            }catch (e: Exception){
                _listPhoto.value = ArrayList()
            }
        }
    }
}