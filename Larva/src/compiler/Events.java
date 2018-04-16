package compiler;

import java.util.ArrayList;
import java.util.HashMap;

public class Events extends Compiler{

	public static int sid = -1;
	
	public HashMap<String, Trigger> events = new HashMap<String, Trigger>();
	
	public HashMap<String, Variable> variables = new HashMap<String, Variable>();
//	public HashMap<String, Variable> otherVars = new HashMap<String, Variable>();
	
	public Events(ParsingString ps, Global context) throws ParseException
	{
		super(ps);
		parse(context);
	}
		
	public void parse(Global context) throws ParseException
	{
		parseSingles(ps, context);
	}
	
	public void parseSingles(ParsingString string, Global context) throws ParseException
	{
		Tokenizer tok = new Tokenizer(Tokenizer.EVENT_MODE);
		ArrayList<Token> tokens = tok.scan(string.toString());
		int cnt= 0;
		while (cnt < tokens.size())
		{
			cnt = Trigger.parseEvent(tokens,cnt, this, context);//parsed tokens are deleted
		}
		if (tokens.size() != cnt)
			throw new ParseException("Unreached End of Events");
	}
	
	public void appendJava(StringBuilder sb, Global g)throws ParseException
	{		
		for (Trigger t : events.values())
		{
			if (t instanceof Event)
				((Event)t).traverseForTypes(events, g);
		}
		
		sb.append("\r\n\r\nboolean initialized = false;" +
				"\r\n\r\nafter():(staticinitialization(*)){" +
				"\r\nif (!initialized){"+
				"\r\n	initialized = true;"+
				"\r\n	_cls_" +g.name + g.id + ".initialize();"+
				"\r\n}"+
				"\r\n}");
		
		for (Trigger t : events.values())
		{
			if (t instanceof Event)
			{
				sb.append("\r\n");
				((Event)t).appendJava(sb,events, this,g);
			}
		}
	}
}
