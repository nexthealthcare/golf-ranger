package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SportsGolf
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TrendingDown
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ScreeningGrade
import com.example.ui.components.CycleVisualizer
import com.example.ui.components.GolfTopBar
import com.example.ui.theme.CleanWhiteBorder
import com.example.ui.theme.EnergeticGold
import com.example.ui.theme.FairwayGreenPrimary
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
fun HomeScreen(
  viewModel: GolfRangerViewModel,
  modifier: Modifier = Modifier
) {
  val profile by viewModel.golferProfile.collectAsState()
  val screeningResults by viewModel.screeningResults.collectAsState()
  val report by viewModel.analysisReport.collectAsState()
  val completedExercises by viewModel.completedExercises.collectAsState()

  val limitedCount = screeningResults.count { it.value != ScreeningGrade.PASS }
  val mbti = report?.mbti

  Scaffold(
    topBar = {
      GolfTopBar(
        title = "골프레인저",
        subtitle = "골프 바디 MBTI & 스윙 조교"
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
      // 1. 재미있는 골프 바디 MBTI 진단 카드 (화사한 화이트 & 그린)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, FairwayGreenPrimary.copy(alpha = 0.3f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(TagMintBg)
                .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Text(
                text = "나의 골프 바디 MBTI",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TagMintText
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(TagAmberBg)
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "2주 재검 D-14",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TagAmberText
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = mbti?.animalEmoji ?: "🐯",
              fontSize = 38.sp
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "${mbti?.code ?: "BSE-T"} ${mbti?.name ?: "배치기 타이거형"}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextMainDark
              )
              Text(
                text = "“${mbti?.tagline ?: "마음은 싱글, 임팩트는 벌떡!"}”",
                fontSize = 12.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = FairwayGreenPrimary
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // 1줄 요약 태그
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            SummaryTag(text = "평균 ${profile.handicap}핸디", bg = Color(0xFFF3F4F6), textCol = TextMainDark)
            SummaryTag(text = "몸 브레이크 ${limitedCount}개", bg = if (limitedCount > 0) TagRedBg else TagMintBg, textCol = if (limitedCount > 0) TagRedText else TagMintText)
            SummaryTag(text = report?.estimatedStrokesSaved ?: "-3~5타 절감", bg = TagMintBg, textCol = TagMintText)
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 메인 CTA: 원클릭 진단 시작
      Button(
        onClick = { viewModel.navigateTo(GolfScreen.SURVEY) },
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = FairwayGreenPrimary),
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .testTag("start_diagnosis_button")
      ) {
        Icon(Icons.Default.SportsGolf, contentDescription = null, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("내 골프 MBTI & 신체검진 시작하기", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(4.dp))
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 2. Body -> Swing -> Game 핵심 사이클 다이어그램
      CycleVisualizer(activeStage = "ALL")

      Spacer(modifier = Modifier.height(14.dp))

      // 3. 간결하고 재미있는 빠른 액션 카드 3개
      Text(
        text = "⚡ 조교의 맞춤 훈련 메뉴",
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = TextMainDark
      )

      Spacer(modifier = Modifier.height(8.dp))

      FunQuickCard(
        emoji = "📊",
        title = "AI 종합 분석 & 처방 리포트",
        subtitle = "신체/기술/게임 3분할 & 1줄 치트키",
        tag = "report_action_card",
        onClick = {
          viewModel.refreshAnalysis()
          viewModel.navigateTo(GolfScreen.REPORT)
        }
      )

      Spacer(modifier = Modifier.height(8.dp))

      FunQuickCard(
        emoji = "🤸",
        title = "골프 신체검진 13항목 바로가기",
        subtitle = "그림 & 음성으로 관절 가동성 1분 체크",
        tag = "screening_action_card",
        onClick = { viewModel.navigateTo(GolfScreen.SCREENING) }
      )

      Spacer(modifier = Modifier.height(8.dp))

      FunQuickCard(
        emoji = "⏱️",
        title = "오늘의 3분 운동 & 연습장 드릴",
        subtitle = "타이머 누르고 뚝딱 실천 (${completedExercises.size}개 완료)",
        tag = "routine_action_card",
        onClick = { viewModel.navigateTo(GolfScreen.ROUTINE) }
      )

      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}

@Composable
private fun SummaryTag(text: String, bg: Color, textCol: Color) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(6.dp))
      .background(bg)
      .padding(horizontal = 8.dp, vertical = 4.dp)
  ) {
    Text(
      text = text,
      fontSize = 11.sp,
      fontWeight = FontWeight.Bold,
      color = textCol
    )
  }
}

@Composable
private fun FunQuickCard(
  emoji: String,
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
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = androidx.compose.foundation.BorderStroke(1.dp, CleanWhiteBorder),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = emoji, fontSize = 24.sp)

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = TextMainDark
        )
        Text(
          text = subtitle,
          fontSize = 11.5.sp,
          color = TextMuted
        )
      }

      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
        contentDescription = null,
        tint = TextMuted,
        modifier = Modifier.size(16.dp)
      )
    }
  }
}
