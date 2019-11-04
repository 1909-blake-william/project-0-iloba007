package com.Login;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.customer.Customer;
import com.customerDao.CustomerDao;
import com.revature.bankapp.util.AuthUtil;

public class LoginPrompt implements Prompt {

	private Logger log = Logger.getRootLogger();
	private Scanner scan = new Scanner(System.in);
	private CustomerDao customerDao = CustomerDao.currentImplementation;
	private AuthUtil authUtil = AuthUtil.instance;

	/**
	 * gather user input for logging in or registering
	 */
	// @Override
	public Prompt run() {
		System.out.println("Enter 1 to login");
		System.out.println("Enter 2 to register");
		System.out.println("Enter 3 to log As Admin");
		String choice = scan.nextLine();
		switch (choice) {
		case "1": {
			log.debug("attempting to login");
			System.out.println("Enter username:");
			String username = scan.nextLine();
			System.out.println("Enter password");
			String password = scan.nextLine();

			Customer u = authUtil.login(username, password);
			if (u == null) {
				log.info("failed to login due to credentials");
				System.out.println("Invalid Credentials");
				break;
			} else {
				log.info("successfully logged in");
				return new MainMenuPrompt();
			}
		}
		case "2": {
			return new RegisterPrompt();
			/*
			 * System.out.println("Enter new username:"); String username = scan.nextLine();
			 * Customer u = customerDao.findByUsername(username); if (u != null) {
			 * System.out.println("invalid username"); break; }
			 * System.out.println("Enter new Password:"); String password = scan.nextLine();
			 * Customer newCustomer = new Customer<Account>(null, null, username, password,
			 * 0, new Account()); customerDao.save(newCustomer); break;
			 */
		}
		case "3": {
			return new logInAsAdminPrompt();

		}
		default:
			System.out.println("invalid option");
			break;
		}
		return this;
	}
}
