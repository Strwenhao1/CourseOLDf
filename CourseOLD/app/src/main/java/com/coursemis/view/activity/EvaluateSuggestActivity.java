package com.coursemis.view.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.coursemis.R;
import com.coursemis.adapter.SuggestAdapter;
import com.coursemis.util.HttpUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class EvaluateSuggestActivity extends Activity {

	private int				teacherid;
	private int				courseid;
	private ListView		lv_sugest;
	private Button			bt_back;

	private List<String>	suggests=new ArrayList<String>();
	private AsyncHttpClient	client;
	private SuggestAdapter  adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		client=new AsyncHttpClient();
		setContentView(R.layout.activity_evaluate_suggest);
		bt_back = (Button) findViewById(R.id.bt_back);
		lv_sugest = (ListView) findViewById(R.id.lv_suggest);
	}

	private void initData() {
		Intent intent = getIntent();
		teacherid = intent.getExtras().getInt("teacherid");
		courseid = intent.getExtras().getInt("courseid");
		RequestParams params = new RequestParams();
		System.out.println("teahcerid"+teacherid+":courseid:"+courseid+"=======");
		
		params.put("teacherid", teacherid + "");
		params.put("courseid", courseid + "");
		adapter=new SuggestAdapter(EvaluateSuggestActivity.this, suggests);
		lv_sugest.setAdapter(adapter);
		client.post(HttpUtil.server_evaluate_suggest, params, new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(int arg0, JSONObject arg1) {
				for(int i=0;i<arg1.optJSONArray("result").length();i++){
					JSONObject object = arg1.optJSONArray("result")
							.optJSONObject(i);
					String str=object.optString("suggest");
					suggests.add(str);
				}
				System.out.println("展示结果："+suggests.size()+"===");
				adapter.notifyDataSetChanged();
				super.onSuccess(arg0, arg1);
			}
		});
	}

	private void initListener() {
		bt_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EvaluateSuggestActivity.this.finish();
			}
		});
	}
}
