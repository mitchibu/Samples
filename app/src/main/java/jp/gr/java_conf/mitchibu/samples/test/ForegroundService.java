package jp.gr.java_conf.mitchibu.samples.test;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import java.util.Objects;

@SuppressLint("Registered")
public abstract class ForegroundService extends Service {
	public static <T extends ForegroundService> void start(Context context, Class<T> clazz) {
		start(context, clazz, null);
	}

	public static <T extends ForegroundService> void start(Context context, Class<T> clazz, Bundle params) {
		Intent intent = new Intent(context, clazz);
		if(params != null) intent.putExtras(params);
		ContextCompat.startForegroundService(context, intent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		String channelId = getChannelId();
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationManager nm = ContextCompat.getSystemService(this, NotificationManager.class);
			NotificationChannel channel = Objects.requireNonNull(nm).getNotificationChannel(channelId);
			if(channel == null) {
				channel = new NotificationChannel(channelId, getChannelName(), getChannelImportance());
				nm.createNotificationChannel(channel);
			}
		}

		NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId);
		int id = onCreateNotification(builder);

		Notification n = builder.build();
		n.flags |= Notification.FLAG_NO_CLEAR;
		startForeground(id, n);
	}

	@Override
	public void onDestroy() {
		stopForeground(true);
	}

	@TargetApi(Build.VERSION_CODES.N)
	protected int getChannelImportance() {
		return NotificationManager.IMPORTANCE_DEFAULT;
	}

	protected abstract String getChannelId();
	protected abstract CharSequence getChannelName();
	protected abstract int onCreateNotification(NotificationCompat.Builder builder);
}
