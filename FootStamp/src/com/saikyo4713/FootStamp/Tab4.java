package com.saikyo4713.FootStamp;

import android.annotation.SuppressLint;
import android.content.*;
import android.net.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.*;
import android.widget.*;

@SuppressLint("ValidFragment")
public class Tab4 extends Fragment {
		TextView textView;
		
		public Tab4(TextView tView) {
			textView = tView;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.activity_tab4, null);

            Button telBtn = (Button)view.findViewById(R.id.phoneBtn);
            
            telBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 //전화걸기 *아래 AndroidManifest에서 permission(허가)을 주어야 바로 전화거 걸립니다
                public void onClick(View v) {
                    // TODO Auto-generated method stub
					//Toast.makeText(act, "개발자에게 전화를 겁니다.", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01050573626"));
                    startActivity(intent);
                }
            });
            
            Button blogBtn = (Button) view.findViewById(R.id.blogBtn);
			
            blogBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Uri uri = Uri.parse("http://saikyo4713.blog.me");
			    	Intent it  = new Intent(Intent.ACTION_VIEW,uri);
			    	startActivity(it);
				}
	    	});            
			
			
	    	return view;
		}

}