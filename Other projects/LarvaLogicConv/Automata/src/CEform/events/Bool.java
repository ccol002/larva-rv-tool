package CEform.events;

import java.util.ArrayList;

import Lustre.Logic;
import Lustre.Lustre;
import QDDC.QDDC;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class Bool extends Event{

	Event lhs;
	Token operator;
	Event rhs;
		
	public Bool(Event p)
	{
		lhs = p;
	}
	
	//starting from the operator
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		if (tokens.get(cnt).is("and")||tokens.get(cnt).is("or"))
			operator = tokens.get(cnt);
		else throw new ParseException("Operator Expected: " + Tokenizer.debugShow(tokens, cnt));
		cnt++;
		rhs = new Event();
		cnt = rhs.parse(tokens, cnt);
		return cnt;
	}
	
	public String toString()
	{
		return "(" + lhs + ") " + operator + " (" + rhs + ")";
	}
	
	public void getEvents(ArrayList<PEA.Event> list)
	{
		lhs.getEvents(list);
		rhs.getEvents(list);
		
	}
}
