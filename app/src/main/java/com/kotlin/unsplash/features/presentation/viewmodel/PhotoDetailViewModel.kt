package com.kotlin.unsplash.features.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.kotlin.unsplash.core.paging.CLIENT_ID
import com.kotlin.unsplash.features.data.models.asDatabaseModel
import com.kotlin.unsplash.features.domain.entities.Photo
import com.kotlin.unsplash.features.domain.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(private val repository: Repository, private val item: SavedStateHandle,
                           application: Application): AndroidViewModel(application) {
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
                val result = item.get<Photo>("photo")
                    ?.let { repository.getAPhoto(it.id, CLIENT_ID) }
                _photo.value = result
            }catch (e: Exception){
                _photo.value = null
            }
        }
    }

    private suspend fun insert(){
        withContext(Dispatchers.IO){
            val item = photo.value?.asDatabaseModel()
            if (item != null) {
                repository.addPhoto(item)
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