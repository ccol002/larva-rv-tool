package compiler;

import java.util.ArrayList;
import java.util.HashMap;

public class Transitions extends Compiler{

	HashMap<State, Transition> transitions = new HashMap<State, Transition>();
	
	
	public void parse(HashMap<String, Trigger> events, States states, Global g) throws ParseException
	{
		Tokenizer tok = new Tokenizer(Tokenizer.AUTOMATON_MODE);
		ArrayList<Token> tokens = tok.scan(ps.string.toString());
		int cnt = 0;
		while (cnt < tokens.size())
		{		
			if (states.all.containsKey(tokens.get(cnt).text))
			{
				State source = states.all.get(tokens.get(cnt).text);
				if (transitions.containsKey(source))
				{
					cnt++;//source
					cnt = transitions.get(source).addArrow(cnt,tokens,states.all,events, g);
				}
				else
				{		
					Transition t = new Transition();
					cnt = t.parse(cnt,tokens,states.all,events, g);
					transitions.put(source, t);
				}
			}
			else
				throw new ParseException("Unknown State: "+tokens.get(cnt));
		}
		
		//COMPENSATION ADAPTATION
		//adding transitions which trigger after the completion of a nested automaton
		for (State s:states.all.values())
			if (s.nested != null)
			{
				Transition t = new Transition();
				t.source = states.all.get("_" + s.name.text);
				Arrow a = new Arrow();
				a.destination = s;
				
				if (!events.containsKey("c_" + s.nested.text + "_done"))
				{
					Event e= new Event();
					e.channelName = new Token("c_" + s.nested.text + "_start");
					e.args= new ArrayList<Token>();
					e.methodName = new Token("receive");
					e.name = new Token("_c_" + s.nested.text + "_start");
					e.type = Event.EventType.channel;
					g.events.events.put(e.name.text, e);
					events.put(e.name.text, e);
					
					EventCollection ec = new EventCollection();
					ec.events = new ArrayList<Trigger>();
					ec.events.add(e);
					ec.name = new Token("c_" + s.nested.text + "_start");
					g.events.events.put(ec.name.text, ec);
					events.put(ec.name.text, ec);
					
					HashMap<String, Object> hm = new HashMap<String, Object>();
					hm.put(ec.name.text,null);
					EventCollection.reverse.put(e.name.text, hm);
					
					
					
					e= new Event();
					e.channelName = new Token("c_" + s.nested.text + "_done");
					e.args= new ArrayList<Token>();
					e.methodName = new Token("receive");
					e.name = new Token("_c_" + s.nested.text + "_done");
					e.type = Event.EventType.channel;
					g.events.events.put(e.name.text, e);
					events.put(e.name.text, e);
					
					ec = new EventCollection();
					ec.events = new ArrayList<Trigger>();
					ec.events.add(e);
					ec.name = new Token("c_" + s.nested.text + "_done");
					g.events.events.put(ec.name.text, ec);
					events.put(ec.name.text, ec);
					
					hm = new HashMap<String, Object>();
					hm.put(ec.name.text,null);
					EventCollection.reverse.put(e.name.text, hm);
					
				}
				
				a.trigger = events.get("c_" + s.nested.text + "_done");
				t.arrows.add(a);
				transitions.put(t.source, t);
			}
	}
	
	public void appendJava(StringBuilder sb, Property l, Global g, Global root)throws ParseException
	{
		if (Compiler.verbose){
			sb.append("\r\n\r\n_cls_"+root.name+root.id+".pw.println(\"[" + l.name + "]AUTOMATON::> "+l.name+"(\"+");
			for (int i = 0; i < g.variables.size(); i++)
			{
				if (g.stringMethods.get(i)!= null)
					sb.append(g.stringMethods.get(i) + "(" + g.variables.get(i).name+") + \" \" + ");
				else
					sb.append(g.variables.get(i).name+" + \" \" + ");
			}

			sb.append("\") STATE::>\"+ _string_"+l.name+"(_state_id_"+l.name+", 0));");
		}
		sb.append("\r\n_cls_"+root.name+root.id+".pw.flush();");
		sb.append("\r\n\r\nif (0==1){}");
		for (State s:transitions.keySet())
		{
			sb.append("\r\nelse if (_state_id_"+l.name+"=="+s.id+"){");
		
			transitions.get(s).appendJava(sb,l,g);
				
			sb.append("\r\n}");
		}
	}

}
