package com.kotlin.unsplash.features.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.kotlin.unsplash.features.data.models.PhotoDB
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoDBDetailViewModel @Inject constructor(private val item: SavedStateHandle , application: Application): AndroidViewModel(application) {
    private val _photoDB = MutableLiveData<PhotoDB>()
    val photoDB: LiveData<PhotoDB>
    get() = _photoDB

    init {
        _photoDB.value = item.get<PhotoDB>("photoDB")
    }
}

