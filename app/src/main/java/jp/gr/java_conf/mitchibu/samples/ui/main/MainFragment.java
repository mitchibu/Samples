package jp.gr.java_conf.mitchibu.samples.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import jp.gr.java_conf.mitchibu.samples.R;
import jp.gr.java_conf.mitchibu.samples.databinding.MainFragmentBinding;
import jp.gr.java_conf.mitchibu.samples.ui.base.BindLayout;
import jp.gr.java_conf.mitchibu.samples.ui.base.DataBindingFragment;

@BindLayout(R.layout.main_fragment)
public class MainFragment extends DataBindingFragment<MainFragmentBinding> {
	private MainViewModel model;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		model = ViewModelProviders.of(this).get(MainViewModel.class);

		NavHostFragment host = (NavHostFragment)getChildFragmentManager().findFragmentById(R.id.host_fragment);
		NavController controller = NavHostFragment.findNavController(Objects.requireNonNull(host));
		controller.getNavigatorProvider().addNavigator("test", new Test());
		NavigationUI.setupWithNavController(getBinding().bottomNavigation, controller);
	}

	class Test extends Navigator {
		@NonNull
		@Override
		public NavDestination createDestination() {
			return null;
		}

		@Override
		public void navigate(@NonNull NavDestination destination, @Nullable Bundle args, @Nullable NavOptions navOptions, @Nullable Extras navigatorExtras) {
		}

		@Override
		public boolean popBackStack() {
			return false;
		}
	}
}
