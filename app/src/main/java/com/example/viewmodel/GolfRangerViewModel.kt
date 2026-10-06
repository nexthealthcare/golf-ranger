package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.AnalysisEngine
import com.example.data.ScreeningDataSource
import com.example.model.AnalysisReport
import com.example.model.GolferProfile
import com.example.model.ScreeningGrade
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class GolfScreen {
  HOME,
  SURVEY,
  SCREENING,
  REPORT,
  ROUTINE
}

class GolfRangerViewModel : ViewModel() {

  private val _currentScreen = MutableStateFlow(GolfScreen.HOME)
  val currentScreen: StateFlow<GolfScreen> = _currentScreen.asStateFlow()

  private val _golferProfile = MutableStateFlow(GolferProfile())
  val golferProfile: StateFlow<GolferProfile> = _golferProfile.asStateFlow()

  // 13개 신체 검진 결과 맵
  private val _screeningResults = MutableStateFlow<Map<Int, ScreeningGrade>>(
    // 기본 초기값으로 샘플 상태를 제공해 편리하게 시작할 수 있게 함
    mapOf(
      1 to ScreeningGrade.LIMITED, // 골반 틸트
      2 to ScreeningGrade.PASS,
      3 to ScreeningGrade.LIMITED, // 흉추 회전
      4 to ScreeningGrade.RESTRICTED, // 오버헤드 스쿼트
      5 to ScreeningGrade.PASS,
      6 to ScreeningGrade.PASS,
      7 to ScreeningGrade.LIMITED,
      8 to ScreeningGrade.LIMITED,
      9 to ScreeningGrade.PASS,
      10 to ScreeningGrade.PASS,
      11 to ScreeningGrade.PASS,
      12 to ScreeningGrade.RESTRICTED, // 발목
      13 to ScreeningGrade.LIMITED
    )
  )
  val screeningResults: StateFlow<Map<Int, ScreeningGrade>> = _screeningResults.asStateFlow()

  private val _currentScreeningIndex = MutableStateFlow(0)
  val currentScreeningIndex: StateFlow<Int> = _currentScreeningIndex.asStateFlow()

  private val _analysisReport = MutableStateFlow<AnalysisReport?>(null)
  val analysisReport: StateFlow<AnalysisReport?> = _analysisReport.asStateFlow()

  private val _completedExercises = MutableStateFlow<Set<String>>(emptySet())
  val completedExercises: StateFlow<Set<String>> = _completedExercises.asStateFlow()

  init {
    // 앱 시작 시 초기 분석 가설 리포트 생성
    refreshAnalysis()
  }

  fun navigateTo(screen: GolfScreen) {
    _currentScreen.value = screen
  }

  fun updateHandicap(value: Int) {
    _golferProfile.update { it.copy(handicap = value) }
  }

  fun updateStrongestArea(value: String) {
    _golferProfile.update { it.copy(strongestArea = value) }
  }

  fun updateWeakestArea(value: String) {
    _golferProfile.update { it.copy(weakestArea = value) }
  }

  fun updateMemorableMistake(value: String) {
    _golferProfile.update { it.copy(memorableMistake = value) }
  }

  fun updateShortTermGoal(value: String) {
    _golferProfile.update { it.copy(shortTermGoal = value) }
  }

  fun updateLongTermGoal(value: String) {
    _golferProfile.update { it.copy(longTermGoal = value) }
  }

  fun toggleRoundIssue(issue: String) {
    _golferProfile.update {
      val current = it.roundIssues.toMutableSet()
      if (current.contains(issue)) {
        if (current.size > 1) current.remove(issue)
      } else {
        current.add(issue)
      }
      it.copy(roundIssues = current)
    }
  }

  fun setSwingVideo(uri: String?, name: String?) {
    _golferProfile.update {
      it.copy(swingVideoUri = uri, swingVideoFileName = name)
    }
  }

  fun setSwingHabit(habit: String) {
    _golferProfile.update {
      it.copy(selectedSwingHabit = habit)
    }
  }

  fun setScreeningGrade(itemId: Int, grade: ScreeningGrade) {
    _screeningResults.update {
      it.toMutableMap().apply { put(itemId, grade) }
    }
  }

  fun nextScreening() {
    val total = ScreeningDataSource.screeningItems.size
    if (_currentScreeningIndex.value < total - 1) {
      _currentScreeningIndex.value += 1
    } else {
      // 13개 모두 완료 시 리포트 생성 후 리포트 화면으로 이동
      refreshAnalysis()
      navigateTo(GolfScreen.REPORT)
    }
  }

  fun prevScreening() {
    if (_currentScreeningIndex.value > 0) {
      _currentScreeningIndex.value -= 1
    }
  }

  fun setScreeningIndex(index: Int) {
    _currentScreeningIndex.value = index.coerceIn(0, ScreeningDataSource.screeningItems.size - 1)
  }

  fun refreshAnalysis() {
    val report = AnalysisEngine.analyze(
      profile = _golferProfile.value,
      screeningResults = _screeningResults.value
    )
    _analysisReport.value = report
  }

  fun toggleExerciseComplete(exerciseTitle: String) {
    _completedExercises.update {
      val mutable = it.toMutableSet()
      if (mutable.contains(exerciseTitle)) {
        mutable.remove(exerciseTitle)
      } else {
        mutable.add(exerciseTitle)
      }
      mutable
    }
  }

  fun resetForRetest() {
    _currentScreeningIndex.value = 0
    navigateTo(GolfScreen.SCREENING)
  }
}
