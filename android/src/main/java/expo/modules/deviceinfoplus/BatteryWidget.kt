package expo.modules.deviceinfoplus

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

class BatteryWidget : GlanceAppWidget() {

        override suspend fun provideGlance(context: Context, id: GlanceId) {
                val batteryLevel = getBatteryLevel(context)

                provideContent { BatteryWidgetContent(batteryLevel) }
        }

        private fun getBatteryLevel(context: Context): Int {
                val intent =
                        context.registerReceiver(
                                null,
                                IntentFilter(android.content.Intent.ACTION_BATTERY_CHANGED)
                        )

                val level = intent?.getIntExtra(android.os.BatteryManager.EXTRA_LEVEL, -1) ?: -1
                val scale = intent?.getIntExtra(android.os.BatteryManager.EXTRA_SCALE, -1) ?: -1

                return if (level >= 0 && scale > 0) {
                        (level * 100) / scale
                } else {
                        -1
                }
        }

        @Composable
        private fun BatteryWidgetContent(batteryLevel: Int) {
                Column(
                        modifier =
                                GlanceModifier.fillMaxSize()
                                        .background(ColorProvider(Color(0xFF1E1E1E)))
                                        .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {
                        Text(text = "🔋", style = TextStyle(fontSize = 32.sp))
                        Text(
                                text = "Battery",
                                style =
                                        TextStyle(
                                                color = ColorProvider(Color.White),
                                                fontSize = 14.sp
                                        )
                        )
                        Text(
                                text = "$batteryLevel%",
                                style =
                                        TextStyle(
                                                color = ColorProvider(Color.White),
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.Bold
                                        )
                        )
                }
        }
}

class BatteryWidgetReceiver : GlanceAppWidgetReceiver() {
        override val glanceAppWidget: GlanceAppWidget = BatteryWidget()

        override fun onEnabled(context: Context) {
                super.onEnabled(context)

                // Start the foreground service
                val intent = Intent(context, BatteryWidgetUpdateService::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(intent)
                } else {
                        context.startService(intent)
                }
        }

        override fun onDisabled(context: Context) {
                super.onDisabled(context)

                // Stop the foreground service when last widget is removed
                val intent = Intent(context, BatteryWidgetUpdateService::class.java)
                context.stopService(intent)
        }
}
