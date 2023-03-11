package business.rulesets;

import java.awt.Component;

import java.util.HashMap;

import librarysystem.AddMemberWindow;
import librarysystem.CheckoutABookWindow;
import librarysystem.LoginWindow;


final public class RuleSetFactory {
	private RuleSetFactory(){}
	static HashMap<Class<? extends Component>, RuleSet> map = new HashMap<>();
	static {
		map.put(LoginWindow.class, new LoginRuleSet());
		map.put(AddMemberWindow.class, new AddMemberRuleSet());
		map.put(CheckoutABookWindow.class, new CheckoutRuleSet());
	}
	public static RuleSet getRuleSet(Component c) {
		Class<? extends Component> cl = c.getClass();
		if(!map.containsKey(cl)) {
			throw new IllegalArgumentException(
					"No RuleSet found for this Component");
		}
		return map.get(cl);
	}
}
