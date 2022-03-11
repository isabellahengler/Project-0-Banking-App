package com.revature.driver;

import com.revature.beans.*;
import com.revature.beans.User.UserType;
import com.revature.dao.*;
import com.revature.exceptions.*;
import com.revature.services.UserService;
import com.revature.utils.SessionCache;
import com.revature.services.*;
import com.revature.driver.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

//user menus 

public class UserMenu {

	static UserDaoDB udb = new UserDaoDB();
	static UserDriver ud = new UserDriver();
	static Scanner scan = new Scanner(System.in);
	private static Connection conn;
	private static ResultSet rs;
	static AccountDaoDB adb = new AccountDaoDB();
	static AccountService acs = new AccountService(adb);
	static User logged; 

	public void menu() throws SQLException {

		Boolean status = true;

		User current = null;

		SessionCache.setCurrentUser(current);

		while (status) {

			if (!SessionCache.getCurrentUser().isPresent()) {
				System.out.println("Welcome"); 
				System.out.println("Enter 1 to Login: ");
				System.out.println("Enter 2 to Register: ");
				int men = scan.nextInt();
				switch (men) {

				case 1:

					ud.login();

					break;

				case 2:

					ud.addUser();

					break;

				}

			}

			else if (SessionCache.getCurrentUser().isPresent()) {

				if (SessionCache.getCurrentUser().get().getUserType() == UserType.CUSTOMER) {
					//signed in user and stores them in logged 
					logged=SessionCache.getCurrentUser().get(); 

					System.out.println("Welcome: " + SessionCache.getCurrentUser().get());

					System.out.println("-Customer Account Menu-");
					System.out.println("1. Register ");
					System.out.println("2. Get UserID");
					System.out.println("3. Add Account  ");
					System.out.println("4. GetAllUsers ");
					System.out.println("5. UpdateUser ");
					System.out.println("6. RemoveUser ");

					int menu_choice;

					menu_choice = scan.nextInt();

					switch (menu_choice) {

					case 1:

						ud.addUser();

						break;
						
					case 2: 
					
					ud.getUserId();
					 break; 

					case 3:
						acs.createNewAccount(logged); 
						break;

					case 4:

						ud.getAllUsers();

						break;

					case 5:

						ud.updateUsers();

						break;

					case 6:

						ud.deleteUser();

						break;

					case 7:

						System.out.println("Thank you for choosing BANK!");
						System.out.println("Have a nice day! ");

						status = false;
						System.exit(0);

						break;

					default:

						System.out.println("Please enter a number between 1-7: ");

						break;

					}
				}

				else if (SessionCache.getCurrentUser().get().getUserType() == UserType.EMPLOYEE) {

					System.out.println("Employee: ");

					break;

				}

			}

		}

	}

//account menus 

	public static void transferTo() {

	}

}
