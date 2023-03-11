package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;

	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		System.out.println("All users: " + map);
		if (!map.containsKey(id)) {
			throw new LoginException("Username " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();

		if (!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
	}

	public void checkoutBook(String isbn, String memberId) throws CheckoutException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> memberMap = da.readMemberMap();
		System.out.println("All members: " + memberMap);
		if (!memberMap.containsKey(memberId)) {
			throw new CheckoutException("Member Id " + memberId + " not found");
		}
		
		LibraryMember member = memberMap.get(memberId);

		HashMap<String, Book> bookMap = da.readBooksMap();
		System.out.println("All Books: " + bookMap);
		if (!bookMap.containsKey(isbn)) {
			throw new CheckoutException("This book is not found!");
		} else {
			Book matchedBook = bookMap.get(isbn);
			if (!matchedBook.isAvailable()) {
				throw new CheckoutException("This book is not available!");
			} else {
				try {
					BookCopy copy = matchedBook.getNextAvailableCopy();
					int maxLength = matchedBook.getMaxCheckoutLength();

					member.checkout(copy, LocalDate.now(), LocalDate.now().plusDays(maxLength));
					da.saveMember(member); // actually updating member
					da.saveBook(matchedBook);
				} catch (CheckoutException e) {
					throw new CheckoutException(e.getMessage());
				}
			}
		}
	}

	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

}
