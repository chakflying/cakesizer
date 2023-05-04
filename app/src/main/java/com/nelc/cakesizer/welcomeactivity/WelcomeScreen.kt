package com.nelc.cakesizer.welcomeactivity

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nelc.cakesizer.ui.theme.CakeSizerTheme
import androidx.hilt.navigation.compose.hiltViewModel
import com.nelc.cakesizer.ui.NavEvents
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(
    updateRoute: (String) -> Unit,
    navEvents: NavEvents,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Greeting("Android")
        Button(onClick = {
            scope.launch {
                navEvents.startArEvents.tryEmit(Unit)
            }
        }) {
            Text(text = "Go to AR")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CakeSizerTheme {
        Greeting("Android")
    }
}