package jp.gr.java_conf.mitchibu.samples.model.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import jp.gr.java_conf.mitchibu.samples.model.entity.EntityA;

@Database(entities = {EntityA.class}, version = 1, exportSchema = true)
public abstract class MyDatabase extends RoomDatabase {
	public abstract EntityA_DAO entityADao();
}
