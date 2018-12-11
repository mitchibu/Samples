package jp.gr.java_conf.mitchibu.samples.test;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;

public abstract class AndroidSavableViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
	private Bundle saveInstanceState = null;

	public AndroidSavableViewModelFactory(@NonNull Application app) {
		super(app);
	}

	public AndroidSavableViewModelFactory with(Bundle saveInstanceState) {
		this.saveInstanceState = saveInstanceState;
		return this;
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return restore(super.create(modelClass));
	}

	protected  <T extends ViewModel> T restore(T vm) {
		if(saveInstanceState != null && (vm instanceof SavableViewModel)) ((SavableViewModel)vm).from(saveInstanceState);
		return vm;
	}
}
