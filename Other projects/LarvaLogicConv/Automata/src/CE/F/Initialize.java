package CE.F;

import java.util.ArrayList;

import CE.P.P;
import CEform.CEform;

import parsing.ParseException;
import parsing.ParsingString;
import parsing.Token;
import parsing.Tokenizer;

public class Initialize extends F{
	P p;
		
	//init(P)
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("init"))
			cnt++;
		else throw new ParseException("init expected: " + Tokenizer.debugShow(tokens, cnt));
		
		if (tokens.get(cnt).is("("))
			cnt++;
		else throw new ParseException("( expected: " + Tokenizer.debugShow(tokens, cnt));
		
		
		p = new P();
		cnt = p.parse(tokens, cnt);
		
		if (tokens.get(cnt).is(")"))
				cnt++;
			else
				throw new ParseException(") Expected: " + Tokenizer.debugShow(tokens, cnt));
			
		return cnt;
	}
	
	public CEform getCEFormula()throws ParseException
	{
		CEform ceform = new CEform();
		String string = "not ([not "+p.toString()+"];true)";
		ceform.parse(new ParsingString(new StringBuilder(string)));
		return ceform;
	}
	
	public String toString()
	{
		return "initialize (" + p + ")";
	}
	
}
