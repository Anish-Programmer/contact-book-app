package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {

	private Connection connection;



	// Constructor that establishes database connection
	public ContactDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/contactbook",
					"root",
					""
					);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * private Connection connection;
	 *
	 * public ContactDAO(){
	 *	try {
	 *		Class.forName("com.mysql.cj.jdbc.Driver");
	 *		connection = DriverManager.getConnection
	 *	}
	 *}
	 * */




	// Example method for adding a contact
	public int addContact(Contact contact) {
		String sql = "INSERT INTO contacts (name, phone, email, address) VALUES (?, ?, ?, ?)";

		try(PreparedStatement ps = connection.prepareStatement(sql);) {

			ps.setString(1, contact.getName());
			ps.setString(2, contact.getPhone());
			ps.setString(3, contact.getEmail());
			ps.setString(4, contact.getAddress());
			int rowsAffected = ps.executeUpdate();
			return rowsAffected;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}


	// method to get contact by id
	public Contact getContactById(int id) {
		Contact contact = null;
		String sql = "SELECT * FROM contacts WHERE id = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			// Set the ID parameter in the query
			ps.setInt(1, id);

			// Execute the query
			try (ResultSet resultSet = ps.executeQuery()) {
				if (resultSet.next()) { // Check if there is a row in the result set
					contact = new Contact(
							resultSet.getInt("id"),
							resultSet.getString("name"),
							resultSet.getString("phone"),
							resultSet.getString("email"),
							resultSet.getString("address")
							);
				}
			} catch (SQLException e) {
				e.printStackTrace(); // Log any SQL-related errors
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Log errors in preparing or executing the statement
		}

		return contact;
	}

	// Method to get a contact by name
	public Contact getContactByName(String name) {
		Contact contact = null;
		String sql = "SELECT * FROM contacts WHERE name = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, name);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					contact = new Contact(
							rs.getInt("id"),
							rs.getString("name"),
							rs.getString("phone"),
							rs.getString("email"),
							rs.getString("address")
							);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contact;
	}

	// method to get all the contacts
	public List<Contact> getAllContacts() {
		List<Contact> contacts = new ArrayList<>();
		String query = "SELECT * FROM contacts";
		try (PreparedStatement ps = connection.prepareStatement(query);
				ResultSet resultSet = ps.executeQuery();) {


			while (resultSet.next()) {
				contacts.add(new Contact(
						resultSet.getInt("id"),
						resultSet.getString("name"),
						resultSet.getString("phone"),
						resultSet.getString("email"),
						resultSet.getString("address")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contacts;
	}




	// method to update contact
	public void updateContact(Contact contact) {

		String sql = "UPDATE contacts SET name = ?, phone = ?, email = ?, address = ? WHERE id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);){


			ps.setString(1, contact.getName());
			ps.setString(2, contact.getPhone());
			ps.setString(3, contact.getEmail());
			ps.setString(4, contact.getAddress());
			ps.setInt(5, contact.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// method  to delete contact
	public void deleteContact(int id) {
		String sql = "DELETE FROM contacts WHERE id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);) {

			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	// method  ot search contact
	public  List<Contact> searchContact(String name) {

		List<Contact> contacts = new ArrayList<>();
		String sql = "SELECT * FROM contacts WHERE name LIKE ?";
		try (PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet resultSet = ps.executeQuery();) {

			ps.setString(1, "%" + name + "%");

			while (resultSet.next()) {
				contacts.add(new Contact(
						resultSet.getInt("id"),
						resultSet.getString("name"),
						resultSet.getString("phone"),
						resultSet.getString("email"),
						resultSet.getString("address")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contacts;
	}




}
