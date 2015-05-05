package com.saikyo4713.FootStamp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

@SuppressLint("ValidFragment")
public class Tab3 extends Fragment {
		TextView textView;
		
		public Tab3(TextView tView) {
			textView = tView;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.activity_tab3, null);
	    	return view;
		}

}