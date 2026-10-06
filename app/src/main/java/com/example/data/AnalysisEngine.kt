package com.example.data

import com.example.model.AnalysisReport
import com.example.model.BodyExercise
import com.example.model.GolfDrill
import com.example.model.GolfBodyType
import com.example.model.GolferProfile
import com.example.model.ScreeningGrade

object AnalysisEngine {

  fun analyze(
    profile: GolferProfile,
    screeningResults: Map<Int, ScreeningGrade>
  ): AnalysisReport {
    val failedTests = screeningResults.filter { it.value != ScreeningGrade.PASS }
    val severeTests = screeningResults.filter { it.value == ScreeningGrade.RESTRICTED }

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

    val hasPelvicTiltIssue = screeningResults[1] != ScreeningGrade.PASS
    val hasPelvicRotationIssue = screeningResults[2] != ScreeningGrade.PASS
    val hasTorsoRotationIssue = screeningResults[3] != ScreeningGrade.PASS
    val hasSquatIssue = screeningResults[4] != ScreeningGrade.PASS
    val hasBalanceIssue = screeningResults[5] != ScreeningGrade.PASS
    val hasShoulderIssue = screeningResults[6] != ScreeningGrade.PASS || screeningResults[7] != ScreeningGrade.PASS
    val hasWristIssue = screeningResults[11] != ScreeningGrade.PASS
    val hasAnkleIssue = screeningResults[12] != ScreeningGrade.PASS

    val hasEEHabit = profile.selectedSwingHabits.any { it.contains("얼리 익스텐션") || it.contains("배치기") }
    val hasOTTHabit = profile.selectedSwingHabits.any { it.contains("오버 더 탑") || it.contains("덮어") }
    val hasCWHabit = profile.selectedSwingHabits.any { it.contains("치킨윙") }
    val hasCastHabit = profile.selectedSwingHabits.any { it.contains("캐스팅") || it.contains("스쿠핑") }

    // 전문 스포츠 퍼포먼스 체형/스윙 분류
    val bodyType = when {
      (hasSquatIssue || hasAnkleIssue || hasPelvicTiltIssue || hasEEHabit) && (hasEEHabit || hasAnkleIssue || hasSquatIssue) -> GolfBodyType(
        code = "TYPE-A (EE)",
        name = "얼리 익스텐션 보상 유형",
        categoryTitle = "골반 틸트 & 발목 족배굴곡 제한군",
        summaryText = "다운스윙 임팩트 구간에서 척추각을 유지하지 못하고 골반이 공 방향으로 조기 전진하는 패턴입니다.",
        keyHabit = "임팩트 시 골반 조기 전진 (Early Extension)",
        bodyCause = "발목 족배굴곡 가동성 저하 및 요추-골반 척추각 분리 조절력 결핍",
        performanceStrength = "상체 파워 전달 및 임팩트 직관력",
        correctiveStrategy = "고관절 힌지 접지 유지 및 힙 턴 공간 확보 드릴"
      )

      hasTorsoRotationIssue || hasPelvicRotationIssue || hasOTTHabit -> GolfBodyType(
        code = "TYPE-B (OTT)",
        name = "오버 더 탑 궤도 보상 유형",
        categoryTitle = "흉추 상체 독립 회전 결핍군",
        summaryText = "백스윙 시 상체 코일링 회전량이 부족하여 다운스윙 전환 시 상체가 선행하여 엎어 들어오는 패턴입니다.",
        keyHabit = "다운스윙 궤도 이탈 (Over-the-Top)",
        bodyCause = "흉추 상체 독립 회전 가동성 잠김 및 견갑대 안정성 부족",
        performanceStrength = "정확한 컨택을 노리는 높은 집중력",
        correctiveStrategy = "흉추 모빌리티 확장 및 하체 선행 시퀀스 드릴"
      )

      hasShoulderIssue || hasCWHabit -> GolfBodyType(
        code = "TYPE-C (CW)",
        name = "치킨윙 릴리스 보상 유형",
        categoryTitle = "견관절 회전근개 가동성 제한군",
        summaryText = "임팩트 통과 후 팔로우스루 구간에서 견관절 내회전 제한으로 인해 좌측 팔꿈치가 외측으로 당겨지는 패턴입니다.",
        keyHabit = "팔로우스루 좌측 주관절 굴곡 (Chicken Wing)",
        bodyCause = "어깨 내회전 및 견갑상완 관절 가동 범위 제한",
        performanceStrength = "빠른 클럽헤드 스피드 형성",
        correctiveStrategy = "견관절 회전 가동성 회복 및 대칭 릴리스 드릴"
      )

      hasWristIssue || hasCastHabit -> GolfBodyType(
        code = "TYPE-D (SCP)",
        name = "스쿠핑 캐스팅 보상 유형",
        categoryTitle = "수근관절 힌지 및 코킹 제어 결핍군",
        summaryText = "다운스윙 초기 단계에서 손목 코킹이 조기 해제되어 클럽헤드로 볼을 퍼올리려 하는 패턴입니다.",
        keyHabit = "임팩트 전 손목 조기 풀림 (Casting / Scooping)",
        bodyCause = "손목 힌지 가동성 및 코킹 정적 유지력 부족",
        performanceStrength = "섬세한 쇼트게임 터치 감각",
        correctiveStrategy = "최저점 지연 타격 및 래깅 유지 드릴"
      )

      else -> GolfBodyType(
        code = "TYPE-E (STA)",
        name = "후반 스태미나 밸런스 감쇄 유형",
        categoryTitle = "하지 밸런스 및 둔근 피로 취약군",
        summaryText = "라운드 후반 나인홀로 갈수록 하지 지지력과 둔근 파워가 저하되어 타점이 분산되는 패턴입니다.",
        keyHabit = "후반 체중 이동 지연 및 밸런스 붕괴",
        bodyCause = "단일 하지 지탱 밸런스 및 코어 둔근 정적 유지력 저하",
        performanceStrength = "초반 홀 안정적인 코스 매니지먼트",
        correctiveStrategy = "단일 하지 지지력 강화 및 둔근 지구력 세트"
      )
    }

    val primaryBody = bodyType.bodyCause
    val primarySwing = bodyType.keyHabit
    val primaryGame = when {
      profile.roundIssues.any { it.contains("뒷땅") || it.contains("탑핑") } -> "미들/롱아이언 타점 불안정 (뒷땅·탑핑)"
      profile.roundIssues.any { it.contains("슬라이스") } -> "드라이버 티샷 시 우측 푸시 슬라이스"
      profile.roundIssues.any { it.contains("후반") } -> "후반 나인홀 스코어 급격한 분산"
      else -> "스코어링 존에서의 방향성 오차 확대"
    }

    val chainExplanation = "1. 신체 원인: ${primaryBody}\n" +
        "2. 스윙 보상: 다운스윙 전환 시 ${primarySwing} 유발\n" +
        "3. 실전 결과: 필드에서 ${primaryGame} 발생"

    // 전문 처방 운동 세트
    val exerciseList = mutableListOf<BodyExercise>()
    if (hasAnkleIssue || hasSquatIssue || bodyType.code.startsWith("TYPE-A")) {
      exerciseList.add(
        BodyExercise(
          title = "벽 발목 족배굴곡 모빌리티",
          targetArea = "발목 관절 & 아킬레스건",
          repsOrTime = "좌우 각 15회 (3세트)",
          leftRightDetail = "좌측 15회 실시 후 우측 15회 교대",
          durationSeconds = 60,
          instructions = listOf(
            "벽에서 10cm 거리에 앞발을 두고 뒤꿈치를 지면에 완전히 밀착합니다.",
            "뒤꿈치 들림 없이 무릎을 벽 방향으로 전방 굴곡하여 3초간 유지합니다."
          ),
          coachingKey = "얼리 익스텐션을 방지하는 발목 지면 반력 접지력 강화",
          voiceCoachScript = "준비하십시오. 벽 앞에 서서 뒤꿈치를 바닥에 견고하게 밀착합니다. 무릎을 천천히 벽 방향으로 굴곡하십시오. 하나, 둘, 셋. 뒤꿈치가 뜨면 안 됩니다. 교대하여 반대측도 동일하게 수행합니다.",
          technicalId = "ANKLE_MOBILITY"
        )
      )
      exerciseList.add(
        BodyExercise(
          title = "어드레스 골반 전후방 틸트 제어",
          targetArea = "요추-골반 분리 조절력",
          repsOrTime = "전후 각 20회 (3세트)",
          leftRightDetail = "전방경사 20회 + 후방경사 20회",
          durationSeconds = 60,
          instructions = listOf(
            "어드레스 셋업을 취하고 상체와 무릎을 정면에 단단히 고정합니다.",
            "골반만을 전방 및 후방으로 부드럽게 틸트하며 척추 중립을 인지합니다."
          ),
          coachingKey = "임팩트 구간 척추각을 유지하기 위한 골반 독자 가동성",
          voiceCoachScript = "어드레스 셋업을 취하십시오. 상체는 고정합니다. 골반 꼬리뼈만을 뒤로 젖혔다가 복부를 수축하며 말아 넣으십시오. 상체가 흔들리지 않도록 골반의 독립적 움직임에 집중합니다.",
          technicalId = "PELVIC_TILT"
        )
      )
    }

    if (hasTorsoRotationIssue || bodyType.code.startsWith("TYPE-B")) {
      exerciseList.add(
        BodyExercise(
          title = "오픈북 흉추 가동성 트위스트",
          targetArea = "흉추 상체 회전 & 광배근",
          repsOrTime = "좌우 각 12회 (3세트)",
          leftRightDetail = "좌측 회전 12회 후 우측 12회 교대",
          durationSeconds = 60,
          instructions = listOf(
            "측와위(옆으로 누운 자세)에서 양 무릎을 90도로 모아 골반을 고정합니다.",
            "상완을 반대편 바닥으로 부드럽게 넘기며 흉추의 회전을 유도합니다."
          ),
          coachingKey = "오버 더 탑을 예방하는 백스윙 상체 코일링 가동 범위 확보",
          voiceCoachScript = "무릎이 바닥에서 떨어지지 않도록 고정하십시오. 팔을 원을 그리듯 반대편 바닥으로 넘기며 시선은 손끝을 주시합니다. 가슴과 흉추를 충분히 확장하십시오.",
          technicalId = "TORSO_ROTATION"
        )
      )
    }

    if (exerciseList.size < 3) {
      exerciseList.add(
        BodyExercise(
          title = "단일 하지 둔근 브릿지 지지",
          targetArea = "대둔근 & 골반 안정성",
          repsOrTime = "좌우 각 10회 (3세트)",
          leftRightDetail = "좌측 지탱 10회 후 우측 10회 교대",
          durationSeconds = 60,
          instructions = listOf(
            "앙와위(누운 자세)에서 골반을 들어올리고 한쪽 다리를 전방으로 신전합니다.",
            "골반이 수평을 유지하도록 지지측 둔근을 수축하여 5초간 유지합니다."
          ),
          coachingKey = "후반 18홀까지 하체 타점을 유지하는 골반 지지력",
          voiceCoachScript = "골반을 지면에서 들어올린 후 한쪽 다리를 전방으로 곧게 뻗으십시오. 골반의 좌우 수평이 무너지지 않도록 둔근을 견고하게 수축하십시오. 5초간 유지합니다.",
          technicalId = "GLUTE_BRIDGE"
        )
      )
    }

    // 전문 처방 골프 드릴
    val drillList = mutableListOf<GolfDrill>()
    drillList.add(
      GolfDrill(
        title = "얼라인먼트 힙 터치 척추각 고정 드릴",
        recommendedClub = "7번 아이언",
        setAndReps = "빈스윙 10회 + 실타격 10구 (총 3세트)",
        howToPractice = listOf(
          "어드레스 후 엉덩이 뒤 3cm 거리에 얼라인먼트 스틱을 수직 거치합니다.",
          "임팩트 순간 좌측 엉덩이가 스틱과의 접촉을 유지하도록 70% 템포로 스윙합니다."
        ),
        feelVsReal = "Feel: 엉덩이를 과도하게 뒤로 유지 / Real: 척추 각도 완벽 보존",
        checkpoint = "임팩트 직후 볼 앞쪽으로 얇고 일정한 디봇 형성 확인",
        voiceCoachScript = "셋업을 취하고 엉덩이 뒤 얼라인먼트 스틱을 확인하십시오. 백스윙부터 임팩트 통과까지 좌측 엉덩이가 스틱에서 떨어지지 않아야 합니다. 척추각을 고정한 상태로 스윙하십시오.",
        technicalId = "DRILL_BUTT"
      )
    )

    drillList.add(
      GolfDrill(
        title = "티 1cm 스치기 최저점 정타 드릴",
        recommendedClub = "피칭 웨지",
        setAndReps = "티 스치기 15회 + 볼 타격 15구 (총 2세트)",
        howToPractice = listOf(
          "고무티를 1cm 높이로 설정하고 클럽헤드로 티 상단 5mm만을 스치고 지나갑니다.",
          "손목으로 걷어 올리지 않고 몸통 턴으로 일정한 최저점을 형성합니다."
        ),
        feelVsReal = "Feel: 볼을 낮게 쓸고 지나가는 감각 / Real: 정타 압축 임팩트",
        checkpoint = "두꺼운 뒷땅 없이 경쾌한 헤드 스침 음향 확인",
        voiceCoachScript = "공 없이 티의 상단만을 겨냥하십시오. 손목으로 퍼올리는 보상을 억제하고 몸통 회전으로 티의 윗부분을 스치고 지나가십시오. 일정한 타점 높이를 유지합니다.",
        technicalId = "DRILL_TEE"
      )
    )

    val estimatedSavings = when {
      profile.handicap >= 25 -> "-5 ~ -7타"
      profile.handicap >= 18 -> "-3 ~ -5타"
      else -> "-2 ~ -3타"
    }

    val coachingMsg = "분석 결과: ${bodyType.code} ${bodyType.name} 패턴이 확인되었습니다. " +
        "단순한 스윙 폼 교정 이전에 ${bodyType.bodyCause}를 회복해야 스윙 보상이 자연스럽게 해소됩니다. " +
        "처방된 모빌리티 세트와 드릴을 주 4회 실천하십시오."

    val mbti = when (bodyType.code) {
      "TYPE-A (EE)" -> com.example.model.GolfMbti(
        code = "BSE-T",
        name = "배치기 타이거",
        animalEmoji = "🐯",
        tagline = "폭발적인 파워, 그러나 임팩트 때 먼저 일어서는 호랑이!",
        superpower = "강력한 상체 파워와 공격적인 비거리 잠재력",
        bodyCause = "골반 전후방 틸트 분리 조절력 결핍 & 발목 가동성 부족",
        quickFix = "다운스윙 때 엉덩이를 벽에 붙이고 1초 유지하기"
      )
      "TYPE-B (OTT)" -> com.example.model.GolfMbti(
        code = "OSD-D",
        name = "엎어치기 드래곤",
        animalEmoji = "🐲",
        tagline = "넘치는 비거리 욕심에 상체가 덤벼드는 용!",
        superpower = "공을 향한 저돌적인 파워와 날카로운 스윙 스피드",
        bodyCause = "흉추 상체 독립 회전 가동성 잠김 및 견갑대 안정성 부족",
        quickFix = "백스윙 탑에서 등을 타깃으로 향한 채 하체 먼저 출발하기"
      )
      "TYPE-C (CW)" -> com.example.model.GolfMbti(
        code = "CWR-E",
        name = "치킨윙 이글",
        animalEmoji = "🦅",
        tagline = "날갯짓은 화려하지만 팔꿈치가 당겨지는 독수리!",
        superpower = "빠른 클럽헤드 스피드와 정교한 손목 감각",
        bodyCause = "어깨 내회전 및 견갑상완 관절 가동 범위 제한",
        quickFix = "임팩트 통과 후 양 팔꿈치를 모으고 악수하듯 뻗어주기"
      )
      "TYPE-D (SCP)" -> com.example.model.GolfMbti(
        code = "SCP-B",
        name = "스쿠핑 베어",
        animalEmoji = "🐻",
        tagline = "곰처럼 묵직하게 공을 띄우려 퍼올리는 곰!",
        superpower = "부드러운 쇼트게임 터치 감각과 로브샷 감각",
        bodyCause = "손목 힌지 가동성 및 코킹 정적 유지력 부족",
        quickFix = "왼손등이 타깃을 향하게 유지하며 볼 5cm 앞 디봇 내기"
      )
      else -> com.example.model.GolfMbti(
        code = "STF-L",
        name = "방전 레오파드",
        animalEmoji = "🐆",
        tagline = "전반엔 맹수처럼 질주하다 후반에 지치는 표범!",
        superpower = "초반 홀 폭발적인 집중력과 완벽한 파 세이브",
        bodyCause = "단일 하지 지탱 밸런스 및 둔근 피로 회복력 저하",
        quickFix = "루틴 때 둔근에 힘주고 티샷 전 심호흡 2번 하기"
      )
    }

    return AnalysisReport(
      mbti = mbti,
      bodyType = bodyType,
      bodyContribution = bodyPct,
      swingContribution = swingPct,
      gameContribution = gamePct,
      primaryBodyLimitation = primaryBody,
      primarySwingCompensation = primarySwing,
      primaryGameMistake = primaryGame,
      chainExplanation = chainExplanation,
      estimatedStrokesSaved = estimatedSavings,
      exercises = exerciseList.take(4),
      drills = drillList,
      retestWeeks = 4,
      clinicalCoachingMessage = coachingMsg
    )
  }
}
