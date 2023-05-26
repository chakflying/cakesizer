package com.nelc.cakesizer.provider

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToastProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : ToastProvider {
    init {
        Timber.i("ToastProvider initialized")
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}
