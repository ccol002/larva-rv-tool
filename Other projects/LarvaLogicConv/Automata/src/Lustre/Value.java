package Lustre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import parsing.Token;



public class Value extends Primitive{

	public Token val;
	public E.Type type;
	public Object obj;
	
	public int parse(int cnt, ArrayList<Token> tokens)throws Exception
	{
		if (tokens.get(cnt).is("true") || tokens.get(cnt).is("false")) //true or false
		{
			type = E.Type.BOOL;		
			obj = Boolean.parseBoolean(tokens.get(cnt).text);
		}
		else if (Character.isDigit(tokens.get(cnt).text.charAt(0)) && tokens.get(cnt).text.indexOf('.') == -1)//is a natural number
		{
			type = E.Type.INT;
			obj = Integer.parseInt(tokens.get(cnt).text);
		}
		else
		{
			type = E.Type.REAL;
			obj = Double.parseDouble(tokens.get(cnt).text);
		}
		val=tokens.get(cnt);
		cnt++;
		return cnt;
	}
	
	public E.Type getType()
	{
		return type;
	}
	
	public String toString()
	{
		return val.toString();
	}
	
	public Value clone()
	{
		Value v = new Value();
		v.val = val.clone();
		v.type = type;
		v.obj = obj; 
		return v;
	}
	
	public Value replaceAndFlatten(Node node, HashMap<Variable, Primitive> replacements)
	{
		return this;
	}
	
	public Hashtable<Variable, Object> getVarList(Hashtable<Variable, Object> hashtable) {
		return hashtable;
	}
	
	public String toJava()
	{
		return obj.toString();
	}
}
