package com.example.data

import com.example.model.AnalysisReport
import com.example.model.BodyExercise
import com.example.model.GolfDrill
import com.example.model.GolfBodyType
import com.example.model.GolferProfile
import com.example.model.ScreeningGrade

/**
 * SFMA (Selective Functional Movement Assessment) 5대 핵심 검진 기반의 바디-스윙 인과관계 분석 엔진
 * 1. FLEXION: 후방 사슬 유연성 & 힙 힌지 -> 얼리 익스텐션(배치기), 척추 C-포스처, 뒷땅
 * 2. EXTENSION: 전방 사슬 & 흉추 신전 -> 척추각 상실(Loss of Posture), 역피봇, 치킨윙
 * 3. ROTATION: 몸통 & 골반 회전 코일링 -> 오버 더 탑(OTT 엎어치기), 슬라이스, 스웨이
 * 4. SLS: 단일 하지 지탱력 & 중둔근 안정성 -> 슬라이드, 축 붕괴, 후반 라운드 난조
 * 5. SQUAT: 고관절·발목·흉추 복합 가동성 -> 얼리 익스텐션, 캐스팅, 지면반력 부실
 */
object AnalysisEngine {

  fun analyze(
    profile: GolferProfile,
    screeningResults: Map<Int, ScreeningGrade>
  ): AnalysisReport {
    val failedTests = screeningResults.filter { it.value != ScreeningGrade.PASS }
    val severeTests = screeningResults.filter { it.value == ScreeningGrade.RESTRICTED }

    // SFMA 5항목 가중치 계산
    val bodyRawScore = (failedTests.size * 22) + (severeTests.size * 18)
    var swingRawScore = 30
    if (profile.roundIssues.any { it.contains("슬라이스") || it.contains("뒷땅") || it.contains("탑핑") }) {
      swingRawScore += 25
    }
    var gameRawScore = 20
    if (profile.roundIssues.any { it.contains("후반") || it.contains("필드") || it.contains("퍼팅") }) {
      gameRawScore += 20
    }

    val totalRaw = (bodyRawScore + swingRawScore + gameRawScore).coerceAtLeast(100).toFloat()
    val bodyPct = ((bodyRawScore / totalRaw) * 100).toInt().coerceIn(25, 55)
    val gamePct = ((gameRawScore / totalRaw) * 100).toInt().coerceIn(15, 30)
    val swingPct = 100 - bodyPct - gamePct

    // SFMA 5대 검진 결과 판정
    val hasFlexionIssue = screeningResults[1] != ScreeningGrade.PASS      // SFMA Flexion
    val hasExtensionIssue = screeningResults[2] != ScreeningGrade.PASS    // SFMA Extension
    val hasRotationIssue = screeningResults[3] != ScreeningGrade.PASS     // SFMA Rotation
    val hasSLSIssue = screeningResults[4] != ScreeningGrade.PASS          // SFMA Single Leg Stance
    val hasSquatIssue = screeningResults[5] != ScreeningGrade.PASS        // SFMA Overhead Squat

    val hasEEHabit = profile.selectedSwingHabits.any { it.contains("얼리 익스텐션") || it.contains("배치기") }
    val hasOTTHabit = profile.selectedSwingHabits.any { it.contains("오버 더 탑") || it.contains("덮어") }
    val hasCWHabit = profile.selectedSwingHabits.any { it.contains("치킨윙") }
    val hasCastHabit = profile.selectedSwingHabits.any { it.contains("캐스팅") || it.contains("스쿠핑") }

    // SFMA 기반 신체 기능 및 스윙 보상 유형 분류
    val bodyType = when {
      // 1. Flexion 또는 Squat 제한 & 얼리 익스텐션 보상군
      (hasSquatIssue || hasFlexionIssue || hasEEHabit) && (hasEEHabit || hasSquatIssue || hasFlexionIssue) -> GolfBodyType(
        code = "TYPE-A (EE)",
        name = "얼리 익스텐션 보상 유형",
        categoryTitle = "SFMA 굴곡(Flexion) & 스쿼트(Squat) 제한군",
        summaryText = "SFMA 전신 굴곡 및 스쿼트 제한으로 인해 고관절 힌지 공간이 부족하여, 다운스윙 시 골반이 공 쪽으로 전진하며 일어서는 패턴입니다.",
        keyHabit = "임팩트 시 골반 조기 전진 (Early Extension / 배치기)",
        bodyCause = "후방 사슬(햄스트링·둔근) 유연성 결핍 및 발목·고관절 복합 가동성 제한",
        performanceStrength = "상체 파워 전달 및 본능적인 볼 컨택 집중력",
        correctiveStrategy = "힙 힌지 굴곡 가동성 회복 및 척추각 유지 드릴"
      )

      // 2. Rotation 제한 & 오버 더 탑(엎어치기) 보상군
      hasRotationIssue || hasOTTHabit -> GolfBodyType(
        code = "TYPE-B (OTT)",
        name = "오버 더 탑 궤도 보상 유형",
        categoryTitle = "SFMA 전신 회전(Rotation) 가동성 결핍군",
        summaryText = "SFMA 회전 검진에서 흉추와 골반의 코일링 가동성이 잠겨 있어, 다운스윙 전환 시 상체가 선행하여 엎어 들어오는 패턴입니다.",
        keyHabit = "다운스윙 아웃-인 엎어치기 궤도 (Over-the-Top)",
        bodyCause = "몸통 및 흉추 50도 이상 독립 회전 가동성 결핍",
        performanceStrength = "과감하고 공격적인 헤드 스피드 형성",
        correctiveStrategy = "흉추 오픈북 회전 모빌리티 확장 및 하체 선행 시퀀스 드릴"
      )

      // 3. Extension 제한 & 치킨윙 / 역피봇 보상군
      hasExtensionIssue || hasCWHabit -> GolfBodyType(
        code = "TYPE-C (CW)",
        name = "척추각 상실 및 치킨윙 보상 유형",
        categoryTitle = "SFMA 전신 신전(Extension) 결핍군",
        summaryText = "SFMA 신전 검진에서 척추 후방 신전 및 견갑대 가동성이 부족하여, 백스윙 역피봇 및 팔로우스루 좌측 팔꿈치 당김이 일어납니다.",
        keyHabit = "다운스윙 척추각 붕괴 및 팔로우스루 치킨윙 (Chicken Wing)",
        bodyCause = "흉추 및 고관절 신전 가동 범위 제한 & 전방 사슬 긴장",
        performanceStrength = "정교한 손목 감각과 숏게임 터치",
        correctiveStrategy = "전방 사슬 스트레칭 및 흉추 신전 가동성 트레이닝"
      )

      // 4. 캐스팅 / 스쿠핑 보상군
      hasCastHabit -> GolfBodyType(
        code = "TYPE-D (SCP)",
        name = "스쿠핑 캐스팅 보상 유형",
        categoryTitle = "SFMA 힙 힌지 부족에 따른 손목 조기 풀림군",
        summaryText = "다운스윙 전환 구간에서 공간 부족을 보상하기 위해 손목 코킹이 일찍 풀리며 볼을 퍼올리는 패턴입니다.",
        keyHabit = "임팩트 전 손목 조기 풀림 (Casting / Scooping)",
        bodyCause = "SFMA 굴곡 가동성 부족으로 인한 타점 보상 동작",
        performanceStrength = "부드러운 쇼트게임 로브 터치 감각",
        correctiveStrategy = "힙 힌지 깊은 공간 확보 및 볼 5cm 앞 디봇 드릴"
      )

      // 5. SLS 제한 & 후반 밸런스 붕괴군
      else -> GolfBodyType(
        code = "TYPE-E (STA)",
        name = "편측 밸런스 & 후반 감쇄 유형",
        categoryTitle = "SFMA 외발서기(SLS) 단일 하지 지지력 취약군",
        summaryText = "SFMA 외발서기 검진에서 편측 둔근 안정성이 저하되어, 다운스윙 시 좌측 벽을 만들지 못하고 옆으로 밀리는 스웨이/슬라이드가 발생합니다.",
        keyHabit = "다운스윙 체중 이동 슬라이드 및 후반 중심축 흔들림",
        bodyCause = "SFMA SLS 단일 하지 지지력 및 중둔근 정적 안정성 저하",
        performanceStrength = "전반 나인홀 안정적인 코스 매니지먼트",
        correctiveStrategy = "싱글 레그 밸런스 강화 및 지면 반력 접지 훈련"
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

    val chainExplanation = "1. SFMA 신체 원인: ${primaryBody}\n" +
        "2. 스윙 보상 기전: ${primarySwing} 발생\n" +
        "3. 실전 필드 결과: ${primaryGame} 유발"

    // SFMA 맞춤 전문 처방 운동 세트
    val exerciseList = mutableListOf<BodyExercise>()

    if (hasFlexionIssue || hasSquatIssue || bodyType.code.startsWith("TYPE-A")) {
      exerciseList.add(
        BodyExercise(
          title = "SFMA 힙 힌지 & 햄스트링 굴곡 모빌리티",
          targetArea = "후방 사슬 (햄스트링·둔근)",
          repsOrTime = "좌우 각 15회 (3세트)",
          leftRightDetail = "양발 모으고 15회 + 편측 힌지 15회",
          durationSeconds = 60,
          instructions = listOf(
            "양발을 모으고 무릎을 곧게 편 상태에서 엉덩이를 뒤로 밀며 상체를 숙입니다.",
            "등을 둥글리지 않고 햄스트링의 신장을 느끼며 3초간 머무른 후 일어섭니다."
          ),
          coachingKey = "어드레스 C-포스처와 얼리 익스텐션을 방지하는 힙 힌지 공간 확보",
          voiceCoachScript = "준비하십시오. 양발을 모으고 무릎을 폅니다. 엉덩이를 뒤로 밀어내며 상체를 깊게 숙입니다. 하나, 둘, 셋. 햄스트링이 늘어나는 자극에 집중하십시오. 천천히 일어납니다.",
          technicalId = "SFMA_FLEXION_EXERCISE"
        )
      )
      exerciseList.add(
        BodyExercise(
          title = "오버헤드 스쿼트 힌지 & 발목 접지력",
          targetArea = "발목 족배굴곡 & 대퇴사두·둔근",
          repsOrTime = "12회씩 3세트",
          leftRightDetail = "뒤꿈치 지면 밀착 3초 정지",
          durationSeconds = 60,
          instructions = listOf(
            "클럽을 머리 위로 들고 뒤꿈치가 뜨지 않게 엉덩이를 무릎 아래로 깊게 앉습니다.",
            "가슴을 열고 3초간 머무른 뒤 발바닥 지면을 힘차게 밀며 일어섭니다."
          ),
          coachingKey = "다운스윙 임팩트 시 지면 반력을 형성하는 필수 하체 접지력",
          voiceCoachScript = "클럽을 머리 위로 높이 드십시오. 뒤꿈치를 바닥에 견고히 붙인 채 깊게 앉습니다. 척추각을 유지하며 3초 버티고, 지면을 박차며 일어섭니다.",
          technicalId = "SFMA_SQUAT_EXERCISE"
        )
      )
    }

    if (hasRotationIssue || bodyType.code.startsWith("TYPE-B")) {
      exerciseList.add(
        BodyExercise(
          title = "오픈북 흉추 가동성 트위스트",
          targetArea = "흉추 상체 회전 & 광배근",
          repsOrTime = "좌우 각 12회 (3세트)",
          leftRightDetail = "좌측 회전 12회 후 우측 12회 교대",
          durationSeconds = 60,
          instructions = listOf(
            "옆으로 누운 자세에서 양 무릎을 90도로 모아 골반을 바닥에 고정합니다.",
            "상완을 반대편 바닥으로 부드럽게 넘기며 흉추의 50도 이상 독립 회전을 유도합니다."
          ),
          coachingKey = "오버 더 탑을 예방하는 백스윙 상체 코일링 가동 범위 확보",
          voiceCoachScript = "무릎이 바닥에서 떨어지지 않도록 고정하십시오. 팔을 원을 그리듯 반대편 바닥으로 넘기며 시선은 손끝을 주시합니다. 가슴과 흉추를 충분히 확장하십시오.",
          technicalId = "SFMA_ROTATION_EXERCISE"
        )
      )
    }

    if (hasExtensionIssue || bodyType.code.startsWith("TYPE-C")) {
      exerciseList.add(
        BodyExercise(
          title = "벽 흉추 신전 & 광배근 릴리스",
          targetArea = "흉추 신전 & 대흉근·전방 사슬",
          repsOrTime = "15회 (3세트)",
          leftRightDetail = "신전 상태 5초 유지",
          durationSeconds = 60,
          instructions = listOf(
            "벽에 등을 대고 서서 양팔을 머리 위로 올려 벽에 밀착합니다.",
            "골반을 살짝 앞으로 밀며 가슴을 들어 올려 흉추 신전을 유도합니다."
          ),
          coachingKey = "역피봇과 척추각 붕괴를 방지하는 흉추 정상 아치 회복",
          voiceCoachScript = "가슴을 활짝 펴고 벽에 팔을 올리십시오. 요추가 꺾이지 않고 흉추에서 부드러운 신전이 일어나도록 가슴을 전방 위쪽으로 들어 올립니다.",
          technicalId = "SFMA_EXTENSION_EXERCISE"
        )
      )
    }

    if (hasSLSIssue || bodyType.code.startsWith("TYPE-E") || exerciseList.size < 3) {
      exerciseList.add(
        BodyExercise(
          title = "SFMA 싱글 레그 밸런스 & 둔근 지탱",
          targetArea = "중둔근 & 편측 밸런스 축",
          repsOrTime = "좌우 각 30초 유지 (3세트)",
          leftRightDetail = "좌측 지탱 30초 후 우측 30초 교대",
          durationSeconds = 60,
          instructions = listOf(
            "한 발로 서서 반대쪽 무릎을 90도로 들고 골반의 수평을 유지합니다.",
            "지탱하는 발의 엉덩이 측면에 힘을 주어 상체가 흔들리지 않게 버팁니다."
          ),
          coachingKey = "다운스윙 임팩트 벽을 구축하고 슬라이드를 방지하는 단일 하지 지지력",
          voiceCoachScript = "한 발로 바르게 서십시오. 지탱하는 발의 엉덩이에 단단히 힘을 주고 시선은 정면을 주시합니다. 흔들림 없이 30초간 균형을 유지합니다.",
          technicalId = "SFMA_SLS_EXERCISE"
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
        title = "헤드 커버 끼우기 궤도 인사이드-아웃 드릴",
        recommendedClub = "6번/7번 아이언",
        setAndReps = "빈스윙 10회 + 타격 15구 (총 2세트)",
        howToPractice = listOf(
          "볼 우측 상단 10cm 지점에 드라이버 헤드커버를 놓습니다.",
          "헤드커버를 건드리지 않고 인사이드에서 어프로치하여 인-아웃 궤도를 형성합니다."
        ),
        feelVsReal = "Feel: 오른쪽 1시 방향으로 밀어치는 느낌 / Real: 정석 드로우 궤도",
        checkpoint = "헤드커버를 치지 않고 깨끗하게 공만 컨택",
        voiceCoachScript = "볼 바깥쪽에 놓인 헤드커버를 주의하십시오. 상체로 덤비면 커버를 치게 됩니다. 하체 회전으로 인사이드 길을 열어주며 스윙하십시오.",
        technicalId = "DRILL_OTT"
      )
    )

    val estimatedSavings = when {
      profile.handicap >= 25 -> "-5 ~ -7타"
      profile.handicap >= 18 -> "-3 ~ -5타"
      else -> "-2 ~ -3타"
    }

    val coachingMsg = "SFMA 5대 검진 분석 결과: ${bodyType.code} ${bodyType.name} 패턴이 확인되었습니다. " +
        "복잡한 스윙 테크닉 교정보다 먼저 ${bodyType.bodyCause}를 회복해야 스윙 보상이 자연스럽게 사라집니다. " +
        "처방된 모빌리티 세트와 드릴을 주 4회 실천하십시오."

    val mbti = when (bodyType.code) {
      "TYPE-A (EE)" -> com.example.model.GolfMbti(
        code = "BSE-T",
        name = "배치기 타이거",
        animalEmoji = "🐯",
        tagline = "폭발적인 파워, 그러나 임팩트 때 먼저 일어서는 호랑이!",
        superpower = "강력한 상체 파워와 공격적인 비거리 잠재력",
        bodyCause = "SFMA 굴곡(Flexion) 및 스쿼트 힙 힌지 공간 결핍",
        quickFix = "다운스윙 때 엉덩이를 벽에 붙이고 1초 유지하기"
      )
      "TYPE-B (OTT)" -> com.example.model.GolfMbti(
        code = "OSD-D",
        name = "엎어치기 드래곤",
        animalEmoji = "🐲",
        tagline = "넘치는 비거리 욕심에 상체가 덤벼드는 용!",
        superpower = "공을 향한 저돌적인 파워와 날카로운 스윙 스피드",
        bodyCause = "SFMA 회전(Rotation) 가동성 결핍 및 상체 코일링 부족",
        quickFix = "백스윙 탑에서 등을 타깃으로 향한 채 하체 먼저 출발하기"
      )
      "TYPE-C (CW)" -> com.example.model.GolfMbti(
        code = "CWR-E",
        name = "치킨윙 이글",
        animalEmoji = "🦅",
        tagline = "날갯짓은 화려하지만 팔꿈치가 당겨지는 독수리!",
        superpower = "빠른 클럽헤드 스피드와 정교한 손목 감각",
        bodyCause = "SFMA 신전(Extension) 결핍으로 인한 척추각 붕괴",
        quickFix = "임팩트 통과 후 양 팔꿈치를 모으고 악수하듯 뻗어주기"
      )
      "TYPE-D (SCP)" -> com.example.model.GolfMbti(
        code = "SCP-B",
        name = "스쿠핑 베어",
        animalEmoji = "🐻",
        tagline = "곰처럼 묵직하게 공을 띄우려 퍼올리는 곰!",
        superpower = "부드러운 쇼트게임 터치 감각과 로브샷 감각",
        bodyCause = "SFMA 굴곡 부족에 따른 공간 부재로 손목 조기 풀림",
        quickFix = "왼손등이 타깃을 향하게 유지하며 볼 5cm 앞 디봇 내기"
      )
      else -> com.example.model.GolfMbti(
        code = "STF-L",
        name = "방전 레오파드",
        animalEmoji = "🐆",
        tagline = "전반엔 맹수처럼 질주하다 후반에 지치는 표범!",
        superpower = "초반 홀 폭발적인 집중력과 완벽한 파 세이브",
        bodyCause = "SFMA 외발서기(SLS) 단일 하지 지탱력 및 중둔근 피로",
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
