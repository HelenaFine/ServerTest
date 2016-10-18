package com.exercise.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//读取资源文件mime.properties
//之后可以通过文件中的key值拿到对应的value值
//mime.properties文件中存放的是不同后缀名的文件类型 在http协议中的响应类型是什么
//.html文件类型 对应的 http响应类型是 text/html
public class MIMEUtil {
	private static Properties p;
	
	static{
		
		InputStream in = null;
		try {
			p = new Properties();
			in = MIMEUtil.class.getResourceAsStream("mime.properties");
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
	
	public static String getContentType(String contentType){
		return p.getProperty(contentType);
	}
	
	public static void main(String[] args) {
		System.out.println(MIMEUtil.getContentType("html"));
	}
	
}
