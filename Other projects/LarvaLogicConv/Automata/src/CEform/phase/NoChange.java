package CEform.phase;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;
import CEform.events.Event;
import CEform.events.Variable;

public class NoChange extends Phase{
	
	public Variable v;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("nochange"))
			cnt++;
		else throw new ParseException("nochange expected: " + Tokenizer.debugShow(tokens, cnt));
		ArrayList<Token> subtokens = Tokenizer.startingEnding(cnt, "(", ")", tokens);
		cnt += subtokens.size()+2;
		v = new Variable();
		int cnt2 = v.parse(subtokens, 0);
		if (cnt2 == subtokens.size())
			return cnt;
		else
			throw new ParseException("Unreached end of statement: " + Tokenizer.debugShow(subtokens, cnt2));
	}
	
	public String toString()
	{
		return "nochange (" + v + ")";
	}
	

}
