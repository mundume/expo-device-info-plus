import { useState, useEffect } from 'react';
import ExpoDeviceInfoPlus, { DeviceInfo} from 'expo-device-info-plus';
import { Button, SafeAreaView, ScrollView, Text, View, StyleSheet } from 'react-native';

export default function App() {
  const [fullDeviceInfo, setFullDeviceInfo] = useState<DeviceInfo | null>(null);
  const [batteryInfo, setBatteryInfo] = useState<number | null>(null);

  const fetchDeviceInfo = async () => {
    const info = await ExpoDeviceInfoPlus.getDeviceInfo();
    setFullDeviceInfo(info);
  };

  const fetchBatteryInfo = async () => {
    const info = await ExpoDeviceInfoPlus.getBatteryLevel();
    setBatteryInfo(info);
  };
  useEffect(() => {
  const subscription = ExpoDeviceInfoPlus.addListener(
    "onBatteryLevelChanged",
    (event: { level: number }) => {
      console.log(event.level);
      setBatteryInfo(event.level);
    }
  );

  ExpoDeviceInfoPlus.startBatteryListener();

  return () => {
    subscription.remove();
    ExpoDeviceInfoPlus.stopBatteryListener();
  };
}, []);



  useEffect(() => {
    fetchDeviceInfo();
   
  }, []);

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView style={styles.container}>
        <Text style={styles.header}>📱 Device Info Plus</Text>
        
        <Group name="Quick Info (Constants)">
          <InfoRow label="Brand" value={fullDeviceInfo?.brand} />
          <InfoRow label="Model" value={fullDeviceInfo?.model} />
          <InfoRow label="Manufacturer" value={fullDeviceInfo?.name} />
          <InfoRow label="OS Version" value={fullDeviceInfo?.version} />
        </Group>

        <Group name="Battery Info">
          <InfoRow label="Battery Level" value={batteryInfo?.toString() || 'N/A'} />
        </Group>
     
      </ScrollView>
    </SafeAreaView>
  );
}

function Group(props: { name: string; children: React.ReactNode }) {
  return (
    <View style={styles.group}>
      <Text style={styles.groupHeader}>{props.name}</Text>
      {props.children}
    </View>
  );
}

function InfoRow({ label, value }: { label: string; value: string }) {
  return (
    <View style={styles.row}>
      <Text style={styles.label}>{label}</Text>
      <Text style={styles.value}>{value}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  header: {
    fontSize: 28,
    fontWeight: 'bold',
    margin: 20,
    textAlign: 'center',
  },
  groupHeader: {
    fontSize: 18,
    fontWeight: '600',
    marginBottom: 12,
    color: '#333',
  },
  group: {
    margin: 16,
    backgroundColor: '#fff',
    borderRadius: 12,
    padding: 16,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  row: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingVertical: 8,
    borderBottomWidth: 1,
    borderBottomColor: '#eee',
  },
  label: {
    fontSize: 14,
    color: '#666',
    flex: 1,
  },
  value: {
    fontSize: 14,
    fontWeight: '500',
    color: '#333',
    flex: 1,
    textAlign: 'right',
  },
  infoContainer: {
    marginTop: 12,
  },
});
