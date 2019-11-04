package com.customerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.account.Account;
import com.customer.Customer;
import com.revature.bankapp.util.AuthUtil;
import com.revature.bankapp.util.ConnectionUtil;

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

			String sql = "INSERT INTO ACCOUNT (account_id, account_type, balance, user_id)"
					+ " VALUES (USER_ID_SEQ.nextval, ?,?,?)";

			PreparedStatement ps = bankApp.prepareStatement(sql);
			ps.setString(1, currentAccount.getAccountType());
			ps.setDouble(2, currentAccount.getBalance());
			Customer customer = auth.getCurrentCustomer();
			ps.setInt(3, customer.getUserId());
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
	public List<Account> findAccountByUserId(int userId) {
		log.debug("attempting to find all Account from DB");
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM ACCOUNT WHERE user_id = ?";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, userId);
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

	@Override
	public int delete(int userId, int accountId) {

		log.debug("attempting to delete account " + userId);
		try (Connection bankApp = ConnectionUtil.getConnection()) {
			String sql = "UPDATE Account SET account_status = 'inactive' WHERE user_id = ? AND ACCOUNT_ID=?";
			PreparedStatement ps = bankApp.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, accountId);
			ps.executeUpdate();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
