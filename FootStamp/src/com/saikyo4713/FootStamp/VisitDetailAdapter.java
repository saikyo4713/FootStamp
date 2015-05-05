package com.saikyo4713.FootStamp;

import java.util.ArrayList;

import com.squareup.picasso.*;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class VisitDetailAdapter extends BaseAdapter {
	private ArrayList<DetailViewModel> mDataList;

	public VisitDetailAdapter(ArrayList<DetailViewModel> dataList) {
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
			convertView = inflater.inflate(R.layout.visit_detail_item, null);
		}
		
		DetailViewModel data = mDataList.get(position);

		//TextView atno = (TextView) convertView.findViewById(R.id.atno);
		TextView aname = (TextView) convertView.findViewById(R.id.aname);
		TextView atname = (TextView) convertView.findViewById(R.id.atname);
		TextView atdname = (TextView) convertView.findViewById(R.id.atdname);
		TextView vtcnt = (TextView) convertView.findViewById(R.id.vtcnt);
		ImageView atdimg = (ImageView) convertView.findViewById(R.id.atdimg);
		ImageView one_stamp = (ImageView) convertView.findViewById(R.id.one_stamp);

		int vtCount = Integer.parseInt(data.atdcontent);
		if(vtCount > 0) {
			Picasso.with(parent.getContext()).load(R.drawable.one_stamp).into(one_stamp);
		}
		
		Picasso.with(parent.getContext()).load(data.atdimg).into(atdimg);
		//aname.setText(data.aname);
		//atname.setText(data.atname);
		atdname.setText(data.atdname);
		vtcnt.setText("방문횟수 : " + data.atdcontent + "회");
		

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