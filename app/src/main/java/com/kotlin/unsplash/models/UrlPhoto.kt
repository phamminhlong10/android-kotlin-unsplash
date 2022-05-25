package com.kotlin.unsplash.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UrlPhoto(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
): Parcelable