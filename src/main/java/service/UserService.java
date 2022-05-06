package service;

import dao.UserDao;

import java.util.List;

import models.User;

public class UserService {
	
	private UserDao dao;
	
	public UserService(UserDao dao) {
		this.dao = dao;
	}

	public User validateLogin(String username, String password)
	{
		User ret = dao.selectUser(username);
		
		if(ret != null && ret.getPassword().equals(password))
			return ret;
		else
			return null;
	}
	
	public List<User> getAllFinanceMangers(){
		return dao.selectAllFinanceManagers();
	}
}
