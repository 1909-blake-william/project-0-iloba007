package com.driver;

import com.Login.LoginPrompt;
import com.Login.MainMenuPrompt;
import com.Login.Prompt;

public class BankDriver {
	public static void main(String[] args) {
		Prompt p = new LoginPrompt();
		while (true) {
			p = p.run();
		}
	}
}
