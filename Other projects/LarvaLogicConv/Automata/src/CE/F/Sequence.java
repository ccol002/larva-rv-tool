package CE.F;

import java.util.ArrayList;

import CE.P.P;
import CEform.CEform;
import parsing.ParseException;
import parsing.ParsingString;
import parsing.Token;
import parsing.Tokenizer;

public class Sequence extends F{
	P lhs;
	P rhs;
//	Object value;
		
	public Sequence()
	{}
	
	//starting from the operator
	//seq(P,P1)
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("seq"))
			cnt++;
		else throw new ParseException("seq Expected: " + Tokenizer.debugShow(tokens, cnt));

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
		
//		if (tokens.get(cnt).is(","))
//			cnt++;
//		else throw new ParseException(", expected: " + Tokenizer.debugShow(tokens, cnt));
//		
//		
//		value = tokens.get(cnt).getNumber();
//		cnt++;
		while (brackets > 0 && tokens.get(cnt).is(")"))
		{
			brackets--;
			cnt++;
		}
		if (brackets != 0) throw new ParseException(") Expected: " + Tokenizer.debugShow(tokens, cnt));
		
		rhs = new P();
		cnt = rhs.parse(tokens, cnt);
		return cnt;
	}
	
	public String toString()
	{
		return "sequence (" + lhs + "," + rhs + ")";//," + value + ")";
	}

	public CEform getCEFormula()throws ParseException
	{
		CEform ceform = new CEform();
		String string = "not (true;["+lhs+"];[(not "+lhs+") and (not "+rhs + ")];true)";
		ceform.parse(new ParsingString(new StringBuilder(string)));
		return ceform;
	}
	
	
}
