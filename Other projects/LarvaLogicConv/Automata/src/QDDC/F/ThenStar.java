package QDDC.F;

import java.util.ArrayList;

import Lustre.Lustre;
import QDDC.QDDC;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class ThenStar extends F{
	Gf lhs;
	F rhs;
		
	public ThenStar(Gf g)
	{
		lhs = g;
	}
	
	//starting from the operator
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("thenstar"))
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
			
			String lhsparams = getOrderedParamsNoTypes("_b,", lhs);
			lhsparams = lhsparams.substring(3, lhsparams.length());
			
			String rhsparams = getOrderedParamsNoTypes("_b,", rhs);
			rhsparams = rhsparams.substring(3, rhsparams.length());
		
			Lustre.logic.addParse("node " + acceptor + "("+ getParams("_b:bool;",rhs, lhs) +")returns(_p:bool);" +
					" var lhs,rhs:bool;" +
					" lhsstart,rhsstart:bool;" +
					" let " +
					"  lhsstart = _b or (false->pre first(rhsstart,not rhs));" +
					"  rhsstart = (first(lhsstart,not lhs));" +
					"  lhs = " + lhs.acceptor + "(lhsstart," + lhsparams + ");" +
					"  rhs = " + rhs.acceptor + "(rhsstart," + rhsparams + ");" +
					"  _p = rhs" + ";" +
					" tel");
		
//			Lustre.logic.addParse("node " + acceptor + "("+ getParams("_b:bool;",rhs, lhs) +")returns(_p:bool);" +
//					" var lhs,rhs:bool;nowlhs:bool;" +
//					" let " +
//					"  lhs = " + lhs.acceptor + "(first(rising(true->pre(not nowlhs)), not(rhs)) and after(_b)," + lhsparams + ");" +
//					"  rhs = " + rhs.acceptor + "(first(rising(false->pre(nowlhs)), not(lhs))," + rhsparams + ");" +
//					"  nowlhs = (pre(nowlhs) and rising(not lhs)) or (not (pre nowlhs) and rising(not rhs));" +
//					"  _p = (lhs and nowlhs) or (rhs and nowrhs)" + ";" +
//					" tel");
		}
	}
	
	public void createWeakAcceptor()throws Exception
	{
		if (acceptor == null){
			rhs.createAcceptor();
			lhs.createAcceptor();
			acceptor = "then_"+(++QDDC.unique);
			
			String lhsparams = getOrderedParamsNoTypes("_b,", lhs);
			lhsparams = lhsparams.substring(3, lhsparams.length());
			
			String rhsparams = getOrderedParamsNoTypes("_b,", rhs);
			rhsparams = rhsparams.substring(3, rhsparams.length());
		
			Lustre.logic.addParse("node " + acceptor + "("+ getParams("_b:bool;",rhs, lhs) +")returns(_p:bool);" +
					" var lhs,rhs:bool;" +
					" lhsstart,rhsstart:bool;" +
					" let " +
					"  lhsstart = _b or (first(rhsstart,not rhs));" +
					"  rhsstart = (first(lhsstart,not lhs));" +
					"  lhs = " + lhs.acceptor + "(lhsstart," + lhsparams + ");" +
					"  rhs = " + rhs.acceptor + "(rhsstart," + rhsparams + ");" +
					"  _p = rhs or lhs;" +
					" tel");
		
//			Lustre.logic.addParse("node " + acceptor + "("+ getParams("_b:bool;",rhs, lhs) +")returns(_p:bool);" +
//					" var lhs,rhs:bool;nowlhs:bool;" +
//					" let " +
//					"  lhs = " + lhs.acceptor + "(first(rising(true->pre(not nowlhs)), not(rhs)) and after(_b)," + lhsparams + ");" +
//					"  rhs = " + rhs.acceptor + "(first(rising(false->pre(nowlhs)), not(lhs))," + rhsparams + ");" +
//					"  nowlhs = (pre(nowlhs) and rising(not lhs)) or (not (pre nowlhs) and rising(not rhs));" +
//					"  _p = (lhs and nowlhs) or (rhs and nowrhs)" + ";" +
//					" tel");
		}
	}
	
}
