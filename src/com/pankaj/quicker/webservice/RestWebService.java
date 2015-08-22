package com.pankaj.quicker.webservice;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.pankaj.quicker.ApplicationQuicker;
import com.pankaj.quicker.R;
import com.pankaj.quicker.utils.Constants;
import com.pankaj.quicker.utils.TransparentProgressDialog;

import android.content.Context;
import android.util.Log;

public class RestWebService {
	
	Context context;
	
	public RestWebService(Context context) {
		baseURL = Constants.BASE_URL;
		urlSuffix = Constants.SUFFIX_URL;
		this.context = context;
		transparentProgressDialog = new TransparentProgressDialog(context,
				R.drawable.needle);
	}
	
	String baseURL;
	String urlSuffix;
	TransparentProgressDialog transparentProgressDialog;


	private String getServiceURL(String resourceName, String extraParameters) {
		return baseURL + urlSuffix + resourceName + extraParameters;
	}

	public void onComplete() {
		if (transparentProgressDialog.isShowing()) {
			transparentProgressDialog.dismiss();
		}
	}


	public void onSuccess(String data) {

	}
	
	public void onError(VolleyError error){
		
	}


	public void serviceCall(String resourceName, String extraParameters) {
		String url = getServiceURL(resourceName, extraParameters);

		if (resourceName.equalsIgnoreCase(Constants.API_GET_CAR_INFO)) {
			getCall(url);
		}

	}

	private void getCall(String url) {
		transparentProgressDialog.show();
		
		StringRequest req = new StringRequest(Method.GET, url, new Response.Listener<String>() {

			@Override
			public void onResponse(String data) {
				onComplete();
				onSuccess(data);
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				onComplete();
				Log.d("Error", error.toString());
				onError(error);
			}
			
		});
		
		// add the request object to the queue to be executed
	    ApplicationQuicker.getInstance().getRequestQueue().add(req);
	}

}
