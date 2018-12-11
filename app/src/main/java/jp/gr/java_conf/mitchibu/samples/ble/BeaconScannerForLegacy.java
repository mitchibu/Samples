package jp.gr.java_conf.mitchibu.samples.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.UUID;

public class BeaconScannerForLegacy extends BeaconScanner {
	private BluetoothAdapter.LeScanCallback callback = null;

	@Override
	public synchronized void start() {
		getBluetoothAdapter().startLeScan(null, callback = new BluetoothAdapter.LeScanCallback() {
			@Override
			public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
				deliverEvent(device, rssi, scanRecord);
			}
		});
	}

	@Override
	public synchronized void stop() {
		try {
			getBluetoothAdapter().stopLeScan(callback);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
