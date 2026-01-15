package expo.modules.deviceinfoplus

import android.content.Context
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.views.ExpoView
import kotlin.math.sin

class ExpressiveWavyView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {
  private var progressValue by mutableFloatStateOf(0.5f)

  private val composeView =
          ComposeView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

            setContent {
              MaterialTheme {
                WavyProgressIndicator(
                        modifier = Modifier.fillMaxWidth().height(4.dp),
                        progress = progressValue
                )
              }
            }
          }

  init {
    addView(composeView)
  }

  fun setProgress(value: Float) {
    progressValue = value.coerceIn(0f, 1f)
  }
}

@Composable
fun WavyProgressIndicator(
        modifier: Modifier = Modifier,
        progress: Float = 0.5f,
        color: Color = MaterialTheme.colorScheme.primary
) {
  val infiniteTransition = rememberInfiniteTransition(label = "wave")
  val animatedOffset by
          infiniteTransition.animateFloat(
                  initialValue = 0f,
                  targetValue = 1f,
                  animationSpec =
                          infiniteRepeatable(
                                  animation = tween(durationMillis = 1000, easing = LinearEasing),
                                  repeatMode = RepeatMode.Restart
                          ),
                  label = "waveOffset"
          )

  Canvas(modifier = modifier) {
    val width = size.width
    val height = size.height
    val progressWidth = width * progress
    val waveLength = 40f
    val amplitude = height / 4
    val centerY = height / 2

    val path =
            Path().apply {
              moveTo(0f, centerY)

              var x = 0f
              while (x <= progressWidth) {
                val y =
                        centerY +
                                amplitude *
                                        sin((x / waveLength + animatedOffset) * 2 * Math.PI)
                                                .toFloat()
                lineTo(x, y)
                x += 2f
              }
            }

    drawPath(path = path, color = color, style = Stroke(width = height, cap = StrokeCap.Round))
  }
}
