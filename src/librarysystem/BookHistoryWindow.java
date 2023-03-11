package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import business.Book;
import business.BookCopy;
import business.CheckoutRecordEntry;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataaccess.Auth;

public class BookHistoryWindow extends JFrame implements LibWindow {
	/**
	 * default version
	 */
	private static final long serialVersionUID = 1L;
	public static final BookHistoryWindow INSTANCE = new BookHistoryWindow();
	ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;

	public JPanel getMainPanel() {
		return mainPanel;
	}

	private JPanel mainPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private DefaultTableModel tableModel;
	private JTable table;
	private Book book;
	private Collection<LibraryMember> libraryMember;

	private BookHistoryWindow() {
	}

	public void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTopPanel();
		defineMiddlePanel();
		defineLowerPanel();
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized = true;
		setSize(600, 540);
	}

	public void setId(String id) {
		this.book = ci.readBook(id);
		this.libraryMember = ci.readMemberMap().values();
	}

	public void defineTopPanel() {
		if (this.book == null)
			return;
		setTitle(this.book.getTitle() + "( " + this.book.getIsbn() + " )");

	}

	public void defineMiddlePanel() {

		middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
		middlePanel.setLayout(fl);
		this.tableModel = new DefaultTableModel();

		// Create a JTable with the DefaultTableModel
		this.table = new JTable(tableModel);

		// Create a JScrollPane to contain the JTable
		JScrollPane scrollPane = new JScrollPane(table);

		middlePanel.add(scrollPane);
		setData();

	}

	public void defineLowerPanel() {
		lowerPanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		lowerPanel.setLayout(fl);
		JButton backButton = new JButton("Back");
		addBackButtonListener(backButton);
		lowerPanel.add(backButton);
	}

	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.setVisible(true);
		});
	}

	@Override
	public boolean isInitialized() {

		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;

	}

	public void setData() {
		// Clear existing data from the table model
		this.tableModel.setRowCount(0);
		this.tableModel.setColumnCount(0);
		// Add columns to the table model
		Object[] columnNames = { "Member", "Copy No.", "Checkout Date", "Due Date", "Expire" };
		for (Object columnName : columnNames) {
			this.tableModel.addColumn(columnName);
		}

		for (LibraryMember libMem : this.libraryMember) {

			try {
				int i = 0;
				for (CheckoutRecordEntry cre : libMem.getEntries()) {
					try {
						if (this.book.getIsbn().equals(cre.getBookCopy().getBook().getIsbn())
								&& !cre.getBookCopy().isAvailable()) {
							Object[] obj = { libMem.getFirstName() + " " + libMem.getLastName(),
									cre.getBookCopy().getCopyNum(), cre.getCheckoutDate(), cre.getDueDate(), cre.getDueDate().isBefore(LocalDate.now())? "Yes" :"No" };
							this.tableModel.addRow(obj);

						}
					} catch (Exception e) {

					}
				}
			} catch (Exception e) {

			}
		}
	}


	public void updateList() {
		// TODO Auto-generated method stub

	}

}
