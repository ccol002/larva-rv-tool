package Events;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.TreeMap;

import larva.SC;
import larva._cls_project0;

public class EventGenerator {
	
	//can be set by -d switch
	public static String database = "new1000";
	//can be set by -s switch
	public static Long starttime = 1230000000000l;//this is december as it should be//this was august 1218900000000l;//0l;//1218900000000l//1230000000000l;
	//the time dormancy was run for the first time was 1218900000000
	// 16 / 8 / 2009
	
	public static Long endtime = 3000000000000l;
	
	//can be set by -i switch
	public static boolean initialize = true;
	
	static Connection conn;
	static ArrayList<Template> templates;
	
	//starting time
	static long stopWatch;
	static long lastTime;
	
	static {////////////timer\\\\\\\\\\\\\\\
		startTimer();
		}
	
	
	
	public static void printTimeMillis(String text)
	{
		long thisTime = System.currentTimeMillis() - stopWatch;
		if (thisTime>lastTime)
		{
			lastTime = thisTime;
			System.out.println("<<<< "+text+" >>>> TIME ELAPSED " + thisTime); 
		}
	}
	
	public static void startTimer()
	{
		stopWatch = System.currentTimeMillis(); 
		System.out.println("TIMER STARTED " + stopWatch); 
	}
	
	public static void initialize() throws SQLException
	{
		try {
		// Step 1: Load the JDBC driver. 
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  

        // Step 2: Establish the connection to the database. 
        String url = "jdbc:odbc:" + database; //fraud
        conn = DriverManager.getConnection(url);//,"root","root");  
		} 
		catch (ClassNotFoundException e) { 
            e.printStackTrace();
        } 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Show the event types and user id's
	 * 
	 * also showing a related query if there is one
	 * 
	 * and also showing the amount of the transaction if it is applicable
	 */
	public static void generateLOADevents() throws SQLException
	{
		Statement stat = conn.createStatement();
        
        ResultSet result = stat.executeQuery("SELECT * FROM fraud.afm_fraudeventrecord;");
        
        while (result.next())
        {
       	
        	String event = result.getString("eventType");
        	String user = result.getString("userId");
        	long timestamp = result.getLong("timestamp");
        	
        	if (
        			(event.contains("LOAD_START"))
        			
        			){
        	
        		methodLOAD(timestamp, user);
        	
        		try{Thread.sleep(200);}catch(Exception ex){ex.printStackTrace();}
        		
        	}        	
        }
        stat.close();
	}
	
	public static void terminate() throws SQLException
	{
		conn.close();
	}
		
	public static long timestamp(Template t)throws Exception
	{
		Object o = t.params.get(t.timeStampField);
		if (o.getClass().equals(Double.class))
			return ((Double)o).longValue();
		else if (o.getClass().equals(Long.class))
			return (Long)o;
		else if (o.getClass().equals(Timestamp.class))
			return ((Timestamp)o).getTime();
		else throw new Exception("***Unexpected type of timestamp!!!");
	}
	
	public static long timestamp(Object o)throws Exception
	{
		if (o.getClass().equals(Double.class))
			return ((Double)o).longValue();
		else if (o.getClass().equals(Long.class))
			return (Long)o;
		else if (o.getClass().equals(Timestamp.class))
			return ((Timestamp)o).getTime();
		else throw new Exception("***Unexpected type of timestamp!!!");
	}
		
	public static void execute(){
		try{
			
			////////////timer\\\\\\\\\\\\\\\
			printTimeMillis("Starting Execution");
			
			initialize();
					
			ArrayList<Statement> stats = new ArrayList<Statement>();
			ArrayList<ResultSet> results = new ArrayList<ResultSet>();
			int size = templates.size();
			boolean[] complete = new boolean[size];
						
			int completed = 0;	
			
			//execute statements
			for (int i = 0; i < size; i++)
			{
			  stats.add(conn.createStatement());      
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
			
			TreeMap<Long,ArrayList<Integer>> sorted = new TreeMap<Long,ArrayList<Integer>>();
			
			for (int i = 0; i < size; i++)
				if (!complete[i])
				{
					long time = timestamp(results.get(i).getObject(templates.get(i).timeStampField));
					if (!sorted.containsKey(time))
					{
						ArrayList<Integer> list = new ArrayList<Integer>();
						list.add(i);
						sorted.put(time,list);
					}
				}
			
			////////////timer\\\\\\\\\\\\\\\
			printTimeMillis("starting loop");
			
			//order them and display
			while (completed < size){//sorted.firstEntry() != null){
				
				_cls_project0._pw.flush();
				
				int indx = -1;
				
				try
				{
					if (sorted.firstEntry() == null)
					{
						break;
					}

					ArrayList<Integer> list = sorted.firstEntry().getValue();

					indx = list.get(0);

					if (list.size() == 1)
						//remove list from sorted structure
						sorted.remove(sorted.firstKey());
					else //remove indx from list
						list.remove(0);

						////////////timer\\\\\\\\\\\\\\\
						printTimeMillis("process a template");

						Template t = templates.get(indx);
						//					if (t.is("DormTX"))
						//					{
						//						System.out.print("problem 1");
						//					}


						if (t.pass(results.get(indx)))
						{	
							////////////timer\\\\\\\\\\\\\\\
							printTimeMillis("going   to    call");
							//						if (indx == 0)
							//							System.out.println("Norm?!");
							t.call(t.name,timestamp(t));
							System.out.println(t.params);
							////////////timer\\\\\\\\\\\\\\\
							printTimeMillis("call     completed");
						}
						else
						{
							System.out.print("prob 2");
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
						long time = timestamp(results.get(indx).getObject(templates.get(indx).timeStampField));
						if (!sorted.containsKey(time))
						{
							ArrayList<Integer> list2 = new ArrayList<Integer>();
							list2.add(indx);
							sorted.put(time,list2);
						}
						else
						{
							ArrayList<Integer> list2 = sorted.get(time);
							list2.add(indx);
						}
					}
					
				
			//	try{Thread.sleep(200);}catch(Exception ex){ex.printStackTrace();}        	        	
			}
			
			for (int i = 0; i < size; i++)
				stats.get(i).close();
			
			terminate();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	public static void methodLOAD(long timestamp, String user)
	{
		System.out.println("LOAD found...");
	}
	
	public static void main(String[] args)
	{
		try {            
			
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
				else if (args[i].equals("-d"))
				{
					i++;
					if (i < args.length)
					{
						database = args[i];
						i++;
					}
					else
						throw new Exception("Database name expected after -d");
				}
				else
					throw new Exception("Enexpected argument: " + args[i]); 
			}
			
			
			//analyzeEventsQueriesAmounts();
			//analyzeRuleComponentsQueries();			
			//generateLOADevents();			
			
				
			templates = new ArrayList<Template>();
			Template t;
			
			t = new Template("NormTX", "tl_transactionentry", database, "creationTimestamp");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
			t.addFilter(new Filter("transactionEntryId"));
            t.addFilter(new Filter("transactionAmount"));
            //t.addFilter(new Filter("sourceAllocationId"));
            //t.addFilter(new Filter("destinationAllocationId"));
            t.addFilter(new Filter("transactionCurrency","baseCurrency"));
			t.addFilter(new Filter("user","userId"));
            t.addFilter(new Filter("DTYPE"));
            t.addFilter(new Filter("authorGroup",Filter.EQUALS, "USER"));
            t.addFilter(new Filter("transactionEntryStatus",Filter.EQUALS,"SUCCESSFUL"));
            templates.add(t);
            
                   
            
            t = new Template("DormFeeTX", "tl_transactionentry", database, "creationTimestamp");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
            t.addFilter(new Filter("user","userId"));
            t.addFilter(new Filter("transactionEntryId"));
            t.addFilter(new Filter("transactionAmount"));
            t.addFilter(new Filter("transactionCurrency"));
            t.addFilter(new Filter("instrument", "sourceAllocationId"));
            t.addFilter(new Filter("transactionEntryStatus",Filter.EQUALS,"SUCCESSFUL"));
            t.addFilter(new Filter("feeType", Filter.EQUALS, "DORMANCY_FEE"));
            templates.add(t);
            
            //except dormancy
            t = new Template("FeeTX", "tl_transactionentry", database, "creationTimestamp");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
            t.addFilter(new Filter("DTYPE",Filter.EQUALS,"DeductFeeTXEntry"));
            t.addFilter(new Filter("user","userId"));
            t.addFilter(new Filter("transactionEntryId"));
            t.addFilter(new Filter("transactionAmount"));
            t.addFilter(new Filter("transactionCurrency"));
            t.addFilter(new Filter("transactionEntryStatus",Filter.EQUALS,"SUCCESSFUL"));
            t.addFilter(new Filter("feeType", Filter.NOT_EQUALS, "DORMANCY_FEE"));
            templates.add(t);
            
            t = new Template("ReclaimFeeTX", "tl_transactionentry", database, "creationTimestamp");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
            t.addFilter(new Filter("user","userId"));
            t.addFilter(new Filter("instrument", "sourceAllocationId"));
            t.addFilter(new Filter("transactionEntryId"));
            t.addFilter(new Filter("transactionAmount"));
            t.addFilter(new Filter("transactionCurrency"));
            t.addFilter(new Filter("transactionEntryStatus",Filter.EQUALS,"SUCCESSFUL"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "ReclaimBreakageFundsTXEntry"));
            templates.add(t);
            
            t = new Template("PurchaseTX", "tl_transactionentry", database, "creationTimestamp");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
            t.addFilter(new Filter("user","userId"));
            t.addFilter(new Filter("transactionAmount"));
            t.addFilter(new Filter("transactionCurrency"));
            t.addFilter(new Filter("transactionEntryId"));
           //always successful 
            t.addFilter(new Filter("transactionEntryStatus",Filter.EQUALS,"SUCCESSFUL"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "PurchaseNotificationTXEntry"));
            templates.add(t);
            
            t = new Template("PurchaseVoidTX", "tl_transactionentry", database, "creationTimestamp");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
            t.addFilter(new Filter("user","userId"));
            t.addFilter(new Filter("transactionAmount"));
            t.addFilter(new Filter("transactionCurrency"));
           //always successful 
            t.addFilter(new Filter("transactionEntryStatus",Filter.EQUALS,"SUCCESSFUL"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "VoidPurchaseNotificationTXEntry"));
            templates.add(t);
              
            t = new Template("PurchaseCancTX", "tl_transactionentry", database, "creationTimestamp");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
            t.addFilter(new Filter("user","userId"));
            t.addFilter(new Filter("transactionAmount"));
            t.addFilter(new Filter("transactionCurrency"));
           //always successful 
            t.addFilter(new Filter("transactionEntryStatus",Filter.EQUALS,"SUCCESSFUL"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "PurchaseNotificationCancellationTXEntry"));
            templates.add(t);
            
            t = new Template("PurchaseSettleTX", "tl_transactionentry", database, "creationTimestamp");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
            t.addFilter(new Filter("user","userId"));
            t.addFilter(new Filter("transactionEntryId"));
            t.addFilter(new Filter("transactionAmount"));
            t.addFilter(new Filter("transactionCurrency"));
           //always successful 
            t.addFilter(new Filter("transactionEntryStatus",Filter.EQUALS,"SUCCESSFUL"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "PurchaseConfirmationSettlementTXEntry"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "PlasticCardSettlementTXEntry"));
                                              
            templates.add(t);
            
            //transactions where money goes to the user
            t = new Template("MoneyToUserTX", "tl_transactionentry", database, "creationTimestamp");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
            t.addFilter(new Filter("user","userId"));
            t.addFilter(new Filter("transactionAmount"));
            t.addFilter(new Filter("transactionCurrency"));
            t.addFilter(new Filter("transactionEntryId"));
           //always successful 
            t.addFilter(new Filter("transactionEntryStatus",Filter.EQUALS,"SUCCESSFUL"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "RefundFeeTXEntry"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "MerchantIncomingFundsTXEntry"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "DepositPayoutTXEntry"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "InstantPayoutTXEntry"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "ManualCreditTXEntry"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "BankTransferInTXEntry"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "ManualFeeReversalTXEntry"));
            templates.add(t);
            
            //transactions where money goes to the user
            t = new Template("MoneyFromUserTX", "tl_transactionentry", database, "creationTimestamp");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
            t.addFilter(new Filter("user","userId"));
            t.addFilter(new Filter("transactionAmount"));
            t.addFilter(new Filter("transactionCurrency"));
            t.addFilter(new Filter("transactionEntryId"));
           //always successful 
            t.addFilter(new Filter("transactionEntryStatus",Filter.EQUALS,"SUCCESSFUL"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "ReturnFundsTXEntry"));             
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "CallCentreRefundUserTXEntry"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "BankTransferOutTXEntry"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "ManualDebitTXEntry"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "DepositBankPayInTXEntry"));
            templates.add(t);
            
            
            
//            t = new Template("LoadTX", "tl_transactionentry", database, "creationTimestamp");
//            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
//            t.addFilter(new Filter("transactionEntryId"));
//            t.addFilter(new Filter("user", "userId"));
//            t.addFilter(new Filter("transactionAmount"));
//            t.addFilter(new Filter("sourceAllocationId"));
//            t.addFilter(new Filter("destinationAllocationId"));
//            t.addFilter(new Filter("transactionCurrency"));
//            t.addFilter(new Filter("DTYPE",Filter.EQUALS, "LoadTXEntry"));
//            t.addFilter(new Filter("transactionEntryStatus",Filter.EQUALS,"SUCCESSFUL"));
//            templates.add(t);
            
            
            ///////////////////////////////////////////////////////////////////////
            //user rights / manage account / user status 
            
            t = new Template("ChangeRights", "log_audit", database, "timestamp");
            t.addFilter(new Filter("user","targetUser"));
            t.addFilter(new Filter("message"));
            t.addFilter(new Filter("auditableEvent", Filter.EQUALS, "USER_CHANGE_RIGHTS"));
            templates.add(t);
            
            t = new Template("ChangeScheme", "log_audit", database, "timestamp");
            t.addFilter(new Filter("user","targetUser"));
            t.addFilter(new Filter("message"));
            t.addFilter(new Filter("auditableEvent", Filter.EQUALS, "USER_CHANGE_SCHEME"));
            templates.add(t);
            
            t = new Template("ChangePassword", "log_audit", database, "timestamp");
            t.addFilter(new Filter("user","targetUser"));
            t.addFilter(new Filter("message", Filter.CONTAINS, "uccess"));
            t.addFilter(new Filter("auditableEvent", Filter.EQUALS, "USER_CHANGE_PASSWORD"));
            templates.add(t);
            
            
            t = new Template("Creation", "log_audit", database, "timestamp");
            t.addFilter(new Filter("user","targetUser"));
            t.addFilter(new Filter("auditableEvent",Filter.EQUALS,"USER_ACCT_CREATION"));
            templates.add(t);
            
            t = new Template("Activation", "log_audit", database, "timestamp");
            t.addFilter(new Filter("user","targetUser"));
            t.addFilter(new Filter("message", Filter.CONTAINS, "uccess"));
            t.addFilter(new Filter("auditableEvent", Filter.EQUALS, "USER_ACCT_ACTIVATION"));
            templates.add(t);    
            
            
            t = new Template("AcctSuspend", "log_audit", database, "timestamp");
            t.addFilter(new Filter("user","targetUser"));
            t.addFilter(new Filter("auditableEvent", Filter.EQUALS, "USER_ACCT_SUSPEND"));
            templates.add(t);
            
            t = new Template("AcctUnSuspend", "log_audit", database, "timestamp");
            t.addFilter(new Filter("user","targetUser"));
            t.addFilter(new Filter("auditableEvent", Filter.EQUALS, "USER_ACCT_UNSUSPEND"));
            templates.add(t);
            
            t = new Template("AcctLock", "log_audit", database, "timestamp");
            t.addFilter(new Filter("user","targetUser"));
            t.addFilter(new Filter("message", Filter.CONTAINS, "uccess"));
            t.addFilter(new Filter("auditableEvent", Filter.EQUALS, "USER_ACCT_LOCK"));
            templates.add(t);
            
            t = new Template("DormTX", "log_audit", database, "timestamp");
            t.addFilter(new Filter("user","targetUser"));
            t.addFilter(new Filter("auditableEvent",Filter.EQUALS,"USER_ACCT_DORMANT"));
            templates.add(t);
            
            t = new Template("UnDormTX", "tl_transactionentry", database, "creationTimestamp");
            t.addFilter(new Filter("user","userId"));
            t.addFilter(new Filter("DTYPE",Filter.EQUALS,"ThawTXEntry"));
            t.addFilter(new Filter("originalFreezeOrigin",Filter.EQUALS,"DORMANCY_FREEZE"));
            templates.add(t);
            
            t = new Template("BreakTX", "log_audit", database, "timestamp");
            t.addFilter(new Filter("user","targetUser"));
            t.addFilter(new Filter("auditableEvent", Filter.EQUALS, "USER_ACCT_BREAKAGE"));
            templates.add(t);    
            
            
            
            ////////////////////////////////////////////////////////////////////////////////////
            //instruments
            
            t = new Template("Assign", "users_alloc", database, "allocationDate");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
            t.addFilter(new Filter("user","userId"));
            t.addFilter(new Filter("userGroup",Filter.EQUALS,"USER"));
            t.addFilter(new Filter("type"));
            t.addFilter(new Filter("currency"));
            t.addFilter(new Filter("instrument", "id"));
            t.timestampMode = Template.DATETIME_TIMESTAMP;
            templates.add(t);
            
            t = new Template("Restrict", "tl_transactionentry", database, "creationTimestamp");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
            t.addFilter(new Filter("user","userId"));
            t.addFilter(new Filter("instrument", "sourceAllocationId"));
            t.addFilter(new Filter("freezeOrigin"));
            t.addFilter(new Filter("DTYPE",Filter.EQUALS, "FreezeTXEntry"));
            t.addFilter(new Filter("transactionEntryStatus",Filter.EQUALS,"SUCCESSFUL"));
            templates.add(t);
                   
            t = new Template("Unrestrict", "tl_transactionentry", database, "creationTimestamp");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
            t.addFilter(new Filter("user","userId"));
            t.addFilter(new Filter("instrument", "sourceAllocationId"));
            t.addFilter(new Filter("DTYPE",Filter.EQUALS, "ThawTXEntry"));
            t.addFilter(new Filter("originalFreezeOrigin"));
            t.addFilter(new Filter("transactionEntryStatus",Filter.EQUALS,"SUCCESSFUL"));
            templates.add(t);
           
            t = new Template("Fallow", "tl_transactionentry", database, "creationTimestamp");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
            t.addFilter(new Filter("user","userId"));
            t.addFilter(new Filter("instrument", "sourceAllocationId"));
            t.addFilter(new Filter("DTYPE",Filter.EQUALS, "ReclaimTXEntry"));
            t.addFilter(new Filter("transactionEntryStatus",Filter.EQUALS,"SUCCESSFUL"));
            templates.add(t);
            
            t = new Template("DelInstr", "log_audit", database, "timestamp");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
            t.addFilter(new Filter("user","targetUser"));
            t.addFilter(new Filter("message", Filter.CONTAINS, "uccess"));
            t.addFilter(new Filter("auditableEvent",Filter.EQUALS, "USER_FUNDINGSRC_DELETE"));
            templates.add(t);
            
            t = new Template("ChangeDefault", "log_audit", database, "timestamp");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
            t.addFilter(new Filter("user","targetUser"));
            t.addFilter(new Filter("message", Filter.CONTAINS, "uccess"));
            t.addFilter(new Filter("auditableEvent",Filter.EQUALS, "USER_VCC_SET_DEFAULT"));
            templates.add(t);
            
            
            /////////////////////////////////////////////
            //properties
            
            t = new Template("PropChange", "props", database, "timestamp");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
            t.addFilter(new Filter("message"));
            templates.add(t);
            
            
            ////////////////////////////////////////////////
            //call centre
            
            t = new Template("CallCentreLoadTX", "tl_transactionentry", database, "creationTimestamp");
            //t = new Template("NormalTransaction", "select * from tl_transactionentry order by creationTimestamp;", "creationTimestamp");
            t.addFilter(new Filter("user","userId"));
            t.addFilter(new Filter("transactionAmount"));
            t.addFilter(new Filter("transactionCurrency"));
            t.addFilter(new Filter("transactionEntryId"));            
            t.addFilter(new Filter("instrument", "sourceAllocationId"));
            t.addFilter(new Filter("DTYPE", Filter.EQUALS, "CallCentreLoadTXEntry"));
            t.addFilter(new Filter("transactionEntryStatus",Filter.EQUALS,"SUCCESSFUL"));
           
            templates.add(t);
            
            execute();
			
			
        } catch (Exception e) { 
            e.printStackTrace();
        }  
	}
	
	
}
