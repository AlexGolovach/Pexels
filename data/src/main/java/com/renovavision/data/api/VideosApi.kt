package com.renovavision.data.api

import com.renovavision.data.models.VideosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VideosApi {

    @GET("popular")
    suspend fun loadVideos(@Query("page") page: Int): VideosResponse
}