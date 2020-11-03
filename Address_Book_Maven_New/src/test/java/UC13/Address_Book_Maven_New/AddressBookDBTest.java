package UC13.Address_Book_Maven_New;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
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
		assertEquals(contactList.size(), 1);
	}
}
