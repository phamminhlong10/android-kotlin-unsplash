package com.kotlin.unsplash.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    val username: String,
    val name: String,
    @Json(name = "first_name")
    val firstName: String?,
    @Json(name = "last_name")
    val lastName: String?,
): Parcelable