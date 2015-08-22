package com.pankaj.quicker.adapters;

import java.util.ArrayList;

import com.pankaj.quicker.R;
import com.pankaj.quicker.modal.CarInfo;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ListBaseAdapter<T> extends BaseAdapter {

	Context context;
	ArrayList<T> arrayList;
	LayoutInflater inflater;

	public ListBaseAdapter(Context context, ArrayList<T> arrayList) {
		this.context = context;
		this.arrayList = arrayList;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (arrayList != null) {
			return arrayList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_layout, parent,
					false);
			holder = new ViewHolder();
			holder.nameTextView = (TextView) convertView
					.findViewById(R.id.textview_name);
			holder.inappTextView = (TextView) convertView
					.findViewById(R.id.textview_inapp);
			// holder.imageVie
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(arrayList.get(position) instanceof CarInfo){
			CarInfo obj = (CarInfo) arrayList.get(position);
			holder.nameTextView.setText(obj.getName());

			Resources res = context.getResources();
			String inapp = String.format(res.getString(R.string.rupee),
					obj.getPrice());

			holder.inappTextView.setText(inapp);
		}

		return convertView;
	}

	public static class ViewHolder {
		TextView nameTextView;
		TextView inappTextView;
	}

}
