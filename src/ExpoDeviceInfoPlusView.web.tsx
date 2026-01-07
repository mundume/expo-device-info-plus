import * as React from 'react';

import { ExpoDeviceInfoPlusViewProps } from './ExpoDeviceInfoPlus.types';

export default function ExpoDeviceInfoPlusView(props: ExpoDeviceInfoPlusViewProps) {
  return (
    <div>
      <iframe
        style={{ flex: 1 }}
        src={props.url}
        onLoad={() => props.onLoad({ nativeEvent: { url: props.url } })}
      />
    </div>
  );
}
