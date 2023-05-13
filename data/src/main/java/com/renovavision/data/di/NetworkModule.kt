package com.renovavision.data.di

import com.renovavision.data.BuildConfig
import com.renovavision.data.api.PhotosApi
import com.renovavision.data.api.VideosApi
import com.renovavision.data.interceptor.AuthInterceptor
import com.renovavision.data.repo.PhotosRepositoryImpl
import com.renovavision.data.repo.VideosRepositoryImpl
import com.renovavision.domain.repo.PhotosRepository
import com.renovavision.domain.repo.VideosRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

private const val PHOTOS = "PHOTOS"
private const val VIDEOS = "VIDEOS"

fun networkModule(apiKey: String, httpCacheDir: File?) = module {
    single {
        val authInterceptor = AuthInterceptor(apiKey)
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        OkHttpClient.Builder().apply {
            httpCacheDir?.let { cache(Cache(it, (1024 * 1024 * 100))) }
        }.addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    single { MoshiConverterFactory.create() } bind Converter.Factory::class

    single(named(PHOTOS)) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.PHOTO_API_URL)
            .client(get())
            .addConverterFactory(get())
            .build()
    }

    single(named(VIDEOS)) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.VIDEO_API_URL)
            .client(get())
            .addConverterFactory(get())
            .build()
    }

    single { get<Retrofit>(named(PHOTOS)).create(PhotosApi::class.java) }
    single { get<Retrofit>(named(VIDEOS)).create(VideosApi::class.java) }

    single<PhotosRepository> { PhotosRepositoryImpl(get(), get()) }
    single<VideosRepository> { VideosRepositoryImpl(get(), get()) }
}