package jp.gr.java_conf.mitchibu.samples.test;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;

public class SavableViewModelFactory extends ViewModelProvider.NewInstanceFactory {
	private Bundle saveInstanceState = null;

	public SavableViewModelFactory with(Bundle saveInstanceState) {
		this.saveInstanceState = saveInstanceState;
		return this;
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return restore(super.create(modelClass));
	}

	protected <T extends ViewModel> T restore(T vm) {
		if(saveInstanceState != null && (vm instanceof SavableViewModel)) ((SavableViewModel)vm).from(saveInstanceState);
		return vm;
	}
}
