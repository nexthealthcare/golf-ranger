package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GolfTopBar
import com.example.ui.theme.PerformanceGreenPrimary
import com.example.ui.theme.PerformanceGreenContainer
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceCardSecondary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.viewmodel.GolfRangerViewModel
import com.example.viewmodel.GolfScreen

/**
 * Professional Golfer Survey Screen (Clinical Athletic Intake)
 * Clean, restrained, high-legibility layout for mature golfers without emojis.
 */
@Composable
fun SurveyScreen(
  viewModel: GolfRangerViewModel,
  modifier: Modifier = Modifier
) {
  val profile by viewModel.golferProfile.collectAsState()

  val videoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
  ) { uri: Uri? ->
    if (uri != null) {
      viewModel.setSwingVideo(uri.toString(), "골퍼 등록 스윙 영상")
    }
  }

  val allRoundIssues = listOf(
    "드라이버 슬라이스 및 푸시 샷",
    "비거리 부족 (드라이버 190m 이하)",
    "아이언 뒷땅 및 탑핑 타점 오차",
    "그린 주변 숏게임 거리감 불안정",
    "홀당 3퍼트 빈도 과다",
    "후반 나인홀 체력 저하 및 샷 난조",
    "연습장 대비 실전 필드 스윙 괴리",
    "라운드 후 요추 및 손목 통증",
    "지속적 연습에도 타수 정체"
  )

  val confidenceOptions = listOf("드라이버 티샷", "아이언 온그린", "숏게임 어프로치", "퍼팅 거리감", "트러블 탈출")
  val anxietyOptions = listOf("아이언 타점 오차", "티샷 슬라이스 OB", "30m 쌩크", "퍼팅 쓰리펏", "후반 체력 고갈")
  val swingHabits = listOf(
    "임팩트 시 골반 조기 전진 (얼리 익스텐션)",
    "백스윙 탑 상체 덮어침 (오버 더 탑)",
    "팔로우스루 좌측 팔꿈치 당김 (치킨윙)",
    "다운스윙 손목 조기 풀림 (캐스팅)"
  )

  Scaffold(
    topBar = {
      GolfTopBar(
        title = "1단계: 골퍼 프로필 및 실전 문진",
        subtitle = "골프 기능 해부학 분석 기초 자료 수집",
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
      // 1. 평균 핸디캡 선택 (슬라이더 & 수치)
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
              text = "최근 5게임 평균 핸디캡",
              fontSize = 14.sp,
              fontWeight = FontWeight.SemiBold,
              color = TextPrimary
            )
            Text(
              text = "${profile.handicap} (평균 ${72 + profile.handicap}타)",
              fontSize = 14.sp,
              fontWeight = FontWeight.SemiBold,
              color = PerformanceGreenPrimary
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          Slider(
            value = profile.handicap.toFloat(),
            onValueChange = { viewModel.updateHandicap(it.toInt()) },
            valueRange = 0f..36f,
            steps = 35,
            colors = SliderDefaults.colors(
              thumbColor = PerformanceGreenPrimary,
              activeTrackColor = PerformanceGreenPrimary,
              inactiveTrackColor = SurfaceBorder
            ),
            modifier = Modifier.testTag("handicap_slider")
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("싱글 (0~9)", fontSize = 11.sp, color = TextTertiary)
            Text("보기플레이 (18)", fontSize = 11.sp, color = TextTertiary)
            Text("초급 (28+)", fontSize = 11.sp, color = TextTertiary)
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 2. 강점 및 불안 영역 선택 (정제된 칩)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Text(
            text = "가장 안정적인 샷 (자신감)",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
          )
          Spacer(modifier = Modifier.height(8.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            confidenceOptions.take(3).forEach { option ->
              val isSelected = profile.strongestArea == option
              FilterChip(
                selected = isSelected,
                onClick = { viewModel.updateStrongestArea(option) },
                label = { Text(option, fontSize = 12.sp) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = PerformanceGreenPrimary,
                  selectedLabelColor = Color.White
                )
              )
            }
          }
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            confidenceOptions.drop(3).forEach { option ->
              val isSelected = profile.strongestArea == option
              FilterChip(
                selected = isSelected,
                onClick = { viewModel.updateStrongestArea(option) },
                label = { Text(option, fontSize = 12.sp) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = PerformanceGreenPrimary,
                  selectedLabelColor = Color.White
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          Text(
            text = "가장 불안정한 샷 (미스 다발)",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
          )
          Spacer(modifier = Modifier.height(8.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            anxietyOptions.take(3).forEach { option ->
              val isSelected = profile.weakestArea == option
              FilterChip(
                selected = isSelected,
                onClick = { viewModel.updateWeakestArea(option) },
                label = { Text(option, fontSize = 12.sp) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = Color(0xFFB91C1C),
                  selectedLabelColor = Color.White
                )
              )
            }
          }
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            anxietyOptions.drop(3).forEach { option ->
              val isSelected = profile.weakestArea == option
              FilterChip(
                selected = isSelected,
                onClick = { viewModel.updateWeakestArea(option) },
                label = { Text(option, fontSize = 12.sp) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = Color(0xFFB91C1C),
                  selectedLabelColor = Color.White
                )
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 3. 최근 라운드 미스 항목 (복수 선택 리스트)
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
              text = "실전 라운드 미스 증상 (복수 선택)",
              fontSize = 14.sp,
              fontWeight = FontWeight.SemiBold,
              color = TextPrimary
            )
            Text(
              text = "${profile.roundIssues.size}개 항목 선택됨",
              fontSize = 12.sp,
              color = TextTertiary
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          allRoundIssues.forEach { issue ->
            val isChecked = profile.roundIssues.contains(issue)
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(if (isChecked) PerformanceGreenContainer else Color.Transparent)
                .clickable { viewModel.toggleRoundIssue(issue) }
                .padding(horizontal = 10.dp, vertical = 8.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(16.dp)
                  .clip(RoundedCornerShape(3.dp))
                  .border(1.dp, if (isChecked) PerformanceGreenPrimary else SurfaceBorder, RoundedCornerShape(3.dp))
                  .background(if (isChecked) PerformanceGreenPrimary else Color.White),
                contentAlignment = Alignment.Center
              ) {
                if (isChecked) {
                  Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                }
              }
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = issue,
                fontSize = 13.5.sp,
                fontWeight = if (isChecked) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isChecked) PerformanceGreenPrimary else TextPrimary
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 4. 스윙 패턴 및 영상 진단 옵션
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Text(
            text = "체감되는 스윙 보상 동작",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
          )

          Spacer(modifier = Modifier.height(10.dp))

          swingHabits.forEach { habit ->
            val isSelected = profile.selectedSwingHabit == habit
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
                .clip(RoundedCornerShape(6.dp))
                .clickable { viewModel.setSwingHabit(habit) }
                .padding(vertical = 6.dp, horizontal = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(16.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .border(1.5.dp, if (isSelected) PerformanceGreenPrimary else SurfaceBorder, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
              ) {
                if (isSelected) {
                  Box(
                    modifier = Modifier
                      .size(8.dp)
                      .clip(RoundedCornerShape(4.dp))
                      .background(PerformanceGreenPrimary)
                  )
                }
              }
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = habit,
                fontSize = 13.5.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) PerformanceGreenPrimary else TextSecondary
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
              onClick = {
                videoPickerLauncher.launch(
                  PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                )
              },
              shape = RoundedCornerShape(6.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text("영상 파일 등록", fontSize = 12.sp, color = TextPrimary)
            }

            OutlinedButton(
              onClick = {
                viewModel.setSwingVideo("demo_video", "샘플 스윙 영상 적용됨")
              },
              shape = RoundedCornerShape(6.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text("기준 영상 적용", fontSize = 12.sp, color = TextSecondary)
            }
          }

          if (profile.swingVideoFileName != null) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "등록됨: ${profile.swingVideoFileName}",
              fontSize = 12.sp,
              color = PerformanceGreenPrimary
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      Button(
        onClick = { viewModel.navigateTo(GolfScreen.SCREENING) },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PerformanceGreenPrimary),
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("goto_screening_button")
      ) {
        Text("다음: 13가지 신체검진 프로토콜", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.width(6.dp))
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
