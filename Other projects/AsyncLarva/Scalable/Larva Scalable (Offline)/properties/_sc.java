import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SC {

	static String database = "new20";
	static long initialTime = 1218900000000l;//0l;//1218900000000l;//1230000000000l;

	//the time dormancy was run for the first time was 1218900000000
	// 16 / 8 / 2009
	
	static String url;
	static Connection conn; 
	static boolean initialized = false;

	static ArrayList initiallyUsers(){
		System.out.println(">> Creating Initial List of Users...");
		try{
			long starting = initialTime;  
			Statement s = conn.createStatement();
			
			try{
			s.execute("CREATE INDEX user_index ON log_audit(targetUser);");
			}
			catch(Exception ex)
			{//ignore if it is duplicated
				}
			
			
			ResultSet rs = s.executeQuery("select distinct targetUser from log_audit as logg where auditableEvent=\"USER_ACCT_CREATION\" and timestamp < " + starting +
					" and not exists(select targetUser from log_audit as loggg where logg.targetUser=loggg.targetUser" + 
					" and auditableEvent=\"USER_ACCT_BREAKAGE\" and timestamp < " + starting + ");");

			ArrayList al = new ArrayList();
			while (rs.next())
				al.add(rs.getString("targetUser"));

			ArrayList als = new ArrayList();
			als.add(al);
			return als;

		}catch(Exception ex)
		{ex.printStackTrace();}
		return null;
	}

	static ArrayList initiallyInstruments(){
		System.out.println(">> Creating Initial List of Instruments...");
		try{
			long starting = initialTime;  
			Statement s = conn.createStatement();
			
			ResultSet rs = s.executeQuery("select id,userId from users_alloc as alloc where userGroup=\"USER\" and unix_timestamp(allocationDate) < " + starting +
					" and not exists(select * from tl_transactionentry as allocc where alloc.id=allocc.sourceAllocationId" + 
					" and transactionType=\"RECLAIM\" and unix_timestamp(allocationDate) < " + starting + ");");

			ArrayList al = new ArrayList();
			ArrayList al2 = new ArrayList();
			while (rs.next())
			{
				al.add(rs.getString("userId"));
				al2.add(rs.getString("id"));
			}
				
			ArrayList als = new ArrayList();
			als.add(al);
			als.add(al2);
			return als;

		}catch(Exception ex)
		{ex.printStackTrace();}
		return null;
	}

	static HashMap<String, Object> initializeifMain(){
		if (!initialized) {
		try { 
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  
			url = "jdbc:odbc:" + database;
			conn = DriverManager.getConnection(url);//,"root","root");
			initialized = true;
		} 
		catch (Exception ex) 
		{ ex.printStackTrace(); }
		}
		return null;
	}
	
	static HashMap<String, Object> initializeif(String s)
	{
		System.out.println(">> Initializing for user " + s);
		HashMap<String, Object> list = new HashMap<String, Object>();

		try {

			//check for breakage
			//Statement st = conn.createStatement();
			//ResultSet rs = st.executeQuery("select timestamp from log_audit where userId = \""+s+"\"" + 
			//    " and auditableEvent=\"USER_ACCT_BREAKAGE\" and timestamp < "
			//    + initialTime + " order by creationTimestamp desc limit 1;");
			//
			//long lastBR = -1;
			//if (rs.next())
			//   	lastBR = rs.getLong("timestamp");

			//last successful user transaction
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select creationTimestamp from tl_transactionentry" 
					+ " where userId=\""+s+"\" and authorGroup=\"USER\" and transactionEntryStatus=\"SUCCESSFUL\"" 
					+ " and creationTimestamp < " + initialTime + " order by creationTimestamp desc limit 1;");

			long lastTX = 0;

			if (rs.next()) {
				lastTX = rs.getLong("creationTimestamp");
			} else {
				//user creation
				rs = st.executeQuery("select timestamp from log_audit where targetUser = \"" + s + "\" and"
						+ " auditableEvent=\"USER_ACCT_CREATION\" order by timestamp desc limit 1;");

				if (rs.next()) 
					lastTX = rs.getLong("timestamp");
			}

			long lastDM = -1;
			//last dormancy
			rs = st.executeQuery("select timestamp from log_audit where targetUser = \"" + s + "\" and"
					+ " auditableEvent=\"USER_ACCT_DORMANT\" order by timestamp desc limit 1;");

			if (rs.next()) {
				lastDM = rs.getLong("creationTimestamp");
			}

			list.put("passStart", lastTX);
			list.put("idp", lastTX);			
			list.put("bp", lastTX);

			if (lastDM > lastTX)
				//dormant
				list.put("STATE>user_lc", "frozen");
			else
				list.put("STATE>user_lc", "created");

			list.put("lastFreeze", lastDM);
			list.put("drp", lastDM);
			
			//for now assume that feeExpected is false... 
			//we cannot correctly check it anyway because of the issue of remote balance (actual and available)
			list.put("feeExpected", false);

		} catch (Exception ex)
		{ex.printStackTrace();}

		return list;

	}

	static HashMap<String, Object> initializeifInstruments(String i, String s)
	{
		System.out.println(">> Initializing for user " + s + " instrument " + i);
		HashMap<String, Object> list = new HashMap<String, Object>();

		try {

			//check for breakage
			//Statement st = conn.createStatement();
			//ResultSet rs = st.executeQuery("select timestamp from log_audit where userId = \""+s+"\"" + 
			//    " and auditableEvent=\"USER_ACCT_BREAKAGE\" and timestamp < "
			//    + initialTime + " order by creationTimestamp desc limit 1;");
			//
			//long lastBR = -1;
			//if (rs.next())
			//   	lastBR = rs.getLong("timestamp");

			//last successful user transaction
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select id,type,currency from users_alloc" 
					+ " where id=\""+i+"\";");

			if (rs.next()){
			  list.put("type", rs.getString("type"));
			  list.put("currency", rs.getString("currency"));
			}
			
			//go back into the database and find last modification of instrument state change	
			
			Statement st1 = conn.createStatement();
			ResultSet rs1 = st1.executeQuery("select creationTimestamp,DTYPE from tl_transactionEntry" +
					" where sourceAllocationId = \""+i+"\"" + 
			    " and (DTYPE=\"FreezeTXEntry\" or DTYPE=\"ThawTXEntry\" or DTYPE=\"ReclaimTXEntry\")and creationTimestamp < "
			    + initialTime + " order by creationTimestamp desc limit 1;");
			
			String type = "";
			
			if (rs1.next()){
			type = rs1.getString("DTYPE");
			}
			
			//set the state
			list.put("STATE>instrument_lc", "assigned");
			if (type.equals("FreezeTXEntry"))
					list.put("STATE>instrument_lc", "restricted");
			else if (type.equals("ReclaimTXEntry"))
				list.put("STATE>instrument_lc", "fallow_assigned");

		} catch (Exception ex)
		{ex.printStackTrace();}

		return list;

	}

	boolean passedDays(long start,long now,int days)
	{//an allowance of 5 days
		return (((now-start)/(1000*60*60*24))>(days + 5));
	}
}