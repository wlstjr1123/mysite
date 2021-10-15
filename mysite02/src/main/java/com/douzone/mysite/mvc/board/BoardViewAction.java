package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class BoardViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String writeNo = request.getParameter("no");
		String page = request.getParameter("page");
		
		
		Cookie viewsCookie = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("boardViews")) {
				viewsCookie = cookie;
			}
		}
		
		if (viewsCookie != null) {
			if (!viewsCookie.getValue().contains("[" + writeNo +"]")) {
				new BoardDao().updateViews(Long.parseLong(writeNo));
				viewsCookie.setValue(viewsCookie.getValue() + "[" + writeNo +"]");
				viewsCookie.setPath(request.getContextPath() + "/board");
				viewsCookie.setMaxAge(60 * 60 * 24);
				response.addCookie(viewsCookie);
			}
		} else {
			Cookie newCookie = new Cookie("boardViews", "[" + writeNo +"]");
			new BoardDao().updateViews(Long.parseLong(writeNo));
			newCookie.setPath(request.getContextPath() + "/board");
			newCookie.setMaxAge(60 * 60 * 24);
			response.addCookie(newCookie);
		}
		
		BoardVo vo = new BoardDao().findWrite(Long.parseLong(writeNo));
		
		request.setAttribute("vo", vo);
		request.setAttribute("page", page);
		MvcUtil.forward("board/view", request, response);

	}

}
