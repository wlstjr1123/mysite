package com.douzone.mysite.security;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.service.AdminService;
import com.douzone.mysite.vo.AdminVo;

public class SiteInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	AdminService adminService;
	
	@Autowired
	ServletContext servletContext;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		AdminVo vo = (AdminVo) servletContext.getAttribute("site");
		
		if (vo == null) {
			servletContext.setAttribute("site", adminService.getAdminContent());
		}
		
		
		return true;
	}

}
