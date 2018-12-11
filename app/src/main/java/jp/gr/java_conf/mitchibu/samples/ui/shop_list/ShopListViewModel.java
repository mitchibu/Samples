package jp.gr.java_conf.mitchibu.samples.ui.shop_list;

import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.arch.paging.RxPagedListBuilder;
import android.os.Bundle;
import android.support.annotation.NonNull;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import jp.gr.java_conf.mitchibu.samples.model.dao.MyDatabase;
import jp.gr.java_conf.mitchibu.samples.model.entity.EntityA;
import jp.gr.java_conf.mitchibu.samples.test.SavableViewModel;

public class ShopListViewModel extends SavableViewModel {
	public final Flowable<PagedList<EntityA>> modelList;
	public int position = 0;
	public int offset = 0;

	static boolean test = false;
	public ShopListViewModel(final MyDatabase db) {
		android.util.Log.v("test", "ShopListViewModel");
		if(!test) {
			test = true;
			new Thread() {
				public void run() {
					//db.entityADao().deleteAll();
					EntityA[] entities = new EntityA[10];
					for(int i = 0; i < entities.length; ++i) {
						entities[i] = new EntityA();
						entities[i].name = "shop: " + i;
						entities[i].imageUrl = "http://www.vijp.com/kitakami/wp-content/uploads/2017/08/image.jpg";
					}
					db.entityADao().insert(entities);
				}
			}.start();
		}
		modelList = new RxPagedListBuilder<>(db.entityADao().get3(),/* page size */ 20).buildFlowable(BackpressureStrategy.LATEST);
	}

	@Override
	protected void onCleared() {
		android.util.Log.v("test", "onCleared");
	}

	@Override
	public void to(@NonNull Bundle bundle) {
		bundle.putInt("position", position);
		bundle.putInt("offset", offset);
	}

	@Override
	public void from(@NonNull Bundle bundle) {
		position = bundle.getInt("position", 0);
		offset = bundle.getInt("offset", 0);
	}
}
