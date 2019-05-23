package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;


public class Runner {

	//runner properties
	//loaded by -p switch
	public static long period = 1000*60*60*12;//12 hrs
	//loaded by -s switch
	public static boolean starting = false;
	//loaded by -m switch
	public static String monitor = "C:\\Documents and Settings\\ixaris\\Desktop\\projects3\\Event Generator\\bin";	
	//loaded by -c switch
	public static String confDir = "";
	//loaded by -o switch
	public static String output = "C:\\Documents and Settings\\ixaris\\Desktop\\projects3\\Event Generator\\outputs";
	//loaded by -b switch
	public static String beautifier = "C:\\Documents and Settings\\ixaris\\Desktop\\projects3\\LarvaOutput\\bin";
	
	//locals
	public static long lastRun;
	public static Process p;
	
	//event generator (monitor)
	//loaded from properties file
	public static String monitorDB = "test";//this is currently not used -- this is hardcoded in the larva compiler!
	public static long startingTime = 0;
	
	//data preparation
	//loaded from properties file	
	public static String dataDB = "";
	
	public static String sqlStats = "";
	
	
/*	public static void createProcedures()
	{
		Connection conn;
		
		//load stored procedures from file
		if (storedProcedures.length() > 0)
		{
			StringBuilder sb = new StringBuilder("");
			try{
				BufferedReader inStream = new BufferedReader(new InputStreamReader(new FileInputStream(storedProcedures)));  

				String line;
				while ((line = inStream.readLine()) != null)
				{
					sb.append(line + "\r\n");
				}

				// Step 1: Load the JDBC driver. 
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  

				// Step 2: Establish the connection to the database. 
				String url = "jdbc:odbc:" + dataDB; //fraud
				conn = DriverManager.getConnection(url);//,"root","root");  

				Statement s = conn.createStatement();

				s.execute(sb.toString()); 

			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	*/

	public static void prepareData()
	{
		//replace the initial time of the procedure calls!!
		Connection conn;
		
		//load stored procedures from file
		if (sqlStats.length() > 0)
		{
			StringBuilder sb = new StringBuilder("");
			try{
				BufferedReader inStream = new BufferedReader(new InputStreamReader(new FileInputStream(sqlStats)));  

				String line;
				while ((line = inStream.readLine()) != null)
				{
					if (starting)
						line = line.replaceAll("@start", Long.toString(0));
					else
						line = line.replaceAll("@start", Long.toString(lastRun));
					
					line = line.replaceAll("@end", Long.toString(lastRun + period));
					sb.append(line + "\r\n");
				}

				// Step 1: Load the JDBC driver. 
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  

				// Step 2: Establish the connection to the database. 
				String url = "jdbc:odbc:" + dataDB; //fraud
				conn = DriverManager.getConnection(url);//,"root","root");  

				Statement s = conn.createStatement();

				int startindx = 0;
				while (startindx < sb.length() && sb.indexOf(";",startindx) != -1)
				{
					int newindx = sb.indexOf(";",startindx);
					String cmd = sb.substring(startindx,newindx);
					startindx = newindx +1;
					try{	
						s.execute(cmd);
					} 
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}

			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
		
	public static void runMonitor()
	{
		try {
			String command = "java -cp \"" + monitor + "\";\"" + monitor + "\\aspectjrt.jar\" Events.EventGenerator "
			+ " -s " + lastRun + " -e " + (lastRun + period) + " -d " + dataDB;
			
			
			p = Runtime.getRuntime().exec(command);
			
			System.out.println("Starting monitor at " + lastRun + " monitoring till " + (lastRun + period));
			
			
			System.out.println(" * * * ");
			
			final StringBuilder s = new StringBuilder("");
	        final StringBuilder e = new StringBuilder("");
	        
	        final BufferedReader errStream = new BufferedReader(new InputStreamReader(p.getErrorStream()));    
		      final BufferedReader inStream = new BufferedReader(new InputStreamReader(p.getInputStream()));  
	        		          
	        new Thread(){
		            public void run(){
				        try{				        	
				        	 String line;
				        	 while ((line = errStream.readLine()) != null){
				        		 System.out.println(line);
					        	  e.append(line + "\r\n");
				        	 }
				         }catch (Exception ex)
				         {
					         ex.printStackTrace(); 					         
				         }
		                }
		            }.start();
	        
	        new Thread(){
	          public void run(){
			        try{				        	
			        	 String line;
			        	while ((line = inStream.readLine()) != null){
			        		System.out.println(line);
			        		s.append(line + "\r\n");
			        	}
			        	
			         }catch (Exception ex)
			         {
				         ex.printStackTrace();
			         }
	              }
	          }.start();	
	          
			
			p.waitFor();
			
			System.out.println(" * * * ");
			
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static void waitForPeriod()
	{
		try {
			long current = System.currentTimeMillis();
			
			//keep waiting till the current time exceeds the time of the last monitor run and the period
			while (current < lastRun + period)
			{
				long wait = period - (current - lastRun);
				
				if (wait > 0 && wait < period)
				{
					System.out.println("Going to wait for " + wait);
					Thread.sleep(wait);
				}
				
				current = System.currentTimeMillis();
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void processOutput()
	{
		try{			
			File dir = new File(output);
			String[] files = null;

			if (dir.exists())
			{
				files = dir.list(new FilenameFilter(){
					public boolean accept(File dir, String name){
						if (name.endsWith(".txt")) return true;
						else return false;
					}
				});

				int c = 0;
				for (int i = 0; i < files.length; i++)
					if (files[i].indexOf("_output_") == 0)
					{					
						c++;
						
						String name = files[i].substring(8,files[i].indexOf(' '));
						
						java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("(yy-MM-dd) [HH mm ss]");
						java.util.Date date = new java.util.Date(lastRun);
						java.util.Date date2 = new java.util.Date(lastRun+period);
						String datetime = dateFormat.format(date);
						String datetime2 = dateFormat.format(date2);
						
						String filename = name;
						if (c > 1)
							filename += "(" + (c-1) + ")";
						
						filename += " from " + datetime + " till " + datetime2;
						
						File f = new File(output + "\\" + files[i]);
						f.renameTo(new File(output + "\\" + filename + ".txt"));

						String cmd = "java -cp \"" + beautifier + "\" larvaOutput.Parser \""
						+ output + "\\" + filename + ".txt\" -o \"" + output + "\\" + filename + "\"";

						p = Runtime.getRuntime().exec(cmd);

						System.out.println("Beautifying output...");

						
						final StringBuilder s = new StringBuilder("");
				        final StringBuilder e = new StringBuilder("");
				        
				        final BufferedReader errStream = new BufferedReader(new InputStreamReader(p.getErrorStream()));    
					      final BufferedReader inStream = new BufferedReader(new InputStreamReader(p.getInputStream()));  
				        		          
				        new Thread(){
					            public void run(){
							        try{				        	
							        	 String line;
							        	 while ((line = errStream.readLine()) != null){
							        		 System.out.println(line);
								        	  e.append(line + "\r\n");
							        	 }
							         }catch (Exception ex)
							         {
								         ex.printStackTrace(); 					         
							         }
					                }
					            }.start();
				        
				        new Thread(){
				          public void run(){
						        try{				        	
						        	 String line;
						        	while ((line = inStream.readLine()) != null){
						        		System.out.println(line);
						        		s.append(line + "\r\n");
						        	}
						        	
						         }catch (Exception ex)
						         {
							         ex.printStackTrace();
						         }
				              }
				          }.start();	
				          				
						
						p.waitFor();
				}
			}
			else
				throw new Exception("The output directory of the monitor does not coincide with the output specified!");
		}catch(Exception ex)
		{ex.printStackTrace();}
	}
	
	public static void run()
	{
		lastRun = startingTime;
		
		//the initialization must be executed manually before running the monitor
		//the reason being that JDBC does not allow the execution of multiple SQL statements
//		System.out.println("Starting initialization procedure...");
//		if (starting) createProcedures();
		
		
		while (true)
		{
			System.out.println("Preparing data for monitoring...");
			prepareData();
			
			if (starting)
			{				
				try{
					PrintWriter pw = new PrintWriter(monitor + "\\larva\\initialization.txt");
					pw.println("initialization = true");
					pw.close();
				}
				catch(Exception ex)
				{ex.printStackTrace();}
			}
				
			System.out.println("Starting the monitor...");
			runMonitor();
						
			if (starting)
			{
				starting = false;
				
				try{
					PrintWriter pw = new PrintWriter(monitor + "\\larva\\initialization.txt");
					pw.println("initialization = false");
					pw.close();
				}
				catch(Exception ex)
				{ex.printStackTrace();}
			}
			
			//formats the output into a user friendly style
			processOutput();
	
			//update time
			lastRun += period;
			
			System.out.println("Monitoring till " + lastRun + " completed...");
			
			//System.out.println("Waiting for time to pass...");
			waitForPeriod();						
		}
	}
	
	public static void main(String[] args)
	{
		try {
			for (int i = 0; i < args.length; )
			{
				if (args[i].equals("-s"))
				{
					starting = true;
					i++;
				}
				else if (args[i].equals("-m"))
				{
					i++;
					if (i < args.length)
					{
						monitor = args[i];
						i++;
					}
					else
						throw new Exception("Monitor directory expected after -m");
				}
				else if (args[i].equals("-p"))
				{
					i++;
					if (i < args.length)
					{
						try {
							period = Long.parseLong(args[i]);
						}catch (Exception ex)
						{
							throw new Exception("Number of milliseconds expected after -p");
						}
						i++;
					}
					else
						throw new Exception("Number of milliseconds expected after -p");
				}
				else if (args[i].equals("-c"))
				{
					i++;
					if (i < args.length)
					{
						confDir = args[i];
						i++;
					}
					else
						throw new Exception("Configuration directory expected after -c");
				}
				else if (args[i].equals("-o"))
				{
					i++;
					if (i < args.length)
					{
						output = args[i];
						i++;
					}
					else
						throw new Exception("Output directory expected after -o");
				}
				else if (args[i].equals("-b"))
				{
					i++;
					if (i < args.length)
					{
						beautifier = args[i];
						i++;
					}
					else
						throw new Exception("Beautifier directory expected after -b");
				}
				else
					throw new Exception("Enexpected argument: " + args[i]); 
			}
			
			Properties prop = new Properties();
			File f = new File(confDir);
			prop.load(new FileReader(f));
			
			if (prop.containsKey("period"))
				period = Long.parseLong(prop.getProperty("period"));
			if (prop.containsKey("starting"))
				starting = Boolean.parseBoolean(prop.getProperty("starting"));
			if (prop.containsKey("monitor"))
				monitor = prop.getProperty("monitor");
			if (prop.containsKey("monitorDB"))
				monitorDB = prop.getProperty("monitorDB");
			if (prop.containsKey("startingTime"))
				startingTime = Long.parseLong(prop.getProperty("startingTime"));
			if (prop.containsKey("dataDB"))
				dataDB = prop.getProperty("dataDB");
			if (prop.containsKey("output"))
				output = prop.getProperty("output");
			if (prop.containsKey("sqlStats"))
				sqlStats = prop.getProperty("sqlStats");
			if (prop.containsKey("beautifier"))
				beautifier = prop.getProperty("beautifier");		
			
			run();
			
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	
}
