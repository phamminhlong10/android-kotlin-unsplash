package com.kotlin.unsplash.di_container

import android.content.Context
import androidx.room.Room
import com.kotlin.unsplash.features.data.datasource.LocalDataSourceDao
import com.kotlin.unsplash.features.data.datasource.PhotoDatabase
import com.kotlin.unsplash.features.data.datasource.RemoteDataSource
import com.kotlin.unsplash.features.data.repositories.RepositoryImpl
import com.kotlin.unsplash.features.domain.repositories.Repository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private const val BASE_URL = "https://api.unsplash.com/"

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Singleton
    @Provides
    fun provideRemoteDataSource(): RemoteDataSource {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL).build().create(RemoteDataSource::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule{

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): PhotoDatabase {
        return Room.databaseBuilder(
            context,
            PhotoDatabase::class.java,
            "photo_database"
        ).build()
    }

    @Provides
    fun provideLocalDataSourceDao(localDatabase: PhotoDatabase): LocalDataSourceDao {
        return localDatabase.getLocalDataSourceDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DIContainer{
    @Binds
    abstract fun bindRepository(
        repositoryImpl: RepositoryImpl
    ): Repository
}