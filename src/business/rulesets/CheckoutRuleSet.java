package business.rulesets;

import java.awt.Component;

import librarysystem.CheckoutABookWindow;

public class CheckoutRuleSet implements RuleSet {
	CheckoutABookWindow cw;

	@Override
	public void applyRules(Component ob) throws RuleException {

		cw = (CheckoutABookWindow) ob;
		nonemptyRule();

	}

	private void nonemptyRule() throws RuleException {
		if (cw.getIsbn().trim().isEmpty() || cw.getMemberId().trim().isEmpty()) {
			throw new RuleException("All fields must be non-empty!");
		}
	}

}
