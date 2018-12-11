package jp.gr.java_conf.mitchibu.samples.ble;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

@TargetApi(Build.VERSION_CODES.M)
public abstract class BeaconScanner {
	public static final int STATUS_OK = 0;
	public static final int STATUS_BLUETOOTH_NOT_AVAILABLE = -2;
	public static final int STATUS_LOCATION_PERMISSION_NOT_GRANTED = -3;
	public static final int STATUS_BLUETOOTH_NOT_ENABLED = -4;
	public static final int STATUS_LOCATION_NOT_ENABLED = -5;

	public static int status(Context context) {
		if(!isBluetoothAvailable()) return STATUS_BLUETOOTH_NOT_AVAILABLE;
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isLocationPermissionGranted(context)) return STATUS_LOCATION_PERMISSION_NOT_GRANTED;
		if(!isBluetoothEnabled()) return STATUS_BLUETOOTH_NOT_ENABLED;
		if(!isLocationEnabled(context)) return STATUS_LOCATION_NOT_ENABLED;
		return STATUS_OK;
	}

	private static boolean isBluetoothAvailable() {
		return BluetoothAdapter.getDefaultAdapter() != null;
	}

	private static boolean isLocationPermissionGranted(Context context) {
		return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
	}

	private static boolean isBluetoothEnabled() {
		return BluetoothAdapter.getDefaultAdapter().isEnabled();
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("ObsoleteSdkInt")
	private static boolean isLocationEnabled(Context context) {
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			String providers = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
			return !TextUtils.isEmpty(providers) && (providers.contains(LocationManager.GPS_PROVIDER) || providers.contains(LocationManager.NETWORK_PROVIDER));
		} else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
			try {
				switch(Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE)) {
				case Settings.Secure.LOCATION_MODE_HIGH_ACCURACY:
				case Settings.Secure.LOCATION_MODE_SENSORS_ONLY:
				case Settings.Secure.LOCATION_MODE_BATTERY_SAVING:
					return true;
				case Settings.Secure.LOCATION_MODE_OFF:
				default:
					return false;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
			return lm.isLocationEnabled();
		}
		return false;
	}

	public enum ScanMode {
		LowPower,
		Balanced,
		LowLatency
	}

	private ScanListener scanListener = null;
	ScanMode mode = ScanMode.LowLatency;

	private BluetoothAdapter adapter = null;

	public abstract void start();
	public abstract void stop();

	BluetoothAdapter getBluetoothAdapter() {
		if(adapter == null) adapter = BluetoothAdapter.getDefaultAdapter();
		return adapter;
	}

	void deliverEvent(BluetoothDevice device, int rssi, byte[] scanRecord) {
		if(scanListener == null) return;
		scanListener.onScan(device, rssi, scanRecord);
	}

	public interface ScanListener {
		void onScan(BluetoothDevice device, int rssi, byte[] payload);
	}

	public static class Builder {
		private final BeaconScanner scanner;

		public Builder() {
			scanner = Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ? new BeaconScannerForLegacy() : new BeaconScannerForLollipop();
		}

		public Builder callback(ScanListener scanListener) {
			scanner.scanListener = scanListener;
			return this;
		}

		public Builder scanMode(ScanMode mode) {
			scanner.mode = mode;
			return this;
		}

		public BeaconScanner build() {
			return scanner;
		}
	}
}
