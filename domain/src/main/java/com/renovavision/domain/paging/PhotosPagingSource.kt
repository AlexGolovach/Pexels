package com.renovavision.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.renovavision.domain.models.Photo
import com.renovavision.domain.repo.PhotosRepository

class PhotosPagingSource(private val repository: PhotosRepository) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return runCatching {
            val nextPage = params.key ?: 1
            val response = repository.loadPhotos(nextPage, (nextPage - 1) * params.loadSize)

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isEmpty()) null else nextPage + 1
            )
        }.getOrElse {
            LoadResult.Error(it)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}