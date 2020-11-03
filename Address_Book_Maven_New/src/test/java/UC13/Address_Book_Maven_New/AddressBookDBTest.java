package UC13.Address_Book_Maven_New;

import static org.junit.Assert.*;
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
}
