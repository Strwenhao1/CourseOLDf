package com.coursemis.view.activity;

import org.json.JSONObject;

import com.coursemis.R;
import com.coursemis.model.Course;
import com.coursemis.model.Coursetime;
import com.coursemis.util.DialogUtil;
import com.coursemis.util.HttpUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class CourseEditActivity extends Activity {

	public Context context;///
	private AsyncHttpClient client;
	
	SharedPreferences preferences;
	
	private Button back;
	private TextView top_title;
	private Button finise_edit;
	private EditText name_course;
	private Button time_course_button;
	private EditText time_course_editText;
	//private Spinner time_course_1;
	private Spinner time_course_2,time_course_3;
	//private ArrayAdapter<String> adapter_1;
	private ArrayAdapter<String> adapter_2;
	private static String [] time_1 = {"周一","周二","周三","周四","周五","周六","周日"};
	private static String [] time_2 = {"1","2","3","4","5","6","7","8","9","10","11","12"};
	private EditText address_course;
	
	private int COURSETIME = 0;
	private int weekday;
	private int startclass;
	private int endclass;
	
	private int teacherid;
	//一下用于课程编辑
	private String action;
	private int courseid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_edit);
		
		this.context = this;
		client = new AsyncHttpClient();
		
		preferences = getSharedPreferences("courseMis", 0);
		
		teacherid = preferences.getInt("teacherid", 0);//0为默认值
		
		Intent intent = getIntent();
		/*teacherid = intent.getExtras().getInt("teacherid");*/
		//action = intent.getExtras().getString("action");
		
		courseid = intent.getExtras().getInt("courseid");
		
		back=(Button)findViewById(R.id.reback_btn);
		
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CourseEditActivity.this.finish();
			}
			
		});
		
		top_title = (TextView)findViewById(R.id.title);
		top_title.setText("         课程编辑             ");
		finise_edit = (Button) findViewById(R.id.continue_btn);
		name_course = (EditText) findViewById(R.id.name_course_edit);
		//time_course_1 = (Spinner) findViewById(R.id.time_course_1);
		time_course_button = (Button) findViewById(R.id.time_course_button);
		time_course_editText = (EditText) findViewById(R.id.time_course_editText);
		time_course_2 = (Spinner) findViewById(R.id.time_course_2);
		time_course_3 = (Spinner) findViewById(R.id.time_course_3);
		address_course = (EditText) findViewById(R.id.address_course);
		//adapter_1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,time_1);
		//adapter_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		adapter_2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,time_2);
		adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		//time_course_1.setAdapter(adapter_1);
		time_course_2.setAdapter(adapter_2);
		time_course_3.setAdapter(adapter_2);
		
		
		RequestParams params = new RequestParams();
		params.put("courseid", courseid+"");
		params.put("action", "course_info");///
		
		client.post(HttpUtil.server_course_info, params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, JSONObject arg1) {
						// TODO Auto-generated method stub
						
						JSONObject object = arg1.optJSONArray("result").optJSONObject(0);
						Coursetime coursetime = new Coursetime();
						coursetime.setCtAddress(object.optString("CtAddress").toString());
						coursetime.setCtEndClass(object.optInt("CtEndClass"));
						coursetime.setCtId(object.optInt("CtId"));
						coursetime.setCtStartClass(object.optInt("CtStartClass"));
						coursetime.setCtWeekChoose(object.optInt("CtWeekChoose"));
						coursetime.setCtWeekDay(object.optInt("CtWeekDay"));
						coursetime.setCtWeekNum(object.optInt("CtWeekNum"));
						
						Course course = new Course();
						course.setCId(object.optJSONObject("course").optInt("CId"));
						course.setCName(object.optJSONObject("course").optString("CName"));
						coursetime.setCourse(course);
						
						name_course.setText(course.getCName());
						
						String [] day = {"周一","周二","周三","周四","周五","周六","周日"};
						weekday = coursetime.getCtWeekDay();
						String week_day_temp = day[coursetime.getCtWeekDay()-1];
						time_course_editText.setText(week_day_temp);
						
						//time_course_1.setSelection(coursetime.getCtWeekDay()-1);
						time_course_2.setSelection(coursetime.getCtStartClass()-1);
						time_course_3.setSelection(coursetime.getCtEndClass()-1);
						address_course.setText(coursetime.getCtAddress());
						
						super.onSuccess(arg0, arg1);
					}

				});
		time_course_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			
				Intent intent = getIntent();		        
		        intent.setClass(CourseEditActivity.this,CourseTimeActivity.class);		        
		        CourseEditActivity.this.startActivityForResult(intent,COURSETIME);
				
			}
		});
		/*time_course_1.setOnItemSelectedListener(
				new OnItemSelectedListener(){

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						weekday = arg2+1;
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
					}
			
		});*/
		time_course_2.setOnItemSelectedListener(
				new OnItemSelectedListener(){

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						startclass = arg2 + 1;
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
					}
			
		});
		time_course_3.setOnItemSelectedListener(
				new OnItemSelectedListener(){

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						endclass = arg2 + 1;
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
					}
			
		});
		
		finise_edit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RequestParams params = new RequestParams();
				params.put("courseid", courseid+"");
				params.put("name_course", name_course.getText().toString().trim());
				params.put("weekday", weekday+"");
				params.put("startclass", startclass+"");
				params.put("endclass", endclass+"");
				params.put("address_course", address_course.getText().toString().trim());
				DialogUtil.showDialog(context, "address_course: "+address_course.getText().toString(), true);
				client.post(HttpUtil.server_course_edit , params,
						new JsonHttpResponseHandler() {

							@Override
							public void onSuccess(int arg0, JSONObject arg1) {
								// TODO Auto-generated method stub
								String editCourse_msg = arg1.optString("result");
								DialogUtil.showDialog(context, editCourse_msg, true);
								
								Intent intent = new Intent(CourseEditActivity.this, CourseInfoActivity.class);
								Bundle bundle = new Bundle(); 
								bundle.putInt("teacherid", teacherid);
								bundle.putInt("courseid", courseid);
								intent.putExtras(bundle);
								CourseEditActivity.this.startActivity(intent);
								CourseEditActivity.this.finish();
								
								super.onSuccess(arg0, arg1);
							}

						});
				
			}
		});
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == COURSETIME && resultCode == Activity.RESULT_OK) {
			Bundle bundle = intent.getExtras();
			String resultTime = bundle.getString("time_1");
			weekday = bundle.getInt("weekday_1");
			System.out.println(resultTime);
			time_course_editText.setText(resultTime);
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.course_edit, menu);
		return true;
	}

}
