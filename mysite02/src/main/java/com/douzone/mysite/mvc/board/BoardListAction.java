package com.douzone.mysite.mvc.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class BoardListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		
		if (page == null || page.equals("")) {
			page = "0";
		}
		
		List<BoardVo> list = new BoardDao().findPage(Integer.parseInt(page));
		Long count = new BoardDao().dataCount();
		
		Long startPage = 0L, endPage = 0L;
		
		if (Integer.parseInt(page) > 1 && Integer.parseInt(page) < count - 2) {
			startPage = Long.parseLong(page) - 2;
			endPage = Long.parseLong(page) + 2;
		} else {
			if (Integer.parseInt(page) < 2) {
				startPage = 0L;
				endPage = 4L;
			} else if (Integer.parseInt(page) > count-3) {
				startPage = count-5;
				endPage = count-1;
			}
		}
		
		HttpSession session = request.getSession();
		UserVo sessionUserVo = (UserVo) session.getAttribute("authUser");
		
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		request.setAttribute("page", Integer.parseInt(page));
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		MvcUtil.forward("board/list", request, response);

	}

}
