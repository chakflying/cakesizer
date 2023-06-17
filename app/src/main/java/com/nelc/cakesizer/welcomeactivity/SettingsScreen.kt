package com.nelc.cakesizer.welcomeactivity

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.nelc.cakesizer.R
import com.nelc.cakesizer.data.SettingsStore
import com.nelc.cakesizer.ui.theme.CakeSizerTheme
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val settingsStore = SettingsStore(context)

    val language = settingsStore.languageFlow.collectAsState(initial = "")
    val quality = settingsStore.qualityFlow.collectAsState(initial = 0)

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 12.dp),
        color = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 24.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    text = stringResource(R.string.settings),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                IconButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = {
                        onClose()
                    }
                ) {
                    Icon(
                        Icons.Rounded.Close,
                        contentDescription = "Close Settings",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 6.dp),
                    text = stringResource(R.string.language),
                    style = MaterialTheme.typography.bodyLarge,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        modifier = Modifier
                            .clickable {
                                scope.launch {
                                    settingsStore.setLanguage("zh")
                                }
                            }
                            .padding(24.dp),
                        text = "繁",
                        color = if (language.value == "zh") {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        },
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        modifier = Modifier
                            .clickable {
                                scope.launch {
                                    settingsStore.setLanguage("en")
                                }
                            }
                            .padding(24.dp),
                        text = "Eng",
                        color = if (language.value == "en") {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        },
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 6.dp),
                    text = stringResource(R.string.quality),
                    style = MaterialTheme.typography.bodyLarge,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        modifier = Modifier
                            .clickable {
                                scope.launch {
                                    settingsStore.setQuality(0)
                                }
                            }
                            .padding(24.dp),
                        color = if (quality.value == 0) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        },
                        text = stringResource(R.string.high),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        modifier = Modifier
                            .clickable {
                                scope.launch {
                                    settingsStore.setQuality(1)
                                }
                            }
                            .padding(24.dp),
                        color = if (quality.value == 1) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        },
                        text = stringResource(R.string.medium),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }

            }

        }
    }
}

fun setAppLanguage(language: String) {
    val enLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("en")
    val zhLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("zh-HK")

    // Call this on the main thread as it may require Activity.restart()
    if (language == "zh") {
        AppCompatDelegate.setApplicationLocales(zhLocale)
    }
    if (language == "en") {
        AppCompatDelegate.setApplicationLocales(enLocale)
    }
}


@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    CakeSizerTheme {
        SettingsScreen()
    }
}
