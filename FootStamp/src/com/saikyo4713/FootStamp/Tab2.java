package com.saikyo4713.FootStamp;

import java.io.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;

import com.saikyo4713.FootStamp.R;
import com.squareup.picasso.*;

import android.app.*;
import android.content.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.*;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tab2 extends Fragment implements OnClickListener {
		private ListView listView;
		private DefaultListAdapter mAdapter;
		private ArrayList<DefaultListModel> mDataList;
		private View view;
					
		private int ano = 0;
		
		private ProgressDialog dialog;
		private TextView textEmpty;
		private ImageView imgEmpty;
		private ProgressBar progress;
				
		public Tab2(ListView mlistView) {
			//listView = mlistView;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			view = inflater.inflate(R.layout.activity_tab2, container, false);	
									
			view.findViewById(R.id.btn1).setOnClickListener(this);
			view.findViewById(R.id.btn2).setOnClickListener(this);
			view.findViewById(R.id.btn3).setOnClickListener(this);
			view.findViewById(R.id.btn4).setOnClickListener(this);
			view.findViewById(R.id.btn5).setOnClickListener(this);
			view.findViewById(R.id.btn6).setOnClickListener(this);
					
			new JsonLoadingTask().execute();			
	    	return view;
		}

		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.btn1:
					mDataList.clear();
					ano = 1;
					new JsonLoadingTask().execute();
					mAdapter.setData(mDataList);
					mAdapter.notifyDataSetChanged();
				break;

				case R.id.btn2:
					mDataList.clear();
					ano = 2;
					new JsonLoadingTask().execute();
					mAdapter.setData(mDataList);
					mAdapter.notifyDataSetChanged();					
				break;

				case R.id.btn3:
					mDataList.clear();
					ano = 3;
					new JsonLoadingTask().execute();
					mAdapter.setData(mDataList);
					mAdapter.notifyDataSetChanged();					
				break;

				case R.id.btn4:
					mDataList.clear();
					ano = 4;
					new JsonLoadingTask().execute();
					mAdapter.setData(mDataList);
					mAdapter.notifyDataSetChanged();					
				break;

				case R.id.btn5:
					mDataList.clear();
					ano = 5;
					new JsonLoadingTask().execute();
					mAdapter.setData(mDataList);
					mAdapter.notifyDataSetChanged();					
				break;

				case R.id.btn6:
					mDataList.clear();
					ano = 6;
					new JsonLoadingTask().execute();
					mAdapter.setData(mDataList);
					mAdapter.notifyDataSetChanged();					
				break;

				default:
				break;
			}
		}		

		/**
		 * 원격의 데이터를 가지고 JSON 객체를 생성한 다음에 객체에서 데이터 타입별로 데이터를 읽어서 StringBuffer에 추가한다.
		 */
		public String getJsonText() {
			
			// 내부적으로 문자열 편집이 가능한 StringBuffer 생성자
			//StringBuffer sb = new StringBuffer();
			
			try {
				String line = getStringFromUrl("http://transer.iptime.org/~withweb/index.php/project/areaList?ano=" + ano );
				
				// 원격에서 읽어온 데이터로 JSON 객체 생성
				JSONObject object = new JSONObject(line);
				
				// "kkt_list" 배열로 구성 되어있으므로 JSON 배열생성
				JSONArray jsonArray = new JSONArray(object.getString("List"));
								
				mDataList = new ArrayList<DefaultListModel>();
				
				//Log.d("", " jsonArray.length() : " + jsonArray.length());
				
				if(jsonArray.length() > 0) {
					for (int i = 0; i < jsonArray.length(); i++) {
						// bodylist 배열안에 내부 JSON 이므로 JSON 내부 객체 생성
						JSONObject insideObject = jsonArray.getJSONObject(i);
	
						// StringBuffer 메소드 ( append : StringBuffer 인스턴스에 뒤에 덧붙인다. )
						// JSONObject 메소드 ( get.String(), getInt(), getBoolean() .. 등 : 객체로부터 데이터의 타입에 따라 원하는 데이터를 읽는다. )	
						
						mDataList.add(new DefaultListModel(insideObject.getString("atno"),insideObject.getString("atimg"),insideObject.getString("atname"),insideObject.getString("aname")));
						
					} // for
				}
				
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
				//super.onPreExecute();
				//dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
				listView = (ListView) view.findViewById(R.id.areaList);
				//TextView textEmpty = (TextView) view.findViewById(R.id.textEmpty);
				//textEmpty = (TextView) view.findViewById(R.id.textEmpty);
				imgEmpty = (ImageView) view.findViewById(R.id.imgEmpty);
				progress = (ProgressBar) view.findViewById(R.id.loading);
				//Picasso.with(getActivity().getApplicationContext()).load(R.drawable.loading).into(imgEmpty);
				listView.setEmptyView(progress);
				//textEmpty.setText("데이터 로딩중 입니다..");				
		    }						
			
			@Override
			protected String doInBackground(String... strs) {
				return getJsonText();
			} // doInBackground : 백그라운드 작업을 진행한다.
			@Override
			protected void onPostExecute(String result) {
				//textView.setText(result);	
		        //super.onPostExecute(result);
		        //dialog.hide();
				
				mAdapter = new DefaultListAdapter(mDataList);	
								
				if(mDataList.size() > 0) {
					listView.setAdapter(mAdapter);				
					listView.setOnItemClickListener(new OnItemClickListener()
					{
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id)
						{
							//Toast.makeText(getActivity(), mDataList.get(position).atno, Toast.LENGTH_SHORT).show();
							Bundle bundle = new Bundle();
							bundle.putString("atno", mDataList.get(position).atno);
													
							//Intent intent = new Intent(getActivity().getApplicationContext(), DetailListActivity.class);
							//Intent intent = new Intent(parent.getContext(), DetailListActivity.class);
							Intent intent = new Intent(getActivity().getBaseContext(), DetailListActivity.class);
							intent.putExtras(bundle);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							//parent.getContext().startActivity(intent);
							getActivity().startActivity(intent);
						}
					});
				}else{
					//textEmpty.setText("검색된 문화유산이 없습니다.");	
					listView.setEmptyView(imgEmpty);
					progress.setVisibility(View.GONE);
					Picasso.with(getActivity().getApplicationContext()).load(R.drawable.no_data_black).into(imgEmpty);
					//textEmpty.setVisibility(View.VISIBLE);
				}				
			} // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
		} // JsonLoadingTask				
}