package CEform.phase;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class Len extends Phase{
	CEform.P.P p;
	Token operator;
	Object value;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("len"))
			cnt++;
		else throw new ParseException("len expected: " + Tokenizer.debugShow(tokens, cnt));
				
		if (tokens.get(cnt).is("<") || tokens.get(cnt).is("<=")
			|| tokens.get(cnt).is(">") || tokens.get(cnt).is(">=")
			|| tokens.get(cnt).is("=="))
			{
				operator = tokens.get(cnt);
				cnt++;
			}
			else
				throw new ParseException("Operator Expected: " + Tokenizer.debugShow(tokens, cnt));
			
		value = tokens.get(cnt).getNumber();
		cnt++;
		return cnt;
	}
	
	public String toString()
	{
		return "len " + operator + " " + value;
	}

	public boolean isSDTP()throws ParseException
	{
		if (operator.is("<") || operator.is("<="))
			return false;
		else
			return true;
	}
	
	public boolean isSUTP()throws ParseException
	{
		if (operator.is(">") || operator.is(">="))
			return false;
		else
			return true;
	}
}
