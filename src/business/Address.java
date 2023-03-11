package business;

import java.io.Serializable;

/* Immutable */
final public class Address implements Serializable {

	private static final long serialVersionUID = -891229800414574888L;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String id;

	public Address(String street, String city, String state, String zip) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public String getID() {
		return this.id;
	}

	@Override
	public String toString() {
		return "(" + id + " " + street + ", " + city + ", " + zip + ")";

	}
}
