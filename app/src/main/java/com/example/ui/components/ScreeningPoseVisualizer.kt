package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.ui.theme.FairwayGreenPrimary
import com.example.ui.theme.FairwayGreenContainer
import com.example.ui.theme.EnergeticGold
import com.example.ui.theme.EnergeticCoral

/**
 * 신체검진 대표 입체감 일러스트 비주얼라이저
 * 단순 선형(와이어프레임)이 아닌, 골퍼의 신체 볼륨감과 동작 방향, 골프 클럽이 결합된 입체 일러스트입니다.
 */
@Composable
fun ScreeningPoseVisualizer(
  iconType: String,
  modifier: Modifier = Modifier.width(180.dp).height(125.dp)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height

    // 1. 입체 배경 바닥 매트 (그린 러프 & 페어웨이 질감 베이스)
    val matPath = Path().apply {
      moveTo(w * 0.12f, h * 0.88f)
      lineTo(w * 0.88f, h * 0.88f)
      lineTo(w * 0.95f, h * 0.96f)
      lineTo(w * 0.05f, h * 0.96f)
      close()
    }
    drawPath(
      path = matPath,
      brush = Brush.verticalGradient(
        colors = listOf(Color(0xFFE2E8F0), Color(0xFFCBD5E1)),
        startY = h * 0.88f,
        endY = h * 0.96f
      )
    )

    // 일러스트 색상 토큰
    val skinColor = Color(0xFFFFD1A4)
    val skinShadow = Color(0xFFF0B37E)
    val shirtGreen = FairwayGreenPrimary
    val shirtHighlight = Color(0xFF34D399)
    val pantsNavy = Color(0xFF1E293B)
    val shoeWhite = Color(0xFFF8FAFC)
    val clubSilver = Color(0xFF94A3B8)
    val arrowGold = EnergeticGold

    when (iconType) {
      "PELVIC_TILT" -> {
        // [골반 틸트] 어드레스 측면 자세에서 골반 꼬리뼈의 전후방 틸트 곡선 화살표
        // 머리 & 골프모자
        drawCircle(skinColor, radius = 10f, center = Offset(w * 0.35f, h * 0.32f))
        drawArc(
          color = shirtGreen,
          startAngle = 180f,
          sweepAngle = 180f,
          useCenter = true,
          topLeft = Offset(w * 0.35f - 11f, h * 0.32f - 11f),
          size = Size(22f, 15f)
        )
        // 척추 몸통 (어드레스 각도 기울기)
        val spinePath = Path().apply {
          moveTo(w * 0.38f, h * 0.40f)
          lineTo(w * 0.50f, h * 0.60f)
          lineTo(w * 0.44f, h * 0.62f)
          lineTo(w * 0.34f, h * 0.42f)
          close()
        }
        drawPath(spinePath, shirtGreen)

        // 골반 블록 (입체 사각)
        drawRoundRect(
          brush = Brush.verticalGradient(listOf(pantsNavy, Color(0xFF0F172A))),
          topLeft = Offset(w * 0.46f, h * 0.58f),
          size = Size(w * 0.12f, h * 0.10f),
          cornerRadius = CornerRadius(6f, 6f)
        )

        // 다리 & 무릎 어드레스 굴곡
        drawLine(pantsNavy, Offset(w * 0.52f, h * 0.68f), Offset(w * 0.48f, h * 0.80f), strokeWidth = 9f, cap = StrokeCap.Round)
        drawLine(pantsNavy, Offset(w * 0.48f, h * 0.80f), Offset(w * 0.50f, h * 0.90f), strokeWidth = 9f, cap = StrokeCap.Round)
        // 신발
        drawRoundRect(
          color = shoeWhite,
          topLeft = Offset(w * 0.44f, h * 0.88f),
          size = Size(22f, 8f),
          cornerRadius = CornerRadius(4f, 4f)
        )

        // 입체 동작 화살표 (골반 전방/후방 틸트 양방향 회전 아크)
        val arrowPath = Path().apply {
          moveTo(w * 0.62f, h * 0.55f)
          cubicTo(w * 0.72f, h * 0.58f, w * 0.72f, h * 0.70f, w * 0.62f, h * 0.73f)
        }
        drawPath(arrowPath, color = arrowGold, style = Stroke(width = 4.5f, cap = StrokeCap.Round))
        // 화살표 머리 (위)
        val headUp = Path().apply {
          moveTo(w * 0.62f, h * 0.55f)
          lineTo(w * 0.66f, h * 0.50f)
          lineTo(w * 0.68f, h * 0.58f)
          close()
        }
        drawPath(headUp, arrowGold)
        // 화살표 머리 (아래)
        val headDown = Path().apply {
          moveTo(w * 0.62f, h * 0.73f)
          lineTo(w * 0.68f, h * 0.70f)
          lineTo(w * 0.66f, h * 0.78f)
          close()
        }
        drawPath(headDown, arrowGold)
      }

      "PELVIC_ROTATION" -> {
        // [골반 회전 분리] 상체는 고정된 채 골반만 트위스트되는 3D 다이내믹 포즈
        // 머리
        drawCircle(skinColor, radius = 11f, center = Offset(w * 0.5f, h * 0.28f))
        // 고정된 상체 (팔짱 낀 포즈)
        val torsoPath = Path().apply {
          moveTo(w * 0.40f, h * 0.38f)
          lineTo(w * 0.60f, h * 0.38f)
          lineTo(w * 0.56f, h * 0.56f)
          lineTo(w * 0.44f, h * 0.56f)
          close()
        }
        drawPath(torsoPath, shirtGreen)
        // 팔짱 라인
        drawLine(shirtHighlight, Offset(w * 0.42f, h * 0.46f), Offset(w * 0.58f, h * 0.46f), strokeWidth = 5f, cap = StrokeCap.Round)

        // 회전하는 골반 (기울어진 3D 타원)
        drawOval(
          brush = Brush.horizontalGradient(listOf(pantsNavy, Color(0xFF334155), pantsNavy)),
          topLeft = Offset(w * 0.38f, h * 0.54f),
          size = Size(w * 0.24f, 18f)
        )

        // 양다리 안정된 스탠스
        drawLine(pantsNavy, Offset(w * 0.43f, h * 0.62f), Offset(w * 0.38f, h * 0.88f), strokeWidth = 8f, cap = StrokeCap.Round)
        drawLine(pantsNavy, Offset(w * 0.57f, h * 0.62f), Offset(w * 0.62f, h * 0.88f), strokeWidth = 8f, cap = StrokeCap.Round)
        drawRoundRect(shoeWhite, topLeft = Offset(w * 0.33f, h * 0.86f), size = Size(18f, 7f), cornerRadius = CornerRadius(3f, 3f))
        drawRoundRect(shoeWhite, topLeft = Offset(w * 0.58f, h * 0.86f), size = Size(18f, 7f), cornerRadius = CornerRadius(3f, 3f))

        // 골반 트위스트 3D 원형 궤적 화살표
        drawArc(
          color = arrowGold,
          startAngle = -20f,
          sweepAngle = 140f,
          useCenter = false,
          topLeft = Offset(w * 0.32f, h * 0.50f),
          size = Size(w * 0.36f, 26f),
          style = Stroke(width = 4.5f, cap = StrokeCap.Round)
        )
      }

      "TORSO_ROTATION" -> {
        // [상체 흉추 회전] 골반은 의자에 고정, 상체가 45도 이상 코일링 회전하는 포즈
        // 머리 (측면 회전 시선)
        drawCircle(skinColor, radius = 10f, center = Offset(w * 0.54f, h * 0.26f))
        // 회전된 흉추 몸통
        val torsoPath = Path().apply {
          moveTo(w * 0.36f, h * 0.36f)
          lineTo(w * 0.64f, h * 0.32f)
          lineTo(w * 0.56f, h * 0.56f)
          lineTo(w * 0.44f, h * 0.56f)
          close()
        }
        drawPath(torsoPath, shirtGreen)
        // 양 어깨에 얹은 클럽 샤프트 (회전 각도 시각화)
        drawLine(clubSilver, Offset(w * 0.28f, h * 0.40f), Offset(w * 0.72f, h * 0.28f), strokeWidth = 4f, cap = StrokeCap.Round)

        // 고정된 하체 & 시트
        drawRoundRect(
          color = Color(0xFF64748B),
          topLeft = Offset(w * 0.36f, h * 0.62f),
          size = Size(w * 0.28f, 10f),
          cornerRadius = CornerRadius(4f, 4f)
        )
        drawLine(pantsNavy, Offset(w * 0.44f, h * 0.62f), Offset(w * 0.44f, h * 0.88f), strokeWidth = 8f, cap = StrokeCap.Round)
        drawLine(pantsNavy, Offset(w * 0.56f, h * 0.62f), Offset(w * 0.56f, h * 0.88f), strokeWidth = 8f, cap = StrokeCap.Round)

        // 상체 코일링 회전 입체 아크
        drawArc(
          color = arrowGold,
          startAngle = -45f,
          sweepAngle = 90f,
          useCenter = false,
          topLeft = Offset(w * 0.30f, h * 0.20f),
          size = Size(w * 0.40f, 30f),
          style = Stroke(width = 4f, cap = StrokeCap.Round)
        )
      }

      "OVERHEAD_SQUAT" -> {
        // [오버헤드 딥 스쿼트] 클럽을 머리 위로 곧게 뻗고 깊게 앉은 완벽한 스쿼트 3D 실루엣
        // 머리 위 클럽 샤프트
        drawLine(clubSilver, Offset(w * 0.22f, h * 0.16f), Offset(w * 0.78f, h * 0.16f), strokeWidth = 4.5f, cap = StrokeCap.Round)
        // 위로 곧게 뻗은 양팔
        drawLine(skinColor, Offset(w * 0.30f, h * 0.16f), Offset(w * 0.42f, h * 0.32f), strokeWidth = 6f, cap = StrokeCap.Round)
        drawLine(skinColor, Offset(w * 0.70f, h * 0.16f), Offset(w * 0.58f, h * 0.32f), strokeWidth = 6f, cap = StrokeCap.Round)

        // 머리 & 상체
        drawCircle(skinColor, radius = 9f, center = Offset(w * 0.5f, h * 0.30f))
        drawRoundRect(
          color = shirtGreen,
          topLeft = Offset(w * 0.43f, h * 0.36f),
          size = Size(w * 0.14f, 24f),
          cornerRadius = CornerRadius(5f, 5f)
        )

        // 깊게 굴곡된 대퇴 & 종아리 (90도 이하 딥스쿼트)
        val legLeft = Path().apply {
          moveTo(w * 0.44f, h * 0.54f)
          lineTo(w * 0.32f, h * 0.64f)
          lineTo(w * 0.34f, h * 0.86f)
        }
        drawPath(legLeft, pantsNavy, style = Stroke(width = 8.5f, cap = StrokeCap.Round, join = StrokeJoin.Round))

        val legRight = Path().apply {
          moveTo(w * 0.56f, h * 0.54f)
          lineTo(w * 0.68f, h * 0.64f)
          lineTo(w * 0.66f, h * 0.86f)
        }
        drawPath(legRight, pantsNavy, style = Stroke(width = 8.5f, cap = StrokeCap.Round, join = StrokeJoin.Round))

        // 신발 접지
        drawRoundRect(shoeWhite, topLeft = Offset(w * 0.28f, h * 0.85f), size = Size(18f, 7f), cornerRadius = CornerRadius(3f, 3f))
        drawRoundRect(shoeWhite, topLeft = Offset(w * 0.62f, h * 0.85f), size = Size(18f, 7f), cornerRadius = CornerRadius(3f, 3f))
      }

      "ANKLE_MOBILITY" -> {
        // [벽 발목 족배굴곡] 벽 앞에서 뒤꿈치 붙인 채 무릎을 앞으로 밀어내는 모빌리티 측정
        // 우측 수직 벽 (회색 격자 벽면)
        drawRoundRect(
          color = Color(0xFF94A3B8),
          topLeft = Offset(w * 0.78f, h * 0.20f),
          size = Size(14f, h * 0.70f),
          cornerRadius = CornerRadius(3f, 3f)
        )

        // 골퍼 다리 & 무릎 (무릎이 벽 방향으로 10cm 전진)
        // 대퇴
        drawLine(pantsNavy, Offset(w * 0.35f, h * 0.45f), Offset(w * 0.65f, h * 0.68f), strokeWidth = 9f, cap = StrokeCap.Round)
        // 정강이 (벽을 향해 깊게 기울어짐)
        drawLine(pantsNavy, Offset(w * 0.65f, h * 0.68f), Offset(w * 0.45f, h * 0.88f), strokeWidth = 9f, cap = StrokeCap.Round)

        // 바닥에 완전 밀착된 발 & 뒤꿈치 접지
        drawRoundRect(
          color = shoeWhite,
          topLeft = Offset(w * 0.40f, h * 0.86f),
          size = Size(36f, 9f),
          cornerRadius = CornerRadius(4f, 4f)
        )

        // 뒤꿈치 접지 초록색 체크 포인트
        drawCircle(FairwayGreenPrimary, radius = 5f, center = Offset(w * 0.42f, h * 0.88f))

        // 무릎 전진 추진 화살표
        val lungeArrow = Path().apply {
          moveTo(w * 0.58f, h * 0.64f)
          lineTo(w * 0.74f, h * 0.64f)
        }
        drawPath(lungeArrow, arrowGold, style = Stroke(width = 4.5f, cap = StrokeCap.Round))
        val arrowHead = Path().apply {
          moveTo(w * 0.74f, h * 0.64f)
          lineTo(w * 0.69f, h * 0.59f)
          lineTo(w * 0.69f, h * 0.69f)
          close()
        }
        drawPath(arrowHead, arrowGold)
      }

      "DRILL_BUTT" -> {
        // [얼라인먼트 힙 터치 드릴] 어드레스 후 엉덩이 뒤 스틱과 닿아있는 임팩트 자세
        // 수직 얼라인먼트 스틱
        drawLine(Color(0xFFE11D48), Offset(w * 0.65f, h * 0.22f), Offset(w * 0.65f, h * 0.88f), strokeWidth = 5f, cap = StrokeCap.Round)

        // 골퍼 몸통 (임팩트 시 엉덩이가 스틱에 밀착 유지)
        drawCircle(skinColor, radius = 9f, center = Offset(w * 0.44f, h * 0.34f))
        val torso = Path().apply {
          moveTo(w * 0.44f, h * 0.42f)
          lineTo(w * 0.63f, h * 0.58f) // 엉덩이가 스틱(0.65)에 터치
          lineTo(w * 0.55f, h * 0.64f)
          lineTo(w * 0.40f, h * 0.46f)
          close()
        }
        drawPath(torso, shirtGreen)

        // 접촉면 발광 하이라이트 원
        drawCircle(Color(0xFFFEF08A), radius = 10f, center = Offset(w * 0.64f, h * 0.59f), style = Stroke(width = 3f))
        drawCircle(EnergeticCoral, radius = 4f, center = Offset(w * 0.64f, h * 0.59f))

        // 다리 & 임팩트 시 클럽 샤프트
        drawLine(pantsNavy, Offset(w * 0.60f, h * 0.62f), Offset(w * 0.50f, h * 0.88f), strokeWidth = 8f, cap = StrokeCap.Round)
        drawLine(clubSilver, Offset(w * 0.46f, h * 0.48f), Offset(w * 0.32f, h * 0.88f), strokeWidth = 3.5f, cap = StrokeCap.Round)
      }

      "DRILL_TEE" -> {
        // [티 1cm 스치기 드릴] 인-투-인 궤도로 볼 없이 티 상단만 쓸어내는 다운블로우 아크
        // 고무티 (1cm 돌출)
        drawRoundRect(
          color = Color(0xFFEF4444),
          topLeft = Offset(w * 0.48f, h * 0.78f),
          size = Size(10f, 16f),
          cornerRadius = CornerRadius(2f, 2f)
        )
        // 완벽한 클럽헤드 최저점 스윙 아크 곡선
        val swingArc = Path().apply {
          moveTo(w * 0.20f, h * 0.60f)
          cubicTo(w * 0.35f, h * 0.76f, w * 0.60f, h * 0.76f, w * 0.80f, h * 0.55f)
        }
        drawPath(swingArc, arrowGold, style = Stroke(width = 4f, cap = StrokeCap.Round))

        // 클럽헤드 아이언 페이스
        val ironHead = Path().apply {
          moveTo(w * 0.52f, h * 0.74f)
          lineTo(w * 0.60f, h * 0.71f)
          lineTo(w * 0.58f, h * 0.65f)
          lineTo(w * 0.50f, h * 0.68f)
          close()
        }
        drawPath(ironHead, Color(0xFF475569))
        drawLine(clubSilver, Offset(w * 0.55f, h * 0.68f), Offset(w * 0.70f, h * 0.35f), strokeWidth = 3.5f, cap = StrokeCap.Round)
      }

      else -> {
        // 기본 표준 입체 신체 가동성 포즈 (스탠딩 가동 검진)
        drawCircle(skinColor, radius = 10f, center = Offset(w * 0.5f, h * 0.26f))
        drawRoundRect(
          color = shirtGreen,
          topLeft = Offset(w * 0.42f, h * 0.35f),
          size = Size(w * 0.16f, 32f),
          cornerRadius = CornerRadius(6f, 6f)
        )
        drawLine(skinColor, Offset(w * 0.42f, h * 0.38f), Offset(w * 0.28f, h * 0.50f), strokeWidth = 6f, cap = StrokeCap.Round)
        drawLine(skinColor, Offset(w * 0.58f, h * 0.38f), Offset(w * 0.72f, h * 0.50f), strokeWidth = 6f, cap = StrokeCap.Round)
        drawLine(pantsNavy, Offset(w * 0.45f, h * 0.60f), Offset(w * 0.42f, h * 0.88f), strokeWidth = 8f, cap = StrokeCap.Round)
        drawLine(pantsNavy, Offset(w * 0.55f, h * 0.60f), Offset(w * 0.58f, h * 0.88f), strokeWidth = 8f, cap = StrokeCap.Round)
        drawRoundRect(shoeWhite, topLeft = Offset(w * 0.36f, h * 0.86f), size = Size(16f, 7f), cornerRadius = CornerRadius(3f, 3f))
        drawRoundRect(shoeWhite, topLeft = Offset(w * 0.56f, h * 0.86f), size = Size(16f, 7f), cornerRadius = CornerRadius(3f, 3f))
      }
    }
  }
}
