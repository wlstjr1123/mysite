package com.douzone.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class LoginFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Access COntrol(보안, 인증체크)
				HttpSession session = request.getSession();
				UserVo authUser = (UserVo) session.getAttribute("authUser");
				if (authUser != null) {
					MvcUtil.redirect(request.getContextPath(), request, response);
					return;
				}
				
		MvcUtil.forward("user/loginform", request, response);

	}

}
