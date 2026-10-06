package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BodyExercise
import com.example.model.GolfDrill
import com.example.ui.components.ExecutionCoachDialog
import com.example.ui.components.GolfTopBar
import com.example.ui.theme.CleanWhiteBorder
import com.example.ui.theme.EnergeticGold
import com.example.ui.theme.FairwayGreenPrimary
import com.example.ui.theme.GolfFairwayCardSurface
import com.example.ui.theme.TagAmberBg
import com.example.ui.theme.TagAmberText
import com.example.ui.theme.TagMintBg
import com.example.ui.theme.TagMintText
import com.example.ui.theme.TextMainDark
import com.example.ui.theme.TextMuted
import com.example.viewmodel.GolfRangerViewModel
import com.example.viewmodel.GolfScreen

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
        subtitle = "음성 코칭과 함께 따라하기",
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
        .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
      // 상단 달성률 카드
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GolfFairwayCardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, CleanWhiteBorder)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "⚡ 오늘의 루틴 달성률",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = TextMainDark
            )
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(TagMintBg)
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "$completedCount / $totalTasks 완료",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                color = TagMintText
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
              .fillMaxWidth()
              .height(8.dp)
              .clip(RoundedCornerShape(4.dp)),
            color = FairwayGreenPrimary,
            trackColor = Color(0xFFE5E7EB)
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "“음성 코칭 버튼을 누르고 조교의 구령에 맞춰 따라 해보세요!”",
            fontSize = 12.sp,
            color = TextMuted
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = "💪 신체 모빌리티 회복 훈련",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = TextMainDark
      )

      Spacer(modifier = Modifier.height(6.dp))

      exercises.forEach { ex ->
        val isDone = completedExercises.contains(ex.title)
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (isDone) Color(0xFFE8F5E9) else GolfFairwayCardSurface
          ),
          border = androidx.compose.foundation.BorderStroke(1.dp, CleanWhiteBorder)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (isDone) FairwayGreenPrimary else Color(0xFFE5E7EB))
                .clickable { viewModel.toggleExerciseComplete(ex.title) },
              contentAlignment = Alignment.Center
            ) {
              if (isDone) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
              }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = ex.title,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDone) FairwayGreenPrimary else TextMainDark
              )
              Text(
                text = "📌 ${ex.leftRightDetail}",
                fontSize = 11.sp,
                color = TextMuted
              )
            }

            Button(
              onClick = { selectedExercise = ex },
              colors = ButtonDefaults.buttonColors(containerColor = FairwayGreenPrimary),
              shape = RoundedCornerShape(8.dp)
            ) {
              Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(3.dp))
              Text("실행", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = "🏌️ 골프 연습장 실전 드릴",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = TextMainDark
      )

      Spacer(modifier = Modifier.height(6.dp))

      drills.forEach { drill ->
        val isDone = completedExercises.contains(drill.title)
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (isDone) Color(0xFFE8F5E9) else GolfFairwayCardSurface
          ),
          border = androidx.compose.foundation.BorderStroke(1.dp, CleanWhiteBorder)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (isDone) FairwayGreenPrimary else Color(0xFFE5E7EB))
                .clickable { viewModel.toggleExerciseComplete(drill.title) },
              contentAlignment = Alignment.Center
            ) {
              if (isDone) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
              }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = drill.title,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDone) FairwayGreenPrimary else TextMainDark
              )
              Text(
                text = "🎯 ${drill.setAndReps}",
                fontSize = 11.sp,
                color = TextMuted
              )
            }

            Button(
              onClick = { selectedDrill = drill },
              colors = ButtonDefaults.buttonColors(containerColor = EnergeticGold),
              shape = RoundedCornerShape(8.dp)
            ) {
              Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(3.dp))
              Text("실행", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      OutlinedButton(
        onClick = { viewModel.navigateTo(GolfScreen.REPORT) },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
      ) {
        Text("AI 분석 리포트로 돌아가기", fontSize = 13.sp)
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }

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
