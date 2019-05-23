package CE.F;

import java.util.ArrayList;

import Lustre.Lustre;
import QDDC.QDDC;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class Bool extends F{

	F lhs;
	Token operator;
	F rhs;
		
	public Bool(F g)
	{
		lhs = g;
	}
	
	//starting from the operator
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		if (tokens.get(cnt).is("and")||tokens.get(cnt).is("or"))
			operator = tokens.get(cnt);
		else throw new ParseException("Operator Expected: " + Tokenizer.debugShow(tokens, cnt));
		cnt++;
		rhs = new F();
		cnt = rhs.parse(tokens, cnt);
		return cnt;
	}
	
	public String toString()
	{
		return "(" + lhs + ") " + operator + " (" + rhs + ")";
	}
	
}
