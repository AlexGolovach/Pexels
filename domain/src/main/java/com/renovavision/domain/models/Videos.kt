package com.renovavision.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Videos(
    val page: Int,
    val perPage: Int,
    val totalResults: Int,
    val url: String?,
    val videos: List<Video>
)

@Parcelize
data class Video(
    val id: Long,
    val image: String?,
    val user: User?,
    val videoFile: VideoFile?
) : Parcelable

@Parcelize
data class User(
    val id: Long,
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class VideoFile(
    val id: Long,
    val fileType: String?,
    val link: String?
) : Parcelable