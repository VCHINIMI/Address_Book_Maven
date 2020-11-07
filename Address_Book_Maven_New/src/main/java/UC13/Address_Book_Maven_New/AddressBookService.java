package UC13.Address_Book_Maven_New;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
		int result = addressBookDBService.updateContactData(fname, phone);
		if (result == 0)
			return;
		Contact conatctData = this.getAddressBookData(fname);
		if (conatctData != null)
			conatctData.phone_Number = phone;
	}

	@SuppressWarnings("static-access")
	private Contact getAddressBookData(String fname) {
		return this.addressBookList.stream().filter(ContactDataItem -> ContactDataItem.f_Name.equals(fname)).findFirst()
				.orElse(null);
	}

	public boolean checkAddressBookInSyncWithDB(String fname) throws AddressBookException {
		List<Contact> addressBookDataList = addressBookDBService.getAddressBookData(fname);
		return addressBookDataList.get(0).equals(this.getAddressBookData(fname));
	}

	public List<Contact> getContactsBetweenDates(LocalDate start, LocalDate end) throws AddressBookException {
		List<Contact> contactBetweenDateList = addressBookDBService.getContactsBetweenDates(start, end);
		return contactBetweenDateList;
	}

	public List<Contact> getContactsByCity(String city) throws AddressBookException {
		List<Contact> contactInCityList = addressBookDBService.getContactsByCity(city);
		return contactInCityList;
	}

	public List<Contact> getContactsByState(String state) throws AddressBookException {
		List<Contact> contactInStateList = addressBookDBService.getContactsByState(state);
		return contactInStateList;
	}

	public void addNewContactToDB(String f_Name, String l_Name, String address, String city, String state, int zip,
			int phone_Number, String email, String bookName, String bookType, LocalDate date)
			throws AddressBookException {
		addressBookList.add(addressBookDBService.addNewContact(f_Name, l_Name, address, city, state, zip, phone_Number,
				email, bookName, bookType, date));

	}

	public void addContactArrayToDB(Contact[] arrayOfContacts) throws AddressBookException {
		List<Contact> contactsList = Arrays.asList(arrayOfContacts);
		Map<Integer, Boolean> employeeAdditionStatus = new HashMap<Integer, Boolean>();
		Iterator<Contact> iterator = contactsList.iterator();
		while (iterator.hasNext()) {
			Contact contact = iterator.next();
			Runnable task = () -> {
				employeeAdditionStatus.put(contact.hashCode(), false);
				System.out.println("Employee Being Added : " + Thread.currentThread().getName());
				try {
					this.addNewContactToDB(contact.f_Name, contact.l_Name, contact.address, contact.city, contact.state,
							contact.zip, contact.phone_Number, contact.email, contact.bookName, contact.bookType,
							contact.date);
				} catch (AddressBookException e) {
					System.out.println(e.getMessage() + " in add contact array");
				}
				employeeAdditionStatus.put(contact.hashCode(), true);
				System.out.println("Emp Added" + Thread.currentThread().getName());
			};
			Thread thread = new Thread(task, contact.f_Name);
			thread.start();
		}

		while (employeeAdditionStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.THREAD_FAULT);
			}

		}

	}

	public int countEntries() {
		return addressBookList.size();
	}
}