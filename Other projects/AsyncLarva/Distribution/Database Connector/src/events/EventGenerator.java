package events;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.TreeMap;

import editMe.EditMe;
import larva.SC;
import larva._cls_script0;

/*
 * This class is the entry point to the Database event extractor system
 * 
 * The aim of this system is to extract events from a database and replay them to the monitor
 * 
 * 
 */

public abstract class EventGenerator {
	

	//can be set by -s switch
	public static Long starttime = 0l;
	//can be set by -e switch
	public static Long endtime = 9999999999999l;
	//can be set by -url switch
	public static String url = "jdbc:mysql://localhost:3306/audit";
	//can be set by -un switch
	public static String un = "root";
	//can be set by -pw switch
	public static String pw = "";
	
	
	//user of this class should populate the templates
	static ArrayList<Template> templates = new ArrayList<Template>();
	
	
	//timers for profiling purposes
	static long stopWatch;
	static long lastTime;
	
	
	static{
		//timer for profiling purposes
		startTimer();
		
		EditMe.configuration();
	}
	
	public static void registerDBEvent(Template t)
	{
		templates.add(t);
	}
	
	
	private static void printTimeMillis(String text)
	{
		long thisTime = System.currentTimeMillis() - stopWatch;
		if (thisTime>lastTime)
		{
			lastTime = thisTime;
			System.out.println("<<<< "+text+" >>>> TIME ELAPSED " + thisTime); 
		}
	}
	
	private static void startTimer()
	{
		stopWatch = System.currentTimeMillis(); 
		System.out.println("TIMER STARTED " + stopWatch); 
	}
	
	
	private static long timestamp(Template t)throws Exception
	{
		Object o = t.params.get(t.timeStampField);
		return timestamp(o);
	}
	
	private static long timestamp(Object o)throws Exception
	{
		if (o.getClass().equals(Double.class))
			return ((Double)o).longValue();
		else if (o.getClass().equals(Long.class))
			return (Long)o;
		else if (o.getClass().equals(Timestamp.class))
			return ((Timestamp)o).getTime();
		else throw new Exception("***Unexpected type of timestamp!!!");
	}
		
	//execute SQL statements and generate events
	private static void execute(){
		
		ArrayList<Connection> conns = new ArrayList<Connection>();
		ArrayList<Statement> stats = new ArrayList<Statement>();
		ArrayList<ResultSet> results = new ArrayList<ResultSet>();
		int size = templates.size();
		
		
		try{
			
			////////////timer\\\\\\\\\\\\\\\
			printTimeMillis("Starting Execution");					
			
			boolean[] complete = new boolean[size];
						
			int completed = 0;	
			
			//execute statement for each template
			for (int i = 0; i < size; i++)
			{
				//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  
				//String url = "jdbc:odbc:" + database;
				Class.forName("com.mysql.jdbc.Driver");  
				//String url = "jdbc:mysql://localhost:3306/" + database;
				conns.add(DriverManager.getConnection(url,un,pw));
			  
				stats.add(conns.get(i).createStatement());      
			  
				String sql = templates.get(i).getSQL();
			  
				results.add(stats.get(i).executeQuery(sql));
				
		        if (!results.get(i).next())//mark an empty result set as completed
			    {
				  complete[i] = true;
				  completed++;
			    }
			    else
				  complete[i] = false;
			}
			
			//keeps a reference to the index of the results in sorted order
			TreeMap<Long,ArrayList<Integer>> sorted = new TreeMap<Long,ArrayList<Integer>>();
			
			for (int i = 0; i < size; i++)
				if (!complete[i])
				{
					long time = timestamp(results.get(i).getObject(templates.get(i).timeStampField));
					if (!sorted.containsKey(time))
					{
						ArrayList<Integer> list = new ArrayList<Integer>();
						list.add(i);
						sorted.put(time,list);//put the timestamp, and result index in the treemap
					}
					else
					{
						ArrayList<Integer> list = sorted.get(time);
						list.add(i);
					}
				}
			
			////////////timer\\\\\\\\\\\\\\\
			printTimeMillis("starting loop");
			
			//order them and display
			while (completed < size){//sorted.firstEntry() != null){
				
				_cls_script0._pw.flush();
				
				int indx = -1;
				
				try
				{
					if (sorted.firstEntry() == null) break;

					ArrayList<Integer> list = sorted.firstEntry().getValue(); //get the first timestamp containing an event

					indx = list.get(0);//get the index of the resultset containing the first event

					if (list.size() == 1)
						//remove list from sorted structure
						sorted.remove(sorted.firstKey());
					else //just remove indx from list
						list.remove(0);

						////////////timer\\\\\\\\\\\\\\\
						printTimeMillis("process a template");

						Template t = templates.get(indx);


						if (t.pass(results.get(indx)))//apply filtering on event
						{	
							////////////timer\\\\\\\\\\\\\\\
							printTimeMillis("going   to    call");
							
							t.call(t.name,timestamp(t)); //generate event
							System.out.println(t.params);
							
							////////////timer\\\\\\\\\\\\\\\
							printTimeMillis("call     completed");
						}
						

					}catch(Exception ex){
						ex.printStackTrace();
					}
					
				
					if (!results.get(indx).next())
					{
						completed++;
						complete[indx] = true;
					}
					else
					{
						//get next event in the resultset being processed 
						//(recall that the resultset is itself sorted so we are simply taking into consideration the earliest event in each resultset)
						long time = timestamp(results.get(indx).getObject(templates.get(indx).timeStampField));
						
						if (!sorted.containsKey(time))//add timestamp if it does not exist in treemap
						{
							ArrayList<Integer> list2 = new ArrayList<Integer>();
							list2.add(indx);
							sorted.put(time,list2);
						}
						else // add the index to the resultset to the treemap since the timestamp in question already exists
						{
							ArrayList<Integer> list2 = sorted.get(time);
							list2.add(indx);
						}
					}
					
				
			//	try{Thread.sleep(200);}catch(Exception ex){ex.printStackTrace();}
					
			}//end while (completed < size)
			
			
			
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally {//cleaning up
			
			for (int i = 0; i < size; i++)
			{
				if (results.get(i) != null) {
					try {
						results.get(i).close();
					} catch (SQLException sqlEx) { } // ignore
					results.set(i,null);
				}
			}
			for (int i = 0; i < size; i++)
			{
				if (stats.get(i) != null) {
					try {
						stats.get(i).close();
					} catch (SQLException sqlEx) { } // ignore
					stats.set(i,null);
				}				
			}
			for (int i = 0; i < size; i++)
			{
				if (conns.get(i) != null) {
					try {
						conns.get(i).close();
					} catch (SQLException sqlEx) { } // ignore
					conns.set(i,null);
				}
			}
		}
	}
	
	
	public static void main(String[] args)
	{
		try {            
			

//				System.out.println("  -s <timestamp in milliseconds> to specify the starting time");
//				System.out.println("  -e <timestamp in milliseconds> to specify the end time");
//				System.out.println("  -url <url> to specify database to store monitors");				
//				System.out.println("  -un <username> to specify the username to access the database");				
//				System.out.println("  -pw <password> to specify the password to access the database");				
			
			for (int i = 0; i < args.length;)
			{
				if (args[i].equals("-s"))
				{
					i++;
					if (i < args.length)
					{
						try {
							starttime = Long.parseLong(args[i]);
						}catch (Exception ex)
						{
							throw new Exception("Number of milliseconds expected after -s");
						}
						i++;
					}
					else
						throw new Exception("Number of milliseconds expected after -s");
				}
				else if (args[i].equals("-e"))
				{
					i++;
					if (i < args.length)
					{
						try {
							endtime = Long.parseLong(args[i]);
						}catch (Exception ex)
						{
							throw new Exception("Number of milliseconds expected after -e");
						}
						i++;
					}
					else
						throw new Exception("Number of milliseconds expected after -s");
				}
				else if (args[i].equals("-url"))
				{
					i++;
					if (i < args.length)
					{
						url = args[i];
						i++;
					}
					else
						throw new Exception("Database name expected after -url");
				}
				else if (args[i].equals("-un"))
				{
					i++;
					if (i < args.length)
					{
						un = args[i];
						i++;
					}
					else
						throw new Exception("username expected after -un");
				}
				else if (args[i].equals("-pw"))
				{
					i++;
					if (i < args.length)
					{
						pw = args[i];
						i++;
					}
					else
						throw new Exception("password expected after -url");
				}
				else
					throw new Exception("Enexpected argument: " + args[i]); 
			}   
            
                 
            execute();//initialization and cleaning up of db connection is handled here
			
			
        } catch (Exception e) { 
            e.printStackTrace();
        }  
        finally{
        	
        		SC.cleanup();//close SC connection and _cls_script0 connection
        	
        }
        
	}
	
	
}
