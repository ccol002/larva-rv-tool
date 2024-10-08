package newCEform.events;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;

public class Variable extends Event{

	public Token token;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		if (tokens.get(cnt).isIdentifier())
			token = tokens.get(cnt);
		return cnt+1;
	}
	
	public String toString()
	{
		return token.text;
	}

}
