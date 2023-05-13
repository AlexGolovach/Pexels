package com.renovavision.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renovavision.data.models.PHOTOS_TABLE_NAME
import com.renovavision.data.models.PhotoResponse

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotos(photos: List<PhotoResponse>)

    @Query("DELETE FROM $PHOTOS_TABLE_NAME")
    fun deleteAllPhotos()

    @Query("SELECT * FROM $PHOTOS_TABLE_NAME LIMIT 15 OFFSET :offset")
    fun getPhotos(offset: Int): List<PhotoResponse>
}