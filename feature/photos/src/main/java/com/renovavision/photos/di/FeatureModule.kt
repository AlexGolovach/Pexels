package com.renovavision.photos.di

import com.renovavision.domain.use_cases.PhotosUseCase
import com.renovavision.photos.viewModel.PhotoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    single { PhotosUseCase(get()) }

    viewModel { PhotoListViewModel(get(), get()) }
}