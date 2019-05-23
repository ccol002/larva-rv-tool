package newCEform.P;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;
import Lustre.Logic;
import Lustre.Lustre;
import Lustre.Node;
import QDDC.QDDC;
import QDDC.G.G;

public class Rising extends P{
	
	P p;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("rising"))
			cnt++;
		else throw new ParseException("rising expected: " + Tokenizer.debugShow(tokens, cnt));
		ArrayList<Token> subtokens = Tokenizer.startingEnding(cnt, "(", ")", tokens);
		cnt += subtokens.size()+2;
		p = new P();
		int cnt2 = p.parse(subtokens, 0);
		if (cnt2 == subtokens.size())
			return cnt;
		else
			throw new ParseException("Unreached end of statement: " + Tokenizer.debugShow(subtokens, cnt2));
	}
	
	public String toString()
	{
		return "rising (" + p + ")";
	}
	
	public newPEA.Bool toPEABool()
	{
		try{
			throw new Exception("undefined!");
		}catch(Exception ex){ex.printStackTrace();}
		return null;
	}
}
