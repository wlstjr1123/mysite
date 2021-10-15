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
			MvcUtil.redirect(request.getContextPath() + "/board?a=list", request, response);
			return;
		}
		
		String writeNo = request.getParameter("writeNo");
		if (writeNo != null) {
			BoardVo vo = new BoardDao().findWrite(Long.parseLong(writeNo));
			request.setAttribute("parentVo", vo);
		}
		
		MvcUtil.forward("board/write", request, response);

	}

}
