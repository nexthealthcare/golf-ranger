package com.example.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BodyExercise
import com.example.ui.theme.AccentGold
import com.example.ui.theme.PineGreenPrimary
import kotlinx.coroutines.delay

@Composable
fun ExerciseTimerDialog(
  exercise: BodyExercise,
  onDismiss: () -> Unit,
  onCompleted: () -> Unit
) {
  var remainingSeconds by remember { mutableIntStateOf(exercise.durationSeconds) }
  var isRunning by remember { mutableStateOf(false) }
  val totalSeconds = exercise.durationSeconds

  LaunchedEffect(isRunning, remainingSeconds) {
    if (isRunning && remainingSeconds > 0) {
      delay(1000L)
      remainingSeconds -= 1
    } else if (remainingSeconds == 0) {
      isRunning = false
    }
  }

  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(20.dp),
    containerColor = MaterialTheme.colorScheme.surface,
    title = {
      Column {
        Text(
          text = exercise.title,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = PineGreenPrimary
        )
        Text(
          text = "타겟: ${exercise.targetArea} • ${exercise.repsOrTime}",
          fontSize = 12.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // 타이머 원형 프로그레스
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(140.dp)
            .padding(8.dp)
        ) {
          val progress = if (totalSeconds > 0) remainingSeconds.toFloat() / totalSeconds.toFloat() else 0f

          CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.size(130.dp),
            color = PineGreenPrimary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            strokeWidth = 8.dp
          )

          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val minutes = remainingSeconds / 60
            val seconds = remainingSeconds % 60
            Text(
              text = String.format("%02d:%02d", minutes, seconds),
              fontSize = 30.sp,
              fontWeight = FontWeight.Bold,
              color = PineGreenPrimary
            )
            Text(
              text = if (remainingSeconds == 0) "세트 완료!" else if (isRunning) "운동 진행 중" else "준비",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 조교 코칭 키포인트
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(AccentGold.copy(alpha = 0.15f))
            .padding(10.dp)
        ) {
          Text(
            text = "조교 팁: ${exercise.coachingKey}",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 컨트롤러
        Row(
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(
            onClick = {
              remainingSeconds = totalSeconds
              isRunning = false
            },
            modifier = Modifier.testTag("reset_timer_button")
          ) {
            Icon(Icons.Default.Refresh, contentDescription = "초기화", tint = MaterialTheme.colorScheme.onSurfaceVariant)
          }

          Spacer(modifier = Modifier.width(16.dp))

          FilledTonalButton(
            onClick = { isRunning = !isRunning },
            colors = ButtonDefaults.filledTonalButtonColors(
              containerColor = if (isRunning) Color(0xFFC04030) else PineGreenPrimary,
              contentColor = Color.White
            ),
            modifier = Modifier.testTag("play_pause_timer_button")
          ) {
            Icon(
              imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
              contentDescription = if (isRunning) "일시정지" else "시작"
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(if (isRunning) "일시 정지" else "타이머 시작")
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          onCompleted()
          onDismiss()
        },
        colors = ButtonDefaults.buttonColors(containerColor = PineGreenPrimary),
        modifier = Modifier.testTag("finish_exercise_button")
      ) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text("세트 완료 완료")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("닫기", color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
    }
  )
}
