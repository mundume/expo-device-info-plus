package expo.modules.deviceinfoplus

import android.os.Build
import android.content.Context
import android.os.BatteryManager
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition


class ExpoDeviceInfoPlusModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("ExpoDeviceInfoPlus")


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
          val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

          val level = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
          return@AsyncFunction level.toDouble()

      }
  }
}
