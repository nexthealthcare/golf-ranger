package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AnalysisEngine
import com.example.data.ScreeningDataSource
import com.example.model.GolferProfile
import com.example.model.ScreeningGrade
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("골프레인저", appName)
  }

  @Test
  fun `verify screening items size is 13`() {
    assertEquals(13, ScreeningDataSource.screeningItems.size)
  }

  @Test
  fun `verify analysis engine synthesis`() {
    val profile = GolferProfile(
      handicap = 20,
      roundIssues = setOf("드라이버 슬라이스 / 푸시", "아이언 뒷땅 / 탑핑이 잦음")
    )
    val testGrades = mapOf(
      4 to ScreeningGrade.RESTRICTED, // 오버헤드 스쿼트
      12 to ScreeningGrade.RESTRICTED // 발목
    )
    val report = AnalysisEngine.analyze(profile, testGrades)

    assertNotNull(report)
    assertEquals(100, report.bodyContribution + report.swingContribution + report.gameContribution)
    assertTrue(report.exercises.size in 3..5)
    assertTrue(report.drills.size in 2..3)
  }
}
