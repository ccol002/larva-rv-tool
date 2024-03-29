package compiler;

import java.util.ArrayList;

public class Foreach extends Global{

	//the code for initially which returns an arraylist of objects
	public ArrayList<Token> initially = new ArrayList<Token>();
	
	public Foreach(Global parent, ParsingString string) throws ParseException
	{
		super(string);
		this.parent = parent;
		headerParse();
		commonParse();
		parseInitially();
		if (ps.string.toString().trim().length() > 0)
			System.out.println("Warning: Not all script was successfully parsed!!...(possible wrong order of sections)");
	}
	
	public void headerParse()throws ParseException
	{
		Tokenizer tok = new Tokenizer(Tokenizer.EVENT_MODE);
		ArrayList<Token> tokens = tok.scan(ps.parameter);
		for (int i = 1; i < tokens.size()-1; i++)//brackets (or comma) are included
		{
			Variable var = new Variable();
			var.type = tokens.get(i++);
			var.name = tokens.get(i++);
			
			if (i < tokens.size() && tokens.get(i).is("equateUsing"))
			{
				if (i+1 < tokens.size() && tokens.get(i+1).isIdentifier())
				{
					equateMethods.add(tokens.get(i+1).text);
					i+= 2;
				}
				else
					throw new ParseException("Identifier Expected: "+Tokenizer.debugShow(tokens, i+1));
					
			}
			else
				equateMethods.add(null);
			
			if (i < tokens.size() && tokens.get(i).is("stringUsing"))
			{
				if (i+1 < tokens.size() && tokens.get(i+1).isIdentifier())
				{
					stringMethods.add(tokens.get(i+1).text);
					i+= 2;
				}
				else
					throw new ParseException("Identifier Expected: "+Tokenizer.debugShow(tokens, i+1));
					
			}
			else
				stringMethods.add(null);
			
			if (context.size() > 0)
				context.add(new Token(Tokenizer.get(","),","));
			context.add(var.name);
			variables.add(var);
			contextVariables.put(var.name.text, var);
		}
	}
	
	public void parseInitially()throws ParseException
	{
		Tokenizer tok = new Tokenizer(Tokenizer.ACTION_MODE);
		initially = tok.scan(parseWrapper("INITIALLY",false).toString());
	}
	
}
