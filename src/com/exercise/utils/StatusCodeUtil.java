package com.exercise.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//读取资源文件status_code.properties
//之后可以通过文件中的key值拿到对应的value值
//status_code.properties文件中存放的是http协议中响应状态码所对应的信息描述
//例如 404 对应的描述是 NotFound
public class StatusCodeUtil {
	private static Properties p;
	
	static{
		
		InputStream in = null;
		try {
			p = new Properties();
			in = StatusCodeUtil.class.getResourceAsStream("status_code.properties");
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
	
	public static String getStatusMsg(String statusCode){
		return p.getProperty(statusCode);
	}
	public static String getStatusMsg(int statusCode){
		return getStatusMsg(statusCode+"");
	}
	
	public static void main(String[] args) {
		System.out.println(StatusCodeUtil.getStatusMsg(500));
	}
	
}
