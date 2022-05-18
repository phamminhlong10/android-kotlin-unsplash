package com.kotlin.unsplash.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.kotlin.unsplash.domain.Photo

@Dao
interface PhotoDao{

    @Query("SELECT * FROM photoDB_table")
    fun getPhotos(): LiveData<List<PhotoDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: PhotoDB)
}


@Database(entities = [PhotoDB::class], version = 1, exportSchema = false)
abstract class PhotoDatabase: RoomDatabase(){
    abstract val photoDao: PhotoDao

    companion object{
        @Volatile
        private var INSTANCE: PhotoDatabase? = null

        fun getInstance(context: Context): PhotoDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PhotoDatabase::class.java,
                        "photo_database"
                    ).build()
                }
                return instance
            }
        }
    }
}