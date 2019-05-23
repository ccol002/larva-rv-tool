package QDDC.G;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;
import Lustre.Lustre;
import QDDC.QDDC;
import QDDC.P.P;

public class Len extends G{
	P p;
	Token operator;
	Object value;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("len"))
			cnt++;
		else throw new ParseException("len expected: " + Tokenizer.debugShow(tokens, cnt));
				
		if (tokens.get(cnt).is("<") || tokens.get(cnt).is("<=")
			|| tokens.get(cnt).is(">") || tokens.get(cnt).is(">=")
			|| tokens.get(cnt).is("=="))
			{
				operator = tokens.get(cnt);
				cnt++;
			}
			else
				throw new ParseException("Operator Expected: " + Tokenizer.debugShow(tokens, cnt));
			
		value = tokens.get(cnt).getNumber();
		cnt++;
		return cnt;
	}
	
	public String toString()
	{
		return "len " + operator + " " + value;
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			acceptor = "len_" + (++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + "(_b:bool; _rt_clock:int) returns (_p:bool);" +
				" let" +
				"  _p = since(_b,_rt_clock,true) " + operator.toString() + " " + value + ";" +
				" tel");	
		}
	}
}
