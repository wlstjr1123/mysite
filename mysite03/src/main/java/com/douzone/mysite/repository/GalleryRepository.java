package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GalleryVo;

@Repository
public class GalleryRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(GalleryVo vo) {
		int count = sqlSession.insert("gallery.insert", vo);
		return count == 1;
	}
	
	public List<GalleryVo> findAll() {
		List<GalleryVo> list = sqlSession.selectList("gallery.findAll");
		return list;
	}
}
