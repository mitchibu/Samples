package jp.gr.java_conf.mitchibu.samples.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jp.gr.java_conf.mitchibu.samples.model.dao.MyDatabase;
import jp.gr.java_conf.mitchibu.samples.ui.util.TestFactory;

@Module
public class ViewModelModule {
	@Provides
	@Singleton
	public TestFactory provideTestFactory(MyDatabase db) {
		return new TestFactory(db);
	}
}
