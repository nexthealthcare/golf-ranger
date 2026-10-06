package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PerformanceGreenPrimary
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

/**
 * Clean Analytical Factor Distribution Card (Apple Health / Garmin style)
 * Emphasizes data clarity and high contrast without emojis or excessive gradients.
 */
@Composable
fun ContributionPieCard(
  bodyPct: Int,
  swingPct: Int,
  gamePct: Int,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
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
        Column {
          Text(
            text = "원인 기여도 분석",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
          )
          Text(
            text = "신체 역학 및 스윙 데이터 분석 가설",
            fontSize = 12.sp,
            color = TextTertiary
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .border(1.dp, SurfaceBorder, RoundedCornerShape(4.dp))
            .background(Color(0xFFF8F9FA))
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = "우선순위 모델",
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = TextSecondary
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Segmented metric bar (Clean, non-flashy palette)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(10.dp)
          .clip(RoundedCornerShape(4.dp))
      ) {
        val bodyWeight = bodyPct.coerceAtLeast(1).toFloat()
        val swingWeight = swingPct.coerceAtLeast(1).toFloat()
        val gameWeight = gamePct.coerceAtLeast(1).toFloat()

        Box(
          modifier = Modifier
            .weight(bodyWeight)
            .height(10.dp)
            .background(PerformanceGreenPrimary)
        )
        Box(
          modifier = Modifier
            .weight(swingWeight)
            .height(10.dp)
            .background(Color(0xFF3B82F6)) // High-contrast clean cobalt blue
        )
        Box(
          modifier = Modifier
            .weight(gameWeight)
            .height(10.dp)
            .background(Color(0xFF64748B)) // Slate gray
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Analytical data breakdown row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        DataCell(
          category = "신체 요인",
          percent = "$bodyPct%",
          description = "관절 가동성 & 안정성",
          indicatorColor = PerformanceGreenPrimary
        )

        DataCell(
          category = "스윙 메커니즘",
          percent = "$swingPct%",
          description = "보상 궤도 & 릴리스",
          indicatorColor = Color(0xFF3B82F6)
        )

        DataCell(
          category = "게임 매니지먼트",
          percent = "$gamePct%",
          description = "클럽 선택 & 멘탈",
          indicatorColor = Color(0xFF64748B)
        )
      }
    }
  }
}

@Composable
private fun DataCell(
  category: String,
  percent: String,
  description: String,
  indicatorColor: Color
) {
  Column(
    modifier = Modifier.padding(2.dp)
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Box(
        modifier = Modifier
          .size(6.dp)
          .clip(RoundedCornerShape(1.dp))
          .background(indicatorColor)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = category,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = TextSecondary
      )
    }

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = percent,
      fontSize = 20.sp,
      fontWeight = FontWeight.SemiBold,
      color = TextPrimary
    )

    Text(
      text = description,
      fontSize = 11.sp,
      color = TextTertiary
    )
  }
}
