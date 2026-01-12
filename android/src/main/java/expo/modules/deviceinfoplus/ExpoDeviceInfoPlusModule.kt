package expo.modules.deviceinfoplus

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition

class ExpoDeviceInfoPlusModule : Module() {

  private var batteryReceiver: BroadcastReceiver? = null

  override fun definition() = ModuleDefinition {
    Name("ExpoDeviceInfoPlus")

    Events("onBatteryLevelChanged")

    AsyncFunction("getDeviceInfo") {
      mapOf(
              "name" to Build.MANUFACTURER,
              "brand" to Build.BRAND,
              "model" to Build.MODEL,
              "version" to Build.VERSION.RELEASE
      )
    }

    AsyncFunction("getBatteryLevel") {
      val context = appContext.reactContext ?: return@AsyncFunction null
      val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
      bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }

    AsyncFunction("startBatteryListener") {
      val context = appContext.reactContext ?: return@AsyncFunction null

      if (batteryReceiver != null) return@AsyncFunction null

      batteryReceiver =
              object : BroadcastReceiver() {
                override fun onReceive(ctx: Context?, intent: Intent?) {
                  val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: return
                  sendEvent("onBatteryLevelChanged", mapOf("level" to level))
                }
              }

      context.registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

      return@AsyncFunction null
    }

    AsyncFunction("stopBatteryListener") {
      val context = appContext.reactContext ?: return@AsyncFunction null
      batteryReceiver?.let { context.unregisterReceiver(it) }
      batteryReceiver = null
      return@AsyncFunction null
    }

    OnDestroy {
      val context = appContext.reactContext ?: return@OnDestroy
      batteryReceiver?.let { context.unregisterReceiver(it) }
      batteryReceiver = null
    }
  }
}
