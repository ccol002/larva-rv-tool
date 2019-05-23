package QDDC.G;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;
import Lustre.Logic;
import Lustre.Lustre;
import Lustre.Node;
import QDDC.QDDC;
import QDDC.F.F;
import QDDC.P.P;

public class Stable extends G{
	
	P p;
	Object value;
	
	//stable(P,l)
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("stable"))
			cnt++;
		else throw new ParseException("stable expected: " + Tokenizer.debugShow(tokens, cnt));
		ArrayList<Token> subtokens = Tokenizer.startingEnding(cnt, "(", ")", tokens);
		cnt += subtokens.size()+2;
		p = new P();
		int cnt2 = p.parse(subtokens, 0);
		
		if (subtokens.get(cnt2).is(","))
			cnt2++;
		else
			throw new ParseException(", Expected! " + Tokenizer.debugShow(subtokens, cnt2));
		 
		value = subtokens.get(cnt2).getNumber();
		cnt2++;
		
		if (cnt2 == subtokens.size())
			return cnt;
		else
			throw new ParseException("Unreached end of statement: " + Tokenizer.debugShow(subtokens, cnt2));
	}
	
	public String toString()
	{
		return "stable (" + p + "," + value + ")";
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			p.createAcceptor();
			
			Age a = new Age();
			a.p = p;
			a.value = value;
			a.operator = new Token(Tokenizer.get("<"), "<");
			a.createAcceptor();
					
			acceptor = "stable_" + (++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + "("+getParams("_b:bool;_rt_clock:int;",p,a) +") returns (_p:bool);" +
				" let" +
				" _p = not ((false->pre(" + p.getSignature() + ")) and not(" + p.getSignature() + ") and (false->pre("+a.getSignature()+")));" +
				" tel");	
		}
	}
}
