package jp.gr.java_conf.mitchibu.samples;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.Objects;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import jp.gr.java_conf.mitchibu.samples.databinding.MainActivityBinding;
import jp.gr.java_conf.mitchibu.samples.model.usecase.BeaconWorker;
import jp.gr.java_conf.mitchibu.samples.ui.base.BindLayout;
import jp.gr.java_conf.mitchibu.samples.ui.base.DataBindingActivity;
import jp.gr.java_conf.mitchibu.samples.ui.main_home.HomeViewModel;

@BindLayout(R.layout.main_activity)
public class MainActivity extends DataBindingActivity<MainActivityBinding> {
	private NavController controller;
	HomeViewModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		model = ViewModelProviders.of(this).get(HomeViewModel.class);
		model.position.setValue(savedInstanceState == null ? 0 : savedInstanceState.getInt("position", 0));
		setSupportActionBar(getBinding().toolBar);

		NavHostFragment host = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.host_fragment);
		controller = NavHostFragment.findNavController(Objects.requireNonNull(host));
		NavigationUI.setupActionBarWithNavController(this, controller, getBinding().drawer);
		NavigationUI.setupWithNavController(getBinding().navigation, controller);

		//ForegroundService.start(this, MyForegroundService.class);
		BeaconWorker.cancel();
		BeaconWorker.start(false);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		android.util.Log.v("test", "MainActivity.onSaveInstanceState");
		Integer position = model.position.getValue();
		if(position != null) outState.putInt("position", position);
	}

	@Override
	public void onBackPressed() {
		MainActivityBinding binding = getBinding();
		if(binding.drawer.isDrawerOpen(GravityCompat.START)) {
			binding.drawer.closeDrawer(GravityCompat.START);
		} else {
			BeaconWorker.cancel();
			super.onBackPressed();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return NavigationUI.onNavDestinationSelected(item, controller) || super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onSupportNavigateUp() {
		MainActivityBinding binding = getBinding();
		return NavigationUI.navigateUp(controller, binding.drawer) || super.onSupportNavigateUp();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		controller.onHandleDeepLink(intent);
	}
}
