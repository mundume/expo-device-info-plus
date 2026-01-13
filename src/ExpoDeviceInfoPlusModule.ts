import { NativeModule, requireNativeModule } from 'expo';

export type DeviceInfo = {
  name: string;
  brand: string;
  model: string;
  version: string;
};

export type BatteryLevelEvent = {
  level: number;
};

export type BatteryStatusEvent = {
  isCharging: boolean;
};

declare class ExpoDeviceInfoPlusModule extends NativeModule<{
  onBatteryLevelChanged: (event: BatteryLevelEvent) => void;
  onBatteryStatusChanged: (event: BatteryStatusEvent) => void;
}> {
  getDeviceInfo(): Promise<DeviceInfo>;
  getBatteryLevel(): Promise<number>;
  getBatteryTemperature(): Promise<number>;
  getBatteryVoltage(): Promise<number>;
}

export default requireNativeModule<ExpoDeviceInfoPlusModule>('ExpoDeviceInfoPlus');
