package com.renovavision.navigation.di

import com.renovavision.navigation.navigation.PhotoNavigation
import com.renovavision.navigation.navigation.VideoNavigation
import com.renovavision.navigation.navigator.AppNavigator
import com.renovavision.navigation.navigator.IAppNavigator
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module

val navigationModule = module {
    single<IAppNavigator> { AppNavigator() }.binds(
        arrayOf(
            PhotoNavigation::class,
            VideoNavigation::class
        )
    )
}