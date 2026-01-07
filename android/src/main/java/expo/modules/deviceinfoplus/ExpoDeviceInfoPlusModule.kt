package expo.modules.deviceinfoplus

import android.os.Build
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition

class ExpoDeviceInfoPlusModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("ExpoDeviceInfoPlus")

    // Device constants - available synchronously
    Constants(
            "brand" to Build.BRAND,
            "manufacturer" to Build.MANUFACTURER,
            "model" to Build.MODEL,
            "deviceName" to Build.DEVICE,
            "product" to Build.PRODUCT,
            "osName" to "Android",
            "osVersion" to Build.VERSION.RELEASE,
            "sdkVersion" to Build.VERSION.SDK_INT,
            "hardware" to Build.HARDWARE,
            "fingerprint" to Build.FINGERPRINT,
            "isEmulator" to isEmulator()
    )

    // Synchronous functions
    Function("getBrand") { Build.BRAND }
    Function("getManufacturer") { Build.MANUFACTURER }
    Function("getModel") { Build.MODEL }
    Function("getDeviceName") { Build.DEVICE }
    Function("getProduct") { Build.PRODUCT }
    Function("getOsVersion") { Build.VERSION.RELEASE }
    Function("getSdkVersion") { Build.VERSION.SDK_INT }
    Function("getHardware") { Build.HARDWARE }
    Function("isEmulator") { isEmulator() }

    // Async function to get all device info at once
    AsyncFunction("getDeviceInfo") {
      mapOf(
              "brand" to Build.BRAND,
              "manufacturer" to Build.MANUFACTURER,
              "model" to Build.MODEL,
              "deviceName" to Build.DEVICE,
              "product" to Build.PRODUCT,
              "osName" to "Android",
              "osVersion" to Build.VERSION.RELEASE,
              "sdkVersion" to Build.VERSION.SDK_INT,
              "hardware" to Build.HARDWARE,
              "fingerprint" to Build.FINGERPRINT,
              "isEmulator" to isEmulator(),
              "boardName" to Build.BOARD,
              "bootloader" to Build.BOOTLOADER,
              "display" to Build.DISPLAY,
              "host" to Build.HOST,
              "buildId" to Build.ID,
              "type" to Build.TYPE,
              "buildTags" to Build.TAGS
      )
    }
  }

  private fun isEmulator(): Boolean {
    return (Build.FINGERPRINT.startsWith("generic") ||
            Build.FINGERPRINT.startsWith("unknown") ||
            Build.MODEL.contains("google_sdk") ||
            Build.MODEL.contains("Emulator") ||
            Build.MODEL.contains("Android SDK built for x86") ||
            Build.MANUFACTURER.contains("Genymotion") ||
            Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic") ||
            Build.PRODUCT == "google_sdk")
  }
}
