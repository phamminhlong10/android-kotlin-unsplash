package com.kotlin.unsplash.service

import com.kotlin.unsplash.domain.Photo
import com.kotlin.unsplash.domain.Topic
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


const val CLIENT_ID = "ZrI5lFMen8VP2J_NriVgXcRZqyZn_lqO2IA7ZM5jsNE"
private const val BASE_URL = "https://api.unsplash.com/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).
    baseUrl(BASE_URL).build()

interface UnsplashService {

    @GET("photos")
    suspend fun getListPhoto(@Query("client_id")clientID: String, @Query("page") page: Int?): List<Photo>

    @GET("topics")
    suspend fun getListTopic(@Query("client_id")clientID: String, @Query("per_page") perPage: Int?): List<Topic>

    @GET("topics/{id_or_slug}/photos")
    suspend fun getListTopicPhoto(@Path("id_or_slug")id: String?, @Query("client_id")clientID: String, @Query("page") page: Int?): List<Photo>


    @GET("photos/{id}")
    suspend fun getASinglePhoto(@Path("id")id: String, @Query("client_id")clientID: String): Photo
}

object UnsplashApi{
    val retrofitService: UnsplashService by lazy {
        retrofit.create(UnsplashService::class.java)
    }
}