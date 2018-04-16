package compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class EventCollection extends Trigger{

	public int sid = -1;
	public ArrayList<Trigger> events = new ArrayList<Trigger>();
		
	public ArrayList<Token> filter = new ArrayList<Token>();//in the method call
	//contains a list of events related to an event collection
	public static HashMap<String, HashMap<String,Object>> reverse = new HashMap<String, HashMap<String,Object>>();
	
	public EventCollection()
	{
		id = ++Events.sid;
	}
			
	public int parseOneEventInCollection(ArrayList<Token> eventColl, int cnt, Events events, Global context) throws ParseException
	{
		if (eventColl.get(cnt).is("{"))
		{
			EventCollection ec = new EventCollection();
			ec.name = new Token("_"+name.text+(++sid));
			ec.otherVars = (HashMap<String, Variable>)otherVars.clone();
			
			this.events.add(ec);
			updateReverseHash(ec);
			
			return ec.parse(eventColl,cnt,events, context);
		}
		else
		{
			return parseOneEvent(eventColl, cnt, events, context);
		}
	}
	
	public int parseOneEvent(ArrayList<Token> eventColl, int cnt, Events events, Global context) throws ParseException
	{
		Trigger ev;
		//check if name exists in events
		if (events.events.containsKey(eventColl.get(cnt).text))
		{//it is a valid event //check that the variables include the collections' variable
			ev = events.events.get(eventColl.get(cnt).text);
			for (Variable v:variables.values())
			{
				if (!ev.variables.containsValue(v))
				{
					otherVars.put(v.name.text, v);
					variables.remove(v.name.text);
				}					
			}
			for (Variable v: ev.variables.values())
			{
				if (otherVars.containsKey(v.name.text) && !v.set)
				{
					v.set = true;
					variables.put(v.name.text, v);
					otherVars.remove(v.name.text);
				}
			}
			
			this.events.add(ev);
					
			//consume paramlist
			while (cnt<eventColl.size() && eventColl.get(cnt).isNot(",")  && eventColl.get(cnt).isNot("|"))
				cnt++;
			
			//if there is a ";" without a where clause it would not cause a problem...it will be ignored
			//however, if there is anything which is not a where it will complain
			
		}
		else
		{
			//throw new ParseException("Unknown event!!!" + Parser.debugShow(eventColl, cnt));
		//if not an event, then create one!!
			ev = new Event();
			ev.name = new Token("_"+name.text+(++sid));
			ev.otherVars = otherVars;
			cnt = ((Event)ev).parse(eventColl, cnt, this, events, context);
			
			this.events.add(ev);
			events.events.put(ev.name.text, ev);			
		}
		
		updateReverseHash(ev);
		
		return cnt;
	}
	
	public void updateReverseHash(Trigger ev)
	{
		//it will "show" ev as a child of the current (this) collection		
		
		//reverse contain the list of basic events as keys
		//and their values are the eventcollection to which they belong
		
		if (!reverse.containsKey(ev.name.text))
			reverse.put(ev.name.text, new HashMap<String, Object>());
		
		if (!reverse.get(ev.name.text).containsKey(name.text))
			reverse.get(ev.name.text).put(name.text, null);
		
	}
	
	public int parse(ArrayList<Token> tokens, int cnt, Events events, Global context) throws ParseException
	{
		ArrayList<Token> thisColl = Tokenizer.startingEnding(cnt, "{", "}", tokens);
		
		cnt += thisColl.size()+2;//{}
		
		int cnt2 = 0;
		
		while (cnt2 < thisColl.size())
		{
			ArrayList<Token> list = new ArrayList<Token>();
			int opens = 0;
			//a "|" of sub collection MUST be ignored!!!!
			while (cnt2 < thisColl.size() && (thisColl.get(cnt2).isNot("|") || (opens > 0)))
			{
				if (thisColl.get(cnt2).is("{"))
					opens++;
				if (thisColl.get(cnt2).is("}"))
					opens--;
				
				list.add(thisColl.get(cnt2++));
				
			}
			if (cnt2 < thisColl.size() && thisColl.get(cnt2).isNot("|"))
				throw new ParseException("Missing Closing Delimiter : " + Tokenizer.debugShow(thisColl, cnt2));
			else
				cnt2++;//|
			
			parseOneEventInCollection(list, 0, events, context);
						
		}
		
		if (cnt<tokens.size() && tokens.get(cnt).is("filter"))//where
		{
			cnt++;
			if (cnt < tokens.size() && tokens.get(cnt).is("{"))
			{
				filter = Tokenizer.startingEnding(cnt, "{", "}", tokens);
				cnt += filter.size()+2;
			}
			else
			{
				throw new ParseException("Error in filter clause! " + Tokenizer.debugShow(tokens, cnt));
			}				
		}
		
		if (cnt<tokens.size() && tokens.get(cnt).is("where"))//where
		{
			cnt++;
			if (cnt < tokens.size() && tokens.get(cnt).is("{"))
			{
				whereClause = Tokenizer.startingEnding(cnt, "{", "}", tokens);
				cnt += whereClause.size()+2;
			}
			else
			{
				whereClause = Tokenizer.ending(cnt, ";", tokens);
				cnt+=whereClause.size();
				whereClause.add(tokens.get(cnt++));//;
			}				
		}
		
		if (!events.events.containsKey(getName().text))
			events.events.put(getName().text, this);
		else
			throw new ParseException("Duplicate Event! " + getName().text);
		
		return cnt;
	}
	
	public String toString()
	{
		return name.text;
	}
}
