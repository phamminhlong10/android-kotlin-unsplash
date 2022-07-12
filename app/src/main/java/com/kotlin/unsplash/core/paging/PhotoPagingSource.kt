package com.kotlin.unsplash.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kotlin.unsplash.features.domain.entities.Photo
import com.kotlin.unsplash.features.domain.entities.Topic
import com.kotlin.unsplash.features.domain.repositories.Repository
import javax.inject.Inject


const val CLIENT_ID = "ZrI5lFMen8VP2J_NriVgXcRZqyZn_lqO2IA7ZM5jsNE"
const val MAIN_SCREEN = 0
const val TOPIC_PHOTO_SCREEN = 1
class PhotoPagingSource (private val repository: Repository, private val screen: Int, val topic: Topic?): PagingSource<Int, Photo>() {
    private lateinit var response: List<Photo>
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try{
            val nextPage = params.key ?: 1
            when(screen){
                MAIN_SCREEN ->  response = repository.getPhotos(CLIENT_ID, nextPage)
                TOPIC_PHOTO_SCREEN -> response =
                    topic?.id?.let { repository.getPhotoFromTopic(it, CLIENT_ID, nextPage) }!!
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