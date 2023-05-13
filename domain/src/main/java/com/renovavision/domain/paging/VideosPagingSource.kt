package com.renovavision.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.renovavision.domain.models.Video
import com.renovavision.domain.repo.VideosRepository

class VideosPagingSource(private val repository: VideosRepository) : PagingSource<Int, Video>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Video> {
        return runCatching {
            val nextPage = params.key ?: 1
            val response = repository.loadVideos(nextPage, (nextPage - 1) * params.loadSize)

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isEmpty()) null else nextPage + 1
            )
        }.getOrElse {
            LoadResult.Error(it)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Video>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}