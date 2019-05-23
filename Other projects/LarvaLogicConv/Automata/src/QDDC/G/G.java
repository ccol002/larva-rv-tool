package QDDC.G;

import java.util.ArrayList;

import Lustre.Lustre;
import QDDC.QDDC;
import QDDC.F.F;
import QDDC.F.Ithen;
import QDDC.P.P;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;


public class G extends QDDC{

	G g;
		
	public int specialParse(ArrayList<Token> tokens, int cnt) throws ParseException {
			P p = new P();
			cnt = p.parse(tokens, cnt);
		
			if (tokens.get(cnt).is("leadsto"))
				g = new LeadsTo(p);
			else if (tokens.get(cnt).is("persist"))
				g = new Persist(p);
			else
				throw new ParseException("Unexpected Token: " + Tokenizer.debugShow(tokens, cnt));
			
			cnt = g.parse(tokens, cnt);
			return cnt;
		}
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
	
		if (tokens.get(cnt).is("begin"))
		{
			g = new Begin();
			cnt = g.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("stable"))
		{
			g = new Stable();
			cnt = g.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("bounded"))
		{
			g = new Bounded();
			cnt = g.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("[["))
		{
			g = new G();
			cnt = Interval.parse(tokens, cnt,g);
			g = g.g;
		}
		else if (tokens.get(cnt).is("["))
		{
			g = new Point();
			cnt = g.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("len"))
		{
			g = new Len();
			cnt = g.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("sigma"))
		{
			g = new Sigma();
			cnt = g.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("age"))
		{
			g = new Age();
			cnt = g.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("count"))
		{
			g = new Count();
			cnt = g.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("("))
		{
			cnt = parse(tokens, cnt + 1);
			if (tokens.get(cnt).is(")"))
				cnt++;
			else throw new ParseException(") Expected: " + Tokenizer.debugShow(tokens, cnt));
		}	
		else
			throw new ParseException("Unexpected token: " + Tokenizer.debugShow(tokens, cnt));
		
		if (cnt < tokens.size() && (tokens.get(cnt).is("and") || tokens.get(cnt).is("or")))
		{
			g = new BoolG(g);
			cnt = g.parse(tokens, cnt);
		}
		return cnt;
	}

	public void createAcceptor()throws Exception
	{
		if (acceptor == null)
		{
			g.createAcceptor();
			acceptor = g.acceptor;
		}
	}
	
	public String toString()
	{
		return g.toString();
	}
}
