package newCEform.phase;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class Cnt extends Phase{
	
	String p;
	Token operator;
	Object value;

	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("cnt"))
			cnt++;
		else throw new ParseException("cnt expected: " + Tokenizer.debugShow(tokens, cnt));
				
		ArrayList<Token> subtokens = Tokenizer.startingEnding(cnt, "(", ")", tokens);
		cnt += subtokens.size()+2;
		p = subtokens.get(0).text;
		
		if (tokens.get(cnt).is("<") || tokens.get(cnt).is("<=")
			|| tokens.get(cnt).is(">") || tokens.get(cnt).is(">=")
			|| tokens.get(cnt).is("==")|| tokens.get(cnt).is("!="))
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
		return "cnt(" + p + ")" + operator + " " + value;
	}

	public boolean isSDTP()throws ParseException
	{
		return true;
	}
	
	public boolean isSUTP()throws ParseException
	{
		return true;
	}
}
