package com.example.demo3.bysj.service;




import com.example.demo3.bysj.dao.UserDao;
import com.example.demo3.bysj.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Collection;

public final class UserService {
	@Autowired
	UserDao userDao;
	//private UserDao userDao = UserDao.getInstance();
	private static UserService userService = new UserService();
	
	public UserService() {
	}
	
	public static UserService getInstance(){
		return UserService.userService;
	}

	public Collection<User> getUsers(){
		return userDao.findAll();
	}
	
	public User getUser(Integer id) throws SQLException {
		return userDao.find(id);
	}

	public User findByUsername(String username) throws SQLException{
	    return userDao.findByUsername(username);
    }
	
	public User login(String username, String password) throws SQLException{
		return userDao.login(username, password);
	}
	public boolean changePassword(User user) throws SQLException{
		return userDao.changePassword(user);
	}
}
