package CE.F;

import java.util.ArrayList;

import CE.CE;
import CEform.CEform;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;


public class F extends CE{

	F f;
			
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
	
		if (tokens.get(cnt).is("init"))
		{
			f = new Initialize();
			cnt = f.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("stab"))
		{
			f = new Stable();
			cnt = f.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("prog"))
		{
			f = new Progress();
			cnt = f.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("seq"))
		{
			f = new Sequence();
			cnt = f.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("synch"))
		{
			f = new Synch();
			cnt = f.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("("))
		{
			cnt = parse(tokens, cnt + 1);
			if (tokens.get(cnt).is(")"))
				cnt++;
			else throw new ParseException(") Expected: " + Tokenizer.debugShow(tokens, cnt));
		}	
		else
			throw new ParseException("Unexpected token: " + Tokenizer.debugShow(tokens, cnt));
		
		if (cnt < tokens.size() && (tokens.get(cnt).is("and") || tokens.get(cnt).is("or")))
		{
			f = new Bool(f);
			cnt = f.parse(tokens, cnt);
		}
		return cnt;
	}

	public String toString()
	{
		return f.toString();
	}
	
	public CEform getCEFormula()throws ParseException
	{
		return f.getCEFormula();
	}
}
