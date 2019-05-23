package QDDC.P;

import java.util.ArrayList;

import Lustre.Lustre;
import QDDC.QDDC;

import parsing.ParseException;
import parsing.Token;

public class BoolValue extends P{

	Object value;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
			if (tokens.get(cnt).is("true")||tokens.get(cnt).is("false"))
				value = new Boolean(tokens.get(cnt).text);
			return cnt+1;
		}
	
	public String toString()
	{
		return value.toString();
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			
			acceptor = "p_" + (++QDDC.unique);
			Lustre.logic.addParse("node " + acceptor + "() returns (p:bool);" +
					" let" +
					" p = " + value.toString() + ";" +
					" tel");
		}
	}
	
}
