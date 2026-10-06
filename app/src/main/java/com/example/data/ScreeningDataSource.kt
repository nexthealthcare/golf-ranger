package com.example.data

import com.example.model.ScreeningItem

object ScreeningDataSource {
  val screeningItems: List<ScreeningItem> = listOf(
    ScreeningItem(
      id = 1,
      title = "골반 전후방 틸트 가동성",
      englishSubtitle = "Pelvic Tilt Mobility",
      category = "요추-골반 제어",
      description = "어드레스 자세에서 상체와 무릎을 고정한 상태로 골반만을 전방 및 후방으로 독립 틸트할 수 있는지 측정합니다.",
      instructionSteps = listOf(
        "어드레스 자세를 취하고 양팔을 가슴에 교차해 얹습니다.",
        "상체와 무릎의 움직임 없이 허리를 아치형으로 젖힙니다 (골반 전방경사).",
        "다시 꼬리뼈를 말아 넣으며 복부를 수축합니다 (골반 후방경사)."
      ),
      voiceGuideText = "어드레스 자세에서 상체와 무릎을 고정하십시오. 골반만 앞뒤로 부드럽게 틸트합니다. 요추와 골반의 독립적인 조절력을 확인합니다.",
      passCriteria = "상체 흔들림 없이 골반만을 독립적으로 전후방으로 원활하게 틸트 가능",
      swingImpact = "부족 시 어드레스 C-포스처 유발 및 다운스윙 임팩트 구간 얼리 익스텐션(배치기)의 주요 원인이 됩니다.",
      technicalId = "PELVIC_TILT"
    ),
    ScreeningItem(
      id = 2,
      title = "골반 분리 회전력",
      englishSubtitle = "Pelvic Rotation Disassociation",
      category = "하체 회전 분리",
      description = "상체(어깨)를 완벽히 고정한 채 골반만을 좌우로 독립 회전할 수 있는지 점검합니다.",
      instructionSteps = listOf(
        "어드레스 자세를 취하고 어깨와 가슴을 정면에 고정합니다.",
        "어깨의 회전이나 기울임 없이 골반만 좌우로 회전합니다.",
        "양방향 모두 30도 이상 균형 있게 돌아가는지 확인합니다."
      ),
      voiceGuideText = "어깨를 정면에 고정하고 골반만 좌우로 회전하십시오. 다운스윙 하체 선행 시퀀스의 필수 가동성입니다.",
      passCriteria = "어깨의 회전 없이 골반만 좌우 30도 이상 독립 회전 가능",
      swingImpact = "하체 선행(Kinematic Sequence)이 제한되어 상체가 먼저 덤비는 오버 더 탑(Over-the-top) 슬라이스를 유발합니다.",
      technicalId = "PELVIC_ROTATION"
    ),
    ScreeningItem(
      id = 3,
      title = "흉추 상체 분리 회전력",
      englishSubtitle = "Torso Upper Body Disassociation",
      category = "흉추 회전 분리",
      description = "하체(골반)를 완전히 고정한 채 상체(흉추)만을 좌우로 독립 회전할 수 있는지 점검합니다.",
      instructionSteps = listOf(
        "어드레스 자세에서 양 발과 골반을 단단히 고정합니다.",
        "골반과 무릎이 회전하지 않도록 지지한 상태로 가슴과 어깨만 회전합니다.",
        "척추 각도가 흐트러지지 않는지 확인합니다."
      ),
      voiceGuideText = "골반을 고정한 상태로 상체만 좌우로 회전하십시오. 백스윙 시 안정적인 상체 코일링을 확보하는 기준입니다.",
      passCriteria = "골반과 무릎의 회전 없이 상체만 좌우 60도 이상 회전 가능",
      swingImpact = "백스윙 시 꼬임(X-Factor)이 부족하여 하체가 밀리는 스웨이(Sway) 또는 역피봇 보상이 발생합니다.",
      technicalId = "TORSO_ROTATION"
    ),
    ScreeningItem(
      id = 4,
      title = "오버헤드 딥 스쿼트",
      englishSubtitle = "Deep Overhead Squat",
      category = "전신 기능 가동성",
      description = "클럽을 머리 위로 수직 거치하고 뒤꿈치 들림 없이 대퇴부가 수평 이하로 내려가는지 평가합니다.",
      instructionSteps = listOf(
        "양발을 어깨너비로 벌리고 클럽을 머리 위로 수직으로 뻗습니다.",
        "발뒤꿈치가 지면에서 떨어지지 않도록 유지하며 깊게 쪼그려 앉습니다.",
        "팔이 앞으로 기울어지지 않고 상체 정렬이 유지되는지 점검합니다."
      ),
      voiceGuideText = "클럽을 머리 위로 유지하고 깊게 스쿼트합니다. 발뒤꿈치가 지면에 완전히 접지된 상태를 유지하십시오.",
      passCriteria = "뒤꿈치 들림 없이 양팔이 수직을 유지하며 대퇴부가 수평선 이하로 도달",
      swingImpact = "스윙 중 척추각을 유지하지 못하고 일어서며 임팩트 타점이 흔들려 뒷땅 및 탑핑을 유발합니다.",
      technicalId = "OVERHEAD_SQUAT"
    ),
    ScreeningItem(
      id = 5,
      title = "단일 하지 지탱 밸런스",
      englishSubtitle = "Single Leg Balance",
      category = "정적 밸런스 & 안정성",
      description = "한 발로 서서 흔들림 없이 15초 이상 자세를 유지할 수 있는지 좌우 각각 평가합니다.",
      instructionSteps = listOf(
        "한 발을 들고 양팔은 가슴에 모읍니다.",
        "지지발의 발목과 골반이 무너지지 않도록 중심을 유지합니다.",
        "좌측과 우측 각각 15초 이상 버틸 수 있는지 체크합니다."
      ),
      voiceGuideText = "한 발로 서서 15초간 안정성을 유지하십시오. 피니시 자세의 밸런스와 임팩트 타점 안정성을 확인합니다.",
      passCriteria = "지지발의 흔들림이나 골반 기울임 없이 좌우 모두 15초 이상 유지",
      swingImpact = "체중 이동 후 피니시에서 균형이 무너지며 임팩트 타점 일관성이 저하됩니다.",
      technicalId = "SINGLE_LEG_BALANCE"
    ),
    ScreeningItem(
      id = 6,
      title = "어깨 외회전 가동성",
      englishSubtitle = "Shoulder External Rotation",
      category = "견관절 가동성",
      description = "팔꿈치를 90도로 굴곡한 상태에서 전완이 지면 수직선 뒤로 넘어가는지 평가합니다.",
      instructionSteps = listOf(
        "팔꿈치를 어깨높이에서 90도로 유지합니다.",
        "척추의 보상 젖힘 없이 전완을 등 뒤쪽으로 회전합니다.",
        "양팔이 수직 기준(90도) 이상 뒤로 넘어가는지 측정합니다."
      ),
      voiceGuideText = "팔꿈치를 90도로 유지하고 손등을 뒤로 넘기십시오. 백스윙 탑에서 클럽 샤프트를 샬로윙하는 기초 가동성입니다.",
      passCriteria = "척추 보상 없이 전완이 지면 수직선 뒤로 90도 이상 가동",
      swingImpact = "다운스윙 시 클럽이 눕혀지지 못하고 가파르게 진입하여 엎어치기 궤도를 형성합니다.",
      technicalId = "SHOULDER_EXTERNAL"
    ),
    ScreeningItem(
      id = 7,
      title = "어깨 내회전 가동성",
      englishSubtitle = "Shoulder Internal Rotation",
      category = "견관절 가동성",
      description = "팔꿈치를 90도로 유지한 상태에서 손바닥을 아래 방향으로 60도 이상 회전할 수 있는지 점검합니다.",
      instructionSteps = listOf(
        "팔꿈치를 어깨높이에서 90도로 유지합니다.",
        "견갑골이 앞으로 솟지 않도록 고정한 채 손을 아래로 내립니다.",
        "지면 기준 60도 이상 회전 범위를 확인합니다."
      ),
      voiceGuideText = "팔꿈치를 고정하고 전완을 아래로 회전하십시오. 임팩트 이후 릴리스 및 팔로우스루의 대칭성을 평가합니다.",
      passCriteria = "견갑골 들림 없이 손이 아래 방향으로 60도 이상 회전 가능",
      swingImpact = "임팩트 후 왼팔이 당겨지는 치킨윙(Chicken Wing) 현상이 발생합니다.",
      technicalId = "SHOULDER_INTERNAL"
    ),
    ScreeningItem(
      id = 8,
      title = "후면 사슬 유연성 (발끝 닿기)",
      englishSubtitle = "Toe Touch Posterior Chain",
      category = "후면 사슬 & 햄스트링",
      description = "무릎을 완전히 편 상태로 상체를 굴곡하여 손가락 끝이 발끝에 접촉하는지 점검합니다.",
      instructionSteps = listOf(
        "양발을 모으고 무릎을 곧게 폅니다.",
        "무릎을 굽히지 않고 엉덩이를 뒤로 밀며 상체를 숙입니다.",
        "손끝이 발가락에 자연스럽게 닿는지 확인합니다."
      ),
      voiceGuideText = "무릎을 펴고 상체를 숙여 손끝을 발끝에 접촉하십시오. 고관절 힌지 셋업을 형성하는 기본 유연성입니다.",
      passCriteria = "무릎 굽힘 없이 손끝이 발가락에 통증 없이 접촉",
      swingImpact = "어드레스 시 고관절 힌지가 접히지 않아 등이 둥글게 굽는 C자세가 발생합니다.",
      technicalId = "TOE_TOUCH"
    ),
    ScreeningItem(
      id = 9,
      title = "착석 고관절 회전 가동성",
      englishSubtitle = "Seated Hip Rotation",
      category = "고관절 회전",
      description = "의자에 앉아 무릎을 고정한 채 발목을 안/밖으로 회전하여 고관절 회전 범위를 측정합니다.",
      instructionSteps = listOf(
        "의자에 앉아 양 무릎을 90도로 세웁니다.",
        "무릎 위치를 고정한 채 발목을 바깥쪽으로 보내 내회전(35도)을 측정합니다.",
        "발목을 안쪽으로 보내 외회전(45도)을 측정합니다."
      ),
      voiceGuideText = "의자에 앉아 무릎을 고정하고 발목을 회전하십시오. 백스윙과 다운스윙 시 고관절 회전 제한 여부를 평가합니다.",
      passCriteria = "골반 보상 없이 내회전 35도 이상, 외회전 45도 이상 확보",
      swingImpact = "임팩트 구간에서 좌측 골반의 회전이 차단(Block)되어 상체 푸시 샷이 발생합니다.",
      technicalId = "SEATED_HIP"
    ),
    ScreeningItem(
      id = 10,
      title = "척추 측면 굴곡 유연성",
      englishSubtitle = "Spine Lateral Flexion",
      category = "척추 측면 가동성",
      description = "골반을 고정한 채 순수하게 상체를 옆으로 기울여 손끝이 무릎 바깥선에 도달하는지 측정합니다.",
      instructionSteps = listOf(
        "바르게 선 상태에서 양손을 허벅지 외측에 둡니다.",
        "몸통이 앞이나 뒤로 회전하지 않도록 순수 측면으로만 기울입니다.",
        "손끝이 무릎 관절선 이하에 도달하는지 좌우 확인합니다."
      ),
      voiceGuideText = "몸통을 순수 측면으로 기울이십시오. 다운스윙 시 사이드 벤딩을 유지하는 능력입니다.",
      passCriteria = "상체 회전이나 골반 이동 없이 손끝이 무릎 외측선 도달",
      swingImpact = "임팩트 시 측면 척추 기울기를 유지하지 못해 배치기 및 쌩크가 발생합니다.",
      technicalId = "LATERAL_FLEXION"
    ),
    ScreeningItem(
      id = 11,
      title = "손목 힌지 및 코킹 가동성",
      englishSubtitle = "Wrist Hinge Mobility",
      category = "손목 관절 가동성",
      description = "전완을 고정한 상태로 손목을 코킹(상방) 및 힌지(후방)로 70도 이상 가동할 수 있는지 평가합니다.",
      instructionSteps = listOf(
        "팔을 앞으로 뻗어 전완을 수평으로 둡니다.",
        "손목만 위로 꺾는 코킹과 손등 쪽으로 젖히는 힌지를 시행합니다.",
        "70도 이상의 각도가 부드럽게 확보되는지 확인합니다."
      ),
      voiceGuideText = "손목의 코킹과 힌지 가동성을 확인합니다. 래깅 유지와 임팩트 압축력을 결정하는 지표입니다.",
      passCriteria = "팔꿈치 보상 없이 70도 이상의 코킹 및 힌지 가동성 확보",
      swingImpact = "캐스팅(Casting) 및 스쿠핑(Scooping)을 유발하여 볼을 띄우지 못하고 비거리가 감소합니다.",
      technicalId = "WRIST_MOBILITY"
    ),
    ScreeningItem(
      id = 12,
      title = "발목 족배굴곡 벽 검사",
      englishSubtitle = "Ankle Dorsiflexion Wall Test",
      category = "하지 관절 접지력",
      description = "벽에서 10cm 거리에 엄지발가락을 두고 발뒤꿈치 들림 없이 무릎이 벽에 접촉하는지 평가합니다.",
      instructionSteps = listOf(
        "벽 앞에 서서 엄지발가락을 벽에서 약 10cm 거리에 둡니다.",
        "발뒤꿈치가 바닥에서 뜨지 않도록 견고하게 접지합니다.",
        "무릎을 앞으로 굴곡하여 벽에 접촉할 수 있는지 측정합니다."
      ),
      voiceGuideText = "발뒤꿈치를 바닥에 고정하고 무릎을 벽으로 굴곡하십시오. 지면 반력 활용과 얼리 익스텐션 방지의 핵심 지표입니다.",
      passCriteria = "발뒤꿈치 들림 없이 무릎이 벽에 자연스럽게 접촉",
      swingImpact = "다운스윙 시 발목 가동성 결핍으로 골반이 전방으로 튀어나오는 얼리 익스텐션의 핵심 원인입니다.",
      technicalId = "ANKLE_MOBILITY"
    ),
    ScreeningItem(
      id = 13,
      title = "단일 하지 브릿지 코어 안정성",
      englishSubtitle = "Single Leg Glute Bridge",
      category = "둔근 & 골반 안정성",
      description = "앙와위 브릿지 자세에서 한 다리를 전방으로 신전하고 골반 수평을 10초간 유지할 수 있는지 평가합니다.",
      instructionSteps = listOf(
        "바닥에 등을 대고 누워 무릎을 세우고 골반을 들어올립니다.",
        "한쪽 다리를 무릎 높이와 수평이 되게 전방으로 뻗습니다.",
        "지지측 골반이 아래로 처지지 않고 10초간 수평을 유지하는지 측정합니다."
      ),
      voiceGuideText = "골반을 들어올린 상태에서 한 다리를 뻗어 10초간 수평을 유지하십시오. 둔근의 정적 지지력을 확인합니다.",
      passCriteria = "골반의 회전이나 하강 없이 둔근 힘으로 10초간 수평 유지",
      swingImpact = "임팩트 구간 하체 지지력이 붕괴되어 스웨이/슬라이드가 발생하고 비거리가 누수됩니다.",
      technicalId = "GLUTE_BRIDGE"
    )
  )
}
