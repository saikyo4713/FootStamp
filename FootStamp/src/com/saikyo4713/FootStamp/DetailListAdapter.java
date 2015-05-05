package com.saikyo4713.FootStamp;

import java.util.ArrayList;

import com.squareup.picasso.*;
import android.util.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class DetailListAdapter extends BaseAdapter {
	private ArrayList<DefaultListModel> mDataList;
	
	public DetailListAdapter(ArrayList<DefaultListModel> dataList) {
		mDataList = dataList;
	}

	@Override
	public int getCount() {
		return (mDataList == null) ? 0 : mDataList.size();
	}

	public void setData(ArrayList<DefaultListModel> dataList) {
		mDataList = dataList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.detail_list_item, null);
		}

		DefaultListModel data = mDataList.get(position);
	
		ImageView atimg = (ImageView) convertView.findViewById(R.id.atimg);
	
		//Log.d("", "data.atimg : " + data.atimg);
		Picasso.with(parent.getContext()).load(data.atimg).into(atimg);

		return convertView;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}