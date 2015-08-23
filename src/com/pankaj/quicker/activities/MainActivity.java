package com.pankaj.quicker.activities;

import com.pankaj.quicker.R;
import com.pankaj.quicker.fragments.CarListingFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setTitle("Car Listing");
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.main_layout);

		init();
		findViews();
	}

	private void init() {
		Fragment fragment = new CarListingFragment();
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		fragmentTransaction.replace(R.id.layout_main_container, fragment);
		fragmentTransaction.commit();
	}

	private void findViews() {
	}

}
