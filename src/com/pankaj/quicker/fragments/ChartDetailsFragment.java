package com.pankaj.quicker.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pankaj.quicker.R;
import com.pankaj.quicker.modal.CarInfo;
import com.pankaj.quicker.utils.AlertMessage;
import com.widget.chart.PieChart;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChartDetailsFragment {
	Activity activity;

	Button shareButton;
	Button smsButton;
	
	CarInfo objCarInfo;

	public View getFragmentView(View rootView, CarInfo objCarInfo,
			Activity activity) {
		this.objCarInfo = objCarInfo;
		this.activity = activity;
		init();
		findViews(rootView);
		listeners();
		return rootView;
	}

	private void init() {

	}

	private void findViews(View rootView) {
		PieChart pieChart = (PieChart) rootView.findViewById(R.id.pieChart);
		shareButton = (Button)rootView.findViewById(R.id.button_chartDetail_share);
		smsButton = (Button)rootView.findViewById(R.id.button_chartDetail_sms);
		populateChart(pieChart);
	}

	private void listeners(){
		shareButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey use Quicker cars for detailed car information. \n" + objCarInfo.toString()); //overriding toString in CarInfo modal to return all information
				sendIntent.setType("text/plain");
				activity.startActivity(Intent.createChooser(sendIntent, activity.getResources().getText(R.string.share_title)));
			}
		});
		
		smsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertMessage(activity).showTostMessage("Functionality not implemented yet.");
			}
		});
	}
	
	private void populateChart(PieChart pieChart) {
		List<String> colorList = new ArrayList<String>();
		String[] array = activity.getResources().getStringArray(
				R.array.pie_chart_colors);
		colorList = new ArrayList<String>(Arrays.asList(array));
		
		pieChart.setColor(colorList);  //setting color for pie chart

		int size = objCarInfo.getCities().length;
		float[] user = new float[size];
		for (int i = 0; i < size; i++) {
			user[i] = Float.parseFloat(objCarInfo.getCities()[i].getUsers());
		}

		pieChart.setData(user);  // data for pie chart

		String[] labels = new String[size];
		for (int i = 0; i < size; i++) {
			labels[i] = objCarInfo.getCities()[i].getCity()+" - "+user[i];
		}

		pieChart.setLabels(labels); //labels for pie chart
	}

}
