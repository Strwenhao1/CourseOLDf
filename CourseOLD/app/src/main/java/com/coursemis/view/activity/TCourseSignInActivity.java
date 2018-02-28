package com.coursemis.view.activity;

import java.util.ArrayList;

import com.coursemis.R;
import com.coursemis.util.P;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class TCourseSignInActivity extends Activity{
	
	private Intent intent =null;
	ArrayList<String> list=null;
	private ListView lv=null;
	
	public void ButtonOnclick_tcoursesignin__back(View view)
	{
		finish();
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		intent=getIntent();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tcoursesigninactivity);
		ArrayList<String> list_temp= new ArrayList<String>();
		list=intent.getStringArrayListExtra("studentCourseSignInInfo");
		
		for(int i =1;i<list.size();i++)
		{
			String temp =list.get(i);
			String temp1 = temp.substring(0, temp.indexOf(" "));//
			String temp2 = temp.substring(temp.indexOf(" ")+1,temp.length());
			String temp3 = temp2.substring(0,temp2.indexOf(" "));//
			String temp4 = temp2.substring(temp2.indexOf(" ")+1,temp2.length());
			String temp5 = temp4.substring(0,temp4.indexOf(" "));//
			String temp6 = temp4.substring(temp4.indexOf(" ")+1,temp4.length());//
			list_temp.add("学号:"+temp1+"姓名:"+temp3+"已到次数:"+temp5+"总次数:"+temp6);
			
		}
		lv = (ListView) findViewById(R.id.t_coursesignListview);        
	        ArrayAdapter<String> a = new ArrayAdapter<String>(this,
	         	R.layout.listview_item_1, list);
	        lv.setAdapter(a);
	        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
	}

}
