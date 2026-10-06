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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Timer
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
import com.example.ui.components.ScreeningPoseVisualizer
import com.example.ui.theme.CleanWhiteBorder
import com.example.ui.theme.EnergeticGold
import com.example.ui.theme.FairwayGreenPrimary
import com.example.ui.theme.GolfFairwayCardSurface
import com.example.ui.theme.TagAmberBg
import com.example.ui.theme.TagAmberText
import com.example.ui.theme.TagMintBg
import com.example.ui.theme.TagMintText
import com.example.ui.theme.TagRedBg
import com.example.ui.theme.TagRedText
import com.example.ui.theme.TextMainDark
import com.example.ui.theme.TextMuted
import com.example.viewmodel.GolfRangerViewModel
import com.example.viewmodel.GolfScreen

@Composable
fun ReportScreen(
  viewModel: GolfRangerViewModel,
  modifier: Modifier = Modifier
) {
  val report by viewModel.analysisReport.collectAsState()
  val profile by viewModel.golferProfile.collectAsState()
  val completedExercises by viewModel.completedExercises.collectAsState()

  var selectedExercise by remember { mutableStateOf<BodyExercise?>(null) }
  var selectedDrill by remember { mutableStateOf<GolfDrill?>(null) }

  Scaffold(
    topBar = {
      GolfTopBar(
        title = "AI 바디-골프 처방전",
        subtitle = "골프 MBTI & 타수 감축 솔루션",
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
      if (report == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
          Text("데이터를 분석하고 있습니다...")
        }
        return@Column
      }

      val r = report!!
      val mbti = r.mbti

      // 1. 🏆 골프 바디 MBTI 결과 카드 (화사한 화이트 & 그린)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = GolfFairwayCardSurface),
        border = androidx.compose.foundation.BorderStroke(2.dp, FairwayGreenPrimary),
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
                .background(TagMintBg)
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "GOLF BODY MBTI",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TagMintText
              )
            }
            Text(
              text = "타수 절감 목표: ${r.estimatedStrokesSaved}",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = FairwayGreenPrimary
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = mbti.animalEmoji, fontSize = 44.sp)
            Spacer(modifier = Modifier.width(14.dp))
            Column {
              Text(
                text = "${mbti.code} ${mbti.name}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextMainDark
              )
              Text(
                text = "“${mbti.tagline}”",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = FairwayGreenPrimary
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          MbtiTraitRow(label = "슈퍼파워", text = mbti.superpower, tagBg = TagMintBg, tagText = TagMintText)
          Spacer(modifier = Modifier.height(4.dp))
          MbtiTraitRow(label = "신체원인", text = mbti.bodyCause, tagBg = TagRedBg, tagText = TagRedText)
          Spacer(modifier = Modifier.height(4.dp))
          MbtiTraitRow(label = "1줄치트키", text = mbti.quickFix, tagBg = TagAmberBg, tagText = TagAmberText)
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 2. 원인 기여도 3분할 카드
      ContributionPieCard(
        bodyPct = r.bodyContribution,
        swingPct = r.swingContribution,
        gamePct = r.gameContribution
      )

      Spacer(modifier = Modifier.height(12.dp))

      // 3. 인과관계 3줄 연결 카드
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GolfFairwayCardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, CleanWhiteBorder)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "🔗 3단계 인과관계 체인",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TextMainDark
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "1️⃣ 바디: ${r.primaryBodyLimitation}",
            fontSize = 12.5.sp,
            color = FairwayGreenPrimary,
            fontWeight = FontWeight.SemiBold
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "2️⃣ 스윙: 보상으로 ${r.primarySwingCompensation}",
            fontSize = 12.5.sp,
            color = EnergeticGold,
            fontWeight = FontWeight.SemiBold
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "3️⃣ 게임: 실전에서 ${r.primaryGameMistake} 유발!",
            fontSize = 12.5.sp,
            color = Color(0xFFDC2626),
            fontWeight = FontWeight.SemiBold
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 4. 개인 맞춤 운동 처방 (상세 좌우 세트/반복 & 음성 실행 버튼)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "💪 맞춤 모빌리티 운동 (${r.exercises.size}가지)",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = TextMainDark
        )
        Text(
          text = "음성 코칭 포함",
          fontSize = 11.5.sp,
          fontWeight = FontWeight.Bold,
          color = FairwayGreenPrimary
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      r.exercises.forEachIndexed { idx, ex ->
        val isDone = completedExercises.contains(ex.title)
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (isDone) Color(0xFFE8F5E9) else GolfFairwayCardSurface
          ),
          border = androidx.compose.foundation.BorderStroke(1.dp, CleanWhiteBorder)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "${idx + 1}. ${ex.title}",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = TextMainDark
                )
                Text(
                  text = "타겟: ${ex.targetArea}",
                  fontSize = 11.5.sp,
                  color = TextMuted
                )
              }

              // 운동 실행 (음성 코칭) 버튼
              Button(
                onClick = { selectedExercise = ex },
                colors = ButtonDefaults.buttonColors(containerColor = FairwayGreenPrimary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("action_exercise_${idx}")
              ) {
                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("운동 실행", fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 좌우 세트 상세 태그
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(TagMintBg)
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = "📌 ${ex.leftRightDetail}",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                color = TagMintText
              )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = "💡 ${ex.coachingKey}",
              fontSize = 11.5.sp,
              color = FairwayGreenPrimary
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 5. 골프 연습장 드릴 (일러스트 & 음성 실행 버튼)
      Text(
        text = "🏌️ 연습장 원포인트 드릴 (${r.drills.size}가지)",
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = TextMainDark
      )

      Spacer(modifier = Modifier.height(8.dp))

      r.drills.forEachIndexed { idx, drill ->
        val isDone = completedExercises.contains(drill.title)
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (isDone) Color(0xFFE8F5E9) else GolfFairwayCardSurface
          ),
          border = androidx.compose.foundation.BorderStroke(1.dp, CleanWhiteBorder)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "${idx + 1}. ${drill.title}",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = TextMainDark
                )
                Text(
                  text = "추천 클럽: ${drill.recommendedClub}",
                  fontSize = 11.5.sp,
                  color = TextMuted
                )
              }

              // 드릴 실행 (음성 코칭) 버튼
              Button(
                onClick = { selectedDrill = drill },
                colors = ButtonDefaults.buttonColors(containerColor = EnergeticGold),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("action_drill_${idx}")
              ) {
                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, tint = Color.White, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("드릴 실행", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
              }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 드릴 세트/반복수 태그
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(TagAmberBg)
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = "🎯 ${drill.setAndReps}",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                color = TagAmberText
              )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
              text = drill.feelVsReal,
              fontSize = 11.5.sp,
              fontWeight = FontWeight.SemiBold,
              color = FairwayGreenPrimary
            )
            Text(
              text = "체크: ${drill.checkpoint}",
              fontSize = 11.sp,
              color = TextMuted
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 하단 액션 버튼들
      Button(
        onClick = { viewModel.navigateTo(GolfScreen.ROUTINE) },
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = FairwayGreenPrimary),
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("goto_routine_button")
      ) {
        Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text("오늘의 실천 루틴으로 이동", fontSize = 14.sp, fontWeight = FontWeight.Bold)
      }

      Spacer(modifier = Modifier.height(8.dp))

      Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
          onClick = { viewModel.resetForRetest() },
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .weight(1f)
            .height(46.dp)
        ) {
          Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("신체 재검진", fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedButton(
          onClick = { viewModel.navigateTo(GolfScreen.HOME) },
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .weight(1f)
            .height(46.dp)
        ) {
          Text("홈 화면으로", fontSize = 12.sp)
        }
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }

  // 운동 코칭 다이얼로그 (음성 + 세트 트래커)
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

  // 드릴 코칭 다이얼로그 (음성 + 스윙 드릴 가이드)
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
private fun MbtiTraitRow(label: String, text: String, tagBg: Color, tagText: Color) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.padding(vertical = 1.dp)
  ) {
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(4.dp))
        .background(tagBg)
        .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
      Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = tagText)
    }
    Spacer(modifier = Modifier.width(8.dp))
    Text(text, fontSize = 12.sp, color = TextMainDark, maxLines = 1)
  }
}
