package UC13.Address_Book_Maven_New;

import java.util.List;

public class AddressBookService {
	public static List<Contact> addressBookList;
	private AddressBookDBService addressBookDBService ;
	public enum IOService {
		DB_IO
	}
	
	public AddressBookService() {
		this.addressBookDBService = AddressBookDBService.getInstance();
	}
	public List<Contact> readAddressBookData(IOService ioService) throws AddressBookException {
		if (ioService.equals(IOService.DB_IO))
			this.addressBookList = addressBookDBService.readData();
		return this.addressBookList;
	}
}