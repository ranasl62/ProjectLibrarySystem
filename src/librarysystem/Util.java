package librarysystem;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import business.LibraryMember;

public class Util {
	public static final Color DARK_BLUE = Color.BLUE.darker();
	public static final Color ERROR_MESSAGE_COLOR = Color.RED.darker(); // dark red
	public static final Color INFO_MESSAGE_COLOR = new Color(24, 98, 19); // dark green
	public static final Color LINK_AVAILABLE = DARK_BLUE;
	public static final Color LINK_NOT_AVAILABLE = Color.gray;
	// rgb(18, 75, 14)

	public static Font makeSmallFont(Font f) {
		return new Font(f.getName(), f.getStyle(), (f.getSize() - 2));
	}

	public static void adjustLabelFont(JLabel label, Color color, boolean bigger) {
		if (bigger) {
			Font f = new Font(label.getFont().getName(), label.getFont().getStyle(), (label.getFont().getSize() + 2));
			label.setFont(f);
		} else {
			Font f = new Font(label.getFont().getName(), label.getFont().getStyle(), (label.getFont().getSize() - 2));
			label.setFont(f);
		}
		label.setForeground(color);

	}

	/**
	 * Sorts a list of numeric strings in natural number order
	 */
	public static List<String> numericSort(List<String> list) {
		Collections.sort(list, new NumericSortComparator());
		return list;
	}

	static class NumericSortComparator implements Comparator<String> {
		@Override
		public int compare(String s, String t) {
			if (!isNumeric(s) || !isNumeric(t))
				throw new IllegalArgumentException("Input list has non-numeric characters");
			int sInt = Integer.parseInt(s);
			int tInt = Integer.parseInt(t);
			if (sInt < tInt)
				return -1;
			else if (sInt == tInt)
				return 0;
			else
				return 1;
		}
	}

	public static boolean isNumeric(String s) {
		if (s == null)
			return false;
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void centerFrameOnDesktop(Component f) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int height = toolkit.getScreenSize().height;
		int width = toolkit.getScreenSize().width;
		int frameHeight = f.getSize().height;
		int frameWidth = f.getSize().width;
		f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
	}

	public static JScrollPane getTableView(String[] columnNames, Object[][] data) {
		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
		// Create a JTable with the DefaultTableModel
		JTable table = new JTable(tableModel);	
		// Create a JScrollPane to contain the JTable
		JScrollPane scrollPane = new JScrollPane(table);
		return scrollPane;
	}
	
	public static Object[][] memberMap(Set<Entry<String,LibraryMember>> set){
		List<Map.Entry<String, LibraryMember>> members = new ArrayList<>(set);

		Collections.sort(members, new Comparator<Map.Entry<String, LibraryMember>>() {
			@Override
			public int compare(Entry<String, LibraryMember> o1, Entry<String, LibraryMember> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});

		Object[][] object = new Object[members.size()][4];

		int i = 0;
		for (Map.Entry<String, LibraryMember> entry : members) {
			LibraryMember lm = entry.getValue();
			System.out.print(lm);
			object[i][0] = entry.getKey();
			object[i][1] = lm.getFirstName() + " " + lm.getLastName();
			object[i][2] = lm.getTelephone();
			object[i][3] = lm.getAddress().getCity() + " - " + lm.getAddress().getZip();
			i++;
		}
		return object;
	}
}
