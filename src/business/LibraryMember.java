package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private CheckoutRecord record;

	public LibraryMember(String memberId, String fname, String lname, String tel, Address add) {
		super(fname, lname, tel, add);
		this.memberId = memberId;
		record = new CheckoutRecord();
	}

	public String getMemberId() {
		return memberId;
	}

	public void checkout(BookCopy copy, LocalDate todaysDate, LocalDate todayPlusCheckoutLength)
			throws CheckoutException {
		if (!copy.isAvailable()) {
			throw new CheckoutException("This book is not available!");
		} else {
			copy.changeAvailability();
			CheckoutRecordEntry checkoutEntry = CheckoutRecordEntry.createEntry(copy, todaysDate,
					todayPlusCheckoutLength);
			if (record == null) {
				record = new CheckoutRecord();
			}
			record.addEntry(checkoutEntry);
		}

	}
	
	public List<CheckoutRecordEntry> getEntries() {
		return record.getEntries();
	}

	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + ", "
				+ getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
