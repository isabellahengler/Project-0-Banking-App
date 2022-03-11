package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import com.revature.beans.User;
import com.revature.beans.User.UserType;
import com.revature.services.UserService;

/**
 * Implementation of UserDAO that reads/writes to a relational database
 */
public class UserDaoDB implements UserDao {
	
	private static Connection conn; 
	private static Statement stmt;
	private static PreparedStatement pstmt; 
	private static ResultSet rs; 

	
		
	public User addUser(User user) {
		// TODO Auto-generated method stub
		getConnection();
		String query = "insert into user () values (?,?,?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, user.getId()); 
			pstmt.setString(2, user.getFirstName());
			pstmt.setString(3, user.getLastName()); 
			pstmt.setString(4, user.getUsername()); 
			pstmt.setString(5, user.getPassword());
			System.out.println("usertype=" + user.getUserType());
			pstmt.setString(6, user.getUserType().toString());
			
			
			pstmt.executeUpdate(); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	System.out.println ("You have been successfully added to the system.");	
				
		return user;
	}

	public User getUser(Integer userId) {
		// TODO Auto-generated method stub
		
		getConnection(); 
		
		String query = "Select * from user where id = " + userId; 
		
		User user = new User (); 
		
		try {
			
		stmt = conn.createStatement(); 
		rs = stmt.executeQuery(query); 
		
		if (rs.next()) {
			
	
			user.setId(userId);
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			user.setUsername(rs.getString("user_name"));
			user.setPassword(rs.getString("password"));
			String type = rs.getString("usertype");
            UserType enumVal = UserType.valueOf(type);
            user.setUserType(enumVal);

			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return user; 
	}
	public User getUser(String username, String pass) {
		// TODO Auto-generated method stub

		
		getConnection(); 
		
		String query = "SELECT * FROM user WHERE user_name='" + username + "' AND password ='" + pass + "'";		
		
		//User user = null; 
		User user = new User (); 
		
		try {
		
		//pstmt = conn.prepareStatement(query); 
		//pstmt.setString(1, username);
		//pstmt.setString(2, pass);
			stmt = conn.createStatement();
		rs = stmt.executeQuery(query); 
		
		while (rs.next()) { 
			
			// User user = new user 
			
			user.setId(rs.getInt("id"));
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			user.setUsername(rs.getString("user_name"));
			String type = rs.getString("usertype"); 
			UserType enumval = UserType.valueOf(type); 
			user.setUserType(enumval);
				
		}
		
	} catch (SQLException e) { 
		
		e.printStackTrace(); 
	}
		
		return user; 
		
	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub 
		getConnection();  
	
		String query = "Select * from user"; 

		List<User> userList = new ArrayList<User>();

		try {

			stmt = conn.createStatement();

			rs = stmt.executeQuery(query);

			while (rs.next()) {

				User user = new User();

				user.setId(rs.getInt("id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setPassword(rs.getString("password"));
				user.setUsername(rs.getString("user_name"));
				userList.add(user);

			}

		} catch (Exception e) {

			e.printStackTrace();
	}
		return userList; 
	}

	public User updateUser(User u) {
		// TODO Auto-generated method stub
		
		getConnection(); 
		
		String query ="Update user set first_name = ?, last_name = ?, password = ?, user_name = ? where id = ?";
		
		
		
		try { 
			
		pstmt = conn.prepareStatement(query); 
		
			pstmt.setString(1, u.getFirstName());
			pstmt.setString(2, u.getLastName());
			pstmt.setString(3, u.getPassword());
			pstmt.setString(4, u.getUsername());
			//String s = rs.getString("usertype"); 
			//UserType enumval = UserType.valueOf(s); 
			//pstmt.setString(5, u.getUserType().name());
			pstmt.setInt(5, u.getId());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return u; 
	}

	public boolean removeUser(User u) {
		// TODO Auto-generated method stub
		
		getConnection();

		String query = "DELETE from user where id = " + u.getId();

		boolean status = false;

		try {

			stmt = conn.createStatement();

			status = stmt.execute(query);

		} catch (SQLException e) {

			e.printStackTrace();

		}
		return status;
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
