package jp.gr.java_conf.mitchibu.samples.di;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import jp.gr.java_conf.mitchibu.samples.ui.shop.ShopFragment;
import jp.gr.java_conf.mitchibu.samples.ui.shop_list.ShopListFragment;
import jp.gr.java_conf.mitchibu.samples.ui.main.MainFragment;
import jp.gr.java_conf.mitchibu.samples.ui.main_home.HomeFragment;
import jp.gr.java_conf.mitchibu.samples.MainActivity;
import jp.gr.java_conf.mitchibu.samples.ui.main_search.SearchFragment;

@Module
abstract class MainActivityModule {
	@ContributesAndroidInjector
	abstract MainFragment contributeMainFragment();
	@ContributesAndroidInjector
	abstract HomeFragment contributeHomeFragment();
	@ContributesAndroidInjector
	abstract SearchFragment contributeSearchFragment();
	@ContributesAndroidInjector
	abstract ShopListFragment contributeShopListFragment();
	@ContributesAndroidInjector
	abstract ShopFragment contributeShopFragment();

	@Binds
	abstract Activity bindsActivity(MainActivity activity);
}
