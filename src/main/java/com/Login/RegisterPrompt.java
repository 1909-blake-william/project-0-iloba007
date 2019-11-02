package com.Login;

import java.util.Scanner;

import com.account.Account;
import com.customer.Customer;
import com.customerDao.CustomerDao;

public class RegisterPrompt implements Prompt {

	Scanner keyboard = new Scanner(System.in);

	@Override
	public Prompt run() {
		String firstName, lastName, username, password;
		int ssn;
		System.out.print("please enter your first name: ");
		firstName = keyboard.nextLine();
		System.out.print("please enter lastName: ");
		lastName = keyboard.nextLine();
		System.out.print("please enter username ");
		username = keyboard.nextLine();
		System.out.print("please enter password ");
		password = keyboard.nextLine();
		System.out.print("please enter social security number: ");
		ssn = Integer.parseInt(keyboard.nextLine());

		CustomerDao customerDao = CustomerDao.currentImplementation;
		customerDao.save(firstName, lastName, username, password, ssn);
		return new LoginPrompt();
	}

}
