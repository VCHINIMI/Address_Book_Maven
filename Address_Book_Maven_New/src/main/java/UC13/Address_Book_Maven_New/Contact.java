package UC13.Address_Book_Maven_New;

public class Contact {
	public int id;
	public String f_Name;
	public String l_Name;
	public String address;
	public String city;
	public String state;
	public int zip;
	public int phone_Number;
	public String email;
	public String bookName;
	public String bookType;

	public Contact(int id, String f_Name, String l_Name, String address, String city, String state, int zip, int phone_Number,
			String email, String bookName, String bookType) {
		this.id = id;
		this.f_Name = f_Name;
		this.l_Name = l_Name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone_Number = phone_Number;
		this.email = email;
		this.bookName = bookName;
		this.bookType = bookType;
	}

	public Contact() {

	}

	public String getF_Name() {
		return f_Name;
	}

	public void setF_Name(String f_Name) {
		this.f_Name = f_Name;
	}

	public String getL_Name() {
		return l_Name;
	}

	public void setL_Name(String l_Name) {
		this.l_Name = l_Name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public int getPhone_Number() {
		return phone_Number;
	}

	public void setPhone_Number(int phone_Number) {
		this.phone_Number = phone_Number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Contact(String f_Name, String l_Name, String address, String city, String state, int zip, int phone_Number,
			String email) {
		super();
		this.f_Name = f_Name;
		this.l_Name = l_Name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone_Number = phone_Number;
		this.email = email;
	}

	public String[] convertContactToCsv() {
		String[] csvContact = { f_Name, l_Name, address, city, state, Integer.toString(zip),
				Integer.toString(phone_Number), email };
		return csvContact;
	}

	@Override
	public String toString() {
		return (f_Name + " " + l_Name + " " + address + " " + city + " " + state + " " + zip + " " + phone_Number + " "
				+ email);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((f_Name == null) ? 0 : f_Name.hashCode());
		result = prime * result + ((l_Name == null) ? 0 : l_Name.hashCode());
		result = prime * result + phone_Number;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + zip;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (f_Name == null) {
			if (other.f_Name != null)
				return false;
		} else if (!f_Name.equals(other.f_Name))
			return false;
		if (l_Name == null) {
			if (other.l_Name != null)
				return false;
		} else if (!l_Name.equals(other.l_Name))
			return false;
		if (phone_Number != other.phone_Number)
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (zip != other.zip)
			return false;
		return true;
	}
}
