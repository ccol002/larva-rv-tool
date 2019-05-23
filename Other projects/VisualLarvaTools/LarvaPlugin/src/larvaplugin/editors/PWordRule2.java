package larvaplugin.editors;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.WordRule;

public class PWordRule2 extends WordRule implements IPredicateRule {
	
	public static String[] javaKeywords = new String[] {"if", "for", "else",
		"int", "String", "Integer", "Double", "double", "boolean", "Clock", "Channel", "try",
		"catch", "long", "import", "true", "false", "Object"};
	
	
	private IToken fSuccessToken;
	/**
	 * Constructor for EmptyCommentRule.
	 * @param defaultToken
	 */
	public PWordRule2(IToken successToken) {
		super(new LarvaWordIdentifier());
		fSuccessToken= successToken;
		for (String s : javaKeywords)
			addWord(s, successToken);
	}
	
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		return evaluate(scanner);
	}


	public IToken getSuccessToken() {
		return fSuccessToken;
	}
};