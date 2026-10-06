package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// =========================================================================
// 골프장 필드(페어웨이 & 그린)를 연상시키는 산뜻하고 고급스러운 골프 코스 컬러 시스템
// 너무 진하거나 어둡지 않고, 푸른 잔디와 페어웨이의 쾌적함이 은은하게 감도는 톤
// =========================================================================

// 1. 브랜드 & 메인 페어웨이 그린
val FairwayGreenPrimary = Color(0xFF227C3E)        // 싱그러운 페어웨이 잔디 그린 (눈이 편안한 미디엄 톤)
val FairwayGreenDark = Color(0xFF185D2E)           // 깊이감 있는 딥 그린
val FairwayGreenLight = Color(0xFF38A169)          // 활기찬 라이트 그린
val FairwayGreenContainer = Color(0xFFE2F2E6)      // 부드러운 그린 잔디 컨테이너
val OnFairwayGreenContainer = Color(0xFF0F4722)

val PerformanceGreenPrimary = FairwayGreenPrimary
val PerformanceGreenDark = FairwayGreenDark
val PerformanceGreenSubtle = Color(0xFF2E8540)
val PerformanceGreenContainer = FairwayGreenContainer
val OnPerformanceGreenContainer = OnFairwayGreenContainer
val PineGreenPrimary = FairwayGreenPrimary
val PineGreenLight = FairwayGreenLight

// 2. 골프장 필드 감성의 배경 & 서피스 (새하얀 병원 느낌 탈피!)
// 새하얀 백색 대신, 푸른 페어웨이와 그린을 연상시키는 은은하고 산뜻한 잔디빛 틴트 적용
val GolfCourseBackground = Color(0xFFEEF6F0)        // 필드 잔디의 쾌적함이 감도는 소프트 그린 배경
val GolfCourseBackgroundDeep = Color(0xFFE5F1E8)    // 살짝 더 짙은 잔디 틴트
val GolfFairwayCardSurface = Color(0xFFF7FAF8)      // 포근하고 은은한 잔디빛 카드 서피스
val GolfCardSurfaceHighlight = Color(0xFFE7F4EB)    // 하이라이트 카드용 페어웨이 틴트
val GolfGrassBorder = Color(0xFFC3DEC8)             // 골프장 잔디 결을 살린 부드러운 그린 보더
val GolfGrassBorderFocus = Color(0xFFA1CFA8)        // 활성화 보더

// 기존 토큰과의 호환 매핑
val CleanWhiteBackground = GolfCourseBackground
val CleanWhiteSurface = GolfFairwayCardSurface
val CleanWhiteSurfaceVariant = GolfCourseBackgroundDeep
val CleanWhiteBorder = GolfGrassBorder
val BackgroundLight = GolfCourseBackground
val SurfaceCard = GolfFairwayCardSurface
val SurfaceCardSecondary = GolfCourseBackgroundDeep
val SurfaceBorder = GolfGrassBorder
val SurfaceBorderFocus = GolfGrassBorderFocus

// 3. 선명한 가독성 (골프 코스 텍스트)
val TextMainDark = Color(0xFF132317)                // 딥 포레스트 차콜 (선명하고 고급스러운 대비)
val TextMuted = Color(0xFF3E5443)                   // 차분한 올리브 차콜 본문
val TextSubtle = Color(0xFF637C68)                  // 캡션 & 메타데이터
val TextPrimary = TextMainDark
val TextSecondary = TextMuted
val TextTertiary = TextSubtle
val TextDisabled = Color(0xFF94A999)

// 4. 포인트 & 액센트 (모래 벙커/골드 티 & 핀 플래그 레드)
val EnergeticGold = Color(0xFFD97706)               // 활력 넘치는 골드 (드릴 & 코칭)
val EnergeticGoldLight = Color(0xFFFEF3C7)
val AccentGold = EnergeticGold
val EnergeticCoral = Color(0xFFDC2626)              // 핀 플래그 레드 (주의)

// 5. 직관적인 상태 태그
val StatusPass = Color(0xFF16A34A)
val StatusPassBg = Color(0xFFDCFCE7)
val StatusCaution = Color(0xFFD97706)
val StatusCautionBg = Color(0xFFFEF3C7)
val StatusRestricted = Color(0xFFDC2626)
val StatusRestrictedBg = Color(0xFFFEE2E2)

val TagMintBg = StatusPassBg
val TagMintText = StatusPass
val TagAmberBg = StatusCautionBg
val TagAmberText = StatusCaution
val TagRedBg = StatusRestrictedBg
val TagRedText = StatusRestricted
val TagBlueBg = Color(0xFFE0F2FE)
val TagBlueText = Color(0xFF0284C7)
