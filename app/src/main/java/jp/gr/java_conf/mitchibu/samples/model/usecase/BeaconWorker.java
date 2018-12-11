package jp.gr.java_conf.mitchibu.samples.model.usecase;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;

import com.neovisionaries.bluetooth.ble.advertising.ADPayloadParser;
import com.neovisionaries.bluetooth.ble.advertising.ADStructure;
import com.neovisionaries.bluetooth.ble.advertising.IBeacon;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import androidx.work.OneTimeWorkRequest;
import androidx.work.Result;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import jp.gr.java_conf.mitchibu.samples.ble.BeaconScanner;

public class BeaconWorker extends Worker {
	private static final String TAG = BeaconWorker.class.getName() + ".TAG";
	private static final long DURATION = 5L;
	private static final long INTERVAL = 5L;

	public static void start(boolean delay) {
		OneTimeWorkRequest.Builder builder = new OneTimeWorkRequest.Builder(BeaconWorker.class);
		if(delay) builder.setInitialDelay(INTERVAL, TimeUnit.SECONDS);
		builder.addTag(TAG);
		final OneTimeWorkRequest request = builder.build();
		WorkManager.getInstance().enqueue(request);
		log(request.getId(), "request");
	}

	public static void cancel() {
		WorkManager.getInstance().cancelAllWorkByTag(TAG);
	}

	private static void log(UUID id, String text) {
		android.util.Log.v(BeaconWorker.class.getSimpleName(), String.format("[%s] %s", id, text));
	}

	private final CountDownLatch countDown = new CountDownLatch(1);

	public BeaconWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
	}

	@NonNull
	@Override
	public Result doWork() {
		log("doWork");
		int status = BeaconScanner.status(getApplicationContext());
		if(status == BeaconScanner.STATUS_OK) {
			BeaconScanner scanner = getBeaconScanner();
			scanner.start();

			try {
				countDown.await(DURATION, TimeUnit.SECONDS);
				scanner.stop();
				if(!isStopped()) {
					start(true);
				}
			} catch(InterruptedException e) {
				e.printStackTrace();
				return Result.failure();
			}
		} else {
			log("status: " + status);
		}
		return Result.success();
	}

	@Override
	public void onStopped() {
		log("onStopped");
		countDown.countDown();
	}

	private BeaconScanner getBeaconScanner() {
		return new BeaconScanner.Builder()
				.callback(new BeaconScanner.ScanListener() {
					@Override
					public void onScan(BluetoothDevice device, int rssi, byte[] payload) {
						log("Found device: " + device);
						List<ADStructure> structures = ADPayloadParser.getInstance().parse(payload);
						if(structures == null) return;

						for(Iterator<ADStructure> it = structures.iterator(); !isStopped() && it.hasNext();) {
							ADStructure structure = it.next();
							if(structure instanceof IBeacon) {
								log("Found iBeacon: " + structure);
							}
						}
					}
				})
				.scanMode(BeaconScanner.ScanMode.LowLatency)
				.build();
	}

	private void log(String text) {
		log(getId(), text);
	}
}
