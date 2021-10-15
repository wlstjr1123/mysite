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

public class BoardModifyRegAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		String writeNo = request.getParameter("writeNo");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		if (page == null || page.equals("")) {
			page = "0";
		}
		
		HttpSession session = request.getSession();
		UserVo sessionVo = (UserVo) session.getAttribute("authUser");
		if (sessionVo == null) {
			MvcUtil.redirect(request.getContextPath() + "/user?a=loginform" + page, request, response);
			return;
		}
		
		new BoardDao().updateModify(Long.parseLong(writeNo), title, content);
		
		MvcUtil.redirect(request.getContextPath() + "/board?a=list&page=" + page, request, response);

	}

}
