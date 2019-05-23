package main;

import java.io.File;
import java.io.PrintWriter;
import CE.CE;
import Lustre.Lustre;
import QDDC.QDDC;
import parsing.ParsingString;
import parsing.ParseException;

public abstract class Main {

	public abstract void parse(ParsingString ps) throws ParseException;
	//public abstract DC toDC();
	
	//public abstract TA toTA();
	
	//public abstract RE toRE();
	
	//public abstract Logic parse();
	
	//public abstract boolean tryRun(String eventStream);
	
	public static void main(String[] args)
	{
		try{
		if (args.length == 0)
		{
			System.out.println("You should specify a mode and a file!!");
			System.out.println("-QDDC");
		}
		else
		{
			int i = 0;
			while (i < args.length)
			{
				if (args[i].equals("-QDDC"))
				{
					if (i+1 < args.length)
					{			
						QDDC qddc = new QDDC();
						qddc.parse(new ParsingString(args[i+1]));
						//System.out.println(qddc.toString());
						System.out.println(qddc.toLustre().original());
						//qddc.toLustre();
						String output = Lustre.toLARVA();
						System.out.println(output);
						if (i+3 < args.length && args[i+2].equals("-o"))
						{
							int bslash = args[i+3].lastIndexOf("\\");
							if (bslash > 0)
								new File(args[i+3].substring(0,args[i+3].lastIndexOf("\\"))).mkdirs();
							try{
								PrintWriter pw1 = new PrintWriter(args[i+3]);
								pw1.write(output);
								pw1.close();
							}catch(Exception ex)
							{
								System.out.println("Error creating output file: " + args[i+3]);
								ex.printStackTrace();
							}
						}
						else
							System.out.println("Warning: No output file specified, output not saved!");
					}
					else
						System.out.println("File name expected!");
				}
				else if (args[i].equals("-LUSTRE"))
				{
					if (i+1 < args.length)
					{			
						Lustre.parseFile(args[i+1]);
						String output = Lustre.toLARVA();
						System.out.println(output);
						if (i+3 < args.length && args[i+2].equals("-o"))
						{
							int bslash = args[i+3].lastIndexOf("\\");
							if (bslash > 0)
								new File(args[i+3].substring(0,bslash)).mkdirs();
							try{
								PrintWriter pw1 = new PrintWriter(args[i+3]);
								pw1.write(output);
								pw1.close();
							}catch(Exception ex)
							{
								System.out.println("Error creating output file: " + args[i+3]);
								ex.printStackTrace();
							}
						}
						else
							System.out.println("Warning: No output file specified, output not saved!");
					}
					else
						System.out.println("File name expected!");
				}
				else if (args[i].equals("-CE"))
				{
					if (i+1 < args.length)
					{			
						newCEform.CEform ceform = new newCEform.CEform();
						ceform.parse(new ParsingString(args[i+1]));
						System.out.println(ceform);
						System.out.println("\r\n*** Slowdown / Speedup analysis ***"); 
						System.out.println(" Slowdown Truth Preserving: " + (ceform.isSDTP()?"yes":"no"));
						System.out.println(" Speedup Truth Preserving:  " + (ceform.isSUTP()?"yes":"no"));
						System.out.println();
						//System.out.println(ceform.formula.createAutomaton());
						System.out.println(ceform.formula.toLarva());
						String output = ceform.formula.toLarva();
						System.out.println(output);
						if (i+3 < args.length && args[i+2].equals("-o"))
						{
							int bslash = args[i+3].lastIndexOf("\\");
							if (bslash > 0)
								new File(args[i+3].substring(0,args[i+3].lastIndexOf("\\"))).mkdirs();
							try{
								PrintWriter pw1 = new PrintWriter(args[i+3]);
								pw1.write(output);
								pw1.close();
							}catch(Exception ex)
							{
								System.out.println("Error creating output file: " + args[i+3]);
								ex.printStackTrace();
							}
						}
						else
							System.out.println("Warning: No output file specified, output not saved!");
					}
					else
						System.out.println("File name expected!");
				}
				else if (args[i].equals("-IMPL"))
				{
					if (i+1 < args.length)
					{			
						CE ce = new CE();
						ce.parse(new ParsingString(args[i+1]));
						CEform.CEform ceform = ce.getCEFormula();
						System.out.println(ceform);
						System.out.println("SDTP:" + ceform.isSDTP());
						System.out.println("SUTP:" + ceform.isSUTP());
						String output = ceform.formula.toLarva();
						System.out.println(output);
						if (i+3 < args.length && args[i+2].equals("-o"))
						{
							int bslash = args[i+3].lastIndexOf("\\");
							if (bslash > 0)
								new File(args[i+3].substring(0,args[i+3].lastIndexOf("\\"))).mkdirs();
							try{
								PrintWriter pw1 = new PrintWriter(args[i+3]);
								pw1.write(output);
								pw1.close();
							}catch(Exception ex)
							{
								System.out.println("Error creating output file: " + args[i+3]);
								ex.printStackTrace();
							}
						}
						else
							System.out.println("Warning: No output file specified, output not saved!");
					}
					else
						System.out.println("File name expected!");
				}
				i++;
			}
		}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
