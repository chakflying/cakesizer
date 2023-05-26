package com.nelc.cakesizer.welcomeactivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.nelc.cakesizer.aractivity.ArActivity
import com.nelc.cakesizer.ui.NavEvents
import com.nelc.cakesizer.ui.NavWrapper
import com.nelc.cakesizer.ui.theme.CakeSizerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import timber.log.Timber

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {
    private val startArEvents: MutableSharedFlow<String> =
        MutableSharedFlow(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val finish: () -> Unit = {
            this.finish()
        }

        val scope = CoroutineScope(Dispatchers.Main)
        val arActivityIntent = Intent(this, ArActivity::class.java)

        startArEvents.map {
            Timber.i("Start AR received")
            arActivityIntent.putExtra("modelPath", it)
            startActivity(arActivityIntent)
        }.launchIn(scope)

        setContent {
            CakeSizerTheme {
                NavWrapper(
                    finish, NavEvents(startArEvents)
                )
            }
        }
    }
}