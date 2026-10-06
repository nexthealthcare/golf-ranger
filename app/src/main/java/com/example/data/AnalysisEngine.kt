package com.example.data

import com.example.model.AnalysisReport
import com.example.model.BodyExercise
import com.example.model.GolfDrill
import com.example.model.GolfMbtiType
import com.example.model.GolferProfile
import com.example.model.ScreeningGrade

object AnalysisEngine {

  fun analyze(
    profile: GolferProfile,
    screeningResults: Map<Int, ScreeningGrade>
  ): AnalysisReport {
    val failedTests = screeningResults.filter { it.value != ScreeningGrade.PASS }
    val severeTests = screeningResults.filter { it.value == ScreeningGrade.RESTRICTED }

    // 가중치 산출 (신체 vs 기술 vs 코스/게임)
    val bodyRawScore = (failedTests.size * 10) + (severeTests.size * 15)
    var swingRawScore = 30
    if (profile.roundIssues.any { it.contains("슬라이스") || it.contains("뒷땅") || it.contains("탑핑") }) {
      swingRawScore += 25
    }
    var gameRawScore = 20
    if (profile.roundIssues.any { it.contains("후반") || it.contains("필드") || it.contains("퍼팅") }) {
      gameRawScore += 20
    }

    val totalRaw = (bodyRawScore + swingRawScore + gameRawScore).coerceAtLeast(100).toFloat()
    var bodyPct = ((bodyRawScore / totalRaw) * 100).toInt().coerceIn(20, 50)
    var gamePct = ((gameRawScore / totalRaw) * 100).toInt().coerceIn(15, 35)
    var swingPct = 100 - bodyPct - gamePct

    // 신체 제한 체크
    val hasPelvicTiltIssue = screeningResults[1] != ScreeningGrade.PASS
    val hasPelvicRotationIssue = screeningResults[2] != ScreeningGrade.PASS
    val hasTorsoRotationIssue = screeningResults[3] != ScreeningGrade.PASS
    val hasSquatIssue = screeningResults[4] != ScreeningGrade.PASS
    val hasBalanceIssue = screeningResults[5] != ScreeningGrade.PASS
    val hasShoulderIssue = screeningResults[6] != ScreeningGrade.PASS || screeningResults[7] != ScreeningGrade.PASS
    val hasHamstringIssue = screeningResults[8] != ScreeningGrade.PASS
    val hasWristIssue = screeningResults[11] != ScreeningGrade.PASS
    val hasAnkleIssue = screeningResults[12] != ScreeningGrade.PASS
    val hasCoreIssue = screeningResults[13] != ScreeningGrade.PASS

    // 재미있는 골프 바디 MBTI 매핑
    val mbti = when {
      hasSquatIssue || hasAnkleIssue || hasPelvicTiltIssue -> GolfMbtiType(
        code = "BSE-T",
        name = "배치기 타이거형",
        animalEmoji = "🐯",
        tagline = "마음은 싱글! 임팩트는 벌떡 일어나는 파워 골퍼",
        keyHabit = "임팩트 순간 골반이 앞으로 돌진 (얼리 익스텐션)",
        bodyCause = "발목과 골반이 굳어 척추각을 유지하지 못함",
        superpower = "강한 상체 힘과 폭발적인 장타 본능",
        quickFix = "엉덩이 뒤 3cm 벽 터치 1초 정지 드릴"
      )

      hasTorsoRotationIssue || hasPelvicRotationIssue -> GolfMbtiType(
        code = "OSD-D",
        name = "엎어치기 드래곤형",
        animalEmoji = "🐲",
        tagline = "팔로 덤비는 열정! 슬라이스 바람을 가르는 전사",
        keyHabit = "백스윙 꼬임 부족으로 상체가 먼저 덮어침 (오버 더 탑)",
        bodyCause = "흉추 상체 독립 회전 잠김",
        superpower = "정확한 임팩트 맞추기 집중력",
        quickFix = "오픈북 흉추 가동성 & 스텝 다운스윙"
      )

      hasShoulderIssue -> GolfMbtiType(
        code = "CWL-E",
        name = "치킨윙 이글형",
        animalEmoji = "🦅",
        tagline = "임팩트 후 왼팔 날개를 번쩍 드는 감각파",
        keyHabit = "팔로우스루 때 왼팔꿈치가 닭날개처럼 벌어짐",
        bodyCause = "어깨 내회전 및 회전근개 가동성 제한",
        superpower = "빠른 헤드 스피드와 헤드 던지기 감각",
        quickFix = "수건 겨드랑이 끼우고 하프스윙 릴리스"
      )

      hasWristIssue -> GolfMbtiType(
        code = "SCP-F",
        name = "스쿠핑 플라밍고형",
        animalEmoji = "🦩",
        tagline = "공을 띄우려 손목으로 퍼올리는 감성파",
        keyHabit = "임팩트 직전 손목이 일찍 풀려 탑핑/뒷땅 유발",
        bodyCause = "손목 힌지 가동성 및 코킹 조절력 부족",
        superpower = "부드러운 손목 스냅과 쇼트게임 터치",
        quickFix = "고무티 1cm 스치기 최저점 정타 드릴"
      )

      else -> GolfMbtiType(
        code = "BBP-B",
        name = "후반방전 베어형",
        animalEmoji = "🐻",
        tagline = "전반엔 라베 페이스! 14홀부터 체력 방전",
        keyHabit = "후반 갈수록 하체 중심이 흔들리며 타수 분산",
        bodyCause = "하지 밸런스 및 둔근 코어 지속력 부족",
        superpower = "초반 9홀 안정적인 코스 매니지먼트",
        quickFix = "싱글 레그 데드리프트 & 둔근 브릿지"
      )
    }

    val primaryBody = mbti.bodyCause
    val primarySwing = mbti.keyHabit
    val primaryGame = when {
      profile.roundIssues.any { it.contains("뒷땅") || it.contains("탑핑") } -> "아이언 뒷땅 & 탑핑"
      profile.roundIssues.any { it.contains("슬라이스") } -> "드라이버 우측 푸시 슬라이스"
      profile.roundIssues.any { it.contains("후반") } -> "후반 나인홀 타수 붕괴"
      else -> "스코어링 구간 정타율 저하"
    }

    val chainExplanation = "【${mbti.animalEmoji} ${mbti.name} 사이클】\n" +
        "1. 신체: $primaryBody\n" +
        "2. 스윙: 보상으로 $primarySwing\n" +
        "3. 게임: 실전에서 $primaryGame 발생!"

    // 처방 운동 (3~4가지, 짧고 굵은 설명)
    val exerciseList = mutableListOf<BodyExercise>()
    if (hasAnkleIssue || hasSquatIssue || mbti.code == "BSE-T") {
      exerciseList.add(
        BodyExercise(
          title = "발목 벽 밀기 스트레칭",
          targetArea = "발목 관절 & 접지력",
          repsOrTime = "좌우 15회 (3세트)",
          durationSeconds = 60,
          instructions = listOf("뒤꿈치 붙이고 무릎을 벽으로 밉니다.", "배치기를 막는 핵심 발목 브레이크를 풉니다."),
          coachingKey = "배치기 방지 1순위 운동"
        )
      )
      exerciseList.add(
        BodyExercise(
          title = "골반 틸트 꼬리뼈 운동",
          targetArea = "골반 전후방 제어",
          repsOrTime = "20회 반복 (3세트)",
          durationSeconds = 60,
          instructions = listOf("어드레스 자세에서 꼬리뼈만 앞뒤로 움직입니다.", "상체는 고정하고 골반만 부드럽게 움직입니다."),
          coachingKey = "척추각 보존 필수"
        )
      )
    }

    if (hasTorsoRotationIssue || mbti.code == "OSD-D") {
      exerciseList.add(
        BodyExercise(
          title = "오픈북 흉추 활짝 열기",
          targetArea = "흉추 상체 회전",
          repsOrTime = "좌우 12회 (3세트)",
          durationSeconds = 60,
          instructions = listOf("옆으로 누워 팔을 활짝 반대편 바닥으로 넘깁니다.", "골반은 고정하고 가슴만 엽니다."),
          coachingKey = "엎어치기 방지 코일링"
        )
      )
    }

    if (hasShoulderIssue || mbti.code == "CWL-E") {
      exerciseList.add(
        BodyExercise(
          title = "벽 슬라이드 Y-W 운동",
          targetArea = "어깨 가동성 & 견갑골",
          repsOrTime = "15회 (3세트)",
          durationSeconds = 60,
          instructions = listOf("벽에 등과 팔을 붙이고 W에서 Y로 올립니다.", "견갑골을 조이며 천천히 내립니다."),
          coachingKey = "치킨윙 탈출 샬로윙"
        )
      )
    }

    if (exerciseList.size < 3) {
      exerciseList.add(
        BodyExercise(
          title = "한발 서기 둔근 브릿지",
          targetArea = "둔근 & 코어 밸런스",
          repsOrTime = "좌우 10회 (3세트)",
          durationSeconds = 60,
          instructions = listOf("누워서 엉덩이를 들고 한 다리를 뻗어 버팁니다.", "골반이 처지지 않게 엉덩이에 힘을 줍니다."),
          coachingKey = "후반 18홀 타점 유지"
        )
      )
    }

    val finalExercises = exerciseList.take(4)

    // 골프 드릴 (2~3가지)
    val drillList = mutableListOf<GolfDrill>()
    drillList.add(
      GolfDrill(
        title = mbti.quickFix,
        recommendedClub = "7번 아이언",
        howToPractice = listOf(
          "엉덩이 뒤에 백을 두고 임팩트 때 엉덩이가 닿아있는지 체크!",
          "몸을 벌떡 세우지 않고 척추각을 지키며 스윙합니다."
        ),
        feelVsReal = "Feel: 엉덩이를 뒤로 쑥 빼는 느낌 / Real: 정타 작렬!",
        checkpoint = "공 앞쪽에 디봇이 깔끔하게 생기는지 확인"
      )
    )

    drillList.add(
      GolfDrill(
        title = "티 1cm 스치기 최저점 정타 드릴",
        recommendedClub = "피칭 웨지",
        howToPractice = listOf(
          "고무티를 1cm 높이로 두고 클럽헤드로 티 윗부분만 경쾌하게 스칩니다.",
          "손목을 퍼올리지 않고 몸통 회전으로 쓸어칩니다."
        ),
        feelVsReal = "Feel: 낮고 길게 지나가는 감각",
        checkpoint = "두꺼운 뒷땅 없이 산뜻한 '착' 소리"
      )
    )

    val estimatedSavings = when {
      profile.handicap >= 25 -> "-5 ~ -7타 절감"
      profile.handicap >= 18 -> "-3 ~ -5타 절감"
      else -> "-2 ~ -3타 절감"
    }

    val coachingMsg = "골프조교의 진단: ${mbti.animalEmoji} ${mbti.name} 성향입니다! " +
        "손기술을 의심하지 마세요. ${mbti.quickFix} 하나만 2주간 실천해도 다음 라운드에서 3타 이상 즉시 줄어듭니다."

    return AnalysisReport(
      mbti = mbti,
      bodyContribution = bodyPct,
      swingContribution = swingPct,
      gameContribution = gamePct,
      primaryBodyLimitation = primaryBody,
      primarySwingCompensation = primarySwing,
      primaryGameMistake = primaryGame,
      chainExplanation = chainExplanation,
      estimatedStrokesSaved = estimatedSavings,
      exercises = finalExercises,
      drills = drillList,
      retestWeeks = 4,
      coachingMessage = coachingMsg
    )
  }
}
