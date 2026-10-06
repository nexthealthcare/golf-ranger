package com.example.model

/**
 * 골퍼 프로필 및 라운드 데이터
 */
data class GolferProfile(
  val handicap: Int = 18,
  val strongestArea: String = "드라이버 티샷",
  val weakestArea: String = "아이언 정타율",
  val memorableMistake: String = "세컨샷 깊은 뒷땅으로 해저드",
  val shortTermGoal: String = "80대 후반 안정 진입 (라베 달성)",
  val longTermGoal: String = "보기플레이어 탈피 및 안정 싱글",
  val roundIssues: Set<String> = setOf(
    "드라이버 슬라이스 / 푸시",
    "아이언 뒷땅 / 탑핑이 잦음",
    "후반 홀 체력 저하와 샷 난조"
  ),
  val swingVideoUri: String? = null,
  val swingVideoFileName: String? = null,
  val selectedSwingHabit: String = "임팩트 시 골반 조기 전진 (얼리 익스텐션)"
)

/**
 * 골프 신체 기능 분석 유형 (Golf Body Performance Classification)
 * 전문 스포츠 퍼포먼스 랩 표준 분류 체계
 */
data class GolfBodyType(
  val code: String,              // 예: "TYPE-A (EE)", "TYPE-B (OTT)"
  val name: String,              // 예: "얼리 익스텐션 보상 유형"
  val categoryTitle: String,     // 예: "골반 틸트 & 족배굴곡 제한군"
  val summaryText: String,       // 핵심 요약
  val keyHabit: String,          // 주요 스윙 기전
  val bodyCause: String,         // 해부학적 신체 원인
  val performanceStrength: String, // 유지되는 장점
  val correctiveStrategy: String // 교정 핵심 전략
)

/**
 * 13가지 기능 해부학적 골프 신체 검진 항목
 */
data class ScreeningItem(
  val id: Int,
  val title: String,
  val englishSubtitle: String,
  val category: String,
  val description: String,
  val instructionSteps: List<String>,
  val voiceGuideText: String,
  val passCriteria: String,
  val swingImpact: String,
  val technicalId: String
)

/**
 * 신체 검진 판정 등급 (Clinical Assessment Grade)
 */
enum class ScreeningGrade(val label: String, val code: String) {
  PASS("정상 (Normal)", "PASS"),
  LIMITED("주의 (Borderline)", "LIMITED"),
  RESTRICTED("제한 (Restricted)", "RESTRICTED")
}

/**
 * 처방 모빌리티 운동 (Prescription Mobility Exercise)
 */
data class BodyExercise(
  val title: String,
  val targetArea: String,
  val repsOrTime: String,
  val leftRightDetail: String,
  val durationSeconds: Int,
  val instructions: List<String>,
  val coachingKey: String,
  val voiceCoachScript: String,
  val technicalId: String
)

/**
 * 처방 골프 실전 드릴 (Prescription Golf Drill)
 */
data class GolfDrill(
  val title: String,
  val recommendedClub: String,
  val setAndReps: String,
  val howToPractice: List<String>,
  val feelVsReal: String,
  val checkpoint: String,
  val voiceCoachScript: String,
  val technicalId: String
)

/**
 * 바디-스윙-게임 통합 분석 리포트 (Performance Diagnostic Report)
 */
data class AnalysisReport(
  val bodyType: GolfBodyType,
  val bodyContribution: Int,
  val swingContribution: Int,
  val gameContribution: Int,
  val primaryBodyLimitation: String,
  val primarySwingCompensation: String,
  val primaryGameMistake: String,
  val chainExplanation: String,
  val estimatedStrokesSaved: String,
  val exercises: List<BodyExercise>,
  val drills: List<GolfDrill>,
  val retestWeeks: Int = 4,
  val clinicalCoachingMessage: String
)
