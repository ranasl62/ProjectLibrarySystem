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

public class MemberCheckoutHistoryWindow extends JFrame implements LibWindow {
	/**
	 * default version
	 */
	private static final long serialVersionUID = 1L;
	public static final MemberCheckoutHistoryWindow INSTANCE = new MemberCheckoutHistoryWindow();
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
	private LibraryMember libraryMember;

	private MemberCheckoutHistoryWindow() {
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
		this.libraryMember = ci.readMember(id);
	}

	public void defineTopPanel() {
		if (this.libraryMember == null)
			return;
		setTitle("Checkout History of " + this.libraryMember.getFirstName() + " " + this.libraryMember.getFirstName());

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
			AllMemberIdsWindow.INSTANCE.setVisible(true);
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
		System.out.print(libraryMember);
		// Add columns to the table model
		Object[] columnNames = { "ISBN", "Title", "Copy No.", "Checkout Date", "Due Date" };
		for (Object columnName : columnNames) {
			this.tableModel.addColumn(columnName);
		}

		for (CheckoutRecordEntry checkoutHistory : this.libraryMember.getEntries()) {
			Object[] obj = { checkoutHistory.getBookCopy().getBook().getIsbn(),
					checkoutHistory.getBookCopy().getBook().getTitle(),
					checkoutHistory.getBookCopy().getCopyNum(), checkoutHistory.getCheckoutDate(),
					checkoutHistory.getDueDate() };
			this.tableModel.addRow(obj);
		}
	}

	public void updateList() {
		// TODO Auto-generated method stub

	}

}
