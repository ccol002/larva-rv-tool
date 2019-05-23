package QDDC.F;

import java.util.ArrayList;

import Lustre.Lustre;
import QDDC.QDDC;
import QDDC.G.G;
import QDDC.P.P;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class AgeF extends F{

	G g;
	Token operator;
	Object value;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("agef"))
			cnt++;
		else throw new ParseException("agef expected: " + Tokenizer.debugShow(tokens, cnt));
		g = new G();
		cnt = g.parse(tokens, cnt);
		
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
		return "agef (" + g + ") " + operator + " " + value;
	}

	public void createAcceptor()throws Exception
	{
		if (acceptor == null)
		{
			g.createAcceptor();
			acceptor = "age_" + (++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + "(" + getParams("_b:bool; _rt_clock:int; "/* _c:real;*/,g)+") returns (age_p:bool);" +
					" let" +
					"  age_p = age(_b, _rt_clock," + g.getSignature() + ") " + operator + " " + value + ";" +
					" tel");
		}
	}
}
