package com.douzone.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.UserDao;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UserVo vo = new UserDao().findByEmailAndPassword(email, password);
		if (vo == null) {
			/* 로그인 실패 */
			request.setAttribute("result", "fail");
			MvcUtil.forward("user/loginform", request, response);
			return;
		}
		
		/* 인증 처리(세션처리) */
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", vo);
		
		MvcUtil.redirect("/mysite02", request, response);
	}

}
