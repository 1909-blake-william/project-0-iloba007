package com.Login;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.account.Account;
import com.bank.Bank;
import com.checking.Checking;
import com.customer.Customer;
import com.customerDao.AccountDao;
import com.customerDao.TransactonDao;
import com.revature.bankapp.util.AuthUtil;
import com.saving.Saving;

public class MainMenuPrompt implements Prompt {

	AccountDao accountDao = AccountDao.currentImplementation;
	TransactonDao transDao = TransactonDao.currentImpl;
	private Logger log = Logger.getRootLogger();
	Scanner keyboard = new Scanner(System.in);
	AuthUtil auth = AuthUtil.instance;
	Bank bank = new Bank();
	boolean exist;

	public Prompt run() {
		printHeader();
		while (!exist) {
			printmenu();
			int choice = getInput();
			performAction(choice);
		}
		return new LoginPrompt();
	}

	private void printHeader() {
		transDao.findAll();
		System.out.println("+------------------------------------------------+");
		System.out.println("|  Welcome to Wakanda Bank                       |");
		System.out.println("|    We Bank on Vabraniunm                       |");
		System.out.println("+------------------------------------------------+");

	}

	private void printmenu() {
		// TODO Auto-generated method stub
		System.out.println("please make a selection");
		System.out.println("1.Create new account");
		System.out.println("2.make a disposit");
		System.out.println("3.make a withdrawal");
		System.out.println("4.list account balance");
		System.out.println("5.Delete account");
		System.out.println("0.Exit");
	}

	private int getInput() {
		int choice = -1;
		do {
			System.out.println("Enter your choice:");
			try {
				choice = Integer.parseInt(keyboard.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("invalid selection. wakanda number only.");
			}
			if (choice < 0 || choice > 5) {
				System.out.println("out of range");
			}
		} while (choice < 0 || choice > 5);
		return choice;
	}

	private void performAction(int choice) {
		switch (choice) {
		case 0:
			System.out.println("thank you for using the application");
			System.exit(0);
			break;
		case 1:
			Prompt p = new CreateAccountPrompt();
			p.run();
			// createAnAccount();
			break;
		case 2:
			makeADeposit();
			break;
		case 3:
			makeAwithdrawal();
			break;
		case 4:
			listBalances();
			break;
		case 5:
			deleteAccount();
			break;
		
		default:
			System.out.println("unknown error occured");
		}

	}

	private void LogInAsAdmin() {
		// TODO Auto-generated method stub
		
	}

	private void deleteAccount() {
		AuthUtil auth = AuthUtil.instance;
		Customer customer = auth.getCurrentCustomer();
		int userId = customer.getUserId();
		int accountId = customer.getUserId();
		accountDao.delete(userId, accountId);
		
		
	}

	private void createAnAccount() {
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
		Account account;
		AuthUtil auth = AuthUtil.instance;
		if (accountType.equalsIgnoreCase("checking")) {
			account = new Checking(initialDeposit);
		} else {
			account = new Saving(initialDeposit);
		}
		log.trace("sending new customer to dao");
		bank.addCustomer(auth.getCurrentUser(account));

	}

	private void makeADeposit() {
		int accountId = selectAccount();
		if (accountId >= 0) {
			System.out.print("How much would you like to deposit");
			double amount = 0;
			try {
				amount = Double.parseDouble(keyboard.nextLine());
			}

			catch (NumberFormatException e) {
				amount = 0;
			}
			accountDao.deposit(accountId, amount);
			transDao.save(amount, accountId, auth.getCurrentCustomer().getUserId());

//			((Account) bank.getCustomer(account).getAccount()).deposit(amount);

		}
	}

	private void listBalances() {
		int account = selectAccount();
		if (account >= 0) {

			System.out.println("balance of account " + accountDao.balance(account));
			// System.out.println(bank.getCustomer(account).getAccount());

		}

	}

	private void makeAwithdrawal() {
		int accountId = selectAccount();
		if (accountId >= 0) {
			System.out.print("How much would you like to withdraw ");
			double amount = 0;
			try {
				amount = Double.parseDouble(keyboard.nextLine());
			}

			catch (NumberFormatException e) {
				amount = 0;
			}
			accountDao.withdraw(accountId, amount);
			transDao.save(amount, accountId, auth.getCurrentCustomer().getUserId());
		}
	}

	private int selectAccount() {
		Customer customer = auth.getCurrentCustomer();
		List<Account> accounts = accountDao.findAccountByUserId(customer.getUserId());
		// ArrayList<Customer> customers = bank.getCustomer();
		if (accounts.size() <= 0) {
			System.out.println("No customers at your bank");
			return -1;
		}
		System.out.println("please select an account");
		for (int i = 0; i < accounts.size(); i++) {
			// System.out.println((i + 1) + ") " + customers.get(i).basicInfo());
			System.out.println("account number " + accounts.get(i).getAccountNumber());
		}
		int accountId = 0;
		System.out.print("please enter your selection.");
		try {
			accountId = Integer.parseUnsignedInt(keyboard.nextLine());
		}

		catch (NumberFormatException e) {
			accountId = -1;
		}
//		if (account < 0 || account > accounts.size()) {
//			System.out.println("invalid account selection.");
//			account = -1;
//		}1

		return accountId;
	}
}
