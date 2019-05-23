package QDDC.F;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;
import Lustre.Lustre;
import QDDC.QDDC;
import QDDC.P.P;

public class End extends F{
	
	public P p;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("end"))
			cnt++;
		else throw new ParseException("end expected: " + Tokenizer.debugShow(tokens, cnt));
		ArrayList<Token> subtokens = Tokenizer.startingEnding(cnt, "(", ")", tokens);
		cnt += subtokens.size()+2;
		p = new P();
		int cnt2 = p.parse(subtokens, 0);
		if (cnt2 == subtokens.size())
			return cnt;
		else
			throw new ParseException("Unreached end of statement: " + Tokenizer.debugShow(subtokens, cnt2));
	}
	
	public String toString()
	{
		return "end (" + p + ")";
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			p.createAcceptor();
			acceptor = "end_" + (++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + "("+getParams("_b:bool;",p) +") returns (_p:bool);" +
				" let" +
				"  _p = after(_b) and " + p.getSignature() +";" +
				" tel");	
		}
	}
}
