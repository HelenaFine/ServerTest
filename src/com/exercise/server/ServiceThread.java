package com.exercise.server;

import java.io.IOException;
import java.net.Socket;

import com.exercise.http.HttpAccessProcessor;
import com.exercise.http.HttpCreator;
import com.exercise.http.HttpRequest;
import com.exercise.http.HttpResponse;
import com.exercise.impl.HttpCreatorImpl;

//给客户端浏览器提供访问服务的线程
public class ServiceThread extends Thread{
	private Socket client;
	
	public ServiceThread(Socket client) {
		this.client = client;
	}


	@Override
	public void run() {
		
		try {
			//创建HttpCreator对象
			HttpCreator creator = new HttpCreatorImpl(client);
			

			//通过HttpCreator分别获得request和response
			HttpRequest request = creator.getHttpRequest();
			HttpResponse response = creator.getHttpResponse();
			
			//通过HttpCreator获得资源处理器对象(processor)
			HttpAccessProcessor processor = creator.getHttpAccessProcessor();
			
			//如果请求的是静态资源
			if(request.isStaticResource()){
				processor.processStaticResource(request, response);
			}
			//如果请求的是动态资源
			else if(request.isDynamicResource()){
				processor.processDynamicResource(request, response);
			}
			//如果是空请求
			else if(request.isNullRequest()){
				return;
			}
			//服务器中没有本次要访问的资源
			else{
				processor.sendError(404,request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//都处理完之后关闭连接
			try {
				if(client!=null)client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
