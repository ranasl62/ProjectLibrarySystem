package business;

import java.io.Serializable;
import java.time.LocalDate;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class CheckoutRecordEntry implements Serializable {
	private BookCopy copy;
	private LocalDate checkoutDate;
	private LocalDate dueDate;

	public CheckoutRecordEntry(BookCopy copy, LocalDate checkoutDate, LocalDate dueDate) {
		this.copy = copy;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}
	
	public BookCopy getBookCopy() {
		return copy;
	}
	
	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void checkout(BookCopy copy, LocalDate todaysDate, LocalDate todayPlusCheckoutLength)
			throws CheckoutException {
		if (!copy.isAvailable()) {
			throw new CheckoutException("This book is not available!");
		} else {
			copy.changeAvailability();
		}

	}

	public static CheckoutRecordEntry createEntry(BookCopy copy, LocalDate todaysDate, LocalDate todayPlusCheckoutLength) {
		return new CheckoutRecordEntry(copy, todaysDate, todayPlusCheckoutLength);
	}

	@Override
	public String toString() {
		return "Checkout Info: " + "Book Copy: " + copy.getCopyNum() + ", Checkout Date: " + checkoutDate.toString()
				+ ", " + "Due Date: " + dueDate.toString();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
