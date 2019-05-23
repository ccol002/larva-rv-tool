package larvaplugin.editors;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordPatternRule;

public class LarvaPartitionScanner extends RuleBasedPartitionScanner {

	public static final String SINLGE_LINE_COMMENT = "singleComment";
	public static final String MULTI_LINE_ACTION = "multiComment";
	public static final String WORD = "word";


	
	public static final String[] PARTITION_TYPES = {SINLGE_LINE_COMMENT,MULTI_LINE_ACTION}; 
		//MULTI_LINE_COMMENT2, WORDS, WORDS2};//GLOBAL, FOREACH, STATES, PROPERTY };
	
	public LarvaPartitionScanner()
	{	
		IToken singleLineCommentToken = new Token(LarvaPartitionScanner.SINLGE_LINE_COMMENT);
		IToken multiLineActionToken = new Token(LarvaPartitionScanner.MULTI_LINE_ACTION);
		IToken wordToken = new Token(LarvaPartitionScanner.WORD);

		
	
		IPredicateRule[] rules = new IPredicateRule[2];
		
	//	rules[2] = new LarvaWhitespaceRule(new LarvaWhitespaceDetector()); 
		rules[1] = new EndOfLineRule("%%",singleLineCommentToken); 
	//	rules[1] = new WordPatternRule(new LarvaWordIdentifier(),"","",wordToken);
		rules[0] = new MultiLineRule("[","]",multiLineActionToken);
	//	rules[2] = new MultiLineRule("\"","\"",wordToken);
		//rules[2] = new PWordRule2(wordToken); 
//		rules[5] = new MultiLineRule("PROPERTY","{",propToken); 
//		rules[4] = new MultiLineRule("GLOBAL","{",globToken); 
//		rules[3] = new MultiLineRule("FOREACH","{",foreachToken); 
//		rules[2] = new MultiLineRule("STATES","{",statesToken); 
		
		setPredicateRules(rules);
	}
	
	
	
	
	
}
