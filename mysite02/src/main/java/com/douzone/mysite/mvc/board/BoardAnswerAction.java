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

public class BoardAnswerAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String parentNo = request.getParameter("parentNo");
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		String page = request.getParameter("page");
		Long userNo;
		
		HttpSession session = request.getSession();
		UserVo sessionUserVo = (UserVo) session.getAttribute("authUser");
		if (sessionUserVo == null) {
			MvcUtil.redirect(request.getContextPath() + "/user?a=loginform", request, response);
			return;
		}
		
		userNo = sessionUserVo.getNo();
		
		BoardVo parentBoard = new BoardDao().findWrite(Long.parseLong(parentNo));
		
		new BoardDao().updateAnswer(parentBoard.getGroupNo(), parentBoard.getOrderNo() + 1);
		
		new BoardDao().insertAnswer(parentBoard, title, contents, userNo);
		
		MvcUtil.redirect(request.getContextPath() + "/board?a=list&page=" + page, request, response);
	}

}
