package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class BoardDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		String no = request.getParameter("no");
		
		HttpSession session = request.getSession();
		UserVo sessionVo = (UserVo) session.getAttribute("authUser");
		if (sessionVo == null) {
			MvcUtil.redirect(request.getContextPath() + "/board?a=list&page=" + page, request, response);
			return;
		}
		
		new BoardDao().delete(Long.parseLong(no));
		
		MvcUtil.redirect(request.getContextPath() + "/board?a=list&page="+page, request, response);

	}

}
