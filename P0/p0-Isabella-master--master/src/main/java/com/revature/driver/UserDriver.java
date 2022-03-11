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

public class UserDriver {
	static Scanner scan = new Scanner(System.in);
	private static Connection conn;
	private static ResultSet rs;

	public static void addUser() {
		User TestDummyUser = new User();

		UserDaoDB udb = new UserDaoDB();
		UserService ussr = new UserService(udb, null);
	
		System.out.println("Enter First Name: ");
		TestDummyUser.setFirstName(scan.next());
		System.out.println("Enter Last Name: ");
		TestDummyUser.setLastName(scan.next());

		System.out.println("Enter Username: ");
		TestDummyUser.setUsername(scan.next());

		System.out.println("Enter Password: ");
		TestDummyUser.setPassword(scan.next());
		TestDummyUser.setId(0);
		//TestDummyUser.setUserType(UserType.CUSTOMER);
		TestDummyUser.setUserType(UserType.CUSTOMER);

		try {
			ussr.register(TestDummyUser);

		} catch (UsernameAlreadyExistsException e)

		{
			System.out.println("Username Unavailable");

			// TODO: handle exception
		}

	}

	public static void getUserId() {

		UserDaoDB udb = new UserDaoDB();

		System.out.println("Please enter User ID: ");

		int userId = scan.nextInt();
		User t = udb.getUser(userId);

		if (t.getId() == null) {

			System.out.println("There is no user associated with this Id.");

		} else {

			System.out.println(udb.getUser(userId));

		}

	}

// console login 

	public static void login() throws SQLException {
		getConnection();

		UserDaoDB udb = new UserDaoDB();
		UserService ussr = new UserService(udb, null);

		User t = new User();

		String username;
		String pass;

		System.out.println("Enter username:");
		username = scan.next();

		System.out.println("Enter password: ");
		pass = scan.next();

		t = udb.getUser(username, pass);
		Statement stmt = conn.createStatement();
		String query = "Select * from user where user_name = '" + username + "' AND password = '" + pass + "'";
		rs = stmt.executeQuery(query);

		if (t.getId() == null) {

			System.out.println("This combination of Username + Password does not exist.");
		} else {

			ussr.login(username, pass);

			System.out.println("Success");

		}
	}

	public static void getAllUsers() {

		UserDaoDB udb = new UserDaoDB();

		udb.getAllUsers();

	}

	public static void updateUsers() {
		// Being taken from
		UserDaoDB udb = new UserDaoDB();
		// Being sent to
		User u = new User();
		int id;

		System.out.println("Enter User ID you wish to update. ");
		id = scan.nextInt();
		u = udb.getUser(id);

		while (u.getId() == null) {

			System.out.println("User ID does not exist.");

			System.out.println("Please enter valid User ID : ");
			id = scan.nextInt();
			u = udb.getUser(id);

		}

		System.out.println(udb.getUser(id));

		System.out.println("Please select what you wish to update: ");
		System.out.println("1. First Name");
		System.out.println("2. Last Name");
		System.out.println("3. Username");
		System.out.println("4. Password");
		System.out.printf("Enter number: ");
		int choice = scan.nextInt();

		switch (choice) {

		case 1:

			System.out.println("Current first name: " + u.getFirstName());
			System.out.println("Enter New First Name:(No spaces) ");
			String newFName = scan.next();

			u.setFirstName(newFName);

			System.out.println("Updated first name: " + u.getFirstName());

			System.out.println(u);
			udb.updateUser(u);

			break;

		case 2:

			System.out.println("Current last name: " + u.getLastName());
			System.out.println("Enter New Last Name :(No spaces) ");
			String newLName = scan.next();

			u.setLastName(newLName);

			System.out.println("Updated last name: " + u.getLastName());

			break;

		case 3:

			System.out.println("Current username: " + u.getUsername());
			System.out.println("Enter New username:");
			String newUname = scan.next();

			u.setUsername(newUname);

			System.out.println("Updated Username: " + u.getUsername());
			break;

		case 4:

			System.out.println("Current password: " + u.getPassword());
			System.out.println("Enter New password: ");
			String newPword = scan.next();

			u.setPassword(newPword);

			System.out.println("Updated Password: " + u.getPassword());

			break;

		}

	}

	public static void deleteUser() {

		UserDaoDB udb = new UserDaoDB();

		User u = new User();

		do {

			System.out.println("Please enter the user Id you want to DELETE: ");
			Integer deleteId = scan.nextInt();
			u = udb.getUser(deleteId);

			if (u.getId() != null) {

				System.out.println("Choosen User: ");
				System.out.println(u.getId());

				System.out.println("Enter CONFIRM to delete and EXIT to leave: ");
				String confirm;
				char confirmed;
				confirm = scan.next().toLowerCase();

				confirmed = confirm.charAt(0);

				switch (confirmed) {

				case ('c'):

					System.out.println("User deleted: ");
					udb.removeUser(u);

					break;

				case ('e'):

					System.out.println("User delete canceled.");

					break;

				default:

					System.out.println("There is no user associated with this Id.");
					break;

				}

			}

		} while (u.getId() == null);

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
