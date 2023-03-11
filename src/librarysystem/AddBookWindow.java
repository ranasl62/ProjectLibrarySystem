package librarysystem;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import business.BookException;
import business.CheckoutException;
import business.ControllerInterface;
import business.SystemController;
import business.rulesets.RuleException;
import business.rulesets.RuleSet;
import business.rulesets.RuleSetFactory;

@SuppressWarnings("serial")
public class AddBookWindow extends JFrame implements LibWindow {
	ControllerInterface ci = new SystemController();

	public static final AddBookWindow INSTANCE = new AddBookWindow();

	private boolean isInitialized = false;

	private JPanel panel;
	
	private JTextField isbnField;
	private JTextField titleField;
	private JTextField maxLenField;
	private JTextField copiesField;
	private JTextField authsField;

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
		
		setTitle("Add A New Book");

		// create the fields and labels
		isbnField = createField(10);
		titleField = createField(10);
		maxLenField = createField(10);
		copiesField = createField(20);
		authsField = createField(10);

		JLabel isbn = createLabel("ISBN:");
		JLabel title = createLabel("Title:");
		JLabel maxLen = createLabel("Max Length:");
		JLabel copies = createLabel("Copies:");
		JLabel auths = createLabel("Authors:");

		// add the fields and labels to the panel
		addRow(isbn, isbnField);
		addRow(title, titleField);
		addRow(maxLen, maxLenField);
		addRow(copies, copiesField);
		addRow(auths, authsField);

		// add the button to the last row
		JButton addButton = new JButton("Add");
		JPanel buttonPanel = new JPanel();
		
		JButton backButton = new JButton("Back");
		addBackButtonListener(backButton);
		buttonPanel.add(backButton);
		
		buttonPanel.add(addButton);
		panel.add(new JPanel());
		panel.add(new JPanel());
		panel.add(buttonPanel);
		addBookListener(addButton);

		// add the panel to the frame
		add(panel);

		// set frame properties
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
	
	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.setVisible(true);
		});
	}

	void addBookListener(JButton buttonPanel) {
		buttonPanel.addActionListener(evt -> {
			String isbn = isbnField.getText();
			String title = titleField.getText();
			String maxLen = maxLenField.getText();
			String copies = copiesField.getText();
			String auths = authsField.getText();

			try {
				RuleSet rules = RuleSetFactory.getRuleSet(AddBookWindow.this);
				rules.applyRules(AddBookWindow.this);
				addBook(isbn, title, maxLen, copies, auths);
			} catch (RuleException e) {
				JOptionPane.showMessageDialog(AddBookWindow.this, e.getMessage());
			}
		});
	}
	
	private void addBook(String isbn, String title, String maxLen, String copies, String auths) {
		try {
			ci.addNewBook(isbn, title, Integer.parseInt(maxLen), Integer.parseInt(copies), auths);
			String output = "Book added Successfully!";
			clearFields();
			JOptionPane.showMessageDialog(this, output);
			LibrarySystem.hideAllWindows();
			
			Timer timer = new javax.swing.Timer(500, (ActionListener) new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AddBookWindow.INSTANCE.setVisible(false);
					AllBookIdsWindow.INSTANCE.init();
					AllBookIdsWindow.INSTANCE.setVisible(true);
					Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
					repaint();
					dispose();
					revalidate();
				}
			});
			timer.setRepeats(false);
			timer.start();
			System.out.println(ci.allBookIds());
		} catch (BookException e) {
			JOptionPane.showMessageDialog(AddBookWindow.this, e.getMessage());
		}
	}
	
	public String getIsbn() {
		return isbnField.getText();
	}
	
	public String getTitle() {
		return titleField.getText();
	}
	
	public String getMaxLength() {
		return maxLenField.getText();
	}
	
	public String getCopies() {
		return copiesField.getText();
	}
	
	public String getAuths() {
		return authsField.getText();
	}
	
	private void clearFields() {
		isbnField.setText("");
		titleField.setText("");
		maxLenField.setText("");
		copiesField.setText("");
		authsField.setText("");

	}
}
