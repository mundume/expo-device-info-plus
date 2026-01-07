export type { DeviceInfo } from './ExpoDeviceInfoPlusModule';

// Export the native module as default
export { default } from './ExpoDeviceInfoPlusModule';

// Named exports for convenience
import ExpoDeviceInfoPlus from './ExpoDeviceInfoPlusModule';

// Constants (synchronous access)
export const brand = ExpoDeviceInfoPlus.brand;
export const manufacturer = ExpoDeviceInfoPlus.manufacturer;
export const model = ExpoDeviceInfoPlus.model;
export const deviceName = ExpoDeviceInfoPlus.deviceName;
export const product = ExpoDeviceInfoPlus.product;
export const osName = ExpoDeviceInfoPlus.osName;
export const osVersion = ExpoDeviceInfoPlus.osVersion;
export const sdkVersion = ExpoDeviceInfoPlus.sdkVersion;
export const hardware = ExpoDeviceInfoPlus.hardware;
export const fingerprint = ExpoDeviceInfoPlus.fingerprint;
export const isEmulator = ExpoDeviceInfoPlus.isEmulator;

// Function exports
export const getBrand = () => ExpoDeviceInfoPlus.getBrand();
export const getManufacturer = () => ExpoDeviceInfoPlus.getManufacturer();
export const getModel = () => ExpoDeviceInfoPlus.getModel();
export const getDeviceName = () => ExpoDeviceInfoPlus.getDeviceName();
export const getProduct = () => ExpoDeviceInfoPlus.getProduct();
export const getOsVersion = () => ExpoDeviceInfoPlus.getOsVersion();
export const getSdkVersion = () => ExpoDeviceInfoPlus.getSdkVersion();
export const getHardware = () => ExpoDeviceInfoPlus.getHardware();
export const getDeviceInfo = () => ExpoDeviceInfoPlus.getDeviceInfo();
