package com.groupe5.steamfav

import android.app.Application
import com.groupe5.steamfav.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.plant


class SteamFavApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(DebugTree())
        }
        startKoin{
            androidLogger()
            androidContext(this@SteamFavApp)
            modules(appModule)
        }
    }
}