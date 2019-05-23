package CE.F;

import java.util.ArrayList;

import CE.P.P;
import CEform.CEform;
import parsing.ParseException;
import parsing.ParsingString;
import parsing.Token;
import parsing.Tokenizer;

public class Synch extends F{
	P lhs;
	P rhs;
	Object value;
		
	public Synch()
	{}
	
	//starting from the operator
	//synch(P,Q,t)
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("synch"))
			cnt++;
		else throw new ParseException("synch Expected: " + Tokenizer.debugShow(tokens, cnt));

		int brackets = 0;
		while (tokens.get(cnt).is("("))
		{
			brackets++;
			cnt++;
		}
		
		lhs = new P();
		cnt = lhs.parse(tokens, cnt);
		
		if (tokens.get(cnt).is(","))
			cnt++;
		else throw new ParseException(", expected: " + Tokenizer.debugShow(tokens, cnt));
		
		rhs = new P();
		cnt = rhs.parse(tokens, cnt);
		
		if (tokens.get(cnt).is(","))
			cnt++;
		else throw new ParseException(", expected: " + Tokenizer.debugShow(tokens, cnt));
		
		value = tokens.get(cnt).getNumber();
		cnt++;
		
		while (brackets > 0 && tokens.get(cnt).is(")"))
		{
			brackets--;
			cnt++;
		}
		if (brackets != 0) throw new ParseException(") Expected: " + Tokenizer.debugShow(tokens, cnt));
		
		return cnt;
	}
	
	public String toString()
	{
		return "synchronization (" + lhs + "," + rhs + "," + value + ")";
	}
	
	public CEform getCEFormula()throws ParseException
	{
		CEform ceform = new CEform();
		String string = "not (true;([" + lhs + " and " + rhs + "] and len >= " + value + ");[" + lhs + "];true)";
		ceform.parse(new ParsingString(new StringBuilder(string)));
		return ceform;
	}
	
	
}
