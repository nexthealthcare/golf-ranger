package com.example.data

import com.example.model.ScreeningItem

object ScreeningDataSource {
  val screeningItems: List<ScreeningItem> = listOf(
    ScreeningItem(
      id = 1,
      title = "골반 전후방 기울임 조절력",
      englishSubtitle = "Pelvic Tilt Mobility",
      category = "골반 & 척추 제어",
      description = "어드레스 자세에서 상체를 고정하고 골반만을 앞뒤로 젖힐 수 있는지 확인합니다.",
      instructionSteps = listOf(
        "어드레스 셋업 자세를 취하고 양손을 가슴에 얹습니다.",
        "상체와 무릎을 고정한 채 허리를 오목하게 젖히며 꼬리뼈를 뒤로 듭니다 (골반 전방경사).",
        "다시 아랫배를 조이며 꼬리뼈를 말아 넣습니다 (골반 후방경사)."
      ),
      voiceGuideText = "준비되셨습니까! 어드레스 자세를 잡고 가슴을 완전히 고정하십시오. 골반만 앞뒤로 부드럽게 틸트해 봅니다. 상체가 함께 흔들리면 안 됩니다.",
      passCriteria = "상체와 무릎의 흔들림 없이 골반만을 독립적으로 부드럽게 앞뒤로 기울일 수 있음",
      swingImpact = "부족할 경우 어드레스 C-자세 또는 S-자세 유발, 다운스윙 시 얼리 익스텐션(배치기)과 허리 통증의 1차 원인",
      previewIconType = "PELVIC_TILT"
    ),
    ScreeningItem(
      id = 2,
      title = "골반 분리 회전 검사",
      englishSubtitle = "Pelvic Rotation Disassociation",
      category = "골반 & 하체 리드",
      description = "상체(어깨)를 완전히 정면에 고정한 채 골반만을 좌우로 독립 회전할 수 있는지 점검합니다.",
      instructionSteps = listOf(
        "5번 아이언 어드레스 자세를 취하고 양팔을 교차해 어깨에 얹습니다.",
        "어깨와 머리를 정면에 고정하고 골반만 좌우로 회전합니다.",
        "반대편 방향도 매끄럽게 회전되는지 확인합니다."
      ),
      voiceGuideText = "주목! 어깨는 정면입니다. 상체를 움직이지 말고 골반만 좌우로 회전하십시오. 다운스윙 하체 리드의 기본 능력입니다.",
      passCriteria = "어깨의 회전이나 흔들림 없이 골반만 좌우 30도 이상 독립 회전 가능",
      swingImpact = "하체 리드가 불가능하여 상체가 먼저 덤비는 엎어치기(Over the top) 및 슬라이스 발생",
      previewIconType = "PELVIC_ROTATION"
    ),
    ScreeningItem(
      id = 3,
      title = "흉추 상체 분리 회전 검사",
      englishSubtitle = "Torso Upper Body Disassociation",
      category = "흉추 & 상체 코일링",
      description = "하체(골반)를 완전히 고정한 채 상체만을 좌우로 독립 회전할 수 있는지 점검합니다.",
      instructionSteps = listOf(
        "어드레스 자세에서 발과 골반을 단단히 고정합니다.",
        "골반과 무릎이 전혀 돌아가지 않도록 지지한 상태로 상체만 우측/좌측으로 틉니다.",
        "회전 중 척추 각도가 흐트러지지 않는지 확인합니다."
      ),
      voiceGuideText = "하체를 땅에 단단히 고정하십시오! 골반이 돌아가지 않게 묶어두고 가슴과 어깨만 좌우로 60도 이상 회전하십시오.",
      passCriteria = "골반과 무릎의 회전 없이 상체만 좌우 60도 이상 편안하게 회전 가능",
      swingImpact = "백스윙 시 꼬임(X-Factor)이 생기지 않아 하체가 밀리는 스웨이(Sway) 또는 역피봇 유발",
      previewIconType = "TORSO_ROTATION"
    ),
    ScreeningItem(
      id = 4,
      title = "딥 오버헤드 스쿼트",
      englishSubtitle = "Deep Overhead Squat",
      category = "전신 가동성 & 밸런스",
      description = "클럽을 머리 위로 들고 발뒤꿈치가 바닥에서 뜨지 않게 깊게 앉을 수 있는지 평가합니다.",
      instructionSteps = listOf(
        "양발을 어깨너비로 벌리고 골프 클럽을 양손으로 넓게 잡아 머리 위로 곧게 뻗습니다.",
        "발뒤꿈치를 바닥에 완전히 붙인 채 엉덩이를 무릎 높이 아래까지 천천히 내립니다.",
        "팔이 앞으로 기울어지지 않고 상체가 바른 자세를 유지하는지 봅니다."
      ),
      voiceGuideText = "클럽을 머리 위로 곧게 뻗고 깊게 앉습니다. 발뒤꿈치가 절대 뜨면 안 됩니다! 가슴을 펴고 수직을 유지하십시오.",
      passCriteria = "발뒤꿈치가 뜨지 않고 양팔이 지면과 수직을 유지하며 대퇴부가 수평 이하로 깊게 내려감",
      swingImpact = "스윙 중 척추 각도를 유지하지 못하고 일어서며 일어나는 치명적 뒷땅/탑핑의 핵심 원인",
      previewIconType = "OVERHEAD_SQUAT"
    ),
    ScreeningItem(
      id = 5,
      title = "단일 하지 지탱 밸런스",
      englishSubtitle = "Single Leg Balance",
      category = "하체 안정성 & 피니시",
      description = "한 발로 서서 15초 동안 흔들림 없이 안정적으로 버틸 수 있는지 좌우 각각 검사합니다.",
      instructionSteps = listOf(
        "한 발을 바닥에서 90도 각도로 들어올리고 양팔은 가슴에 모읍니다.",
        "지탱하는 발의 발목과 골반이 무너지지 않도록 중심을 잡습니다.",
        "좌측과 우측 각각 15초 이상 유지할 수 있는지 체크합니다."
      ),
      voiceGuideText = "한 발로 섭니다! 골반이 처지거나 발목이 흔들리면 안 됩니다. 15초간 피니시 자세의 안정성을 증명하십시오.",
      passCriteria = "좌우 다리 모두 지지발의 흔들림이나 상체 기울어짐 없이 15초 이상 유지",
      swingImpact = "피니시에서 몸이 뒤로 무너지거나 임팩트 순간 타점이 흔들려 정타율이 급격히 떨어짐",
      previewIconType = "SINGLE_LEG_BALANCE"
    ),
    ScreeningItem(
      id = 6,
      title = "어깨 외회전 가동성",
      englishSubtitle = "Shoulder External Rotation",
      category = "어깨 & 샬로윙",
      description = "팔꿈치를 어깨높이로 들고 직각 상태에서 손등을 등 뒤쪽으로 젖힐 수 있는 각도를 봅니다.",
      instructionSteps = listOf(
        "팔꿈치를 어깨높이와 수평이 되게 90도로 들어올립니다.",
        "상체나 허리를 뒤로 꺾지 않은 상태에서 전완을 뒤로 넘깁니다.",
        "양팔이 지면 수직선(90도) 이상 뒤로 넘어가는지 확인합니다."
      ),
      voiceGuideText = "팔꿈치를 90도로 꺾어 들고 손등을 뒤로 넘기십시오. 백스윙 탑에서 클럽 페이스를 열고 샬로윙 궤도를 만드는 기초입니다.",
      passCriteria = "허리의 보상 동작 없이 전완이 지면 수직(90도) 이상 뒤로 부드럽게 넘어감",
      swingImpact = "다운스윙 시 클럽을 수직으로 떨어뜨리지 못해 가파른 다운스윙과 엎어치기 슬라이스 유발",
      previewIconType = "SHOULDER_EXTERNAL"
    ),
    ScreeningItem(
      id = 7,
      title = "어깨 내회전 가동성",
      englishSubtitle = "Shoulder Internal Rotation",
      category = "어깨 & 팔로우스루",
      description = "팔꿈치를 어깨높이에서 유지하며 손바닥을 아래쪽/등 뒤로 회전시킬 수 있는 범위(60도 이상)를 검사합니다.",
      instructionSteps = listOf(
        "팔꿈치를 어깨높이로 90도 유지합니다.",
        "어깨가 앞으로 말려나오지 않도록 견갑골을 고정한 상태로 손을 아래로 내립니다.",
        "지면과 60도 이상 각도를 형성할 수 있는지 확인합니다."
      ),
      voiceGuideText = "팔꿈치를 고정하고 손바닥을 아래 방향으로 회전하십시오. 임팩트 후 클럽 릴리스와 치킨윙 방지에 직결됩니다.",
      passCriteria = "견갑골이 과도하게 솟지 않고 60도 이상 회전 가능",
      swingImpact = "임팩트 후 왼팔이 당겨지는 치킨윙(Chicken Wing) 현상과 푸시/슬라이스 발생",
      previewIconType = "SHOULDER_INTERNAL"
    ),
    ScreeningItem(
      id = 8,
      title = "후면 사슬 유연성 (발끝 닿기)",
      englishSubtitle = "Toe Touch Posterior Chain",
      category = "햄스트링 & 고관절 힌지",
      description = "무릎을 완전히 편 상태에서 상체를 숙여 손끝이 발가락에 닿는지 검사합니다.",
      instructionSteps = listOf(
        "양발을 모으고 무릎을 곧게 폅니다.",
        "무릎을 굽히지 않고 엉덩이를 뒤로 살짝 밀며 상체를 숙입니다.",
        "손끝이 발가락 끝에 편안하게 닿는지 확인합니다."
      ),
      voiceGuideText = "무릎을 완전히 펴고 상체를 숙여 발가락을 터치하십시오. 고관절 힌지 셋업을 만들기 위한 필수 조건입니다.",
      passCriteria = "무릎을 전혀 굽히지 않고 손끝이 발가락 끝에 가볍게 닿음",
      swingImpact = "어드레스 시 고관절 힌지가 접히지 않아 허리가 둥글게 말리는 C자세 유발, 비거리 손실",
      previewIconType = "TOE_TOUCH"
    ),
    ScreeningItem(
      id = 9,
      title = "착석 고관절 회전 가동성",
      englishSubtitle = "Seated Hip Internal/External Rotation",
      category = "고관절 턴 & 힙턴",
      description = "의자에 앉아 허벅지를 고정한 채 발목을 안/밖으로 회전시킬 수 있는 범위를 검사합니다.",
      instructionSteps = listOf(
        "의자에 바르게 앉아 양 무릎을 90도로 세웁니다.",
        "한쪽 무릎의 위치를 고정하고 발목을 바깥으로 보내 엉덩이 내회전(35도)을 체크합니다.",
        "발목을 안쪽으로 보내 엉덩이 외회전(45도)을 체크합니다."
      ),
      voiceGuideText = "의자에 앉아 무릎을 고정한 채 발목을 회전하십시오. 백스윙과 다운스윙 때 힙턴이 막히는 이유를 찾아냅니다.",
      passCriteria = "골반이 들리지 않고 고관절 내회전 35도 이상, 외회전 45도 이상 달성",
      swingImpact = "임팩트 구간에서 좌측 골반이 뒤로 빠지지 못하고 막혀 상체로 덮어 치거나 우측 푸시 샷 발생",
      previewIconType = "SEATED_HIP"
    ),
    ScreeningItem(
      id = 10,
      title = "척추 측면 굴곡 유연성",
      englishSubtitle = "Spine Lateral Flexion",
      category = "척추 & 사이드 벤딩",
      description = "골반을 고정한 채 몸을 옆으로 기울여 손끝이 무릎 바깥선을 지날 수 있는지 검사합니다.",
      instructionSteps = listOf(
        "바르게 서서 양손을 허벅지 옆에 붙입니다.",
        "상체가 앞이나 뒤로 쏠리지 않도록 순수하게 옆으로만 몸을 기울입니다.",
        "손가락 끝이 무릎 관절 높이 이하로 내려가는지 좌우 모두 측정합니다."
      ),
      voiceGuideText = "몸을 순수하게 옆으로 기울이십시오! 앞뒤로 숙여지면 안 됩니다. 다운스윙 시 사이드 벤딩을 만들어내는 능력입니다.",
      passCriteria = "상체의 회전이나 골반 이동 없이 손끝이 무릎 외측선까지 유연하게 도달",
      swingImpact = "임팩트 시 측면 척추 기울기를 유지하지 못해 상체가 벌떡 일어서는 배치기와 쌩크 유발",
      previewIconType = "LATERAL_FLEXION"
    ),
    ScreeningItem(
      id = 11,
      title = "손목 힌지 및 코킹 가동성",
      englishSubtitle = "Wrist Hinge & Cocking Mobility",
      category = "손목 & 래깅",
      description = "전완을 고정한 채 손목을 위(코킹)와 뒤(힌지)로 부드럽게 70도 이상 젖힐 수 있는지 봅니다.",
      instructionSteps = listOf(
        "팔을 앞으로 뻗어 주먹을 쥐고 전완을 지면과 평행하게 둡니다.",
        "손목만 위로 꺾는 코킹과 손등 쪽으로 젖히는 힌지를 시행합니다.",
        "저항감이나 통증 없이 70도 이상의 각도가 나오는지 확인합니다."
      ),
      voiceGuideText = "손목의 코킹과 힌지 가동성을 봅니다. 래깅 유지와 임팩트 압축력을 결정짓는 핵심 손목 움직임입니다.",
      passCriteria = "손목 움직임 시 팔꿈치 움직임 없이 70도 이상 원활한 코킹 및 힌지 유지",
      swingImpact = "캐스팅(Casting) 및 스쿠핑(Scooping) 유발로 볼을 띄우지 못하고 심한 비거리 손실 및 탑핑",
      previewIconType = "WRIST_MOBILITY"
    ),
    ScreeningItem(
      id = 12,
      title = "발목 족배굴곡 벽 검사",
      englishSubtitle = "Ankle Dorsiflexion Mobility",
      category = "발목 & 지면 반력",
      description = "벽에서 엄지발가락을 10cm 떼고 무릎을 앞으로 구부려 발뒤꿈치 들림 없이 벽에 닿는지 검사합니다.",
      instructionSteps = listOf(
        "벽 앞에 서서 한쪽 발가락 끝을 벽에서 주먹 하나(약 10cm) 거리에 둡니다.",
        "발뒤꿈치가 바닥에서 절대 떨어지지 않도록 접지합니다.",
        "무릎을 앞으로 밀어 무릎 뼈가 벽에 가볍게 닿을 수 있는지 확인합니다."
      ),
      voiceGuideText = "발뒤꿈치를 바닥에 완전히 붙이고 무릎을 벽 쪽으로 굽히십시오. 하체 밸런스와 지면 반력 활용의 필수 기초입니다.",
      passCriteria = "발뒤꿈치 들림 없이 무릎이 벽에 안정적으로 닿음",
      swingImpact = "다운스윙 시 발목이 버티지 못해 엉덩이가 공 쪽으로 전진하는 얼리 익스텐션 및 뒷땅 유발",
      previewIconType = "ANKLE_MOBILITY"
    ),
    ScreeningItem(
      id = 13,
      title = "단일 하지 브릿지 코어 안정성",
      englishSubtitle = "Single Leg Glute Bridge Hold",
      category = "둔근 & 골반 안정성",
      description = "누워서 골반을 들어올린 브릿지 자세에서 한 다리를 뻗어 10초간 골반 수평을 유지할 수 있는지 평가합니다.",
      instructionSteps = listOf(
        "바닥에 등을 대고 누워 무릎을 90도로 세우고 엉덩이를 높이 듭니다.",
        "한쪽 다리를 무릎 높이와 같게 앞으로 곧게 뻗습니다.",
        "지탱하는 쪽 골반이 아래로 처지거나 허리에 무리가 가지 않고 10초 유지되는지 봅니다."
      ),
      voiceGuideText = "엉덩이를 들고 한 다리를 뻗어 10초간 버티십시오! 둔근 파워가 스윙 중 골반이 무너지는 것을 막아줍니다.",
      passCriteria = "골반의 기울어짐이나 햄스트링 쥐남 없이 둔근의 힘으로 10초간 수평 유지",
      swingImpact = "임팩트 구간 하체 지지력이 무너져 슬라이드(Slide) 발생 및 에너지 전달 누수로 비거리 격감",
      previewIconType = "GLUTE_BRIDGE"
    )
  )
}
