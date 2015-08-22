package com.pankaj.quicker.activities;

import com.pankaj.quicker.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		init();
		findViews();
	}

	private void init() {
	}

	private void findViews() {
	}

}
