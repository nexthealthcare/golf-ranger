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

    // 골프 바디 MBTI
    val mbti = when {
      hasSquatIssue || hasAnkleIssue || hasPelvicTiltIssue -> GolfMbtiType(
        code = "BSE-T",
        name = "배치기 타이거형",
        animalEmoji = "🐯",
        tagline = "마음은 싱글! 임팩트는 벌떡 일어나는 파워 골퍼",
        keyHabit = "임팩트 순간 골반이 앞으로 돌진 (얼리 익스텐션)",
        bodyCause = "발목 접지력과 골반 틸트가 굳어 척추각 유지 불가",
        superpower = "강한 상체 힘과 폭발적인 장타 본능",
        quickFix = "엉덩이 뒤 3cm 스틱 터치 드릴"
      )

      hasTorsoRotationIssue || hasPelvicRotationIssue -> GolfMbtiType(
        code = "OSD-D",
        name = "엎어치기 드래곤형",
        animalEmoji = "🐲",
        tagline = "상체로 덤비는 열정! 슬라이스 바람을 가르는 전사",
        keyHabit = "백스윙 꼬임 부족으로 상체가 먼저 덮어침 (오버 더 탑)",
        bodyCause = "흉추 상체 독립 회전 가동성 잠김",
        superpower = "정확한 컨택을 노리는 높은 집중력",
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

    // 처방 운동 (상세 좌우 횟수 & 세트 & 음성 코칭 스크립트 탑재)
    val exerciseList = mutableListOf<BodyExercise>()
    if (hasAnkleIssue || hasSquatIssue || mbti.code == "BSE-T") {
      exerciseList.add(
        BodyExercise(
          title = "벽 발목 족배굴곡 스트레칭",
          targetArea = "발목 관절 & 아킬레스건",
          repsOrTime = "좌우 각 15회씩 (총 3세트)",
          leftRightDetail = "왼발 15회 실시 후 오른발 15회 교대 (3세트)",
          durationSeconds = 60,
          instructions = listOf("벽에서 10cm 발을 떼고 뒤꿈치를 밀착합니다.", "무릎을 벽 쪽으로 지긋이 밀어 3초간 멈춥니다."),
          coachingKey = "배치기(얼리 익스텐션)를 근본적으로 막아주는 발목 접지력",
          voiceCoachScript = "준비! 벽을 짚고 발뒤꿈치를 바닥에 꽉 붙이십시오. 무릎을 벽으로 밀어줍니다. 하나, 둘, 셋! 뒤꿈치 뜨면 안 됩니다. 교대하여 반대 발도 똑같이 진행하십시오!",
          visualType = "ANKLE_MOBILITY"
        )
      )
      exerciseList.add(
        BodyExercise(
          title = "어드레스 골반 틸트 꼬리뼈 운동",
          targetArea = "골반 전후방 기울기 제어",
          repsOrTime = "앞뒤 20회 반복 (총 3세트)",
          leftRightDetail = "전방경사 20회 + 후방경사 20회 (3세트)",
          durationSeconds = 60,
          instructions = listOf("어드레스 셋업을 잡고 가슴을 완전히 고정합니다.", "골반 꼬리뼈만 말아 올리고 내리며 척추각을 느낍니다."),
          coachingKey = "임팩트 순간 척추각 보존 필수 제어력",
          voiceCoachScript = "가슴은 고정! 어드레스 자세에서 꼬리뼈만 뒤로 젖혔다가, 아랫배를 말아 넣으십시오. 상체가 흔들리면 무효입니다. 골반만 독립적으로 움직입니다!",
          visualType = "PELVIC_TILT"
        )
      )
    }

    if (hasTorsoRotationIssue || mbti.code == "OSD-D") {
      exerciseList.add(
        BodyExercise(
          title = "오픈북 흉추 가동성 트위스트",
          targetArea = "흉추 상체 회전 & 광배근",
          repsOrTime = "좌우 각 12회씩 (총 3세트)",
          leftRightDetail = "왼쪽 회전 12회 후 오른쪽 12회 (3세트)",
          durationSeconds = 60,
          instructions = listOf("옆으로 누워 양 무릎을 90도로 모아 골반을 잠급니다.", "팔을 크게 원을 그리며 반대편 바닥으로 가슴을 엽니다."),
          coachingKey = "엎어치기 방지 백스윙 코일링 극대화",
          voiceCoachScript = "무릎이 바닥에서 뜨지 않도록 꽉 누르고, 위쪽 팔을 활짝 펴서 반대편 바닥으로 넘깁니다. 시선은 손끝을 따라가며 가슴을 활짝 여십시오!",
          visualType = "TORSO_ROTATION"
        )
      )
    }

    if (exerciseList.size < 3) {
      exerciseList.add(
        BodyExercise(
          title = "싱글 레그 둔근 브릿지 버티기",
          targetArea = "대둔근 & 코어 밸런스",
          repsOrTime = "좌우 각 10회씩 (총 3세트)",
          leftRightDetail = "왼발 지탱 10회 후 오른발 10회 (3세트)",
          durationSeconds = 60,
          instructions = listOf("누워서 엉덩이를 들고 한 다리를 앞으로 곧게 뻗습니다.", "골반이 좌우로 처지지 않게 엉덩이 힘으로 5초 유지합니다."),
          coachingKey = "후반 18홀까지 하체 타점이 흔들리지 않는 힘",
          voiceCoachScript = "골반을 번쩍 들어올리고 한 발을 앞으로 뻗습니다! 골반이 처지면 안 됩니다. 둔근에 힘을 꽉 주고 5초간 버티십시오. 반대편도 교대합니다!",
          visualType = "MOBILITY"
        )
      )
    }

    // 골프 드릴 (세트 및 음성 코칭 포함)
    val drillList = mutableListOf<GolfDrill>()
    drillList.add(
      GolfDrill(
        title = mbti.quickFix,
        recommendedClub = "7번 아이언",
        setAndReps = "10회 빈스윙 + 10구 타격 (총 3세트)",
        howToPractice = listOf(
          "엉덩이 뒤 3cm에 골프백이나 스틱을 세워둡니다.",
          "임팩트 순간 왼쪽 엉덩이가 스틱에 계속 닿아있는지 체크하며 70% 스윙합니다."
        ),
        feelVsReal = "Feel: 엉덩이를 뒤로 쑥 빼는 느낌 / Real: 척추각 완벽 보존",
        checkpoint = "공 앞쪽에 디봇이 깔끔하게 생기는지 확인",
        voiceCoachScript = "셋업 잡으시고 엉덩이 뒤를 확인하십시오! 백스윙 탑, 그리고 다운스윙 임팩트까지 엉덩이가 스틱에서 떨어지면 안 됩니다. 배치기 금지! 가볍게 휘두르십시오!",
        visualType = "DRILL_BUTT"
      )
    )

    drillList.add(
      GolfDrill(
        title = "티 1cm 스치기 최저점 정타 드릴",
        recommendedClub = "피칭 웨지",
        setAndReps = "티 스치기 15회 + 실제 타격 15구 (총 2세트)",
        howToPractice = listOf(
          "고무티를 1cm 높이로 두고 클럽헤드로 티 윗부분만 경쾌하게 스칩니다.",
          "손목을 퍼올리지 않고 몸통 회전으로 쓸어칩니다."
        ),
        feelVsReal = "Feel: 낮고 길게 지나가는 감각 / Real: 깔끔한 정타",
        checkpoint = "두꺼운 뒷땅 없이 산뜻한 '착' 소리",
        voiceCoachScript = "공 없이 티만 꽂습니다! 손목으로 퍼올리지 말고 몸통 회전으로 티의 윗부분 5미리미터만 경쾌하게 스쳐 지나가십시오. 착 소리가 나야 합격입니다!",
        visualType = "DRILL_TEE"
      )
    )

    val estimatedSavings = when {
      profile.handicap >= 25 -> "-5 ~ -7타 절감"
      profile.handicap >= 18 -> "-3 ~ -5타 절감"
      else -> "-2 ~ -3타 절감"
    }

    val coachingMsg = "골프조교의 가설 진단: ${mbti.animalEmoji} ${mbti.name} 성향입니다! " +
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
      exercises = exerciseList.take(4),
      drills = drillList,
      retestWeeks = 4,
      coachingMessage = coachingMsg
    )
  }
}
