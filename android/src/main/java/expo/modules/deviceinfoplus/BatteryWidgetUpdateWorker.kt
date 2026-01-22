package expo.modules.deviceinfoplus

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.glance.appwidget.GlanceAppWidgetManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BatteryWidgetUpdateService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.IO + Job())
    private var updateJob: Job? = null

    companion object {
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "battery_widget_channel"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
        startUpdating()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun startUpdating() {
        updateJob =
                serviceScope.launch {
                    while (true) {
                        try {
                            updateWidget()
                            delay(1000) // Update every 1 second
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
    }

    private suspend fun updateWidget() {
        val manager = GlanceAppWidgetManager(this)
        val glanceIds = manager.getGlanceIds(BatteryWidget::class.java)

        glanceIds.forEach { glanceId -> BatteryWidget().update(this, glanceId) }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                    NotificationChannel(
                                    CHANNEL_ID,
                                    "Battery Widget Updates",
                                    NotificationManager.IMPORTANCE_LOW
                            )
                            .apply { description = "Keeps battery widget updated in real-time" }

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Battery Widget Active")
                .setContentText("Updating battery widget every second")
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .build()
    }

    override fun onDestroy() {
        updateJob?.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
