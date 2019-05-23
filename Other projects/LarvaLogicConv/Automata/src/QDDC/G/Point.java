package QDDC.G;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;
import Lustre.Lustre;
import QDDC.QDDC;
import QDDC.P.P;

public class Point extends G{
	P p;
		
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("["))
			cnt++;
		else throw new ParseException("[ expected: " + Tokenizer.debugShow(tokens, cnt));
		p = new P();
		cnt = p.parse(tokens, cnt);
		
		if (tokens.get(cnt).is("]0"))
				cnt++;
			else
				throw new ParseException("]0 Expected: " + Tokenizer.debugShow(tokens, cnt));
			
		
		return cnt;
	}
	
	public String toString()
	{
		return "[" + p + "]0";
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			p.createAcceptor();
			
			Len len = new Len();
			len.p = p;
			len.operator = new Token(Tokenizer.get("<="), "<=");
			len.value = new Integer(0);
			len.createAcceptor();
			
			Begin begin = new Begin();
			begin.p = p;
			begin.createAcceptor();
			
			acceptor = "point_" + (++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + "(" + getParams("_b:bool;",begin,len) + ") returns (_p:bool);" +
				" let" +
				"  _p = "+begin.getSignature()+" and " + len.getSignature() + ";" +
				" tel");	
		}
	}
}
