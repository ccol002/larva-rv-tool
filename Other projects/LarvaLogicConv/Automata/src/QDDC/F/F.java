package QDDC.F;

import java.util.ArrayList;

import Lustre.Lustre;
import Lustre.Node;
import QDDC.QDDC;
import QDDC.G.BoolG;
import QDDC.G.G;
import QDDC.G.Stable;
import QDDC.P.P;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;

public class F extends QDDC{
	
	F f;
	Unary u; 
	public Lustre l;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
	
		if (tokens.get(cnt).is("end"))
		{
			f = new End();
			cnt = f.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("agef"))
		{
			f = new AgeF();
			cnt = f.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("repeat"))
		{
			f = new Repeat();
			cnt = f.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("always"))
		{
			f = new Always();
			cnt = f.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("eventually"))
		{
			f = new Eventually();
			cnt = f.parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("ithen"))
		{
			f = new Ithen();
			cnt = f.parse(tokens, cnt);
		}
		else if (P.tryParse(tokens, cnt))
		{
			f = new Gf();
			cnt = ((Gf)f).specialParse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("not"))
		{
			u = new Unary();
			cnt = u.parse(tokens, cnt);
			cnt = parse(tokens, cnt);
		}
		else if (tokens.get(cnt).is("("))
		{
			cnt = parse(tokens, cnt + 1);
			if (tokens.get(cnt).is(")"))
				cnt++;
			else throw new ParseException(") Expected: " + Tokenizer.debugShow(tokens, cnt));
		}
		else
		{
			f = new Gf();
			cnt = f.parse(tokens, cnt);
		}
		
		if (cnt < tokens.size() && (tokens.get(cnt).is("and") || tokens.get(cnt).is("or")))			
		{
			if (f instanceof Gf)
			{
				G g = ((Gf)f).g;
				f = new Gf();
				((Gf)f).g = new BoolG(g);
				cnt = ((Gf)f).g.parse(tokens, cnt);
			}
			else
			{
				f = new Bool(f);
				cnt = f.parse(tokens, cnt);
			}
		}
		else if (cnt < tokens.size() && ((tokens.get(cnt).is("then") || (tokens.get(cnt).is("thenstar"))) && f instanceof Gf))
		{
			if (tokens.get(cnt).is("then"))
				f = new Then((Gf)f);
			else
				f = new ThenStar((Gf)f);
			cnt = f.parse(tokens, cnt);
		}
		return cnt;
	}

	public String toString()
	{
		String string;
		if (u != null)
			string = u.toString() + "(" + f + ")";
		else
			string = f.toString();
		return string;
	}

	public Lustre toLustre()throws Exception
	{
		this.l = new Lustre();
		//f.toLustre(l);
		Lustre.afterNode();
		Lustre.strictAfterNode();
		Lustre.starterNode();
		Lustre.alwaysSinceNode();
		Lustre.ageNodeRT();
		Lustre.countNode();
		Lustre.firstNode();
		Lustre.sinceNodeRT();
		for (Node n: l.logic.nodes.values())
			n.mainNode = false;
		f.createAcceptor();
		return this.l;
	}

	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			f.createAcceptor();
			if (u == null)
				this.acceptor = f.acceptor; //no need to create another acceptor (no unary)
			else
			{
				acceptor = "p_" + (++QDDC.unique);
				Lustre.logic.addParse("node " + acceptor + "(" + getParams(f) + ") returns (_p:bool);" +
						" let" +
						"  _p = " + u.toString() + "(" + f.getSignature()+ " );" +
						" tel");
			}			
		}
	}
	
}
