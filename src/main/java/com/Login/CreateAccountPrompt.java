package com.Login;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.account.Account;
import com.customerDao.AccountDao;
import com.revature.bankapp.util.AuthUtil;

public class CreateAccountPrompt implements Prompt {

	AuthUtil auth = AuthUtil.instance;
	AccountDao accountDao = AccountDao.currentImplementation;
	private Logger log = Logger.getRootLogger();
	Scanner keyboard = new Scanner(System.in);

	@Override
	public Prompt run() {
		String accountType = "";
		double initialDeposit = 0;
		boolean valid = false;
		while (!valid) {
			System.out.print("please enter an account type(checking/saving):");
			accountType = keyboard.nextLine();
			if (accountType.equalsIgnoreCase("checking") || accountType.equalsIgnoreCase("saving")) {
				valid = true;
			} else {
				System.out.println("Invalid account type selected, please enter checking or saving.");
			}

		}
		valid = false;
		while (!valid) {
			System.out.print("please enter initial deposit: ");
			try {
				initialDeposit = Double.parseDouble(keyboard.next());
				keyboard.nextLine();
			} catch (NumberFormatException e) {
				System.out.println("Deposit must be a number:");
			}
			if (accountType.equalsIgnoreCase("checking")) {
				if (initialDeposit < 100) {
					System.out.println("checking account minimum of $100 to open.");
				} else {
					valid = true;
				}
			} else if (accountType.equalsIgnoreCase("saving")) {
				if (initialDeposit < 50) {
					System.out.println("checking account minimum of $50 to open.");
				} else {
					valid = true;
				}
			}
		}
		Account account = new Account(initialDeposit, -1, accountType);
		AuthUtil auth = AuthUtil.instance;
//		if (accountType.equalsIgnoreCase("checking")) {
//			account = new Checking(initialDeposit);
//		} else {
//			account = new Saving(initialDeposit);
//		}
		log.trace("sending new account to dao");
		accountDao.save(account);
		// bank.addCustomer(auth.getCurrentUser(account));

		return null;
	}

}
