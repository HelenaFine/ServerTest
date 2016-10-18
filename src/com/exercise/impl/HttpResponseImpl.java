package com.exercise.impl;

import static com.exercise.utils.StatusCodeUtil.getStatusMsg;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.exercise.http.HttpRequest;
import com.exercise.http.HttpResponse;
import com.exercise.utils.ConfigUtil;

public class HttpResponseImpl implements HttpResponse{
    private Socket client;
    private OutputStream out;
    private PrintWriter pw;
    private StringBuffer sBuffer;
	private HttpRequest request;
    public HttpResponseImpl(Socket client,HttpRequest request){
    	this.client = client;
    	this.request = request;
    	try {
    		sBuffer = new StringBuffer();
			out = client.getOutputStream();
			pw = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    }
	
	
	@Override
	public OutputStream getOutputStream() throws Exception {
		return this.out;
	}

	@Override
	public PrintWriter getPrintWriter() throws Exception {
		return this.pw;
	}

	@Override
	public void setStatusLine(String statusCode) {
		sBuffer.append("HTTP/1.1 "+statusCode+" "+getStatusMsg(statusCode));
		setCRLF();
	}

	@Override
	public void setStatusLine(int statusCode) {
		setStatusLine(statusCode+"");
		
	}

	@Override
	public void setResponseHeader(String hName, String hValue) {
		sBuffer.append(hName+": "+hValue);
		setCRLF();		
	}

	@Override
	public void setContentType(String contentType) {
		setResponseHeader("Content-Type",contentType);
	}

	@Override
	public void setContentType(String contentType, String charsetName) {
		setResponseHeader("Content-Type",contentType+";charset="+charsetName);

	}

	@Override
	public void setCRLF() {
		sBuffer.append("\r\n");

	}

	@Override
	public void printResponseHeader() {
		String str = sBuffer.toString();
		//注意这里 一定不能使用换行 要不然图片显示不了
		pw.print(str);
		pw.flush();
//		System.out.println(str);
	}

	@Override
	public void printResponseContent(String requestPath) {
		FileInputStream fis = null;
		try {
			File file = new File(ConfigUtil.getConfigValue("rootPath"),requestPath);
			fis = new FileInputStream(file);
			byte[] buf = new byte[1024];
			int len = -1;
			while((len=fis.read(buf))!=-1){
				out.write(buf, 0, len);
//				System.out.println(new String(buf,0,len));
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(fis!=null)fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}

}
