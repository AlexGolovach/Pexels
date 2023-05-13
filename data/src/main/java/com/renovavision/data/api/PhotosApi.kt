package com.renovavision.data.api

import com.renovavision.data.models.PhotosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosApi {

    @GET("curated")
    suspend fun loadPhotos(@Query("page") page: Int): PhotosResponse
}