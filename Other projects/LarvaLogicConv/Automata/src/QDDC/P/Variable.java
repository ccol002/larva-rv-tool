package QDDC.P;

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
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			
			acceptor = "p_" + (++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + "("+token.text+":bool) returns (_p:bool);" +
					" let" +
					"  _p = " + token.text + ";" +
					" tel");
		}
	}
}
