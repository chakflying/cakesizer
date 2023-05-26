package com.nelc.cakesizer

import android.content.Context
import com.nelc.cakesizer.provider.ToastProvider
import com.nelc.cakesizer.provider.ToastProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {

    @Provides
    @Singleton
    fun provideToast(
        @ApplicationContext context: Context,
    ): ToastProvider {
        return ToastProviderImpl(context = context)
    }
}
