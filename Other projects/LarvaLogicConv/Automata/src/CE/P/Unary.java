package CE.P;

import java.util.ArrayList;

import Lustre.Lustre;
import QDDC.QDDC;

import parsing.ParseException;
import parsing.Token;

public class Unary{
	
	Token token;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		if (tokens.get(cnt).is("not"))
			token = tokens.get(cnt);
		return cnt+1;
	}
	
	public String toString()
	{
		return token.text;
	}
	
}
