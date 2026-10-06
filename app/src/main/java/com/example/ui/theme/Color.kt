package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Professional Performance Sport Palette (Garmin / Apple Health / WHOOP inspired)
// Deep refined golf green, restrained saturation, high readability
val PerformanceGreenPrimary = Color(0xFF1B4332)   // Deep refined heritage athletic green
val PerformanceGreenDark = Color(0xFF143326)
val PerformanceGreenSubtle = Color(0xFF2D6A4F) // Muted athletic green
val PerformanceGreenContainer = Color(0xFFEAF2ED)
val OnPerformanceGreenContainer = Color(0xFF0F291E)

// Neutrals & Surfaces
val BackgroundLight = Color(0xFFF8F9FA)      // Clean laboratory / performance slate white
val SurfaceCard = Color(0xFFFFFFFF)          // Pure white card surfaces
val SurfaceCardSecondary = Color(0xFFF1F3F5) // Subtle secondary block
val SurfaceBorder = Color(0xFFE5E7EB)        // Precision 1px hairline border
val SurfaceBorderFocus = Color(0xFFD1D5DB)

// Typography Colors (Strict Contrast for 40~60s readability)
val TextPrimary = Color(0xFF191F24)          // Deep slate charcoal
val TextSecondary = Color(0xFF495057)        // Balanced secondary body
val TextTertiary = Color(0xFF6C757D)         // Metadata & labels
val TextDisabled = Color(0xFFADB5BD)

// Controlled status tokens (subtle, non-flashy)
val StatusPass = Color(0xFF15803D)           // Restrained forest green
val StatusPassBg = Color(0xFFE7F5EC)
val StatusCaution = Color(0xFFB45309)        // Restrained amber
val StatusCautionBg = Color(0xFFFEF3C7)
val StatusRestricted = Color(0xFFB91C1C)     // Restrained red
val StatusRestrictedBg = Color(0xFFFEE2E2)

// Mappings for existing component references
val FairwayGreenPrimary = PerformanceGreenPrimary
val FairwayGreenDark = PerformanceGreenDark
val FairwayGreenLight = PerformanceGreenSubtle
val FairwayGreenContainer = PerformanceGreenContainer
val OnFairwayGreenContainer = OnPerformanceGreenContainer
val EnergeticGold = StatusCaution
val EnergeticGoldLight = StatusCautionBg
val EnergeticCoral = StatusRestricted
val PineGreenPrimary = PerformanceGreenPrimary
val PineGreenLight = PerformanceGreenSubtle
val AccentGold = StatusCaution

val CleanWhiteBackground = BackgroundLight
val CleanWhiteSurface = SurfaceCard
val CleanWhiteSurfaceVariant = SurfaceCardSecondary
val CleanWhiteBorder = SurfaceBorder
val TextMainDark = TextPrimary
val TextMuted = TextSecondary
val TextSubtle = TextTertiary

val TagMintBg = StatusPassBg
val TagMintText = StatusPass
val TagAmberBg = StatusCautionBg
val TagAmberText = StatusCaution
val TagRedBg = StatusRestrictedBg
val TagRedText = StatusRestricted
val TagBlueBg = Color(0xFFE0F2FE)
val TagBlueText = Color(0xFF0369A1)
