package com.renovavision.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

const val VIDEOS_TABLE_NAME = "Videos"

@JsonClass(generateAdapter = true)
data class VideosResponse(
    val page: Int,
    @field:Json(name = "per_page") val perPage: Int,
    @field:Json(name = "total_results") val totalResults: Int,
    val url: String?,
    val videos: List<VideoResponse>?
)

@Entity(tableName = VIDEOS_TABLE_NAME)
@JsonClass(generateAdapter = true)
data class VideoResponse(
    @PrimaryKey
    val id: Long,
    val width: Int?,
    val height: Int?,
    val url: String?,
    val image: String?,
    val duration: Int?,
    @Embedded(prefix = "user")
    val user: UserResponse?,
    @field:Json(name = "video_files") val videoFiles: List<VideoFileResponse>?
)

@JsonClass(generateAdapter = true)
data class UserResponse(
    val id: Long,
    val name: String?,
    val url: String?
)

@JsonClass(generateAdapter = true)
data class VideoFileResponse(
    val id: Long,
    val quality: String?,
    @field:Json(name = "file_type") val fileType: String?,
    val width: Int?,
    val height: Int?,
    val link: String?
)

@JsonClass(generateAdapter = true)
data class VideoPictureResponse(
    val id: Long,
    val picture: String?,
    val nr: Int?
)