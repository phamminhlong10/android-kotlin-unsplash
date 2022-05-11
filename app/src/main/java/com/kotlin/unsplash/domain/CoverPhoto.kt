package com.kotlin.unsplash.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoverPhoto(
    val id: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    val width: Int,
    val height: Int,
    val urls: UrlPhoto?,
): Parcelable