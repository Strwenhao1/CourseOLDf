package com.coursemis.util;
import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.R.color;
import android.R.integer;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
public class quxian {
	public Intent execute(Context context , double[] years, double [] points )
	{
		  String[] titles = new String[] { "各年份优秀率"};
		  List <double[]>x = new ArrayList<double[]>();
		  for(int i=0;i<titles.length;i++)
		  {
		  	//x.add(new double[]{2010,2011,2012,2013});///
			  x.add(years);
		  }
		  List<double[]>y=new ArrayList<double[]>();
		
		  //y.add(new double[] {80,90,100,90});
		  y.add(points);
		  
		  //设置画笔的颜色
		  int[] colors = new int[] { Color.BLUE};
		  //设置曲线节点处的形状
		  PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE};
		  //构造渲染器
		  XYMultipleSeriesRenderer renderer=buildRenderer(colors,styles,true);
		  int length=renderer.getSeriesRendererCount();
		  //拐点实心填充
		  for(int i=0;i<length;i++)
		  {
			  ((XYSeriesRenderer)renderer.getSeriesRendererAt(i)).setFillPoints(true);
			  
		  }
		  
		  //setChartSettings(renderer, "教师评价走势", "年份", "优秀率", 2010, 2013, 0, 100 , Color.LTGRAY, Color.LTGRAY);
		  setChartSettings(renderer, "教师评价走势", "年份", "优秀率", years[0]-1, years[years.length-1]+1, 0, 100 , Color.LTGRAY, Color.LTGRAY);
		  renderer.setXLabels(4);// 显示x轴的数字
		  renderer.setYLabels(9);
		  renderer.setShowGrid(true);//设置是否在图表中显示网格
		  renderer.setYLabelsAlign(Align.RIGHT);//设置刻度线与Y轴之间的相对位置关系
		  renderer.setZoomButtonsVisible(true);  // 是否显示右下角的放大缩小还原按钮
		  Intent intent=ChartFactory.getLineChartIntent(context,buildDataset(titles,x,y) , renderer, "教师评价走势");
		
		  
		  return intent;
	}
	//将颜色和  节点形状组合成渲染器
	protected XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles, boolean fill)
       {
           XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
           int length = colors.length;
           for (int i = 0; i < length; i++)
           {
               XYSeriesRenderer r = new XYSeriesRenderer();
               r.setColor(colors[i]);
               r.setPointStyle(styles[i]);
               r.setFillPoints(fill);
               renderer.addSeriesRenderer(r);
           }
           return renderer;
       }
	
	//设置x轴y轴的显示
	 protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title,
             String xTitle,String yTitle, double xMin,double xMax, double yMin, double yMax,
             int axesColor,int labelsColor)
	 			{
				renderer.setChartTitle(title);
				renderer.setXTitle(xTitle);
				renderer.setYTitle(yTitle);
				renderer.setXAxisMin(xMin);
				renderer.setXAxisMax(xMax);
				renderer.setYAxisMin(yMin);
				renderer.setYAxisMax(yMax);
				renderer.setAxesColor(axesColor);
				renderer.setLabelsColor(labelsColor);
	 			}
	 
	 
	 
	 protected XYMultipleSeriesDataset buildDataset(String[] titles,List xValues, List yValues)
		{
			XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
			
			int length = titles.length;                  //有几条线
			for (int i = 0; i < length; i++)
			{
				XYSeries series = new XYSeries(titles[i]);    //根据每条线的名称创建
				double[] xV = (double[]) xValues.get(i);                 //获取第i条线的数据
				double[] yV = (double[]) yValues.get(i);
				int seriesLength = xV.length;                 //有几个点
			
				for (int k = 0; k < seriesLength; k++)        //每条线里有几个点
				{
					series.add(xV[k], yV[k]);
				}
			
				dataset.addSeries(series);
			}
			
			return dataset;
		}

	}
