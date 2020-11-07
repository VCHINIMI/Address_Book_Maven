package UC13.Address_Book_Maven_New;

public class AddressBookException extends Exception{
	private static final long serialVersionUID = 5659569326670024185L;

	public enum ExceptionType {
		CONNECTION_FAULT, PATH_FAULT, DATABASE_NOT_EXIST, SQL_FAULT, THREAD_FAULT;
	}

	ExceptionType type;

	public AddressBookException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}

	public AddressBookException(String message, ExceptionType type, Throwable cause) {
		super(message, cause);
		this.type = type;
	}

}
