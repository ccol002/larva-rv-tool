package QDDC.P;

import java.util.ArrayList;

import Lustre.Logic;
import Lustre.Lustre;
import QDDC.QDDC;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class Bool extends P{

	P lhs;
	Token operator;
	P rhs;
		
	public Bool(P p)
	{
		lhs = p;
	}
	
	//starting from the operator
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		if (tokens.get(cnt).is("and")||tokens.get(cnt).is("or"))
			operator = tokens.get(cnt);
		else throw new ParseException("Operator Expected: " + Tokenizer.debugShow(tokens, cnt));
		cnt++;
		rhs = new P();
		cnt = rhs.parse(tokens, cnt);
		return cnt;
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			lhs.createAcceptor();
			rhs.createAcceptor();
			acceptor = "p_" + (++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + " ("+ getParams(lhs,rhs) + ") returns (_p:bool);" +
					" let" +
					"  _p = " + lhs.getSignature() + " " + operator.text + " " + rhs.getSignature() + ";" +
					" tel");
		}
	}
	
	public String toString()
	{
		return "(" + lhs + ") " + operator + " (" + rhs + ")";
	}
}
