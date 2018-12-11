package jp.gr.java_conf.mitchibu.samples.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jp.gr.java_conf.mitchibu.samples.model.dao.MyDatabase;

@Module
public class RoomModule {
	@Provides
	@Singleton
	public MyDatabase provideMyDatabase(Application app) {
		return Room.inMemoryDatabaseBuilder(app, MyDatabase.class)
				//.allowMainThreadQueries()
				.build();
	}
}
