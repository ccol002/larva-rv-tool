package compiler;

import java.util.ArrayList;
import java.util.HashMap;

public class Invariants extends Compiler{

	public HashMap<String, Invariant> invariants = new HashMap<String, Invariant>();
	
	public Invariants(ParsingString ps, Global context) throws ParseException
	{
		super(ps);
		parse(context);
	}
	
	public void parse(Global context)throws ParseException
	{		
		ArrayList<Token> tokens = new Tokenizer(Tokenizer.INV_MODE).scan(ps.string.toString());
		int cnt = 0;
		while (cnt < tokens.size())
		{
			Invariant inv = new Invariant();
			cnt = inv.parse(tokens, cnt);
			invariants.put(inv.name.text, inv);
		}
	}
}
