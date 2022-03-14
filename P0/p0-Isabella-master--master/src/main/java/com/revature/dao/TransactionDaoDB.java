package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Account;
import com.revature.beans.Transaction;
import com.revature.beans.User;
import com.revature.beans.Transaction.TransactionType;
import com.revature.services.AccountService;
import com.revature.services.UserService;
import com.revature.utils.ConnectionUtil;

public class TransactionDaoDB implements TransactionDao {
	private static Connection conn;
	private static Statement stmt;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	static User user = new User();
	static UserDaoDB udb = new UserDaoDB();
	static AccountDaoDB adb= new AccountDaoDB();
	static UserService use = new UserService(udb, adb);
	static AccountService acser = new AccountService(adb);
	static TransactionDaoDB tdb = new TransactionDaoDB();

	public List<Transaction> getAllTransactions() {
		// TODO Auto-generated method stub
		List<Transaction> l = new ArrayList<Transaction>();
		Transaction t = new Transaction();
		String testin = "select * from transaction";

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "Green=green123");

			stmt = conn.createStatement();
			rs = stmt.executeQuery(testin);
			Account inU = new Account();
			Account outU = new Account();

			while (rs.next()) {
				inU = adb.getAccount(rs.getInt("toAccountID"));
				outU= adb.getAccount(rs.getInt("fromAccountID"));
				t.setAmount(rs.getDouble("amount"));
				t.setSender(outU);
				t.setRecipient(inU);
				String gotAt = rs.getString("type");
				TransactionType enumVal = TransactionType.valueOf(gotAt);
				t.setType(enumVal);
				l.add(t);
			}
				//for(Transaction y: l)
				System.out.println(l);
				
				

				
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}return null;
	
	}}