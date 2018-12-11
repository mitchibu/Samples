package jp.gr.java_conf.mitchibu.samples.ui.shop;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.gr.java_conf.mitchibu.samples.R;
import jp.gr.java_conf.mitchibu.samples.databinding.ShopListFragmentBinding;
import jp.gr.java_conf.mitchibu.samples.ui.base.BindLayout;
import jp.gr.java_conf.mitchibu.samples.ui.base.DataBindingFragment;

@BindLayout(R.layout.shop_fragment)
public class ShopFragment extends DataBindingFragment<ShopListFragmentBinding> {
	private ShopViewModel model;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		model = ViewModelProviders.of(this).get(ShopViewModel.class);
	}
}
