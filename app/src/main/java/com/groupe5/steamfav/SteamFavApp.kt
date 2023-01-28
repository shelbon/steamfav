package com.groupe5.steamfav

import android.app.Application
import timber.log.Timber.*
import timber.log.Timber.Forest.plant


class SteamFavApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(DebugTree())
        }
    }
}