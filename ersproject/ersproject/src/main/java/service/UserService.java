package service;

import dao.UserDao;

import java.util.List;

import models.User;

public class UserService {
	
	private UserDao dao;
	
	public UserService(UserDao dao) {
		this.dao = dao;
	}
	
	/**
	 * @param username
	 * @param password
	 * @return
	 * A User Object if the username and password match in the database.
	 * NULL if they do not
	 */
	public User validateLogin(String username, String password)
	{
		User ret = dao.selectUser(username);
		
		if(ret != null && ret.getPassword().equals(password))
			return ret;
		else
			return null;
	}
	
	/**
	 * @return
	 * A List of User objects with their first name, last name, and user ID
	 */
	public List<User> getAllFinanceMangers(){
		return dao.selectAllFinanceManagers();
	}
}
