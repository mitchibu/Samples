package jp.gr.java_conf.mitchibu.samples.ui.shop_list;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import jp.gr.java_conf.mitchibu.samples.databinding.ItemShopBinding;
import jp.gr.java_conf.mitchibu.samples.model.entity.EntityA;
import jp.gr.java_conf.mitchibu.samples.ui.view.BindableAdapter;
import jp.gr.java_conf.mitchibu.samples.ui.view.BindableViewHolder;
import jp.gr.java_conf.mitchibu.samples.ui.view.OnItemClickListener;

public class ShopListAdapter extends BindableAdapter<EntityA> {
	private OnItemClickListener<EntityA> onItemClickListener = null;

	public ShopListAdapter() {
		super(new DiffUtil.ItemCallback<EntityA>() {
			@Override
			public boolean areItemsTheSame(@NonNull EntityA s, @NonNull EntityA t1) {
				return s.id == t1.id;
			}

			@Override
			public boolean areContentsTheSame(@NonNull EntityA s, @NonNull EntityA t1) {
				return s.equals(t1);
			}
		});
	}

	public ShopListAdapter setOnItemClickListener(OnItemClickListener<EntityA> listener) {
		onItemClickListener = listener;
		return this;
	}

	@Override
	public ViewDataBinding onCreateDataBinding(@NonNull ViewGroup parent, int viewType) {
		return ItemShopBinding.inflate((LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE), parent, false);
	}

	@Override
	public void onBindViewHolder(@NonNull BindableViewHolder holder, int position) {
		((ItemShopBinding)holder.getBinding()).setItem(getItem(position));
		((ItemShopBinding)holder.getBinding()).setPosition(position);
		((ItemShopBinding)holder.getBinding()).setOnItemClickListrener(onItemClickListener);
	}
}
