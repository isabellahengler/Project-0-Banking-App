package com.revature.dao;

import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Account;
import com.revature.beans.Transaction;

public class TransactionDaoFile implements TransactionDao {
	
	public static String fileLocation = "";

	public List<Transaction> getAllTransactions() {
		// TODO Auto-generated method stub
		AccountDaoFile adao = new AccountDaoFile();
		fileLocation = AccountDaoFile.fileLocation;
		List<Account> accs = adao.getAccounts();
		List<Transaction> allTrans = new ArrayList<>();
		
		for (Account a : accs) {
			for (Transaction t : a.getTransactions()) {
				allTrans.add(t);
			}
		}
		adao.addAccountsToStream(accs);
		return allTrans;
	}
}