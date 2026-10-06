package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 골프장 잔디의 싱그러움이 감도는 프리미엄 페어웨이 테마
private val GolfCourseColorScheme = lightColorScheme(
  primary = FairwayGreenPrimary,
  onPrimary = Color.White,
  primaryContainer = FairwayGreenContainer,
  onPrimaryContainer = OnFairwayGreenContainer,
  secondary = EnergeticGold,
  onSecondary = Color.White,
  tertiary = FairwayGreenLight,
  background = GolfCourseBackground,
  onBackground = TextMainDark,
  surface = GolfFairwayCardSurface,
  onSurface = TextMainDark,
  surfaceVariant = GolfCourseBackgroundDeep,
  onSurfaceVariant = TextMuted,
  outline = GolfGrassBorder
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = GolfCourseColorScheme,
    typography = Typography,
    content = content
  )
}
