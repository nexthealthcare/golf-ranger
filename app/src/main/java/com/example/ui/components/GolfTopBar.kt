package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.SportsGolf
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.FairwayGreenPrimary
import com.example.ui.theme.TextMainDark
import com.example.ui.theme.TextSubtle

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
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(FairwayGreenPrimary.copy(alpha = 0.12f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.SportsGolf,
            contentDescription = null,
            tint = FairwayGreenPrimary,
            modifier = Modifier.size(20.dp)
          )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
          Text(
            text = title,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = TextMainDark
          )
          if (subtitle != null) {
            Text(
              text = subtitle,
              fontSize = 11.sp,
              color = TextSubtle
            )
          }
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
            contentDescription = "뒤로가기",
            tint = TextMainDark
          )
        }
      }
    },
    actions = { actionContent() },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = Color.White,
      titleContentColor = TextMainDark
    ),
    modifier = Modifier
      .fillMaxWidth()
      .border(0.5.dp, MaterialTheme.colorScheme.outline)
  )
}
