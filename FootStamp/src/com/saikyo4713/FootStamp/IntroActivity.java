package com.saikyo4713.FootStamp;

import android.app.Activity;
import android.content.*;
import android.os.*;
import android.view.*;

public class IntroActivity extends Activity {

	Handler h;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_intro);
		h = new Handler();
		h.postDelayed(irun, 2000);
	}
	
	Runnable irun = new Runnable() {
		@Override
		public void run() {
			Intent i = new Intent(IntroActivity.this, MainActivity.class);
			startActivity(i);
			finish();
			
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);			
		}
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		h.removeCallbacks(irun);
	}
}
