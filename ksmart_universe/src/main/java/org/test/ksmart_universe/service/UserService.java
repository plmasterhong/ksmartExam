package org.test.ksmart_universe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.ksmart_universe.mapper.UserMapper;
import org.test.ksmart_universe.vo.User;

@Service
@Transactional
public class UserService {
	@Autowired private UserMapper userMapper;
	
	public List<User> selectList(){
		return userMapper.selectList();
	}
	
	public List<User> selectList(String sk, String sv){
		return userMapper.selectList(sk, sv);
	}
	
	public int addUser(User user) {
		
		return userMapper.addUser(user);
	}
	
	public User getUserById(String memberId) {
		return userMapper.getUserById(memberId);
	}
	
	public User getUserById(HttpSession session,String memberId, String memberPw) {
		User user = userMapper.getUserById(memberId, memberPw);
		if(user != null) {
			session.setAttribute("SID"		, user.getMemberId());
			session.setAttribute("SNAME"	, user.getMemberName());
			session.setAttribute("SLEVEL"	, user.getMemberLevel());
		}
		return user;
	}
	
	public int modifyUser(User user) {
		return userMapper.modifyUser(user);
	}
	
	public String deleteUser(String memberId, String memberPw){
		String alert="";
		
		User user = userMapper.getUserById(memberId,memberPw);
		
		if(user != null) {
			userMapper.deleteUser(memberId);
			alert = "삭제성공";
		}else {
			alert="비밀번호 일치하지 않습니다.";
		}
				
		return alert;
	}
}
