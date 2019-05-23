package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DBAccess {

	static Connection conn;
	
	public static void initialize() throws SQLException
	{
		try {
		// Step 1: Load the JDBC driver. 
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  

        // Step 2: Establish the connection to the database. 
        String url = "jdbc:odbc:fraud"; 
        conn = DriverManager.getConnection(url);//,"root","root");  
		} 
		catch (ClassNotFoundException e) { 
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
	public static void analyzeEventsQueriesAmounts() throws SQLException
	{
		Statement stat = conn.createStatement();
        
        ResultSet result = stat.executeQuery("SELECT * FROM fraud.afm_fraudeventrecord;");
        
        while (result.next())
        {
        	String event = result.getString("eventType");
        	String user = result.getString("userId");
        	
        	if (
        			(event.contains("LOAD"))
        	&& 		(user.equals("user40"))
        			
        			){
        	System.out.print(new Date(result.getInt("timestamp")));
        	System.out.print(" User : " + user);
        	System.out.print(" Event : " + event);
        	
        	String recordId = result.getString("fraudScoreRecordId");
        	
        	if (recordId != null)
        	{
        		Statement stat2 = conn.createStatement();
        		ResultSet s = stat2.executeQuery("SELECT * FROM fraud.afm_fraudscorerecord where recordId='"+recordId+"';");
        		while (s.next())
        		{
        			System.out.print(" Query Type: " + s.getString("queryType"));
        			System.out.print(" Amount: " + s.getDouble("transactionAmount"));
        		}
        	}
        	
        	
        	System.out.println();
        	}        	
        }
        stat.close();
	}
	
	/*
	 * Shows the rules which have been triggered throughout the execution of the system...
	 *  
	 */
	public static void analyzeRuleComponentsQueries() throws SQLException
	{
		Statement stat = conn.createStatement();
        
        ResultSet result = stat.executeQuery("SELECT * FROM fraud.afm_fraudscorerecord_afm_rulecomponentrecord");
        
        while (result.next())
        {
        	String queryId = result.getString("afm_fraudscorerecord_recordId");
        	String ruleId = result.getString("ruleComponentRecords_componentId");
        	
        	
       		Statement stat2 = conn.createStatement();
        	
       		ResultSet s = stat2.executeQuery("SELECT * FROM fraud.afm_fraudscorerecord where recordId='"+queryId+"';");
        	      		
       		
       		while (s.next())
        		{
       				System.out.print(new Date(s.getInt("timestamp")));
       				System.out.print(" UserID: " +s.getString("userId"));
        			System.out.print("  Query Type: " + s.getString("queryType"));
        			//System.out.print(" Amount: " + s.getDouble("transactionAmount"));
        		}
       		
       		
       		ResultSet t = stat2.executeQuery("SELECT * FROM fraud.afm_rulecomponentrecord where componentId='"+ruleId+"';");
        	
       		
       		while (t.next())
    		{
    			System.out.print("  >>>>>  Rule Name: " + t.getString("ruleLongName"));
    			//System.out.print(" Amount: " + s.getDouble("transactionAmount"));
    		}
        	
        	
        	System.out.println();
        	}
        	
        stat.close();
        
	}
	
	/*
	 * Shows the actions which have been recommended throughout the execution of the system...
	 * 
	 * the recommended action is the outcome of the whole chain rule...not of a single rule...
	 *  
	 */
	public static void analyzeRecommendedActionsQueries() throws SQLException
	{
		Statement stat = conn.createStatement();
        
        ResultSet result = stat.executeQuery("SELECT * FROM fraud.afm_fraudscorerecord_afm_recommendedactionrecord");
        
        while (result.next())
        {
        	String queryId = result.getString("afm_fraudscorerecord_recordId");
        	String ruleId = result.getString("recommendedActionRecords_actionId");
        	
        	
       		Statement stat2 = conn.createStatement();
        	
       		ResultSet s = stat2.executeQuery("SELECT * FROM fraud.afm_fraudscorerecord where recordId='"+queryId+"';");
        	      		
       		
       		while (s.next())
        		{
       				System.out.print(new Date(s.getInt("timestamp")));
       				System.out.print(" UserID: " +s.getString("userId"));
        			System.out.print("  Query Type: " + s.getString("queryType"));
        			//System.out.print(" Amount: " + s.getDouble("transactionAmount"));
        		}
       		
       		
       		ResultSet t = stat2.executeQuery("SELECT * FROM fraud.afm_recommendedactionrecord where actionId='"+ruleId+"';");
        	
       		
       		while (t.next())
    		{
    			System.out.print("  >>>>>  Action Name: " + t.getString("recommendedAction"));
    			//System.out.print(" Amount: " + s.getDouble("transactionAmount"));
    		}
        	
        	
        	System.out.println();
        	}
        	
        stat.close();
        
	}
	
	
	public static void terminate() throws SQLException
	{
		conn.close();
	}
	
	
	
	public static void main(String[] args) {
		try { 

			initialize();
            
			//analyzeEventsQueriesAmounts();
			//analyzeRuleComponentsQueries();
			analyzeRecommendedActionsQueries();
            
			
			terminate();

        } catch (Exception e) { 
            e.printStackTrace();
        }  

	}

}
