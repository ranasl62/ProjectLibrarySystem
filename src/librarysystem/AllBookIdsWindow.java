package librarysystem;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import business.Author;
import business.Book;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataaccess.Auth;
import librarysystem.AllMemberIdsWindow.ButtonEditor;
import librarysystem.AllMemberIdsWindow.ButtonRenderer;

public class AllBookIdsWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static final AllBookIdsWindow INSTANCE = new AllBookIdsWindow();
	ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;

	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private TextArea textArea;

	private DefaultTableModel tableModel;
	private JTable table;

	// Singleton class
	private AllBookIdsWindow() {
	}

	public void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTopPanel();
		defineMiddlePanel();
		defineLowerPanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized = true;
		setData();
	}

	public void defineTopPanel() {

		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("Book List");
		Font labelFont = AllIDsLabel.getFont();
		AllIDsLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 18));

		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);

		topPanel.setLayout(new BorderLayout());

		JPanel label = new JPanel();

		label.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		label.add(AllIDsLabel);
		topPanel.add(label, BorderLayout.NORTH);

		JButton addMemberButton = new JButton("Add Book");
		if (SystemController.currentAuth == null || SystemController.currentAuth == Auth.LIBRARIAN) {
			addMemberButton.setEnabled(false);
		}

		JPanel buttonPanel = new JPanel();

		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 25, 0));
		buttonPanel.add(addMemberButton);

		addBookButtonListener(addMemberButton);
		topPanel.add(buttonPanel, BorderLayout.SOUTH);

	}

	public void defineMiddlePanel() {

		middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
		middlePanel.setLayout(fl);
		this.tableModel = new DefaultTableModel();

		// Create a JTable with the DefaultTableModel
		this.table = new JTable(tableModel);
		table.setDefaultEditor(Object.class, null);
		// Create a JScrollPane to contain the JTable
		JScrollPane scrollPane = new JScrollPane(table);

		middlePanel.add(scrollPane);

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
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}

	public void addBookButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			AddBookWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(AddMemberWindow.INSTANCE);
			AddBookWindow.INSTANCE.setVisible(true);
		});
	}

	class BackToMainListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);

		}
	}

	public void setData() {
		// Clear existing data from the table model
		this.tableModel.setRowCount(0);
		this.tableModel.setColumnCount(0);

		// Add columns to the table model
		Object[] columnNames = { "ISBN", "Title", "Authors", "Action" };
		for (Object columnName : columnNames) {
			this.tableModel.addColumn(columnName);
		}

		List<Map.Entry<String, Book>> books = new ArrayList<>(ci.readBooksMap().entrySet());

		Collections.sort(books, new Comparator<Map.Entry<String, Book>>() {
			@Override
			public int compare(Entry<String, Book> o1, Entry<String, Book> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});

		Object[][] object = new Object[books.size()][4];

		int i = 0;
		for (Map.Entry<String, Book> entry : books) {
			Book book = entry.getValue();
			object[i][0] = entry.getKey();
			object[i][1] = book.getTitle();

			StringBuilder sb = new StringBuilder();
			for (Author auth : book.getAuthors()) {
				sb.append(auth.getBio() + " ");
			}

			object[i][2] = sb.toString();

			i++;
		}

		// Add rows to the table model
		for (Object[] row : object) {

			// Add the button to the last column of this row
			Object[] rowWithButton = Arrays.copyOf(row, row.length + 1);
			rowWithButton[rowWithButton.length - 1] = "View";

			// Add the row to the table model
			this.tableModel.addRow(rowWithButton);
		}

		TableColumn actionColumn = this.table.getColumnModel().getColumn(3);
		actionColumn.setCellRenderer(new ButtonRenderer());
		actionColumn.setCellEditor(new ButtonEditor(new JCheckBox()));

	}

	class ButtonRenderer extends JButton implements TableCellRenderer {

		private static final long serialVersionUID = 1L;

		public ButtonRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText("View");
			return this;
		}
	}

	class ButtonEditor extends DefaultCellEditor {
		protected JButton button;

		private String label;

		private boolean isPushed;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = table.getSelectedRow();
					if (row >= 0) {
						String id = (String) table.getValueAt(row, 0);
						LibrarySystem.hideAllWindows();
						BookHistoryWindow.INSTANCE.setId(id);
						BookHistoryWindow.INSTANCE.init();
						Util.centerFrameOnDesktop(BookHistoryWindow.INSTANCE);
						BookHistoryWindow.INSTANCE.setVisible(true);
					}
					fireEditingStopped();
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
		}

		public Object getCellEditorValue() {
			isPushed = false;
			return new String(label);
		}

		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}

//	private void populateTextArea() {
//		//populate
//		List<String> ids = ci.allBookIds();
//		Collections.sort(ids);
//		StringBuilder sb = new StringBuilder();
//		for(String s: ids) {
//			sb.append(s + "\n");
//		}
//		textArea.setText(sb.toString());
//	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;

	}
}
