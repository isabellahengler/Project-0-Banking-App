package com.revature.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.activity.InvalidActivityException;

import org.apache.log4j.Logger;

import com.revature.beans.User.UserType;
import com.revature.dao.AccountDaoDB;
import com.revature.dao.TransactionDaoDB;
import com.revature.dao.UserDaoDB;
import com.revature.dao.UserDaoFile;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.services.AccountService;
import com.revature.services.UserService;
import com.revature.utils.SessionCache;
import com.revature.beans.*; 
/**
 * This is the entry point to the application
 */



public class BankApplicationDriver {
	
	static Scanner scan = new Scanner (System.in); 
	static User user = new User (); 
	static UserDaoDB udb = new UserDaoDB();
	static AccountDaoDB adb = new AccountDaoDB();
	private static Connection conn;
    private static ResultSet rs;
    private static Statement stmt;
	private static PreparedStatement ppstmt;
    static UserService use = new UserService(udb, adb);
	static TransactionDaoDB tdb = new TransactionDaoDB();
	static AccountService acs = new AccountService(adb);
	static Logger log = Logger.getLogger(BankApplicationDriver.class);
	static User logged;
    
	public static void main(String[] args)  throws SQLException, ClassNotFoundException {
		
	// menu 1 
		System.out.println(
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n****************************************************\n");
		System.out.println("\t\t Welcome To The Banking System.");
		System.out.println("  Enter 1 to login to an existing account. \n");
		System.out.println("\t Enter 2 to create a new account\n");
		// System.out.println("\tTo show a list of all existing users please press 3
		// \n");
		System.out.println(
				"****************************************************\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		int choice = scan.nextInt();
		int cve = 0;

		switch (choice) {
		
		case 1: {
			// login
			// User tester = new User();

			System.out.println("Enter Credentials Below");
			System.out.println("Enter Username");
			String usvr = scan.next();
			System.out.println(" Enter Password ");
			String pss = scan.next();
			if (udb.getUser(usvr, pss) == null) {
				System.out.println("Invalid credentials added");
			}

			else
				System.out.println(use.login(usvr, pss));
			User logged = use.login(usvr, pss);
			int lgId = logged.getId();

			if (logged.getUserType() == UserType.CUSTOMER) {

				cve = 1;
			} else {
				cve = 2;
			}
			switch (cve) {
			case 1:

				System.out.println("");
				System.out.println("Welcome Back");
				System.out.println("Please make a choice from the following menu");
				System.out.println("1.Apply for a new account");
				System.out.println("2.View balance");
				System.out.println("3.Make a deposit");
				System.out.println("4.Make a withdrawl");
				System.out.println("5.Make a transfer");
				int ucho = scan.nextInt();
//user choice 
				switch (ucho) {
				case 1:

					System.out.println("Select the Type of Account you wish to open:");
					System.out.println("1. Checking account");
					System.out.println("2. Savings account");
					int actcho = scan.nextInt();
					boolean typeb = false;
//account type 
					if (actcho == 1) {

						typeb = true;
					}

					acs.createNewAccount(logged, typeb);

					break;

				case 2:
					List<Account> acl = adb.getAccountsByUser(logged);
					System.out.println("Enter account ID you wish to view.");
					for (Account a : acl) {
						System.out.println("Enter: " + a.getId() + " for Account " + a.getType());

					}
					int aic = scan.nextInt();
					Account aB = adb.getAccount(aic);
					System.out.println("Your balance is: $" + aB.getBalance());

					break;
				case 3:
					System.out.println("Please choose a deposit account.");
					List<Account> acd = adb.getAccountsByUser(logged);
					for (Account a : acd) {
						System.out.println("Enter: " + a.getId() + " for Account " + a.getType());}
						int aid = scan.nextInt();
						Account aD = adb.getAccount(aid);
						System.out.println("Enter the deposit amount ");
						double amntd = scan.nextDouble();
						
						try {
							conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Green=green123");
							//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Green=green123");
							String query = "insert into transaction (type, toAccountID,fromAccountID,amount,timestamp) values (?,?,?,?,?)";
								
								
								stmt = conn.createStatement();
								
					            ppstmt = conn.prepareStatement(query);
					            
					            ppstmt.setString(1,"DEPOSIT");
					            
					            ppstmt.setInt(2, aD.getId());
					        
					            ppstmt.setInt(3, aD.getId());
					       
					            ppstmt.setDouble(4, amntd);
					            ppstmt.setInt(5,0);
								ppstmt.executeUpdate();
								acs.deposit(aD,amntd);
								break;
								}
							
						 catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							
					
						}
						

					
				case 4:
					System.out.println("Please choose an account to withdraw from");
					List<Account> acw = adb.getAccountsByUser(logged);
					for (Account a : acw) {
						System.out.println("Enter: " + a.getId() + " for Account " + a.getType());}
						int aiw = scan.nextInt();
						Account aW = adb.getAccount(aiw);
						System.out.println("Please enter the amount to withdraw ");
						double amntw = scan.nextDouble();
						try {
							conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Green=green123");
							String query = "insert into transactions (type, toAccountID,fromAccountID,amount,timestamp) values (?,?,?,?,?)";
								
								
								stmt = conn.createStatement();
								
					            ppstmt = conn.prepareStatement(query);
					            
					            ppstmt.setString(1,"WITHDRAWAL");
					            
					            ppstmt.setInt(2, aW.getId());
					        
					            ppstmt.setInt(3, aW.getId());
					       
					            ppstmt.setDouble(4, amntw);
					            ppstmt.setInt(5,0);
								ppstmt.executeUpdate();
								
								acs.withdraw(aW, amntw);
								break;}
							
						 catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();}
							
					
					
				case 5:
					System.out.println("Please choose an account to withdraw from");
					List<Account> tacw = adb.getAccountsByUser(logged);
					for (Account a : tacw) {
						System.out.println("Enter: " + a.getId() + " for Account " + a.getType());
						}
					
						int taiw = scan.nextInt();
						Account taW = adb.getAccount(taiw);
						
						System.out.println("Please choose an account to deposit into");
						List<Account> tacd = adb.getAccountsByUser(logged);
						for (Account a : tacd) {
							
							System.out.println("Enter: " + a.getId() + " for Account " + a.getType());
							}
						
							int taid = scan.nextInt();
							Account taD = adb.getAccount(taid);
							System.out.println("Please enter the amount to  ");
							double amntt = scan.nextDouble();
					
					try {
						conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Green=green123");
						
						String query = "insert into transactions (type, toAccountID,fromAccountID,amount,timestamp) values (?,?,?,?,?)";
							
							
							stmt = conn.createStatement();
							
				            ppstmt = conn.prepareStatement(query);
				            
				            ppstmt.setString(1,"TRANSFER");
				            
				            ppstmt.setInt(2, taW.getId());
				        
				            ppstmt.setInt(3, taD.getId());
				       
				            ppstmt.setDouble(4, amntt);
				            ppstmt.setInt(5,0);
							ppstmt.executeUpdate();
							
							acs.transfer(taW, taD, amntt);
							break;
							}
						
					 catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();}
					
					
				default:
					break;
				}

				break;

			case 2:
				System.out.println("Employee Sign In");
				System.out.println("");
				System.out.println("Chose the action you would like to take");
				System.out.println("1. View unapproved accounts?");
				System.out.println("2. View transaction history");
				int emc = scan.nextInt();
				switch (emc) {
				case 1:
					System.out.println("Current unapproved accounts:");
					List<Account> allA = new ArrayList();
					List<Account> allUna = new ArrayList();
					allA = adb.getAccounts();
					for (Account a : allA) {
						if (a.isApproved() == false) {
							allUna.add(a);
							System.out.println(a);
						}

					}
					System.out.println("Enter account ID you would like to approve/reject:");

					int ap = scan.nextInt();
					Account apa = adb.getAccount(ap);
					System.out.println("For Account:" + apa + "Chose which action you will take");
					System.out.println("1. Approve");
					System.out.println("2. Reject");
					int dec = scan.nextInt();
					try {
						conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root",
								"Green=green123");
						
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (dec == 1) {
						
						apa.setApproved(true);
						adb.updateAccount(apa);
						System.out.println("Account approved");
						allUna.remove(apa);
						


					} else if (dec == 2) {
						boolean deb = false;
						adb.removeAccount(apa);
						System.out.println(allUna);
						System.out.println("This account has been rejected");
						// acser.approveOrRejectAccount(apa, deb);
					}

				

					System.out.println("");

					break;
				case 2:
					tdb.getAllTransactions();
					break;

				}
				
				break;
			}
		}

			break;

		case 2:
			// adds new user
			User u = new User();

			System.out.println("Enter New Username ");
			String username = scan.next();
			System.out.println("Enter New Password ");
			String password = scan.next();

			System.out.println("Enter first name");
			String first_name = scan.next();

			System.out.println("Enter last name");
			String last_name = scan.next();

			System.out.println("For a customer account select 1");
			System.out.println("");
			System.out.println("For an Employee account select 2");
			int type = scan.nextInt();

			u.setUsername(username);
			u.setPassword(password);
			u.setFirstName(first_name);
			u.setLastName(last_name);
			u.setId(0);

			u.setUserType(UserType.CUSTOMER);

			if (type == 2) {
				u.setUserType(UserType.EMPLOYEE);

			}
			// System.out.println(u);
			use.register(u);
			break;

		default:

			
			break;
		}
	}
}



	
			
	

	

	
	//public static Connection getConnection() {

		//try {

			//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Green=green123");

		//} catch (SQLException e) {
			// TODO: handle exception
//
			//e.printStackTrace();

		//}
		
		//return conn;

	//}

//}






	




	


