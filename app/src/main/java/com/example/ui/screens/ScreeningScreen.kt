package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ScreeningDataSource
import com.example.model.ScreeningGrade
import com.example.ui.components.GolfTopBar
import com.example.ui.components.ScreeningPoseVisualizer
import com.example.ui.theme.PerformanceGreenPrimary
import com.example.ui.theme.PerformanceGreenContainer
import com.example.ui.theme.StatusCaution
import com.example.ui.theme.StatusCautionBg
import com.example.ui.theme.StatusPass
import com.example.ui.theme.StatusPassBg
import com.example.ui.theme.StatusRestricted
import com.example.ui.theme.StatusRestrictedBg
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceCardSecondary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.util.TtsManager
import com.example.viewmodel.GolfRangerViewModel
import com.example.viewmodel.GolfScreen

/**
 * Professional Athletic Screening Protocol Screen (Clinical Biomechanics)
 * Free of emojis, cartoon drawings, or flashy elements.
 */
@Composable
fun ScreeningScreen(
  viewModel: GolfRangerViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val ttsManager = remember { TtsManager(context) }
  var isSpeaking by remember { mutableStateOf(false) }

  DisposableEffect(Unit) {
    onDispose {
      ttsManager.shutdown()
    }
  }

  val currentIndex by viewModel.currentScreeningIndex.collectAsState()
  val screeningResults by viewModel.screeningResults.collectAsState()
  val items = ScreeningDataSource.screeningItems
  val currentItem = items[currentIndex]
  val currentGrade = screeningResults[currentItem.id] ?: ScreeningGrade.PASS

  val progress = (currentIndex + 1).toFloat() / items.size.toFloat()

  Scaffold(
    topBar = {
      GolfTopBar(
        title = "신체검진 프로토콜 (${currentIndex + 1}/${items.size})",
        subtitle = "기능 해부학적 가동성 및 조절력 측정",
        canNavigateBack = true,
        onBackClick = {
          ttsManager.stop()
          viewModel.navigateTo(GolfScreen.SURVEY)
        }
      )
    },
    containerColor = MaterialTheme.colorScheme.background,
    modifier = modifier.fillMaxSize()
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
      // 상단 인덱스 및 카테고리
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "검진 항목 ${currentIndex + 1} / ${items.size}",
          fontSize = 13.sp,
          fontWeight = FontWeight.SemiBold,
          color = PerformanceGreenPrimary
        )

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .border(1.dp, SurfaceBorder, RoundedCornerShape(4.dp))
            .background(Color(0xFFF8F9FA))
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = currentItem.category,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Medium,
            color = TextSecondary
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Linear progress bar
      LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
          .fillMaxWidth()
          .height(4.dp)
          .clip(RoundedCornerShape(2.dp)),
        color = PerformanceGreenPrimary,
        trackColor = SurfaceBorder
      )

      Spacer(modifier = Modifier.height(14.dp))

      // 1~13 가로 프로토콜 인덱스 탭 (깔끔한 사각 탭)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        items.forEachIndexed { idx, item ->
          val grade = screeningResults[item.id]
          val isCurrent = idx == currentIndex

          val tabBorder = if (isCurrent) PerformanceGreenPrimary else SurfaceBorder
          val tabBg = when {
            isCurrent -> PerformanceGreenContainer
            grade == ScreeningGrade.PASS -> Color(0xFFF8FAF9)
            grade == ScreeningGrade.RESTRICTED -> Color(0xFFFEF2F2)
            else -> Color.White
          }
          val tabTextColor = if (isCurrent) PerformanceGreenPrimary else TextSecondary

          Box(
            modifier = Modifier
              .size(34.dp)
              .clip(RoundedCornerShape(6.dp))
              .border(1.dp, tabBorder, RoundedCornerShape(6.dp))
              .background(tabBg)
              .clickable {
                ttsManager.stop()
                isSpeaking = false
                viewModel.setScreeningIndex(idx)
              },
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "${idx + 1}",
              fontSize = 12.sp,
              fontWeight = if (isCurrent) FontWeight.SemiBold else FontWeight.Normal,
              color = tabTextColor
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 메인 검사 카드 (키네틱 스키매틱 & 기술 스펙)
      Card(
        modifier = Modifier.fillMaxWidth(),
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
          Text(
            text = "${currentIndex + 1}. ${currentItem.title}",
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
          )
          Text(
            text = currentItem.englishSubtitle,
            fontSize = 12.sp,
            color = TextTertiary
          )

          Spacer(modifier = Modifier.height(12.dp))

          // Biomechanical Technical Schematic (No cartoon)
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(120.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(SurfaceCardSecondary),
            contentAlignment = Alignment.Center
          ) {
            ScreeningPoseVisualizer(
              iconType = currentItem.technicalId,
              modifier = Modifier.size(170.dp, 110.dp)
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          // 음성 가이드 버튼 (Outlined, clean)
          OutlinedButton(
            onClick = {
              if (isSpeaking) {
                ttsManager.stop()
                isSpeaking = false
              } else {
                ttsManager.speak(currentItem.voiceGuideText)
                isSpeaking = true
              }
            },
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(40.dp)
              .testTag("tts_guide_button")
          ) {
            Icon(
              imageVector = if (isSpeaking) Icons.Default.Stop else Icons.AutoMirrored.Filled.VolumeUp,
              contentDescription = null,
              tint = TextPrimary,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (isSpeaking) "음성 가이드 일시중지" else "측정 방법 전문 음성 안내",
              fontSize = 12.5.sp,
              fontWeight = FontWeight.Medium,
              color = TextPrimary
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 판정 기준 및 스윙 영향 박스 (Clean, informative)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "정상 판정 기준",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = PerformanceGreenPrimary
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = currentItem.passCriteria,
            fontSize = 13.5.sp,
            color = TextPrimary,
            lineHeight = 19.sp
          )

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "가동성 결핍 시 스윙 영향",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFB91C1C)
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = currentItem.swingImpact,
            fontSize = 13.sp,
            color = TextSecondary,
            lineHeight = 18.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 3단계 임상 등급 선택 (이모지 없이 전문 라벨 적용)
      Text(
        text = "자가 평가 결과 입력",
        fontSize = 13.5.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        AssessmentGradeButton(
          label = "정상",
          sub = "Pass",
          isSelected = currentGrade == ScreeningGrade.PASS,
          activeColor = PerformanceGreenPrimary,
          modifier = Modifier.weight(1f),
          onClick = { viewModel.setScreeningGrade(currentItem.id, ScreeningGrade.PASS) }
        )

        AssessmentGradeButton(
          label = "주의",
          sub = "Borderline",
          isSelected = currentGrade == ScreeningGrade.LIMITED,
          activeColor = StatusCaution,
          modifier = Modifier.weight(1f),
          onClick = { viewModel.setScreeningGrade(currentItem.id, ScreeningGrade.LIMITED) }
        )

        AssessmentGradeButton(
          label = "제한",
          sub = "Restricted",
          isSelected = currentGrade == ScreeningGrade.RESTRICTED,
          activeColor = StatusRestricted,
          modifier = Modifier.weight(1f),
          onClick = { viewModel.setScreeningGrade(currentItem.id, ScreeningGrade.RESTRICTED) }
        )
      }

      Spacer(modifier = Modifier.height(18.dp))

      // 이전 / 다음 네비게이션 버튼
      Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
          onClick = {
            ttsManager.stop()
            isSpeaking = false
            viewModel.prevScreening()
          },
          enabled = currentIndex > 0,
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .weight(1f)
            .height(46.dp)
            .testTag("prev_screening_button")
        ) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("이전", fontSize = 13.sp, color = TextPrimary)
        }

        Spacer(modifier = Modifier.width(10.dp))

        Button(
          onClick = {
            ttsManager.stop()
            isSpeaking = false
            viewModel.nextScreening()
          },
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(containerColor = PerformanceGreenPrimary),
          modifier = Modifier
            .weight(1.4f)
            .height(46.dp)
            .testTag("next_screening_button")
        ) {
          Text(
            text = if (currentIndex < items.size - 1) "다음 (${currentIndex + 2}/${items.size})" else "종합 리포트 생성",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
          )
          Spacer(modifier = Modifier.width(4.dp))
          Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
        }
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
private fun AssessmentGradeButton(
  label: String,
  sub: String,
  isSelected: Boolean,
  activeColor: Color,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(8.dp))
      .border(
        width = if (isSelected) 1.5.dp else 1.dp,
        color = if (isSelected) activeColor else SurfaceBorder,
        shape = RoundedCornerShape(8.dp)
      )
      .background(if (isSelected) activeColor.copy(alpha = 0.08f) else Color.White)
      .clickable { onClick() }
      .padding(vertical = 12.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = label,
        fontSize = 13.5.sp,
        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
        color = if (isSelected) activeColor else TextPrimary
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = sub,
        fontSize = 11.sp,
        color = TextTertiary
      )
    }
  }
}
