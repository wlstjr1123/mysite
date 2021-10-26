package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.AdminService;

@Auth(role = "ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;

	@RequestMapping("")
	public String main(Model model) {
		model.addAttribute("siteVo", adminService.getAdminContent());
		return "admin/main";
	}
	
	@RequestMapping("/main/update")
	public String mainUpdate(
			@RequestParam("file1") MultipartFile file, 
			@RequestParam(value="title", required=true, defaultValue="") String title,
			@RequestParam(value="welcomeMessage", required=true, defaultValue="") String welcomMessage,
			@RequestParam(value="description", required=true, defaultValue="") String description,
			@RequestParam(value="no", required=true, defaultValue="1") Long no) {
		
		adminService.setContentModify(file, no, title, welcomMessage, description);
		
		return "redirect:/main";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}

	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
