package CE.F;

import java.util.ArrayList;

import CE.P.P;
import CEform.CEform;

import parsing.ParseException;
import parsing.ParsingString;
import parsing.Token;
import parsing.Tokenizer;

public class Progress extends F{
	
	P p;
	Object value;

	
	//starting from the operator
	//prog(P,l)
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("prog"))
			cnt++;
		else throw new ParseException("prog Expected: " + Tokenizer.debugShow(tokens, cnt));

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
		else throw new ParseException(", Expected: " + Tokenizer.debugShow(tokens, cnt));

		
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
		return "progress (" + p + "," + value + ")";
	}
	
	public CEform getCEFormula()throws ParseException
	{
		CEform ceform = new CEform();
		String string = "not (true;(["+p+"] and len>"+value+");true)";
		ceform.parse(new ParsingString(new StringBuilder(string)));
		return ceform;
	}
	
	
}
