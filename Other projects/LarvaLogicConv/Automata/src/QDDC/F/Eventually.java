package QDDC.F;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;
import Lustre.Logic;
import Lustre.Lustre;
import Lustre.Node;
import QDDC.QDDC;
import QDDC.P.P;

public class Eventually extends F{
	
	F f;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("eventually"))
			cnt++;
		else throw new ParseException("eventually expected: " + Tokenizer.debugShow(tokens, cnt));
		ArrayList<Token> subtokens = Tokenizer.startingEnding(cnt, "(", ")", tokens);
		cnt += subtokens.size()+2;
		f = new F();
		int cnt2 = f.parse(subtokens, 0);
		if (cnt2 == subtokens.size())
			return cnt;
		else
			throw new ParseException("Unreached end of statement: " + Tokenizer.debugShow(subtokens, cnt2));
	}
	
	public String toString()
	{
		return "eventually (" + f + ")";
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			f.createAcceptor();
			
			acceptor = "eventually_" + (++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + "("+getParams("_b:bool;",f) +") returns (_p:bool);" +
				" let" +
				" _p = (false->pre _p) or (" + f.getSignature() + ");" +
				" tel");	
		}
	}
}
