import { requireNativeView } from 'expo';
import * as React from 'react';

import { ExpoDeviceInfoPlusViewProps } from './ExpoDeviceInfoPlus.types';

const NativeView: React.ComponentType<ExpoDeviceInfoPlusViewProps> =
  requireNativeView('ExpoDeviceInfoPlus');

export default function ExpoDeviceInfoPlusView(props: ExpoDeviceInfoPlusViewProps) {
  return <NativeView {...props} />;
}
