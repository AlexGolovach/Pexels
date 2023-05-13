package com.renovavision.pexels

import android.app.Application
import com.renovavision.data.di.databaseModule
import com.renovavision.data.di.networkModule
import com.renovavision.file_helper.helpersModule
import com.renovavision.navigation.di.navigationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                navigationModule,
                networkModule(BuildConfig.API_KEY, cacheDir),
                databaseModule,
                helpersModule
            )
        }
    }
}