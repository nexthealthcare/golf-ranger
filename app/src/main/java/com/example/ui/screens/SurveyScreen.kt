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
import androidx.compose.material.icons.filled.VideoLibrary
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
import com.example.ui.theme.FairwayGreenPrimary
import com.example.ui.theme.FairwayGreenContainer
import com.example.ui.theme.GolfCardSurfaceHighlight
import com.example.ui.theme.GolfGrassBorder
import com.example.ui.theme.GolfCourseBackground
import com.example.ui.theme.GolfFairwayCardSurface
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.ui.theme.EnergeticCoral
import com.example.ui.theme.EnergeticGold
import com.example.viewmodel.GolfRangerViewModel
import com.example.viewmodel.GolfScreen

/**
 * 아마추어 골퍼 정밀 문진 화면
 * 모든 질문 항목이 복수 선택(Multi-select) 가능하며,
 * 골프장 페어웨이 잔디의 싱그러움이 감도는 그린 틴트가 자연스럽게 블렌딩되어 있습니다.
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
    "홀당 3퍼트 빈도 과다 (숏펏 불안)",
    "후반 나인홀 체력 저하 및 샷 난조",
    "연습장 대비 실전 필드 스윙 괴리",
    "라운드 후 요추 및 손목 통증",
    "지속적 연습에도 타수 정체"
  )

  val confidenceOptions = listOf("드라이버 티샷", "아이언 온그린", "숏게임 어프로치", "퍼팅 거리감", "트러블 탈출")
  val anxietyOptions = listOf("아이언 타점 오차", "티샷 슬라이스 OB", "30m 섕크", "퍼팅 쓰리펏", "후반 체력 고갈")
  val swingHabits = listOf(
    "임팩트 시 골반 조기 전진 (얼리 익스텐션/배치기)",
    "백스윙 탑 상체 덮어침 (오버 더 탑)",
    "팔로우스루 좌측 팔꿈치 당김 (치킨윙)",
    "다운스윙 손목 조기 풀림 (캐스팅 / 스쿠핑)"
  )

  val mistakePresets = listOf(
    "세컨샷 깊은 뒷땅으로 해저드",
    "티샷 우측 슬라이스 OB",
    "그린 주변 30m 어프로치 섕크",
    "1m 파퍼트 놓친 후 3퍼트",
    "벙커에서 한 번에 탈출 실패"
  )

  val goalPresets = listOf(
    "80대 후반 안정 진입 (라베 달성)",
    "보기플레이어 탈피 및 안정 싱글",
    "드라이버 비거리 20m 증가",
    "18홀 내내 일정한 아이언 정타율",
    "부상 없이 평생 즐기는 건강한 스윙"
  )

  Scaffold(
    topBar = {
      GolfTopBar(
        title = "골퍼 프로필 및 실전 문진",
        subtitle = "모든 항목 복수 선택 가능 (바디-스윙 연결 분석)",
        canNavigateBack = true,
        onBackClick = { viewModel.navigateTo(GolfScreen.HOME) }
      )
    },
    containerColor = GolfCourseBackground,
    modifier = modifier.fillMaxSize()
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 18.dp, vertical = 14.dp)
    ) {
      // 1. 평균 핸디캡 선택 (슬라이더 & 수치)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GolfFairwayCardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, GolfGrassBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "최근 5게임 평균 핸디캡",
              fontSize = 14.5.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(FairwayGreenContainer)
                .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Text(
                text = "${profile.handicap} (평균 ${72 + profile.handicap}타)",
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold,
                color = FairwayGreenPrimary
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Slider(
            value = profile.handicap.toFloat(),
            onValueChange = { viewModel.updateHandicap(it.toInt()) },
            valueRange = 0f..36f,
            steps = 35,
            colors = SliderDefaults.colors(
              thumbColor = FairwayGreenPrimary,
              activeTrackColor = FairwayGreenPrimary,
              inactiveTrackColor = GolfGrassBorder
            ),
            modifier = Modifier.testTag("handicap_slider")
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("싱글 (0~9)", fontSize = 11.5.sp, color = TextTertiary)
            Text("보기플레이어 (18)", fontSize = 11.5.sp, color = FairwayGreenPrimary, fontWeight = FontWeight.SemiBold)
            Text("초급 골퍼 (28+)", fontSize = 11.5.sp, color = TextTertiary)
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 2. 강점 및 불안 영역 선택 (모두 복수 선택 가능!)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GolfFairwayCardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, GolfGrassBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "가장 자신 있는 샷 (복수 선택)",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
            Text(
              text = "${profile.strongestAreas.size}개 선택됨",
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = FairwayGreenPrimary
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          // 칩 그리드
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              confidenceOptions.take(3).forEach { option ->
                val isSelected = profile.strongestAreas.contains(option)
                FilterChip(
                  selected = isSelected,
                  onClick = { viewModel.toggleStrongestArea(option) },
                  label = { Text(option, fontSize = 12.sp) },
                  leadingIcon = if (isSelected) {
                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                  } else null,
                  colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = FairwayGreenPrimary,
                    selectedLabelColor = Color.White,
                    selectedLeadingIconColor = Color.White,
                    containerColor = Color.White
                  ),
                  border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    borderColor = GolfGrassBorder,
                    selectedBorderColor = FairwayGreenPrimary
                  )
                )
              }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              confidenceOptions.drop(3).forEach { option ->
                val isSelected = profile.strongestAreas.contains(option)
                FilterChip(
                  selected = isSelected,
                  onClick = { viewModel.toggleStrongestArea(option) },
                  label = { Text(option, fontSize = 12.sp) },
                  leadingIcon = if (isSelected) {
                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                  } else null,
                  colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = FairwayGreenPrimary,
                    selectedLabelColor = Color.White,
                    selectedLeadingIconColor = Color.White,
                    containerColor = Color.White
                  ),
                  border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    borderColor = GolfGrassBorder,
                    selectedBorderColor = FairwayGreenPrimary
                  )
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(18.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "가장 불안정한 샷 (복수 선택)",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
            Text(
              text = "${profile.weakestAreas.size}개 선택됨",
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = EnergeticCoral
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              anxietyOptions.take(3).forEach { option ->
                val isSelected = profile.weakestAreas.contains(option)
                FilterChip(
                  selected = isSelected,
                  onClick = { viewModel.toggleWeakestArea(option) },
                  label = { Text(option, fontSize = 12.sp) },
                  leadingIcon = if (isSelected) {
                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                  } else null,
                  colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = EnergeticCoral,
                    selectedLabelColor = Color.White,
                    selectedLeadingIconColor = Color.White,
                    containerColor = Color.White
                  ),
                  border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    borderColor = GolfGrassBorder,
                    selectedBorderColor = EnergeticCoral
                  )
                )
              }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              anxietyOptions.drop(3).forEach { option ->
                val isSelected = profile.weakestAreas.contains(option)
                FilterChip(
                  selected = isSelected,
                  onClick = { viewModel.toggleWeakestArea(option) },
                  label = { Text(option, fontSize = 12.sp) },
                  leadingIcon = if (isSelected) {
                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                  } else null,
                  colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = EnergeticCoral,
                    selectedLabelColor = Color.White,
                    selectedLeadingIconColor = Color.White,
                    containerColor = Color.White
                  ),
                  border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    borderColor = GolfGrassBorder,
                    selectedBorderColor = EnergeticCoral
                  )
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 3. 최근 라운드 미스 증상 (복수 선택 체크박스)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GolfFairwayCardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, GolfGrassBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "실전 라운드 미스 증상 (복수 선택)",
              fontSize = 14.5.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(FairwayGreenContainer)
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "${profile.roundIssues.size}개 증상 선택",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                color = FairwayGreenPrimary
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          allRoundIssues.forEach { issue ->
            val isChecked = profile.roundIssues.contains(issue)
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isChecked) GolfCardSurfaceHighlight else Color.White)
                .border(
                  width = 1.dp,
                  color = if (isChecked) FairwayGreenPrimary else GolfGrassBorder,
                  shape = RoundedCornerShape(8.dp)
                )
                .clickable { viewModel.toggleRoundIssue(issue) }
                .padding(horizontal = 12.dp, vertical = 9.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(18.dp)
                  .clip(RoundedCornerShape(4.dp))
                  .border(1.5.dp, if (isChecked) FairwayGreenPrimary else GolfGrassBorder, RoundedCornerShape(4.dp))
                  .background(if (isChecked) FairwayGreenPrimary else Color.White),
                contentAlignment = Alignment.Center
              ) {
                if (isChecked) {
                  Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(13.dp))
                }
              }
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = issue,
                fontSize = 13.5.sp,
                fontWeight = if (isChecked) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isChecked) FairwayGreenPrimary else TextPrimary
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 4. 스윙 보상 동작 (복수 선택으로 전환!)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GolfFairwayCardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, GolfGrassBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "체감되는 스윙 보상 동작 (복수 선택)",
              fontSize = 14.5.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(FairwayGreenContainer)
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "${profile.selectedSwingHabits.size}개 선택",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                color = FairwayGreenPrimary
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          swingHabits.forEach { habit ->
            val isChecked = profile.selectedSwingHabits.contains(habit)
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isChecked) GolfCardSurfaceHighlight else Color.White)
                .border(
                  width = 1.dp,
                  color = if (isChecked) FairwayGreenPrimary else GolfGrassBorder,
                  shape = RoundedCornerShape(8.dp)
                )
                .clickable { viewModel.toggleSwingHabit(habit) }
                .padding(horizontal = 12.dp, vertical = 9.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(18.dp)
                  .clip(RoundedCornerShape(4.dp))
                  .border(1.5.dp, if (isChecked) FairwayGreenPrimary else GolfGrassBorder, RoundedCornerShape(4.dp))
                  .background(if (isChecked) FairwayGreenPrimary else Color.White),
                contentAlignment = Alignment.Center
              ) {
                if (isChecked) {
                  Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(13.dp))
                }
              }
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = habit,
                fontSize = 13.sp,
                fontWeight = if (isChecked) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isChecked) FairwayGreenPrimary else TextPrimary
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 5. 최근 라운드 가장 기억나는 실수 (복수 선택 프리셋 칩)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GolfFairwayCardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, GolfGrassBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "최근 라운드 기억나는 실수 (복수 선택)",
            fontSize = 14.5.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
          Spacer(modifier = Modifier.height(8.dp))

          mistakePresets.forEach { mistake ->
            val isSelected = profile.memorableMistakes.contains(mistake)
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(if (isSelected) Color(0xFFFEF2F2) else Color.White)
                .border(
                  width = 1.dp,
                  color = if (isSelected) EnergeticCoral else GolfGrassBorder,
                  shape = RoundedCornerShape(6.dp)
                )
                .clickable { viewModel.toggleMemorableMistake(mistake) }
                .padding(horizontal = 10.dp, vertical = 7.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(16.dp)
                  .clip(RoundedCornerShape(3.dp))
                  .border(1.2.dp, if (isSelected) EnergeticCoral else GolfGrassBorder, RoundedCornerShape(3.dp))
                  .background(if (isSelected) EnergeticCoral else Color.White),
                contentAlignment = Alignment.Center
              ) {
                if (isSelected) {
                  Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                }
              }
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = mistake,
                fontSize = 12.5.sp,
                color = if (isSelected) EnergeticCoral else TextPrimary
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 6. 골퍼의 목표 (장·단기 목표 복수 선택 프리셋)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GolfFairwayCardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, GolfGrassBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "원하는 골프 목표 (복수 선택)",
            fontSize = 14.5.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
          Spacer(modifier = Modifier.height(8.dp))

          goalPresets.forEach { goal ->
            val isSelected = profile.targetGoals.contains(goal)
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(if (isSelected) FairwayGreenContainer else Color.White)
                .border(
                  width = 1.dp,
                  color = if (isSelected) FairwayGreenPrimary else GolfGrassBorder,
                  shape = RoundedCornerShape(6.dp)
                )
                .clickable { viewModel.toggleTargetGoal(goal) }
                .padding(horizontal = 10.dp, vertical = 7.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(16.dp)
                  .clip(RoundedCornerShape(3.dp))
                  .border(1.2.dp, if (isSelected) FairwayGreenPrimary else GolfGrassBorder, RoundedCornerShape(3.dp))
                  .background(if (isSelected) FairwayGreenPrimary else Color.White),
                contentAlignment = Alignment.Center
              ) {
                if (isSelected) {
                  Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                }
              }
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = goal,
                fontSize = 12.5.sp,
                color = if (isSelected) FairwayGreenPrimary else TextPrimary,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 7. 스윙 영상 등록 옵션
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GolfFairwayCardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, GolfGrassBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "본인 스윙 영상 등록 (선택)",
            fontSize = 14.5.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
          Text(
            text = "영상이 있다면 첨부하여 신체검진과 함께 정밀 영상진단을 연계합니다.",
            fontSize = 12.sp,
            color = TextSecondary,
            modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
          )

          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
              onClick = {
                videoPickerLauncher.launch(
                  PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                )
              },
              shape = RoundedCornerShape(8.dp),
              colors = ButtonDefaults.buttonColors(containerColor = FairwayGreenPrimary),
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Default.VideoLibrary, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("영상 파일 선택", fontSize = 12.5.sp)
            }

            OutlinedButton(
              onClick = {
                viewModel.setSwingVideo("demo_video", "샘플 기준 스윙 영상 적용")
              },
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text("기준 영상 적용", fontSize = 12.5.sp, color = TextPrimary)
            }
          }

          if (profile.swingVideoFileName != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(FairwayGreenContainer)
                .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
              Text(
                text = "✓ 등록 완료: ${profile.swingVideoFileName}",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = FairwayGreenPrimary
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // 메인 다음 버튼
      Button(
        onClick = { viewModel.navigateTo(GolfScreen.SCREENING) },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = FairwayGreenPrimary),
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .testTag("goto_screening_button")
      ) {
        Text("다음: SFMA 5대 신체검진으로 이동", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(8.dp))
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
