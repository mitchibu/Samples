package jp.gr.java_conf.mitchibu.samples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jp.gr.java_conf.mitchibu.samples.databinding.MainActivityBinding;
import jp.gr.java_conf.mitchibu.samples.databinding.ShopActivityBinding;
import jp.gr.java_conf.mitchibu.samples.ui.base.BindLayout;
import jp.gr.java_conf.mitchibu.samples.ui.base.DataBindingActivity;

@BindLayout(R.layout.shop_activity)
public class ShopActivity extends DataBindingActivity<ShopActivityBinding> {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSupportActionBar(getBinding().toolBar);
	}
}
