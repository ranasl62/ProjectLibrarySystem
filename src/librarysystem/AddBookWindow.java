package librarysystem;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import business.Address;
import business.Author;
import business.Book;
import business.ControllerInterface;
import business.SystemController;
import dataaccess.DataAccessFacade;

public class AddBookWindow extends JFrame implements LibWindow {

	ControllerInterface ci = new SystemController();

	public static final AddBookWindow INSTANCE = new AddBookWindow();

	private boolean isInitialized = false;

	private JPanel panel;

	private JTextField bookTitle;
	private JTextField bookISBN;
	private JTextField authorFirstName;
	private JTextField authorLastName;
	private JTextField authorTelephone;
	private JTextField authorStreet;
	private JTextField authorCity;
	private JTextField authorState;
	private JTextField authorZip;

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	private JTextField messageBar = new JTextField();

	public void clear() {
		messageBar.setText("");
	}

	/* This class is a singleton */
	private AddBookWindow() {

	}

	private JTextField createField(int columns) {
		JTextField field = new JTextField(columns);
		field.setFont(new Font("Arial", Font.PLAIN, 14));
		field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		return field;
	}

	private JLabel createLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("Arial", Font.BOLD, 14));
		return label;
	}

	private void addRow(JLabel label, JTextField textField) {
		panel.add(label);
		panel.add(textField);
		panel.add(new JPanel());
	}

	@Override
	public void init() {
		// create the panel and set its layout
		panel = new JPanel(new GridLayout(0, 3, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// create the fields and labels
		bookTitle = createField(10);
		bookISBN = createField(10);
		authorFirstName = createField(10);
		authorLastName = createField(10);
		authorTelephone = createField(10);
		authorStreet = createField(10);
		authorCity = createField(10);
		authorState = createField(10);
		authorZip = createField(10);

		JLabel bookTitleLabel = createLabel("Book Name:");
		JLabel bookISBNLabel = createLabel("Book ISBN:");
		JLabel authorFirstNameLabel = createLabel("Author First Name:");
		JLabel authorLastNameLabel = createLabel("Author Last Name:");
		JLabel authorTelephoneLabel = createLabel("Author Telephone:");
		JLabel authorStreetLabel = createLabel("Author Street:");
		JLabel authorCityLabel = createLabel("Author City:");
		JLabel authorStateLabel = createLabel("Author State:");
		JLabel authorZipLabel = createLabel("Author Zip:");

		// add the fields and labels to the panel
		addRow(bookTitleLabel, bookTitle);
		addRow(bookISBNLabel, bookISBN);
		addRow(authorFirstNameLabel, authorFirstName);
		addRow(authorLastNameLabel, authorLastName);
		addRow(authorTelephoneLabel, authorTelephone);
		addRow(authorStreetLabel, authorStreet);
		addRow(authorCityLabel, authorCity);
		addRow(authorStateLabel, authorState);
		addRow(authorZipLabel, authorZip);

		// add the button to the last row
		JButton addButton = new JButton("Add");
		JButton backButton = new JButton("Back to book list");
		backButton.addActionListener(e -> {
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.repaint();
			AllBookIdsWindow.INSTANCE.setVisible(true);
		});
		
		addButton.addActionListener(e -> {
			String titleValue = this.bookTitle.getText().trim();
			String isbnValue = this.bookISBN.getText().trim();
			String authorFirstNameValue = this.authorFirstName.getText().trim();
			String authorLastNameValue = this.authorFirstName.getText().trim();
			String authorTelephoneValue = this.authorFirstName.getText().trim();
			String authorStreetValue = this.authorFirstName.getText().trim();
			String authorCityValue = this.authorFirstName.getText().trim();
			String authorStateValue = this.authorFirstName.getText().trim();
			String authorZipValue = this.authorFirstName.getText().trim();
			
			Address address = new Address(
					authorStreetValue,
					authorCityValue,
					authorStateValue,
					authorZipValue
				);
			
			Author author = new Author(
					authorFirstNameValue,
					authorLastNameValue,
					authorTelephoneValue,
					address,
					""
				);
			
			List<Author> authorList = new ArrayList<Author>();
			authorList.add(author);
			
			Book book = new Book(
					isbnValue,
					titleValue,
					30,
					authorList
					);
			
			ci.saveBook(book);
		});
		
		JPanel buttonPanel = new JPanel();
		BorderLayout bl = new BorderLayout();
		bl.setVgap(50);;
		buttonPanel.setLayout(bl);
		buttonPanel.add(addButton, BorderLayout.EAST);
		buttonPanel.add(backButton, BorderLayout.WEST);
		panel.add(new JPanel());
		panel.add(new JPanel());
		panel.add(buttonPanel);

		// add the panel to the frame
		add(panel);

		// set frame properties
		setSize(800, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}
