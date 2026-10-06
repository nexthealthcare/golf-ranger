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
import androidx.compose.foundation.shape.CircleShape
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
import com.example.ui.theme.CleanWhiteBorder
import com.example.ui.theme.EnergeticGold
import com.example.ui.theme.FairwayGreenPrimary
import com.example.ui.theme.TagMintBg
import com.example.ui.theme.TagMintText
import com.example.ui.theme.TextMainDark
import com.example.ui.theme.TextMuted
import com.example.util.TtsManager
import com.example.viewmodel.GolfRangerViewModel
import com.example.viewmodel.GolfScreen

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
        title = "2단계: 신체검진 (${currentIndex + 1}/${items.size})",
        subtitle = "그림 & 음성으로 10초 셀프 체크",
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
        .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
      // 상단 프로그레스 바
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "${currentIndex + 1} / ${items.size} 검진 진행 중",
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          color = FairwayGreenPrimary
        )
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(TagMintBg)
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(currentItem.category, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TagMintText)
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp)),
        color = FairwayGreenPrimary,
        trackColor = Color(0xFFE5E7EB)
      )

      Spacer(modifier = Modifier.height(10.dp))

      // 1~13 번호 원형 점프 버튼
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        items.forEachIndexed { idx, item ->
          val grade = screeningResults[item.id]
          val isCurrent = idx == currentIndex
          val chipBg = when {
            isCurrent -> FairwayGreenPrimary
            grade == ScreeningGrade.PASS -> Color(0xFFDCFCE7)
            grade == ScreeningGrade.RESTRICTED -> Color(0xFFFEE2E2)
            else -> Color(0xFFF3F4F6)
          }
          val textColor = if (isCurrent) Color.White else TextMainDark

          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(chipBg)
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
              fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
              color = textColor
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 검사 항목 자세 일러스트 카드 (화사한 화이트)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CleanWhiteBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "${currentIndex + 1}. ${currentItem.title}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextMainDark
          )
          Text(
            text = currentItem.englishSubtitle,
            fontSize = 11.5.sp,
            color = TextMuted
          )

          Spacer(modifier = Modifier.height(8.dp))

          // 자세 시각화 Canvas
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(130.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(Color(0xFFF9FAFB)),
            contentAlignment = Alignment.Center
          ) {
            ScreeningPoseVisualizer(
              iconType = currentItem.previewIconType,
              modifier = Modifier.size(150.dp, 120.dp)
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          // 음성 조교 버튼 (원터치)
          Button(
            onClick = {
              if (isSpeaking) {
                ttsManager.stop()
                isSpeaking = false
              } else {
                ttsManager.speak(currentItem.voiceGuideText)
                isSpeaking = true
              }
            },
            colors = ButtonDefaults.buttonColors(
              containerColor = if (isSpeaking) Color(0xFFEF4444) else FairwayGreenPrimary
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.testTag("tts_guide_button")
          ) {
            Icon(
              imageVector = if (isSpeaking) Icons.Default.Stop else Icons.AutoMirrored.Filled.VolumeUp,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (isSpeaking) "음성 정지" else "조교 음성 가이드 듣기",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // 간단 2줄 가이드 & 판정 기준 (글자 대폭 축소)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CleanWhiteBorder)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text(
            text = "📋 통과 기준: ${currentItem.passCriteria}",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextMainDark
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "⚠️ 미통과 시: ${currentItem.swingImpact}",
            fontSize = 11.5.sp,
            color = Color(0xFFDC2626)
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 3가지 직관적인 판정 버튼
      Text(
        text = "골퍼 본인의 수행 결과는?",
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = TextMainDark
      )

      Spacer(modifier = Modifier.height(6.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        GradeButton(
          label = "통과 🟢",
          sub = "부드러움",
          isSelected = currentGrade == ScreeningGrade.PASS,
          activeColor = FairwayGreenPrimary,
          modifier = Modifier.weight(1f),
          onClick = { viewModel.setScreeningGrade(currentItem.id, ScreeningGrade.PASS) }
        )

        GradeButton(
          label = "뻐근 🟡",
          sub = "제한됨",
          isSelected = currentGrade == ScreeningGrade.LIMITED,
          activeColor = EnergeticGold,
          modifier = Modifier.weight(1f),
          onClick = { viewModel.setScreeningGrade(currentItem.id, ScreeningGrade.LIMITED) }
        )

        GradeButton(
          label = "불가 🔴",
          sub = "안 움직임",
          isSelected = currentGrade == ScreeningGrade.RESTRICTED,
          activeColor = Color(0xFFEF4444),
          modifier = Modifier.weight(1f),
          onClick = { viewModel.setScreeningGrade(currentItem.id, ScreeningGrade.RESTRICTED) }
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 이전 / 다음 버튼
      Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
          onClick = {
            ttsManager.stop()
            isSpeaking = false
            viewModel.prevScreening()
          },
          enabled = currentIndex > 0,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("prev_screening_button")
        ) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("이전", fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
          onClick = {
            ttsManager.stop()
            isSpeaking = false
            viewModel.nextScreening()
          },
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = FairwayGreenPrimary),
          modifier = Modifier
            .weight(1.3f)
            .height(48.dp)
            .testTag("next_screening_button")
        ) {
          Text(
            text = if (currentIndex < items.size - 1) "다음 (${currentIndex + 2}/${items.size})" else "AI 리포트 보기 🏆",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
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
private fun GradeButton(
  label: String,
  sub: String,
  isSelected: Boolean,
  activeColor: Color,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(if (isSelected) activeColor.copy(alpha = 0.12f) else Color.White)
      .border(
        width = if (isSelected) 2.dp else 1.dp,
        color = if (isSelected) activeColor else CleanWhiteBorder,
        shape = RoundedCornerShape(12.dp)
      )
      .clickable { onClick() }
      .padding(vertical = 12.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = label,
        fontSize = 13.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
        color = if (isSelected) activeColor else TextMainDark
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = sub,
        fontSize = 11.sp,
        color = TextMuted
      )
    }
  }
}
