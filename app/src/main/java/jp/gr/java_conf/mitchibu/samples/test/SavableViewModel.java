package jp.gr.java_conf.mitchibu.samples.test;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.NonNull;

public abstract class SavableViewModel extends ViewModel {
	public abstract void to(@NonNull Bundle bundle);
	public abstract void from(@NonNull Bundle bundle);
}
