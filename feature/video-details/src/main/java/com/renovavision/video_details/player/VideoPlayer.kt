package com.renovavision.video_details.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.FileDataSource
import androidx.media3.datasource.cache.CacheDataSink
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import java.io.File

@UnstableApi
class VideoPlayer(private val context: Context) {

    private var exoPlayer: ExoPlayer? = null
    private var videoCache: SimpleCache? = null
    private var playbackPosition = 0L

    fun initPlayer(videoUrl: String): ExoPlayer? {
        exoPlayer = ExoPlayer.Builder(context).build().apply {
            playWhenReady = true

            initVideoCache()
            val cacheDataSourceFactory = initCacheDataSourceFactory()

            val mediaItem = MediaItem.fromUri(videoUrl)
            val mediaSource = cacheDataSourceFactory?.let { DefaultMediaSourceFactory(it).createMediaSource(mediaItem) }

            mediaSource?.let {
                setMediaSource(it)
            } ?: setMediaItem(mediaItem)

            seekTo(playbackPosition)
            prepare()
            play()
        }

        return exoPlayer
    }

    fun releasePlayer() {
        videoCache?.apply {
            release()
            videoCache = null
        }

        exoPlayer?.apply {
            playbackPosition = currentPosition

            release()
            exoPlayer = null
        }
    }

    private fun initVideoCache() {
        val cacheDirectory = "exo_player_cache"
        val downloadContentDirectory =
            File(context.externalCacheDir, cacheDirectory)
        videoCache = SimpleCache(downloadContentDirectory, NoOpCacheEvictor(), StandaloneDatabaseProvider(context))
    }

    private fun initCacheDataSourceFactory(): CacheDataSource.Factory? {
        return videoCache?.let {
            val cacheSink = CacheDataSink.Factory()
                .setCache(it)
            val upstreamFactory = DefaultDataSource.Factory(context, DefaultHttpDataSource.Factory())
            val downStreamFactory = FileDataSource.Factory()

            CacheDataSource.Factory()
                .setCache(it)
                .setCacheWriteDataSinkFactory(cacheSink)
                .setCacheReadDataSourceFactory(downStreamFactory)
                .setUpstreamDataSourceFactory(upstreamFactory)
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
        }
    }
}