package com.renovavision.photo_details.di

import com.renovavision.photo_details.viewModel.PhotoDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    viewModel { PhotoDetailsViewModel(get()) }
}