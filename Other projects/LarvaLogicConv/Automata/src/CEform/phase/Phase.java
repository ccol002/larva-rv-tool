package CEform.phase;

import java.util.ArrayList;
import java.util.HashSet;

import CEform.CEform;
import CEform.events.BoolValue;
import CEform.events.Event;
import PEA.Bool;
import PEA.Clock;
import PEA.EventDisj;
import PEA.Operator;
import PEA.Operator.Op;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;


public class Phase extends CEform{

	public ArrayList<CEform> conjunction = new ArrayList<CEform>();
	//first in the conjunction is the invariant
	//then if bounded, the bound
	//then the unallowed events
	public Object bound;
	public boolean bounded = false; 
	public boolean upperbound = false;
	public HashSet<String> forbidden = new HashSet<String>();
	public boolean allowEmpty = false;
	public Operator timeop;
	public Bool inv;
	
	//this has to be filled in separately
	public EventDisj entryEvents = new EventDisj();
	public Clock clock;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
	
		if (tokens.get(cnt).is("["))
		{
			Interval interval = new Interval();
			cnt = interval.parse(tokens, cnt);
			conjunction.add(interval);
			inv = interval.p.toPEABool();
		}
		else if (tokens.get(cnt).getBoolean())
		{
			conjunction.add(new BoolValue(true));
			cnt++;
			inv = new PEA.BoolValue(true);
			allowEmpty = true;//temporarily
		}
		else
			throw new ParseException("Unexpected Token: " + Tokenizer.debugShow(tokens, cnt));
		
		if (cnt < tokens.size()-1 && tokens.get(cnt).is("and") && tokens.get(cnt+1).is("len"))
		{
			cnt++;
			Len len = new Len();
			cnt = len.parse(tokens, cnt);
			conjunction.add(len);
			if (len.operator.is("<") || len.operator.is("<="))
				upperbound = true;
			
			if (len.operator.is("<"))
				timeop = new Operator(Op.ls);
			else if (len.operator.is("<="))
				timeop = new Operator (Op.lseq);
			else if (len.operator.is(">"))
			{
				allowEmpty = false;//finalize allow empty
				timeop = new Operator (Op.gt);
			}
			else if (len.operator.is(">="))
			{
				allowEmpty = false;//finalize allow empty
				timeop = new Operator (Op.gteq);
			}
			else
				timeop = new Operator(Op.none);
			
			bounded = true;
			bound = len.value;
		}
		else
			timeop = new Operator(Op.none);
		
		while (cnt < tokens.size()-1 && tokens.get(cnt).is("and"))
		{
			cnt++;
			if (tokens.get(cnt).is("nochange"))
			{
				NoChange nc = new NoChange();
				cnt = nc.parse(tokens, cnt);
				forbidden.add(nc.v.token.text);
			}
			else
				throw new ParseException("nochange Expected: " + Tokenizer.debugShow(tokens, cnt));
		}
		
		return cnt;
	}
	
	public String toString()
	{
		String text = "";
		for (CEform q:conjunction)
			text += " and " + q;
		return text.substring(5);
	}

	public boolean isSDTP()throws ParseException
	{
		boolean chopSDTP = true;
		
		for (CEform p : conjunction)
			if (!p.isSDTP())
			{
				chopSDTP = false;
				break;
			}
		return chopSDTP;
	}
	
	public boolean isSUTP()throws ParseException
	{
		boolean chopSUTP = true;
		
		for (CEform p : conjunction)
			if (!p.isSUTP())
			{
				chopSUTP = false;
				break;
			}
		return chopSUTP;
	}
}
