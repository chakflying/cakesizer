package com.nelc.cakesizer

import com.nelc.cakesizer.data.CakesService
import com.nelc.cakesizer.data.CakesServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.*

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    companion object {
        @Provides
        fun provideHttpClient(): HttpClient = HttpClient(OkHttp) {
            install(Logging) {
                level = LogLevel.INFO
            }
            engine {
                // this: OkHttpConfig
                config {
                    // this: OkHttpClient.Builder
                    followRedirects(true)
                    // ...
                }
            }
        }
    }

    @Binds
    abstract fun bindCakesService(cakesServiceImpl: CakesServiceImpl): CakesService
}