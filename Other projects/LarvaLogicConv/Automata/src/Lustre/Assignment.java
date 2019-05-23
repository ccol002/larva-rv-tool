package Lustre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import parsing.Token;
import parsing.Tokenizer;



public class Assignment {

	public static Assignment beingParsed;
	Variable LHS;
	Primitive RHS;
	
	public int parse(int cnt, ArrayList<Token> tokens)throws Exception
	{
		beingParsed = this;
		LHS = Node.beingParsed.variables.get(tokens.get(cnt).text);
		if (LHS == null)
			throw new Exception("Unknown Variable: " + Tokenizer.debugShow(tokens, cnt));
//		if (!Node.beingParsed.varDependencies.containsKey(LHS))
//			Node.beingParsed.varDependencies.put(LHS, new Hashtable<Variable, Object>());
		cnt++;//lhs
		if (tokens.get(cnt).is("="))
			cnt++;//=
		else
			throw new Exception("= expected: " + Tokenizer.debugShow(tokens, cnt));
		RHS = new Expression();
		cnt = RHS.parse(cnt, tokens);
		return cnt;
	}
	
	public String toString()
	{
		return LHS+" = "+RHS + ";";
	}
	
	public Assignment clone()
	{
		Assignment a = new Assignment();
		a.LHS = LHS.clone();
		a.RHS = RHS.clone();
		return a;
	}
	
	public Assignment replaceAndFlatten(Node node, HashMap<Variable, Primitive> replacements)
		throws Exception
	{
		Primitive p = LHS.replaceAndFlatten(node, replacements);
		if (p instanceof Variable)
			LHS =(Variable)p;
		else
			throw new Exception("Bad Replacement occurred!! " + LHS + " by " + p);
			
		RHS = RHS.replaceAndFlatten(node, replacements);
		return this;
	}
	
	public String toJava() throws Exception
	{
		String java = LHS.toJava() + " = ";
		java += RHS.toJava()+";";
		return java;
	}
}
