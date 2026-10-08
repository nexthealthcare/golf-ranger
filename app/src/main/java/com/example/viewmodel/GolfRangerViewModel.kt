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

  // SFMA 5대 신체 검진 결과 맵 (Flexion, Extension, Rotation, SLS, Squat)
  private val _screeningResults = MutableStateFlow<Map<Int, ScreeningGrade>>(
    mapOf(
      1 to ScreeningGrade.LIMITED,    // SFMA Flexion (전신 굴곡)
      2 to ScreeningGrade.LIMITED,    // SFMA Extension (전신 신전)
      3 to ScreeningGrade.RESTRICTED, // SFMA Rotation (전신 회전)
      4 to ScreeningGrade.PASS,       // SFMA SLS (외발서기 밸런스)
      5 to ScreeningGrade.RESTRICTED  // SFMA Squat (오버헤드 딥 스쿼트)
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

  fun toggleStrongestArea(area: String) {
    _golferProfile.update {
      val current = it.strongestAreas.toMutableSet()
      if (current.contains(area)) {
        if (current.size > 1) current.remove(area)
      } else {
        current.add(area)
      }
      it.copy(
        strongestAreas = current,
        strongestArea = current.firstOrNull() ?: area
      )
    }
  }

  fun toggleWeakestArea(area: String) {
    _golferProfile.update {
      val current = it.weakestAreas.toMutableSet()
      if (current.contains(area)) {
        if (current.size > 1) current.remove(area)
      } else {
        current.add(area)
      }
      it.copy(
        weakestAreas = current,
        weakestArea = current.firstOrNull() ?: area
      )
    }
  }

  fun toggleSwingHabit(habit: String) {
    _golferProfile.update {
      val current = it.selectedSwingHabits.toMutableSet()
      if (current.contains(habit)) {
        if (current.size > 1) current.remove(habit)
      } else {
        current.add(habit)
      }
      it.copy(
        selectedSwingHabits = current,
        selectedSwingHabit = current.firstOrNull() ?: habit
      )
    }
  }

  fun toggleMemorableMistake(mistake: String) {
    _golferProfile.update {
      val current = it.memorableMistakes.toMutableSet()
      if (current.contains(mistake)) {
        if (current.size > 1) current.remove(mistake)
      } else {
        current.add(mistake)
      }
      it.copy(
        memorableMistakes = current,
        memorableMistake = current.firstOrNull() ?: mistake
      )
    }
  }

  fun toggleTargetGoal(goal: String) {
    _golferProfile.update {
      val current = it.targetGoals.toMutableSet()
      if (current.contains(goal)) {
        if (current.size > 1) current.remove(goal)
      } else {
        current.add(goal)
      }
      it.copy(
        targetGoals = current,
        shortTermGoal = current.firstOrNull() ?: goal
      )
    }
  }

  fun updateStrongestArea(value: String) {
    toggleStrongestArea(value)
  }

  fun updateWeakestArea(value: String) {
    toggleWeakestArea(value)
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
    toggleSwingHabit(habit)
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
      // SFMA 5개 모두 완료 시 리포트 생성 후 리포트 화면으로 이동
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
