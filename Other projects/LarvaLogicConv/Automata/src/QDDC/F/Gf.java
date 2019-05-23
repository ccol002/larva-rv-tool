package QDDC.F;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;
import QDDC.G.G;

public class Gf extends F {

	G g;
	
	public Gf(){}
	
	public Gf(G g)
	{
		this.g = g;
	}
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
	
		g = new G();
		cnt = g.parse(tokens, cnt);
		return cnt;
	}

	public int specialParse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		g = new G();
		cnt = g.specialParse(tokens, cnt);
		return cnt;
	}
	
	public String toString()
	{
		return g.toString();
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null)
		{
			g.createAcceptor();
			acceptor = g.acceptor;
		}
	}
	
}
