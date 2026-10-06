package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.ui.theme.EnergeticGold
import com.example.ui.theme.FairwayGreenDark
import com.example.ui.theme.FairwayGreenLight
import com.example.ui.theme.FairwayGreenPrimary

@Composable
fun ScreeningPoseVisualizer(
  iconType: String,
  modifier: Modifier = Modifier.width(180.dp).height(140.dp)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height

    // 1. 3D 원형 플랫폼 그림자
    drawOval(
      brush = Brush.radialGradient(
        colors = listOf(Color(0xFFE2EBE5), Color(0x00F8FAF9)),
        center = Offset(w * 0.5f, h * 0.90f),
        radius = w * 0.42f
      ),
      topLeft = Offset(w * 0.12f, h * 0.82f),
      size = Size(w * 0.76f, h * 0.16f)
    )

    // 입체감 있는 바디 컬러 그라데이션
    val bodyGrad = Brush.verticalGradient(
      colors = listOf(FairwayGreenLight, FairwayGreenPrimary, FairwayGreenDark)
    )
    val accentRibbon = EnergeticGold
    val jointColor = Color.White

    when (iconType) {
      "PELVIC_TILT" -> {
        // 어드레스 골반 틸트 - 입체감 있는 상체 경사 & 골반 커브
        // 머리 (입체 구)
        drawCircle(bodyGrad, radius = 13f, center = Offset(w * 0.38f, h * 0.22f))
        drawCircle(jointColor.copy(alpha = 0.6f), radius = 4f, center = Offset(w * 0.36f, h * 0.20f))

        // 상체 볼륨 캡슐
        drawRoundRect(
          brush = bodyGrad,
          topLeft = Offset(w * 0.36f, h * 0.30f),
          size = Size(18f, 32f),
          cornerRadius = CornerRadius(9f, 9f)
        )

        // 골반 힙 볼륨
        drawCircle(brush = bodyGrad, radius = 11f, center = Offset(w * 0.52f, h * 0.54f))

        // 허벅지 & 종아리
        drawLine(bodyGrad, Offset(w * 0.52f, h * 0.54f), Offset(w * 0.45f, h * 0.72f), strokeWidth = 12f, cap = StrokeCap.Round)
        drawLine(bodyGrad, Offset(w * 0.45f, h * 0.72f), Offset(w * 0.42f, h * 0.88f), strokeWidth = 10f, cap = StrokeCap.Round)

        // 골반 앞뒤 틸트 3D 모션 리본 화살표
        val ribbon = Path().apply {
          moveTo(w * 0.38f, h * 0.54f)
          quadraticTo(w * 0.54f, h * 0.42f, w * 0.68f, h * 0.56f)
        }
        drawPath(ribbon, color = accentRibbon, style = Stroke(width = 6f, cap = StrokeCap.Round))
        drawCircle(accentRibbon, radius = 6f, center = Offset(w * 0.68f, h * 0.56f))
      }

      "PELVIC_ROTATION" -> {
        // 골반 분리 회전 - 정면 상체 고정, 골반 트위스트 리본
        drawCircle(bodyGrad, radius = 13f, center = Offset(w * 0.5f, h * 0.18f))
        drawRoundRect(bodyGrad, topLeft = Offset(w * 0.44f, h * 0.28f), size = Size(22f, 30f), cornerRadius = CornerRadius(10f, 10f))
        // 가슴 위 모은 팔
        drawRoundRect(bodyGrad, topLeft = Offset(w * 0.32f, h * 0.32f), size = Size(64f, 12f), cornerRadius = CornerRadius(6f, 6f))

        // 회전 리본 타원
        val ovalPath = Path().apply {
          moveTo(w * 0.30f, h * 0.54f)
          quadraticTo(w * 0.50f, h * 0.64f, w * 0.70f, h * 0.54f)
        }
        drawPath(ovalPath, accentRibbon, style = Stroke(width = 7f, cap = StrokeCap.Round))
        drawCircle(accentRibbon, radius = 5.5f, center = Offset(w * 0.70f, h * 0.54f))

        // 하체
        drawLine(bodyGrad, Offset(w * 0.45f, h * 0.58f), Offset(w * 0.40f, h * 0.88f), strokeWidth = 11f, cap = StrokeCap.Round)
        drawLine(bodyGrad, Offset(w * 0.55f, h * 0.58f), Offset(w * 0.60f, h * 0.88f), strokeWidth = 11f, cap = StrokeCap.Round)
      }

      "TORSO_ROTATION" -> {
        // 흉추 상체 회전 - 하체 고정 + 가슴 회전
        drawCircle(bodyGrad, radius = 13f, center = Offset(w * 0.56f, h * 0.18f))
        drawRoundRect(bodyGrad, topLeft = Offset(w * 0.46f, h * 0.28f), size = Size(24f, 32f), cornerRadius = CornerRadius(10f, 10f))

        // 가슴 회전 리본
        val chestRibbon = Path().apply {
          moveTo(w * 0.28f, h * 0.34f)
          quadraticTo(w * 0.50f, h * 0.22f, w * 0.74f, h * 0.36f)
        }
        drawPath(chestRibbon, accentRibbon, style = Stroke(width = 7f, cap = StrokeCap.Round))
        drawCircle(accentRibbon, radius = 6f, center = Offset(w * 0.74f, h * 0.36f))

        // 하체 단단한 지지
        drawLine(bodyGrad, Offset(w * 0.45f, h * 0.60f), Offset(w * 0.38f, h * 0.88f), strokeWidth = 12f, cap = StrokeCap.Round)
        drawLine(bodyGrad, Offset(w * 0.55f, h * 0.60f), Offset(w * 0.62f, h * 0.88f), strokeWidth = 12f, cap = StrokeCap.Round)
      }

      "OVERHEAD_SQUAT" -> {
        // 딥 오버헤드 스쿼트 - 클럽을 머리 위로 들고 깊게 앉은 입체 자세
        // 머리
        drawCircle(bodyGrad, radius = 12f, center = Offset(w * 0.5f, h * 0.32f))

        // 머리 위 클럽 샤프트 (골드)
        drawLine(Brush.horizontalGradient(listOf(EnergeticGold, Color(0xFFFCD34D), EnergeticGold)),
          Offset(w * 0.20f, h * 0.14f), Offset(w * 0.80f, h * 0.14f), strokeWidth = 6f, cap = StrokeCap.Round)

        // 수직 팔 볼륨
        drawLine(bodyGrad, Offset(w * 0.36f, h * 0.36f), Offset(w * 0.28f, h * 0.14f), strokeWidth = 9f, cap = StrokeCap.Round)
        drawLine(bodyGrad, Offset(w * 0.64f, h * 0.36f), Offset(w * 0.72f, h * 0.14f), strokeWidth = 9f, cap = StrokeCap.Round)

        // 상체 & 스쿼트 엉덩이 볼륨
        drawRoundRect(bodyGrad, topLeft = Offset(w * 0.44f, h * 0.38f), size = Size(20f, 28f), cornerRadius = CornerRadius(9f, 9f))
        // 깊게 굽힌 다리 볼륨
        drawLine(bodyGrad, Offset(w * 0.46f, h * 0.60f), Offset(w * 0.28f, h * 0.64f), strokeWidth = 12f, cap = StrokeCap.Round)
        drawLine(bodyGrad, Offset(w * 0.28f, h * 0.64f), Offset(w * 0.32f, h * 0.88f), strokeWidth = 10f, cap = StrokeCap.Round)
        drawLine(bodyGrad, Offset(w * 0.54f, h * 0.60f), Offset(w * 0.72f, h * 0.64f), strokeWidth = 12f, cap = StrokeCap.Round)
        drawLine(bodyGrad, Offset(w * 0.72f, h * 0.64f), Offset(w * 0.68f, h * 0.88f), strokeWidth = 10f, cap = StrokeCap.Round)
      }

      "ANKLE_MOBILITY" -> {
        // 발목 벽 검사 - 입체 벽 & 굽힌 무릎
        // 입체 벽 기둥
        drawRoundRect(
          brush = Brush.verticalGradient(listOf(Color(0xFF9CA3AF), Color(0xFF6B7280))),
          topLeft = Offset(w * 0.82f, h * 0.16f),
          size = Size(16f, h * 0.74f),
          cornerRadius = CornerRadius(4f, 4f)
        )

        // 발목 & 다리
        val heel = Offset(w * 0.38f, h * 0.88f)
        val toe = Offset(w * 0.68f, h * 0.88f)
        drawLine(bodyGrad, heel, toe, strokeWidth = 12f, cap = StrokeCap.Round)

        // 벽에 닿는 무릎 캡슐
        val kneeAtWall = Offset(w * 0.80f, h * 0.60f)
        drawLine(bodyGrad, heel, kneeAtWall, strokeWidth = 12f, cap = StrokeCap.Round)
        drawCircle(accentRibbon, radius = 7f, center = kneeAtWall)

        // 상체
        drawCircle(bodyGrad, radius = 12f, center = Offset(w * 0.50f, h * 0.26f))
        drawLine(bodyGrad, Offset(w * 0.50f, h * 0.26f), kneeAtWall, strokeWidth = 10f, cap = StrokeCap.Round)
      }

      "DRILL_BUTT" -> {
        // 골프 드릴: 엉덩이 벽 터치 & 얼라인먼트 스틱
        // 얼라인먼트 스틱 (골드)
        drawLine(Brush.verticalGradient(listOf(EnergeticGold, Color(0xFFD97706))),
          Offset(w * 0.62f, h * 0.20f), Offset(w * 0.62f, h * 0.90f), strokeWidth = 6f, cap = StrokeCap.Round)

        // 골퍼 어드레스 & 힙 접촉점
        drawCircle(bodyGrad, radius = 12f, center = Offset(w * 0.40f, h * 0.26f))
        drawLine(bodyGrad, Offset(w * 0.40f, h * 0.26f), Offset(w * 0.58f, h * 0.54f), strokeWidth = 14f, cap = StrokeCap.Round)
        // 힙 터치 강조 원
        drawCircle(accentRibbon, radius = 8f, center = Offset(w * 0.58f, h * 0.54f))
        drawCircle(Color.White, radius = 4f, center = Offset(w * 0.58f, h * 0.54f))

        // 다리
        drawLine(bodyGrad, Offset(w * 0.58f, h * 0.54f), Offset(w * 0.48f, h * 0.72f), strokeWidth = 11f, cap = StrokeCap.Round)
        drawLine(bodyGrad, Offset(w * 0.48f, h * 0.72f), Offset(w * 0.45f, h * 0.88f), strokeWidth = 10f, cap = StrokeCap.Round)

        // 골프채 7번 아이언
        drawLine(Brush.linearGradient(listOf(Color(0xFF94A3B8), Color(0xFF475569))),
          Offset(w * 0.38f, h * 0.44f), Offset(w * 0.32f, h * 0.86f), strokeWidth = 5f, cap = StrokeCap.Round)
      }

      "DRILL_TEE" -> {
        // 티 1cm 스치기 정타 드릴
        // 바닥 티 (골드)
        drawLine(Brush.verticalGradient(listOf(Color(0xFFCBD5E1), Color(0xFF94A3B8))),
          Offset(w * 0.50f, h * 0.88f), Offset(w * 0.50f, h * 0.74f), strokeWidth = 7f, cap = StrokeCap.Round)
        drawCircle(accentRibbon, radius = 6f, center = Offset(w * 0.50f, h * 0.72f))

        // 스윙 궤도 스치기 잔상 아크
        val sweepArc = Path().apply {
          moveTo(w * 0.25f, h * 0.65f)
          quadraticTo(w * 0.50f, h * 0.73f, w * 0.75f, h * 0.62f)
        }
        drawPath(sweepArc, accentRibbon, style = Stroke(width = 6f, cap = StrokeCap.Round))

        // 클럽 헤드
        drawRoundRect(bodyGrad, topLeft = Offset(w * 0.44f, h * 0.68f), size = Size(20f, 10f), cornerRadius = CornerRadius(3f, 3f))
      }

      else -> {
        // 범용 모빌리티 자세 (입체 볼륨감)
        drawCircle(bodyGrad, radius = 13f, center = Offset(w * 0.5f, h * 0.22f))
        drawRoundRect(bodyGrad, topLeft = Offset(w * 0.45f, h * 0.32f), size = Size(20f, 32f), cornerRadius = CornerRadius(9f, 9f))
        drawLine(bodyGrad, Offset(w * 0.46f, h * 0.64f), Offset(w * 0.38f, h * 0.88f), strokeWidth = 11f, cap = StrokeCap.Round)
        drawLine(bodyGrad, Offset(w * 0.54f, h * 0.64f), Offset(w * 0.62f, h * 0.88f), strokeWidth = 11f, cap = StrokeCap.Round)

        // 다이내믹 에너지 링
        drawCircle(accentRibbon, radius = 9f, center = Offset(w * 0.5f, h * 0.44f), style = Stroke(3f))
      }
    }
  }
}
