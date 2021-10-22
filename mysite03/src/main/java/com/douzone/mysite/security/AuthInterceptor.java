package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			throws Exception {
		
		// 1. handler 종류 확인
		if (handler instanceof HandlerMethod == false) {
			return true;
		}
		
		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		// 3. Handler Method의 @Auth 받아오기
		// getMethodAnnotation이 Auth클레스가 있는지 확인
		// {여기에 @Auth 있는지 확인}
		// public String 메서드(HttpSession session, Model model) {return "board/write";}
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// 4. Handler Method에 @Auth가 없으면 Type에 있는지 확인
		// {여기에 Auth 있는지 확인}
		// public class 클래스
		if (auth == null) {
			// 과제
			
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
//			auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
		}
		
		// 5. Type과 Method에 @Auth가 적용이 안되어 있는 경우
		// 3번과 4번이 모두 없을때.
		if (auth == null) {
			return true;
		}
		
		// 6. @Auth가 적용이 되어 있기 때문에 인증(Authenfication) 여부 확인
		// 여기서 확인해버리기.
		HttpSession session = request.getSession();
		if (session == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// 7. 권한(Authorization) 체크를 위해서 @Auth의 role 가져오기("USER", "ADMIN")
		String role = auth.role();
		
		
		// 8. 권한 체크 (6번 결과와 7번결과를 비교하기)
//		if ("ADMIN".equals(role)) {
//			if (authUser.getRole().equals("ADMIN")==false) {
//				response.sendRedirect(request.getContextPath());
//				return false;
//			}
//		}
		
		
		return true;
	}
}
