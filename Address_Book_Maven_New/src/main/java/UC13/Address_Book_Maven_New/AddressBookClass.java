package UC13.Address_Book_Maven_New;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class AddressBookClass {
	public ArrayList<Contact> addressBook = new ArrayList<Contact>();
	public String nameOfAddressBook;

	public AddressBookClass(String nameOfAddressBook) {
		this.nameOfAddressBook = nameOfAddressBook;
	}

	public String getNameOfAddressBook() {
		return nameOfAddressBook;
	}

	public void setNameOfAddressBook(String nameOfAddressBook) {
		this.nameOfAddressBook = nameOfAddressBook;
	}

	public ArrayList<Contact> getAddressBook() {
		return addressBook;
	}

	public Contact takeContactInformation() {
		Scanner sc = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		System.out.println("Enter first name: ");
		String f_name = sc.nextLine();
		System.out.println("Enter Last Name : ");
		String l_name = sc2.nextLine();
		System.out.println("Enter Address : ");
		String address = sc.nextLine();
		System.out.println("Enter City : ");
		String city = sc2.nextLine();
		System.out.println("Enter State : ");
		String state = sc.nextLine();
		System.out.println("Enter Zip : ");
		int zip = sc2.nextInt();
		sc2.nextLine();
		System.out.println("Enter Phone Number : ");
		int phone_Number = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter email : ");
		String email = sc2.nextLine();
		Contact c = new Contact(f_name, l_name, address, city, state, zip, phone_Number, email);
		return c;
	}

	public void displayAllContacts() {
		Iterator<Contact> it = addressBook.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

	public void addContact(Contact contact) {
		boolean contactExists = addressBook.stream().anyMatch(c -> c.equals(contact));
		if (!contactExists)
			addressBook.add(contact);
		else
			System.out.println("Contact already exsits. please go for edit or delete!");
	}

	public void editContact(String fName) {
		int flag1 = 0;
		for (int i = 0; i < addressBook.size(); i++) {
			if (((addressBook.get(i)).f_Name).equals(fName)) {
				addressBook.set(i, takeContactInformation());
				flag1 = 1;
			}
		}
		if (flag1 == 0)
			System.out.println("Name not in addressBook");

	}

	public void deleteContact(String fName) {
		for (int i = 0; i < addressBook.size(); i++) {
			if (((addressBook.get(i)).f_Name).equals(fName)) {
				addressBook.remove(i);
			} else {
				System.out.println("Name not in AddressBook");
			}
		}
	}

	public String toString() {
		return nameOfAddressBook;
	}

	public List<Contact> searchByCity(String cityString) {
		List<Contact> con = new ArrayList<Contact>();
		for (Contact c : addressBook) {
			if (c.getCity().equals(cityString)) {
				con.add(c);
			}
		}
		return con;
	}
}
