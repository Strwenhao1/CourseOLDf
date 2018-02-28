package com.coursemis.view.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.coursemis.R;
import com.coursemis.adapter.CourseAdapter;
import com.coursemis.model.Course;
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

/**
 * 
 * @author Sivan
 * @2017-5-4下午7:56:22
 * @描述 教师选择要查看教学反馈的课程
 * 
 */
public class EvaluateChooseActivity extends Activity {

	private Button			bt_back;
	private ListView		lv_course_teacher;
	private AsyncHttpClient	client;
	private int				teacherid;
	private CourseAdapter	courseAdapter;
	private List<Course>	courseList=new ArrayList<Course>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		client = new AsyncHttpClient();
		Intent intent = getIntent();
		teacherid = intent.getExtras().getInt("teacherid");
		initData();
		initListener();
	}

	private void initView() {
		setContentView(R.layout.activity_evaluate_choose);
		bt_back = (Button) findViewById(R.id.bt_back);
		lv_course_teacher = (ListView) findViewById(R.id.lv_course_teacher);
	}

	private void initData() {
		if(courseAdapter==null){
			courseAdapter=new CourseAdapter(this,courseList,teacherid);
		}
		lv_course_teacher.setAdapter(courseAdapter);
		RequestParams params = new RequestParams();
		params.put("tid", teacherid + "");
		client.post(HttpUtil.server_teacher_course, params,
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, JSONObject arg1) {
						super.onSuccess(arg0, arg1);
						for(int i=0;i<arg1.optJSONArray("result").length();i++){
							Course course=new Course();
							JSONObject object=arg1.optJSONArray("result").optJSONObject(i);
							course.setCId(object.optInt("CId"));
							course.setCName(object.optString("CName"));
							courseList.add(course);
						}
						courseAdapter.notifyDataSetChanged();
					}
				});
	}

	private void initListener() {
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EvaluateChooseActivity.this.finish();
			}
		});
	}
}
