package jp.gr.java_conf.mitchibu.samples.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import jp.gr.java_conf.mitchibu.samples.MyApplication;

@Singleton
@Component(modules = {
		AndroidSupportInjectionModule.class,
		AppModule.class,
		RoomModule.class,
		ViewModelModule.class,
		RetrofitModule.class,
		ActivityBuilderModule.class
})
public interface AppComponent extends AndroidInjector<MyApplication> {
	@Component.Builder
	abstract class Builder extends AndroidInjector.Builder<MyApplication> {
		@BindsInstance
		public abstract Builder application(Application application);
	}
}
