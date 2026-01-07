// Reexport the native module. On web, it will be resolved to ExpoDeviceInfoPlusModule.web.ts
// and on native platforms to ExpoDeviceInfoPlusModule.ts
export { default } from './ExpoDeviceInfoPlusModule';
export { default as ExpoDeviceInfoPlusView } from './ExpoDeviceInfoPlusView';
export * from  './ExpoDeviceInfoPlus.types';
