package com.saikyo4713.FootStamp;

import java.io.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.json.*;

import com.saikyo4713.FootStamp.Tab1.*;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.nfc.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.telephony.*;
import android.text.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	TextView mNfcInfoText;
	String mNfcData;
	String atdno;
	String device;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);	
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});
		
		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.						
			actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
		}	
		
		/* NFC */
		Intent intent = getIntent();
		String action = intent.getAction();
		Log.d("sung", "action : " + action);
		
		// Device ID
		TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		device = telephony.getDeviceId();				
		
		if (!TextUtils.isEmpty(action) && action.equals("android.nfc.action.NDEF_DISCOVERED"))
		{					
			Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			if (rawMsgs == null)
				return;

			int index = 0;
			String content = "";
			mNfcData = "";
			for (Parcelable p : rawMsgs)
			{
				NdefMessage msg = (NdefMessage) p;
				for (NdefRecord record : msg.getRecords())
				{
					content = "#" + index + " [" + new String(record.getType()) + "]\n" + (new String(record.getPayload())).trim() + "\n";
					mNfcData += content + "\n";
					Log.d("sung", content);
					if (new String(record.getType()).equals("text/plain")) {
						atdno = new String(record.getPayload());
					}
					index++;
				}
			}
			
			new JsonLoadingTask().execute();
			actionBar.setSelectedNavigationItem(2);
		}		
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {		
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
		TextView textView; // 출력할 textView
		ListView listView;
		
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
						
			switch(position) {
			case 0:
				Log.d("", "Tab1 : " + position);
				return new Tab1(listView);
			case 1:
				Log.d("", "Tab2 : " + position);
				return new Tab2(listView);
			case 2:
				Log.d("", "Tab3 : " + position);
				return new Tab3(listView);
			case 3:
				Log.d("", "Tab4 : " + position);
				return new Tab4(textView);				
			}
			return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.4
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
					
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);				
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);
			TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
			
			dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
	
	/**
	 * 원격의 데이터를 가지고 JSON 객체를 생성한 다음에 객체에서 데이터 타입별로 데이터를 읽어서 StringBuffer에 추가한다.
	 */
	public String getJsonText() {
		
		// 내부적으로 문자열 편집이 가능한 StringBuffer 생성자
		//StringBuffer sb = new StringBuffer();
		String result = null;
		
		try {
			String line = getStringFromUrl("http://transer.iptime.org/~withweb/index.php/project/nfcConfirm?atdno=" + atdno + "&device=" + device);
			
			JSONObject object = new JSONObject(line);
			result = object.getString("rtv");

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	} // getJsonText

	
	// getStringFromUrl : 주어진 URL 페이지를 문자열로 얻는다.
	public String getStringFromUrl(String url) throws UnsupportedEncodingException {
		
		// 입력스트림을 "UTF-8" 를 사용해서 읽은 후, 라인 단위로 데이터를 읽을 수 있는 BufferedReader 를 생성한다.
		BufferedReader br = new BufferedReader(new InputStreamReader(getInputStreamFromUrl(url), "UTF-8"));
		
		// 읽은 데이터를 저장한 StringBuffer 를 생성한다.
		StringBuffer sb = new StringBuffer();
		
		try {
			// 라인 단위로 읽은 데이터를 임시 저장한 문자열 변수 line
			String line = null;
			
			// 라인 단위로 데이터를 읽어서 StringBuffer 에 저장한다.
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	} // getStringFromUrl

	
	/**
	 *  getInputStreamFromUrl : 주어진 URL 에 대한 입력 스트림(InputStream)을 얻는다.
	 */
	public static InputStream getInputStreamFromUrl(String url) {
		InputStream contentStream = null;
		try {
			// HttpClient 를 사용해서 주어진 URL에 대한 입력 스트림을 얻는다.
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(new HttpGet(url));
			contentStream = response.getEntity().getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentStream;
	} // getInputStreamFromUrl

	
	/**
	 *	스레드에서 향상된 AsyncTask 를 이용하여
	 * UI 처리 및 Background 작업 등을 하나의 클래스에서 작업 할 수 있도록 지원해준다.
	 */
	private class JsonLoadingTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... strs) {
			return getJsonText();
		} // doInBackground : 백그라운드 작업을 진행한다.
		@Override
		protected void onPostExecute(String result) {
	
			if(result.equals("true")) {
				Toast.makeText(getApplicationContext(), "인증이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getApplicationContext(), "인증에 문제가 있습니다. 잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
			}			
			
		} // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
	} // JsonLoadingTask	

}
