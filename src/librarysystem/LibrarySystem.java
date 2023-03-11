package librarysystem;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import business.ControllerInterface;
import business.LibraryMember;
import business.MenuItem;
import business.SystemController;
import dataaccess.Auth;

public class LibrarySystem extends JFrame implements LibWindow {
	ControllerInterface ci = new SystemController();
	public final static LibrarySystem INSTANCE = new LibrarySystem();
	JPanel mainPanel;
	JMenuBar menuBar;
	JMenu options;
	MenuItem login, allBookIds, allMemberIds, checkoutABook;
	String pathToImage;
	private boolean isInitialized = false;

	private static LibWindow[] allWindows = { LibrarySystem.INSTANCE, LoginWindow.INSTANCE, AllMemberIdsWindow.INSTANCE,
<<<<<<< HEAD
			AllBookIdsWindow.INSTANCE, AddMemberWindow.INSTANCE, MemberCheckoutHistoryWindow.INSTANCE };
=======
			AllBookIdsWindow.INSTANCE, AddMemberWindow.INSTANCE, AddBookWindow.INSTANCE };
>>>>>>> 8d980e3d39440ae2852691041370c5ddb8f2fd6a

	public static void hideAllWindows() {
		for (LibWindow frame : allWindows) {
			frame.setVisible(false);
		}
	}

	private LibrarySystem() {
	}

	public void init() {
		formatContentPane();
		setPathToImage();
		insertSplashImage();

		createMenus();
		// pack();
		setSize(660, 500);
		isInitialized = true;
	}

	private void formatContentPane() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));
		getContentPane().add(mainPanel);
	}

	private void setPathToImage() {
		String currDirectory = System.getProperty("user.dir");
		// for Windows file system
//    	pathToImage = currDirectory+"\\src\\librarysystem\\library.jpg";
		// for unix file system
		pathToImage = currDirectory + "/src/librarysystem/library.jpg";

	}

	private void insertSplashImage() {
		ImageIcon image = new ImageIcon(pathToImage);
		mainPanel.add(new JLabel(image));
	}

	private void createMenus() {
		menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
		addMenuItems();
		setJMenuBar(menuBar);
	}

	private void addMenuItems() {
		options = new JMenu("Options");
		menuBar.add(options);
		login = new MenuItem("Login", null, true); // null -> visible for all
		login.getItem().addActionListener(new LoginListener());
		allBookIds = new MenuItem("All Book Ids", Auth.BOTH, true);
		allBookIds.getItem().addActionListener(new AllBookIdsListener());
		allMemberIds = new MenuItem("All Member Ids", Auth.BOTH, true);
		allMemberIds.getItem().addActionListener(new AllMemberIdsListener());
		checkoutABook = new MenuItem("Checkout a Book", Auth.LIBRARIAN, false);
		checkoutABook.getItem().addActionListener(new CheckoutABookListener());
		options.add(login.getItem());
		options.add(allBookIds.getItem());
		options.add(allMemberIds.getItem());
		options.add(checkoutABook.getItem());
	}

	class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			LoginWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
			LoginWindow.INSTANCE.setVisible(true);

		}

	}

	class AllBookIdsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();

			List<String> ids = ci.allBookIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for (String s : ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllBookIdsWindow.INSTANCE.setData(sb.toString());
			AllBookIdsWindow.INSTANCE.pack();
			// AllBookIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
			AllBookIdsWindow.INSTANCE.setVisible(true);

		}

	}

	class AllMemberIdsListener implements ActionListener {

		@SuppressWarnings("null")
		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllMemberIdsWindow.INSTANCE.init();
			AllMemberIdsWindow.INSTANCE.pack();
			AllMemberIdsWindow.INSTANCE.setVisible(true);

			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();

			AllMemberIdsWindow.INSTANCE.setData(Util.memberMap(ci.readMemberMap().entrySet()));
			AllMemberIdsWindow.INSTANCE.pack();
			// AllMemberIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
			AllMemberIdsWindow.INSTANCE.setVisible(true);

		}

	}

	class CheckoutABookListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			CheckoutABookWindow.INSTANCE.init();
//			CheckoutABookWindow.INSTANCE.pack();
			Util.centerFrameOnDesktop(CheckoutABookWindow.INSTANCE);
			CheckoutABookWindow.INSTANCE.setVisible(true);

		}

	}

	void updateList() {
		login.setHighlight(SystemController.currentAuth);
		allBookIds.setHighlight(SystemController.currentAuth);
		allMemberIds.setHighlight(SystemController.currentAuth);
		checkoutABook.setHighlight(SystemController.currentAuth);
		repaint();
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;

	}

}
