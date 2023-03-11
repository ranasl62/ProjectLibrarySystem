package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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

import business.ControllerInterface;
import business.SystemController;

public class AllMemberIdsWindow extends JFrame implements LibWindow {
	/**
	 * default version
	 */
	private static final long serialVersionUID = 1L;
	public static final AllMemberIdsWindow INSTANCE = new AllMemberIdsWindow();
	ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;

	public JPanel getMainPanel() {
		return mainPanel;
	}

	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private DefaultTableModel tableModel;
	private JTable table;

	private AllMemberIdsWindow() {
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
	}

	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("All Member IDs");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
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

	}

	public void defineLowerPanel() {
		lowerPanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		lowerPanel.setLayout(fl);
		JButton backButton = new JButton("<= Back");
		addBackButtonListener(backButton);
		lowerPanel.add(backButton);
	}

	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
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

	public void setData(Object[][] data) {
		// Clear existing data from the table model
		this.tableModel.setRowCount(0);
		this.tableModel.setColumnCount(0);

		// Add columns to the table model
		Object[] columnNames = { "ID", "Name", "Phone", "Address", "Action" };
		for (Object columnName : columnNames) {
			this.tableModel.addColumn(columnName);
		}

		// Add rows to the table model
		for (Object[] row : data) {
			// Create a button for each row
			JButton viewButton = new JButton("View");
			viewButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Handle button click event
					// You can use the `row` variable to access the data for this row
					System.out.println("View button clicked for row: " + Arrays.toString(row));
				}
			});

			// Add the button to the last column of this row
			Object[] rowWithButton = Arrays.copyOf(row, row.length + 1);
			rowWithButton[rowWithButton.length - 1] = viewButton;

			// Add the row to the table model
			this.tableModel.addRow(rowWithButton);
		}

		TableColumn actionColumn = this.table.getColumnModel().getColumn(4);
		actionColumn.setCellRenderer(new ButtonRenderer());
		actionColumn.setCellEditor(new ButtonEditor(new JCheckBox()));
	}

	/**
	 * @version 1.0 11/09/98
	 */

	class ButtonRenderer extends JButton implements TableCellRenderer {

		private static final long serialVersionUID = 1L;

		public ButtonRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			System.out.print(value);
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

	/**
	 * @version 1.0 11/09/98
	 */

	class ButtonEditor extends DefaultCellEditor {
		protected JButton button;

		private String label;

		private boolean isPushed;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
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
			if (isPushed) {
				//
				//
				JOptionPane.showMessageDialog(button, label + ": Ouch!");
				// System.out.println(label + ": Ouch!");
			}
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

}
