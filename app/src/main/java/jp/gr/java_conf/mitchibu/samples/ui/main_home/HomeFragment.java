package jp.gr.java_conf.mitchibu.samples.ui.main_home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import java.util.Objects;

import jp.gr.java_conf.mitchibu.samples.R;
import jp.gr.java_conf.mitchibu.samples.databinding.HomeFragmentBinding;
import jp.gr.java_conf.mitchibu.samples.ui.base.BindLayout;
import jp.gr.java_conf.mitchibu.samples.ui.base.DataBindingFragment;

@BindLayout(R.layout.home_fragment)
public class HomeFragment extends DataBindingFragment<HomeFragmentBinding> {
	private HomeViewModel model;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		android.util.Log.v("test", "savedInstanceState: " + savedInstanceState);
		model = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(HomeViewModel.class);
		model.position.observe(this, new Observer<Integer>() {
			@Override
			public void onChanged(@Nullable Integer position) {
				if(position == null) position = 0;
				getBinding().pager.setCurrentItem(position, false);
			}
		});
		initPager();
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		android.util.Log.v("test", "onSaveInstanceState");
		outState.putInt("position", getBinding().pager.getCurrentItem());
	}

	private void initPager() {
		getBinding().pager.setAdapter(new CategoryAdapter(getChildFragmentManager()));
		getBinding().pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				model.position.setValue(position);
			}
		});
		getBinding().tab.setupWithViewPager(getBinding().pager);
	}
}
