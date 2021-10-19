package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.GuestBookService;
import com.douzone.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	@Autowired
	private GuestBookService guestBookService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<GuestBookVo> list = guestBookService.findAllList();
		
		model.addAttribute("list", list);
		return "guestbook/list";
	}
	
	@RequestMapping("/add")
	public String add(GuestBookVo vo) {
		guestBookService.add(vo);
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/delete";
	}
	
	@RequestMapping(value="delete", method=RequestMethod.POST)
	public String deletePost(GuestBookVo vo) {
		guestBookService.delete(vo);
		return "redirect:/guestbook/list";
	}
}
