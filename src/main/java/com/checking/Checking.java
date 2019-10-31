package com.checking;

import com.account.Account;

public class Checking extends Account {
	private static String accountType = "checking";

	public Checking(double initialDeposit) {
		super();
		this.setBalance(initialDeposit);
		this.checkInterest(0);
	}

	public void checkInterest() {
		// TODO Auto-generated method stub

	}

	public void setBalance(double initialDeposit) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return "Account type: " + accountType + " Account\n" + "Account number:" + this.getAccountNumber() + "\n"
				+ "Balance: " + this.getBalance() + "\n" + "interest Rate:" + this.getInterest() + "\n";

	}

	public double getInterest() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getBalance() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getAccountNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

}
