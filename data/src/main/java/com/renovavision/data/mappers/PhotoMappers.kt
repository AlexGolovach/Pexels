package com.renovavision.data.mappers

import com.renovavision.data.models.PhotoResponse
import com.renovavision.data.models.PhotosResponse
import com.renovavision.domain.models.Photo
import com.renovavision.domain.models.Photos

val photosMapper: FunctionMapper<PhotosResponse, Photos> = {
    Photos(
        page = it.page,
        perPage = it.perPage,
        photos = it.photos.map { photo -> photoMapper(photo) },
        nextPage = it.nextPage
    )
}

val photoMapper: FunctionMapper<PhotoResponse, Photo> = {
    Photo(
        id = it.id,
        imageUrl = it.src?.large2x.orEmpty(),
        originalImageUrl = it.src?.original.orEmpty(),
        photographer = it.photographer,
        photographerUrl = it.photographerUrl,
        photographerId = it.photographerId,
        alt = it.alt
    )
}