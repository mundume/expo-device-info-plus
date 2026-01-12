import { NativeModule, requireNativeModule } from 'expo';

export type DeviceInfo = {
  name: string;
  brand: string;
  model: string;
  version: string;
};

export type BatteryEvent = {
  level: number;
};

declare class ExpoDeviceInfoPlusModule extends NativeModule<{
  onBatteryLevelChanged: (event: BatteryEvent) => void;
}> {
  getDeviceInfo(): Promise<DeviceInfo>;
  getBatteryLevel(): Promise<number>;
  startBatteryListener(): Promise<void>;
  stopBatteryListener(): Promise<void>;
}

export default requireNativeModule<ExpoDeviceInfoPlusModule>('ExpoDeviceInfoPlus');
