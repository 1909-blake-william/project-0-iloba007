package com.account;

public class Account {
	protected double balance = 0;
	protected double interest = 0.03;
	protected int accountNumber;
	protected String accountType;
	protected static int numberOfAccounts = 1000000;

	public Account() {
		accountNumber = numberOfAccounts++;
	}

	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @param interest the interest to set
	 */
	public void setInterest(double interest) {
		this.interest = interest;
	}

	public Account(double balance, int accountNumber, String accountType) {
		super();
		this.balance = balance;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
	}

	public Account(double initialDeposit) {
		balance = initialDeposit;
		int randomId = (int) Math.floor(Math.random() * 1000000) + 1;
		accountNumber = randomId;
		numberOfAccounts++;
	}

	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * @return the integer
	 */
	public double getInterest() {
		return interest;
	}

	/**
	 * @param integer the integer to set
	 */
	public void setTnterest(double integer) {
		this.interest = interest;
	}

	/**
	 * @return the accountNumber
	 */
	public int getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void withdraw(double amount) {
		if (amount + 5 > balance) {
			System.out.println("you do not have that amount of money, get a job");
			return;
		}
		balance -= amount + 5;
		checkInterest(0);
		System.out.println(
				"you are rich and have withdrawn" + amount + "dollars and incurred a fee of $5 wakanda dollars ");
		System.out.println("you now have a balance :" + balance);
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			System.out.println("you dont have a wakanda dollars");
			return;
		}
		checkInterest(amount);
		amount = amount + amount + interest;
		balance += amount;
		System.out.println("you are rich and have deposited" + amount + " dollars with an interst rate of "
				+ (interest * 100) + "%");
		System.out.println("you now have a balance :" + balance);
	}

	public void checkInterest(double amount) {
		if (balance > 10000) {
			interest = 0.05;
		} else {
			interest = 0.02;
		}

	}

}