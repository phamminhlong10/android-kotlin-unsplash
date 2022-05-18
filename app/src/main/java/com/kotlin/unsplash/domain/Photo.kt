package com.kotlin.unsplash.domain

import android.os.Parcelable
import androidx.room.Entity
import com.kotlin.unsplash.database.PhotoDB
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
    val user: User?,
    val urls: UrlPhoto,
): Parcelable

fun Photo.asDatabaseModel() = PhotoDB(
    id = id,
    createAt = createAt,
    updateAt = updateAt,
    width = width,
    height = height,
    url = urls.regular,
    user = user!!.name
)
