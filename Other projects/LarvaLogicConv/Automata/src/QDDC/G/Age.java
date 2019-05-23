package QDDC.G;

import java.util.ArrayList;

import Lustre.Lustre;
import QDDC.QDDC;
import QDDC.P.P;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class Age extends G{

	public P p;
	public Token operator;
	public Object value;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("age"))
			cnt++;
		else throw new ParseException("age expected: " + Tokenizer.debugShow(tokens, cnt));
		p = new P();
		cnt = p.parse(tokens, cnt);
		
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
		return "age (" + p + ") " + operator + " " + value;
	}

	public void createAcceptor()throws Exception
	{
		if (acceptor == null)
		{
			p.createAcceptor();
			acceptor = "age_" + (++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + "(" + getParams("_b:bool; _rt_clock:int; "/* _c:real;*/,p)+") returns (age_p:bool);" +
					" let" +
					"  age_p = age(_b, _rt_clock," + p.getSignature() + ") " + operator + " " + value + ";" +
					" tel");
		}
	}
}
