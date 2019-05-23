package QDDC;

import java.util.ArrayList;

import Lustre.Logic;
import Lustre.Lustre;
import QDDC.F.F;
import QDDC.G.G;

import parsing.ParseException;
import parsing.ParsingString;
import parsing.Token;
import parsing.Tokenizer;

public class QDDC extends Logic{

	F f;//the initial F
	public static int unique = -1;
	public String acceptor;//the name of the node
	
	public String getSignature()
	{
		return Logic.nodes.get(acceptor).getSignature();
	}
	
	public String getParameterList(int i, int j)
	{
		String params = Logic.nodes.get(acceptor).getParamsNoTypes();
		int start = 0;
		while (i-- > 0)
			start = params.indexOf(",", start) + 1;
		int end = params.length()-1;
		while (j-- > 0)
			end = params.lastIndexOf(",", end) - 1;
		return params.substring(start, end+1);
	}
	
	public void parse(ParsingString ps) throws ParseException{
		if (ps.hasMore())
		{
			Tokenizer tok = new Tokenizer(Tokenizer.QDDC_MODE);
			ArrayList<Token> tokens = tok.scan(ps.toString());
			
			f = new F();
			int cnt = f.parse(tokens, 0);
			if (cnt != tokens.size())
				throw new ParseException("Unreached end of statement! " + Tokenizer.debugShow(tokens, cnt));
//			else if (F.pendingBrackets != 0 || G.pendingBrackets != 0)
//				throw new ParseException("Error Found in bracketing! " + Tokenizer.debugShow(tokens, cnt));
		}
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){//the node has not yet been created
			f.createAcceptor();
		}
	}
	
	public String toString()
	{
		return f.toString();
	}
	
	//creates and returns an acceptor
	public Lustre toLustre()throws Exception
	{
		f.toLustre();
		return f.l;
	}
}
