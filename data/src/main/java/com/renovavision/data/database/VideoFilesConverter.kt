package com.renovavision.data.database

import androidx.room.TypeConverter
import com.renovavision.data.models.VideoFileResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class VideoFilesConverter {

    private val moshi by lazy {
        Moshi.Builder().build()
    }

    @TypeConverter
    fun fromListToJson(list: List<VideoFileResponse>): String {
        val type = Types.newParameterizedType(List::class.java, VideoFileResponse::class.java)
        val jsonAdapter = moshi.adapter<List<VideoFileResponse>>(type)

        return jsonAdapter.toJson(list)
    }

    @TypeConverter
    fun fromJsonToList(value: String): List<VideoFileResponse> {
        val type = Types.newParameterizedType(List::class.java, VideoFileResponse::class.java)
        val jsonAdapter = moshi.adapter<List<VideoFileResponse>>(type)

        return jsonAdapter.fromJson(value) ?: emptyList()
    }
}