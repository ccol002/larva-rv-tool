package compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import java.util.List;

public abstract class Trigger {

	public int id;
	public Token name;
	public ArrayList<Token> parameterList = new ArrayList<Token>();
	
	public HashMap<String,Variable> variables = new HashMap<String,Variable>();//variables related to the method call
	public HashMap<String,Variable> otherVars = new HashMap<String,Variable>();//other variables
//	public HashMap<String,Variable> undeclared = new HashMap<String,Variable>();//other variables
	
	
	public List<Token> whereClause = new ArrayList<Token>();
	//String generatedWhere;
	
	public int getId()
	{
		return id;
	}
	
	public Token getName()
	{
		return name;
	}
	
	public static int parseEvent(ArrayList<Token> tokens, int cnt, Events events, Global context) throws ParseException
	{
		
		EventCollection tmp = new EventCollection();
		tmp.name = tokens.get(cnt++);
		tmp.parameterList = Tokenizer.startingEnding(cnt, "(", ")", tokens);//()
		Trigger.parseParameters(tmp, events);
		
		cnt+= tmp.parameterList.size()+2;
		
		if (cnt>=tokens.size() || tokens.get(cnt++).isNot("="))//=
			throw new ParseException("Missing Delimiter \"=\" " + Tokenizer.debugShow(tokens, cnt));
				
		cnt = tmp.parse(tokens, cnt, events, context);	//treat it as a collection!
				
		return cnt;
	}
		
	public static void parseParameters(Trigger event, Events current)throws ParseException
	{
		for (int i = 0; i < event.parameterList.size(); i++)//skipping the comma
		{
			Variable var = new Variable();
			
			//check if there is a type and get it
			if (i < event.parameterList.size()-1 && event.parameterList.get(i+1).isNot(","))//,
				var.type = event.parameterList.get(i++);
			
			if (i >= event.parameterList.size())
			{
				throw new ParseException("Error in Parameter List: "+Tokenizer.debugShow(event.parameterList, i));
			}
			else
				var.name = event.parameterList.get(i++);
			
			if (!current.variables.containsKey(var.name.text))
				current.variables.put(var.name.text, var);
			else
			{//careful!! dont introduce new instances of the same variable!
				Variable v2 = var;
				var = current.variables.get(var.name.text);
				if (v2.type != null && var.type == null)
				{
					var.type = v2.type;
				}
				else if (v2.type != null && !v2.type.equals(var.type))
					throw new ParseException("Mismatch in variable type: " + v2);
			}
	
			if (!event.otherVars.containsKey(var.name.text))
				event.otherVars.put(var.name.text, var);
			//ADD them when they occur in the method call
			//if (!event.variables.containsKey(var.name.text))
				//event.variables.put(var.name.text, var);
		}
	}
	//public void appendJava(StringBuilder sb, HashMap<String, Trigger> events, Events current);
}
