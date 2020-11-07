package UC13.Address_Book_Maven_New;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import UC13.Address_Book_Maven_New.AddressBookService.IOService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddressBookJSONTest {

//	Initialising REST URL	
	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}
	
//	Count Entries
	@Test
	public void returnCountOfEntriesFromJson() {
		Contact[] arrayOfContacts = getContactArray();
		AddressBookService addressBookService ;
		addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
		int count = addressBookService.countEntries(IOService.REST_IO);
		assertEquals(1, count);
	}
	
	public void AddContactTest() {
		Contact contact = new Contact(0, "sri", "modugu", "Dr.no", "vjd", "AP", 560016, 98003, "sm@gmail", "BOOK4", "FRIEND");
		Response response = addEmployeeToJsonServer(contact);
		assertEquals(201, response.getStatusCode());
	}

	private Contact[] getContactArray() {
		Response response = RestAssured.get("/contacts");
		System.out.println("ENTRIES :"+ response.asString());
		Contact[] contactArray = new Gson().fromJson(response.asString(), Contact[].class);
		return contactArray;
	}
	
	private Response addEmployeeToJsonServer(Contact employeePayrollData) {
		String empJson = new Gson().toJson(employeePayrollData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type","application/json");
		request.body(empJson);
		return request.post("/contacts");
	}
}
