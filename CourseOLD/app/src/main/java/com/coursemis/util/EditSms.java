package com.coursemis.util;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.R.color;
import android.R.integer;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class EditSms {

	
public Intent execute(Context context, int []personnum) {
int[] colors = new int[] { Color.RED, Color.YELLOW, Color.BLUE };
DefaultRenderer renderer = buildCategoryRenderer(colors);
CategorySeries categorySeries = new CategorySeries("grade shows");
categorySeries.add("良好", personnum[0]);
categorySeries.add("一般", personnum[1]);
categorySeries.add("优秀 ", personnum[2]);  //  现在是在本地，到时改成传过的数据即可
return ChartFactory.getPieChartIntent(context, categorySeries, renderer,null);
}
  
protected DefaultRenderer buildCategoryRenderer(int[] colors) {
	DefaultRenderer renderer = new DefaultRenderer();
	for (int color : colors) {
		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		r.setColor(color);
		renderer.addSeriesRenderer(r);
	}
	return renderer;
	}
}
