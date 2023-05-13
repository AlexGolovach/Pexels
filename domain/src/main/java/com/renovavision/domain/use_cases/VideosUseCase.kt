package com.renovavision.domain.use_cases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.renovavision.domain.models.Video
import com.renovavision.domain.paging.VideosPagingSource
import com.renovavision.domain.repo.VideosRepository
import kotlinx.coroutines.flow.Flow

class VideosUseCase(private val repository: VideosRepository) {

    fun invoke(): Flow<PagingData<Video>> {
        return Pager(PagingConfig(pageSize = 15)) {
            VideosPagingSource(repository)
        }.flow
    }
}