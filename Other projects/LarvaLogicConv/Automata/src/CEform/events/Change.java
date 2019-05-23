package CEform.events;

import java.util.ArrayList;

import PEA.Event;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class Change extends CEform.events.Event{
	
	String p;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("change"))
			cnt++;
		else throw new ParseException("change expected: " + Tokenizer.debugShow(tokens, cnt));
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
		return "change (" + p + ")";
	}
	
	public void getEvents(ArrayList<PEA.Event> list)
	{
		PEA.Event ev= new Event();
		ev.name = p; 
		list.add(ev);
	}
}
