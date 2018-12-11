package jp.gr.java_conf.mitchibu.samples.ble;

import android.bluetooth.BluetoothDevice;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Cancellable;

public class RxBeaconScanner {
	public static Observable<Result> scan(@NonNull final BeaconScanner.Builder builder) {
		return Observable.create(new ObservableOnSubscribe<Result>() {
			@Override
			public void subscribe(final ObservableEmitter<Result> emitter) {
				final BeaconScanner scanner = builder.callback(new BeaconScanner.ScanListener() {
					@Override
					public void onScan(BluetoothDevice device, int rssi, byte[] payload) {
						emitter.onNext(new Result(device, rssi, payload));
					}
				}).build();
				scanner.start();

				emitter.setCancellable(new Cancellable() {
					@Override
					public void cancel() {
						scanner.stop();
					}
				});
			}
		});
	}

	public static class Result {
		public final BluetoothDevice device;
		public final int rssi;
		public final byte[] payload;

		Result(BluetoothDevice device, int rssi, byte[] payload) {
			this.device = device;
			this.rssi = rssi;
			this.payload = payload;
		}
	}
}
