package jp.gr.java_conf.mitchibu.samples.model.dao;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import jp.gr.java_conf.mitchibu.samples.model.entity.EntityA;

@Dao
public interface EntityA_DAO {
	@Query("select * from entityA")
	List<EntityA> get();

	@Query("select * from entityA")
	Flowable<List<EntityA>> get2();

	@Query("select * from entityA")
	DataSource.Factory<Integer, EntityA> get3();

	@Query("select * from entityA where id = :id limit 1")
	EntityA findById(long id);

	@Insert
	void insert(EntityA... entities);

	@Query("delete from entityA")
	void deleteAll();
}
