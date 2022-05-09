package com.kotlin.unsplash.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Topic(
    val id: String,
    val title: String,
    @Json(name = "cover_photo")
    val coverPhoto: CoverPhoto
): Parcelable