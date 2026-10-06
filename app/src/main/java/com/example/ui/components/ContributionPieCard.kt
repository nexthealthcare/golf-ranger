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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EnergeticCoral
import com.example.ui.theme.EnergeticGold
import com.example.ui.theme.FairwayGreenPrimary
import com.example.ui.theme.TagMintBg
import com.example.ui.theme.TagMintText
import com.example.ui.theme.TextMainDark
import com.example.ui.theme.TextMuted

@Composable
fun ContributionPieCard(
  bodyPct: Int,
  swingPct: Int,
  gamePct: Int,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "⚡ 원인 기여도 3분할",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextMainDark
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(TagMintBg)
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = "AI 코칭 가설 우선순위",
            fontSize = 10.5.sp,
            fontWeight = FontWeight.Bold,
            color = TagMintText
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 3색 분할 바 (초록 - 노랑 - 파랑)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(14.dp)
          .clip(RoundedCornerShape(7.dp))
      ) {
        val bodyWeight = bodyPct.coerceAtLeast(1).toFloat()
        val swingWeight = swingPct.coerceAtLeast(1).toFloat()
        val gameWeight = gamePct.coerceAtLeast(1).toFloat()

        Box(
          modifier = Modifier
            .weight(bodyWeight)
            .height(14.dp)
            .background(FairwayGreenPrimary)
        )
        Box(
          modifier = Modifier
            .weight(swingWeight)
            .height(14.dp)
            .background(EnergeticGold)
        )
        Box(
          modifier = Modifier
            .weight(gameWeight)
            .height(14.dp)
            .background(Color(0xFF0284C7)) // Sky blue
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 3분할 요약 칩
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        MetricChip(
          title = "바디 (신체)",
          pct = "$bodyPct%",
          sub = "가동성 잠김",
          color = FairwayGreenPrimary
        )
        MetricChip(
          title = "스윙 (기술)",
          pct = "$swingPct%",
          sub = "보상 동작",
          color = EnergeticGold
        )
        MetricChip(
          title = "게임 (클럽)",
          pct = "$gamePct%",
          sub = "선택 & 멘탈",
          color = Color(0xFF0284C7)
        )
      }
    }
  }
}

@Composable
private fun MetricChip(
  title: String,
  pct: String,
  sub: String,
  color: Color
) {
  Column(
    modifier = Modifier
      .clip(RoundedCornerShape(10.dp))
      .background(color.copy(alpha = 0.08f))
      .padding(horizontal = 10.dp, vertical = 8.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = title,
      fontSize = 11.sp,
      fontWeight = FontWeight.Medium,
      color = TextMuted
    )
    Text(
      text = pct,
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold,
      color = color
    )
    Text(
      text = sub,
      fontSize = 10.sp,
      color = TextMuted
    )
  }
}
