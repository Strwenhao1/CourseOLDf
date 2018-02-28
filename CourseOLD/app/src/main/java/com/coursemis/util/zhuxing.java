package com.coursemis.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.R.color;
import android.R.integer;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class zhuxing {
private static final int SERIES_NR =3;

private int [] years;
	
public Intent execute(Context context, int [][]grade, int []year) {
	years = new int[year.length];
	years = year;
return  ChartFactory.getBarChartIntent(context, getBarDemoDataset(grade, year), getBarDemoRenderer(), Type.DEFAULT);
}
  

private XYMultipleSeriesDataset getBarDemoDataset(int [][]grade, int []year) {
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    //final int nr = 10;
    final int nr = grade.length;
    //int grade[]={10,20,30};
    String []str={"优秀","良好","一般"};
    for ( int i = 0; i < SERIES_NR ; i++)
    {
      CategorySeries series = new CategorySeries(str[i]);
      //加
      	for(int j = 0,k=0; j < year[year.length -1]; j++){
      		
      		if(j==year[k]){
      			series.add(grade[k][i]);
      			k++;
      		}
      		else{
      			series.add(0);
      		}
      	}
      
//    	  for ( int k = 0; k < nr; k++) {
//    		  series.add(grade[k][i]);
//    	  }
      dataset.addSeries(series.toXYSeries());
    }
    return dataset;
  }

public XYMultipleSeriesRenderer getBarDemoRenderer() {
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    SimpleSeriesRenderer r = new SimpleSeriesRenderer();
    r.setColor(Color. BLUE );
    renderer.addSeriesRenderer(r);
    r = new SimpleSeriesRenderer();
    r.setColor(Color. GREEN );
    renderer.addSeriesRenderer(r);
    r = new SimpleSeriesRenderer();
    r.setColor(Color. RED );
    renderer.addSeriesRenderer(r);
    setChartSettings(renderer);
    return renderer;
  }

   private void setChartSettings(XYMultipleSeriesRenderer renderer) {
    renderer.setChartTitle( "教师评价表" );
    renderer.setXTitle( "年份" );
    renderer.setYTitle( "人数" );
    renderer.setXLabels(4);// 显示x轴的数字
    //renderer.setXAxisMin(1);
    //renderer.setXAxisMax(5);
    renderer.setXAxisMin(years[0]);
    renderer.setXAxisMax(years[years.length-1]);
    renderer.setYAxisMin(0);
    renderer.setYAxisMax(100);
    renderer.setBarSpacing(0.5);  //设置柱状图中柱子之间的间隔 
  }
}
