package com.exercise.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//读取资源文件error_page.properties
//之后可以通过文件中的key值拿到对应的value值
//error_page.properties文件中存放的是错误信息页面存放的位置
//例如显示404错误的页面文件的路径是 /error/404.html
public class ErrorPageUtil {
	private static Properties p;
	
	static{
		
		InputStream in = null;
		try {
			p = new Properties();
			in = ErrorPageUtil.class.getResourceAsStream("error_page.properties");
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(in!=null)in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getErrorPagePath(String errorCode){
		return p.getProperty(errorCode);
	}
	public static String getErrorPagePath(int errorCode){
		return getErrorPagePath(errorCode+"");
	}
	
	public static void main(String[] args) {
		System.out.println(ErrorPageUtil.getErrorPagePath(404));
	}
	
}
