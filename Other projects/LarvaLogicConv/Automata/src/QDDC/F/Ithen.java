package QDDC.F;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;
import Lustre.Logic;
import Lustre.Node;
import QDDC.QDDC;
import QDDC.G.G;
import QDDC.P.P;

public class Ithen extends F{
	
	G g;
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException {
		
		if (tokens.get(cnt).is("ithen"))
			cnt++;
		else throw new ParseException("ithen expected: " + Tokenizer.debugShow(tokens, cnt));
		ArrayList<Token> subtokens = Tokenizer.startingEnding(cnt, "(", ")", tokens);
		cnt += subtokens.size()+2;
		g = new G();
		int cnt2 = g.parse(subtokens, 0);
		if (cnt2 == subtokens.size())
			return cnt;
		else
			throw new ParseException("Unreached end of statement: " + Tokenizer.debugShow(subtokens, cnt2));
	}
	
	public String toString()
	{
		return "ithen (" + g + ")";
	}
	
	public void createAcceptor()throws Exception
	{
		if (acceptor == null){
			g.createAcceptor();
			
			Node n = Logic.nodes.get(g.acceptor);
			String params = "";
			for (Lustre.Variable v:n.inputVars)
				params += v.getName() + ",";
			params = params.substring(0,params.length()-1);
//			
//			acceptor = "ithen_" + (++QDDC.unique);
//			Lustre.Lustre.logic.addParse("node " + acceptor + "("+getParams("_b:bool;",g) +") returns (_p:bool);" +
//				" var b:bool; temp:bool; temp2:bool;" +
//				" let" +
//				" temp = " + g.acceptor + "(_b," + params + ");" +
//				" temp2 = " + g.acceptor + "(after(_b) and not temp," + params + ");" +
//				" temp = if (temp) then true else temp2;" +
//				"  _p = temp;" +
//				" tel");
			
//			ThenStar ts = new ThenStar(new Gf(g));
//			ts.rhs = new Gf(g);
//			ts.createWeakAcceptor();
			
//			acceptor = "ithen_" + (++QDDC.unique);
//			Lustre.Lustre.logic.addParse("node " + acceptor + "("+getParams("_b:bool;",ts) +") returns (_p:bool);" +
//				" let" +
//				"  _p = "+ts.getSignature()+";" +
//				" tel");
			
			g.createAcceptor();
			
			acceptor = "ithen_" + (++QDDC.unique);
			Lustre.Lustre.logic.addParse("node " + acceptor + "("+getParams("_b:bool;",g) +") returns (_p:bool);" +
				" var temp:bool;" +
				" let" +
				"  temp = ((not _p) and (true->pre _p));" +//if (_b) then start else ((not _p) and pre temp);
				"  _p = "+g.acceptor+"(_b or temp, "+params+");" +
				" tel");
			
		}
	}
}
