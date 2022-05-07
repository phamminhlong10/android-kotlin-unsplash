package com.kotlin.unsplash.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Topic(
    val id: String,
    val title: String
): Parcelable