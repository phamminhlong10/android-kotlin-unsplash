package com.kotlin.unsplash.features.domain.repositories

import androidx.lifecycle.LiveData
import com.kotlin.unsplash.features.data.models.PhotoDB
import com.kotlin.unsplash.features.domain.entities.Photo
import com.kotlin.unsplash.features.domain.entities.Topic

interface Repository {
    suspend fun getPhotos(clientId: String, page: Int): List<Photo>
    suspend fun getTopics(clientId: String, page: Int): List<Topic>
    suspend fun getPhotoFromTopic(id: String, clientId: String, page: Int): List<Photo>
    suspend fun getAPhoto(id: String, clientId: String): Photo
    fun getPhotosLocal(): LiveData<List<PhotoDB>>
    fun addPhoto(photoDB: PhotoDB)
}