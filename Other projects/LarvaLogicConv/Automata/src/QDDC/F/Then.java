package QDDC.F;

import java.util.ArrayList;

import Lustre.Lustre;
import QDDC.QDDC;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class Then extends F{
	Gf lhs;
	F rhs;
		
	public Then(Gf g)
	{
		lhs = g;
	}
	
	//starting from the operator
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("then"))
			cnt++;
		else throw new ParseException("Operator Expected: " + Tokenizer.debugShow(tokens, cnt));

		rhs = new F();
		cnt = rhs.parse(tokens, cnt);
		return cnt;
	}
	
	public String toString()
	{
		return "(" + lhs + ") then (" + rhs + ")";
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			rhs.createAcceptor();
			lhs.createAcceptor();
			acceptor = "then_"+(++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + "("+ getParams("_b:bool;",rhs, lhs) +")returns(_p:bool);" +
					" let " +
					"  _p = " + rhs.acceptor + "(first(_b,not(" + lhs.getSignature() +  ")),"+rhs.getParameterList(1,0)+");" +
					" tel");
		}
	}
	
}
