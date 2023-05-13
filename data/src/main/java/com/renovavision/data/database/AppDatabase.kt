package com.renovavision.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.renovavision.data.models.PhotoResponse
import com.renovavision.data.models.VideoResponse

@TypeConverters(VideoFilesConverter::class)
@Database(
    version = 1,
    entities = [
        PhotoResponse::class,
        VideoResponse::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getPhotoDao(): PhotoDao

    abstract fun getVideoDao(): VideoDao

    companion object {
        const val DB_NAME = "Pexel.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}