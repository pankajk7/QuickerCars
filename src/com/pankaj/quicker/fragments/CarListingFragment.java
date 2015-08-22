package com.pankaj.quicker.fragments;

import java.util.ArrayList;
import java.util.Arrays;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.pankaj.quicker.R;
import com.pankaj.quicker.adapters.ListBaseAdapter;
import com.pankaj.quicker.modal.CarInfo;
import com.pankaj.quicker.utils.AlertMessage;
import com.pankaj.quicker.utils.ConnectionDetector;
import com.pankaj.quicker.utils.Constants;
import com.pankaj.quicker.webservice.RestWebService;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CarListingFragment extends Fragment{

	ListView listView;
	
	ArrayList<CarInfo> carinfoList;
	ListBaseAdapter<?> adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		init();
		View rootView = inflater.inflate(R.layout.list_cars_layout, container, false);
		findViews(rootView);
		listeners();
		populate();
		return rootView;
	}
	
	private void init(){
		carinfoList = new ArrayList<CarInfo>();
	}
	
	private void findViews(View rootView){
		listView = (ListView)rootView.findViewById(R.id.listview_carInfo);
	}
	
	private void listeners(){
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("info", carinfoList.get(position));
				Fragment fragment = new CarInfoFragment();
				fragment.setArguments(bundle);
				FragmentManager fm = getActivity().getSupportFragmentManager();
				FragmentTransaction fragmentTransaction = fm.beginTransaction();
				fragmentTransaction.replace(R.id.fragment_mainActivity, fragment);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		});
	}
	private void populate() {
		if(!new ConnectionDetector(getActivity()).isConnectedToInternet()){
			new AlertMessage(getActivity()).showTostMessage(R.string.warning_internet_message);
			return;
		}
		
		new RestWebService(getActivity()){
			public void onSuccess(String data) {
				Log.d("Success", "Yippeee");
				CarInfo[] array = new Gson().fromJson(data, CarInfo[].class);
				carinfoList = new ArrayList<CarInfo>(Arrays.asList(array));
				setAdapter();
			};
			public void onError(VolleyError error) {
				Log.d("Error", error.toString());
			};
		}.serviceCall(Constants.API_GET_CAR_INFO, "");
	}
	 
	private void setAdapter(){
		adapter = new ListBaseAdapter<>(getActivity(), carinfoList);
		listView.setAdapter(adapter);
	}
}
