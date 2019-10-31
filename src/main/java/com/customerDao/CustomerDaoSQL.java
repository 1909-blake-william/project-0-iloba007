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
import com.revature.bankapp.ConnectionUtil;

public class CustomerDaoSQL implements CustomerDao {

	private Logger log = Logger.getRootLogger();

	Customer extractUser(ResultSet rs) throws SQLException {
		String firstName = rs.getString("Customer_firstName");
		String lastName = rs.getString("lastName");
		int ssn = rs.getInt("ssn");
		String username = rs.getString("username");
		String password = rs.getString("password");
		String account = rs.getString("account");
		// Account extractedAccount = new Account(initialDeposit);
		return new Customer<Account>(firstName, lastName, username, password, ssn, new Account());
	}

	@Override
	public int save(Customer currentCustomer) {
		log.debug("running save method");
		try (Connection bankApp = ConnectionUtil.getConnection()) {

			String sql = "INSERT INTO CUSTOMER (USER_ID, first_name,last_name,username,password,ssn )"
					+ " VALUES (USER_ID_SEQ.nextval, ?,?,?,?,?)";

			PreparedStatement ps = bankApp.prepareStatement(sql);
			ps.setString(1, currentCustomer.getFirstName());
			ps.setString(2, currentCustomer.getLastName());
			ps.setString(3, currentCustomer.getUsername());
			ps.setString(4, currentCustomer.getPassword());
			ps.setInt(5, currentCustomer.getSsn());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;

		}
	}

	@Override
	public List<Customer> findAll() {
		log.debug("attempting to find all Customer from DB");
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM Customer";

			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Customer> customers = new ArrayList<Customer>();
			while (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				System.out.println("customer name: " + firstName + ", " + lastName);
			}
			return customers;

		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}
	}

	private List<Customer> extractTypes(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer findById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer findByUsernameAndPassword(String username, String password) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public Customer findByUsername(String username) {

		return null;
	}
}
