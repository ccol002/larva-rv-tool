package CEform.events;

import java.util.ArrayList;

import CEform.CEform;
import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class Event extends CEform{

	Event p;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
//		if (tokens.get(cnt).is("true") || tokens.get(cnt).is("false"))
//		{
//			p = new BoolValue();
//			cnt = p.parse(tokens, cnt);
//		}	
//		else 
			if (tokens.get(cnt).isIdentifier())
		{
			p = new Variable();
			cnt = p.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("change"))
		{
			p = new Change();
			cnt = p.parse(tokens, cnt);
		}
//		else if (tokens.get(cnt).is("nochange"))
//		{
//			p = new NoChange();
//			cnt = p.parse(tokens, cnt);
//		}
		else if (tokens.get(cnt).is("invchange"))
		{
			p = new InvChange();
			cnt = p.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("("))
		{
			cnt = parse(tokens, cnt + 1);
			if (tokens.get(cnt).is(")"))
					cnt++;
			else
				throw new ParseException(") Expected: " + Tokenizer.debugShow(tokens, cnt));
		}
		else
			throw new ParseException("Unexpected token: " + Tokenizer.debugShow(tokens, cnt));
		
		if (cnt < tokens.size() && (tokens.get(cnt).is("and") || tokens.get(cnt).is("or")))
		{
			if (tokens.get(cnt).is("and"))
				throw new ParseException("I dont know how to handle conjunction of events!!");
			
			p = new Bool(p);
			cnt = p.parse(tokens, cnt);
		}	
		return cnt;
	}

	public static boolean tryParse(ArrayList<Token> tokens, int cnt) throws ParseException {
		if (tokens.get(cnt).is("true") || tokens.get(cnt).is("false"))
		{
			return true;
		}	
		else if (tokens.get(cnt).isIdentifier()
				 || tokens.get(cnt).is("invchange") || tokens.get(cnt).is("change"))
		{
			return true;
		}
		else if (tokens.get(cnt).is("not"))
		{
			return tryParse(tokens, cnt+1);
		}
		else if (tokens.get(cnt).is("("))
		{
			return tryParse(tokens, cnt + 1);
		}
		else
			return false;
	}

	public void getEvents(ArrayList<PEA.Event> list)
	{
		p.getEvents(list);
	}
	
	public String toString()
	{
		String string;
		string = p.toString();
		return string;
	}

	public boolean isSDTP()
	{
		return true;
	}
	
	public boolean isSUTP()
	{
		return true;
	}
}
