package Lustre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import parsing.Token;
import parsing.Tokenizer;



public class Variable extends Primitive{

	public static int unique = -1;
	
	public Token var;
	public E.Type type;
	public E.Io io;
	
	public Variable()
	{}
		
	public Variable(Token var, E.Type type, E.Io io)
	{
		this.var = var;
		this.type = type;
		this.io = io;
	}
	
	public Variable Clone()//shallow
	{	
		return new Variable(var,type,io);
	}
	
	public Variable clone()
	{
		Variable v = new Variable();
		v.var = var.clone();
		v.type = type;
		v.io = io;
		return v;
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof Variable)
			return var.text.equals(((Variable)obj).var.text);
		else
			return false;
	}
	
	public int hashCode()
	{
		return var.text.hashCode();
	}
			
	public int parse(int cnt, ArrayList<Token> tokens)throws Exception
	{
		Variable v = Node.beingParsed.variables.get(tokens.get(cnt).text);
		if (v == null)
			throw new Exception("Unknown variable: " + Tokenizer.debugShow(tokens, cnt));
		//Node.beingParsed.varDependencies.get(Assignment.beingParsed.LHS).put(v, new Object());
		cnt++;
		var = v.var;
		type = v.type;
		io = v.io;
		return cnt;
	}
	
	public E.Type getType()
	{
		return type;
	}
	
	public String toString()
	{
		return var.toString();
	}
	
	public String getName()
	{
		return var.text;
	}
	
	public Primitive replaceAndFlatten(Node node, HashMap<Variable, Primitive> replacements)
	{
		if (replacements != null && replacements.containsKey(this))
			return replacements.get(this).clone();
		else 
			return this;
	}
	
	public Hashtable<Variable, Object> getVarList(Hashtable<Variable, Object> hashtable) {
		if (!hashtable.containsKey(this))
			hashtable.put(this,new Object());
		return hashtable;
	}
	
//	public String toJava()
//	{
//		String java = "";
//		if (io == E.Io.IN)
//			java += "in.";
//		else if (io == E.Io.OUT)
//			java += "out.";
//		else //if (io == E.Io.LOCAL)
//			java += "loc.";
//		java += var.text;
//		return java;
//	}
	
	public String toJava()
	{		
		return var.text;
	}
}
