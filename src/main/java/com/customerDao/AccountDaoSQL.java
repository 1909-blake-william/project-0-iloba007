package com.customerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.account.Account;
import com.checking.Checking;
import com.customer.Customer;
import com.revature.bankapp.ConnectionUtil;
import com.revature.bankapp.util.AuthUtil;

public class AccountDaoSQL implements AccountDao {
	private Logger log = Logger.getRootLogger();
	AuthUtil auth = AuthUtil.instance;

	@Override
	public Account extractAccount(ResultSet rs) throws SQLException {
		int accountNumber = rs.getInt("account_id");
		double balance = rs.getDouble("balance");
		String accountType = rs.getString("account_type");

		return new Account(balance, accountNumber, accountType);
	}

	@Override
	public int save(Account currentAccount) {
		log.debug("running save method");
		try (Connection bankApp = ConnectionUtil.getConnection()) {

			String sql = "INSERT INTO ACCOUNT (account_id,account_type,balance)" + " VALUES (USER_ID_SEQ.nextval, ?,?)";

			PreparedStatement ps = bankApp.prepareStatement(sql);
			ps.setString(1, currentAccount.getAccountType());
			ps.setDouble(2, currentAccount.getBalance());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Account> findAll() {
		log.debug("attempting to find all Account from DB");
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM ACCOUNT";

			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Account> customers = new ArrayList<Account>();

			while (rs.next()) {
				customers.add(extractAccount(rs));
			}
			return customers;

		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int deposit(int id, double amount) {
		try (Connection bankApp = ConnectionUtil.getConnection()) {

			double balance = balance(id);

			String sql = "UPDATE ACCOUNT SET balance = ? WHERE ACCOUNT_ID = ?";
			PreparedStatement ps = bankApp.prepareStatement(sql);
			ps.setDouble(1, balance + amount);
			ps.setInt(2, id);

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int withdraw(int id, double amount) {
		try (Connection bankApp = ConnectionUtil.getConnection()) {

			double balance = balance(id);

			String sql = "UPDATE ACCOUNT SET balance = ? WHERE ACCOUNT_ID = ?";
			PreparedStatement ps = bankApp.prepareStatement(sql);
			ps.setDouble(1, balance - amount);
			ps.setInt(2, id);

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double balance(int id) {
		log.debug("attempting to balance for account " + id);
		try (Connection bankApp = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ACCOUNT WHERE  ACCOUNT_ID = ? ";
			PreparedStatement ps = bankApp.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			double balance = 0;
			if (rs.next()) {
				balance = rs.getDouble("balance");
			}
			// TODO Auto-generated method stub
			return balance;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
