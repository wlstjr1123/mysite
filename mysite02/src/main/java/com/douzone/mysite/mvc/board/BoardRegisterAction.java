package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class BoardRegisterAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		Long userNo;
		
		HttpSession session = request.getSession();
		UserVo sessionUserVo = (UserVo) session.getAttribute("authUser");
		if (sessionUserVo == null || title == null || title.equals("") || contents == null || contents.equals("")) {
			MvcUtil.redirect(request.getContextPath() + "/board?a=list", request, response);
			return;
		}
		
		userNo = sessionUserVo.getNo();
		
		BoardVo boardVo = new BoardVo();
		boardVo.setTitle(title);
		boardVo.setContents(contents);
		boardVo.setUserNo(userNo);
		
		new BoardDao().insert(boardVo);
		
		MvcUtil.redirect(request.getContextPath() + "/board?a=list", request, response);

	}

}
