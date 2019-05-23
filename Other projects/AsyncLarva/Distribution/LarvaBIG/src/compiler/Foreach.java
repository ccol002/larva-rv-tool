package compiler;

import java.util.ArrayList;

public class Foreach extends Global{

	//the code for initially which returns an arraylist of objects
	public ArrayList<Token> initially = new ArrayList<Token>();
	
	private int iLimit;
	
	public Foreach(Global parent, ParsingString string) throws ParseException
	{
		super(string);
		this.parent = parent;
		
		iLimit = 0;
		headerParse();
		commonParse();
		parseInitially();
		if (ps.string.toString().trim().length() > 0)
			System.out.println("Warning: Not all script was successfully parsed!!..." +
					"(possible wrong order of sections) Error found at: [" + 
					ps.string.substring(0, Math.min(ps.string.length(), 20)) + "...]");
	}
	
	public void headerParse()throws ParseException
	{
		Tokenizer tok = new Tokenizer(Tokenizer.EVENT_MODE);
		ArrayList<Token> tokens = tok.scan(ps.parameter);
		
		//there cannot be nothing or just the brackets
		if (tokens.size() <= 2)
			throw new ParseException("Expecting object type and variable names enclosed within brackets.");
		
		int i = 0;
		if (tokens.get(i).is("("))
			i++;
		else
			throw new ParseException("'(' Expected: " + Tokenizer.debugShow(tokens, i));
		
		boolean inBracks = true;
		
		while (i < tokens.size() && !tokens.get(i).is(")")) //brackets (or comma) are included
		{
			Variable var = new Variable();
			
			if (i+1 < tokens.size())
			{
				var.type = tokens.get(i++);
				var.name = tokens.get(i++);
			}
			else
				throw new ParseException("Class Type and Variable Name Expected: " + Tokenizer.debugShow(tokens, i));
			
			if (i < tokens.size() && tokens.get(i).is("equateUsing"))
			{
				if (i+1 < tokens.size() && tokens.get(i+1).isIdentifier())
				{
					equateMethods.add(tokens.get(i+1).text);
					i += 2;
				}
				else
					throw new ParseException("Identifier Expected: " + Tokenizer.debugShow(tokens, i+1));
			}
			else
				equateMethods.add(null);
			
			if (i < tokens.size() && tokens.get(i).is("stringUsing"))
			{
				if (i+1 < tokens.size() && tokens.get(i+1).isIdentifier())
				{
					stringMethods.add(tokens.get(i+1).text);
					i += 2;
				}
				else
					throw new ParseException("Identifier Expected: " + Tokenizer.debugShow(tokens, i+1));
			}
			else
				stringMethods.add(null);
			
			if (context.size() > 0)
				context.add(new Token(Tokenizer.get(","),","));
			context.add(var.name);
			variables.add(var);
			contextVariables.put(var.name.text, var);
		}
		
		if (i < tokens.size() && tokens.get(i).is(")"))
			i++;
		else
			throw new ParseException("')' Expected: " + Tokenizer.debugShow(tokens, i));
	
		if (i < tokens.size())
		{
			if (tokens.get(i).is("limit"))
			{
				if (i+1 < tokens.size() && tokens.get(i+1).isIdentifier())
				{
					int lim = tokens.get(i+1).getInteger();
					if (lim < 1)
						throw new ParseException("Expected Identifier with value >= 1: " + Tokenizer.debugShow(tokens, i+1));
					
					iLimit = lim;
					i += 2;
				}
				else
					throw new ParseException("Identifier Expected: " + Tokenizer.debugShow(tokens, i+1));
			}
			else
				throw new ParseException("'limit' Keyword Expected: " + Tokenizer.debugShow(tokens, i));
		}
		
		if (i != tokens.size())
			throw new ParseException("End of Foreach operator expected: " + Tokenizer.debugShow(tokens, i));

	}
	
	public void parseInitially()throws ParseException
	{
		Tokenizer tok = new Tokenizer(Tokenizer.ACTION_MODE);
		initially = tok.scan(parseWrapper("INITIALLY",false).toString());
		String s = "";
	}
	
	public Integer LimitSize()
	{
		if (iLimit < 1)
			return null;
		else
			return iLimit;
	}

}
