package com.example.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GolfCourseBackground
import com.example.ui.theme.GolfGrassBorder
import com.example.ui.theme.PerformanceGreenPrimary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextTertiary

/**
 * Restrained, high-legibility Top Bar modeled after Apple Health & Garmin.
 * Strictly avoids decorative emojis and unnecessary cartoon emblems.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GolfTopBar(
  title: String,
  subtitle: String? = null,
  canNavigateBack: Boolean = false,
  onBackClick: () -> Unit = {},
  actionContent: @Composable () -> Unit = {}
) {
  TopAppBar(
    title = {
      Column {
        Text(
          text = title,
          fontSize = 17.sp,
          fontWeight = FontWeight.SemiBold,
          color = TextPrimary,
          letterSpacing = (-0.2).sp
        )
        if (subtitle != null) {
          Text(
            text = subtitle,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = TextTertiary,
            letterSpacing = 0.sp
          )
        }
      }
    },
    navigationIcon = {
      if (canNavigateBack) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier.testTag("top_bar_back_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "이전 화면",
            tint = TextPrimary,
            modifier = Modifier.size(20.dp)
          )
        }
      }
    },
    actions = { actionContent() },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = GolfCourseBackground,
      titleContentColor = TextPrimary
    ),
    modifier = Modifier
      .fillMaxWidth()
      .border(0.5.dp, GolfGrassBorder)
  )
}
