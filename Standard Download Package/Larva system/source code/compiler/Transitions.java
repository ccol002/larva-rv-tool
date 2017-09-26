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
