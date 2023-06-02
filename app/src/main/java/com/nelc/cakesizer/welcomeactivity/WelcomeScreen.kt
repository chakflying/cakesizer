package com.nelc.cakesizer.welcomeactivity

import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.nelc.cakesizer.R
import com.nelc.cakesizer.ui.NavEvents
import com.nelc.cakesizer.ui.theme.CakeSizerTheme
import com.nelc.cakesizer.ui.theme.Gray200
import com.nelc.cakesizer.ui.theme.Gray800
import com.nelc.cakesizer.ui.theme.Red500
import com.nelc.cakesizer.ui.theme.Typography
import com.nelc.cakesizer.ui.theme.Yellow100
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(
    updateRoute: (String) -> Unit,
    navEvents: NavEvents,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    val isDarkTheme = isSystemInDarkTheme()
    val context = LocalContext.current
    val pattern = getDrawable(context, R.drawable.background_tile)!!
        .toBitmap(400, 400).asImageBitmap()

    val backgroundScroll = remember {
        Animatable(0F)
    }

    val sizeScrollState = rememberScrollState()
    val designScrollState = rememberScrollState()

    var showCredits by remember { mutableStateOf(false) }

    LaunchedEffect(navEvents) {
        viewModel.navEvents = navEvents
    }

    LaunchedEffect(backgroundScroll) {
        backgroundScroll.animateTo(
            400000f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3000000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart,
            )
        )
    }

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {

            val paint = if (isDarkTheme) {
                Paint().asFrameworkPaint().apply {
                    alpha = 70
                    isAntiAlias = true
                    shader = ImageShader(pattern, TileMode.Repeated, TileMode.Repeated)
                }
            } else {
                Paint().asFrameworkPaint().apply {
                    alpha = 100
                    isAntiAlias = true
                    shader = ImageShader(pattern, TileMode.Repeated, TileMode.Repeated)
                }
            }
            translate(top = backgroundScroll.value) {
                rotate(degrees = 15F) {
                    drawIntoCanvas {
                        if (isDarkTheme) {
                            it.nativeCanvas.drawARGB(255, 0, 0, 0)

                        } else {
                            it.nativeCanvas.drawARGB(255, 255, 255, 255)
                        }
                        it.nativeCanvas.drawPaint(paint)
                    }
                }
            }

            paint.reset()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp),
                imageVector = ImageVector.vectorResource(R.drawable.title_01),
                contentScale = ContentScale.Fit,
                contentDescription = "CakeSizer",
                colorFilter = ColorFilter.lighting(add = Red500, multiply = Color.Gray)
            )

            // Size Selector
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 4.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = stringResource(R.string.diameter),
                        style = Typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .horizontalScroll(sizeScrollState),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        if (viewModel.sizeList.isNotEmpty()) {
                            viewModel.sizeList.forEach { i ->
                                Surface(
                                    color = if (viewModel.selectedSize == i) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        Yellow100
                                    },
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .height(96.dp)
                                        .width(96.dp)
                                        .clickable {
                                            viewModel.setSelectedSize(i)
                                        },
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = stringResource(R.string.num_inch, i),
                                            style = Typography.displaySmall,
                                            color = if (viewModel.selectedSize == i) {
                                                MaterialTheme.colorScheme.onPrimary
                                            } else {
                                                Gray800
                                            },
                                        )
                                    }
                                }
                            }
                        } else {
                            // Placeholder
                            (0..2).forEach { _ ->
                                Surface(
                                    color = Yellow100,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .height(96.dp)
                                        .width(96.dp)
                                        .placeholder(
                                            visible = true,
                                            shape = RoundedCornerShape(12.dp),
                                            highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White),
                                            color = Gray200,
                                        ),
                                ) {
                                }
                            }
                        }
                    }

                    Text(
                        modifier = Modifier.padding(top = 12.dp),
                        text = stringResource(R.string.design),
                        style = Typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .horizontalScroll(designScrollState),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        if (viewModel.filteredCakes.isNotEmpty()) {
                            viewModel.filteredCakes.forEach { cake ->
                                Surface(
                                    color = if (viewModel.selectedCake == cake) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        Yellow100
                                    },
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .height(96.dp)
                                        .width(96.dp)
                                        .clickable { viewModel.selectCake(cake) },
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        val thumbnailUrl = URLBuilder().apply {
                                            protocol = URLProtocol.HTTPS
                                            host = stringResource(R.string.server_host)
                                            path(cake.thumbnail_path)
                                        }.buildString()

                                        AsyncImage(
                                            model = thumbnailUrl,
                                            placeholder = painterResource(R.drawable.cake_icon),
                                            contentDescription = "Cake preview",
                                            contentScale = ContentScale.Fit,
                                        )
                                    }
                                }
                            }
                        } else {
                            // Placeholder
                            (0..2).forEach { _ ->
                                Surface(
                                    color = Yellow100,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .height(96.dp)
                                        .width(96.dp)
                                        .placeholder(
                                            visible = true,
                                            shape = RoundedCornerShape(12.dp),
                                            highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White),
                                            color = Gray200,
                                        ),
                                ) {
                                }
                            }
                        }
                    }
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                enabled = viewModel.selectedCake != null,
                onClick = {
                    scope.launch {
                        viewModel.start()
                    }
                }) {
                Text(
                    text = stringResource(R.string.start),
                    style = Typography.displaySmall
                )
            }

            Text(
                modifier = Modifier
                    .padding(6.dp)
                    .clickable {
                        showCredits = true
                    },
                text = stringResource(R.string.credits),
                style = MaterialTheme.typography.bodySmall,
                textDecoration = TextDecoration.Underline
            )
        }

        if (showCredits) {
            Dialog(
                onDismissRequest = {
                    showCredits = false
                },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                AnimatedVisibility(
                    visible = showCredits,
                    enter = slideInVertically { fullHeight ->
                        fullHeight * 2
                    },
                    exit = slideOutVertically { fullHeight ->
                        fullHeight * 2
                    }
                ) {
                    CreditsScreen(
                        modifier = Modifier.padding(32.dp),
                        onClose = {
                            showCredits = false
                        }
                    )
                }
            }
        }
    }

    if (viewModel.downloading) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background.copy(alpha = 0.7f),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
            ) {
                CircularProgressIndicator(
                    progress = viewModel.downloadProgressState.value.toFloat() / (viewModel.selectedCake?.model_size?.toFloat()
                        ?: 1f),
                    modifier = Modifier
                        .width(96.dp)
                        .height(96.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 8.dp,
                )
                Text(
                    text = stringResource(R.string.downloading_model),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CakeSizerTheme {
        WelcomeScreen(
            updateRoute = {},
            navEvents = NavEvents(MutableSharedFlow())
        )
    }
}