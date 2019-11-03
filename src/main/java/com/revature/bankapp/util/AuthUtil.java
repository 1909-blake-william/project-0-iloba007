package com.revature.bankapp.util;

import com.account.Account;
import com.customer.Customer;

public class AuthUtil {
	public static final AuthUtil instance = new AuthUtil();

//	private UserDao userDao = UserDao.currentImplementation;
	private Customer currentCustomer = null;
	DatabaseDao dbDao = new DatabaseDao();

	private AuthUtil() {
		super();
	}

	public Customer login(String username, String password) {
		Customer u = dbDao.checkUserNameAndPassword(username, password);
		currentCustomer = u;
		return u;
	}

	public Customer getCurrentUser(Account account) {
		return currentCustomer;
	}

	/**
	 * Returns the current Customer Object
	 * @return Customer
	 */
	public Customer getCurrentCustomer() {
		return currentCustomer;
	}
}
