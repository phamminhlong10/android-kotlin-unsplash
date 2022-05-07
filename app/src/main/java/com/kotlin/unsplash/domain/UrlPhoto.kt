package com.kotlin.unsplash.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UrlPhoto(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
): Parcelable