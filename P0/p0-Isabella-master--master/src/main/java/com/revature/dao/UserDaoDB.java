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
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Green=green123");
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String query = "insert into user (id, first_name, last_name,user_name,password,usertype) values (?,?,?,?,?,?)";
		String query2 = "select user_name from user";
		try {
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query2);
				
				

				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, user.getId());
				pstmt.setString(2, user.getFirstName());
				pstmt.setString(3, user.getLastName());
				pstmt.setString(4, user.getUsername());
				pstmt.setString(5, user.getPassword());
				pstmt.setString(6, user.getUserType().name());
				
						
				
				pstmt.executeUpdate();
				System.out.println("New user added");}
				
			
				 
				
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		
		return user;
		}
	

	public User getUser(Integer userId) {
		// TODO Auto-generated method stub
		User u = new User();
		
		try {conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Green=green123");
		
		stmt = conn.createStatement();
		
	
			rs = stmt.executeQuery("select * from user WHERE id ="+userId);
			
			if ( rs.next()) {
			
			u.setId(rs.getInt("id"));
			u.setFirstName(rs.getString("first_name"));
			u.setLastName(rs.getString("last_name"));
			u.setUsername(rs.getString("user_name"));
			u.setPassword(rs.getString("password"));
			
			String gotT =rs.getString("usertype");
			UserType enumVal = UserType.valueOf(gotT);
			u.setUserType(enumVal);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

		
		return u;
	}

	public User getUser(String username, String pass) {
		// TODO Auto-generated method stub
		User u = new User();
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Green=green123");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM User WHERE user_name='" + username + "' AND password='" + pass + "'");
			
			if ( rs.next()) {
				
					 u.setId(rs.getInt("id"));
						u.setFirstName(rs.getString("first_name"));
						u.setLastName(rs.getString("last_name"));
						u.setUsername(rs.getString("user_name"));
						u.setPassword(rs.getString("password"));
						String gotT =rs.getString("usertype");
						UserType enumVal = UserType.valueOf(gotT);
						u.setUserType(enumVal);}
						else u = null;	 
						
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
				
			
	
	return u;

	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		
		
		List<User> users = new ArrayList<>();
		 String testin = "select * from user";
		try 
			{
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Green=green123");
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(testin);
			
			
			while (rs.next())
				 {
					User nxU = new User();
					nxU.setId(rs.getInt("id"));
					nxU.setFirstName(rs.getString("first_name"));
					nxU.setLastName(rs.getString("last_name"));
					nxU.setUsername(rs.getString("user_name"));
					nxU.setPassword(rs.getString("password"));
					String gotT =rs.getString("usertype");
					if (gotT!= null) {
					UserType enumVal = UserType.valueOf(gotT);
					nxU.setUserType(enumVal);
					}
					
					users.add(nxU);
				
			}
			}
					 catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				
			return users;
		
		
	
	}

	public User updateUser(User u) {
		
		return null;
	}

	public boolean removeUser(User u) {
		List<User> del = getAllUsers();
		int fnId = u.getId();
		User ted = getUser(fnId);
		del.remove(fnId);
		if(ted == u) {
			
			System.out.println("User deleted");
			return true;
		}
		System.out.println("Failed attempt. Ensure all infprmation is correct.");
		return false;
	}

}

	
		
	