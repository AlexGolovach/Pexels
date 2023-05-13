package com.renovavision.domain.repo

import com.renovavision.domain.models.Video

interface VideosRepository {

    suspend fun loadVideos(page: Int, offset: Int): List<Video>
}