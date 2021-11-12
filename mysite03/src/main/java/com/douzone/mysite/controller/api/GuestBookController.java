package com.douzone.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.GuestBookService;
import com.douzone.mysite.vo.GuestBookVo;

@RestController("GuestBookApiController")
@RequestMapping("/guestbook/api")
public class GuestBookController {

	@Autowired
	private GuestBookService guestBookService;
	
	@GetMapping("/list")
	public JsonResult getItem(@RequestParam(value = "sn", required = false) Long no) {
		GuestBookVo vo = null;
		
		if (no == null || no.equals("")) {
			vo = guestBookService.getMessage(1L);
		} else {
			vo = guestBookService.getMessage(no);
		}
		
		return JsonResult.success(vo);
	}
	
	@PostMapping("/add")
	public JsonResult addItem(GuestBookVo vo) {
		return JsonResult.success(guestBookService.addMessage(vo));
	}
}
