package com.kotlin.unsplash.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.kotlin.unsplash.database.PhotoDB

class PhotoDBDetailViewModel(private val item: PhotoDB, application: Application): AndroidViewModel(application) {
    private val _photoDB = MutableLiveData<PhotoDB>()
    val photoDB: LiveData<PhotoDB>
    get() = _photoDB

    init {
        _photoDB.value = item
    }
}


class PhotoDBDetailViewModelFactory(private val item: PhotoDB, val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoDBDetailViewModel::class.java)){
            return PhotoDBDetailViewModel(item, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}