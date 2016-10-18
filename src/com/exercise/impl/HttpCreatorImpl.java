package com.exercise.impl;

import java.net.Socket;

import com.exercise.http.HttpAccessProcessor;
import com.exercise.http.HttpCreator;
import com.exercise.http.HttpRequest;
import com.exercise.http.HttpResponse;

public class HttpCreatorImpl implements HttpCreator {
    private HttpRequest request;
    private HttpResponse response;
    private HttpAccessProcessor processor;
    private Socket client;
	
    public  HttpCreatorImpl(Socket client){
		this.client = client;
		request = new HttpRequestImpl(client);
		response = new HttpResponseImpl(client,request);
		processor = new HttpAccessProcessorImpl();
		
	}
	
	
	
	@Override
	public HttpRequest getHttpRequest() {
		return this.request;
	}

	@Override
	public HttpResponse getHttpResponse() {
		return this.response;
	}

	@Override
	public HttpAccessProcessor getHttpAccessProcessor() {
		return this.processor;
	}

}
