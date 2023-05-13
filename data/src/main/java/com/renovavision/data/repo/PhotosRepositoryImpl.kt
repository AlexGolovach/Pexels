package com.renovavision.data.repo

import com.renovavision.data.NetworkBoundResources
import com.renovavision.data.api.PhotosApi
import com.renovavision.data.database.PhotoDao
import com.renovavision.data.mappers.photoMapper
import com.renovavision.domain.models.Photo
import com.renovavision.domain.repo.PhotosRepository

class PhotosRepositoryImpl(private val api: PhotosApi, private val dao: PhotoDao) : PhotosRepository {

    override suspend fun loadPhotos(page: Int, offset: Int): List<Photo> {
        val photos = NetworkBoundResources(
            { dao.insertPhotos(it.photos) },
            { dao.getPhotos(offset).map { photo -> photoMapper(photo) } },
            { api.loadPhotos(page) }
        ).load()

        return photos ?: emptyList()
    }
}