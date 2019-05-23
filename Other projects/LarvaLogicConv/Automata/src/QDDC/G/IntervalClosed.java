package QDDC.G;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;
import Lustre.Lustre;
import QDDC.QDDC;
import QDDC.F.End;
import QDDC.P.P;

public class IntervalClosed extends G{
	P p;
		
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("[["))
			cnt++;
		else throw new ParseException("[[ expected: " + Tokenizer.debugShow(tokens, cnt));
		p = new P();
		cnt = p.parse(tokens, cnt);
		
		if (tokens.get(cnt).is("]]"))
				cnt++;
			else
				throw new ParseException("]] Expected: " + Tokenizer.debugShow(tokens, cnt));
			
		
		return cnt;
	}
	
	public String toString()
	{
		return "[[" + p + "]]";
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			p.createAcceptor();
			
//			End end = new End();
//			end.p = p;
//			end.createAcceptor();
//			
//			Interval interval = new Interval();
//			interval.p = p;
//			interval.createAcceptor();
//									
			acceptor = "int_closed_" + (++QDDC.unique);
//			Lustre.logic.addParse("node " + acceptor + "(" + getParams("_b:bool;",interval,end) + ") returns (_p:bool);" +
//					" let" +
//					"  _p = " + interval.getSignature()+ " and " + end.getSignature() + ";" +
//					" tel");
			//we will still use this definition or else we will lift this an F formula because end is in F
			Lustre.logic.addParse("node " + acceptor + "(" + getParams("_b:bool;",p) + ") returns (_p:bool);" +
					" let" +
					"  _p = strict_after(_b)" +
					" and always_since(_b," + p.getSignature() + ");" +
					" tel");
		}
	}
}
