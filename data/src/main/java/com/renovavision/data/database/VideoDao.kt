package com.renovavision.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renovavision.data.models.VIDEOS_TABLE_NAME
import com.renovavision.data.models.VideoResponse

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideos(videos: List<VideoResponse>)

    @Query("DELETE FROM $VIDEOS_TABLE_NAME")
    fun deleteAllVideos()

    @Query("SELECT * FROM $VIDEOS_TABLE_NAME LIMIT 15 OFFSET :offset")
    fun getVideos(offset: Int): List<VideoResponse>
}