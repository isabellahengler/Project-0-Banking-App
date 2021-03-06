package com.revature.services;

import java.util.List;

import com.revature.beans.User;
import com.revature.dao.AccountDao;
import com.revature.dao.UserDao;
import com.revature.dao.UserDaoDB;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.UsernameAlreadyExistsException;

/**
 * This class should contain the business logic for performing operations on users
 */
public class UserService {
	
	UserDao userDao;
	AccountDao accountDao;
	static UserDaoDB udb = new UserDaoDB();
	
	public UserService(UserDao udao, AccountDao adao) {
		this.userDao = udao;
		this.accountDao = adao;
	}
	
	/**
	 * Validates the username and password, and return the User object for that user
	 * @throws InvalidCredentialsException if either username is not found or password does not match
	 * @return the User who is now logged in
	 */
	public User login(String username, String password) {
		User u = null;
		 u = userDao.getUser(username,password);
		if( u == null){
			throw  new InvalidCredentialsException();
		}
		return u;
	}
	
	/**
	 * Creates the specified User in the persistence layer
	 * @param newUser the User to register
	 * @throws UsernameAlreadyExistsException if the given User's username is taken
	 */
	public void register(User newUser) {
		List<User> users = udb.getAllUsers();
		users.forEach(user ->{
			
			if (user.getUsername().equals( newUser.getUsername())){
				System.out.println("This user already Exists");
				throw new UsernameAlreadyExistsException();
			}
			
		});
		userDao.addUser(newUser);
		
	}
}
