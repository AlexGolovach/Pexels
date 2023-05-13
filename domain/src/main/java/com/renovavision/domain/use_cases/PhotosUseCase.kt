package com.renovavision.domain.use_cases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.renovavision.domain.models.Photo
import com.renovavision.domain.paging.PhotosPagingSource
import com.renovavision.domain.repo.PhotosRepository
import kotlinx.coroutines.flow.Flow

class PhotosUseCase(private val repository: PhotosRepository) {

    fun invoke(): Flow<PagingData<Photo>> {
        return Pager(PagingConfig(pageSize = 15)) {
            PhotosPagingSource(repository)
        }.flow
    }
}