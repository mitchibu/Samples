package jp.gr.java_conf.mitchibu.samples.ui.view;

import android.view.View;

public interface OnItemClickListener<T> {
	void onItemClick(View view, int position, T data);
}
