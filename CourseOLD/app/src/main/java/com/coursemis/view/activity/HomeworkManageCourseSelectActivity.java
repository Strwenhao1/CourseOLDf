package com.coursemis.view.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.coursemis.R;
import com.coursemis.util.HttpUtil;
import com.coursemis.util.P;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HomeworkManageCourseSelectActivity extends Activity {
	private ListView hms=null;
	private ArrayList<String> courseInfo=null;
	Intent intent=null;
	Intent intent_temp=null;
	LinearLayout layout=null;
	Button back=null;
	int tid;
	private AsyncHttpClient client=new AsyncHttpClient();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homeworkmanagecourseselect);
		LinearLayout ll=(LinearLayout)findViewById(R.id.hwmcs);
		intent=getIntent();
		courseInfo=intent.getStringArrayListExtra("courselist");
		hms=(ListView)findViewById(R.id.homeworkmanagecourseListview);
		ArrayList<String> courseInfo_temp=new ArrayList<String>();
		for(int i=0;i<courseInfo.size();i++)
		{
			String temp=courseInfo.get(i);
			courseInfo_temp.add("课程名：		"+temp.substring(temp.indexOf(" ")+1, temp.length()));
		}
		
		ArrayAdapter<String> aaRadioButtonAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_checked, courseInfo_temp);
		hms.setAdapter(aaRadioButtonAdapter);
		hms.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		hms.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) {
				// TODO Auto-generated method stub
//				Uri data =Uri.parse(courseInfo.get(arg2));
//			    Intent result=new Intent(null,data);
//		        setResult(RESULT_OK,result);
//			finish();
			

			// TODO Auto-generated method stub
			String uriString=""+courseInfo.get(arg2);
			RequestParams params = new RequestParams();
			SharedPreferences  sharedata=getSharedPreferences("courseMis", 0);
			tid = Integer.parseInt(sharedata.getString("userID",null));
			params.put("tid", tid+"");
			params.put("courseinfo", uriString);
			
			client.post(HttpUtil.server_teacher_homework_select, params,
					new JsonHttpResponseHandler() {

						@Override
						public void onSuccess(int arg0, JSONObject arg1) {
							JSONArray object = arg1.optJSONArray("result");
							P.p(object.toString()+1111);
							
							if(object.length()==0){
								Toast.makeText(HomeworkManageCourseSelectActivity.this,"这门课程您还没有布置过作业", Toast.LENGTH_SHORT).show();
								ArrayList<String> list=new ArrayList<String>();
								Intent intent = new Intent(HomeworkManageCourseSelectActivity.this,HomeworkManageCourseSelectInfoActivity.class);
								intent.putStringArrayListExtra("sourcemanagelist", list);
								intent.putExtra("title",courseInfo.get(arg2));
								startActivity(intent);
								
							}else{
								ArrayList<String> list=new ArrayList<String>();
								for(int i=0;i<arg1.optJSONArray("result").length();i++){
									JSONObject object_temp = arg1.optJSONArray("result").optJSONObject(i);
									P.p(object_temp.toString()+2222);
									list.add(i, (object_temp.optInt("smid")+" "+(object_temp.optString("smname")+" "+  object_temp.optString("smpath") )));
									}
								
								Intent intent = new Intent(HomeworkManageCourseSelectActivity.this,HomeworkManageCourseSelectInfoActivity.class);
								intent.putStringArrayListExtra("sourcemanagelist", list);
								intent.putExtra("title", courseInfo.get(arg2));
								startActivity(intent);
							}
							
							super.onSuccess(arg0, arg1);
						}
					});
			
			
			
			
			
			
			}
		});
		
		
		P.p(courseInfo.size()+""+"111111111111111");
		back=(Button)findViewById(R.id.homeworkmanagecourseselect_back);
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				
			}});
//		for(int i=0;i<courseInfo.size();i++)
//		{
//			final Button btn = new Button(this);
//			btn.setText(courseInfo.get(i));
//			P.p(courseInfo.get(i));
//			btn.setOnClickListener(new OnClickListener(){
//
//				@Override
//				public void onClick(View arg0) {
//				}
//				
//			});
//			
//			
//			LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//			ll.addView(btn,lp1);
//		}
		
		
	
	}
		
		
	

	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.homework_manage_course_select, menu);
		return true;
	}

}
