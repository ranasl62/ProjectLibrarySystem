package business.rulesets;

import java.awt.Component;

import librarysystem.LoginWindow;

public class LoginRuleSet implements RuleSet {
	LoginWindow lw;

	@Override
	public void applyRules(Component ob) throws RuleException {

		lw = (LoginWindow) ob;
		nonemptyRule();

	}

	private void nonemptyRule() throws RuleException {
		if (lw.getUsername().trim().isEmpty() || lw.getPassword().trim().isEmpty()) {
			throw new RuleException("All fields must be non-empty!");
		}
	}

}
