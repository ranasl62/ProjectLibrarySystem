package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class CheckoutRecord implements Serializable {
	private List<CheckoutRecordEntry> entries;

	public CheckoutRecord() {
		this.entries = new ArrayList<>();
	}

	public List<CheckoutRecordEntry> getEntries() {
		return entries;
	}

	public void addEntry(CheckoutRecordEntry entry) {
		if (entry != null) {
			entries.add(entry);
		}
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
