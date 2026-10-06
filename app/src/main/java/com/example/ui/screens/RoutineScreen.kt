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
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BodyExercise
import com.example.model.GolfDrill
import com.example.ui.components.ExecutionCoachDialog
import com.example.ui.components.GolfTopBar
import com.example.ui.theme.PerformanceGreenPrimary
import com.example.ui.theme.PerformanceGreenContainer
import com.example.ui.theme.StatusPass
import com.example.ui.theme.StatusPassBg
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceCardSecondary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.viewmodel.GolfRangerViewModel
import com.example.viewmodel.GolfScreen

/**
 * 2026 Professional Routine Screen (Garmin / WHOOP / Apple Health aesthetic)
 * Clean, restrained, zero emojis, precise checkboxes and audio coaching integration.
 */
@Composable
fun RoutineScreen(
  viewModel: GolfRangerViewModel,
  modifier: Modifier = Modifier
) {
  val report by viewModel.analysisReport.collectAsState()
  val completedExercises by viewModel.completedExercises.collectAsState()

  var selectedExercise by remember { mutableStateOf<BodyExercise?>(null) }
  var selectedDrill by remember { mutableStateOf<GolfDrill?>(null) }

  val exercises = report?.exercises ?: emptyList()
  val drills = report?.drills ?: emptyList()
  val totalTasks = exercises.size + drills.size
  val completedCount = completedExercises.size
  val progress = if (totalTasks > 0) completedCount.toFloat() / totalTasks.toFloat() else 0f

  Scaffold(
    topBar = {
      GolfTopBar(
        title = "오늘의 실천 루틴",
        subtitle = "처방 모빌리티 및 실전 드릴 트레이닝",
        canNavigateBack = true,
        onBackClick = { viewModel.navigateTo(GolfScreen.HOME) }
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
      // 1. 오늘의 루틴 달성 현황 카드 (WHOOP / Garmin Style)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "오늘의 루틴 달성률",
              fontSize = 14.sp,
              fontWeight = FontWeight.SemiBold,
              color = TextPrimary
            )
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(if (completedCount == totalTasks && totalTasks > 0) StatusPassBg else Color(0xFFF1F3F5))
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "$completedCount / $totalTasks 완료",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (completedCount == totalTasks && totalTasks > 0) StatusPass else TextSecondary
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
              .fillMaxWidth()
              .height(6.dp)
              .clip(RoundedCornerShape(3.dp)),
            color = PerformanceGreenPrimary,
            trackColor = SurfaceBorder
          )

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "조교의 음성 가이드와 안내에 맞춰 세트별로 따라 해보세요.",
            fontSize = 12.sp,
            color = TextTertiary
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 2. 신체 모빌리티 회복 훈련 섹션
      Text(
        text = "신체 모빌리티 회복 훈련",
        fontSize = 14.5.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(8.dp))

      exercises.forEach { ex ->
        val isDone = completedExercises.contains(ex.title)
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
          shape = RoundedCornerShape(8.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = androidx.compose.foundation.BorderStroke(1.dp, if (isDone) PerformanceGreenPrimary else SurfaceBorder),
          elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Checkbox indicator
            Box(
              modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(1.dp, if (isDone) PerformanceGreenPrimary else SurfaceBorder, RoundedCornerShape(4.dp))
                .background(if (isDone) PerformanceGreenPrimary else Color.White)
                .clickable { viewModel.toggleExerciseComplete(ex.title) },
              contentAlignment = Alignment.Center
            ) {
              if (isDone) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
              }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = ex.title,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isDone) PerformanceGreenPrimary else TextPrimary
              )
              Text(
                text = ex.leftRightDetail,
                fontSize = 11.5.sp,
                color = TextTertiary
              )
            }

            Button(
              onClick = { selectedExercise = ex },
              shape = RoundedCornerShape(6.dp),
              colors = ButtonDefaults.buttonColors(containerColor = PerformanceGreenPrimary),
              modifier = Modifier.height(34.dp)
            ) {
              Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(13.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("실행", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 3. 골프 실전 드릴 섹션
      Text(
        text = "골프 연습장 실전 드릴",
        fontSize = 14.5.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(8.dp))

      drills.forEach { drill ->
        val isDone = completedExercises.contains(drill.title)
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
          shape = RoundedCornerShape(8.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = androidx.compose.foundation.BorderStroke(1.dp, if (isDone) PerformanceGreenPrimary else SurfaceBorder),
          elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(1.dp, if (isDone) PerformanceGreenPrimary else SurfaceBorder, RoundedCornerShape(4.dp))
                .background(if (isDone) PerformanceGreenPrimary else Color.White)
                .clickable { viewModel.toggleExerciseComplete(drill.title) },
              contentAlignment = Alignment.Center
            ) {
              if (isDone) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
              }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = drill.title,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isDone) PerformanceGreenPrimary else TextPrimary
              )
              Text(
                text = "${drill.recommendedClub} · ${drill.setAndReps}",
                fontSize = 11.5.sp,
                color = TextTertiary
              )
            }

            Button(
              onClick = { selectedDrill = drill },
              shape = RoundedCornerShape(6.dp),
              colors = ButtonDefaults.buttonColors(containerColor = PerformanceGreenPrimary),
              modifier = Modifier.height(34.dp)
            ) {
              Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(13.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("실행", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      OutlinedButton(
        onClick = { viewModel.navigateTo(GolfScreen.REPORT) },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(46.dp)
          .testTag("back_to_report_button")
      ) {
        Text("종합 분석 리포트로 이동", fontSize = 13.sp, color = TextPrimary)
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }

  // 모빌리티 운동 코칭 다이얼로그
  selectedExercise?.let { ex ->
    ExecutionCoachDialog(
      title = ex.title,
      targetOrClub = "타겟: ${ex.targetArea}",
      setsAndReps = ex.leftRightDetail,
      coachingKey = ex.coachingKey,
      voiceScript = ex.voiceCoachScript,
      visualType = ex.technicalId,
      instructions = ex.instructions,
      onDismiss = { selectedExercise = null },
      onCompleted = { viewModel.toggleExerciseComplete(ex.title) }
    )
  }

  // 드릴 코칭 다이얼로그
  selectedDrill?.let { drill ->
    ExecutionCoachDialog(
      title = drill.title,
      targetOrClub = "추천 클럽: ${drill.recommendedClub}",
      setsAndReps = drill.setAndReps,
      coachingKey = drill.feelVsReal,
      voiceScript = drill.voiceCoachScript,
      visualType = drill.technicalId,
      instructions = drill.howToPractice,
      onDismiss = { selectedDrill = null },
      onCompleted = { viewModel.toggleExerciseComplete(drill.title) }
    )
  }
}
