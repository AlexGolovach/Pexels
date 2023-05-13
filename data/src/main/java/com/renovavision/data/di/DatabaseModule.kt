package com.renovavision.data.di

import com.renovavision.data.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { AppDatabase.getInstance(get()).getPhotoDao() }
    single { AppDatabase.getInstance(get()).getVideoDao() }
}