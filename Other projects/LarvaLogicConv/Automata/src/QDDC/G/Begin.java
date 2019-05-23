package QDDC.G;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;
import Lustre.Lustre;
import QDDC.QDDC;
import QDDC.P.P;

public class Begin extends G {
	
	P p;
	
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("begin"))
			cnt++;
		else throw new ParseException("begin expected: " + Tokenizer.debugShow(tokens, cnt));
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
		return "begin (" + p + ")";
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			p.createAcceptor();
			acceptor = "begin_"+(++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + "("+ getParams("_b:bool;", p) +")returns(begin_p:bool);" +
					" let " +
					"  begin_p = if (_b and not ("+p.getSignature()+")) then false" +
					"            else if (_b and " + p.getSignature() + ") then true" +
				    "            else false->pre (begin_p);" +
					" tel");
		}
	}
}
