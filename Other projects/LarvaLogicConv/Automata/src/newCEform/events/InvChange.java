package newCEform.events;

import java.util.ArrayList;

import PEA.Unary;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class InvChange extends Event{
	
	String p;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("invchange"))
			cnt++;
		else throw new ParseException("invchange expected: " + Tokenizer.debugShow(tokens, cnt));
		ArrayList<Token> subtokens = Tokenizer.startingEnding(cnt, "(", ")", tokens);
		cnt += subtokens.size()+2;
		p = subtokens.get(0).text;
		return cnt;
//		p = new Event();
//		int cnt2 = p.parse(subtokens, 0);
//		if (cnt2 == subtokens.size())
//			return cnt;
//		else
//			throw new ParseException("Unreached end of statement: " + Tokenizer.debugShow(subtokens, cnt2));
	}
	
	public String toString()
	{
		return "invchange (" + p + ")";
	}
	
	public void getEvents(ArrayList<newPEA.Event> list)
	{
		newPEA.Event ev = new newPEA.Event();
		ev.name = p;
		ev.unary = new newPEA.Unary();
		list.add(ev);
	}
	
}
