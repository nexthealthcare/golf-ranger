package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.ScreeningDataSource
import com.example.model.ScreeningGrade
import com.example.ui.components.GolfTopBar
import com.example.ui.theme.FairwayGreenContainer
import com.example.ui.theme.FairwayGreenPrimary
import com.example.ui.theme.GolfCardSurfaceHighlight
import com.example.ui.theme.GolfFairwayCardSurface
import com.example.ui.theme.GolfGrassBorder
import com.example.ui.theme.PerformanceGreenContainer
import com.example.ui.theme.PerformanceGreenPrimary
import com.example.ui.theme.StatusCaution
import com.example.ui.theme.StatusRestricted
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.util.TtsManager
import com.example.viewmodel.GolfRangerViewModel
import com.example.viewmodel.GolfScreen

private fun getSfmaDrawableRes(technicalId: String): Int {
  return when (technicalId) {
    "SFMA_FLEXION" -> R.drawable.img_sfma_flexion
    "SFMA_EXTENSION" -> R.drawable.img_sfma_extension
    "SFMA_ROTATION" -> R.drawable.img_sfma_rotation
    "SFMA_SLS" -> R.drawable.img_sfma_sls
    "SFMA_SQUAT" -> R.drawable.img_sfma_squat
    else -> R.drawable.img_sfma_flexion
  }
}

/**
 * SFMA (Selective Functional Movement Assessment) 5대 핵심 신체검진 화면
 * 아마추어가 쉽게 보고 따라할 수 있도록 실제 사람 골퍼 일러스트 사진과 단계별 가이드 제공
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
        title = "SFMA 5대 핵심 신체검진",
        subtitle = "5가지 필수 움직임으로 스윙 제한점 예측",
        canNavigateBack = true,
        onBackClick = {
          ttsManager.stop()
          isSpeaking = false
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
        .padding(horizontal = 18.dp, vertical = 14.dp)
    ) {
      // 상단 프로그레스 및 SFMA 단계 표시
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(FairwayGreenContainer)
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = "SFMA TEST ${currentIndex + 1} / ${items.size}",
              fontSize = 11.5.sp,
              fontWeight = FontWeight.Bold,
              color = FairwayGreenPrimary
            )
          }
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = currentItem.technicalId.replace("SFMA_", ""),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextSecondary
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .border(1.dp, GolfGrassBorder, RoundedCornerShape(6.dp))
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = currentItem.category,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
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
          .height(5.dp)
          .clip(RoundedCornerShape(3.dp)),
        color = FairwayGreenPrimary,
        trackColor = GolfGrassBorder.copy(alpha = 0.5f)
      )

      Spacer(modifier = Modifier.height(14.dp))

      // 5개 SFMA 네비게이션 탭 (탭 이름과 함께 직관적 표시)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items.forEachIndexed { idx, item ->
          val grade = screeningResults[item.id]
          val isCurrent = idx == currentIndex

          val tabBorder = if (isCurrent) FairwayGreenPrimary else GolfGrassBorder
          val tabBg = when {
            isCurrent -> FairwayGreenContainer
            grade == ScreeningGrade.PASS -> Color.White
            grade == ScreeningGrade.RESTRICTED -> Color(0xFFFEF2F2)
            else -> Color(0xFFFFFBEB)
          }

          val shortName = when (item.technicalId) {
            "SFMA_FLEXION" -> "1.굴곡(Flex)"
            "SFMA_EXTENSION" -> "2.신전(Ext)"
            "SFMA_ROTATION" -> "3.회전(Rot)"
            "SFMA_SLS" -> "4.외발(SLS)"
            "SFMA_SQUAT" -> "5.스쿼트(Squat)"
            else -> "${idx + 1}"
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .border(if (isCurrent) 1.5.dp else 1.dp, tabBorder, RoundedCornerShape(8.dp))
              .background(tabBg)
              .clickable {
                ttsManager.stop()
                isSpeaking = false
                viewModel.setScreeningIndex(idx)
              }
              .padding(horizontal = 12.dp, vertical = 7.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = shortName,
              fontSize = 12.sp,
              fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
              color = if (isCurrent) FairwayGreenPrimary else TextSecondary
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 메인 검사 카드 (사람 동작 일러스트 사진 + 동작 지침)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = GolfFairwayCardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, GolfGrassBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "${currentIndex + 1}. ${currentItem.title}",
                fontSize = 16.5.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
              Text(
                text = currentItem.englishSubtitle,
                fontSize = 12.sp,
                color = TextTertiary
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(GolfCardSurfaceHighlight)
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = "동작 가이드",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = FairwayGreenPrimary
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // ★ 사용자가 요청한 "사람이 있는 그림" - 고화질 골퍼 동작 사진 일러스트 ★
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(230.dp)
              .clip(RoundedCornerShape(12.dp))
              .border(1.dp, GolfGrassBorder, RoundedCornerShape(12.dp))
              .background(Color.White),
            contentAlignment = Alignment.Center
          ) {
            Image(
              painter = painterResource(id = getSfmaDrawableRes(currentItem.technicalId)),
              contentDescription = "${currentItem.title} 동작 자세 그림",
              modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
              contentScale = ContentScale.Fit
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          // 동작 설명 박스
          Text(
            text = currentItem.description,
            fontSize = 13.sp,
            color = TextSecondary,
            lineHeight = 18.5.sp
          )

          Spacer(modifier = Modifier.height(14.dp))

          // 아마추어가 따라하기 쉬운 3단계 체크 순서
          Text(
            text = "따라하기 순서 (3단계)",
            fontSize = 12.5.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
          Spacer(modifier = Modifier.height(6.dp))

          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            currentItem.instructionSteps.forEachIndexed { stepIdx, step ->
              Row(verticalAlignment = Alignment.Top) {
                Box(
                  modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(FairwayGreenPrimary),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = "${stepIdx + 1}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = step,
                  fontSize = 12.5.sp,
                  color = TextPrimary,
                  lineHeight = 17.sp,
                  modifier = Modifier.weight(1f)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // 음성 가이드 버튼 (골프 조교 목소리 안내)
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
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(42.dp)
              .testTag("tts_guide_button")
          ) {
            Icon(
              imageVector = if (isSpeaking) Icons.Default.Stop else Icons.AutoMirrored.Filled.VolumeUp,
              contentDescription = null,
              tint = FairwayGreenPrimary,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (isSpeaking) "음성 가이드 일시중지" else "조교의 친절한 음성 설명 듣기",
              fontSize = 13.sp,
              fontWeight = FontWeight.SemiBold,
              color = FairwayGreenPrimary
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 판정 기준 및 스윙 제한점 예측 카드
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GolfFairwayCardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, GolfGrassBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              Icons.Default.Check,
              contentDescription = null,
              tint = FairwayGreenPrimary,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "정상 판정 기준",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = FairwayGreenPrimary
            )
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = currentItem.passCriteria,
            fontSize = 13.sp,
            color = TextPrimary,
            lineHeight = 18.sp
          )

          Spacer(modifier = Modifier.height(12.dp))

          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              Icons.Default.Warning,
              contentDescription = null,
              tint = Color(0xFFDC2626),
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "제한 시 예측되는 스윙 보상 동작",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFFDC2626)
            )
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = currentItem.swingImpact,
            fontSize = 12.5.sp,
            color = TextPrimary,
            lineHeight = 18.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 3단계 자가 평가 등급 선택
      Text(
        text = "나의 동작 자가 평가 결과",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        AssessmentGradeButton(
          label = "정상",
          sub = "Pass (통과)",
          isSelected = currentGrade == ScreeningGrade.PASS,
          activeColor = FairwayGreenPrimary,
          modifier = Modifier.weight(1f),
          onClick = { viewModel.setScreeningGrade(currentItem.id, ScreeningGrade.PASS) }
        )

        AssessmentGradeButton(
          label = "주의",
          sub = "Borderline (부족)",
          isSelected = currentGrade == ScreeningGrade.LIMITED,
          activeColor = StatusCaution,
          modifier = Modifier.weight(1f),
          onClick = { viewModel.setScreeningGrade(currentItem.id, ScreeningGrade.LIMITED) }
        )

        AssessmentGradeButton(
          label = "제한",
          sub = "Restricted (제한)",
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
            .height(48.dp)
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
            if (currentIndex < items.size - 1) {
              viewModel.nextScreening()
            } else {
              viewModel.refreshAnalysis()
              viewModel.navigateTo(GolfScreen.REPORT)
            }
          },
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(containerColor = FairwayGreenPrimary),
          modifier = Modifier
            .weight(1.5f)
            .height(48.dp)
            .testTag("next_screening_button")
        ) {
          Text(
            text = if (currentIndex < items.size - 1) "다음 (${currentIndex + 2}/${items.size})" else "🏆 SFMA 종합 리포트 보기",
            fontSize = 13.5.sp,
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
        width = if (isSelected) 2.dp else 1.dp,
        color = if (isSelected) activeColor else GolfGrassBorder,
        shape = RoundedCornerShape(8.dp)
      )
      .background(if (isSelected) activeColor.copy(alpha = 0.12f) else Color.White)
      .clickable { onClick() }
      .padding(vertical = 12.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = label,
        fontSize = 14.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
        color = if (isSelected) activeColor else TextPrimary
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = sub,
        fontSize = 10.5.sp,
        color = if (isSelected) activeColor else TextTertiary
      )
    }
  }
}
