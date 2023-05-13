package com.renovavision.video_details.di

import com.renovavision.video_details.player.VideoPlayer
import com.renovavision.video_details.viewModel.VideoDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureDetails = module {
    single { VideoPlayer(get()) }

    viewModel { VideoDetailsViewModel(get()) }
}