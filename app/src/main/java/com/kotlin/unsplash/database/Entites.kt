package com.kotlin.unsplash.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.kotlin.unsplash.domain.UrlPhoto
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



