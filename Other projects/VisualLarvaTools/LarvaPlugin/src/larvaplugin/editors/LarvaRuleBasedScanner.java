package larvaplugin.editors;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;


public class LarvaRuleBasedScanner extends RuleBasedScanner 
{		
	
	//public static Font boldFont = new Font("Courier New", Font.BOLD, 12);
	public static IToken blueBold;
	public static IToken purple;
		
	static{
		TextAttribute ta = new TextAttribute(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, SWT.BOLD);
		
		blueBold = new Token(ta);
		purple = new Token(new TextAttribute(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_MAGENTA)));
	}
	
	
	public LarvaRuleBasedScanner() 
	{
		IToken green = new Token(new TextAttribute(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN)));
		IToken darkBlue = new Token(new TextAttribute(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE)));
		IToken blue = new Token(new TextAttribute(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE)));
		IToken cyan = new Token(new TextAttribute(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_CYAN)));
		IToken red = new Token(new TextAttribute(Display.getCurrent().getSystemColor(SWT.COLOR_RED)));
		IToken darkRed = new Token(new TextAttribute(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED)));
		
		IRule commentRule = new EndOfLineRule("%%",green); 
		IRule multiLineRule = new MultiLineRule("[","]",purple); 
		IRule quotesRule = new SingleLineRule("\"","\"",red); 
				
		
//		IRule prop = new MultiLineRule("PROPERTY","{",blue); 
//		IRule glob = new MultiLineRule("GLOBAL","{",red); 
//		IRule states = new MultiLineRule("STATES","{",blue); 
//		IRule foreach = new MultiLineRule("FOREACH","{",red); 
		
		WordRule words = new PWordRule(blueBold);
		WordRule words2 = new PWordRule2(blue);
		IRule white = new WhitespaceRule(new LarvaWhitespaceDetector());
		
		IRule[] rules = new IRule[] {commentRule,multiLineRule, quotesRule, words, words2, white};
		setRules(rules);
	}
}
