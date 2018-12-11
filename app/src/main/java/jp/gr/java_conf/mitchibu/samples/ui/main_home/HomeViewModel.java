package jp.gr.java_conf.mitchibu.samples.ui.main_home;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
	public final MutableLiveData<Integer> position = new MutableLiveData<>();
}
