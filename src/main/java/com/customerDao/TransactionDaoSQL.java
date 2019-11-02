package com.customerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.customer.Customer;
import com.revature.bankapp.util.AuthUtil;
import com.revature.bankapp.util.ConnectionUtil;
import com.transaction.Transaction;

public class TransactionDaoSQL implements TransactonDao {

	Logger log = Logger.getRootLogger();

	@Override
	public boolean save(double amount, int accountId, int userId) {
		log.debug("running save method");
		try (Connection bankApp = ConnectionUtil.getConnection()) {

			String sql = "INSERT INTO transaction (transaction_id, amount, account_Id, user_id)"
					+ " VALUES (TRANSACTION_ID_SEQ.nextval, ?,?,?)";

			PreparedStatement ps = bankApp.prepareStatement(sql);
			ps.setDouble(1, amount);
			ps.setInt(2, accountId);
			ps.setInt(3, userId);

			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Transaction> findByIds(int accountId, int userId) {
		log.debug("looking for transaction by userId");
		List<Transaction> transactions = new ArrayList<>();
		try (Connection bankApp = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM transaction WHERE account_id = ? AND user_id = ?";

			PreparedStatement ps = bankApp.prepareStatement(sql);
			ps.setInt(1, accountId);
			ps.setInt(2, userId);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int transactionId = rs.getInt("transaction_id");
				float amount = rs.getInt("amount");

				transactions.add(new Transaction(transactionId, amount, accountId, userId));
			}
			return transactions;

		} catch (SQLException e) {
			e.printStackTrace();
			return transactions;
		}
	}

	@Override
	public List<Transaction> findAll() {
		log.debug("attempting to find all Transaction from DB");
		List<Transaction> transactions = new ArrayList<>();
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM transaction";

			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				AuthUtil auth = AuthUtil.instance;
				//Customer customer = auth.getCurrentUser();
				int transactionId = rs.getInt("transaction_id");
				float amount = rs.getFloat("amount");
				int accountId = rs.getInt("account_id");
				int userId = rs.getInt("user_id");
				transactions.add(new Transaction(transactionId, amount, accountId, userId));
				//System.out.println("customer name: " + customer.getFirstName() + ", " + customer.getLastName());
			}
			System.out.println(transactions);
			return transactions;

		} catch (SQLException e) {
			e.printStackTrace();
			return transactions;
		}
	}

}
