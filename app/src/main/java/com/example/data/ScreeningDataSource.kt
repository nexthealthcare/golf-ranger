package com.example.data

import com.example.model.ScreeningItem

object ScreeningDataSource {
  val screeningItems: List<ScreeningItem> = listOf(
    ScreeningItem(
      id = 1,
      title = "전신 굴곡 검진 (SFMA Flexion)",
      englishSubtitle = "Multi-Segmental Flexion",
      category = "후방 사슬 유연성 & 힙 힌지",
      description = "양발을 모으고 무릎을 곧게 편 채 상체를 앞으로 숙여 손가락 끝이 발끝에 닿는지 검사합니다. 햄스트링, 둔근, 요추 기립근의 후방 사슬 유연성과 고관절 굴곡(힙 힌지) 능력을 종합 평가합니다.",
      instructionSteps = listOf(
        "양발을 나란히 모으고 무릎을 완전히 편 상태로 섭니다.",
        "골반(고관절)을 접으며 척추를 부드럽게 둥글려 앞으로 숙입니다.",
        "무릎이 구부러지지 않은 상태에서 손끝이 발끝에 자연스럽게 닿는지 확인합니다."
      ),
      voiceGuideText = "양발을 모으고 무릎을 곧게 펴세요. 숨을 내쉬며 천천히 상체를 앞으로 숙여 손끝으로 발끝을 터치해 봅니다. 무릎이 구부러지지 않도록 주의하며 유연성을 확인합니다.",
      passCriteria = "무릎 굽힘 없이 손가락 끝이 발끝에 편안하게 터치 가능 (고관절 굴곡 70도 이상 & 균일한 척추 굴곡 곡선)",
      swingImpact = "후방 사슬 유연성 및 힙 힌지 부족 시, 어드레스에서 등이 굽는 C-포스처가 형성되며, 다운스윙 시 고관절 회전 공간이 부족해 상체가 벌떡 일어서는 배치기(얼리 익스텐션)와 뒷땅/탑핑 타점 오차를 직결 유발합니다.",
      technicalId = "SFMA_FLEXION",
      drawableResName = "img_sfma_flexion"
    ),
    ScreeningItem(
      id = 2,
      title = "전신 신전 검진 (SFMA Extension)",
      englishSubtitle = "Multi-Segmental Extension",
      category = "전방 사슬 가동성 & 흉추 신전",
      description = "양발을 모으고 양팔을 머리 위로 뻗은 채 상체와 척추를 뒤로 젖혀 신전 가동성을 평가합니다. 복부 전방 사슬, 고관절 굴곡근, 흉추 신전력과 척추 후방 지탱력을 점검합니다.",
      instructionSteps = listOf(
        "양발을 모으고 양팔을 귀 옆으로 높이 들어 만세 자세를 취합니다.",
        "무릎을 편 채 골반을 살짝 앞으로 밀면서 가슴과 시선을 천장 쪽으로 부드럽게 젖힙니다.",
        "골반 앞쪽(ASIS)이 발끝선을 넘고, 견갑골이 뒤꿈치 수직선 너머로 신전되는지 확인합니다."
      ),
      voiceGuideText = "양발을 모으고 양팔을 위로 뻗으세요. 무릎을 편 채 가슴을 활짝 펴며 뒤로 부드럽게 젖혀봅니다. 통증 없이 자연스럽게 신전되는지 확인합니다.",
      passCriteria = "통증 없이 ASIS(골반)가 발끝선을 넘고 견갑골이 뒤꿈치 수직선 뒤로 넘어가며 고른 척추 아치 형성",
      swingImpact = "흉추 및 고관절 신전 가동성 결핍 시, 백스윙 탑에서 척추가 타깃 방향으로 꺾이는 역피봇(Reverse Spine Angle)이 발생하고, 다운스윙~피니시에서 척추각이 무너져(Loss of Posture) 심한 슬라이스와 만성 요통을 유발합니다.",
      technicalId = "SFMA_EXTENSION",
      drawableResName = "img_sfma_extension"
    ),
    ScreeningItem(
      id = 3,
      title = "전신 회전 검진 (SFMA Rotation)",
      englishSubtitle = "Multi-Segmental Rotation",
      category = "몸통 & 골반 회전 분리 (코일링)",
      description = "양발을 모으고 선 상태에서 발바닥을 바닥에 고정하고 몸통, 흉추, 골반을 좌우로 최대 회전합니다. 골프 백스윙의 꼬임과 다운스윙 하체 선행에 필요한 회전 가동 범위를 점검합니다.",
      instructionSteps = listOf(
        "양발을 모으고 양팔을 가슴 앞에 X자로 교차해 얹습니다.",
        "발바닥이 바닥에서 떨어지지 않게 단단히 고정한 상태로 상체와 골반을 좌측으로 최대 회전합니다.",
        "동일하게 우측으로도 회전하여 양방향 회전 각도(어깨 100도 이상, 골반 50도 이상)와 좌우 대칭성을 확인합니다."
      ),
      voiceGuideText = "양발을 모으고 양팔을 가슴에 교차하세요. 발바닥을 고정한 채 몸통 전체를 좌우로 회전합니다. 백스윙 꼬임과 팔로우스루 회전 대칭성을 체크합니다.",
      passCriteria = "발바닥 고정 상태에서 골반 50도 이상, 어깨선 100도 이상 통증 없이 좌우 대칭 회전 가능",
      swingImpact = "회전 가동성 잠김 시, 백스윙 코일링 부족으로 상체가 덤벼 엎어 치는 궤도인 오버 더 탑(Over-the-Top) 슬라이스가 발생하거나, 골반이 회전하지 못하고 옆으로 밀리는 스웨이(Sway) 및 슬라이드가 발생합니다.",
      technicalId = "SFMA_ROTATION",
      drawableResName = "img_sfma_rotation"
    ),
    ScreeningItem(
      id = 4,
      title = "외발서기 밸런스 (SFMA SLS)",
      englishSubtitle = "Single Leg Stance (SLS)",
      category = "단일 하지 지지력 & 중둔근 안정성",
      description = "한 발로 서서 반대쪽 무릎을 90도로 들어 올린 후, 10초 이상 골반의 수평과 신체 중심을 흔들림 없이 유지할 수 있는지 평가합니다.",
      instructionSteps = listOf(
        "바르게 선 상태에서 한쪽 발을 들어 무릎과 엉덩이를 90도로 들어 올립니다.",
        "지탱하는 발의 엉덩이(중둔근)에 힘을 주어 골반이 옆으로 빠지거나 상체가 기울지 않게 10초간 버팁니다.",
        "좌우 다리를 교대하여 양측 지지력과 밸런스 차이를 비교합니다."
      ),
      voiceGuideText = "한 발로 서서 반대쪽 다리를 90도로 들어 올리세요. 골반이 비뚤어지지 않고 10초 이상 안정적으로 서 있는지 점검합니다. 좌우 번갈아 테스트해 보세요.",
      passCriteria = "골반의 처짐(Trendelenburg)이나 상체 과도한 기울임, 발 디딤 없이 좌우 각 10초 이상 안정적 중심 유지",
      swingImpact = "단일 하지 지지력 및 둔근 안정성 저하 시, 다운스윙 임팩트 구간에서 좌측 벽(리드 사이드)을 만들지 못하고 골반이 밀리는 슬라이드(Slide) 및 중심축 흔들림, 라운드 후반 나인홀 급격한 타수 붕괴로 이어집니다.",
      technicalId = "SFMA_SLS",
      drawableResName = "img_sfma_sls"
    ),
    ScreeningItem(
      id = 5,
      title = "오버헤드 딥 스쿼트 (SFMA Squat)",
      englishSubtitle = "Overhead Deep Squat",
      category = "발목 족배굴곡·고관절·흉추 복합 가동성",
      description = "양손(또는 골프클럽)을 머리 위로 곧게 뻗은 채 뒤꿈치가 지면에서 떨어지지 않고 대퇴부가 수평 이하로 깊게 내려갈 수 있는지 평가합니다.",
      instructionSteps = listOf(
        "양발을 어깨 너비로 벌리고 양팔(또는 클럽)을 머리 위로 수직으로 곧게 뻗습니다.",
        "뒤꿈치를 바닥에 완전히 밀착한 상태에서 엉덩이를 무릎 아래 깊숙이 앉습니다.",
        "상체가 앞으로 과도하게 쏟아지지 않고 가슴이 정면을 향해 열려 있는지 확인합니다."
      ),
      voiceGuideText = "클럽을 머리 위로 들고 서서 뒤꿈치를 바닥에 붙인 채 깊게 앉으세요. 발목과 고관절, 흉추의 복합적인 가동성과 코어 안정성을 평가합니다.",
      passCriteria = "뒤꿈치 들림 없이 대퇴부가 지면과 수평 이하로 하강하며, 상체 척추각이 정강이 각도와 평행 유지",
      swingImpact = "발목 족배굴곡 및 고관절 힌지 복합 제한 시, 다운스윙 시 지면 반력을 쓰지 못하고 골반이 볼 방향으로 전진하는 얼리 익스텐션(배치기)과 캐스팅으로 인한 비거리 손실이 발생합니다.",
      technicalId = "SFMA_SQUAT",
      drawableResName = "img_sfma_squat"
    )
  )
}
