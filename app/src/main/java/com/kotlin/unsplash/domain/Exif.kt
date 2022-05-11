package com.kotlin.unsplash.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exif(
    val make: String,
    val model: String,
    val name: String,
    @Json(name = "exposure_time")
    val exposureTime: String,
    val aperture: String,
    @Json(name = "focal_length")
    val focalLength: String,
    val iso: Int
): Parcelable