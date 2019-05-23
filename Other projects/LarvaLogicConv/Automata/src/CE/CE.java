package CE;

import java.util.ArrayList;

import parsing.ParseException;
import parsing.ParsingString;
import parsing.Token;
import parsing.Tokenizer;
import CE.F.F;
import CEform.CEform;

//Formula ::= Bool | Bool => Bool | Formula;Formula
//Bool ::= Var > Var | Var < Var | Var = Var | Var >= Var | Var <= Var | boolVar
//Var ::=  Int(Bool) | Num

public class CE {
	
	F f;

	public void parse(ParsingString ps) throws ParseException{
		if (ps.hasMore())
		{
			Tokenizer tok = new Tokenizer(Tokenizer.CE_MODE);
			ArrayList<Token> tokens = tok.scan(ps.toString());
			
			f = new F();
			int cnt = f.parse(tokens, 0);
			if (cnt != tokens.size())
				throw new ParseException("Unreached end of statement! " + Tokenizer.debugShow(tokens, cnt));
//			else if (F.pendingBrackets != 0 || G.pendingBrackets != 0)
//				throw new ParseException("Error Found in bracketing! " + Tokenizer.debugShow(tokens, cnt));
		}
	}
	

	public String toString()
	{
		return f.toString();
	}
	
	public CEform getCEFormula()throws ParseException
	{
		if (f != null)
			return f.getCEFormula();
		else 
			throw new ParseException("No formula found!");
	}
	
	public static void main(String[] args)
	{
		try{
			if (args.length == 0)
			{
				System.out.println("You should specify a mode and a file!!");
			}
			else
			{
				CE ce = new CE();
				ce.parse(new ParsingString(args[0]));
				System.out.println(ce);
				CEform ceform = ce.getCEFormula();
				System.out.println(ceform);
//				CEform ceform = new CEform();
//				ceform.parse(new ParsingString(new StringBuilder("not true")));
//				System.out.println(ceform);
//				System.out.println(ceform.formula.createAutomaton());
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
