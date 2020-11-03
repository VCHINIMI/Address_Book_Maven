package UC13.Address_Book_Maven_New;

import java.util.List;

public class AddressBookService {
	public static List<Contact> addressBookList;
	private AddressBookDBService addressBookDBService;

	public enum IOService {
		DB_IO
	}

	public AddressBookService() {
		this.addressBookDBService = AddressBookDBService.getInstance();
	}

	@SuppressWarnings("static-access")
	public List<Contact> readAddressBookData(IOService ioService) throws AddressBookException {
		if (ioService.equals(IOService.DB_IO))
			this.addressBookList = addressBookDBService.readData();
		return this.addressBookList;
	}

	public void updateContactPhone(String fname, int phone) throws AddressBookException {
		int result =  addressBookDBService.updateContactData(fname,phone);
		if(result == 0)
			return;
		Contact conatctData = this.getAddressBookData(fname);
		if(conatctData != null) conatctData.phone_Number = phone;
	}
	
	@SuppressWarnings("static-access")
	private Contact getAddressBookData(String fname) {
		return this.addressBookList.stream()
					.filter(ContactDataItem -> ContactDataItem.f_Name.equals(fname))
					.findFirst()
					.orElse(null);
	}
	
	public boolean checkAddressBookInSyncWithDB(String fname) throws AddressBookException {
		List<Contact> addressBookDataList = addressBookDBService.getAddressBookData(fname);
		return addressBookDataList.get(0).equals(this.getAddressBookData(fname));
	}
}