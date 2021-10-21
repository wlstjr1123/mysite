package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	public List<BoardVo> findPage(int page) {
		List<BoardVo> list = boardRepository.findPage(page);
		return list;
	}
	
	public Long dataCount() {
		return boardRepository.dataCount();
	}
	
	public BoardVo findWrite(Long writeNum) {
		return boardRepository.findWrite(writeNum);
	}
	
	public void insert(BoardVo vo) {
		boardRepository.insert(vo);
	}
	
	public void updateViews(Long no) {
		boardRepository.updateViews(no);
	}
	
	public void updateModify(Long no, String title, String content) {
		boardRepository.updateModify(no, title, content);
	}
}
