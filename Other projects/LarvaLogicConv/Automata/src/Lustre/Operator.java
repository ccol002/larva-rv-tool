package Lustre;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;


public class Operator {

	Token op;
	
	public int parse(int cnt, ArrayList<Token> tokens)throws Exception
	{
		if (isOperator(tokens.get(cnt))) 
				 {
					 op = tokens.get(cnt);
					 cnt++;
				 }
		else throw new Exception("Operator Expected! " + Tokenizer.debugShow(tokens, cnt));
		return cnt;
	}
	
	public int parseUnary(int cnt, ArrayList<Token> tokens)throws Exception
	{
		if (isUnary(tokens.get(cnt))) 
				 {
					 op = tokens.get(cnt);
					 cnt++;
				 }
		return cnt;
	}
	
	public static boolean isUnary(Token token)throws ParseException
	{
		if (token.is("not") || token.is("+") || token.is("-"))//not,+,-
			return true;
		else
			return false;
	}
	
	public static boolean isOperator(Token token)throws ParseException
	{
		if (token.is("and") || token.is("or") || token.is("<")
				||token.is(">")||token.is("<=")||token.is(">=")
				||token.is("+")||token.is("-")||token.is("*")
				||token.is("/")||token.is("%")||token.is("=")||token.is("==")||token.is("!=")) 
			return true;
		else
			return false;
	}

	public String toString()
	{
		return op.toString();
	}
	
	public Operator clone()
	{
		Operator o = new Operator();
		o.op = op.clone();
		return o;
	}
	
	public String toJava()throws ParseException
	{
		if (op.is("and")) return "&&";
		else if (op.is("or")) return "||";
		else if (op.is("not")) return "!";
		else return op.text;
	}
}
