package QDDC.G;

import java.util.ArrayList;

import Lustre.Lustre;
import QDDC.QDDC;
import QDDC.P.P;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class Count extends G{

	P p;
	Token operator;
	Object value;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("count"))
			cnt++;
		else throw new ParseException("count expected: " + Tokenizer.debugShow(tokens, cnt));
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
		return "count (" + p + ") " + operator + " " + value;
	}

	public void createAcceptor()throws Exception
	{
		if (acceptor == null)
		{
			p.createAcceptor();
			acceptor = "count_" + (++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + "(" + getParams("_b:bool;"/* _c:real;*/,p)+") returns (count_p:bool);" +
					" let" +
					"  count_p = count(_b," + p.getSignature() + ") " + operator + " " + value + ";" +
					" tel");
		}
	}
}
