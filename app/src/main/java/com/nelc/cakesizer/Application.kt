package com.nelc.cakesizer

import android.app.Application
import com.google.android.filament.utils.Utils
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Application : Application() {
    companion object {
        lateinit var instance: com.nelc.cakesizer.Application private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Utils.init()

        Timber.plant(Timber.DebugTree())
    }
}
