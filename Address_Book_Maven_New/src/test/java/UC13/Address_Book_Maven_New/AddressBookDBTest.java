package UC13.Address_Book_Maven_New;

import static org.junit.Assert.*;

import java.awt.dnd.DragGestureEvent;
import java.time.LocalDate;
import java.util.List;
import org.junit.Test;
import UC13.Address_Book_Maven_New.AddressBookService.IOService;

public class AddressBookDBTest {

//	Test case for loading data From DB	
	@Test
	public void givenAddressBookDBShouldReturn_CountOfEntries() throws AddressBookException {
		AddressBookService addressBookService = new AddressBookService();
		List<Contact> addressBookData = addressBookService.readAddressBookData(IOService.DB_IO);
		assertEquals(6, addressBookData.size());
	}
	
//	Test Case for Updating Contact
	@Test
	public void givenNewPhoneForContact_ShouldUpdateInDB() throws AddressBookException {
		AddressBookService addressBookService = new AddressBookService();
		List<Contact> addressBookData = addressBookService.readAddressBookData(IOService.DB_IO);
		addressBookService.updateContactPhone("akuvakka", 12345);
		boolean result = addressBookService.checkAddressBookInSyncWithDB("akuvakka");
		assertTrue(result);
	}
	
//  Retrieve contacts added between dates
	@Test
	public void givenDates_ShouldReturnContacts() throws AddressBookException {
		AddressBookService addressBookService = new AddressBookService();
		List<Contact> contactList = addressBookService.getContactsBetweenDates(LocalDate.of(2015, 1, 1),LocalDate.of(2020, 1, 1) );
		assertEquals(contactList.size(), 5);
	}
	
//  Search by City
	@Test
	public void searchByCity_ShouldReturnContact() throws AddressBookException {
		AddressBookService addressBookService = new AddressBookService();
		List<Contact> contactList = addressBookService.getContactsByCity("VSKP");
		assertEquals(contactList.size(), 1);
	}
	
//	 Search By State
	@Test
	public void searchByState_ShouldReturnContact() throws AddressBookException {
		AddressBookService addressBookService = new AddressBookService();
		List<Contact> contactList = addressBookService.getContactsByState("AP");
		assertEquals(contactList.size(), 4);
	}

// Add new Contact
	@Test 
	public void addNewContactAndCheckInSync() throws AddressBookException {
		AddressBookService addressBookService = new AddressBookService(); 
		addressBookService.readAddressBookData(IOService.DB_IO);
		addressBookService.addNewContactToDB("Vina", "ch", "Dr.no", "Vskp", "AP", 123, 99014,
				"vchiay", "BOOK3", "FRIEND",LocalDate.now());
		boolean res = addressBookService.checkAddressBookInSyncWithDB("Vina");
		assertTrue(res);
	}
	
//	Add multiple contacts to db using Threads
	@Test
	public void addMultipleContactsToDB_ByThreads() throws AddressBookException {
		AddressBookService addressBookService = new AddressBookService(); 
		addressBookService.readAddressBookData(IOService.DB_IO);
		Contact[] arrayOfContacts = { new Contact(0, "sri", "modugu", "Dr.no", "vjd", "AP", 560016, 98003, "sm@gmail", "BOOK4", "FRIEND", LocalDate.of(2019, 9, 3)),
									  new Contact(0, "naresh", "ch", "Dr.no", "kkd", "AP", 530033, 98234, "nc@gmail", "BOOK4", "FAMILY", LocalDate.of(2019, 10, 4)),
									  new Contact(0, "jagga", "rao", "Dr.no", "kkd", "AP", 530033, 98232, "jg@gmail", "BOOK5", "FAMILY", LocalDate.of(20110, 10, 4)),
									};
		addressBookService.addContactArrayToDB(arrayOfContacts);
		assertEquals(10, addressBookService.countEntries());
	}
}
