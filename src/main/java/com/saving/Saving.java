package com.saving;

import com.account.Account;

public class Saving extends Account {
	private static String accountType = "savings";

	public Saving(double initialDeposit) {
		super();
		this.setBalance(initialDeposit);
		this.checkInterest(0);
	}

	public void setBalance(double initialDeposit) {
		// TODO Auto-generated method stub

	}

	public void checkInterest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return "Account type: " + accountType + " Account\n" + "Account number:" + this.getAccountNumber() + "\n"
				+ "Balance: " + this.getBalance() + "\n" + "interest Rate:" + this.getBalance() + "\n";
	}

	public int getAccountNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getBalance() {
		// TODO Auto-generated method stub
		return 0;
	}

}
