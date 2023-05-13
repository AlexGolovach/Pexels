package com.renovavision.data.repo

import com.renovavision.data.NetworkBoundResources
import com.renovavision.data.api.VideosApi
import com.renovavision.data.database.VideoDao
import com.renovavision.data.mappers.videosMapper
import com.renovavision.domain.models.Video
import com.renovavision.domain.repo.VideosRepository

class VideosRepositoryImpl(private val api: VideosApi, private val dao: VideoDao) : VideosRepository {

    override suspend fun loadVideos(page: Int, offset: Int): List<Video> {
        val videos = NetworkBoundResources(
            { dao.insertVideos(it.videos ?: emptyList()) },
            { dao.getVideos(offset).map { video -> videosMapper(video) } },
            { api.loadVideos(page) }
        ).load()

        return videos ?: emptyList()
    }
}