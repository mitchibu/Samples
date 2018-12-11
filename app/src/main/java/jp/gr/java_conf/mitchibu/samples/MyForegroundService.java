package jp.gr.java_conf.mitchibu.samples;

import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import jp.gr.java_conf.mitchibu.samples.test.ForegroundService;

public class MyForegroundService extends ForegroundService {
	@Override
	protected String getChannelId() {
		return "test_id";
	}

	@Override
	protected CharSequence getChannelName() {
		return "test_name";
	}

	@Override
	protected int onCreateNotification(NotificationCompat.Builder builder) {
		builder.setContentTitle("test title");
		builder.setContentText("test text");
		builder.setAutoCancel(false);
		builder.setSmallIcon(R.mipmap.ic_launcher);
		return 1;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}
}
