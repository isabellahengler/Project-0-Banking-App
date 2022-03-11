package com.revature.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.activity.InvalidActivityException;

import com.revature.beans.User;
import com.revature.beans.User.UserType;
import com.revature.dao.AccountDaoDB;
import com.revature.dao.UserDaoDB;
import com.revature.dao.UserDaoFile;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.services.UserService;
import com.revature.utils.SessionCache;
import com.revature.beans.*; 
/**
 * This is the entry point to the application
 */



public class BankApplicationDriver {
	
	static Scanner scan = new Scanner (System.in); 
	private static Connection conn;
    private static ResultSet rs;
    
	public static void main(String[] args)  throws SQLException, ClassNotFoundException {
		
		UserMenu Welcome = new UserMenu(); 
		 Welcome.menu();
		
			
		}
	
			
	

	

	
	public static Connection getConnection() {

		try {

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Green=green123");

		} catch (SQLException e) {
			// TODO: handle exception

			e.printStackTrace();

		}
		
		return conn;

	}

}






	




	


