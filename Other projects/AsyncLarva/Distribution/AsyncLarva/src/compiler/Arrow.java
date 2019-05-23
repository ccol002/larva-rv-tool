package compiler;

import java.util.ArrayList;
import java.util.HashMap;

public class Arrow {

	Trigger trigger;
	ArrayList<Token> condition = new ArrayList<Token>();
	ArrayList<Token> action = new ArrayList<Token>();
	State destination;
	HashMap<String,Boolean> checks = new HashMap<String,Boolean>();

	public int parse(int cnt, ArrayList<Token> tokens, HashMap<String, State> all, HashMap<String, Trigger> events, Global g) throws ParseException{
		
		if (all.containsKey(tokens.get(cnt).text))
			{destination = all.get(tokens.get(cnt).text);
			cnt++;//dest
			}
		else
			throw new ParseException("Unknown State : " + tokens.get(cnt).text);
		
		if (tokens.get(cnt++).isNot("["))
			throw new ParseException("Missing Delimiter (\"[\") : " + Tokenizer.debugShow(tokens, cnt));
		
		
		if (events.containsKey(tokens.get(cnt).text))
			trigger = events.get(tokens.get(cnt).text);
		else
			throw new ParseException("Unknown Event : " + tokens.get(cnt).text);
		
		
		
		while (cnt < tokens.size() && tokens.get(cnt).isNot("\\") && tokens.get(cnt).isNot("]"))
		{
			cnt++;//ending with / or ]			
		}
		
		if (tokens.get(cnt).isNot("\\") && tokens.get(cnt).isNot("]"))
			throw new ParseException("Missing Delimiter (\"]\" or \"\\\") : " + Tokenizer.debugShow(tokens, cnt));
	
		
		int openings = 1;//for square brackets...
		
		
		if (tokens.get(cnt).is("\\"))//ended with / 
		{
			cnt++;//skipping separator

			while (cnt < tokens.size() && tokens.get(cnt).isNot("\\") && (tokens.get(cnt).isNot("]") || openings > 1))
			{
				if (tokens.get(cnt).is("[")) openings++;
				else if (tokens.get(cnt).is("]")) openings--;
				
				condition.add(tokens.get(cnt++));				
			}
			if (tokens.get(cnt).isNot("\\") && tokens.get(cnt).isNot("]"))
				throw new ParseException("Missing Delimiter (\"]\" or \"\\\") : " + Tokenizer.debugShow(tokens, cnt));
		}
		
		
		if (tokens.get(cnt).is("\\"))//ended with / 
		{
			cnt++;//skipping separator
			
			while (cnt < tokens.size() && (tokens.get(cnt).isNot("]") || openings > 1))
			{
				if (tokens.get(cnt).is("[")) openings++;
				else if (tokens.get(cnt).is("]")) openings--;
				
				action.add(tokens.get(cnt++));				
			}
					
		}
		
		if (tokens.get(cnt++).isNot("]"))
			throw new ParseException("Missing Delimiter (\"]\") : " + Tokenizer.debugShow(tokens, cnt));
		
		
		cnt = parseChecks(tokens, cnt, g);
		
		
		return cnt;
	}
	
	/*
	 * This only works if the Object being compared are not the same instance!!
	 */
	public int parseChecks(ArrayList<Token> tokens, int cnt, Global g) throws ParseException
	{
		while (cnt < tokens.size() && tokens.get(cnt).is("["))
		{
			cnt++;//[

			boolean enable;
			if (cnt < tokens.size() && tokens.get(cnt).is("enable"))
				enable = true;
			else if (cnt < tokens.size() && tokens.get(cnt).is("disable"))
				enable = false;
			else 
				throw new ParseException(" enable / disable expected: " + Tokenizer.debugShow(tokens, cnt));
			cnt++;
			
			String invName;
			Invariant inv = null;
			if (cnt < tokens.size() && tokens.get(cnt).isIdentifier())
			{
				invName= tokens.get(cnt).text;
				if (!g.invariants.invariants.containsKey(invName))
					throw new ParseException("Unknown invariant!!: " + Tokenizer.debugShow(tokens, cnt));
				else
					inv = g.invariants.invariants.get(invName);
			}
			else
				throw new ParseException("Identifier Expected: " + Tokenizer.debugShow(tokens, cnt));
			cnt++;
			
			if (cnt < tokens.size() && tokens.get(cnt).is("]"))
				cnt++;
			else
				throw new ParseException("\"]\" Expected: " + Tokenizer.debugShow(tokens, cnt));
						
			checks.put(invName, enable);
			
		}
		
		return cnt;
	}
	
	/*public int parseChecks(ArrayList<Token> tokens, int cnt) throws ParseException
	{
		while (cnt < tokens.size() && tokens.get(cnt).is("["))
		{
			cnt++;
			boolean enable;
			if (cnt < tokens.size() && tokens.get(cnt).is("enableCheck"))
				enable = true;
			else if (cnt < tokens.size() && tokens.get(cnt).is("disableCheck"))
				enable = false;
			else 
				throw new ParseException(" enableCheck / disableCheck expected: " + Tokenizer.debugShow(tokens, cnt));
			cnt++;
			
			String methodName;
			if (cnt < tokens.size() && tokens.get(cnt).isIdentifier())
				methodName= tokens.get(cnt).text;
			else
				throw new ParseException("Identifier Expected: " + Tokenizer.debugShow(tokens, cnt));
			cnt++;
			
			if (cnt < tokens.size() && tokens.get(cnt).is("on"))
				cnt++;
			else 
				throw new ParseException("on Expected: " + Tokenizer.debugShow(tokens, cnt));
			
			
			String variableName;
			if (cnt < tokens.size() && tokens.get(cnt).isIdentifier())
				variableName= tokens.get(cnt).text;
			else
				throw new ParseException("Identifier Expected: " + Tokenizer.debugShow(tokens, cnt));
			cnt++;
			
			
			if (cnt < tokens.size() && tokens.get(cnt).is("]"))
				cnt++;
			else
				throw new ParseException("\"]\" Expected: " + Tokenizer.debugShow(tokens, cnt));
			
			
			//update hashtables
			String checkName = "_"+methodName+"_"+variableName+(++sid);
			checks.put(checkName, enable);
			
			if (!allChecks.containsKey(variableName))
				allChecks.put(variableName, new HashMap<String, String>());
				
			HashMap<String, String> temp = allChecks.get(variableName);
			temp.put(checkName, methodName);
		}
		
		return cnt;
	}*/
}



