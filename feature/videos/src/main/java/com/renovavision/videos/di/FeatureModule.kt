package com.renovavision.videos.di

import com.renovavision.domain.use_cases.VideosUseCase
import com.renovavision.videos.viewModel.VideoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    single { VideosUseCase(get()) }

    viewModel { VideoListViewModel(get(), get()) }
}