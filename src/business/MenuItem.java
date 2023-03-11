package business;

import javax.swing.JMenuItem;

import dataaccess.Auth;
import dataaccess.User;

public class MenuItem extends JMenuItem {
	private String itemName;
	private boolean highlight = false;
	private Auth visibleTo;
	private JMenuItem menuItem;

	public MenuItem(String item, Auth visibileTo, boolean defaultHighlist) {
		itemName = item;
		highlight = defaultHighlist;
		this.visibleTo = visibileTo;
		menuItem = new JMenuItem(item);
		menuItem.setEnabled(defaultHighlist);
	}

	@Override
	public boolean equals(Object ob) {
		if (ob.getClass() != MenuItem.class)
			return false;
		MenuItem item = (MenuItem) ob;
		return itemName.equals(item.itemName);
	}

	public String getItemName() {
		return itemName;
	}

	public JMenuItem getItem() {
		return menuItem;
	}

	public boolean highlight() {
		return highlight;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setHighlight(Auth currentAuth) {
		if (visibleTo != null) {
			if (currentAuth != null) {
				this.highlight = (currentAuth == Auth.BOTH || visibleTo == Auth.BOTH) ? true : currentAuth == visibleTo;
			} else {
				this.highlight = false;
			}

			menuItem.setEnabled(this.highlight);
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
