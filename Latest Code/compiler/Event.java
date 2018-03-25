package compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Event extends Trigger{

	public static int sid = -1;
	
	public enum EventType {clock, //clockCycle
		 clockDynamic, channel, uponEntry, uponThrowing, uponHandling, uponReturning};
	
	//public ArrayList<Token> methodCall = new ArrayList<Token>();

	public HashMap<String, Variable> methodVars = new HashMap<String, Variable>();
	
	public long clockAmount;//global? for registering?
	public Token methodName;
	public Token clockName;
	public Token channelName;
	public Global container;
	public Token target;
	public Token returned;
	public Token exception;
	public Token within;
	public ArrayList<Token> args = new ArrayList<Token>();//in the method call
	public EventType type = EventType.uponEntry;
	
	private List<Token> allWhere;
	private int level; 
	
	public boolean uponCall = true; 
	
	
	public Event()
	{
		id = ++Events.sid;
	}

	public Token getVariable(List<Token> list, Events current)throws ParseException
	{
		if (list.size() == 0)
			return null;
		
		Variable v = new Variable();
		
		if (list.get(0).is("("))//there should be brackets!!
			list = Tokenizer.startingEnding(0, "(", ")", list);
		
		if ((list.size() != 1)&&((list.size() % 2) == 1))//nested type
		{
			String tempName = "";
			for (int i = 1; i < list.size(); i=i+2)
			{
				if (i != (list.size() - 1))
					tempName += list.get(i).text + ".";
				else
					tempName += list.get(i).text;
			}
			v.name.text = tempName;
			
			if (v.name.text.equals("*"))
				v.name.text = "__"+(++sid);
				
			if (otherVars.containsKey(v.name.text))
				v = otherVars.get(v.name.text);
			
			if (!current.variables.containsKey(v.name.text))
				current.variables.put(v.name.text, v);
			else
				v = current.variables.get(v.name.text);
			
			if (v.type == null)
				v.type = list.get(0);
			else if (!v.type.equals(list.get(0)))
				throw new ParseException("Mismatch in Variable type: " + v);	
			
			if (v.type != null && v.type.text.indexOf('+')==v.type.text.length()-1)
			{
				v.subclasses = true;
				v.type.text = v.type.text.substring(0, v.type.text.length()-1);
			}
		}
		else if (list.size() == 2)//with type
		{				
			v.name = list.get(1);
		
			if (v.name.text.equals("*"))
				v.name.text = "__"+(++sid);
				
			if (otherVars.containsKey(v.name.text))
				v = otherVars.get(v.name.text);
			
			if (!current.variables.containsKey(v.name.text))
				current.variables.put(v.name.text, v);
			else
				v = current.variables.get(v.name.text);
			
			if (v.type == null)
				v.type = list.get(0);
			else if (!v.type.equals(list.get(0)))
				throw new ParseException("Mismatch in Variable type: " + v);	
			
			if (v.type != null && v.type.text.indexOf('+')==v.type.text.length()-1)
			{
				v.subclasses = true;
				v.type.text = v.type.text.substring(0, v.type.text.length()-1);
			}
		}
		else if (list.size() == 1)//without type
		{
			v.name = list.get(0);
			if (!current.variables.containsKey(v.name.text))
				current.variables.put(v.name.text, v);
			else
				v = current.variables.get(v.name.text);
		}
		else if (list.size() == 0)
			return null;
		else
			throw new ParseException("Unexpected Error: " + Tokenizer.debugShow(list, 0));
		
		//careful!! dont introduce new instances of the same variable!!
		
		if (v.name != null && otherVars.containsKey(v.name.text))
			otherVars.remove(v.name.text);
		
		if (!variables.containsKey(v.name.text))
			variables.put(v.name.text, v);
//			else
//				throw new ParseException("Variable already bound: "+v.name.text);
//		}
//		else if (!variables.containsKey(v.name.text) && !undeclared.containsKey(v.name.text))
//		{
//			undeclared.put(v.name.text, v);
//		}
		
		return v.name;
	}
	
	public int parse(ArrayList<Token> tokens, int cnt, EventCollection ec, Events current, Global context) throws ParseException
	{
		Map<String,Variable> allVisibleVars = new HashMap<String,Variable>();
		Global tempContext = context;
		while (tempContext != null)
		{
			//note that this binds to the outermost container
			if (tempContext.localVariables.containsKey(tokens.get(cnt).text))
				this.container = tempContext;
			allVisibleVars.putAll(tempContext.localVariables);//note a variable from an higher level container may overwrite that of a lower level
			tempContext = tempContext.parent;
		}
			
		if (container != null)//ie the variable has been found
		{
			Variable v = allVisibleVars.get(tokens.get(cnt).text);
			if (v.getVariableType().equals("Clock"))
			{
				
				this.clockName = tokens.get(cnt);
				cnt++;
				if (tokens.get(cnt).is("@") || tokens.get(cnt).is("@d")
						|| tokens.get(cnt).is("@m")|| tokens.get(cnt).is("@s") || tokens.get(cnt).is("@l"))
				{
					this.type = EventType.clock;
					cnt++;
				}
//				else if (tokens.get(cnt).is("@%"))
//				{
//					throw new ParseException("Cyclic clocks are no longer supported in this version :(");
//					this.type = EventType.clockCycle;
//					cnt++;
//				}
				else if (tokens.get(cnt).is("@@"))
				{
					this.type = EventType.clockDynamic;
					cnt++;
				}
				else
					throw new ParseException("@ Expected: " + Tokenizer.debugShow(tokens, cnt));
				
				if (this.type.equals(EventType.clock)){

					double userAmount = tokens.get(cnt).getNumber();

					if (tokens.get(cnt-1).is("@") || tokens.get(cnt-1).is("@s"))
						clockAmount = (long)(userAmount * 1000);
					else if (tokens.get(cnt-1).is("@l"))
						clockAmount = (long)(userAmount);
					else if (tokens.get(cnt-1).is("@d"))
						clockAmount = (long)(userAmount * 24 * 60 * 60 * 1000);
					else if (tokens.get(cnt-1).is("@h"))
						clockAmount = (long)(userAmount * 60 * 60 * 1000);
					else if (tokens.get(cnt-1).is("@m"))
						clockAmount = (long)(userAmount * 60 * 1000);

					cnt++;

					v.clockEvents.add(clockAmount);
				}
//				else
//					v.clockCycleEvents.add(clockAmount);
				
				for (Variable v1:context.allParentsVars(new ArrayList<Variable>()))
				{
					whereClause.addAll(new Tokenizer(Tokenizer.EVENT_MODE).scan(v1.getVariableName()+"=null;"));
				}
				
			}
			else if (v.getVariableType().equals("Channel"))
			{
				this.type = EventType.channel;
				this.channelName = tokens.get(cnt);
				cnt++;
				
				if (tokens.get(cnt).is("."))
					cnt++;
				else throw new ParseException(". Expected: " + Tokenizer.debugShow(tokens, cnt));
				
				if (tokens.get(cnt).text.equals("receive"))
					cnt++;
				else throw new ParseException("Unexpected identifier: " + Tokenizer.debugShow(tokens, cnt));
				
				if (tokens.get(cnt).is("("))
				{
					List<Token> list = tokens.subList(cnt, tokens.size());
					
					Token t = getVariable(list, current);
					if (t != null)
						args.add(t);
					cnt+=list.size();
				}
			}
			else throw new ParseException("Unexpected identifier: " + Tokenizer.debugShow(tokens, cnt));
		}
		else
		{
			//call / execution before method call
			
			if (tokens.get(cnt).is("call"))
			{
				cnt++;
			}
			else if (tokens.get(cnt).is("execution"))
			{ 
				uponCall = false;
				cnt++;
			}
			
			//*.*(...)upon*

			cnt = parseMethodCall(current,tokens,cnt);

			
			//upon...
			if (cnt < tokens.size() && tokens.get(cnt).is("uponThrowing"))
			{
				cnt++;
				type = EventType.uponThrowing;
				int endList = getTypeBoundary(cnt, tokens);
				List<Token> list = tokens.subList(cnt, endList);
				cnt += list.size();

				exception = getVariable(list, current);
			}
			else if (cnt < tokens.size() && tokens.get(cnt).is("uponHandling"))
			{
				cnt++;
				type = EventType.uponHandling;
				int endList = getTypeBoundary(cnt, tokens);
				List<Token> list = tokens.subList(cnt, endList);
				cnt += list.size();

				exception = getVariable(list, current);
			}
			else if (cnt < tokens.size() && tokens.get(cnt).is("uponReturning"))
			{
				cnt++;
				type = EventType.uponReturning;
				int endList = getTypeBoundary(cnt, tokens);
				List<Token> list = tokens.subList(cnt, endList);
				cnt += list.size();

				returned = getVariable(list, current);
				
			}
			
			//within
			if (cnt < tokens.size() && tokens.get(cnt).is("within"))
			{
				cnt++;
				List<Token> list = tokens.subList(cnt, tokens.size());
				cnt += list.size();

				within = getVariable(list, current);		
			}
		}
		
		return cnt;	
	}	
	
	private int getTypeBoundary(int cnt, ArrayList<Token> tokens) {
		int boundary = tokens.size();
		for (int i = cnt; i < tokens.size(); i++) {
			if (tokens.get(i).text.trim().equals("within") && i > cnt) {
				boundary = i;
				break;
			}
		}
		return boundary;
	}
				
	public int parseMethodCall(Events current, ArrayList<Token> tokens, int cnt) throws ParseException
	{	
		//how much "." does tokens contains
		int tempDotCount = 0;
		ArrayList<Integer> dotList = new ArrayList<Integer>();
		for (int i = 0; i < tokens.size(); i++)
		{
			if (tokens.get(i).text.equals("."))
			{
				dotList.add(i);
				tempDotCount++;
			}
		}

		ArrayList<Token> list = null;
		if (tempDotCount > 1)
		{
			list = new ArrayList<Token>();
			String concatenatedTokens = "";
			for (int i = ((dotList.get(dotList.size() - 2)).intValue()) + 1; i >= 0; i--)
			{
				concatenatedTokens += tokens.get(0).text;
				tokens.remove(0);
			}
			Token token = new Token(concatenatedTokens);
			list = Tokenizer.ending(cnt, ".", tokens);//.
			list.add(0, token);
			cnt += list.size();//.
		}
		else
		{
			list = Tokenizer.ending(cnt, ".", tokens);//.
			cnt += list.size() +1;//.
		}

		target = getVariable(list, current);
		
		methodName = tokens.get(cnt++);
		
		
		while (cnt<tokens.size() && tokens.get(cnt).isNot("("))
		{
			methodName.text += tokens.get(cnt++);
		}
			
		if (tokens.size() > cnt+1)
		{
			while (cnt<tokens.size() && tokens.get(cnt).isNot(")")) //not an immediate closing bracket i.e. some argument 
			{		
				cnt++;//( or ","
				list = Tokenizer.twoEnding(cnt, ",", ")", tokens);//, or )
				cnt += list.size();
				if (list.size() > 0)
					args.add(getVariable(list, current));
			}
			cnt++;//)
		}
		else
			throw new ParseException("Invalid Method Call: " + Tokenizer.debugShow(tokens, cnt));
		
		return cnt;
	}
	
	public String recursivelyFindCollections(HashMap<String, Trigger> events, Trigger event, String list)
	{
		if (EventCollection.reverse.containsKey(event.name.text))
			for (String s : EventCollection.reverse.get(event.name.text).keySet())
			{
				String temp = "";
				if (s.indexOf("_")!= 0)
					temp = ", " +  events.get(s).getId() + "/*"+s+"*/";
				String temp2 = recursivelyFindCollections(events, events.get(s), list);
				if (!list.contains(", "+  events.get(s).getId() + "/*"))
					list = temp + temp2;
				else
					list = temp2;
			}
		return list;
	}
	
	public ArrayList<Token> recursiveWhereClauses(HashMap<String, Trigger> events, Trigger event, ArrayList<Token> list)
	{		
		if (EventCollection.reverse.containsKey(event.name.text))
			for (String s : EventCollection.reverse.get(event.name.text).keySet())
			{
				if (events.get(s).whereClause.size() > 0)
					list.addAll(events.get(s).whereClause);
					
				recursiveWhereClauses(events, events.get(s), list);
				
			}
		return list;
	}
	
	public HashMap<String,Variable> recursiveOtherVariables(HashMap<String, Trigger> events, Trigger event, HashMap<String,Variable> list)
	{		
		if (EventCollection.reverse.containsKey(event.name.text))
			for (String s : EventCollection.reverse.get(event.name.text).keySet())
			{
				for (Variable v:events.get(s).otherVars.values())
					list.put(v.name.text,v);
					
				recursiveOtherVariables(events, events.get(s), list);
				
			}
		return list;
	}
		
	//while validating the where, duplicate variables are found, while resolving initializations!!
	public void validateWhere(HashMap<String, Variable> allParentVariables,
			HashMap<String, Variable> allOtherVars, List<Token> allWhere, Global g, Trigger event)throws ParseException
	{
		//validate where
		//check that all relevant variables are initialized 
		HashMap<String, Variable> needingInitialization = new HashMap<String, Variable>();
		for (Variable v:g.allParentsVars(new ArrayList<Variable>()))
		{
			if (needingInitialization.containsKey(v.name.text))
				throw new ParseException("Duplicate Variable: "+v.name.text);
			else
				needingInitialization.put(v.name.text, v);
		}
		
		for (Variable v:allOtherVars.values())
			if (v.type != null && needingInitialization.containsKey(v.name.text))
					throw new ParseException("Duplicate Variable: "+v.name.text);
			else if (v.type != null)
				needingInitialization.put(v.name.text,v);
		
		HashMap<String, Object> alreadyInitialized = new HashMap<String, Object>();
		
		for (Variable v:variables.values())//the variables of the basic event
		{
			if (needingInitialization.containsKey(v.name.text))
				needingInitialization.remove(v.name.text);

			if (!alreadyInitialized.containsKey(v.name.text))
				alreadyInitialized.put(v.name.text, null);	
		}
				
		
		for (int i = 1; i < allWhere.size(); i++)
		{
			if (i >= 0 && allWhere.get(i).is("="))//=
			{
				boolean wasRemoved = false;
				if (needingInitialization.containsKey(allWhere.get(i-1).text))
				{
					needingInitialization.remove(allWhere.get(i-1).text);
					alreadyInitialized.put(allWhere.get(i-1).text, null);
				}
				else if(alreadyInitialized.containsKey(allWhere.get(i-1).text))
				{
					String name = allWhere.get(i-1).text;
					int start =  i-1;
					while (start > -1 && allWhere.get(start).isNot(";"))
						start--;
					start++;
					int end = i-1;
					while (end < allWhere.size() && allWhere.get(end).isNot(";"))
						end++;
					if (end == allWhere.size())
						throw new ParseException("Missing Delimeter: \";\"" + Tokenizer.debugShow(allWhere,end-1));
					List<Token> removed = new ArrayList<Token>();
					for (int j = 0; j < end - start +1; j++)
					{
						removed.add(allWhere.get(start));
						allWhere.remove(start);					
					}
					i-=i-start+1;
					wasRemoved = true;
					System.out.print("Warning: variable \"" + name + "\" in event \""+event.name+"\" already initialized...");
					System.out.println("the second initialization will be ignored! \"" + Tokenizer.show(removed) + "\"");
					
				}
				if (!wasRemoved)
				{
					//remove type declaration if it exists!
					Variable v = null;
					if (i > 0 && allOtherVars.containsKey(allWhere.get(i-1).text))
						v = allOtherVars.get(allWhere.get(i-1).text);
					else if (i > 0 && allParentVariables.containsKey(allWhere.get(i-1).text))
						v = allParentVariables.get(allWhere.get(i-1).text);

					if (i>1 && v!= null && v.type != null && allWhere.get(i-2).text.equals(v.type.text))
					{
						allWhere.remove(i-2);
						i--;
					}
					else if (i>1 && v!= null && v.type == null && allWhere.get(i-2).isNot(";"))//;
					{
						v.type = allWhere.get(i-2);
						allWhere.remove(i-2);
						i--;
					}
				}
			}
		}
		if (needingInitialization.size() > 0)
			throw new ParseException("Missing Initialization of Variable(s): " + needingInitialization.values() + " in event " + event.name);
	}
	
	public void generateLevelByLevel(HashMap<String, Variable> allParentVariables,
			HashMap<String, Variable> allOtherVars, HashMap<String, Trigger> events,
			Trigger event, Global g, List<Token> templist, int templevel)
			throws ParseException
	{
		
		
		if (EventCollection.reverse.containsKey(event.name.text))
			for (String s : EventCollection.reverse.get(event.name.text).keySet())
			{
				HashMap<String, Variable> allOtherPlusOne = (HashMap<String, Variable>)allOtherVars.clone();
				allOtherPlusOne.putAll(events.get(s).otherVars);
				
				//note that each individual "collections" tree (_r0 -> r -> _any2 -> any) is taken independently
				//the where clause accumulates ONLY while it is going deeper in the tree BUT NOT sideways 
				//(i.e. in the forloop the templist is still the same throughout)
				//that's why we pass a clone of templist!!!
				List<Token> clonedList = (List<Token>)((ArrayList<Token>)templist).clone();
				clonedList.addAll(events.get(s).whereClause);
				if (templevel > level)//will keep the where clause with the highest level
				{
					allWhere = clonedList;
					level = templevel;
				}										
				if (s.indexOf("_")!= 0)	//it is not an automatically generated event
				{
					Trigger t = events.get(s);
					
//					if (!(t instanceof Event && (((Event)t).type.equals(Event.EventType.clock)
//							|| ((Event)t).type.equals(Event.EventType.clockCycle)
//							|| ((Event)t).type.equals(Event.EventType.channel))))
					validateWhere(allParentVariables, allOtherPlusOne, clonedList,g, t);
				}
				generateLevelByLevel(allParentVariables, allOtherPlusOne, events, events.get(s), g,
						clonedList, templevel+1);				
			}
	}
	
	public String generateWhere(HashMap<String, Trigger> events, Global g)throws ParseException
	{
		//first check standalone where
		HashMap<String, Variable> allParentVariables = g.allParentsVarsHash(new HashMap<String, Variable>());
		
	//	HashMap<String, Variable> allOtherVars = recursiveOtherVariables(events,this,new HashMap<String, Variable>());
		
		allWhere = new ArrayList<Token>();
		level = 0;
		generateLevelByLevel(allParentVariables,otherVars,events,this, g,whereClause,1);
		
	//	validateWhere(allParentVariables, this.otherVars, this.whereClause, g);
		//all the context has to be initialized at this stage
		//BUT NOT all the other variables of the collections which contain this event!
		
							
		//where clause handling
//		ArrayList<Token> allWhere = new ArrayList<Token>();
//		allWhere.addAll(whereClause);
//		allWhere.addAll(recursiveWhereClauses(events, this, new ArrayList<Token>()));
		
	//	validateWhere(allParentVariables, allOtherVars, allWhere, g);
				
		return Tokenizer.showStats(allWhere);
	}
	
	public void traverseForTypes(HashMap<String, Trigger> events, Global g)throws ParseException
	{
		HashMap<String, Variable> allParentVariables = g.allParentsVarsHash(new HashMap<String, Variable>());
						
		HashMap<String, Variable> allOtherVars = recursiveOtherVariables(events,this,new HashMap<String, Variable>());
		//the other variables
						
		//where clause handling
		ArrayList<Token> allWhere = new ArrayList<Token>();
		allWhere.addAll(whereClause);
		allWhere.addAll(recursiveWhereClauses(events, this, new ArrayList<Token>()));
		
		
		//validate where
		//check that all relevant variables are initialized 
		
		for (int i = 1; i < allWhere.size(); i++)
		{
			if (allWhere.get(i).is("="))//=
			{		
				//remove type declaration if it exists!
				Variable v = null;
				if (allOtherVars.containsKey(allWhere.get(i-1).text))
					v = allOtherVars.get(allWhere.get(i-1).text);
				else if (allParentVariables.containsKey(allWhere.get(i-1).text))
					v = allParentVariables.get(allWhere.get(i-1).text);
				
				if (i>1 && v!= null && v.type != null && allWhere.get(i-2).text.equals(v.type.text))
				{
					allWhere.remove(i-2);
					i--;
				}
				else if (i>1 && v!= null && v.type == null && allWhere.get(i-2).isNot(";"))//;
				{
					v.type = allWhere.get(i-2);
					allWhere.remove(i-2);
					i--;
				}
			}
		}
	}
		
	public void appendJava(StringBuilder sb, HashMap<String, Trigger> events, Events current, Global g)
		throws ParseException
	{
		if (type == EventType.uponEntry)
			sb.append("before ( ");
		else if (type == EventType.uponHandling)
			sb.append("before ( ");
		else if (type == EventType.uponReturning)
			sb.append("after ( ");
		else if (type == EventType.uponThrowing)
			sb.append("after ( ");
		else 
			sb.append("before ( ");
		
		for (Variable v:variables.values())
		{
			if (v.type != null && !v.name.equals(returned)
					&& !v.name.equals(exception))//bug fix 15/7/13
				sb.append(v.toJava() + ",");
		}
		//sb.deleteCharAt(sb.length()-1); MOVED later...because it does not apply for channel and clock

		if (type == EventType.clock)
		{
			sb.append("Clock _c, long millis) : (call(* Clock.event(long))" +
					" && args(millis) && target(_c) " +
					" && (if (_c.name.equals(\""+this.clockName+"\")))"+
					" && (if (millis == "+clockAmount+"))"+
					" && !cflow(adviceexecution())");
		}
//		else if (type == EventType.clockCycle)
//		{
//			sb.append("Clock _c, long millis) : (call(* Clock.event(long))" +
//					" && args(millis) && target(_c) && (if (millis % "+clockAmount+"==0))"+
//					" && !cflow(adviceexecution())"+
//					" && !cflow(within(larva.*))  && !(within(larva.*))");
//		}
		else if (type == EventType.clockDynamic)
		{
			sb.append("Clock _c, long millis) : (call(* Clock.event(long))" +
					" && args(millis) && target(_c)"+
					" && !cflow(adviceexecution())");
		}
		else if (type == EventType.channel)
		{
			
			sb.append("Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_"+container.name+container.id+"."+ this.channelName +")))");

			if (args.size()>0)
			{
				sb.append(" && args(");
				for (int i = 0; i < args.size(); i++)
					sb.append(current.variables.get(args.get(i).text).getVariableName()+",");
				
				sb.deleteCharAt(sb.length()-1);
				sb.append(")");
			}
		//	sb.append(" && !cflow(adviceexecution())");
		}
		else
		{
			sb.deleteCharAt(sb.length()-1);
			
			if (type == EventType.uponEntry)
			{
				sb.append(") : (");
			}
			else if (type == EventType.uponHandling)
			{
				sb.append("): (withincode(* ");
				if (target != null && variables.containsKey(target.text) && variables.get(target.text).type != null)
					sb.append(variables.get(target.text).type.text);
				else
					sb.append("*");

				sb.append("." + methodName.text + "(..))");
				sb.append(" && handler(");
				if (exception == null)
					sb.append("*");
				else
				{
					sb.append(current.variables.get(exception.text).getVariableType());
					sb.append(") && args(" + current.variables.get(exception.text).getVariableName());
				}
				sb.append(") && cflow(");

			}
			else if (type == EventType.uponReturning)
			{
				sb.append(") returning (");
				if (returned != null && variables.containsKey(returned.text) && !returned.text.equals("*"))// && variables.get(returned.text).type != null)
					sb.append(variables.get(returned.text).type.text + " " + variables.get(returned.text).getVariableName());
//				else
//				sb.append("*");
				sb.append(") : (");
			}
			else if (type == EventType.uponThrowing)
			{
				sb.append(") throwing (");
				if (exception != null && variables.containsKey(exception.text) && !returned.text.equals("*") && variables.get(exception.text).type != null)
					sb.append(variables.get(exception.text).type.text + " " + variables.get(exception.text).getVariableName());
				sb.append(") : (");
			}

			//call
			if (uponCall)
				sb.append("call(");
			else
				sb.append("execution(");
			
			if (!methodName.text.equals("new"))//handles constructor call 
				sb.append("* ");
			
			
			if (target != null && variables.containsKey(target.text) && variables.get(target.text).type != null)
				sb.append(variables.get(target.text).type.text);
			else
				sb.append("*");
			
			if (variables.containsKey(target.text) && variables.get(target.text).subclasses)
				sb.append("+");

			sb.append("." + methodName.text + "(..))");

			//suppressed target matching in case of constructor (one should use uponReturning not target)
			if (target != null && !methodName.text.equals("new") && variables.containsKey(target.text) && variables.get(target.text).type != null)
				sb.append(" && target(" + target.text+")");

			if (args.size()>0)
			{
				sb.append(" && args(");
				for (int i = 0; i < args.size(); i++)
				{
					sb.append(current.variables.get(args.get(i).text).getVariableName()+",");
				}
				sb.deleteCharAt(sb.length()-1);
				sb.append(")");
			}

			if (within != null)
			{
				sb.append(" && this(" + within + ")");
			}
			
			if (type == EventType.uponHandling)
				sb.append(")");//closing cflow

			sb.append(" && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))");
		}
		
		//forming collection
		
		StringBuilder s = new StringBuilder();
		if (this.name.text.indexOf("_")!=0)
			s.append(", "+id + "/*"+name.text+"*/");//leave out events which we have generated

		//handle collection events
		s.append(recursivelyFindCollections(events, this, ""));


		for (Trigger t : events.values())
			if (t instanceof EventCollection && (((EventCollection)t).events.contains(this)))
		{
				List<Token> list = ((EventCollection)t).filter;
				if (list.size()>0)
					sb.append(" && if (" + Tokenizer.showStats(list) + ")");
		}
		sb.append(") {");
		
		sb.append("\r\n\r\nsynchronized(_asp_"+Global.name+"0.lock){");
		
		//method body
		
		
			ArrayList<Variable> allParentVariables = g.allParentsVars(new ArrayList<Variable>());
			//the context variables
			for (Variable v : allParentVariables)
				sb.append("\r\n"+v.type+" "+v.name+";");

			HashMap<String, Variable> allOtherVars = recursiveOtherVariables(events,this,new HashMap<String, Variable>());

			
			//the other variables
			for (Variable v : allOtherVars.values())
				if (v.type != null && !variables.containsKey(v.name.text))//not a variable bound to the method
					sb.append("\r\n"+v.type+" "+v.name+";");


			//any necessary method call ... where clause
			sb.append("\r\n"+generateWhere(events, g));

			
			
			
			
			
			if (!this.type.equals(EventType.clock) && !this.type.equals(EventType.clockDynamic))
			{

				sb.append("\r\n_cls_"+g.name+g.id+" _cls_inst = _cls_"+g.name+g.id+"._get_cls_"+g.name+g.id+"_inst( ");
				for (Variable v:allParentVariables)
					sb.append(v.name+",");
				sb.deleteCharAt(sb.length()-1);
				sb.append(");");

			}
			
			
			
			
			
			
//			//set local non-static variables
//			for (Variable v : g.variables)
//			sb.append("\r\n_cls_inst."+v.name+"="+v.name+";");

			//set local static variables
			for (Variable v : variables.values())
				if (v.type != null)
					sb.append("\r\n_cls_inst."+v.name+" = "+v.name+";");

			//to add variables which are not part of the event, but are added by the user...
			//these are instances of the class already
			//however, we can't simply put all the event variables
			//because some of the variables are related to the other events!

			for (Variable v : allOtherVars.values())
				if (v.type != null && !variables.containsKey(v.name.text))
					sb.append("\r\n_cls_"+g.name+g.id + "."+v.name+" = "+v.name+";");

			
			//NOTE 26/5/15: the above was _call_all as below, however if a channel event is declared within a foreach, then this 
			//means that it has a define where clause and as such it is intended for a single instance
			//now it is treated as other events
			
//				if (this.type.equals(EventType.channel))
//				{
//					for (Global h:g.getRecursiveForeaches(new ArrayList<Global>()))
//						sb.append("\r\n_cls_" + g.name + h.id + 
//								"._call_all(thisJoinPoint.getSignature().toString()" + s.toString()+");");	
//				}
//				else 
					if (this.type.equals(EventType.clock) || this.type.equals(EventType.clockDynamic))
				{
					sb.append("\r\nsynchronized(_c){" +
							"\r\n if (_c != null && _c._inst != null) {");
					//for (Global h:g.getRecursiveForeaches(new ArrayList<Global>()))
					//	if (h.equals(g))
							sb.append("\r\n_c._inst._call(thisJoinPoint.getSignature().toString()" + s.toString()+");");
					//	else
							sb.append("\r\n_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString()"+s.toString()+");");
					sb.append("\r\n}\r\n}");
				}

				else
				{		
					//for (Global h:g.getRecursiveForeaches(new ArrayList<Global>()))
					//	if (h.equals(g))
							sb.append("\r\n_cls_inst._call(thisJoinPoint.getSignature().toString()" + s.toString()+");");
					//	else
							sb.append("\r\n_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString()" + s.toString()+");");
					
					
					
					//the event itself
					
				}
		
		sb.append("\r\n}\r\n}");
		
	}

	public String toString()
	{
		return name.text;
	}
}
