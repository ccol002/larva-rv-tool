package newCEform.P;

import java.util.ArrayList;

import Lustre.Lustre;
import QDDC.QDDC;

import parsing.ParseException;
import parsing.Token;

public class Variable extends P{

Token token;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		if (tokens.get(cnt).isIdentifier())
			token = tokens.get(cnt);
		return cnt+1;
	}
	
	public String toString()
	{
		return token.text;
	}
	
	public newPEA.Bool toPEABool()
	{
		newPEA.Variable variable = new newPEA.Variable();
		variable.name = token.text;
		return variable;
	}
}
