package com.pankaj.quicker.fragments;

import com.android.volley.toolbox.NetworkImageView;
import com.pankaj.quicker.ApplicationQuicker;
import com.pankaj.quicker.R;
import com.pankaj.quicker.modal.CarInfo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

public class CarInfoFragment extends Fragment {

	NetworkImageView carImageView;
	
	TextView nameTextView;
	TextView ratingTextView;
	TextView ccTextView;
	TextView typeTextView;
	TextView absTextView;
	TextView mileageTextView;

	View colorView;
	
	CarInfo objCarInfo;
	
	public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.car_info_layout, container, false);
		
		init();
		findView(rootView);
		setViewsValues();
		return rootView;
	};

	private void init(){
		try {
			Bundle bundle = getArguments();
			objCarInfo = (CarInfo)bundle.getSerializable("info");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void findView(View rootView) {
		carImageView = (NetworkImageView)rootView.findViewById(R.id.imageView_carInfo);
		nameTextView = (TextView)rootView.findViewById(R.id.textview_carInfo_name);
		ratingTextView = (TextView)rootView.findViewById(R.id.textView_carInfo_rating);
		colorView = (View)rootView.findViewById(R.id.view_carInfo_color);
		ccTextView = (TextView)rootView.findViewById(R.id.textView_carInfo_cc);
		typeTextView = (TextView)rootView.findViewById(R.id.textView_carInfo_type);
		absTextView = (TextView)rootView.findViewById(R.id.textView_carInfo_abs);
		mileageTextView = (TextView)rootView.findViewById(R.id.textView_carInfo_mileage);
	}
	
	private void setViewsValues(){
		carImageView.setImageUrl(objCarInfo.getImage(), ApplicationQuicker.getInstance().getImageLoader());
		nameTextView.setText(objCarInfo.getName());
		ratingTextView.setText(objCarInfo.getRating());
		ccTextView.setText(objCarInfo.getEngine_cc());
		typeTextView.setText(objCarInfo.getType());
		absTextView.setText(objCarInfo.getAbs_exist());
		mileageTextView.setText(objCarInfo.getMileage());
		
		colorView.setBackgroundColor(Color.parseColor(objCarInfo.getColor()));
	}
}


