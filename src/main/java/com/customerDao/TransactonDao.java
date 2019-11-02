package com.customerDao;

import java.util.List;

import com.transaction.Transaction;

public interface TransactonDao {
	TransactonDao currentImpl = new TransactionDaoSQL();
	/**
	 * For saving the transaction to the database.
	 */
	boolean save(double amount, int accountId, int userId);
	
	/**
	 * for getting all transactions based on user ID.
	 * @param accountId
	 * @param userId
	 * @return
	 */
	List<Transaction> findByIds(int accountId, int userId);
	
	/**
	 * Return all acounts for an admin.
	 * @return
	 */
	List<Transaction> findAll();
}
