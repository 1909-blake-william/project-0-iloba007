package com.revature.bankapp.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.account.Account;
import com.customer.Customer;

public class DatabaseDao {
	public Customer<Account> checkUserNameAndPassword(String username, String password) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM Customer WHERE USERNAME=? AND PASSWORD=? ";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String firstName = rs.getString("first_Name");
				String lastName = rs.getString("last_Name");
				int ssn = rs.getInt("ssn");
				int userId = rs.getInt("user_id");
				return new Customer<Account>(firstName, lastName, username, password, ssn, userId, new Account());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
