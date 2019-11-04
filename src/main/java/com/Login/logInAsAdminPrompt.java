package com.Login;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.adminAction.AdminActionPrompt;
import com.customer.Customer;
import com.revature.bankapp.util.AuthUtil;

public class logInAsAdminPrompt implements Prompt {
	private Logger log = Logger.getRootLogger();
	private Scanner scan = new Scanner(System.in);
	AuthUtil auth = AuthUtil.instance;

	@Override
	public Prompt run() {
		log.debug("attempting to login as Admin");
		System.out.println("Enter username:");
		String username = scan.nextLine();
		System.out.println("Enter password");
		String password = scan.nextLine();

		Customer u = auth.loginAsAdmin(username, password);
		if (u == null) {
			log.info("failed to login due to credentials");
			System.out.println("Invalid Credentials");
			return new MainMenuPrompt();
		} else {
			log.info("successfully logged in");
			return new AdminActionPrompt();
		}

	}

}
