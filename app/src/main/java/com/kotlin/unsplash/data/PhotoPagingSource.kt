package com.kotlin.unsplash.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kotlin.unsplash.domain.Photo
import com.kotlin.unsplash.service.CLIENT_ID
import com.kotlin.unsplash.service.UnsplashService

class PhotoPagingSource(private val unsplashService: UnsplashService ): PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try{
            val nextPage = params.key ?: 1
            val response = unsplashService.getListPhoto(CLIENT_ID, nextPage)

            LoadResult.Page(
                data = response,
                prevKey = if(nextPage == 1) null else nextPage -1,
                nextKey = nextPage+1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}