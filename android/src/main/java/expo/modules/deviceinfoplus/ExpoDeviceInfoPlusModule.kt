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

    Events("onBatteryStatusChanged", "onBatteryLevelChanged")

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
                  // Extract BOTH values from the SAME intent
                  val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
                  val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1

                  // Send BOTH events
                  sendEvent("onBatteryLevelChanged", mapOf("level" to level))
                  sendEvent(
                          "onBatteryStatusChanged",
                          mapOf("isCharging" to (status == BatteryManager.BATTERY_STATUS_CHARGING))
                  )
                }
              }

      context.registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
      return@AsyncFunction null
    }

    // Remove getBatteryChargingInfo entirely - not needed

    AsyncFunction("stopBatteryListener") {
      val context = appContext.reactContext ?: return@AsyncFunction null
      batteryReceiver?.let { context.unregisterReceiver(it) }
      batteryReceiver = null
      return@AsyncFunction null
    }

    AsyncFunction("getBatteryTemperature") {
      val context = appContext.reactContext ?: return@AsyncFunction null

      val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
      // Temperature comes in tenths of a degree Celsius
      val tempTenths = intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) ?: 0

      // Convert to regular Celsius
      tempTenths / 10.0
    }
    AsyncFunction("getBatteryVoltage") {
      val context = appContext.reactContext ?: return@AsyncFunction null
      val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
      intent?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) ?: 0
    }

    OnDestroy {
      val context = appContext.reactContext ?: return@OnDestroy
      batteryReceiver?.let { context.unregisterReceiver(it) }
      batteryReceiver = null
    }
  }
}
