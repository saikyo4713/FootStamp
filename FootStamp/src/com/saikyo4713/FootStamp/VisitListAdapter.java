package com.saikyo4713.FootStamp;

import java.util.ArrayList;

import com.squareup.picasso.*;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class VisitListAdapter extends BaseAdapter {
	private ArrayList<VisitListModel> mDataList;

	public VisitListAdapter(ArrayList<VisitListModel> dataList) {
		mDataList = dataList;
	}

	@Override
	public int getCount() {
		return (mDataList == null) ? 0 : mDataList.size();
	}

	public void setData(ArrayList<VisitListModel> dataList) {
		mDataList = dataList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.visit_list_item, null);
		}
		
		VisitListModel data = mDataList.get(position);

		//TextView atno = (TextView) convertView.findViewById(R.id.atno);
		TextView aname = (TextView) convertView.findViewById(R.id.aname);
		TextView atname = (TextView) convertView.findViewById(R.id.atname);
		TextView acnt = (TextView) convertView.findViewById(R.id.acnt);
		TextView atotalcnt = (TextView) convertView.findViewById(R.id.atotalcnt);
		ImageView atimg = (ImageView) convertView.findViewById(R.id.atimg);
		ImageView all_stamp = (ImageView) convertView.findViewById(R.id.all_stamp);
		
		Picasso.with(parent.getContext()).load(data.atimg).into(atimg);
		
		if(data.acnt.equals(data.atotalcnt)) {
			Picasso.with(parent.getContext()).load(R.drawable.all_stamp).into(all_stamp);
		}
		
		aname.setText(data.aname);
		atname.setText("< " + data.atname + " >");
		atotalcnt.setText("전체 : " + data.atotalcnt + "회");
		acnt.setText("  |  인증 : " + data.acnt + "회");

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