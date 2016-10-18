package com.exercise.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//读取资源文件config.properties
//之后可以通过文件中的key值拿到对应的value值
//config.properties文件中存放的是系统相关配置信息,可以把系统中经常改动的信息配置到这里
public class ConfigUtil {
	private static Properties p;
	
	static{
		
		InputStream in = null;
		try {
			p = new Properties();
			in = ConfigUtil.class.getResourceAsStream("config.properties");
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
	
	public static String getConfigValue(String configName){
		return p.getProperty(configName);
	}
	
	public static void main(String[] args) {
		System.out.println(ConfigUtil.getConfigValue("rootPath"));
	}
	
}
