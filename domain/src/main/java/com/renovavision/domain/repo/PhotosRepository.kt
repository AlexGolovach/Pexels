package com.renovavision.domain.repo

import com.renovavision.domain.models.Photo

interface PhotosRepository {

    suspend fun loadPhotos(page: Int, offset: Int): List<Photo>
}