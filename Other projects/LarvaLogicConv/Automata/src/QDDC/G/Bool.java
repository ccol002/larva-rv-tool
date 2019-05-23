package QDDC.G;

import java.util.ArrayList;

import Lustre.Lustre;
import QDDC.QDDC;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class Bool extends G{

	G lhs;
	Token operator;
	G rhs;
		
	public Bool(G g)
	{
		lhs = g;
	}
	
	//starting from the operator
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		if (tokens.get(cnt).is("and")||tokens.get(cnt).is("or"))
			operator = tokens.get(cnt);
		else throw new ParseException("Operator Expected: " + Tokenizer.debugShow(tokens, cnt));
		cnt++;
		rhs = new G();
		cnt = rhs.parse(tokens, cnt);
		return cnt;
	}
	
	public String toString()
	{
		return "(" + lhs + ") " + operator + " (" + rhs + ")";
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null)
		{
			lhs.createAcceptor();
			rhs.createAcceptor();
			acceptor = "boolg_" + (++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + "(" + getParams("_b:bool;",lhs,rhs) + ") returns (_p:bool);" +
					" let" +
					"  _p = " + lhs.getSignature() + " " + operator.text + " " + rhs.getSignature() + ";" +
					" tel");
		}
	}
}
