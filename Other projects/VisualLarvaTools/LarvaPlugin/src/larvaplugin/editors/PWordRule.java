package larvaplugin.editors;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.WordRule;

public class PWordRule extends WordRule implements IPredicateRule {
	
	public static String[] larvaKeywords = new String[] {"Global", "Foreach", "Events",
		"Property", "States", "Starting", "Bad", "Accept", "Normal", "Imports",
		"Transitions", "Methods", "->", "Variables", "Initially", "Initializeif", "where", "filter" };
	
		
	private IToken fSuccessToken;
	/**
	 * Constructor for EmptyCommentRule.
	 * @param defaultToken
	 */
	public PWordRule(IToken successToken) {
		super(new LarvaWordIdentifier());
		fSuccessToken= successToken;
		for (String s : larvaKeywords)
		{
			addWord(s, successToken);
			addWord(s.toUpperCase(), successToken);
		}
	}
	
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		return evaluate(scanner);
	}


	public IToken getSuccessToken() {
		return fSuccessToken;
	}
};