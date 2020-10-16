package UC13.Address_Book_Maven_New;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class CSVTesting {
	@Test
	public void ReadAndWriteFromCSVTest() throws Exception {
		Contact contact = new Contact("Vinay", "ch", "55", "vskp", "AP", 5330, 99122, "vina.ch@gmail");
		Address_Book_Main address_Book_Main = new Address_Book_Main();
		address_Book_Main.writeContactToCSV(contact);
		address_Book_Main.writeContactToCSV(contact);
		address_Book_Main.writeContactToFile(contact, " ");
		assertEquals(new Contact("Vinay", "ch", "55", "vskp", "AP", 5330, 99122, "vina.ch@gmail"),
				address_Book_Main.readContactFromCsv().get(0));
	}

	@Test
	public void writeContactToAddressBookFileTest() {
		Address_Book_Main address_Book_Main = new Address_Book_Main();
		address_Book_Main.writeContactToFile(
				new Contact("Vinay", "ch", "55", "vskp", "AP", 5330, 99122, "vina.ch@gmail"), "Contacts.txt");
		address_Book_Main.writeContactToFile(
				new Contact("Vinay", "ch", "55", "vskp", "AP", 5330, 99122, "vina.ch@gmail"), "Contacts.txt");
		List<Contact> contactList = address_Book_Main.readContactFromFile("Contacts.txt");
		assertEquals(contactList.get(0), new Contact("Vinay", "ch", "55", "vskp", "AP", 5330, 99122, "vina.ch@gmail"));
		assertEquals(contactList.get(1), new Contact("Vinay", "ch", "55", "vskp", "AP", 5330, 99122, "vina.ch@gmail"));

	}
}