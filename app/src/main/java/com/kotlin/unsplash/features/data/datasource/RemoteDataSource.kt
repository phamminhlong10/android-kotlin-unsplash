package com.kotlin.unsplash.features.data.datasource

import com.kotlin.unsplash.features.domain.entities.Photo
import com.kotlin.unsplash.features.domain.entities.Topic
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteDataSource {

    @GET("photos")
    suspend fun getListPhoto(@Query("client_id")clientID: String, @Query("page") page: Int?): List<Photo>

    @GET("topics")
    suspend fun getListTopic(@Query("client_id")clientID: String, @Query("per_page") perPage: Int?): List<Topic>

    @GET("topics/{id_or_slug}/photos")
    suspend fun getListTopicPhoto(@Path("id_or_slug")id: String?, @Query("client_id")clientID: String, @Query("page") page: Int?): List<Photo>


    @GET("photos/{id}")
    suspend fun getASinglePhoto(@Path("id")id: String, @Query("client_id")clientID: String): Photo
}