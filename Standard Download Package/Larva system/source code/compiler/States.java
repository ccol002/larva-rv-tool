package compiler;

import java.util.ArrayList;
import java.util.HashMap;

public class States extends Compiler{

	ArrayList<State> accepting = new ArrayList<State>();
	ArrayList<State> bad = new ArrayList<State>();
	ArrayList<State> normal = new ArrayList<State>();
	ArrayList<State> starting = new ArrayList<State>();
	
	HashMap<String, State> all = new HashMap<String, State>();
	
		
	public void parse() throws ParseException {
		
		if (ps.hasMore("ACCEPTING"))
			parseStates(ps.parseWrapper("ACCEPTING"), State.Type.ACCEPTING, accepting);
		if (ps.hasMore("BAD"))
			parseStates(ps.parseWrapper("BAD"), State.Type.BAD, bad);
		if (ps.hasMore("NORMAL"))
			parseStates(ps.parseWrapper("NORMAL"), State.Type.NORMAL, normal);
		if (ps.hasMore("STARTING"))
			parseStates(ps.parseWrapper("STARTING"), State.Type.STARTING, starting);
		if (starting.size() != 1)
			throw new ParseException("More than One Starting State!");
	}
	
	public void parseStates(ParsingString ps,State.Type type, ArrayList<State> states) throws ParseException {
		
		Tokenizer tok = new Tokenizer(Tokenizer.AUTOMATON_MODE);
		ArrayList<Token> tokens = tok.scan(ps.string.toString());
		
		int cnt = 0; 
		
		while (cnt < tokens.size())
		{
			State s = new State();
			s.name = tokens.get(cnt++);
			
			if (cnt < tokens.size() && tokens.get(cnt).is("{"))
			{
			s.code = Tokenizer.startingEnding(cnt, "{", "}", tokens);
			cnt += s.code.size() + 2;
			}

			s.type = type;
			
			if (!all.containsKey(s.name))
			{
				all.put(s.name.text, s);
				states.add(s);
			}
			else
				throw new ParseException("Duplicate State : " + s.name);
		}
	}


}
