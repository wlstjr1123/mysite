package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.UserRepositoryException;
import com.douzone.mysite.vo.UserVo;

@Repository
public class UserRepository {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SqlSession sqlSession;
	
	public UserVo findByEmailAndPassword(String email, String password) {
		Map<String, String> map = new HashMap<>();
		map.put("e", email);
		map.put("p", password);
		
		UserVo vo = sqlSession.selectOne("user.findByEmailAndPassword", map);
		
		return vo;
	}
	
	public UserVo findByNo(Long no) throws UserRepositoryException{
		return sqlSession.selectOne("user.findByNo", no);
	}
	
	public boolean update(UserVo vo) {
		int count = sqlSession.update("user.update", vo);
		
		return count == 1;
	}
	
	public boolean insert(UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);
		
		return count == 1;
	}

}
