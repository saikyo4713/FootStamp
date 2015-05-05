package com.saikyo4713.FootStamp;

import java.io.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.json.*;

import com.saikyo4713.FootStamp.Tab1.*;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.support.v4.app.Fragment;
import android.telephony.*;
import android.util.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.*;
import android.widget.*;

@SuppressLint("ValidFragment")
public class Tab4 extends Fragment {
		TextView textView;
		String device;
		
		private ProgressDialog dialog;
		private ImageView imgEmpty;
		
		public Tab4(TextView tView) {
			textView = tView;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.activity_tab4, null);

			// Device ID
			TelephonyManager telephony = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
			device = telephony.getDeviceId();			
			
            Button telBtn = (Button)view.findViewById(R.id.phoneBtn);
            
            telBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 //전화걸기 *아래 AndroidManifest에서 permission(허가)을 주어야 바로 전화거 걸립니다
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                	Toast.makeText(getActivity(), "개발자에게 연락을 합니다.", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01050573626"));
                    startActivity(intent);
                }
            });
            
            Button blogBtn = (Button) view.findViewById(R.id.blogBtn);
			
            blogBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getActivity(), "개발자의 블로그로 이동합니다.", Toast.LENGTH_SHORT).show();
					Uri uri = Uri.parse("http://saikyo4713.blog.me");
			    	Intent it  = new Intent(Intent.ACTION_VIEW,uri);
			    	startActivity(it);
				}
	    	});            
			

            Button resetBtn = (Button) view.findViewById(R.id.resetBtn);
			
            resetBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//http://transer.iptime.org/~withweb/index.php/project/visitLogReset?device=352424060779168			
					Log.d("TEST", "TEST");
					
					new JsonLoadingTask().execute();
				}
	    	});                  
            
	    	return view;
		}
	
		/**
		 * 원격의 데이터를 가지고 JSON 객체를 생성한 다음에 객체에서 데이터 타입별로 데이터를 읽어서 StringBuffer에 추가한다.
		 */
		public String getJsonText() {
			
			// 내부적으로 문자열 편집이 가능한 StringBuffer 생성자
			//StringBuffer sb = new StringBuffer();
			String result = null;
			
			try {
				String line = getStringFromUrl("http://transer.iptime.org/~withweb/index.php/project/visitLogReset?device=" + device);
				
				// 원격에서 읽어온 데이터로 JSON 객체 생성
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

		@Override
		public void onDestroy() {
			super.onDestroy();
			if(dialog != null) dialog.dismiss();
		}		
		
		/**
		 *	스레드에서 향상된 AsyncTask 를 이용하여
		 * UI 처리 및 Background 작업 등을 하나의 클래스에서 작업 할 수 있도록 지원해준다.
		 */
		private class JsonLoadingTask extends AsyncTask<String, Void, String> {

			@Override
		    protected void onPreExecute() {
					super.onPreExecute();
					dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
		    }						
			
			@Override
			protected String doInBackground(String... strs) {
				return getJsonText();
			} // doInBackground : 백그라운드 작업을 진행한다.
			@Override
			protected void onPostExecute(String result) {
		        super.onPostExecute(result);
		        dialog.hide();				
				Log.d("", "result : " + result);
				
				if(result.equals("true")) {
					Toast.makeText(getActivity(), "방문내역 초기화가 완료 되었습니다.\n앱을 재실행 해 주세요.", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getActivity(), "방문내역 초기화에 문제가 있습니다.\n잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
				}			

			} // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
		} // JsonLoadingTask				
		
}