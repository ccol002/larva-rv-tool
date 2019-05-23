package QDDC.F;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;

public class Unary{
	
	Token token;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		if (tokens.get(cnt).is("not"))
		{
			token = tokens.get(cnt);
			cnt++;
		}
		return cnt;
	}
	
	public String toString()
	{
		return token.text;
	}
}
