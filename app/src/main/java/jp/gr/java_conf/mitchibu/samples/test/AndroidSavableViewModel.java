package jp.gr.java_conf.mitchibu.samples.test;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.Bundle;
import android.support.annotation.NonNull;

public abstract class AndroidSavableViewModel extends AndroidViewModel {
	public AndroidSavableViewModel(@NonNull Application application) {
		super(application);
	}

	public abstract void to(@NonNull Bundle bundle);
	public abstract void from(@NonNull Bundle bundle);
}
