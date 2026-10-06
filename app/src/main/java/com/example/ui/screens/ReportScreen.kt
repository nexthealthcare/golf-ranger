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
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.example.ui.components.ContributionPieCard
import com.example.ui.components.ExecutionCoachDialog
import com.example.ui.components.GolfTopBar
import com.example.ui.theme.PerformanceGreenPrimary
import com.example.ui.theme.PerformanceGreenContainer
import com.example.ui.theme.StatusCaution
import com.example.ui.theme.StatusCautionBg
import com.example.ui.theme.StatusPass
import com.example.ui.theme.StatusPassBg
import com.example.ui.theme.StatusRestricted
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceCardSecondary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.viewmodel.GolfRangerViewModel
import com.example.viewmodel.GolfScreen

/**
 * 2026 Professional Golf Performance & Biomechanics Diagnostic Report
 * Modeled after WHOOP, Garmin, and Apple Health.
 * Restrained UI, strictly zero emojis, clear typographic hierarchy.
 */
@Composable
fun ReportScreen(
  viewModel: GolfRangerViewModel,
  modifier: Modifier = Modifier
) {
  val report by viewModel.analysisReport.collectAsState()
  val completedExercises by viewModel.completedExercises.collectAsState()

  var selectedExercise by remember { mutableStateOf<BodyExercise?>(null) }
  var selectedDrill by remember { mutableStateOf<GolfDrill?>(null) }

  Scaffold(
    topBar = {
      GolfTopBar(
        title = "신체-스윙 종합 진단 리포트",
        subtitle = "생체역학적 기능 결손 및 맞춤 처방 솔루션",
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
      if (report == null) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
          contentAlignment = Alignment.Center
        ) {
          Text("진단 데이터를 분석하고 있습니다...", color = TextSecondary, fontSize = 14.sp)
        }
        return@Column
      }

      val r = report!!
      val bodyType = r.bodyType

      // 1. 골퍼 신체 기능 분석 유형 분류 헤더 카드
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
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(PerformanceGreenContainer)
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = bodyType.code,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = PerformanceGreenPrimary
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFF1F3F5))
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "재검진 주기 4주",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = bodyType.name,
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
          )

          Text(
            text = bodyType.categoryTitle,
            fontSize = 13.sp,
            color = TextSecondary
          )

          Spacer(modifier = Modifier.height(14.dp))

          // 3-Part Metric Row
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(6.dp))
              .background(SurfaceCardSecondary)
              .padding(vertical = 10.dp, horizontal = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column {
              Text("예상 타수 절감", fontSize = 11.sp, color = TextTertiary)
              Spacer(modifier = Modifier.height(2.dp))
              Text(r.estimatedStrokesSaved, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = PerformanceGreenPrimary)
            }
            Column {
              Text("모빌리티 운동", fontSize = 11.sp, color = TextTertiary)
              Spacer(modifier = Modifier.height(2.dp))
              Text("${r.exercises.size}개 처방", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            }
            Column {
              Text("실전 드릴", fontSize = 11.sp, color = TextTertiary)
              Spacer(modifier = Modifier.height(2.dp))
              Text("${r.drills.size}개 처방", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = bodyType.summaryText,
            fontSize = 13.sp,
            color = TextSecondary,
            lineHeight = 19.sp
          )

          Spacer(modifier = Modifier.height(12.dp))

          DiagnosticBullet(label = "신체 원인", content = bodyType.bodyCause)
          Spacer(modifier = Modifier.height(6.dp))
          DiagnosticBullet(label = "교정 전략", content = bodyType.correctiveStrategy)
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 2. 바디 → 스윙 → 게임 3단계 인과관계 체인 카드
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Text(
            text = "바디-스윙-게임 인과관계 메커니즘",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
          )
          Text(
            text = "신체 기능 결손이 실전 스윙 및 스코어로 전이되는 기전",
            fontSize = 12.sp,
            color = TextTertiary
          )

          Spacer(modifier = Modifier.height(14.dp))

          ChainStepRow(step = "01 신체", title = r.primaryBodyLimitation, stepColor = PerformanceGreenPrimary)
          Spacer(modifier = Modifier.height(8.dp))
          ChainStepRow(step = "02 스윙", title = "보상 기전: ${r.primarySwingCompensation}", stepColor = Color(0xFF2563EB))
          Spacer(modifier = Modifier.height(8.dp))
          ChainStepRow(step = "03 게임", title = "실전 누수: ${r.primaryGameMistake}", stepColor = StatusRestricted)

          Spacer(modifier = Modifier.height(12.dp))

          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(6.dp))
              .background(SurfaceCardSecondary)
              .padding(12.dp)
          ) {
            Text(
              text = r.chainExplanation,
              fontSize = 12.5.sp,
              color = TextSecondary,
              lineHeight = 18.sp
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 3. 기여도 3분할 카드
      ContributionPieCard(
        bodyPct = r.bodyContribution,
        swingPct = r.swingContribution,
        gamePct = r.gameContribution
      )

      Spacer(modifier = Modifier.height(18.dp))

      // 4. 개인 맞춤 모빌리티 운동 처방 (3~5가지)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "처방 신체 모빌리티 훈련",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
          )
          Text(
            text = "${r.exercises.size}가지 맞춤 회복 운동",
            fontSize = 12.sp,
            color = TextTertiary
          )
        }

        Text(
          text = "좌우 세트 가이드",
          fontSize = 12.sp,
          fontWeight = FontWeight.Medium,
          color = PerformanceGreenPrimary
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      r.exercises.forEachIndexed { idx, ex ->
        val isDone = completedExercises.contains(ex.title)
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          shape = RoundedCornerShape(8.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = androidx.compose.foundation.BorderStroke(1.dp, if (isDone) PerformanceGreenPrimary else SurfaceBorder),
          elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "${idx + 1}. ${ex.title}",
                    fontSize = 14.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                  )
                  if (isDone) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                      modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .background(StatusPassBg)
                        .padding(horizontal = 5.dp, vertical = 2.dp)
                    ) {
                      Text("완료", fontSize = 10.5.sp, color = StatusPass, fontWeight = FontWeight.SemiBold)
                    }
                  }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = "타겟 부위: ${ex.targetArea}",
                  fontSize = 12.sp,
                  color = TextTertiary
                )
              }

              Button(
                onClick = { selectedExercise = ex },
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PerformanceGreenPrimary),
                modifier = Modifier
                  .height(34.dp)
                  .testTag("action_exercise_${idx}")
              ) {
                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("실행", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(SurfaceCardSecondary)
                .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
              Text(
                text = ex.leftRightDetail,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary
              )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
              text = "체크포인트: ${ex.coachingKey}",
              fontSize = 12.sp,
              color = PerformanceGreenPrimary,
              lineHeight = 16.sp
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // 5. 골프 연습장 원포인트 드릴 처방 (2~3가지)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "처방 골프 실전 드릴",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
          )
          Text(
            text = "${r.drills.size}가지 필드 교정 루틴",
            fontSize = 12.sp,
            color = TextTertiary
          )
        }

        Text(
          text = "클럽 권장 사양",
          fontSize = 12.sp,
          fontWeight = FontWeight.Medium,
          color = TextSecondary
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      r.drills.forEachIndexed { idx, drill ->
        val isDone = completedExercises.contains(drill.title)
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          shape = RoundedCornerShape(8.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = androidx.compose.foundation.BorderStroke(1.dp, if (isDone) PerformanceGreenPrimary else SurfaceBorder),
          elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "${idx + 1}. ${drill.title}",
                    fontSize = 14.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                  )
                  if (isDone) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                      modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .background(StatusPassBg)
                        .padding(horizontal = 5.dp, vertical = 2.dp)
                    ) {
                      Text("완료", fontSize = 10.5.sp, color = StatusPass, fontWeight = FontWeight.SemiBold)
                    }
                  }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = "권장 클럽: ${drill.recommendedClub}",
                  fontSize = 12.sp,
                  color = TextTertiary
                )
              }

              Button(
                onClick = { selectedDrill = drill },
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PerformanceGreenPrimary),
                modifier = Modifier
                  .height(34.dp)
                  .testTag("action_drill_${idx}")
              ) {
                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("실행", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(SurfaceCardSecondary)
                .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
              Text(
                text = drill.setAndReps,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary
              )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
              text = drill.feelVsReal,
              fontSize = 12.sp,
              color = PerformanceGreenPrimary,
              lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "체크: ${drill.checkpoint}",
              fontSize = 11.5.sp,
              color = TextTertiary
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // 메인 CTA
      Button(
        onClick = { viewModel.navigateTo(GolfScreen.ROUTINE) },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PerformanceGreenPrimary),
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("goto_routine_button")
      ) {
        Text("오늘의 실천 루틴으로 이동", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.width(6.dp))
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
      }

      Spacer(modifier = Modifier.height(8.dp))

      Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
          onClick = { viewModel.resetForRetest() },
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .weight(1f)
            .height(44.dp)
        ) {
          Icon(Icons.Default.Refresh, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("신체 재검진", fontSize = 12.5.sp, color = TextPrimary)
        }

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedButton(
          onClick = { viewModel.navigateTo(GolfScreen.HOME) },
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .weight(1f)
            .height(44.dp)
        ) {
          Text("홈 대시보드", fontSize = 12.5.sp, color = TextPrimary)
        }
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }

  // 운동 코칭 다이얼로그
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

@Composable
private fun DiagnosticBullet(label: String, content: String) {
  Row(verticalAlignment = Alignment.Top) {
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(3.dp))
        .background(Color(0xFFE5E7EB))
        .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
      Text(label, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextSecondary)
    }
    Spacer(modifier = Modifier.width(8.dp))
    Text(content, fontSize = 12.5.sp, color = TextPrimary, lineHeight = 17.sp, modifier = Modifier.weight(1f))
  }
}

@Composable
private fun ChainStepRow(step: String, title: String, stepColor: Color) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.fillMaxWidth()
  ) {
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(4.dp))
        .background(stepColor.copy(alpha = 0.12f))
        .padding(horizontal = 6.dp, vertical = 3.dp)
    ) {
      Text(step, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = stepColor)
    }
    Spacer(modifier = Modifier.width(8.dp))
    Text(
      text = title,
      fontSize = 13.sp,
      fontWeight = FontWeight.Medium,
      color = TextPrimary
    )
  }
}
