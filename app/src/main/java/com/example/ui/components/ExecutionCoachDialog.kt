package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.ui.theme.CleanWhiteBorder
import com.example.ui.theme.EnergeticGold
import com.example.ui.theme.FairwayGreenPrimary
import com.example.ui.theme.TagAmberBg
import com.example.ui.theme.TagAmberText
import com.example.ui.theme.TagMintBg
import com.example.ui.theme.TagMintText
import com.example.ui.theme.TextMainDark
import com.example.ui.theme.TextMuted
import com.example.util.TtsManager
import kotlinx.coroutines.delay

@Composable
fun ExecutionCoachDialog(
  title: String,
  targetOrClub: String,
  setsAndReps: String,
  coachingKey: String,
  voiceScript: String,
  visualType: String,
  instructions: List<String>,
  onDismiss: () -> Unit,
  onCompleted: () -> Unit
) {
  val context = LocalContext.current
  val ttsManager = remember { TtsManager(context) }
  var isSpeaking by remember { mutableStateOf(false) }
  var currentSet by remember { mutableIntStateOf(1) }
  val maxSets = 3

  DisposableEffect(Unit) {
    // 다이얼로그 열릴 때 조교 음성 자동 안내 시작
    ttsManager.speak(voiceScript)
    isSpeaking = true
    onDispose {
      ttsManager.shutdown()
    }
  }

  AlertDialog(
    onDismissRequest = {
      ttsManager.stop()
      onDismiss()
    },
    shape = RoundedCornerShape(20.dp),
    containerColor = Color.White,
    title = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = title,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = TextMainDark
          )
          Text(
            text = targetOrClub,
            fontSize = 12.sp,
            color = FairwayGreenPrimary,
            fontWeight = FontWeight.SemiBold
          )
        }
        IconButton(
          onClick = {
            ttsManager.stop()
            onDismiss()
          }
        ) {
          Icon(Icons.Default.Close, contentDescription = "닫기", tint = TextMuted)
        }
      }
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // 3D 입체 일러스트 자세 시각화
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF9FAFB)),
          contentAlignment = Alignment.Center
        ) {
          ScreeningPoseVisualizer(
            iconType = visualType,
            modifier = Modifier.size(160.dp, 120.dp)
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 좌우 몇 회 몇 세트 상세 안내 태그
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(TagMintBg)
            .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
          Column {
            Text(
              text = "📌 추천 횟수 및 세트",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = TagMintText
            )
            Text(
              text = setsAndReps,
              fontSize = 12.5.sp,
              fontWeight = FontWeight.Bold,
              color = FairwayGreenPrimary
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 조교 음성 가이드 버튼 (토글)
        Button(
          onClick = {
            if (isSpeaking) {
              ttsManager.stop()
              isSpeaking = false
            } else {
              ttsManager.speak(voiceScript)
              isSpeaking = true
            }
          },
          colors = ButtonDefaults.buttonColors(
            containerColor = if (isSpeaking) Color(0xFFEF4444) else FairwayGreenPrimary
          ),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.fillMaxWidth().testTag("coach_voice_button")
        ) {
          Icon(
            imageVector = if (isSpeaking) Icons.Default.Stop else Icons.AutoMirrored.Filled.VolumeUp,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (isSpeaking) "음성 코칭 정지" else "📢 조교 음성 코칭 다시 듣기",
            fontSize = 12.5.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 세트 완료 트래커 (1세트, 2세트, 3세트)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "현재 진행: ${currentSet} / ${maxSets}세트",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextMainDark
          )

          Button(
            onClick = {
              if (currentSet < maxSets) {
                currentSet++
              } else {
                onCompleted()
                ttsManager.stop()
                onDismiss()
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = FairwayGreenPrimary),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.testTag("complete_set_button")
          ) {
            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = if (currentSet < maxSets) "${currentSet}세트 완료" else "전체 완료 🏆",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 원포인트 조교 팁
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(TagAmberBg)
            .padding(8.dp)
        ) {
          Text(
            text = "💡 $coachingKey",
            fontSize = 11.5.sp,
            color = TagAmberText,
            fontWeight = FontWeight.SemiBold
          )
        }
      }
    },
    confirmButton = {},
    dismissButton = {}
  )
}
