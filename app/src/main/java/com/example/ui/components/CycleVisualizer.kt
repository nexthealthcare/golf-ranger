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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.GolfCourse
import androidx.compose.material.icons.filled.SportsGolf
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EnergeticGold
import com.example.ui.theme.FairwayGreenPrimary
import com.example.ui.theme.TextMainDark
import com.example.ui.theme.TextMuted

@Composable
fun CycleVisualizer(
  modifier: Modifier = Modifier,
  activeStage: String = "ALL"
) {
  var selectedTab by remember { mutableStateOf(if (activeStage == "ALL") "BODY" else activeStage) }

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
        Text(
          text = "🎯 골프레인저 인과관계 사이클",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = TextMainDark
        )
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(FairwayGreenPrimary.copy(alpha = 0.1f))
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = "Body → Swing → Game",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = FairwayGreenPrimary
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        CycleStepNode(
          title = "바디 (신체)",
          badge = "브레이크",
          icon = Icons.Default.FitnessCenter,
          isSelected = selectedTab == "BODY",
          onClick = { selectedTab = "BODY" }
        )

        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(16.dp))

        CycleStepNode(
          title = "스윙 (기술)",
          badge = "보상동작",
          icon = Icons.Default.SportsGolf,
          isSelected = selectedTab == "SWING",
          onClick = { selectedTab = "SWING" }
        )

        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(16.dp))

        CycleStepNode(
          title = "게임 (결과)",
          badge = "타수낭비",
          icon = Icons.Default.GolfCourse,
          isSelected = selectedTab == "GAME",
          onClick = { selectedTab = "GAME" }
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      val tipText = when (selectedTab) {
        "BODY" -> "💡 관절이 굳어있으면 뇌가 몸을 보호하려 무의식적으로 스윙 궤도를 비틉니다."
        "SWING" -> "💡 부족한 유연성을 메우려 배치기나 엎어치기 등 보상 동작이 튀어나옵니다."
        "GAME" -> "💡 결국 긴 클럽이나 후반 홀에서 낭비타가 발생합니다. 원인을 잡아야 타수가 줄어듭니다!"
        else -> ""
      }

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
          .padding(10.dp)
      ) {
        Text(
          text = tipText,
          fontSize = 12.sp,
          color = TextMuted,
          lineHeight = 16.sp
        )
      }
    }
  }
}

@Composable
private fun CycleStepNode(
  title: String,
  badge: String,
  icon: ImageVector,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  val bgColor by animateColorAsState(
    if (isSelected) FairwayGreenPrimary else MaterialTheme.colorScheme.surfaceVariant,
    label = "bg"
  )
  val iconColor by animateColorAsState(
    if (isSelected) Color.White else TextMuted,
    label = "icon"
  )

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .clickable { onClick() }
      .padding(4.dp)
  ) {
    Box(
      modifier = Modifier
        .size(42.dp)
        .clip(CircleShape)
        .background(bgColor),
      contentAlignment = Alignment.Center
    ) {
      Icon(icon, contentDescription = title, tint = iconColor, modifier = Modifier.size(20.dp))
    }
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = title,
      fontSize = 12.sp,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
      color = if (isSelected) FairwayGreenPrimary else TextMainDark
    )
    Text(
      text = badge,
      fontSize = 10.sp,
      color = if (isSelected) EnergeticGold else TextMuted,
      fontWeight = FontWeight.Bold
    )
  }
}
