package com.nelc.cakesizer

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import coil.util.DebugLogger
import com.google.android.filament.utils.Utils
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Application : Application(), ImageLoaderFactory {
    companion object {
        lateinit var instance: com.nelc.cakesizer.Application private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Utils.init()

        Timber.plant(Timber.DebugTree())
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(SvgDecoder.Factory())
            }
            .logger(DebugLogger())
            .crossfade(true)
            .build()
    }
}
