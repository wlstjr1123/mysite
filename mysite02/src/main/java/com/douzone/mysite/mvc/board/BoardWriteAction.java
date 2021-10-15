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

public class BoardWriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserVo sessionVo = (UserVo) session.getAttribute("authUser");
		if (sessionVo == null) {
			MvcUtil.redirect(request.getContextPath() + "/user?a=loginform", request, response);
			return;
		}
		
		String writeNo = request.getParameter("writeNo");
		String page = request.getParameter("page");
		if (writeNo != null) {
			BoardVo vo = new BoardDao().findWrite(Long.parseLong(writeNo));
			request.setAttribute("parentVo", vo);
		}
		
		if (page != null) {
			request.setAttribute("page", page);
		}
		
		MvcUtil.forward("board/write", request, response);

	}

}
