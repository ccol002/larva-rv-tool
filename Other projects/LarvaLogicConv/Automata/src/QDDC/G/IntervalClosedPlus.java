package QDDC.G;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;
import Lustre.Lustre;
import QDDC.QDDC;
import QDDC.P.P;

public class IntervalClosedPlus extends G{
	P p;
		
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("[["))
			cnt++;
		else throw new ParseException("[[ expected: " + Tokenizer.debugShow(tokens, cnt));
		p = new P();
		cnt = p.parse(tokens, cnt);
		
		if (tokens.get(cnt).is("]]+"))
				cnt++;
			else
				throw new ParseException("]]+ Expected: " + Tokenizer.debugShow(tokens, cnt));
			
		
		return cnt;
	}
	
	public String toString()
	{
		return "[[" + p + "]]+";
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			p.createAcceptor();
			
			IntervalClosed interval = new IntervalClosed();
			interval.p = p;
			interval.createAcceptor();
			
			Point point = new Point();
			point.p = p;
			point.createAcceptor();

			acceptor = "int_closed_plus_" + (++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + "(" + getParams("_b:bool;",interval,point) + ") returns (_p:bool);" +
				" let" +
				"  _p = " + interval.getSignature() + " or " + point.getSignature() + ";" + //exp.getSignature() + ";" +
				" tel");	
		}
	}
}
