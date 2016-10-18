package com.exercise.servlet;

import java.io.PrintWriter;

import com.exercise.http.HttpRequest;
import com.exercise.http.HttpResponse;
import com.exercise.http.Servlet;

public class LoginServlet implements Servlet{

	@Override
	public void service(HttpRequest request, HttpResponse response) {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		String msg = null;
		//连接数据库进行查询
		//tom 123
		if("tom".equals(username)&&"123".equals(password)){
			msg = "hello tom,欢迎您的登录";
		}else{
			msg = "抱歉,用户名或者密码不正确";
		}
		
		try {
			PrintWriter pw = response.getPrintWriter();
			pw.println("<html>");
				pw.println("<head>");
					pw.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
				pw.println("</head>");
				pw.println("<body>");
					pw.println("<h1>"+msg+"</h1>");
				pw.println("</body>");
			pw.println("</html>");
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
