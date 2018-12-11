package jp.gr.java_conf.mitchibu.samples;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;

import com.squareup.picasso.Picasso;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import jp.gr.java_conf.mitchibu.samples.di.DaggerAppComponent;

public class MyApplication extends DaggerApplication {
	static {
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
	}

//	@Inject
//	public MyDatabase db;

	BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			android.util.Log.v("test", "isLocationEnabled: " + isLocationEnabled(context));
		}
	};

	@SuppressWarnings("deprecation")
	@SuppressLint("ObsoleteSdkInt")
	static boolean isLocationEnabled(Context context) {
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
			LocationManager lm = (LocationManager)context.getSystemService(LOCATION_SERVICE);
			return lm.isLocationEnabled();
		}
		return false;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Picasso picasso = new Picasso.Builder(this).build();
		picasso.setIndicatorsEnabled(true);
//		picasso.setLoggingEnabled(true);
		Picasso.setSingletonInstance(picasso);

		IntentFilter filter = new IntentFilter();
		filter.addAction(LocationManager.MODE_CHANGED_ACTION);
		registerReceiver(receiver, filter);
	}

	@Override
	protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
		return DaggerAppComponent
				.builder()
				.application(this)
				.create(this);
	}
}
