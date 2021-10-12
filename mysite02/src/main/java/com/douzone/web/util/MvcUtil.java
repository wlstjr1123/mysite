package com.douzone.web.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MvcUtil {
	public static void forward(String path, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

	public static void redirect(String url, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.sendRedirect(url);		
	}
}
