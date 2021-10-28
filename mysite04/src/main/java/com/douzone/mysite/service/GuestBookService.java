package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestBookRepository;
import com.douzone.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {
	@Autowired
	GuestBookRepository guestbookRepository;
	
	public List<GuestBookVo> getMessageList() {
			return guestbookRepository.findAll();
	}
	
	public boolean deleteMessage(Long no, String password) {
		GuestBookVo vo = new GuestBookVo();
		vo.setNo(no);
		vo.setPassword(password);
		
		return guestbookRepository.delete(vo);
	}

	public boolean addMessage(GuestBookVo vo) {
		return guestbookRepository.insert(vo);
	}
}
