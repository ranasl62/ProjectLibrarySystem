package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;

import business.CheckoutException;
import business.ControllerInterface;
import business.LoginException;
import business.SystemController;
import business.rulesets.RuleException;
import business.rulesets.RuleSet;
import business.rulesets.RuleSetFactory;

public class CheckoutABookWindow extends JFrame implements LibWindow {
	public static final CheckoutABookWindow INSTANCE = new CheckoutABookWindow();
	ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;

	public JPanel getMainPanel() {
		return mainPanel;
	}

	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private JTextField isbn;
	private JTextField memberId;

	private CheckoutABookWindow() {
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
		setSize(400, 180);
	}

	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("Checkout A Book");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
	}

	public void defineMiddlePanel() {
		middlePanel = new JPanel();
		middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel leftTextPanel = defineLeftTextPanel();
		JPanel rightTextPanel = defineRightTextPanel();
		middlePanel.add(leftTextPanel);
		middlePanel.add(rightTextPanel);
	}

	public void defineLowerPanel() {
		lowerPanel = new JPanel();

		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new BorderLayout());

		JButton checkoutButton = new JButton("Checkout");
		checkoutButton.setBackground(Color.ORANGE);
		checkoutButton.setForeground(Color.WHITE);
		checkoutButton.setContentAreaFilled(true);
		checkoutButton.setOpaque(true);
		checkoutButton.setBorderPainted(false);
		JPanel containerRightPanel = new JPanel();
		containerRightPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		addCheckoutButtonListener(checkoutButton);
		containerRightPanel.add(checkoutButton);

		JPanel containerLeftPanel = new JPanel();
		containerLeftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JButton backButton = new JButton("< Back to Main");
		backButton.setBackground(Color.LIGHT_GRAY);
		backButton.setForeground(Color.ORANGE);
		backButton.setContentAreaFilled(true);
		backButton.setOpaque(true);
		backButton.setBorderPainted(false);
		addBackButtonListener(backButton);
		containerLeftPanel.add(backButton);

		containerPanel.add(containerLeftPanel, BorderLayout.WEST);
		containerPanel.add(containerRightPanel, BorderLayout.EAST);

		lowerPanel.add(containerPanel);
	}

//    public void setData(String data) {
//        textArea.setText(data);
//    }

	private JPanel defineRightTextPanel() {

		JPanel topText = new JPanel();
		JPanel bottomText = new JPanel();
		topText.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		bottomText.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

		isbn = new JTextField(10);
		JLabel label = new JLabel("ISBN");
		label.setFont(Util.makeSmallFont(label.getFont()));
		topText.add(isbn);
		bottomText.add(label);

		JPanel leftTextPanel = new JPanel();
		leftTextPanel.setLayout(new BorderLayout());
		leftTextPanel.add(topText, BorderLayout.NORTH);
		leftTextPanel.add(bottomText, BorderLayout.CENTER);

		return leftTextPanel;
	}

	private JPanel defineLeftTextPanel() {

		JPanel topText = new JPanel();
		JPanel bottomText = new JPanel();
		topText.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		bottomText.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

		memberId = new JTextField(10);
		JLabel label = new JLabel("Member Id");
		label.setFont(Util.makeSmallFont(label.getFont()));
		topText.add(memberId);
		bottomText.add(label);

		JPanel rightTextPanel = new JPanel();
		rightTextPanel.setLayout(new BorderLayout());
		rightTextPanel.add(topText, BorderLayout.NORTH);
		rightTextPanel.add(bottomText, BorderLayout.CENTER);

		return rightTextPanel;
	}

	private void addCheckoutButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			String isbn = getIsbn();
			String memberId = getMemberId();
			try {
				RuleSet rules = RuleSetFactory.getRuleSet(CheckoutABookWindow.this);
				rules.applyRules(CheckoutABookWindow.this);
				checkout(isbn, memberId);
			} catch (RuleException e) {
				JOptionPane.showMessageDialog(CheckoutABookWindow.this, e.getMessage());
			}
		});
	}

	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			INSTANCE.setVisible(false);
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}

	private void checkout(String memberId, String isbn) {
		try {
			ci.checkoutBook(memberId, isbn);
			String output = "Checkout Successful!";
			clearFields();
			JOptionPane.showMessageDialog(this, output);
		} catch (CheckoutException e) {
			JOptionPane.showMessageDialog(CheckoutABookWindow.this, e.getMessage());
		}
	}

	public String getIsbn() {
		return isbn.getText();
	}

	public String getMemberId() {
		return memberId.getText();
	}

	@Override
	public boolean isInitialized() {

		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;

	}

	private void clearFields() {
		isbn.setText("");
		memberId.setText("");
	}

}
