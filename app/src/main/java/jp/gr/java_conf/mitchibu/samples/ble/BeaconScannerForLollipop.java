package jp.gr.java_conf.mitchibu.samples.ble;

import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;

import java.util.ArrayList;
import java.util.List;

public class BeaconScannerForLollipop extends BeaconScanner {
	private final List<ScanFilter> scanFilters = new ArrayList<>();
	private final ScanSettings.Builder scanSettingsBuilder = new ScanSettings.Builder();

	private ScanCallback callback = null;
	private BluetoothLeScanner scanner = null;

	@Override
	public synchronized void start() {
		scanSettingsBuilder.setScanMode(scanMode());
		scanner = getBluetoothAdapter().getBluetoothLeScanner();
		scanner.startScan(scanFilters, scanSettingsBuilder.build(), callback = new ScanCallback() {
			@Override
			public void onScanResult(int callbackType, ScanResult result) {
				ScanRecord scanRecord = result.getScanRecord();
				if(scanRecord == null) return;
				deliverEvent(result.getDevice(), result.getRssi(), scanRecord.getBytes());
			}
		});
	}

	@Override
	public synchronized void stop() {
		try {
			scanner.stopScan(callback);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private int scanMode() {
		switch(mode) {
		case Balanced:
			return ScanSettings.SCAN_MODE_BALANCED;
		case LowPower:
			return ScanSettings.SCAN_MODE_LOW_POWER;
		default:
			return ScanSettings.SCAN_MODE_LOW_LATENCY;
		}
	}
}
