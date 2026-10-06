package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.ui.theme.PerformanceGreenPrimary
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

/**
 * Biomechanical Kinetic Schematic (Technical angle & vector gauge)
 * Resembles Trackman / WHOOP / Garmin athletic diagnostic schematics.
 * Completely free of cartoons, characters, or illustrations.
 */
@Composable
fun ScreeningPoseVisualizer(
  iconType: String,
  modifier: Modifier = Modifier.width(180.dp).height(120.dp)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height

    // Baseline grid / technical reference line
    drawLine(
      color = SurfaceBorder,
      start = Offset(w * 0.15f, h * 0.85f),
      end = Offset(w * 0.85f, h * 0.85f),
      strokeWidth = 1.5f
    )

    val primaryColor = PerformanceGreenPrimary
    val neutralAxisColor = TextTertiary.copy(alpha = 0.5f)

    when (iconType) {
      "PELVIC_TILT" -> {
        // Pelvic tilt anterior/posterior neutral axis schematic
        // Spine neutral line
        drawLine(neutralAxisColor, Offset(w * 0.5f, h * 0.2f), Offset(w * 0.5f, h * 0.85f), strokeWidth = 1.5f)
        // Pelvic plane tilted
        drawLine(primaryColor, Offset(w * 0.32f, h * 0.65f), Offset(w * 0.68f, h * 0.55f), strokeWidth = 3f, cap = StrokeCap.Round)
        // Angle arc
        drawArc(
          color = primaryColor,
          startAngle = -20f,
          sweepAngle = 40f,
          useCenter = false,
          topLeft = Offset(w * 0.44f, h * 0.52f),
          size = Size(w * 0.12f, h * 0.16f),
          style = Stroke(width = 1.5f)
        )
        // Center joint node
        drawCircle(primaryColor, radius = 4f, center = Offset(w * 0.5f, h * 0.6f))
      }

      "PELVIC_ROTATION" -> {
        // Horizontal pelvic rotation angle schematic
        drawLine(neutralAxisColor, Offset(w * 0.25f, h * 0.5f), Offset(w * 0.75f, h * 0.5f), strokeWidth = 1.5f)
        // Rotated axis (+30 deg)
        val rotatedPath = Path().apply {
          moveTo(w * 0.30f, h * 0.62f)
          lineTo(w * 0.70f, h * 0.38f)
        }
        drawPath(rotatedPath, color = primaryColor, style = Stroke(width = 3f, cap = StrokeCap.Round))
        // Center kinetic rotation arc
        drawArc(
          color = primaryColor,
          startAngle = -30f,
          sweepAngle = 60f,
          useCenter = false,
          topLeft = Offset(w * 0.42f, h * 0.40f),
          size = Size(w * 0.16f, h * 0.20f),
          style = Stroke(width = 1.5f)
        )
        drawCircle(primaryColor, radius = 4f, center = Offset(w * 0.5f, h * 0.5f))
      }

      "TORSO_ROTATION" -> {
        // Thoracic rotation vs Pelvic fixed base
        // Fixed pelvic base (neutral)
        drawLine(neutralAxisColor, Offset(w * 0.35f, h * 0.75f), Offset(w * 0.65f, h * 0.75f), strokeWidth = 2.5f)
        // Thoracic upper plane (rotated 60 deg)
        drawLine(primaryColor, Offset(w * 0.28f, h * 0.45f), Offset(w * 0.72f, h * 0.25f), strokeWidth = 3f, cap = StrokeCap.Round)
        // Connecting vertical kinetic column
        drawLine(neutralAxisColor, Offset(w * 0.5f, h * 0.35f), Offset(w * 0.5f, h * 0.75f), strokeWidth = 1.5f)
        drawCircle(primaryColor, radius = 4f, center = Offset(w * 0.5f, h * 0.35f))
      }

      "OVERHEAD_SQUAT" -> {
        // Squat kinetic hinge: Overhead plane & Hip drop angle
        // Overhead club line
        drawLine(primaryColor, Offset(w * 0.25f, h * 0.2f), Offset(w * 0.75f, h * 0.2f), strokeWidth = 2.5f, cap = StrokeCap.Round)
        // Knee-hip 90 degree vector
        val squatVector = Path().apply {
          moveTo(w * 0.35f, h * 0.85f)
          lineTo(w * 0.42f, h * 0.60f)
          lineTo(w * 0.50f, h * 0.62f)
          lineTo(w * 0.58f, h * 0.60f)
          lineTo(w * 0.65f, h * 0.85f)
        }
        drawPath(squatVector, color = primaryColor, style = Stroke(width = 2.5f, cap = StrokeCap.Round))
        drawCircle(primaryColor, radius = 3.5f, center = Offset(w * 0.50f, h * 0.62f))
      }

      "ANKLE_MOBILITY" -> {
        // Ankle dorsiflexion wall angle vector
        // Vertical wall line
        drawLine(neutralAxisColor, Offset(w * 0.75f, h * 0.25f), Offset(w * 0.75f, h * 0.85f), strokeWidth = 2f)
        // Foot ground contact
        drawLine(neutralAxisColor, Offset(w * 0.35f, h * 0.85f), Offset(w * 0.65f, h * 0.85f), strokeWidth = 2f)
        // Tibia dorsiflexion forward vector
        drawLine(primaryColor, Offset(w * 0.40f, h * 0.85f), Offset(w * 0.73f, h * 0.60f), strokeWidth = 3f, cap = StrokeCap.Round)
        // Angle arc at heel
        drawArc(
          color = primaryColor,
          startAngle = -35f,
          sweepAngle = 35f,
          useCenter = false,
          topLeft = Offset(w * 0.34f, h * 0.72f),
          size = Size(w * 0.14f, h * 0.16f),
          style = Stroke(width = 1.5f)
        )
        drawCircle(primaryColor, radius = 3.5f, center = Offset(w * 0.73f, h * 0.60f))
      }

      "DRILL_BUTT" -> {
        // Alignment plane butt contact line
        drawLine(neutralAxisColor, Offset(w * 0.65f, h * 0.2f), Offset(w * 0.65f, h * 0.85f), strokeWidth = 2f)
        // Spine posture line in contact
        drawLine(primaryColor, Offset(w * 0.45f, h * 0.3f), Offset(w * 0.64f, h * 0.55f), strokeWidth = 3f, cap = StrokeCap.Round)
        // Leg support line
        drawLine(primaryColor, Offset(w * 0.64f, h * 0.55f), Offset(w * 0.52f, h * 0.85f), strokeWidth = 3f, cap = StrokeCap.Round)
        // Hinge contact node
        drawCircle(primaryColor, radius = 4f, center = Offset(w * 0.64f, h * 0.55f))
      }

      "DRILL_TEE" -> {
        // Clean low-point sweep arc
        val arcPath = Path().apply {
          moveTo(w * 0.25f, h * 0.65f)
          quadraticTo(w * 0.50f, h * 0.82f, w * 0.75f, h * 0.65f)
        }
        drawPath(arcPath, color = primaryColor, style = Stroke(width = 2.5f, cap = StrokeCap.Round))
        // Tee reference pin
        drawLine(neutralAxisColor, Offset(w * 0.5f, h * 0.85f), Offset(w * 0.5f, h * 0.78f), strokeWidth = 2f)
        drawCircle(primaryColor, radius = 3.5f, center = Offset(w * 0.5f, h * 0.80f))
      }

      else -> {
        // General biomechanical joint axis
        drawLine(neutralAxisColor, Offset(w * 0.5f, h * 0.25f), Offset(w * 0.5f, h * 0.85f), strokeWidth = 1.5f)
        drawLine(primaryColor, Offset(w * 0.35f, h * 0.45f), Offset(w * 0.65f, h * 0.65f), strokeWidth = 3f, cap = StrokeCap.Round)
        drawCircle(primaryColor, radius = 4f, center = Offset(w * 0.5f, h * 0.55f))
      }
    }
  }
}
