package com.renovavision.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

const val PHOTOS_TABLE_NAME = "Photos"

@JsonClass(generateAdapter = true)
data class PhotosResponse(
    val page: Int,
    @field:Json(name = "per_page") val perPage: Int,
    val photos: List<PhotoResponse>,
    @field:Json(name = "next_page") val nextPage: String?
)

@Entity(tableName = PHOTOS_TABLE_NAME)
@JsonClass(generateAdapter = true)
data class PhotoResponse(
    @PrimaryKey
    val id: Long,
    val width: Int?,
    val height: Int?,
    val url: String?,
    val photographer: String?,
    @field:Json(name = "photographer_url") val photographerUrl: String?,
    @field:Json(name = "photographer_id") val photographerId: Long?,
    @field:Json(name = "avg_color") val avgColor: String,
    @Embedded
    val src: ImageResponse?,
    val liked: Boolean?,
    val alt: String?
)

@JsonClass(generateAdapter = true)
data class ImageResponse(
    val original: String?,
    val large2x: String?,
    val large: String?,
    val medium: String?,
    val small: String?,
    val portrait: String?,
    val landscape: String?,
    val tiny: String?
)