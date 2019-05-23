package compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Transition {

	State source;
	public ArrayList<Arrow> arrows = new ArrayList<Arrow>();
	
	public int addArrow(int cnt, ArrayList<Token> tokens, HashMap<String, State> all, HashMap<String, Trigger> events, Global g) throws ParseException
	{		
		if (tokens.get(cnt++).isNot("->"))
			throw new ParseException("Missing Delimiter \"->\"");
		
		Arrow a = new Arrow();
		cnt = a.parse(cnt,tokens,all, events,g);
		arrows.add(a);
		return cnt;
	}

	public int parse(int cnt, ArrayList<Token> tokens, HashMap<String, State> all, HashMap<String, Trigger> events, Global g) throws ParseException 
	{
		if (all.containsKey(tokens.get(cnt).text))
		{
			source = all.get(tokens.get(cnt).text);
			cnt++;
		}
		else
			throw new ParseException("Unknown State : " + tokens.get(cnt).text);
		
		cnt = addArrow(cnt, tokens, all, events,g);
		return cnt;
	}
	
	//COMPENSATION MODIFICATION
	public ArrayList<Token> replaceWithHashmaps(ArrayList<Token> action, Trigger event)
	{
		HashMap<String,Variable> variables = new HashMap<String,Variable> ();
		
		//iterate through the event collection to get a list of variables
		for (Trigger t: ((EventCollection)event).events)
		{
			variables.putAll(t.variables);
			variables.putAll(t.otherVars);
		}
		
		for (Token t:action)
		{
			if (variables.containsKey(t.text) && variables.get(t.text).type != null)//leave out placeholders
			{
				t.text = t.text+".get(_dat)";
			}
		}
		return action;
		
	}
	
	
	public void appendJava(StringBuilder sb, Property l, Global g)throws ParseException 
	{	
		sb.append("\r\n		if (1==0){}");
		for (Arrow a:arrows)
		{
			sb.append("\r\n		else if ((_occurredEvent(_event,"+a.trigger.getId()+"/*"+a.trigger.name+"*/))");
			if (a.condition.size() > 0)
				sb.append(" && ("+Global.handleVariableReplacement(a.condition,g)+")");
			sb.append("){");
			
			
			
			//the action will now appear in the switch statement upon reaching an error state			
			//sb.append("\r\n		"+Global.redirectOutput(Global.handleVariableReplacement(a.action,g)));
			
			
			
			
			sb.append("\r\n		_state_id_"+l.name+" = "+a.destination.id+";//moving to state "+a.destination);
			
			if (a.destination.code != null)
				sb.append("\r\n" +Global.redirectOutput(Global.handleVariableReplacement(a.destination.code,g)));

			
			
			
			//COMPENSATION ADAPTATION
			//install compensation
			sb.append("\r\n_comps.push("+a.id+");");
			sb.append("\r\n_comps.push(sid++);");
				
			//store deviation on stack 
			if (a.deviation!=null)
			{
				sb.append("\r\n_comps.push(-1);");
				sb.append("\r\n_comps.push("+a.deviation.id+");");
			}

			
			//COMPENSATION ADAPTATION
			//carrying out compensations
			if (a.destination.type.equals(State.Type.BAD))
			{
				sb.append("\r\n while (_comps.size()>0){"
				         +"\r\n   Integer _dat = _comps.pop();"
				         +"\r\n   Integer _act = _comps.pop();"
				         
				         +"\r\n   if (_act == -1){"
				         +"\r\n    	_state_id_"+l.name+" = _dat; //deviating"
				         +"\r\n     break;"
				         +"\r\n   }"
				         +"\r\n   else if (_act == -2){"
                         +"\r\n     //end of compensation for this scope reached"
                         +"\r\n     break;"
                         +"\r\n   } "
				         
				         +"\r\n   switch (_act) {");
				
				for (Transition t: l.transitions.transitions.values()){
					for (Arrow a2: t.arrows)
					{
				         sb.append("\r\n case "+a2.id+":");
				         sb.append(Global.redirectOutput(Global.handleVariableReplacement(replaceWithHashmaps(a2.action,a2.trigger),g)));
				         sb.append("break;");
					}
				}
				sb.append("\r\n }}" +
						"\r\n c_" + l.name + "_done.send();");     //i know that this is useless for the first automaton 
                                                                  //which cannot be nested but this is harmless
			}
			//COMPENSATION MODIFICATION
			//discarding compensations
			else if (a.destination.type.equals(State.Type.ACCEPTING))
			{
				sb.append("\r\n while (_comps.size()>0){"
				         +"\r\n   Integer _dat = _comps.pop();"
				         +"\r\n   Integer _act = _comps.pop();"
				         
				         +"\r\n   if (_act == -2){"
				         +"\r\n    	//stopping discard due to nesting"
				         +"\r\n     break;"
				         +"\r\n   }");
				
			
				sb.append("\r\n }" +
						"\r\n c_" + l.name + "_done.send();"); //i know that this is useless for the first automaton 
				                                              //which cannot be nested but this is harmless
				
			}else if (a.destination.nested != null && source.id>=0)//going into a nested state (unless you have just finished a nested automaton)
			{
				sb.append("\r\n c_" + a.destination.nested.text + "_start.send();"
						+"\r\n _comps.push(-2);_comps.push(-2);");
				sb.append("\r\n _state_id_"+l.name + "*=-1;"); //the negative id of a state is the "fake" state from which 
				                                               //we will return when the nested automaton completes 
			}
			
			
			//invariants
			for (String s : a.checks.keySet())
			{
				boolean enable = a.checks.get(s);
				sb.append("\r\n" + s + "_enb = " +enable +";");
				if (enable)
				{
					Invariant inv = g.invariants.invariants.get(s);
					sb.append(inv.name + "_temp = "+ Tokenizer.showStats(inv.call) + ";");				
				}
			}

				sb.append("\r\n		_goto_"+l.name+"(_info);");
			sb.append("\r\n		}");
		}
	} 
	
}
