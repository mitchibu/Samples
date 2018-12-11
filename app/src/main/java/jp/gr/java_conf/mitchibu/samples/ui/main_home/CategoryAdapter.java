package jp.gr.java_conf.mitchibu.samples.ui.main_home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import jp.gr.java_conf.mitchibu.samples.ui.shop_list.ShopListFragment;

public class CategoryAdapter extends FragmentStatePagerAdapter {
	private final SparseArray<Fragment> map = new SparseArray<>();

	public CategoryAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = map.get(position);
		if(fragment == null) {
			fragment = new ShopListFragment();
			map.put(position, fragment);
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return 5;
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		return "position_" + position;
	}
}
