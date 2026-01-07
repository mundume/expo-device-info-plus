import { registerWebModule, NativeModule } from 'expo';

import { ExpoDeviceInfoPlusModuleEvents } from './ExpoDeviceInfoPlus.types';

class ExpoDeviceInfoPlusModule extends NativeModule<ExpoDeviceInfoPlusModuleEvents> {
  PI = Math.PI;
  async setValueAsync(value: string): Promise<void> {
    this.emit('onChange', { value });
  }
  hello() {
    return 'Hello world! 👋';
  }
}

export default registerWebModule(ExpoDeviceInfoPlusModule, 'ExpoDeviceInfoPlusModule');
