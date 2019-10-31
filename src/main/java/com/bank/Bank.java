package com.bank;

import java.util.ArrayList;

import com.account.Account;
import com.customer.Customer;

public class Bank {
	ArrayList<Customer> customers = new ArrayList<Customer>();

	public void addCustomer(Customer customer) {
		customers.add(customer);

	}

	public Customer getCustomer(int account) {

		return customers.get(account);
	}

	public ArrayList<Customer> getCustomer(){
		return customers;
	}
}
