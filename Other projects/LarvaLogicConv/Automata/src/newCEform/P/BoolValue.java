package newCEform.P;

import java.util.ArrayList;

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
	
	public newPEA.Bool toPEABool()
	{
		return new newPEA.BoolValue((Boolean)value);
	}
	
}
