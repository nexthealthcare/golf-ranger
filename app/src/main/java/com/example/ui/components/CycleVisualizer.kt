package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GolfFairwayCardSurface
import com.example.ui.theme.PerformanceGreenPrimary
import com.example.ui.theme.PerformanceGreenContainer
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceCardSecondary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

/**
 * Professional Performance Cycle (Body -> Swing -> Game)
 * Formatted like a sports biomechanics pipeline (Garmin / WHOOP style).
 */
@Composable
fun CycleVisualizer(
  modifier: Modifier = Modifier,
  activeStage: String = "ALL"
) {
  var selectedTab by remember { mutableStateOf(if (activeStage == "ALL") "BODY" else activeStage) }

  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(containerColor = GolfFairwayCardSurface),
    border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder),
    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "바디-스윙-게임 인과관계 분석",
          fontSize = 15.sp,
          fontWeight = FontWeight.SemiBold,
          color = TextPrimary
        )

        Text(
          text = "Body → Swing → Game",
          fontSize = 11.5.sp,
          fontWeight = FontWeight.Medium,
          color = PerformanceGreenPrimary
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 3-Stage Pipeline (Clean, high-legibility blocks)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        PipelineStep(
          stepNumber = "01",
          title = "신체 가동성",
          subtitle = "원인 발생",
          isSelected = selectedTab == "BODY",
          onClick = { selectedTab = "BODY" }
        )

        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowForward,
          contentDescription = null,
          tint = TextTertiary.copy(alpha = 0.4f),
          modifier = Modifier.size(14.dp)
        )

        PipelineStep(
          stepNumber = "02",
          title = "스윙 메커니즘",
          subtitle = "보상 동작",
          isSelected = selectedTab == "SWING",
          onClick = { selectedTab = "SWING" }
        )

        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowForward,
          contentDescription = null,
          tint = TextTertiary.copy(alpha = 0.4f),
          modifier = Modifier.size(14.dp)
        )

        PipelineStep(
          stepNumber = "03",
          title = "실전 스코어",
          subtitle = "타수 손실",
          isSelected = selectedTab == "GAME",
          onClick = { selectedTab = "GAME" }
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      val detailText = when (selectedTab) {
        "BODY" -> "관절의 회전 가동성과 정적 밸런스가 결핍되면, 뇌는 신체를 보호하기 위해 본능적으로 스윙 궤도를 변형시킵니다. 신체 축을 바로잡는 것이 가장 빠른 타수 교정의 출발점입니다."
        "SWING" -> "잠긴 관절 가동 범위를 보상하기 위해 임팩트 시 상체를 일으켜 세우거나(얼리 익스텐션), 상체로 덮어치는(오버 더 탑) 인위적인 보상 동작이 발생합니다."
        "GAME" -> "스윙 폼의 보상 동작은 실전 필드의 긴 클럽(드라이버/롱아이언)이나 후반 홀 체력 감쇄 시 타점 오차(OB/뒷땅)로 이어져 홀당 1~2타의 불필요한 누수를 발생시킵니다."
        else -> ""
      }

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(SurfaceCardSecondary)
          .padding(12.dp)
      ) {
        Text(
          text = detailText,
          fontSize = 13.sp,
          color = TextSecondary,
          lineHeight = 19.sp
        )
      }
    }
  }
}

@Composable
private fun PipelineStep(
  stepNumber: String,
  title: String,
  subtitle: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  val borderCol = if (isSelected) PerformanceGreenPrimary else SurfaceBorder
  val bgCol = if (isSelected) PerformanceGreenContainer else Color.White

  Column(
    modifier = Modifier
      .clip(RoundedCornerShape(8.dp))
      .border(1.dp, borderCol, RoundedCornerShape(8.dp))
      .background(bgCol)
      .clickable { onClick() }
      .padding(horizontal = 12.dp, vertical = 10.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = stepNumber,
      fontSize = 11.sp,
      fontWeight = FontWeight.Bold,
      color = if (isSelected) PerformanceGreenPrimary else TextTertiary
    )
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = title,
      fontSize = 13.sp,
      fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
      color = TextPrimary
    )
    Text(
      text = subtitle,
      fontSize = 11.sp,
      color = TextTertiary
    )
  }
}
