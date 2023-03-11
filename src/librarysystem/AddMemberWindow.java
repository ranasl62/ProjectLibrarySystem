package librarysystem;

import java.awt.BorderLayout;
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

import business.AddMemberWindowException;
import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.LoginException;
import business.SystemController;
import business.rulesets.RuleException;
import business.rulesets.RuleSet;
import business.rulesets.RuleSetFactory;

@SuppressWarnings("serial")
public class AddMemberWindow extends JFrame implements LibWindow {
	ControllerInterface ci = new SystemController();

	public static final AddMemberWindow INSTANCE = new AddMemberWindow();

	private boolean isInitialized = false;

	private JPanel panel;

	private JTextField memberIdField;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField streetField;
	private JTextField cityField;
	private JTextField stateField;
	private JTextField zipField;
	private JTextField telephoneField;

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
	private AddMemberWindow() {

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
		memberIdField = createField(10);
		firstNameField = createField(10);
		lastNameField = createField(10);
		streetField = createField(20);
		cityField = createField(10);
		stateField = createField(10);
		zipField = createField(10);
		telephoneField = createField(10);

		JLabel memberIdLabel = createLabel("Member ID:");
		JLabel firstNameLabel = createLabel("First Name:");
		JLabel lastNameLabel = createLabel("Last Name:");
		JLabel streetLabel = createLabel("Street:");
		JLabel cityLabel = createLabel("City:");
		JLabel stateLabel = createLabel("State:");
		JLabel zipLabel = createLabel("Zip:");
		JLabel telephoneLabel = createLabel("Telephone:");

		setTitle("Add Member");

		// add the fields and labels to the panel
		addRow(memberIdLabel, memberIdField);
		addRow(firstNameLabel, firstNameField);
		addRow(lastNameLabel, lastNameField);
		addRow(streetLabel, streetField);
		addRow(cityLabel, cityField);
		addRow(stateLabel, stateField);
		addRow(zipLabel, zipField);
		addRow(telephoneLabel, telephoneField);

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
		addMemberListener(addButton);

		// add the panel to the frame
		add(panel);

		// set frame properties
		setSize(600, 480);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			AllMemberIdsWindow.INSTANCE.setVisible(true);
		});
	}

	void addMemberListener(JButton buttonPanel) {
		buttonPanel.addActionListener(evt -> {
			try {
				RuleSet rules = RuleSetFactory.getRuleSet(AddMemberWindow.this);
				rules.applyRules(AddMemberWindow.this);
				
				LibrarySystem.hideAllWindows();
				AllMemberIdsWindow.INSTANCE.init();
				Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
				AllMemberIdsWindow.INSTANCE.setVisible(true);

				Address address = new Address(stateField.getText(), cityField.getText(), streetField.getText(),
						zipField.getText());

				LibraryMember libraryMember = new LibraryMember(memberIdField.getText(), firstNameField.getText(),
						lastNameField.getText(), telephoneField.getText(), address);
				try {
					ci.saveMember(libraryMember);
				}
				catch(AddMemberWindowException e) {
					throw new RuleException(e.getMessage());
				}
				String output = "Member add successfully";
				clearFields();
				JOptionPane.showMessageDialog(AllMemberIdsWindow.INSTANCE, output);
				Timer timer = new javax.swing.Timer(500, (ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AddMemberWindow.INSTANCE.setVisible(false);
						dispose();
						revalidate();
					}
				});
				timer.setRepeats(false);
				timer.start();
				AllMemberIdsWindow.INSTANCE.setVisible(true);
				AllMemberIdsWindow.INSTANCE.setData(Util.memberMap(ci.readMemberMap().entrySet()));
			} catch (RuleException e) {
				JOptionPane.showMessageDialog(AddMemberWindow.this, e.getMessage());
			}

		});

	}

	private void clearFields() {
		memberIdField.setText("");
		firstNameField.setText("");
		lastNameField.setText("");
		streetField.setText("");
		cityField.setText("");
		stateField.setText("");
		zipField.setText("");
		telephoneField.setText("");

	}

	public String getMemberIdFieldText() {
		return memberIdField.getText();
	}

	public String getFirstNameFieldText() {
		return firstNameField.getText();
	}

	public String getLastNameFieldText() {
		return lastNameField.getText();
	}

	public String getStreetFieldText() {
		return streetField.getText();
	}

	public String getCityFieldText() {
		return cityField.getText();
	}

	public String getStateFieldText() {
		return stateField.getText();
	}

	public String getZipFieldText() {
		return zipField.getText();
	}

	public String getTelephoneFieldText() {
		return telephoneField.getText();
	}

}
