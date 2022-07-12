package com.kotlin.unsplash.features.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kotlin.unsplash.features.domain.entities.Photo
import kotlinx.parcelize.Parcelize

@Entity(tableName = "photoDB_table")
@Parcelize
data class PhotoDB(
    @PrimaryKey
    val id: String,
    val createAt: String,
    val updateAt: String,
    val width: Int,
    val height: Int,
    val url: String,
    val user: String
): Parcelable


fun Photo.asDatabaseModel() = PhotoDB(
    id = id,
    createAt = createAt,
    updateAt = updateAt,
    width = width,
    height = height,
    url = urls.regular,
    user = user.name
)



