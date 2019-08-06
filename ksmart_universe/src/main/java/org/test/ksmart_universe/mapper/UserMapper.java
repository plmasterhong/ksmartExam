package org.test.ksmart_universe.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.test.ksmart_universe.vo.User;

@Mapper
public interface UserMapper {
	public List<User> selectList();
	
	public List<User> selectList(String sk, String sv);
	
	public int addUser(User user);
	
	public User getUserById(String memberId);
	
	public User getUserById(String memberId, String memberPw);
	
	public int modifyUser(User user);
	
	public int deleteUser(String memberId);
}
