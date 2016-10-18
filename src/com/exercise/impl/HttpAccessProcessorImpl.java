package com.exercise.impl;

import com.exercise.http.HttpAccessProcessor;
import com.exercise.http.HttpRequest;
import com.exercise.http.HttpResponse;
import com.exercise.http.Servlet;
import com.exercise.utils.ErrorPageUtil;
import com.exercise.utils.MIMEUtil;
import com.exercise.utils.ServletMappingUtil;

public class HttpAccessProcessorImpl implements HttpAccessProcessor{

	public HttpAccessProcessorImpl(){
		
	}
	
	@Override
	public void processStaticResource(HttpRequest request, HttpResponse response) {
		//拿到请求的静态资源文件的后缀
				String[] str = request.getRequestPath().split("[.]");
				
				response.setStatusLine(200);
				
				response.setContentType(MIMEUtil.getContentType(str[str.length-1]));
				
				response.setCRLF();
				
				response.printResponseHeader();
				
				response.printResponseContent(request.getRequestPath());
	}

	@Override
	public void processDynamicResource(HttpRequest request, HttpResponse response) {
		
		response.setStatusLine(200);
		
		response.setContentType(MIMEUtil.getContentType("html"),"UTF-8");
		
		response.setCRLF();
		
		response.printResponseHeader();
          String className = ServletMappingUtil.getServletClass(request.getRequestPath());
		
		try {
			Object o = Class.forName(className).newInstance();
			if(o instanceof Servlet){
				((Servlet)o).service(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void sendError(int statusCode, HttpRequest request, HttpResponse response) {
       response.setStatusLine(statusCode);
       response.setContentType(MIMEUtil.getContentType("html"), "utf-8");
       response.setCRLF();
       response.printResponseHeader();
		response.printResponseContent(ErrorPageUtil.getErrorPagePath(statusCode));
		}

}
