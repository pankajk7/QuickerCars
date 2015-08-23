package com.pankaj.quicker.fragments;

import com.android.volley.toolbox.NetworkImageView;
import com.pankaj.quicker.ApplicationQuicker;
import com.pankaj.quicker.R;
import com.pankaj.quicker.modal.CarInfo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

public class CarDetailsFragment{

	NetworkImageView carImageView;
	
	TextView nameTextView;
	TextView ratingTextView;
	TextView ccTextView;
	TextView typeTextView;
	TextView absTextView;
	TextView mileageTextView;
	TextView descriptionTextView;

	View colorView;
	Activity activity;
	
	CarInfo objCarInfo;
	
	public View getFragmentView(View rootView,
			CarInfo objCarInfo, Activity activity) {
		// View rootView = inflater
		// .inflate(R.layout.show_venue_from_search_result_layout,
		// container, false);
		this.objCarInfo = objCarInfo;
		this.activity = activity;
		findView(rootView);
		setViewsValues();
		return rootView;
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
		descriptionTextView = (TextView)rootView.findViewById(R.id.textView_carInfo_description);
	}
	
	private void setViewsValues(){
		try{
		carImageView.setImageUrl(objCarInfo.getImage(), ApplicationQuicker.getInstance().getImageLoader());
		nameTextView.setText(objCarInfo.getName());
		ratingTextView.setText(objCarInfo.getRating());
		ccTextView.setText(objCarInfo.getEngine_cc());
		typeTextView.setText(objCarInfo.getType());
		absTextView.setText(objCarInfo.getAbs_exist());
		mileageTextView.setText(objCarInfo.getMileage());
		descriptionTextView.setText(objCarInfo.getDescription());
		
		colorView.setBackgroundColor(Color.parseColor(objCarInfo.getColor()));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}


