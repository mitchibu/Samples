package jp.gr.java_conf.mitchibu.samples.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import dagger.android.support.DaggerAppCompatActivity;

public class DataBindingActivity<T extends ViewDataBinding> extends DaggerAppCompatActivity {
	private T binding;

	public T getBinding() {
		return binding;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BindLayout layout = getClass().getAnnotation(BindLayout.class);
		binding = DataBindingUtil.setContentView(this, Objects.requireNonNull(layout).value());
	}
}
