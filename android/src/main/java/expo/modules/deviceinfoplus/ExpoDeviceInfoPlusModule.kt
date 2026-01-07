package expo.modules.deviceinfoplus

import android.os.Build
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition

class ExpoDeviceInfoPlusModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("ExpoDeviceInfoPlus")

    // Device constants - available synchronously
    AsyncFunction("getDeviceInfo") {
      mapOf(
              "name" to Build.MANUFACTURER,
              "brand" to Build.BRAND,
              "model" to Build.MODEL,
              "version" to Build.VERSION.RELEASE
      )
    }
  }
}
