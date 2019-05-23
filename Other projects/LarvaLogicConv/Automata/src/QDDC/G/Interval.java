package QDDC.G;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;
import Lustre.Lustre;
import QDDC.QDDC;
import QDDC.P.P;

public class Interval extends G{
	P p;
		
	public static int parse(ArrayList<Token> tokens, int cnt, G g) throws ParseException {
		
		if (tokens.get(cnt).is("[["))
			cnt++;
		else throw new ParseException("[[ expected: " + Tokenizer.debugShow(tokens, cnt));
		P p = new P();
		cnt = p.parse(tokens, cnt);
		
		if (tokens.get(cnt).is("]"))
		{
			cnt++;
			g.g = new Interval();
			((Interval)g.g).p = p;			
		}
		else if (tokens.get(cnt).is("]+"))
		{
			cnt++;
			g.g = new IntervalPlus();
			((IntervalPlus)g.g).p = p;			
		}
		else if (tokens.get(cnt).is("]]"))
		{
			cnt++;
			g.g = new IntervalClosed();
			((IntervalClosed)g.g).p = p;			
		}
		else if (tokens.get(cnt).is("]]+"))
		{
			cnt++;
			g.g = new IntervalClosedPlus();
			((IntervalClosedPlus)g.g).p = p;			
		}
		else
			throw new ParseException("] Expected: " + Tokenizer.debugShow(tokens, cnt));
			
		return cnt;
	}
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("[["))
			cnt++;
		else throw new ParseException("[[ expected: " + Tokenizer.debugShow(tokens, cnt));
		p = new P();
		cnt = p.parse(tokens, cnt);
		
		if (tokens.get(cnt).is("]"))
				cnt++;
			else
				throw new ParseException("] Expected: " + Tokenizer.debugShow(tokens, cnt));
			
		
		return cnt;
	}
	
	public String toString()
	{
		return "[[" + p + "]";
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			p.createAcceptor();
			acceptor = "interval_" + (++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + "(" + getParams("_b:bool;",p) + ") returns (_p:bool);" +
				" let" +
				"  _p = strict_after(_b)" +
				" and pre(always_since(_b," + p.getSignature() + ") );" +
				" tel");	
		}
	}
}
