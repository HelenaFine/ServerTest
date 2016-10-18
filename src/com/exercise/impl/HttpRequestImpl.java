package com.exercise.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.exercise.http.HttpRequest;
import com.exercise.utils.ConfigUtil;
import com.exercise.utils.ServletMappingUtil;

public class HttpRequestImpl implements HttpRequest{
    private Socket client;
    private String protocol;
    private String requestMethod;
    private String requestPath;
    private Map<String,String> requestHeader;
    private Map<String,String> parameters;
    private  BufferedReader  br ;
	private boolean isNullRequest;
	
	
    public HttpRequestImpl(Socket client){
    	this.client = client;
    	requestHeader = new HashMap<String,String>();
    	parameters = new HashMap<String,String>();
    	getInfos();
    }
    
    
	private void getInfos(){
		try {
			br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			//GET /test.html HTTP/1.1
			String requestLine = br.readLine();
			//处理浏览器发送空请求的情况
			if(requestLine==null){
				this.isNullRequest = true;
				return;
			}
			
			parseRequestLine(requestLine);
			
			String rh = null;
			while(!"".equals(rh=br.readLine())){
				parseRequestHeader(rh);
			}
			
			if(br.ready()){
				char[] buf = new char[1024];
				int len = br.read(buf);
				parseParameterByPost(new String(buf,0,len));
			}else{
				parseParameterByGet(requestPath);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void parseRequestLine(String requestLine) throws Exception{
		String[] str = requestLine.split(" ");
		if(str.length!=3){
			throw new Exception("请求行的格式不符合http协议");
		}
		this.requestMethod = str[0];
		this.requestPath = str[1];
		this.protocol = str[2];
	}
	
	private void parseRequestHeader(String rh) throws Exception{
		String[] str = rh.split(": ");
		if(str.length!=2){
			throw new Exception("request消息报头的格式不符合http协议");
		}
		requestHeader.put(str[0],str[1]);
	}
	
	private void parseParameterByPost(String strParameter){
		//id=2&name=tom
		String[] str = strParameter.split("&");
		for(String kv:str){
			String[] kvStr = kv.split("=");
			if(kvStr.length<=1){
				return;
			}
			String parameterName = kvStr[0];
			String parameterValue = kvStr[1];
			parameters.put(parameterName, parameterValue);
		}
	}
	
	private void parseParameterByGet(String requestPath){
		// /test.html?id=2&name=tom
		//[/test.html, id=2&name=tom]
		// /test.html
		String[] str = requestPath.split("[?]");
		if(str.length<=1){
			return;
		}
		parseParameterByPost(str[1]);
		this.requestPath = str[0];
	}
	
	@Override
	public String getProtocol() {
		return this.protocol;	
		}

	@Override
	public String getRequestMethod() {
		return this.requestMethod;

	}

	@Override
	public String getRequestPath() {
		return this.requestPath;
	}

	@Override
	public Map<String, String> getRequestHeader() {
		return this.requestHeader;
	}

	@Override
	public String getParameter(String parameterName) {
		return parameters.get(parameterName);
	}

	@Override
	public boolean isStaticResource() {
		File file = new File(ConfigUtil.getConfigValue("rootPath"),requestPath);
		return file.exists();
	}

	@Override
	public boolean isDynamicResource() {
		return ServletMappingUtil.isContainsKey(requestPath);
	}

	@Override
	public boolean isNullRequest() {
		return this.isNullRequest;
	}

}
