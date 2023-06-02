package com.nelc.cakesizer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.nelc.cakesizer.R

val Rubik = FontFamily(
    Font(R.font.rubik_light, FontWeight.Light),
    Font(R.font.rubik_regular, FontWeight.Normal),
    Font(R.font.rubik_bold, FontWeight.Bold),
    Font(R.font.rubik_black, FontWeight.Black),
)

val defaultTypography = Typography()

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = Rubik),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = Rubik),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = Rubik),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = Rubik),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = Rubik),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = Rubik),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = Rubik),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = Rubik),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = Rubik),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = Rubik),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = Rubik),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = Rubik),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = Rubik),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = Rubik),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = Rubik)
)