package com.saikyo4713.FootStamp;

import java.io.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.json.*;

import com.saikyo4713.FootStamp.Tab1.*;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.v4.app.Fragment;
import android.util.*;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.*;

public class DetailViewActivity extends Activity {

		private ListView listView;
		private DetailViewAdapter mAdapter;
		private ArrayList<DetailViewModel> mDataList;
			
		private String atdno;	
	
		private ProgressDialog dialog;
		private ImageView imgEmpty;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.default_detail_view);
			
			atdno = getIntent().getStringExtra("atno");
			
			Log.d("", "atdno : " + atdno);
			
			new JsonLoadingTask().execute();
		}

		@Override
		protected void onDestroy() {
			super.onDestroy();
			if(dialog != null) dialog.dismiss();
		}		
		
		/**
		 * 원격의 데이터를 가지고 JSON 객체를 생성한 다음에 객체에서 데이터 타입별로 데이터를 읽어서 StringBuffer에 추가한다.
		 */
		public String getJsonText() {
			
			// 내부적으로 문자열 편집이 가능한 StringBuffer 생성자
			//StringBuffer sb = new StringBuffer();
			
			try {
				String line = getStringFromUrl("http://transer.iptime.org/~withweb/index.php/project/detailView?atdno=" + atdno);
				
				// 원격에서 읽어온 데이터로 JSON 객체 생성
				JSONObject object = new JSONObject(line);
				
				// "kkt_list" 배열로 구성 되어있으므로 JSON 배열생성
				JSONArray jsonArray = new JSONArray(object.getString("List"));
				mDataList = new ArrayList<DetailViewModel>();
				
				for (int i = 0; i < jsonArray.length(); i++) {
					// bodylist 배열안에 내부 JSON 이므로 JSON 내부 객체 생성
					JSONObject insideObject = jsonArray.getJSONObject(i);

					// StringBuffer 메소드 ( append : StringBuffer 인스턴스에 뒤에 덧붙인다. )
					// JSONObject 메소드 ( get.String(), getInt(), getBoolean() .. 등 : 객체로부터 데이터의 타입에 따라 원하는 데이터를 읽는다. )	
			
					mDataList.add(new DetailViewModel(insideObject.getString("aname"),insideObject.getString("atname"),insideObject.getString("atdname"),insideObject.getString("atdcontent"),insideObject.getString("atdimg")));
					
				} // for
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
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
		    protected void onPreExecute() {
					super.onPreExecute();
					dialog = ProgressDialog.show(DetailViewActivity.this, "Loading...", "Please wait...", true);
		    }					
			
			@Override
			protected String doInBackground(String... strs) {
				return getJsonText(); 
			} // doInBackground : 백그라운드 작업을 진행한다.
			@Override
			protected void onPostExecute(String result) {
				//textView.setText(result);		
		        super.onPostExecute(result);
		        dialog.hide();				
				//Log.d("", "mDataList : " + mDataList);
				
				mAdapter = new DetailViewAdapter(mDataList);
				
				listView = (ListView) findViewById(R.id.detailView);
				listView.setAdapter(mAdapter);				

			} // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
		} // JsonLoadingTask			
		
}