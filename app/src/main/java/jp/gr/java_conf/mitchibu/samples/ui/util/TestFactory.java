package jp.gr.java_conf.mitchibu.samples.ui.util;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import jp.gr.java_conf.mitchibu.samples.model.dao.MyDatabase;
import jp.gr.java_conf.mitchibu.samples.test.SavableViewModelFactory;

public class TestFactory extends SavableViewModelFactory {
	private final MyDatabase db;

	public TestFactory(MyDatabase db) {
		this.db = db;
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		try {
			return restore(modelClass.getConstructor(MyDatabase.class).newInstance(db));
		} catch(Exception e) {
			throw new RuntimeException("Cannot create an instance of " + modelClass, e);
		}
	}
}
