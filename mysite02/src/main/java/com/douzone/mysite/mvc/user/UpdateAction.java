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

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
				
		// Access COntrol(보안, 인증체크)
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		
		
		if ((name == null || name.isEmpty()) ||
				(password == null || password.isEmpty()) ||
				(gender == null || gender.isEmpty())) {
			/* 로그인 실패 */
			MvcUtil.forward("user/updateform", request, response);
			return;
		}
		
		UserVo vo = new UserVo();
		vo.setNo(Long.parseLong(no));
		vo.setName(name);
		vo.setPassword(password);
		vo.setGender(gender);
		
		new UserDao().update(vo);
		
		/* 인증 처리(세션처리) */
		UserVo vo2 = new UserVo();
		vo2.setNo(Long.parseLong(no));
		vo2.setName(name);
		HttpSession session2 = request.getSession(true);
		session2.setAttribute("authUser", vo2);
		
		MvcUtil.redirect("/mysite02/user?a=joinsuccess", request, response);
		
		
	}

}
