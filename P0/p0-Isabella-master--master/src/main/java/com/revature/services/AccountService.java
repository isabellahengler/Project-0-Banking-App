package com.revature.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.revature.beans.Account;
import com.revature.beans.Account.AccountType;
import com.revature.beans.Transaction;
import com.revature.beans.User;
import com.revature.beans.User.UserType;
import com.revature.beans.Transaction.TransactionType;
import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoDB;
import com.revature.exceptions.OverdraftException;
import com.revature.exceptions.UnauthorizedException;
import com.revature.utils.SessionCache;

/**
 * This class should contain the business logic for performing operations on Accounts
 */
public class AccountService {
	
	public AccountDao actDao;
	public static final double STARTING_BALANCE = 25d;
	static Connection conn;
	
	public AccountService(AccountDao dao) {
		this.actDao = dao;
	}
	
	/**
	 * Withdraws funds from the specified account
	 * @throws OverdraftException if amount is greater than the account balance
	 * @throws UnsupportedOperationException if amount is negative
	 */
	public void withdraw(Account a, Double amount) {
		double newBalance = a.getBalance() - amount;
		if (newBalance < 0 || !a.isApproved()) {
			throw new OverdraftException();
		} else if(amount < 0) {
			throw new UnsupportedOperationException();
		} else {
			a.setBalance(newBalance);
			List<Transaction> transList;
			if (a.getTransactions() == null) {
				transList = new ArrayList<>();
			} else {
				transList = a.getTransactions();
			}
			Transaction trans = new Transaction();
			trans.setAmount(amount);
			trans.setType(TransactionType.WITHDRAWAL);
			transList.add(trans);
			a.setTransactions(transList);
			actDao.updateAccount(a);
		}
	}
	
	/**
	 * Deposit funds to an account
	 * @throws UnsupportedOperationException if amount is negative
	 */
	public void deposit(Account a, Double amount) {
		double newBalance = a.getBalance() + amount;
		if (amount < 0 || !a.isApproved()) {
			throw new UnsupportedOperationException();
		} else {
			a.setBalance(newBalance);
			List<Transaction> transList;
			if (a.getTransactions() == null) {
				transList = new ArrayList<>();
			} else {
				transList = a.getTransactions();
			}
			Transaction trans = new Transaction();
			trans.setAmount(amount);
			trans.setType(TransactionType.DEPOSIT);
			transList.add(trans);
			a.setTransactions(transList);
			actDao.updateAccount(a);
		}
	}
	
	/**
	 * Transfers funds between accounts
	 * @throws UnsupportedOperationException if amount is negative or 
	 * the transaction would result in a negative balance for either account
	 * or if either account is not approved
	 * @param fromAct the account to withdraw from
	 * @param toAct the account to deposit to
	 * @param amount the monetary value to transfer
	 */
	public void transfer(Account fromAct, Account toAct, double amount) {
		double fromBal = fromAct.getBalance() - amount;
		double toBal = toAct.getBalance() + amount;
		if (amount < 0 || (fromBal < 0)) {
			throw new UnsupportedOperationException();
		} else {
			fromAct.setBalance(fromBal);
			toAct.setBalance(toBal);
			Transaction trans = new Transaction();
			Transaction trans2 = new Transaction();
			trans.setSender(fromAct);
			trans.setRecipient(toAct);
			trans.setAmount(amount);
			trans.setType(TransactionType.TRANSFER);
			trans2.setSender(fromAct);
			trans2.setRecipient(toAct);
			trans2.setAmount(amount);
			trans2.setType(TransactionType.TRANSFER);
			List<Transaction> transListFrom;
			List<Transaction> transListTo;
			if (fromAct.getTransactions() == null) {
				transListFrom = new ArrayList<>();
			} else {
				transListFrom = fromAct.getTransactions();
			}
			transListFrom.add(trans);
			if (toAct.getTransactions() == null) {
				transListTo = new ArrayList<>();
			} else {
				transListTo = toAct.getTransactions();
			}
			transListTo.add(trans2);
			toAct.setTransactions(transListTo);
			actDao.updateAccount(fromAct);
			actDao.updateAccount(toAct);
		}
	}
	
	/**
	 * Creates a new account for a given User
	 * @return the Account object that was created
	 */
	public Account createNewAccount(User u) {
		getConnection();

        Account a = new Account();
        AccountDaoDB adb = new AccountDaoDB();
        a = new Account();
        User us = new User();
    

        a.setOwnerId(u.getId());

        

       

        System.out.println("Please choose account type CHECKINGS, SAVINGS: ");

        Scanner scan = new Scanner(System.in);
        String accType = scan.next().toLowerCase();

        switch (accType.charAt(0)) {

        case 'c':

            a.setBalance(STARTING_BALANCE);
            a.setApproved(false);
            a.setType(AccountType.CHECKING);
            a.setId(0);

            break;

        case 's':

            a.setBalance(STARTING_BALANCE);
            a.setApproved(false);
            a.setType(AccountType.SAVINGS);
            a.setId(0);

            break;

        }

        adb.addAccount(a);

        return a;

	}
	
	/**
	 * Approve or reject an account.
	 * @param a
	 * @param approval
	 * @throws UnauthorizedException if logged in user is not an Employee
	 * @return true if account is approved, or false if unapproved
	 */
	public boolean approveOrRejectAccount(Account a, boolean approval) {
		Optional<User> u = SessionCache.getCurrentUser();
		if (u.isPresent()) {
			User user = u.get();
			if (user.getUserType() == User.UserType.EMPLOYEE) {
				a.setApproved(approval);
				return a.isApproved();
			} else {
				throw new UnauthorizedException();
			}
			
		}
		return false;
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


