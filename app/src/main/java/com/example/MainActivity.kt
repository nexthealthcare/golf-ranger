package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.sp
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ReportScreen
import com.example.ui.screens.RoutineScreen
import com.example.ui.screens.ScreeningScreen
import com.example.ui.screens.SurveyScreen
import com.example.ui.theme.AccentGold
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PineGreenPrimary
import com.example.viewmodel.GolfRangerViewModel
import com.example.viewmodel.GolfScreen

class MainActivity : ComponentActivity() {
  private val viewModel: GolfRangerViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          GolfRangerApp(viewModel = viewModel)
        }
      }
    }
  }
}

@Composable
fun GolfRangerApp(viewModel: GolfRangerViewModel) {
  val currentScreen by viewModel.currentScreen.collectAsState()

  // 시스템 뒤로가기 제어 (BackHandler)
  BackHandler(enabled = currentScreen != GolfScreen.HOME) {
    when (currentScreen) {
      GolfScreen.SURVEY -> viewModel.navigateTo(GolfScreen.HOME)
      GolfScreen.SCREENING -> viewModel.navigateTo(GolfScreen.SURVEY)
      GolfScreen.REPORT -> viewModel.navigateTo(GolfScreen.HOME)
      GolfScreen.ROUTINE -> viewModel.navigateTo(GolfScreen.REPORT)
      GolfScreen.HOME -> Unit
    }
  }

  Scaffold(
    bottomBar = {
      NavigationBar(
        modifier = Modifier
          .windowInsetsPadding(WindowInsets.navigationBars)
          .testTag("main_bottom_nav"),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = PineGreenPrimary
      ) {
        NavigationBarItem(
          selected = currentScreen == GolfScreen.HOME,
          onClick = { viewModel.navigateTo(GolfScreen.HOME) },
          icon = { Icon(Icons.Default.Home, contentDescription = "홈") },
          label = { Text("홈", fontSize = 11.sp) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            selectedTextColor = PineGreenPrimary,
            indicatorColor = PineGreenPrimary
          ),
          modifier = Modifier.testTag("nav_item_home")
        )

        NavigationBarItem(
          selected = currentScreen == GolfScreen.SURVEY,
          onClick = { viewModel.navigateTo(GolfScreen.SURVEY) },
          icon = { Icon(Icons.Default.Assignment, contentDescription = "설문") },
          label = { Text("설문", fontSize = 11.sp) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            selectedTextColor = PineGreenPrimary,
            indicatorColor = PineGreenPrimary
          ),
          modifier = Modifier.testTag("nav_item_survey")
        )

        NavigationBarItem(
          selected = currentScreen == GolfScreen.SCREENING,
          onClick = { viewModel.navigateTo(GolfScreen.SCREENING) },
          icon = { Icon(Icons.Default.FitnessCenter, contentDescription = "신체검진") },
          label = { Text("신체검진", fontSize = 11.sp) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            selectedTextColor = PineGreenPrimary,
            indicatorColor = PineGreenPrimary
          ),
          modifier = Modifier.testTag("nav_item_screening")
        )

        NavigationBarItem(
          selected = currentScreen == GolfScreen.REPORT,
          onClick = {
            viewModel.refreshAnalysis()
            viewModel.navigateTo(GolfScreen.REPORT)
          },
          icon = { Icon(Icons.Default.Psychology, contentDescription = "AI분석") },
          label = { Text("AI분석", fontSize = 11.sp) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            selectedTextColor = PineGreenPrimary,
            indicatorColor = PineGreenPrimary
          ),
          modifier = Modifier.testTag("nav_item_report")
        )

        NavigationBarItem(
          selected = currentScreen == GolfScreen.ROUTINE,
          onClick = { viewModel.navigateTo(GolfScreen.ROUTINE) },
          icon = { Icon(Icons.Default.Timer, contentDescription = "트레이닝") },
          label = { Text("루틴", fontSize = 11.sp) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            selectedTextColor = PineGreenPrimary,
            indicatorColor = PineGreenPrimary
          ),
          modifier = Modifier.testTag("nav_item_routine")
        )
      }
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      AnimatedContent(
        targetState = currentScreen,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "ScreenTransition"
      ) { screen ->
        when (screen) {
          GolfScreen.HOME -> HomeScreen(viewModel = viewModel)
          GolfScreen.SURVEY -> SurveyScreen(viewModel = viewModel)
          GolfScreen.SCREENING -> ScreeningScreen(viewModel = viewModel)
          GolfScreen.REPORT -> ReportScreen(viewModel = viewModel)
          GolfScreen.ROUTINE -> RoutineScreen(viewModel = viewModel)
        }
      }
    }
  }
}
