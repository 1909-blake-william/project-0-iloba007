package com.customerDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.account.Account;

public interface AccountDao {

	AccountDao currentImplementation = new AccountDaoSQL();

	Account extractAccount(ResultSet rs) throws SQLException;

	public int save(Account currentAccount);

	public List<Account> findAll(int i);

	public int deposit(int id, double amount);

	public int withdraw(int id, double amount);

	public double balance(int id);

	List<Account> findAccountByUserId(int userId);
}
