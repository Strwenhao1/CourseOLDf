package com.coursemis.util;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpDownloader {
	
	private URL url = null; 
	
	/**
	 * 根据URL下载文件,前提是这个文件当中的内容是文本,函数的返回值就是文本当中的内容
	 * 1.创建一个URL对象
	 * 2.通过URL对象,创建一个HttpURLConnection对象
	 * 3.得到InputStream
	 * 4.从InputStream当中读取数据
	 * @param urlStr:网络文件地址
	 * @param path:指定下载到SD卡上的文件目录
	 * @return 保存到SD卡的文件路径
	 */
	public String download(String urlStr,String path){
		
		int start = urlStr.lastIndexOf("/");
		int end = urlStr.length();
		String fileName = urlStr.substring(start,end);//截取文件名，为下载到SD卡上的文件名
		
		HttpURLConnection urlConn = null;
		try {
			url = new URL(urlStr);
			urlConn = (HttpURLConnection)url.openConnection();
			urlConn.connect();//一定要加上，否则urlConn.getInputStream()报错
			urlConn.setConnectTimeout(6000);
			InputStream inputStream = urlConn.getInputStream();
			FileUtil fileUtils = new FileUtil();
			File resultFile = fileUtils.write2SDFromInput(path, fileName, inputStream);
			
			if(resultFile == null){
					return null;
			}
			return resultFile.getAbsolutePath();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
				if (null != urlConn)
					urlConn.disconnect();
		}
		return null;
	}
}