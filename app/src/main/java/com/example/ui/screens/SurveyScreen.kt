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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.ui.theme.CleanWhiteBorder
import com.example.ui.theme.EnergeticGold
import com.example.ui.theme.FairwayGreenPrimary
import com.example.ui.theme.TagAmberBg
import com.example.ui.theme.TagAmberText
import com.example.ui.theme.TagMintBg
import com.example.ui.theme.TagMintText
import com.example.ui.theme.TextMainDark
import com.example.ui.theme.TextMuted
import com.example.viewmodel.GolfRangerViewModel
import com.example.viewmodel.GolfScreen

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
      viewModel.setSwingVideo(uri.toString(), "골퍼 본인 스윙 영상")
    }
  }

  val allRoundIssues = listOf(
    "드라이버 슬라이스 / 훅",
    "비거리 부족 (드라이버 180m 이하)",
    "아이언 뒷땅 / 탑핑이 잦음",
    "숏게임 어프로치 거리감 불안",
    "3퍼트가 너무 많음",
    "후반에 무너짐 (체력 저하)",
    "필드만 가면 스윙이 바뀜",
    "라운드 후 허리/손목이 아픔",
    "연습해도 제자리걸음 (정체기)"
  )

  val confidenceOptions = listOf("드라이버 티샷", "아이언 온그린", "숏게임 어프로치", "원펏 거리감", "트러블 탈출")
  val anxietyOptions = listOf("아이언 뒷땅/탑핑", "티샷 슬라이스 OB", "30m 쌩크", "3퍼트", "후반 체력 고갈")
  val swingHabits = listOf(
    "임팩트 시 골반이 앞으로 밀림 (배치기)",
    "백스윙 시 상체가 엎어 들어옴 (오버 더 탑)",
    "임팩트 후 왼팔이 당겨짐 (치킨윙)",
    "다운스윙 손목이 일찍 풀림 (캐스팅)"
  )

  Scaffold(
    topBar = {
      GolfTopBar(
        title = "1단계: 골퍼 성향 설문",
        subtitle = "글자는 적게, 터치는 빠르게!",
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
      // 1. 평균 핸디캡 선택 (슬라이더)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CleanWhiteBorder)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "🏌️ 평균 핸디캡",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = TextMainDark
            )
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(TagMintBg)
                .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Text(
                text = "${profile.handicap}핸디 (평균 ${72 + profile.handicap}타)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TagMintText
              )
            }
          }

          Spacer(modifier = Modifier.height(6.dp))

          Slider(
            value = profile.handicap.toFloat(),
            onValueChange = { viewModel.updateHandicap(it.toInt()) },
            valueRange = 0f..36f,
            steps = 35,
            colors = SliderDefaults.colors(
              thumbColor = FairwayGreenPrimary,
              activeTrackColor = FairwayGreenPrimary,
              inactiveTrackColor = Color(0xFFE5E7EB)
            ),
            modifier = Modifier.testTag("handicap_slider")
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("싱글 (0~9)", fontSize = 11.sp, color = TextMuted)
            Text("보기플레이 (18)", fontSize = 11.sp, color = TextMuted)
            Text("백돌이 (28+)", fontSize = 11.sp, color = TextMuted)
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 2. 자신 있는 부분 & 불안한 부분 (원터치 칩)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CleanWhiteBorder)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "👍 가장 자신 있는 무기",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TextMainDark
          )
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            confidenceOptions.take(3).forEach { option ->
              FilterChip(
                selected = profile.strongestArea == option,
                onClick = { viewModel.updateStrongestArea(option) },
                label = { Text(option, fontSize = 11.5.sp) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = FairwayGreenPrimary,
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
              FilterChip(
                selected = profile.strongestArea == option,
                onClick = { viewModel.updateStrongestArea(option) },
                label = { Text(option, fontSize = 11.5.sp) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = FairwayGreenPrimary,
                  selectedLabelColor = Color.White
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = "⚠️ 가장 불안한 샷",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TextMainDark
          )
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            anxietyOptions.take(3).forEach { option ->
              FilterChip(
                selected = profile.weakestArea == option,
                onClick = { viewModel.updateWeakestArea(option) },
                label = { Text(option, fontSize = 11.5.sp) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = Color(0xFFEF4444),
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
              FilterChip(
                selected = profile.weakestArea == option,
                onClick = { viewModel.updateWeakestArea(option) },
                label = { Text(option, fontSize = 11.5.sp) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = Color(0xFFEF4444),
                  selectedLabelColor = Color.White
                )
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 3. 최근 라운드 문제점 체크 (복수 선택)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CleanWhiteBorder)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "💣 최근 라운드 미스 (복수 선택)",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = TextMainDark
            )
            Text(
              text = "${profile.roundIssues.size}개 선택",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = FairwayGreenPrimary
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          allRoundIssues.forEach { issue ->
            val isChecked = profile.roundIssues.contains(issue)
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isChecked) FairwayGreenPrimary.copy(alpha = 0.08f) else Color.Transparent)
                .clickable { viewModel.toggleRoundIssue(issue) }
                .padding(horizontal = 8.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(18.dp)
                  .clip(RoundedCornerShape(4.dp))
                  .background(if (isChecked) FairwayGreenPrimary else Color(0xFFE5E7EB)),
                contentAlignment = Alignment.Center
              ) {
                if (isChecked) {
                  Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(13.dp))
                }
              }
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = issue,
                fontSize = 13.sp,
                fontWeight = if (isChecked) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isChecked) FairwayGreenPrimary else TextMainDark
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 4. 스윙 습관 자가 선택 & 영상 진단 (간편 선택)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CleanWhiteBorder)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "🎬 내 스윙 영상 & 습관",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TextMainDark
          )

          Spacer(modifier = Modifier.height(8.dp))

          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
              onClick = {
                videoPickerLauncher.launch(
                  PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                )
              },
              colors = ButtonDefaults.buttonColors(containerColor = FairwayGreenPrimary),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Default.Videocam, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("영상 등록", fontSize = 12.sp)
            }

            Button(
              onClick = {
                viewModel.setSwingVideo("demo_video", "샘플 스윙 영상 적용됨")
              },
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F4F6)),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Default.Movie, contentDescription = null, tint = TextMainDark, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("샘플 적용", fontSize = 12.sp, color = TextMainDark)
            }
          }

          if (profile.swingVideoFileName != null) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "✅ ${profile.swingVideoFileName}",
              fontSize = 11.5.sp,
              color = FairwayGreenPrimary,
              fontWeight = FontWeight.Bold
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "체감되는 스윙 습관 1가지",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = TextMuted
          )

          Spacer(modifier = Modifier.height(6.dp))

          swingHabits.forEach { habit ->
            val isSelected = profile.selectedSwingHabit == habit
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { viewModel.setSwingHabit(habit) }
                .padding(vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(16.dp)
                  .clip(CircleShape)
                  .background(if (isSelected) FairwayGreenPrimary else Color(0xFFD1D5DB)),
                contentAlignment = Alignment.Center
              ) {
                if (isSelected) {
                  Box(
                    modifier = Modifier
                      .size(6.dp)
                      .clip(CircleShape)
                      .background(Color.White)
                  )
                }
              }
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = habit,
                fontSize = 12.5.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) FairwayGreenPrimary else TextMuted
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 다음: 신체검진 버튼
      Button(
        onClick = { viewModel.navigateTo(GolfScreen.SCREENING) },
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = FairwayGreenPrimary),
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .testTag("goto_screening_button")
      ) {
        Text("다음: 13가지 신체검진 (그림&음성)", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(6.dp))
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
