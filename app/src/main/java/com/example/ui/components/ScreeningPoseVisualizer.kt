package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AccentGold
import com.example.ui.theme.PineGreenPrimary

@Composable
fun ScreeningPoseVisualizer(
  iconType: String,
  modifier: Modifier = Modifier.width(160.dp).height(140.dp)
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val bodyColor = PineGreenPrimary
    val motionColor = AccentGold

    // Base ground line
    drawLine(
      color = Color.LightGray.copy(alpha = 0.5f),
      start = Offset(w * 0.1f, h * 0.9f),
      end = Offset(w * 0.9f, h * 0.9f),
      strokeWidth = 3f,
      cap = StrokeCap.Round
    )

    when (iconType) {
      "PELVIC_TILT" -> {
        // Golfer in address posture with pelvic tilt arrows
        val headCenter = Offset(w * 0.4f, h * 0.25f)
        drawCircle(color = bodyColor, radius = 14f, center = headCenter)
        // Torso inclined
        val hipPoint = Offset(w * 0.52f, h * 0.52f)
        drawLine(bodyColor, headCenter, hipPoint, strokeWidth = 10f, cap = StrokeCap.Round)
        // Knee and foot
        val kneePoint = Offset(w * 0.45f, h * 0.72f)
        val footPoint = Offset(w * 0.42f, h * 0.9f)
        drawLine(bodyColor, hipPoint, kneePoint, strokeWidth = 9f, cap = StrokeCap.Round)
        drawLine(bodyColor, kneePoint, footPoint, strokeWidth = 9f, cap = StrokeCap.Round)
        // Pelvic tilt bidirectional arc
        val arcPath = Path().apply {
          moveTo(w * 0.42f, h * 0.50f)
          quadraticTo(w * 0.56f, h * 0.46f, w * 0.64f, h * 0.54f)
        }
        drawPath(arcPath, motionColor, style = Stroke(width = 6f, cap = StrokeCap.Round))
        drawCircle(motionColor, radius = 6f, center = Offset(w * 0.64f, h * 0.54f))
      }

      "PELVIC_ROTATION" -> {
        // Front standing golfer with rotating hips
        val headCenter = Offset(w * 0.5f, h * 0.2f)
        drawCircle(color = bodyColor, radius = 14f, center = headCenter)
        val spineBottom = Offset(w * 0.5f, h * 0.52f)
        drawLine(bodyColor, headCenter, spineBottom, strokeWidth = 10f, cap = StrokeCap.Round)
        // Folded arms
        drawLine(bodyColor, Offset(w * 0.35f, h * 0.36f), Offset(w * 0.65f, h * 0.36f), strokeWidth = 8f, cap = StrokeCap.Round)
        // Legs
        drawLine(bodyColor, spineBottom, Offset(w * 0.4f, h * 0.9f), strokeWidth = 8f, cap = StrokeCap.Round)
        drawLine(bodyColor, spineBottom, Offset(w * 0.6f, h * 0.9f), strokeWidth = 8f, cap = StrokeCap.Round)
        // Rotation oval arrow around hips
        val rotPath = Path().apply {
          moveTo(w * 0.32f, h * 0.52f)
          quadraticTo(w * 0.5f, h * 0.60f, w * 0.68f, h * 0.52f)
        }
        drawPath(rotPath, motionColor, style = Stroke(width = 6f, cap = StrokeCap.Round))
        drawCircle(motionColor, radius = 5f, center = Offset(w * 0.68f, h * 0.52f))
      }

      "TORSO_ROTATION" -> {
        // Fixed lower body, rotating upper torso
        val headCenter = Offset(w * 0.55f, h * 0.2f)
        drawCircle(color = bodyColor, radius = 14f, center = headCenter)
        val spineBottom = Offset(w * 0.5f, h * 0.52f)
        drawLine(bodyColor, headCenter, spineBottom, strokeWidth = 10f, cap = StrokeCap.Round)
        // Rotation arrow on chest
        val rotChest = Path().apply {
          moveTo(w * 0.3f, h * 0.34f)
          quadraticTo(w * 0.5f, h * 0.26f, w * 0.72f, h * 0.36f)
        }
        drawPath(rotChest, motionColor, style = Stroke(width = 6f, cap = StrokeCap.Round))
        drawCircle(motionColor, radius = 5f, center = Offset(w * 0.72f, h * 0.36f))
        // Legs fixed
        drawLine(bodyColor, spineBottom, Offset(w * 0.38f, h * 0.9f), strokeWidth = 9f, cap = StrokeCap.Round)
        drawLine(bodyColor, spineBottom, Offset(w * 0.62f, h * 0.9f), strokeWidth = 9f, cap = StrokeCap.Round)
      }

      "OVERHEAD_SQUAT" -> {
        // Deep squat with club overhead
        val headCenter = Offset(w * 0.5f, h * 0.35f)
        drawCircle(color = bodyColor, radius = 13f, center = headCenter)
        // Club bar over head
        drawLine(motionColor, Offset(w * 0.22f, h * 0.16f), Offset(w * 0.78f, h * 0.16f), strokeWidth = 6f, cap = StrokeCap.Round)
        // Upright arms
        drawLine(bodyColor, Offset(w * 0.35f, h * 0.40f), Offset(w * 0.28f, h * 0.16f), strokeWidth = 7f, cap = StrokeCap.Round)
        drawLine(bodyColor, Offset(w * 0.65f, h * 0.40f), Offset(w * 0.72f, h * 0.16f), strokeWidth = 7f, cap = StrokeCap.Round)
        // Torso and deep bent thighs
        val hipPoint = Offset(w * 0.5f, h * 0.65f)
        drawLine(bodyColor, headCenter, hipPoint, strokeWidth = 10f, cap = StrokeCap.Round)
        // Knees out and feet flat
        drawLine(bodyColor, hipPoint, Offset(w * 0.32f, h * 0.63f), strokeWidth = 8f, cap = StrokeCap.Round)
        drawLine(bodyColor, Offset(w * 0.32f, h * 0.63f), Offset(w * 0.35f, h * 0.9f), strokeWidth = 8f, cap = StrokeCap.Round)
        drawLine(bodyColor, hipPoint, Offset(w * 0.68f, h * 0.63f), strokeWidth = 8f, cap = StrokeCap.Round)
        drawLine(bodyColor, Offset(w * 0.68f, h * 0.63f), Offset(w * 0.65f, h * 0.9f), strokeWidth = 8f, cap = StrokeCap.Round)
      }

      "SINGLE_LEG_BALANCE" -> {
        // One leg standing, one leg lifted at 90 deg
        val headCenter = Offset(w * 0.48f, h * 0.2f)
        drawCircle(color = bodyColor, radius = 14f, center = headCenter)
        val hipPoint = Offset(w * 0.48f, h * 0.52f)
        drawLine(bodyColor, headCenter, hipPoint, strokeWidth = 10f, cap = StrokeCap.Round)
        // Support leg
        drawLine(bodyColor, hipPoint, Offset(w * 0.46f, h * 0.9f), strokeWidth = 9f, cap = StrokeCap.Round)
        // Lifted leg 90 degrees
        val liftedKnee = Offset(w * 0.68f, h * 0.52f)
        val liftedFoot = Offset(w * 0.68f, h * 0.72f)
        drawLine(bodyColor, hipPoint, liftedKnee, strokeWidth = 8f, cap = StrokeCap.Round)
        drawLine(bodyColor, liftedKnee, liftedFoot, strokeWidth = 8f, cap = StrokeCap.Round)
        // Balance circle
        drawCircle(motionColor, radius = 8f, center = Offset(w * 0.46f, h * 0.9f), style = Stroke(3f))
      }

      "SHOULDER_EXTERNAL" -> {
        // Torso with L-shaped arms rotating back
        val headCenter = Offset(w * 0.5f, h * 0.25f)
        drawCircle(color = bodyColor, radius = 14f, center = headCenter)
        val spine = Offset(w * 0.5f, h * 0.65f)
        drawLine(bodyColor, headCenter, spine, strokeWidth = 10f, cap = StrokeCap.Round)
        // Right shoulder L shape
        val shoulderR = Offset(w * 0.65f, h * 0.38f)
        val elbowR = Offset(w * 0.75f, h * 0.38f)
        val handR = Offset(w * 0.78f, h * 0.18f)
        drawLine(bodyColor, Offset(w * 0.5f, h * 0.38f), elbowR, strokeWidth = 8f, cap = StrokeCap.Round)
        drawLine(bodyColor, elbowR, handR, strokeWidth = 8f, cap = StrokeCap.Round)
        // Arc motion
        val extPath = Path().apply {
          moveTo(w * 0.68f, h * 0.18f)
          quadraticTo(w * 0.80f, h * 0.12f, w * 0.86f, h * 0.22f)
        }
        drawPath(extPath, motionColor, style = Stroke(5f, cap = StrokeCap.Round))
        drawCircle(motionColor, radius = 5f, center = Offset(w * 0.86f, h * 0.22f))
      }

      "SHOULDER_INTERNAL" -> {
        val headCenter = Offset(w * 0.5f, h * 0.25f)
        drawCircle(color = bodyColor, radius = 14f, center = headCenter)
        val spine = Offset(w * 0.5f, h * 0.65f)
        drawLine(bodyColor, headCenter, spine, strokeWidth = 10f, cap = StrokeCap.Round)
        // Arm downward rotation
        val elbowR = Offset(w * 0.75f, h * 0.38f)
        val handR = Offset(w * 0.75f, h * 0.58f)
        drawLine(bodyColor, Offset(w * 0.5f, h * 0.38f), elbowR, strokeWidth = 8f, cap = StrokeCap.Round)
        drawLine(bodyColor, elbowR, handR, strokeWidth = 8f, cap = StrokeCap.Round)
        // Downward arc
        val intPath = Path().apply {
          moveTo(w * 0.78f, h * 0.44f)
          quadraticTo(w * 0.84f, h * 0.54f, w * 0.76f, h * 0.62f)
        }
        drawPath(intPath, motionColor, style = Stroke(5f, cap = StrokeCap.Round))
        drawCircle(motionColor, radius = 5f, center = Offset(w * 0.76f, h * 0.62f))
      }

      "TOE_TOUCH" -> {
        // Person bending down touching toes with straight legs
        val hipPoint = Offset(w * 0.38f, h * 0.48f)
        // Straight legs
        drawLine(bodyColor, hipPoint, Offset(w * 0.38f, h * 0.9f), strokeWidth = 9f, cap = StrokeCap.Round)
        // Folded forward upper body
        val headCenter = Offset(w * 0.52f, h * 0.68f)
        drawLine(bodyColor, hipPoint, headCenter, strokeWidth = 9f, cap = StrokeCap.Round)
        drawCircle(bodyColor, radius = 12f, center = headCenter)
        // Hands reaching to toes
        val handPoint = Offset(w * 0.42f, h * 0.88f)
        drawLine(bodyColor, Offset(w * 0.45f, h * 0.58f), handPoint, strokeWidth = 7f, cap = StrokeCap.Round)
        drawCircle(motionColor, radius = 6f, center = handPoint)
      }

      "SEATED_HIP" -> {
        // Chair and seated hip rotation
        drawLine(Color.Gray, Offset(w * 0.35f, h * 0.62f), Offset(w * 0.55f, h * 0.62f), strokeWidth = 6f)
        drawLine(Color.Gray, Offset(w * 0.4f, h * 0.62f), Offset(w * 0.4f, h * 0.9f), strokeWidth = 5f)
        // Seated torso
        val headCenter = Offset(w * 0.45f, h * 0.3f)
        drawCircle(bodyColor, radius = 13f, center = headCenter)
        drawLine(bodyColor, headCenter, Offset(w * 0.45f, h * 0.62f), strokeWidth = 9f, cap = StrokeCap.Round)
        // Knee 90 deg and foot swinging out
        val kneePoint = Offset(w * 0.62f, h * 0.62f)
        drawLine(bodyColor, Offset(w * 0.45f, h * 0.62f), kneePoint, strokeWidth = 8f, cap = StrokeCap.Round)
        val footPoint = Offset(w * 0.75f, h * 0.82f)
        drawLine(bodyColor, kneePoint, footPoint, strokeWidth = 8f, cap = StrokeCap.Round)
        drawCircle(motionColor, radius = 6f, center = footPoint)
      }

      "LATERAL_FLEXION" -> {
        // Standing side tilt
        val headCenter = Offset(w * 0.58f, h * 0.22f)
        drawCircle(bodyColor, radius = 14f, center = headCenter)
        val spineCurved = Path().apply {
          moveTo(w * 0.5f, h * 0.6f)
          quadraticTo(w * 0.52f, h * 0.4f, w * 0.58f, h * 0.25f)
        }
        drawPath(spineCurved, bodyColor, style = Stroke(9f, cap = StrokeCap.Round))
        // Reaching hand down to knee
        drawLine(bodyColor, Offset(w * 0.56f, h * 0.38f), Offset(w * 0.68f, h * 0.72f), strokeWidth = 7f, cap = StrokeCap.Round)
        drawCircle(motionColor, radius = 6f, center = Offset(w * 0.68f, h * 0.72f))
        // Legs
        drawLine(bodyColor, Offset(w * 0.5f, h * 0.6f), Offset(w * 0.44f, h * 0.9f), strokeWidth = 8f, cap = StrokeCap.Round)
        drawLine(bodyColor, Offset(w * 0.5f, h * 0.6f), Offset(w * 0.56f, h * 0.9f), strokeWidth = 8f, cap = StrokeCap.Round)
      }

      "WRIST_MOBILITY" -> {
        // Forearm and cocked wrist
        drawLine(bodyColor, Offset(w * 0.2f, h * 0.6f), Offset(w * 0.55f, h * 0.6f), strokeWidth = 10f, cap = StrokeCap.Round)
        // Hand cocked up 70 deg
        drawLine(bodyColor, Offset(w * 0.55f, h * 0.6f), Offset(w * 0.68f, h * 0.32f), strokeWidth = 9f, cap = StrokeCap.Round)
        drawCircle(bodyColor, radius = 10f, center = Offset(w * 0.68f, h * 0.32f))
        // Angular arc
        val arcPath = Path().apply {
          moveTo(w * 0.62f, h * 0.58f)
          quadraticTo(w * 0.68f, h * 0.50f, w * 0.64f, h * 0.38f)
        }
        drawPath(arcPath, motionColor, style = Stroke(5f, cap = StrokeCap.Round))
      }

      "ANKLE_MOBILITY" -> {
        // Vertical wall line on right
        drawLine(Color.DarkGray, Offset(w * 0.8f, h * 0.2f), Offset(w * 0.8f, h * 0.9f), strokeWidth = 8f)
        // Foot on floor, heel down
        val footHeel = Offset(w * 0.4f, h * 0.9f)
        val footToe = Offset(w * 0.65f, h * 0.9f)
        drawLine(bodyColor, footHeel, footToe, strokeWidth = 9f, cap = StrokeCap.Round)
        // Knee touching wall
        val kneeAtWall = Offset(w * 0.78f, h * 0.62f)
        drawLine(bodyColor, footHeel, kneeAtWall, strokeWidth = 9f, cap = StrokeCap.Round)
        drawCircle(motionColor, radius = 7f, center = kneeAtWall)
      }

      "GLUTE_BRIDGE" -> {
        // Mat on floor
        val shoulderGround = Offset(w * 0.25f, h * 0.78f)
        drawCircle(bodyColor, radius = 13f, center = Offset(w * 0.16f, h * 0.78f))
        // Elevated hips in bridge
        val elevatedHips = Offset(w * 0.48f, h * 0.52f)
        drawLine(bodyColor, shoulderGround, elevatedHips, strokeWidth = 9f, cap = StrokeCap.Round)
        // Support foot on floor
        val footGround = Offset(w * 0.58f, h * 0.9f)
        val kneeSupport = Offset(w * 0.55f, h * 0.58f)
        drawLine(bodyColor, elevatedHips, kneeSupport, strokeWidth = 8f, cap = StrokeCap.Round)
        drawLine(bodyColor, kneeSupport, footGround, strokeWidth = 8f, cap = StrokeCap.Round)
        // Extended straight leg
        val extendedFoot = Offset(w * 0.84f, h * 0.52f)
        drawLine(bodyColor, elevatedHips, extendedFoot, strokeWidth = 8f, cap = StrokeCap.Round)
        drawCircle(motionColor, radius = 6f, center = extendedFoot)
      }

      else -> {
        // General athletic stance
        drawCircle(bodyColor, radius = 14f, center = Offset(w * 0.5f, h * 0.25f))
        drawLine(bodyColor, Offset(w * 0.5f, h * 0.35f), Offset(w * 0.5f, h * 0.65f), strokeWidth = 9f)
        drawLine(bodyColor, Offset(w * 0.5f, h * 0.65f), Offset(w * 0.4f, h * 0.9f), strokeWidth = 8f)
        drawLine(bodyColor, Offset(w * 0.5f, h * 0.65f), Offset(w * 0.6f, h * 0.9f), strokeWidth = 8f)
      }
    }
  }
}
