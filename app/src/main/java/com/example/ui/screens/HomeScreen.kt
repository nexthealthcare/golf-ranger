package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ScreeningGrade
import com.example.ui.components.CycleVisualizer
import com.example.ui.components.GolfTopBar
import com.example.ui.theme.*
import com.example.viewmodel.GolfRangerViewModel
import com.example.viewmodel.GolfScreen

/**
 * 2026 Performance Athletic Home Screen (Garmin / Apple Health / WHOOP style)
 * Clean, restrained, professional sports biomechanics dashboard without cartoon visuals or emojis.
 */
@Composable
fun HomeScreen(
  viewModel: GolfRangerViewModel,
  modifier: Modifier = Modifier
) {
  val profile by viewModel.golferProfile.collectAsState()
  val screeningResults by viewModel.screeningResults.collectAsState()
  val report by viewModel.analysisReport.collectAsState()
  val completedExercises by viewModel.completedExercises.collectAsState()

  val restrictedCount = screeningResults.count { it.value == ScreeningGrade.RESTRICTED }
  val cautionCount = screeningResults.count { it.value == ScreeningGrade.LIMITED }
  val bodyType = report?.bodyType

  Scaffold(
    topBar = {
      GolfTopBar(
        title = "골프레인저",
        subtitle = "골프조교의 바디-스윙 진단 & MBTI 솔루션"
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
        .padding(horizontal = 18.dp, vertical = 14.dp)
    ) {
      val mbti = report?.mbti

      // 1. 헤더 진단 현황 메트릭 카드
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = GolfFairwayCardSurface),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, FairwayGreenPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(PerformanceGreenContainer)
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "GOLF BODY MBTI",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = PerformanceGreenPrimary
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xFFF1F3F5))
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "재검진 D-14",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = mbti?.animalEmoji ?: "🐯", fontSize = 36.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "${mbti?.name ?: "배치기 타이거"} (${mbti?.code ?: "BSE-T"})",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
              Text(
                text = "“${mbti?.tagline ?: "폭발적인 파워, 그러나 임팩트 때 먼저 일어서는 호랑이!"}”",
                fontSize = 12.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = PerformanceGreenPrimary
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // 3-Part Health Metric Bar (평균 타수 / 관절 가동성 제한 수 / 예상 타수 절감치)
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(6.dp))
              .background(SurfaceCardSecondary)
              .padding(vertical = 10.dp, horizontal = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            MetricBlock(label = "평균 핸디", value = "${profile.handicap}", unit = "Handicap")
            MetricBlock(
              label = "가동성 제한",
              value = "${restrictedCount + cautionCount}",
              unit = "개 항목",
              valueColor = if (restrictedCount > 0) StatusRestricted else PerformanceGreenPrimary
            )
            MetricBlock(
              label = "예상 타수 절감",
              value = report?.estimatedStrokesSaved ?: "-3~5",
              unit = "타",
              valueColor = PerformanceGreenPrimary
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 메인 CTA: 정밀 진단 시작하기 버튼
      Button(
        onClick = { viewModel.navigateTo(GolfScreen.SURVEY) },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PerformanceGreenPrimary),
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("start_diagnosis_button")
      ) {
        Text("정밀 설문 및 신체검진 시작", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.width(6.dp))
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 2. Body → Swing → Game 인과관계 사이클
      CycleVisualizer(activeStage = "ALL")

      Spacer(modifier = Modifier.height(16.dp))

      // 3. 전문 메뉴 그리드 (절제된 리스트 형태)
      Text(
        text = "퍼포먼스 진단 및 트레이닝",
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(8.dp))

      PerformanceMenuRow(
        title = "🏆 AI 종합 분석 & 골프 MBTI 처방전",
        subtitle = "신체/기술/게임 3분할 기여도 및 인과관계 진단서",
        tag = "report_action_card",
        onClick = {
          viewModel.refreshAnalysis()
          viewModel.navigateTo(GolfScreen.REPORT)
        }
      )

      Spacer(modifier = Modifier.height(8.dp))

      PerformanceMenuRow(
        title = "🩺 SFMA 5대 핵심 신체검진",
        subtitle = "골퍼 동작 사진 가이드 & 5가지 스윙 제한점 예측",
        tag = "screening_action_card",
        onClick = { viewModel.navigateTo(GolfScreen.SCREENING) }
      )

      Spacer(modifier = Modifier.height(8.dp))

      PerformanceMenuRow(
        title = "💪 처방 모빌리티 및 실전 드릴 루틴",
        subtitle = "좌우 세트 가이드 및 음성 실행 트레이닝 (${completedExercises.size}개 완료)",
        tag = "routine_action_card",
        onClick = { viewModel.navigateTo(GolfScreen.ROUTINE) }
      )

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
private fun MetricBlock(
  label: String,
  value: String,
  unit: String,
  valueColor: Color = TextPrimary
) {
  Column(horizontalAlignment = Alignment.Start) {
    Text(text = label, fontSize = 11.sp, color = TextTertiary)
    Spacer(modifier = Modifier.height(2.dp))
    Row(verticalAlignment = Alignment.Bottom) {
      Text(
        text = value,
        fontSize = 17.sp,
        fontWeight = FontWeight.SemiBold,
        color = valueColor
      )
      Spacer(modifier = Modifier.width(2.dp))
      Text(
        text = unit,
        fontSize = 11.sp,
        color = TextSecondary,
        modifier = Modifier.padding(bottom = 2.dp)
      )
    }
  }
}

@Composable
private fun PerformanceMenuRow(
  title: String,
  subtitle: String,
  tag: String,
  onClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .testTag(tag),
    shape = RoundedCornerShape(8.dp),
    colors = CardDefaults.cardColors(containerColor = GolfFairwayCardSurface),
    border = androidx.compose.foundation.BorderStroke(1.dp, GolfGrassBorder),
    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 14.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          fontSize = 14.sp,
          fontWeight = FontWeight.SemiBold,
          color = TextPrimary
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = subtitle,
          fontSize = 12.sp,
          color = TextSecondary
        )
      }

      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
        contentDescription = null,
        tint = TextTertiary,
        modifier = Modifier.size(16.dp)
      )
    }
  }
}
