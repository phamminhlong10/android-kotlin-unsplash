package com.kotlin.unsplash.features.domain.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val id: String,
    @Json(name = "created_at")
    val createAt: String,
    @Json(name = "updated_at")
    val updateAt: String,
    val width: Int,
    val height: Int,
    val user: User,
    val urls: UrlPhoto,
): Parcelable
