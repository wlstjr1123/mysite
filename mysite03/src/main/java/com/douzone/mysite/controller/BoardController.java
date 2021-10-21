package com.douzone.mysite.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/list/{page}")
	public String list(@PathVariable("page") String page, Model model) {
		
		if (page == null || page.equals("")) {
			page = "0";
		}
		
		List<BoardVo> list = boardService.findPage(Integer.parseInt(page));
		Long count = boardService.dataCount();
		
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
		
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("page", Integer.parseInt(page));
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "board/list";
	}
	
	@RequestMapping(value="/write/{no}/{page}", method=RequestMethod.GET)
	public String getWrite(
			@PathVariable(value="no", required = false) String writeNo, 
			@PathVariable(value="page", required = false) String page, 
			HttpSession session, 
			Model model) {
		UserVo sessionVo = (UserVo) session.getAttribute("authUser");
		if (sessionVo == null) {
			return "redirect:/user/login";
		}
		
		if (writeNo != null) {
			BoardVo vo = boardService.findWrite(Long.parseLong(writeNo));
			model.addAttribute("parentVo", vo);
		}
		
		if (page != null) {
			model.addAttribute("page", page);
		}
		
		return "board/write";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String getWrite(HttpSession session, Model model) {
		UserVo sessionVo = (UserVo) session.getAttribute("authUser");
		if (sessionVo == null) {
			return "redirect:/user/login";
		}
		
		return "board/write";
	}
	
	@RequestMapping("/register")
	public String register(String title, String content, HttpSession session) {
		Long userNo;
		
		UserVo sessionUserVo = (UserVo) session.getAttribute("authUser");
		if (sessionUserVo == null || title == null || title.equals("") || content == null || content.equals("")) {
			return "redirect:/user/login";
		}
		
		userNo = sessionUserVo.getNo();
		
		BoardVo boardVo = new BoardVo();
		boardVo.setTitle(title);
		boardVo.setContents(content);
		boardVo.setUserNo(userNo);
		
		boardService.insert(boardVo);
		
		return "redirect:/board/list/0";
	}
	
	@RequestMapping("/view/{no}/{page}")
	public String view(@PathVariable("no") String no, 
			@PathVariable("page") String page, 
			@CookieValue(value = "boardViews", required = false) Cookie cookie, 
			HttpServletResponse response, 
			Model model) {
		String writeNo = no;
		
		
		Cookie viewsCookie = cookie;
		
		if (viewsCookie != null) {
			if (!viewsCookie.getValue().contains("[" + writeNo +"]")) {
				boardService.updateViews(Long.parseLong(writeNo));
				viewsCookie.setValue(viewsCookie.getValue() + "[" + writeNo +"]");
				viewsCookie.setPath("/");
				viewsCookie.setMaxAge(60 * 60 * 24);
				response.addCookie(viewsCookie);
			}
		} else {
			Cookie newCookie = new Cookie("boardViews", "[" + writeNo +"]");
			boardService.updateViews(Long.parseLong(writeNo));
			newCookie.setPath("/");
			newCookie.setMaxAge(60 * 60 * 24);
			response.addCookie(newCookie);
		}
		
		BoardVo vo = boardService.findWrite(Long.parseLong(writeNo));
		
		model.addAttribute("vo", vo);
		model.addAttribute(page);
		
		return "board/view";
	}
	
	@RequestMapping("/modify/{no}/{page}")
	public String modify(@PathVariable("no") String writeNo, 
			@PathVariable("page") String page, HttpSession session, Model model) {
		
		if (page == null || page.equals("")) {
			page = "0";
		}
		
		UserVo sessionVo = (UserVo) session.getAttribute("authUser");
		if (sessionVo == null) {
			return "redirect:/user/login";
		}
		
		BoardVo vo = boardService.findWrite(Long.parseLong(writeNo));
		
		model.addAttribute("vo", vo);
		model.addAttribute("page", page);
		
		return "board/modify";
	}
	
	@RequestMapping("/modifyReg/{page}")
	public String modifyReg(@PathVariable("page") String page,
			String writeNo,
			String title,
			String content,
			HttpSession session) {
		
		if (page == null || page.equals("")) {
			page = "0";
		}
		
		UserVo sessionVo = (UserVo) session.getAttribute("authUser");
		if (sessionVo == null) {

			return "redirect:/user/login";
		}
		
		boardService.updateModify(Long.parseLong(writeNo), title, content);
		
		return "redirect:/board/list/"+ page;
	}
	
}
