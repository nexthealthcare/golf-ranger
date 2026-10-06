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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.example.ui.theme.GolfFairwayCardSurface
import com.example.ui.theme.PerformanceGreenPrimary
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceCardSecondary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.util.TtsManager

/**
 * Clinical Performance Execution Dialog (Garmin / WHOOP clinical athletic style)
 * Clean, restrained, high-legibility interface with voice guidance and set tracking.
 */
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
    shape = RoundedCornerShape(12.dp),
    containerColor = GolfFairwayCardSurface,
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
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
          )
          Text(
            text = targetOrClub,
            fontSize = 13.sp,
            color = PerformanceGreenPrimary,
            fontWeight = FontWeight.Medium
          )
        }
        IconButton(
          onClick = {
            ttsManager.stop()
            onDismiss()
          }
        ) {
          Icon(Icons.Default.Close, contentDescription = "닫기", tint = TextTertiary, modifier = Modifier.size(20.dp))
        }
      }
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth()
      ) {
        // Biomechanical kinetic visualizer
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceCardSecondary),
          contentAlignment = Alignment.Center
        ) {
          ScreeningPoseVisualizer(
            iconType = visualType,
            modifier = Modifier.size(160.dp, 100.dp)
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Set / repetition specifications
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .border(1.dp, SurfaceBorder, RoundedCornerShape(6.dp))
            .background(Color(0xFFF8F9FA))
            .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "권장 세트 및 횟수",
              fontSize = 12.sp,
              fontWeight = FontWeight.Medium,
              color = TextSecondary
            )
            Text(
              text = setsAndReps,
              fontSize = 13.sp,
              fontWeight = FontWeight.SemiBold,
              color = TextPrimary
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Step by step list
        instructions.forEachIndexed { idx, inst ->
          Row(
            modifier = Modifier.padding(vertical = 2.dp),
            verticalAlignment = Alignment.Top
          ) {
            Text(
              text = "${idx + 1}. ",
              fontSize = 13.sp,
              fontWeight = FontWeight.SemiBold,
              color = PerformanceGreenPrimary
            )
            Text(
              text = inst,
              fontSize = 13.sp,
              color = TextSecondary,
              lineHeight = 18.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Audio Coach Toggle
        OutlinedButton(
          onClick = {
            if (isSpeaking) {
              ttsManager.stop()
              isSpeaking = false
            } else {
              ttsManager.speak(voiceScript)
              isSpeaking = true
            }
          },
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .testTag("coach_voice_button")
        ) {
          Icon(
            imageVector = if (isSpeaking) Icons.Default.Stop else Icons.AutoMirrored.Filled.VolumeUp,
            contentDescription = null,
            tint = TextPrimary,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (isSpeaking) "음성 가이드 일시중지" else "전문 코칭 음성 가이드 듣기",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = TextPrimary
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Set Completion Progress
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "진행 상태: ${currentSet} / ${maxSets} 세트",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = TextPrimary
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
            colors = ButtonDefaults.buttonColors(containerColor = PerformanceGreenPrimary),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.testTag("complete_set_button")
          ) {
            Text(
              text = if (currentSet < maxSets) "${currentSet}세트 완료" else "전체 세트 완료",
              fontSize = 13.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      }
    },
    confirmButton = {},
    dismissButton = {}
  )
}
