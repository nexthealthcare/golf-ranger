package com.example.model

/**
 * 골퍼의 기본 정보 및 라운드 문제점 설문 데이터
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
  val selectedSwingHabit: String = "임팩트 시 골반이 앞으로 밀림 (배치기)"
)

/**
 * 재미있고 직관적인 골프 바디 MBTI 유형
 */
data class GolfMbtiType(
  val code: String,
  val name: String,
  val animalEmoji: String,
  val tagline: String,
  val keyHabit: String,
  val bodyCause: String,
  val superpower: String,
  val quickFix: String
)

/**
 * 13가지 골프 기능성 신체 검진 항목
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
  val previewIconType: String,
  val quickTip: String = ""
)

/**
 * 신체 검진 평가 결과
 */
enum class ScreeningGrade(val label: String, val emoji: String) {
  PASS("정상 통과", "🟢"),
  LIMITED("제한됨 (주의)", "🟡"),
  RESTRICTED("불가 / 심함", "🔴")
}

/**
 * 처방 운동 (상세 세트/반복 & 좌우 횟수 & 음성 코칭)
 */
data class BodyExercise(
  val title: String,
  val targetArea: String,
  val repsOrTime: String,
  val leftRightDetail: String, // 예: "좌측 15회 / 우측 15회 (총 3세트)"
  val durationSeconds: Int,
  val instructions: List<String>,
  val coachingKey: String,
  val voiceCoachScript: String, // 조교의 실전 음성 코칭 대사
  val visualType: String = "MOBILITY"
)

/**
 * 골프 연습 드릴 (일러스트 & 음성 가이드 포함)
 */
data class GolfDrill(
  val title: String,
  val recommendedClub: String,
  val setAndReps: String, // 예: "10회 스윙씩 3세트 (총 30구)"
  val howToPractice: List<String>,
  val feelVsReal: String,
  val checkpoint: String,
  val voiceCoachScript: String,
  val visualType: String = "DRILL_BUTT"
)

/**
 * AI Body-Swing-Game 연결 분석 결과
 */
data class AnalysisReport(
  val mbti: GolfMbtiType,
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
  val coachingMessage: String
)
