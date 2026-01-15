import { requireNativeViewManager } from 'expo-modules-core';
import * as React from 'react';

type ExpressiveWavyProgressProps = {
  progress: number;  // 0.0 to 1.0
  style?: any;
};

// Use the correct exported name
const NativeView = requireNativeViewManager('ExpoDeviceInfoPlus_ExpressiveWavyView');

export default function ExpressiveWavyProgress(props: ExpressiveWavyProgressProps) {
  return <NativeView {...props} />;
}