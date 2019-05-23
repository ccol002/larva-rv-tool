package CEform;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import CEform.formula.Formula;
import Lustre.Logic;
import Lustre.Lustre;
import QDDC.F.F;
import QDDC.G.G;

import parsing.ParseException;
import parsing.ParsingString;
import parsing.Token;
import parsing.Tokenizer;

public class CEform{

	public Formula formula;//the initial F

	public void parse(ParsingString ps) throws ParseException{
		if (ps.hasMore())
		{
			Tokenizer tok = new Tokenizer(Tokenizer.CEform_MODE);
			ArrayList<Token> tokens = tok.scan(ps.toString());
			
			formula = new Formula();
			int cnt = formula.parse(tokens, 0);
			if (cnt != tokens.size())
				throw new ParseException("Unreached end of statement! " + Tokenizer.debugShow(tokens, cnt));
//			else if (F.pendingBrackets != 0 || G.pendingBrackets != 0)
//				throw new ParseException("Error Found in bracketing! " + Tokenizer.debugShow(tokens, cnt));
		}
	}
	
	public boolean isSDTP()throws ParseException
	{
		return formula.isSDTP();
	}
	
	public boolean isSUTP()throws ParseException
	{
		return formula.isSUTP();
	}
	
	public String toString()
	{
		return formula.toString();
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
//				CE ce = new CE();
//				ce.parse(new ParsingString());
//				CEform ceform = ce.getCEFormula();
//				System.out.println(ceform);
				CEform ceform = new CEform();
				ceform.parse(new ParsingString(args[0]));
				System.out.println(ceform);
				System.out.println("SDTP:" + ceform.isSDTP());
				System.out.println("SUTP:" + ceform.isSUTP());
				System.out.println(ceform.formula.createAutomaton());
				//System.out.println(ceform.formula.toLarva());
				if (args.length > 2 && args[1].equals("-o"))
				{
					new File(args[2].substring(0,args[2].lastIndexOf("\\"))).mkdirs();
					try{
						PrintWriter pw1 = new PrintWriter(args[2]);
						pw1.write(ceform.formula.toLarva());
						pw1.close();
					}catch(Exception ex)
					{
						System.out.println("Error creating output file: " + args[2]);
						ex.printStackTrace();
					}
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
}
