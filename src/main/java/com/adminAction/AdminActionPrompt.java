package com.adminAction;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.Login.Prompt;
import com.account.Account;
import com.customerDao.AccountDao;

public class AdminActionPrompt implements Prompt {
	AccountDao accountDao = AccountDao.currentImplementation;
	private Logger log = Logger.getRootLogger();
	Scanner keyboard = new Scanner(System.in);

	@Override
	public Prompt run() {

		System.out.println("Admin actions please choose:");
		System.out.println("'1' to view all accounts");
		String choice = keyboard.nextLine();

		switch (choice) {
		case "1": {
			List<Account> allAccounts = accountDao.findAll();
			for (Account acc : allAccounts) {
				System.out.println(acc);
			}
			break;
		}
		default:
			break;
		}
//		AuthUtil auth = AuthUtil.instance;

		return this;
	}

}
