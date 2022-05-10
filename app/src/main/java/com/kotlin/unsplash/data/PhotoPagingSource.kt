package com.kotlin.unsplash.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kotlin.unsplash.domain.Photo
import com.kotlin.unsplash.domain.Topic
import com.kotlin.unsplash.service.CLIENT_ID
import com.kotlin.unsplash.service.UnsplashService


const val MAIN_SCREEN = 0
const val TOPIC_PHOTO_SCREEN = 1
class PhotoPagingSource(private val unsplashService: UnsplashService, private val screen: Int, val topic: Topic?): PagingSource<Int, Photo>() {
    private lateinit var response: List<Photo>
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try{
            val nextPage = params.key ?: 1
            when(screen){
                0 ->  response = unsplashService.getListPhoto(CLIENT_ID, nextPage)
                1 -> response = unsplashService.getListTopicPhoto(topic?.id, CLIENT_ID, nextPage)
            }

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