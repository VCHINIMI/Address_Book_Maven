package UC13.Address_Book_Maven_New;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;

import UC13.Address_Book_Maven_New.AddressBookService.IOService;

public class AddressBookDBTest {

//	Test case for loading data From DB	
	@Test
	public void givenEmployeePayrollInDB_ShouldMatchEmployeeCount() throws AddressBookException {
		AddressBookService addressBookService = new AddressBookService();
		List<Contact> addressBookData = addressBookService.readAddressBookData(IOService.DB_IO);
		assertEquals(6, addressBookData.size());
	}
}
