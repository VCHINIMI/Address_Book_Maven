package UC13.Address_Book_Maven_New;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;
import com.mysql.jdbc.integration.jboss.ExtendedMysqlExceptionSorter;

import UC13.Address_Book_Maven_New.AddressBookException.ExceptionType;

public class AddressBookDBService {

	private static AddressBookDBService addressBookDBService;
	private java.sql.PreparedStatement addressBookDataStatement;
	private static List<Contact> addressBookList;

//  Private Constructor for singleton Object	
	private AddressBookDBService() {
	}

//	To get Instance of object
	public static AddressBookDBService getInstance() {
		if (addressBookDBService == null)
			addressBookDBService = new AddressBookDBService();
		return addressBookDBService;
	}

//	Method for establishing Connection
	private Connection getConnection() throws AddressBookException {
		try {
			String jdbcURL = "jdbc:mysql://localhost:3306/address_book?useSSL=false";
			String userName = "root";
			String password = "root";
			Connection connection;
			System.out.println("Connecting to Database" + jdbcURL);
			connection = DriverManager.getConnection(jdbcURL, userName, password);
			System.out.println("successfull" + connection);
			return connection;
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.CONNECTION_FAULT);
		}
	}

//	Read Data From DB by SQL	
	public List<Contact> readData() throws AddressBookException {
		String sql = "select ab.id,ab.fname,ab.lname,a.address,a.city,a.state,a.zipcode," + "ab.phone,ab.email, "
				+ "bn.bookname , bt.booktype from address_book_table_1 "
				+ "ab INNER JOIN address_table_1 a ON ab.id = a.contactId"
				+ " INNER JOIN book_name_table_1 bn ON ab.id = bn.contact_id"
				+ " INNER JOIN book_type_table_1 bt ON ab.id = bt.contact_id;";
		return getDataFromDatabaseBySQL(sql);
	}

//	get Data from Database by connection and result set	
	private List<Contact> getDataFromDatabaseBySQL(String sql) throws AddressBookException {
		List<Contact> addressBookList = new ArrayList<>();
		try (Connection connection = this.getConnection()) {
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String fname = resultSet.getString("fname");
				String lname = resultSet.getString("lname");
				String address = resultSet.getString("address");
				String city = resultSet.getString("city");
				String state = resultSet.getString("state");
				int zip = resultSet.getInt("zipcode");
				int phone = resultSet.getInt("phone");
				String email = resultSet.getString("email");
				String bookName = resultSet.getString("bookname");
				String bookType = resultSet.getString("booktype");
				addressBookList.add(
						new Contact(id, fname, lname, address, city, state, zip, phone, email, bookName, bookType));
			}
			return addressBookList;
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DATABASE_NOT_EXIST);
		} catch (AddressBookException e) {
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.SQL_FAULT);
		}
	}

//	Updating contact data on DB
	public int updateContactData(String fname, int phone) throws AddressBookException {
		return this.updateEmployeeDetailsUsingStatement(fname, phone);
	}

	private int updateEmployeeDetailsUsingStatement(String fname, int phone) throws AddressBookException {
		String sql = String.format("update address_book_table_1 set phone = %d where fname = '%s';", phone, fname);
		try (Connection connection = this.getConnection()) {
			java.sql.Statement statement = connection.createStatement();
			int result = statement.executeUpdate(sql);
			return result;
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.CONNECTION_FAULT);
		} catch (AddressBookException e) {
			throw new AddressBookException(e.getMessage(), e.type);
		}
	}

	public List<Contact> getAddressBookData(String name) throws AddressBookException {
		addressBookList = new ArrayList<Contact>();
		if (this.addressBookDataStatement == null)
			this.preparedStatementForAddressBook();
		try {
			addressBookDataStatement.setString(1, name);
			ResultSet resultSet = addressBookDataStatement.executeQuery();
			addressBookList = this.getAddressBookData(resultSet);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return addressBookList;
	}

	private List<Contact> getAddressBookData(ResultSet resultSet) throws AddressBookException {
		List<Contact> addressBookList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String fname = resultSet.getString("fname");
				String lname = resultSet.getString("lname");
				String address = resultSet.getString("address");
				String city = resultSet.getString("city");
				String state = resultSet.getString("state");
				int zip = resultSet.getInt("zipcode");
				int phone = resultSet.getInt("phone");
				String email = resultSet.getString("email");
				String bookName = resultSet.getString("bookname");
				String bookType = resultSet.getString("booktype");
				addressBookList.add(
						new Contact(id, fname, lname, address, city, state, zip, phone, email, bookName, bookType));
			}
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.CONNECTION_FAULT);
		}
		return addressBookList;
	}

	private void preparedStatementForAddressBook() throws AddressBookException {
		try {
			Connection connection = this.getConnection();
			String sql = "select ab.id,ab.fname,ab.lname,a.address,a.city,a.state,a.zipcode,"
					+ "ab.phone,ab.email, bn.bookname , bt.booktype from address_book_table_1 ab"
					+ " INNER JOIN address_table_1 a ON ab.id = a.contactId"
					+ " INNER JOIN book_name_table_1 bn ON ab.id = bn.contact_id"
					+ " INNER JOIN book_type_table_1 bt ON ab.id = bt.contact_id" + " WHERE ab.fname = ?";
			addressBookDataStatement = connection.prepareStatement(sql);
		} catch (AddressBookException e) {
			throw new AddressBookException(e.getMessage(), e.type);
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.CONNECTION_FAULT);
		}
	}

	public List<Contact> getContactsBetweenDates(LocalDate start, LocalDate end) throws AddressBookException {
		String sql = "select ab.id,ab.fname,ab.lname,a.address,a.city,a.state,a.zipcode," + "ab.phone,ab.email, "
				+ "bn.bookname , bt.booktype from address_book_table_1 "
				+ "ab INNER JOIN address_table_1 a ON ab.id = a.contactId"
				+ " INNER JOIN book_name_table_1 bn ON ab.id = bn.contact_id"
				+ " INNER JOIN book_type_table_1 bt ON ab.id = bt.contact_id"
				+ " WHERE ab.date_added BETWEEN '%s' AND '%s' ;";
		String sql2 = String.format(sql, Date.valueOf(start), Date.valueOf(end));
		return getDataFromDatabaseBySQL(sql2);
	}

	public List<Contact> getContactsByCity(String city) throws AddressBookException {
		String sql = "select ab.id,ab.fname,ab.lname,a.address,a.city,a.state,a.zipcode," + "ab.phone,ab.email, "
				+ "bn.bookname , bt.booktype from address_book_table_1 "
				+ "ab INNER JOIN address_table_1 a ON ab.id = a.contactId"
				+ " INNER JOIN book_name_table_1 bn ON ab.id = bn.contact_id"
				+ " INNER JOIN book_type_table_1 bt ON ab.id = bt.contact_id" + " WHERE a.city = '%s';";
		String sql2 = String.format(sql, city);
		return getDataFromDatabaseBySQL(sql2);
	}

	public List<Contact> getContactsByState(String state) throws AddressBookException {
		String sql = "select ab.id,ab.fname,ab.lname,a.address,a.city,a.state,a.zipcode," + "ab.phone,ab.email, "
				+ "bn.bookname , bt.booktype from address_book_table_1 "
				+ "ab INNER JOIN address_table_1 a ON ab.id = a.contactId"
				+ " INNER JOIN book_name_table_1 bn ON ab.id = bn.contact_id"
				+ " INNER JOIN book_type_table_1 bt ON ab.id = bt.contact_id" + " WHERE a.state = '%s';";
		String sql2 = String.format(sql, state);
		return getDataFromDatabaseBySQL(sql2);
	}

//	Add Contact to DB	
	public Contact addNewContact(String f_Name, String l_Name, String address, String city, String state, int zip,
			int phone_Number, String email, String bookName, String bookType, LocalDate date)
			throws AddressBookException {
		int contactId = -1;
		Connection connection = null;
		Contact contact = null;
		try {
			connection = this.getConnection();
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				// throw new AddressBookException(e.getMessage(),
				// AddressBookException.ExceptionType.CONNECTION_FAULT);
			}
		} catch (AddressBookException e) {
			throw new AddressBookException(e.getMessage(), e.type);
		}

		// insert into address_book_table
		try (java.sql.Statement statement = connection.createStatement()) {
			String sql = String.format(
					"INSERT INTO address_book_table_1(fname,lname,phone,email,date_added)"
							+ " VALUES ('%s','%s', %s ,'%s','%s') ;",
					f_Name, l_Name, phone_Number, email, Date.valueOf(date));
			int rowsAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if (rowsAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				while (resultSet.next())
					contactId = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
				return contact;
			} catch (SQLException e1) {
			}
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.SQL_FAULT);
		}

		// insert into address_table_1;
		try (java.sql.Statement statement = connection.createStatement()) {
			String sql = String.format(
					"INSERT INTO address_table_1(contactId, address, city, state, zipcode) values (%s, '%s','%s','%s', %s); ",
					contactId, address, city, state, zip);
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			try {
				connection.rollback();
				return contact;
			} catch (SQLException e1) {
			}
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.SQL_FAULT);
		}

		// insert into bookname
		try (java.sql.Statement statement = connection.createStatement()) {
			String sql = String.format("INSERT INTO book_name_table_1(contact_Id, bookname) values (%s, '%s'); ",
					contactId, bookName);
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			try {
				connection.rollback();
				return contact;
			} catch (SQLException e1) {
			}
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.SQL_FAULT);
		}

		// insert into booktype
		try (java.sql.Statement statement = connection.createStatement()) {
			String sql = String.format("INSERT INTO book_type_table_1(contact_Id, booktype) values (%s, '%s'); ",
					contactId, bookType);
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if (rowAffected == 1)
				contact = new Contact(contactId, f_Name, l_Name, address, city, state, zip, phone_Number, email,
						bookName, bookType, date);
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
			}
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.SQL_FAULT);
		}

		// final commit
		try {
			connection.commit();
		} catch (SQLException e) {
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.CONNECTION_FAULT);
		} finally {
			// Connection close
			try {
				connection.close();
			} catch (SQLException e) {
				throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.CONNECTION_FAULT);
			}
		}
		return contact;
	}
}
