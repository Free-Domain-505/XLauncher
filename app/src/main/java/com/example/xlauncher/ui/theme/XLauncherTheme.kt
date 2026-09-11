package com.example.xlauncher.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

val DarkBlue = Color(0xFF0B132B)
val LightBlue = Color(0xFF1C2541)
val AccentBlue = Color(0xFF3A506B)
val CyanAccent = Color(0xFF5BC0BE)
val TextPrimary = Color(0xFFFFFFFF)
val TextSecondary = Color(0xFFA0AAB2)

val XLauncherColorScheme = darkColorScheme(
    primary = CyanAccent,
    onPrimary = DarkBlue,
    primaryContainer = AccentBlue,
    onPrimaryContainer = TextPrimary,
    secondary = LightBlue,
    onSecondary = TextPrimary,
    background = DarkBlue,
    onBackground = TextPrimary,
    surface = LightBlue,
    onSurface = TextPrimary,
    surfaceVariant = AccentBlue,
    onSurfaceVariant = TextSecondary,
    error = Color(0xFFCF6679),
    onError = Color.Black
)
