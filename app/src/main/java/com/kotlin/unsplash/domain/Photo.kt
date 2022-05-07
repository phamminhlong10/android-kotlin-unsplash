package com.kotlin.unsplash.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val id: String,
    @Json(name = "created_at")
    val createAt: String,
    @Json(name = "updated_at")
    val updateAt: String,
    val width: Int,
    val height: Int,
    val exif: Exif?,
    val urls: UrlPhoto,
): Parcelable