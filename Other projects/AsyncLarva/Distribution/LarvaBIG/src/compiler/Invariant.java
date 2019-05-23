package compiler;

import java.util.ArrayList;

public class Invariant {

	Token name;
	Token returnType;
	ArrayList<Token> call = new ArrayList<Token>();
	boolean initialization = false;
	
	public int parse(ArrayList<Token> tokens, int cnt)throws ParseException
	{
		if (cnt < tokens.size() && tokens.get(cnt).isIdentifier())
			returnType= tokens.get(cnt);
		else
			throw new ParseException("Return Type Expected: " + Tokenizer.debugShow(tokens, cnt));
		cnt++;

		if (cnt < tokens.size() && tokens.get(cnt).isIdentifier())
			name = tokens.get(cnt);
		else
			throw new ParseException("Invariant Name: " + Tokenizer.debugShow(tokens, cnt));
		cnt++;

		if (cnt < tokens.size() && tokens.get(cnt).is("="))
			cnt++;
		else
			throw new ParseException("\"=\" Expected: " + Tokenizer.debugShow(tokens, cnt));

		while (cnt < tokens.size() && tokens.get(cnt).isNot(";"))
			call.add(tokens.get(cnt++));
		
		if (cnt == tokens.size() && tokens.get(cnt).isNot(";"))
			throw new ParseException("\";\" Expected: " + Tokenizer.debugShow(tokens, cnt));
		cnt++;//;
		
		if (cnt < tokens.size() && tokens.get(cnt).is("enable"))
		{
			cnt++;
			initialization= true;
		}	
		
		return cnt;
	}
	
	public Invariant()
	{	}
	
	public String toString()
	{
		return name.toString();
	}
}
