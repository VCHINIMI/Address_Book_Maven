package UC13.Address_Book_Maven_New;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {

	private static AddressBookDBService addressBookDBService;

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
}
