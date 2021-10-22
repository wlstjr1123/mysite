package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.repository.GalleryRepository;
import com.douzone.mysite.service.GalleryService;
import com.douzone.mysite.vo.GalleryVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	@Autowired
	private GalleryService galleryService;
	
	@RequestMapping("")
	public String index(Model model) {
		List<GalleryVo> list = galleryService.findAll();
		model.addAttribute("list", list);
		
		return "gallery/index";
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String upload(
			@RequestParam(value="comments", required = true, defaultValue = "") String comments,
			@RequestParam(value="file") MultipartFile multipartFile) {
		
		String url = galleryService.restore(multipartFile);
		if (url != null) {
			GalleryVo vo = new GalleryVo();
			vo.setUrl(url);
			vo.setComments(comments);
			galleryService.insert(vo);
		}
		
		return "redirect:/gallery";
	}
}
