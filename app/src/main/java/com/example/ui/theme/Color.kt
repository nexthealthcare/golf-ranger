package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// 골프레인저 산뜻한 그린 & 클린 화이트 팔레트 (높은 가시성과 친근한 비주얼)
val FairwayGreenPrimary = Color(0xFF1E7E34)     // 싱그러운 필드 페어웨이 그린
val FairwayGreenDark = Color(0xFF14532D)        // 딥 포레스트 그린
val FairwayGreenLight = Color(0xFF22C55E)       // 밝은 포인트 그린
val FairwayGreenContainer = Color(0xFFE8F5E9)   // 화사한 연초록 컨테이너
val OnFairwayGreenContainer = Color(0xFF0F5132)

// 퍼포먼스 & 보조 색상
val PerformanceGreenPrimary = FairwayGreenPrimary
val PerformanceGreenDark = FairwayGreenDark
val PerformanceGreenSubtle = Color(0xFF2E7D32)
val PerformanceGreenContainer = FairwayGreenContainer
val OnPerformanceGreenContainer = OnFairwayGreenContainer
val PineGreenPrimary = FairwayGreenPrimary
val PineGreenLight = FairwayGreenLight

// 산뜻한 화이트 & 뉴트럴 배경
val CleanWhiteBackground = Color(0xFFF7FAF8)    // 쾌적하고 눈이 편안한 밝은 배경
val CleanWhiteSurface = Color(0xFFFFFFFF)       // 순백색 카드 서피스
val CleanWhiteSurfaceVariant = Color(0xFFF1F5F2)
val CleanWhiteBorder = Color(0xFFE2E8F0)        // 부드러운 경계선
val BackgroundLight = CleanWhiteBackground
val SurfaceCard = CleanWhiteSurface
val SurfaceCardSecondary = CleanWhiteSurfaceVariant
val SurfaceBorder = CleanWhiteBorder
val SurfaceBorderFocus = Color(0xFFCBD5E1)

// 선명한 텍스트 가독성 (40~60대 골퍼도 또렷하게 읽히는 대비)
val TextMainDark = Color(0xFF111827)            // 진한 차콜 블랙
val TextMuted = Color(0xFF4B5563)               // 부드러운 중간 톤
val TextSubtle = Color(0xFF6B7280)              // 캡션 텍스트
val TextPrimary = TextMainDark
val TextSecondary = TextMuted
val TextTertiary = TextSubtle
val TextDisabled = Color(0xFF9CA3AF)

// 포인트 액센트
val EnergeticGold = Color(0xFFD97706)           // 활력 넘치는 골드 오렌지 (드릴)
val EnergeticGoldLight = Color(0xFFFEF3C7)
val AccentGold = EnergeticGold
val EnergeticCoral = Color(0xFFDC2626)          // 주의 알림 코랄 레드

// 직관적인 3등급 상태 태그
val StatusPass = Color(0xFF16A34A)              // 정상 (그린)
val StatusPassBg = Color(0xFFDCFCE7)
val StatusCaution = Color(0xFFD97706)           // 주의 (옐로우/앰버)
val StatusCautionBg = Color(0xFFFEF3C7)
val StatusRestricted = Color(0xFFDC2626)        // 제한 (레드)
val StatusRestrictedBg = Color(0xFFFEE2E2)

val TagMintBg = StatusPassBg
val TagMintText = StatusPass
val TagAmberBg = StatusCautionBg
val TagAmberText = StatusCaution
val TagRedBg = StatusRestrictedBg
val TagRedText = StatusRestricted
val TagBlueBg = Color(0xFFE0F2FE)
val TagBlueText = Color(0xFF0284C7)
