package com.exercise.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//读取资源文件servlet_mapping.properties
//之后可以通过文件中的key值拿到对应的value值
//servlet_mapping.properties文件中存放的是服务器中哪些java类可以让浏览器进行访问
//例如 浏览器可以通过 /HelloWorld 这个路径访问到 com.briup.servlet.HelloWorldServlet类中的service方法
public class ServletMappingUtil {
	private static Properties p;
	
	static{
		
		InputStream in = null;
		try {
			p = new Properties();
			in = ServletMappingUtil.class.
					getResourceAsStream("servlet_mapping.properties");
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
	
	public static boolean isContainsKey(String servletPath){
		return p.containsKey(servletPath);
	}
	public static String getServletClass(String servletPath){
		
		return p.getProperty(servletPath);
	}
	
	public static void main(String[] args) {
		System.out.println(ServletMappingUtil.isContainsKey("/HelloWorld"));
		System.out.println(ServletMappingUtil.getServletClass("/HelloWorld"));
	}
	
}
