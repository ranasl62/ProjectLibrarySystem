package business;

import java.util.HashMap;
import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;

	public void checkoutBook(String memberId, String isbn) throws CheckoutException;

	public List<String> allMemberIds();

	public List<String> allBookIds();

	public HashMap<String, LibraryMember> readMemberMap();
	
	public void saveBook(Book book);

	public void saveMember(LibraryMember libraryMember) throws AddMemberWindowException;

	public LibraryMember readMember(String id);

	public Book readBook(String id);

}
