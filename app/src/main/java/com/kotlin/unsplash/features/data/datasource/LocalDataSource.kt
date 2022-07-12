package com.kotlin.unsplash.features.data.datasource

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kotlin.unsplash.features.data.models.PhotoDB

@Dao
interface LocalDataSourceDao{

    @Query("SELECT * FROM photoDB_table")
    fun getPhotos(): LiveData<List<PhotoDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: PhotoDB)
}

@Database(entities = [PhotoDB::class], version = 1, exportSchema = false)
abstract class PhotoDatabase: RoomDatabase(){
    abstract fun getLocalDataSourceDao(): LocalDataSourceDao
}