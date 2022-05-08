package com.kotlin.unsplash.service

import com.kotlin.unsplash.domain.Photo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val CLIENT_ID = "ZrI5lFMen8VP2J_NriVgXcRZqyZn_lqO2IA7ZM5jsNE"
private const val BASE_URL = "https://api.unsplash.com/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).
    baseUrl(BASE_URL).build()

interface UnsplashService {

    @GET("photos")
    suspend fun getListPhoto(@Query("client_id")clientID: String, @Query("page") page: Int?): List<Photo>
}

object UnsplashApi{
    val retrofitService: UnsplashService by lazy {
        retrofit.create(UnsplashService::class.java)
    }
}