import { NativeModule, requireNativeModule } from 'expo';

export type DeviceInfo = {
  name: string
  brand: string
  model: string
  version: string
}


declare class ExpoDeviceInfoPlusModule extends NativeModule<{}> {
 
  getDeviceInfo(): Promise<DeviceInfo>;
}

// This call loads the native module object from the JSI.
export default requireNativeModule<ExpoDeviceInfoPlusModule>('ExpoDeviceInfoPlus');
