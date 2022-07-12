package com.kotlin.unsplash.features.data.repositories

import androidx.lifecycle.LiveData
import com.kotlin.unsplash.features.data.datasource.LocalDataSourceDao
import com.kotlin.unsplash.features.data.datasource.RemoteDataSource
import com.kotlin.unsplash.features.data.models.PhotoDB
import com.kotlin.unsplash.features.domain.entities.Photo
import com.kotlin.unsplash.features.domain.entities.Topic
import com.kotlin.unsplash.features.domain.repositories.Repository
import javax.inject.Inject


class RepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource, private val localDataSourceDao: LocalDataSourceDao) : Repository{

    override suspend fun getPhotos(clientId: String, page: Int): List<Photo> {
        try {
            return remoteDataSource.getListPhoto(clientId, page)
        }catch (ex: Exception){
            throw ex
        }
    }

    override suspend fun getTopics(clientId: String, page: Int): List<Topic> {
        try {
            return remoteDataSource.getListTopic(clientId, page)
        }catch (ex: Exception){
            throw ex
        }
    }

    override suspend fun getPhotoFromTopic(id: String, clientId: String, page: Int): List<Photo> {
        try {
            return remoteDataSource.getListTopicPhoto(id, clientId, page)
        }catch (ex: Exception){
            throw ex
        }
    }

    override suspend fun getAPhoto(id: String, clientId: String): Photo {
        try {
            return remoteDataSource.getASinglePhoto(id, clientId)
        }catch (ex: Exception){
            throw ex
        }
    }

    override fun getPhotosLocal(): LiveData<List<PhotoDB>> {
        try {
            return localDataSourceDao.getPhotos()
        }catch (ex: Exception){
            throw ex
        }
    }

    override fun addPhoto(photoDB: PhotoDB) {
        try {
            localDataSourceDao.insertPhoto(photoDB)
        }catch (ex: Exception){
            throw ex
        }

    }
}