package newCEform.phase;

import java.util.ArrayList;

import newCEform.P.P;
import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class Interval extends Phase{
	
	P p;
		
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("["))
			cnt++;
		else throw new ParseException("[ expected: " + Tokenizer.debugShow(tokens, cnt));
		p = new P();
		cnt = p.parse(tokens, cnt);
		
		if (tokens.get(cnt).is("]"))
				cnt++;
			else
				throw new ParseException("] Expected: " + Tokenizer.debugShow(tokens, cnt));
			
		
		return cnt;
	}
	
	public String toString()
	{
		return "[" + p + "]";
	}
	
}
