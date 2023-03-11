package business.rulesets;

import java.awt.Component;

import librarysystem.AddMemberWindow;
import librarysystem.LoginWindow;

public class AddMemberRuleSet implements RuleSet {
	AddMemberWindow amw;

	@Override
	public void applyRules(Component ob) throws RuleException {

		amw = (AddMemberWindow) ob;
		nonemptyRule();

	}

	private void nonemptyRule() throws RuleException {
		if (amw.getMemberIdFieldText().trim().isEmpty() || amw.getFirstNameFieldText().trim().isEmpty()
				|| amw.getLastNameFieldText().trim().isEmpty() || amw.getStreetFieldText().trim().isEmpty()
				|| amw.getCityFieldText().trim().isEmpty() || amw.getStateFieldText().trim().isEmpty()
				|| amw.getZipFieldText().trim().isEmpty() || amw.getTelephoneFieldText().trim().isEmpty()) {
			throw new RuleException("All fields must be non-empty!");
		}
	}

}
