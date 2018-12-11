package jp.gr.java_conf.mitchibu.samples.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import jp.gr.java_conf.mitchibu.samples.MainActivity;
import jp.gr.java_conf.mitchibu.samples.ShopActivity;

@Module
abstract class ActivityBuilderModule {
	@ContributesAndroidInjector(modules = {MainActivityModule.class})
	abstract MainActivity contributeMainActivity();
	@ContributesAndroidInjector
	abstract ShopActivity contributeShopActivity();
}
