package com.saikyo4713.FootStamp;

import java.util.ArrayList;

import com.squareup.picasso.*;

import android.util.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class DetailViewAdapter extends BaseAdapter {
	private ArrayList<DetailViewModel> mDataList;
	
	public DetailViewAdapter(ArrayList<DetailViewModel> dataList) {
		mDataList = dataList;
	}

	@Override
	public int getCount() {
		return (mDataList == null) ? 0 : mDataList.size();
	}

	public void setData(ArrayList<DetailViewModel> dataList) {
		mDataList = dataList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.detail_view_item, null);
		}

		DetailViewModel data = mDataList.get(position);

		TextView aname = (TextView) convertView.findViewById(R.id.aname);
		TextView atname = (TextView) convertView.findViewById(R.id.atname);
		TextView atdname = (TextView) convertView.findViewById(R.id.atdname);
		TextView atdcontent = (TextView) convertView.findViewById(R.id.atdcontent);
		ImageView atdimg = (ImageView) convertView.findViewById(R.id.atdimg);

		Picasso.with(parent.getContext()).load(data.atdimg).into(atdimg);
		aname.setText(data.aname + "  > ");
		atname.setText(data.atname);
		atdname.setText(data.atdname);
		atdcontent.setText(data.atdcontent);	
		
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