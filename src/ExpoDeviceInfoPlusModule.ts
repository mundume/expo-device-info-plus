import { NativeModule, requireNativeModule } from 'expo';

import { ExpoDeviceInfoPlusModuleEvents } from './ExpoDeviceInfoPlus.types';

declare class ExpoDeviceInfoPlusModule extends NativeModule<ExpoDeviceInfoPlusModuleEvents> {
  PI: number;
  hello(): string;
  setValueAsync(value: string): Promise<void>;
}

// This call loads the native module object from the JSI.
export default requireNativeModule<ExpoDeviceInfoPlusModule>('ExpoDeviceInfoPlus');
