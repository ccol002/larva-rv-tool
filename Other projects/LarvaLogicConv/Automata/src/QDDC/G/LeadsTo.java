package QDDC.G;

import java.util.ArrayList;

import Lustre.Lustre;
import QDDC.QDDC;
import QDDC.F.F;
import QDDC.P.P;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class LeadsTo extends G{
	P lhs;
	P rhs;
	Object value;
		
	public LeadsTo(P p)
	{
		lhs = p;
	}
	
	//starting from the operator
	//P leadsto(l) P
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("leadsto"))
			cnt++;
		else throw new ParseException("leadsto Expected: " + Tokenizer.debugShow(tokens, cnt));

		int brackets = 0;
		while (tokens.get(cnt).is("("))
		{
			brackets++;
			cnt++;
		}
		value = tokens.get(cnt).getNumber();
		cnt++;
		while (brackets > 0 && tokens.get(cnt).is(")"))
		{
			brackets--;
			cnt++;
		}
		if (brackets != 0) throw new ParseException(") Expected: " + Tokenizer.debugShow(tokens, cnt));
		
		rhs = new P();
		cnt = rhs.parse(tokens, cnt);
		return cnt;
	}
	
	public String toString()
	{
		return "(" + lhs + ") leadsto(" + value + ") (" + rhs + ")";
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			rhs.createAcceptor();
			lhs.createAcceptor();
			acceptor = "leadsto_"+(++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + "("+ getParams("_b:bool; _rt_clock:int;",rhs, lhs) +")returns(_p:bool);" +
					" let " +
					"  _p = " + "age(_b,_rt_clock," + lhs.getSignature() + ") <= " + value + " or " + rhs.getSignature() + ";" +
					" tel");
		}
	}
	
}
