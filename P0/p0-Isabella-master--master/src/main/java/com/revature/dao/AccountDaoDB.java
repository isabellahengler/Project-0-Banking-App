package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Account;
import com.revature.beans.Account.AccountType;
import com.revature.beans.Transaction;
import com.revature.beans.User;
import com.revature.utils.ConnectionUtil;

/**
 * Implementation of AccountDAO which reads/writes to a database
 */
public class AccountDaoDB implements AccountDao {

	private static Connection conn;
	private static Statement stmt;
	private static PreparedStatement pstmt;
	private static ResultSet rs;

	public Account addAccount(Account a) {
		// TODO Auto-generated method stub
	
		System.out.println(a);
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Green=green123");

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		String query = "insert into accounts (accountID, ownerID, balance,type,approved) values (?,?,?,?,?)";
		try {
			stmt = conn.createStatement();

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, a.getId());
			pstmt.setInt(2, a.getOwnerId());
			pstmt.setDouble(3, a.getBalance());
			AccountType enumVal = a.getType();
			pstmt.setString(4, (enumVal.name()));
			pstmt.setBoolean(5, a.isApproved());

			pstmt.executeUpdate();
			System.out.println("New Account added");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
	}

	public Account getAccount(Integer actId) {
		// TODO Auto-generated method stub
		Account acc = new Account();
		String testin = "select * from accounts";

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Green=green123");

			stmt = conn.createStatement();
			rs = stmt.executeQuery(testin);

			while (rs.next()) {
				Account nxS = new Account();
				nxS.setId(rs.getInt("accountID"));
				nxS.setOwnerId(rs.getInt("ownerID"));
				nxS.setBalance(rs.getDouble("balance"));
				nxS.setApproved(rs.getBoolean("approved"));
				String gotAt = rs.getString("type");
				AccountType enumVal = AccountType.valueOf(gotAt);
				nxS.setType(enumVal);

				if (nxS.getId() == actId) {

					acc = nxS;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return acc;

	}

	public List<Account> getAccounts() {
		// TODO Auto-generated method stub
		List<Account> acnts = new ArrayList<>();
		String testin = "select * from accounts";

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Green=green123");

			stmt = conn.createStatement();
			rs = stmt.executeQuery(testin);

			while (rs.next()) {
				Account nxS = new Account();
				nxS.setId(rs.getInt("acc_Id"));
				nxS.setOwnerId(rs.getInt("own_Id"));
				nxS.setBalance(rs.getDouble("balance"));
				nxS.setApproved(rs.getBoolean("approved"));
				String gotAt = rs.getString("acc_type");
				AccountType enumVal = AccountType.valueOf(gotAt);
				nxS.setType(enumVal);

				acnts.add(nxS);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return acnts;

	}

	public List<Account> getAccountsByUser(User u) {

		// TODO Auto-generated method stub
		List<Account> accs = new ArrayList<>();
		int idGot = u.getId();
		String testin = "select * from accounts";

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Green=green123");

			stmt = conn.createStatement();
			rs = stmt.executeQuery(testin);

			while (rs.next()) {
				Account nxS = new Account();
				nxS.setId(rs.getInt("accountID"));
				nxS.setOwnerId(rs.getInt("ownerID"));
				nxS.setBalance(rs.getDouble("balance"));
				nxS.setApproved(rs.getBoolean("approved"));
				String gotAt = rs.getString("type");
				AccountType enumVal = AccountType.valueOf(gotAt);
				nxS.setType(enumVal);

				if (nxS.getOwnerId() == u.getId()) {

					accs.add(nxS);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return accs;

	}

	public Account updateAccount(Account a) {
		// TODO Auto-generated method stub
		//System.out.println("made it to update account");
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Green=green123");
		
			
			String query = "Update accounts set ownerID=?, balance=?, approved=?, type=? where accountID=" + a.getId();
		
			stmt = conn.createStatement();
			
			
	            pstmt = conn.prepareStatement(query);
	            pstmt.setInt(1, a.getOwnerId());
	            pstmt.setDouble(2, a.getBalance());
	            pstmt.setBoolean(3, a.isApproved());
	            pstmt.setString(4, a.getType().name());
	            int result = pstmt.executeUpdate();
	            
	            if (result == 0);
	                return null;
	        
	        
	     
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace(); }
		
		

		
		return a;
	}

	public boolean removeAccount(Account a) {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Green=green123");
		
			
		      String query = "DELETE FROM accounts WHERE accounID=" + a.getId();
		        boolean status = false;

		        try {

		            stmt = conn.createStatement();
		            if (stmt.executeUpdate(query) != 0) {
		                status = true;
		            }

		        } catch (SQLException e) {

		            e.printStackTrace();

		        }
		        return status;
		    
	        
	        
	     
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace(); }
		
		

		
		return false;
		}
			
			
			
}



			