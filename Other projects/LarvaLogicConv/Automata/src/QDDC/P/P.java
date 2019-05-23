package QDDC.P;

import java.util.ArrayList;

import Lustre.Logic;
import Lustre.Lustre;
import QDDC.QDDC;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class P extends QDDC{

	P p;
	Unary u;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		if (tokens.get(cnt).is("true") || tokens.get(cnt).is("false"))
		{
			p = new BoolValue();
			cnt = p.parse(tokens, cnt);
		}	
		else if (tokens.get(cnt).isIdentifier())
		{
			p = new Variable();
			cnt = p.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("rising"))
		{
			p = new Rising();
			cnt = p.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("not"))
		{
			u = new Unary();
			cnt = u.parse(tokens, cnt);
			cnt = parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("("))
		{
			cnt = parse(tokens, cnt + 1);
			if (tokens.get(cnt).is(")"))
					cnt++;
			else
				throw new ParseException(") Expected: " + Tokenizer.debugShow(tokens, cnt));
		}
		else
			throw new ParseException("Unexpected token: " + Tokenizer.debugShow(tokens, cnt));
		
		if (cnt < tokens.size() && (tokens.get(cnt).is("and") || tokens.get(cnt).is("or")))
		{
			p = new Bool(p);
			cnt = p.parse(tokens, cnt);
		}	
		return cnt;
	}

	public static boolean tryParse(ArrayList<Token> tokens, int cnt) throws ParseException {
		if (tokens.get(cnt).is("true") || tokens.get(cnt).is("false"))
		{
			return true;
		}	
		else if (tokens.get(cnt).isIdentifier() || tokens.get(cnt).is("rising"))
		{
			return true;
		}
		else if (tokens.get(cnt).is("not"))
		{
			return tryParse(tokens, cnt+1);
		}
		else if (tokens.get(cnt).is("("))
		{
			return tryParse(tokens, cnt + 1);
		}
		else
			return false;
	}

	
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			p.createAcceptor();
			if (u == null)
				this.acceptor = p.acceptor; //no need to create another acceptor (no unary)
			else
			{
				acceptor = "p_" + (++QDDC.unique);
				Lustre.logic.addParse("node " + acceptor + "(" + getParams(p) + ") returns (_p:bool);" +
						" let" +
						"  _p = " + u.toString() + "(" + p.getSignature() + " );" +
						" tel");
			}			
		}
	}
	
	public String toString()
	{
		String string;
		if (u != null)
			string = u.toString() + "(" + p + ")";
		else
			string = p.toString();
		return string;
	}
}
