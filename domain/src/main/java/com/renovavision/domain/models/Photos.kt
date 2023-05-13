package com.renovavision.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Photos(
    val page: Int,
    val perPage: Int,
    val photos: List<Photo>,
    val nextPage: String?
)

@Parcelize
data class Photo(
    val id: Long,
    val imageUrl: String,
    val originalImageUrl: String,
    val photographer: String?,
    val photographerUrl: String?,
    val photographerId: Long?,
    val alt: String?
) : Parcelable