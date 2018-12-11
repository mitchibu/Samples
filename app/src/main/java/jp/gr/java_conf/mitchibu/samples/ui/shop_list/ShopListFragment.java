package jp.gr.java_conf.mitchibu.samples.ui.shop_list;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import javax.inject.Inject;

import androidx.navigation.Navigation;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jp.gr.java_conf.mitchibu.samples.R;
import jp.gr.java_conf.mitchibu.samples.databinding.ShopListFragmentBinding;
import jp.gr.java_conf.mitchibu.samples.model.entity.EntityA;
import jp.gr.java_conf.mitchibu.samples.ui.base.BindLayout;
import jp.gr.java_conf.mitchibu.samples.ui.base.DataBindingFragment;
import jp.gr.java_conf.mitchibu.samples.ui.main.MainFragmentDirections;
import jp.gr.java_conf.mitchibu.samples.ui.util.TestFactory;
import jp.gr.java_conf.mitchibu.samples.ui.view.OnItemClickListener;

@BindLayout(R.layout.shop_list_fragment)
public class ShopListFragment extends DataBindingFragment<ShopListFragmentBinding> {
	@Inject
	TestFactory factory;

	private final CompositeDisposable disposables = new CompositeDisposable();

	private ShopListViewModel model;
	private ShopListAdapter adapter;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//		model = ViewModelProviders.of(Objects.requireNonNull(getActivity()), factory).get(ShopListViewModel.class);
		model = ViewModelProviders.of(this, factory.with(savedInstanceState)).get(ShopListViewModel.class);

		getBinding().list.setAdapter(adapter = new ShopListAdapter().setOnItemClickListener(new OnItemClickListener<EntityA>() {
			@Override
			public void onItemClick(View view, int position, EntityA data) {
				//startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("test://hoge/")));
				Navigation.findNavController(view).navigate(MainFragmentDirections.actionMainFragmentToShopFragment());
			}
		}));
	}

	private boolean test = false;
	@Override
	public void onStart() {
		super.onStart();
		test = false;
		android.util.Log.v("test", "start");
		disposables.add(model.modelList
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<PagedList<EntityA>>() {
					@Override
					public void accept(PagedList<EntityA> entityAS) throws Exception {
						android.util.Log.v("test", "isEmpty: " + entityAS.isEmpty());
						getBinding().setIsEmpty(entityAS.isEmpty());
						adapter.submitList(entityAS);
						if(!test) {
							test = true;
							((LinearLayoutManager)getBinding().list.getLayoutManager()).scrollToPositionWithOffset(model.position, model.offset);
						}
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {
						throwable.printStackTrace();
					}
				}));
	}

	@Override
	public void onStop() {
		super.onStop();
		android.util.Log.v("test", "stop");
		disposables.clear();
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		android.util.Log.v("test", "ShopListFragment.onSaveInstanceState");

		model.position = ((LinearLayoutManager)getBinding().list.getLayoutManager()).findFirstVisibleItemPosition();
		View view = getBinding().list.getChildAt(0);
		model.offset = view == null ? 0 : (view.getTop() - getBinding().list.getPaddingTop());
		model.to(outState);
	}
}
