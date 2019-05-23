package CE.F;

import java.util.ArrayList;

import CE.P.P;
import CEform.CEform;

import parsing.ParseException;
import parsing.ParsingString;
import parsing.Token;
import parsing.Tokenizer;

public class Stable extends F{
	
	P p;
	P q;
	P p1;
	Object value;
	
	//stab(P,Q,P1,t)
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("stab"))
			cnt++;
		else throw new ParseException("stab expected: " + Tokenizer.debugShow(tokens, cnt));
		
		int brackets = 0;
		while (tokens.get(cnt).is("("))
		{
			brackets++;
			cnt++;
		}
		
		p = new P();
		cnt = p.parse(tokens, cnt);
		
		if (tokens.get(cnt).is(","))
			cnt++;
		else throw new ParseException(", expected: " + Tokenizer.debugShow(tokens, cnt));
		
		q = new P();
		cnt = q.parse(tokens, cnt);
		
		if (tokens.get(cnt).is(","))
			cnt++;
		else throw new ParseException(", expected: " + Tokenizer.debugShow(tokens, cnt));
		
		p1 = new P();
		cnt = p1.parse(tokens, cnt);
		
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
		return "stability (" + p + "," + q + "," + p1 + "," + value + ")";
	}

	public CEform getCEFormula()throws ParseException
	{
		CEform ceform = new CEform();
		String string = "not (true;[not "+p+"];(["+p+" and "+q+"] and len < " + value + ");[not "+p+" and not " + q + "];true)";
		ceform.parse(new ParsingString(new StringBuilder(string)));
		return ceform;
	}
	
	
}
