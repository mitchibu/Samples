package jp.gr.java_conf.mitchibu.samples.ui.main_search;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.gr.java_conf.mitchibu.samples.R;

public class SearchFragment extends Fragment {
	private SearchViewModel model;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		model = ViewModelProviders.of(this).get(SearchViewModel.class);
		// TODO: Use the ViewModel
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.search_fragment, container, false);
	}
}
