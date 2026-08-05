package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = BrokeItGreen,
    onPrimary = Color.White,
    primaryContainer = BrokeItGreenLight,
    onPrimaryContainer = BrokeItGreenDark,
    secondary = BrokeItGreenAccent,
    onSecondary = Color.White,
    tertiary = BrokeItYellow,
    background = BrokeItBackground,
    onBackground = BrokeItDark,
    surface = BrokeItSurface,
    onSurface = BrokeItDark,
    surfaceVariant = BrokeItCardBg,
    onSurfaceVariant = BrokeItGray,
    outline = BrokeItBorder,
    error = BrokeItRed,
    onError = Color.White
)

@Composable
fun BrokeItTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
