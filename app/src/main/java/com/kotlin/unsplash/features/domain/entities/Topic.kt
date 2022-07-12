package com.kotlin.unsplash.features.domain.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Topic(
    val id: String,
    val title: String,
    @Json(name = "cover_photo")
    val coverPhoto: CoverPhoto
): Parcelable