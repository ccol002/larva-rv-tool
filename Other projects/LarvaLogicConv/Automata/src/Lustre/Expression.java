package Lustre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import parsing.Token;


public class Expression extends Primitive{

	Primitive expression;
	Operator operator;
	Primitive expression2;
	
	public Expression clone()
	{
		Expression e = new Expression();
		e.expression = expression.clone();
		if (operator != null)
			e.operator = operator.clone();
		if (expression2 != null)
			e.expression2 = expression2.clone();
		return e;
	}
	
	public int parse(int cnt, ArrayList<Token> tokens)throws Exception
	{
		expression = new Primitive();
		cnt = expression.parse(cnt,tokens);
		if (Operator.isOperator(tokens.get(cnt)))
		{
			operator = new Operator();
			cnt = operator.parse(cnt, tokens);
			expression2 = new Expression();
			cnt = expression2.parse(cnt, tokens);
		}
		return cnt;
	}

	public E.Type getType()throws Exception
	{
		return expression.getType();
	}
	
	public String toString()
	{
		if (operator != null && expression2 != null)
			return expression.toString() + " " + operator.toString() + " " + expression2.toString();
		else 
			return expression.toString();
	}
	
	public Expression replaceAndFlatten(Node node, HashMap<Variable, Primitive> replacements)throws Exception
	{
		expression = expression.replaceAndFlatten(node, replacements);
		if(expression2 != null)
			expression2 = expression2.replaceAndFlatten(node, replacements);
		return this;
	}
	
	public Hashtable<Variable, Object> getVarList(Hashtable<Variable, Object> hashtable) {
		expression.getVarList(hashtable);
		if (expression2 != null)
			expression2.getVarList(hashtable);
		return hashtable;
	}

	public String toJava()throws Exception
	{
		String java = expression.toJava();
		if (operator != null)
		{
			java += " " + operator.toJava() + " " + expression2.toJava();
		}
		return java;
	}
	
}
