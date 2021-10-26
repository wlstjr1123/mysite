package com.douzone.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.AdminVo;

@Repository
public class AdminRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public AdminVo findAll() {
		return sqlSession.selectOne("admin.findAll");
	}
	
	public boolean update(AdminVo vo) {
		int count = sqlSession.update("admin.update", vo);
		return count == 1;
	}
}
