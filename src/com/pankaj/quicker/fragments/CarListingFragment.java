package com.pankaj.quicker.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.pankaj.quicker.R;
import com.pankaj.quicker.activities.CarInfoMainActivity;
import com.pankaj.quicker.adapters.ListBaseAdapter;
import com.pankaj.quicker.modal.CarInfo;
import com.pankaj.quicker.utils.AlertMessage;
import com.pankaj.quicker.utils.ConnectionDetector;
import com.pankaj.quicker.utils.Constants;
import com.pankaj.quicker.utils.ReadWriteJsonFileUtils;
import com.pankaj.quicker.webservice.RestWebService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class CarListingFragment extends Fragment {

	ListView listView;
	TextView apiHitsTextView;
	Button priceButton;
	Button ratingButton;
	Button listAllButton;
	EditText searchEditText;

	List<CarInfo> carinfoList;
	ListBaseAdapter<?> adapter;
	
	List<CarInfo> tempList = new ArrayList<CarInfo>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		init();
		View rootView = inflater.inflate(R.layout.list_cars_layout, container,
				false);
		findViews(rootView);
		listeners();
		hideKeypad();
		getList();
		getApiHits();
		return rootView;
	}

	private void init() {
		carinfoList = new ArrayList<CarInfo>();
	}

	private void findViews(View rootView) {
		listView = (ListView) rootView.findViewById(R.id.listview_listCars);
		apiHitsTextView = (TextView) rootView
				.findViewById(R.id.textView_listCars_apiHit);
		priceButton = (Button) rootView
				.findViewById(R.id.button_listCars_price);
		ratingButton = (Button) rootView
				.findViewById(R.id.button_listCars_rating);
		searchEditText = (EditText)rootView
				.findViewById(R.id.editText_search);
		listAllButton = (Button)rootView
				.findViewById(R.id.button_listCars_listAll);
	}

	private void listeners() {
		listAllButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				adapter.addList(carinfoList);
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CarInfo objCarInfo = (CarInfo)parent.getAdapter().getItem(position);
				Intent intent = new Intent(getActivity(), CarInfoMainActivity.class);
				intent.putExtra(Constants.PARAMETER_CAR_INFO, objCarInfo);
				startActivity(intent);
			}
		});

		priceButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Collections.sort(carinfoList, byPrice());
				setAdapter();
			}
		});

		ratingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Collections.sort(carinfoList, byRating());
				setAdapter();
			}
		});
		
		searchEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				tempList = new ArrayList<CarInfo>();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String string = s.toString();
				if(s.length() > 1){
					
					for (int i = 0; i < carinfoList.size(); i++) {
						string = string.toLowerCase();
						String name = carinfoList.get(i).getName().toLowerCase();
						String brand = carinfoList.get(i).getBrand().toLowerCase();
						String type = carinfoList.get(i).getType().toLowerCase();
						Log.d("Data:", string+"-"+name+"-"+brand+"-"+type);
						if(name.contains(string)){
							Log.d("Name", "true");
							tempList.add(carinfoList.get(i));
						}else if(brand.contains(string)) {
							Log.d("brand", "true");
							tempList.add(carinfoList.get(i));
						}else if(type.contains(string)){
							Log.d("type", "true");
							tempList.add(carinfoList.get(i));
						}
					}
					adapter.addList(tempList);
				}else{
					adapter.addList(carinfoList);
				}
			}
		});
	}

	private Comparator<CarInfo> byPrice() {
		return new Comparator<CarInfo>() {
			@Override
			public int compare(CarInfo lhs, CarInfo rhs) {
				Float fLHS = Float.parseFloat(lhs.getPrice());
				Float fRHS = Float.parseFloat(rhs.getPrice());
				return fRHS.compareTo(fLHS);
			}
		};
	}

	private Comparator<CarInfo> byRating() {
		return new Comparator<CarInfo>() {
			@Override
			public int compare(CarInfo lhs, CarInfo rhs) {
				return rhs.getRating().compareTo(lhs.getRating());
			}
		};
	}

	private void getList() {
		boolean showLoading = true;

		//for checking data in file if exist
		String data = new ReadWriteJsonFileUtils(getActivity())
				.readJsonFileData(Constants.PARAMETER_JSON_FILE_CAR_INFO);
		//if there is data then assign to arraylist
		if (data != null) {
			CarInfo[] array = new Gson().fromJson(data, CarInfo[].class);
			carinfoList = new ArrayList<CarInfo>(Arrays.asList(array));
			if (carinfoList.size() > 0) {
				setAdapter();
			}
			showLoading = false;
		}
		
		if (!new ConnectionDetector(getActivity()).isConnectedToInternet()) {
			new AlertMessage(getActivity())
					.showTostMessage(R.string.warning_internet_message);
			return;
		}
		
		callAPICarInfo(showLoading);
	}

	private void callAPICarInfo(final boolean showLoading) {
		new RestWebService(getActivity()) {
			public void onSuccess(String data) {
				if (showLoading) {
					CarInfo[] array = new Gson()
							.fromJson(data, CarInfo[].class);
					carinfoList = new ArrayList<CarInfo>(Arrays.asList(array));
					new ReadWriteJsonFileUtils(getActivity())
							.createJsonFileData(
									Constants.PARAMETER_JSON_FILE_CAR_INFO,
									data);
					setAdapter();
				} else {
					new ReadWriteJsonFileUtils(getActivity())
							.createJsonFileData(
									Constants.PARAMETER_JSON_FILE_CAR_INFO,
									data);
				}
			};

			public void onError(VolleyError error) {
				Log.d("Error", error.toString());
			};
		}.serviceCall(Constants.API_GET_CAR_INFO, "", showLoading);
	}

	private void getApiHits() {
		if (!new ConnectionDetector(getActivity()).isConnectedToInternet()) {
			return;
		}

		new RestWebService(getActivity()) {
			public void onSuccess(String data) {
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(data);
					String apiHits = jsonObject
							.getString(Constants.PARAMETER_JSON_API_HITS);
					apiHitsTextView.setText(String.format(getActivity()
							.getResources().getString(R.string.api_hit),
							apiHits));
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};

			public void onError(VolleyError error) {
				Log.d("Error", error.toString());
			};
		}.serviceCall(Constants.API_GET_API_HITS, "", false); // false for not
																// showing
																// loading
	}

	private void setAdapter() {
		adapter = new ListBaseAdapter<>(getActivity(), carinfoList);
		listView.setAdapter(adapter);
	}
	
	
	private void hideKeypad() {
		View view = getActivity().getCurrentFocus();

		InputMethodManager inputManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (view instanceof EditText) {
			inputManager.hideSoftInputFromWindow(getActivity()
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
}
