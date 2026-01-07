import { NativeModule, requireNativeModule } from 'expo';

export interface DeviceInfo {
  brand: string;
  manufacturer: string;
  model: string;
  deviceName: string;
  product: string;
  osName: string;
  osVersion: string;
  sdkVersion: number;
  hardware: string;
  fingerprint: string;
  isEmulator: boolean;
  boardName: string;
  bootloader: string;
  display: string;
  host: string;
  buildId: string;
  type: string;
  buildTags: string;
}

declare class ExpoDeviceInfoPlusModule extends NativeModule<{}> {
 
  brand: string;
  manufacturer: string;
  model: string;
  deviceName: string;
  product: string;
  osName: string;
  osVersion: string;
  sdkVersion: number;
  hardware: string;
  fingerprint: string;
 

 
  getBrand(): string;
  getManufacturer(): string;
  getModel(): string;
  getDeviceName(): string;
  getProduct(): string;
  getOsVersion(): string;
  getSdkVersion(): number;
  getHardware(): string;
  isEmulator(): boolean;

 
  getDeviceInfo(): Promise<DeviceInfo>;
}

// This call loads the native module object from the JSI.
export default requireNativeModule<ExpoDeviceInfoPlusModule>('ExpoDeviceInfoPlus');
