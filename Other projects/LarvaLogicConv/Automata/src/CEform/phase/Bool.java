package CEform.phase;

import java.util.ArrayList;

import Lustre.Lustre;
import QDDC.QDDC;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class Bool extends Phase{

	Phase lhs;
	Token operator;
	Phase rhs;
		
	public Bool(Phase g)
	{
		lhs = g;
	}
	
	//starting from the operator
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		if (tokens.get(cnt).is("and"))
			operator = tokens.get(cnt);
		else throw new ParseException("Operator Expected: " + Tokenizer.debugShow(tokens, cnt));
		cnt++;
		rhs = new Phase();
		cnt = rhs.parse(tokens, cnt);
		return cnt;
	}
	
	public String toString()
	{
		return "(" + lhs + ") " + operator + " (" + rhs + ")";
	}

	public boolean isSDTP()throws ParseException
	{
		return lhs.isSDTP() && rhs.isSDTP();
	}
	
	public boolean isSUTP()throws ParseException
	{
		return lhs.isSUTP() && rhs.isSUTP();
	}
	
}
