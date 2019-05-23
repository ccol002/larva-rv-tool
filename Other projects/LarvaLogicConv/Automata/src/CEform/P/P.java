package CEform.P;

import java.util.ArrayList;

import CEform.CEform;
import Lustre.Logic;
import Lustre.Lustre;
import PEA.PowerSet;
import PEA.Unary.Op;
import QDDC.QDDC;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class P extends CEform{

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

	public PEA.Bool toPEABool()
	{
		PEA.Bool bool = new PEA.Bool();
		if (u != null)
			bool.unary = new PEA.Unary();
		
		bool.lhs = p.toPEABool();
		return bool;
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

	public boolean isSDTP()
	{
		return true;
	}
	
	public boolean isSUTP()
	{
		return true;
	}
}
