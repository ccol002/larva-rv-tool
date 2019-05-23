package QDDC.G;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;
import Lustre.Lustre;
import QDDC.QDDC;
import QDDC.P.P;

public class Sigma extends G{
	P p;
	Token operator;
	Object value;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("sigma"))
			cnt++;
		else throw new ParseException("sigma expected: " + Tokenizer.debugShow(tokens, cnt));
		p = new P();
		cnt = p.parse(tokens, cnt);
		
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
		return "sigma (" + p + ") " + operator + " " + value;
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			p.createAcceptor();
			acceptor = "sigma_" + (++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + "(" + getParams("_b:bool; _rt_clock:int;", p) + ") returns (_p:bool);" +
				" let" +
				"  _p = since(_ b,_rt_clock,"+ p.getSignature() +") " + operator.toString() + " " + value + ";"  +
				" tel");	
		}
	}
}
