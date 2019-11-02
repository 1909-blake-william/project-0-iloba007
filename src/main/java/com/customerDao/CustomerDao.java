package com.customerDao;

import java.util.List;

import com.customer.Customer;

public interface CustomerDao {
	CustomerDao currentImplementation = new CustomerDaoSQL();

	int save(String firstName, String lastName, String username, String password, int ssn);

	List<Customer> findAll();

	Customer findById();

	Customer findByUsernameAndPassword(String username, String password);

	Customer findByUsername(String username);
}
