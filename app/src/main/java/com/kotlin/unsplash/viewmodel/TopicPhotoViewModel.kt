package com.kotlin.unsplash.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kotlin.unsplash.data.PhotoPagingSource
import com.kotlin.unsplash.data.TOPIC_PHOTO_SCREEN
import com.kotlin.unsplash.domain.Photo
import com.kotlin.unsplash.domain.Topic
import com.kotlin.unsplash.service.UnsplashService
import kotlinx.coroutines.flow.Flow

class TopicPhotoViewModel(private val topic: Topic, private val unsplashService: UnsplashService, application: Application)
    : AndroidViewModel(application) {

    val photo: Flow<PagingData<Photo>> = Pager(PagingConfig(pageSize = 10)) {
        PhotoPagingSource(unsplashService, TOPIC_PHOTO_SCREEN, topic)
    }.flow
        .cachedIn(viewModelScope)
}

class TopicPhotoViewModelFactory(private val topic: Topic, private val unsplashService: UnsplashService, val application: Application)
    :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TopicPhotoViewModel::class.java)){
            return TopicPhotoViewModel(topic, unsplashService, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}