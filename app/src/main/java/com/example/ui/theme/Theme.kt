package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
  primary = FairwayGreenPrimary,
  onPrimary = Color.White,
  primaryContainer = FairwayGreenContainer,
  onPrimaryContainer = OnFairwayGreenContainer,
  secondary = EnergeticGold,
  onSecondary = Color.White,
  tertiary = FairwayGreenLight,
  background = CleanWhiteBackground,
  onBackground = TextMainDark,
  surface = CleanWhiteSurface,
  onSurface = TextMainDark,
  surfaceVariant = CleanWhiteSurfaceVariant,
  onSurfaceVariant = TextMuted,
  outline = CleanWhiteBorder
)

private val DarkColorScheme = lightColorScheme(
  // Maintain a clean, bright, visible white/green appearance as requested
  primary = FairwayGreenPrimary,
  onPrimary = Color.White,
  primaryContainer = FairwayGreenContainer,
  onPrimaryContainer = OnFairwayGreenContainer,
  secondary = EnergeticGold,
  onSecondary = Color.White,
  tertiary = FairwayGreenLight,
  background = CleanWhiteBackground,
  onBackground = TextMainDark,
  surface = CleanWhiteSurface,
  onSurface = TextMainDark,
  surfaceVariant = CleanWhiteSurfaceVariant,
  onSurfaceVariant = TextMuted,
  outline = CleanWhiteBorder
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = LightColorScheme, // Keep bright white and fresh golf green
    typography = Typography,
    content = content
  )
}
