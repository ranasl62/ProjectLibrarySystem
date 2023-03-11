package business.rulesets;

import java.awt.Component;

import librarysystem.AddBookWindow;
import librarysystem.CheckoutABookWindow;

public class BookRuleSet implements RuleSet {
	AddBookWindow ab;

	@Override
	public void applyRules(Component ob) throws RuleException {

		ab = (AddBookWindow) ob;
		nonemptyRule();
		numericMaxLen();
		numericCopies();
	}

	private void nonemptyRule() throws RuleException {
		if (ab.getIsbn().trim().isEmpty() || ab.getTitle().trim().isEmpty() || ab.getMaxLength().trim().isEmpty()
				|| ab.getCopies().trim().isEmpty() || ab.getAuths().trim().isEmpty()) {
			throw new RuleException("All fields must be non-empty!");
		}
	}

	private void numericMaxLen() throws RuleException {
		try {
			Integer.parseInt(ab.getMaxLength().trim());
		}
		catch(NumberFormatException e) {
			throw new RuleException("Max Length must be numeric!");
		}
	}
	
	private void numericCopies() throws RuleException {
		try {
			Integer.parseInt(ab.getCopies().trim());
		}
		catch(NumberFormatException e) {
			throw new RuleException("Copies must be numeric!");
		}
	}
}
