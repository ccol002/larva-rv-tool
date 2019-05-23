import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;

import larva.SC;
import larva._cls_project0;
import Events.EventGenerator;

public class SC {

	public static String propName = "project";
	public static _cls_project0 globalClass;
	public static String database = EventGenerator.database;//"new20";
	public static long initialTime = EventGenerator.COP;//1230000000000l;//1218900000000l;//0l;//1218900000000l;//1230000000000l;

	//the time dormancy was run for the first time was 1228900000000
	// 16 / 12 / 2009
	
	static String url;
	static Connection conn; 
	static boolean initialized = false;

	static boolean init = true;

	static {
		createConnection();
	}
	
	static void createConnection()
	{
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
	}
	
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
			
			try{
				s.execute("CREATE INDEX vw_alloc_index ON aaaa(allocationId);");
				}
				catch(Exception ex) {//ignore if it is duplicated
					}
			
			try{
				s.execute("CREATE INDEX tx_entry_index ON aaaa(transactionEntryId);");
				}
				catch(Exception ex) {//ignore if it is duplicated
					}
			
			String sql = "select distinct targetUser from log_audit as logg where auditableEvent=\"USER_ACCT_CREATION\" and timestamp < " + starting +
			" and not exists(select targetUser from log_audit as loggg where logg.targetUser=loggg.targetUser" + 
			" and auditableEvent=\"USER_ACCT_BREAKAGE\" and timestamp < " + starting + ");";
			
			ResultSet rs = s.executeQuery(sql);

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
			
//			try{
//				s.execute("CREATE INDEX allocid_index ON users_alloc(instrumentId);");
//				}catch(Exception ex)
//				{//ex.printStackTrace();
//					}
			
			try{
				s.execute("CREATE INDEX alloc_index ON tl_transactionentry(sourceAllocationId);");
				}catch(Exception ex)
				{//ex.printStackTrace();
					}
				
			try{
				s.execute("CREATE INDEX transbeONtransie ON users_trans_be(instrumentEntryId);");
				}catch(Exception ex)
				{//ex.printStackTrace();
					}
	
			try{
				s.execute("CREATE INDEX transieONtrans ON users_trans_ie(transactionEntryId);");
				}catch(Exception ex)
				{//ex.printStackTrace();
					}
			
			String sql = "select id,userId from users_alloc as alloc where userGroup=\"USER\" and unix_timestamp(allocationDate) < " + (starting/1000)
			 + " and not exists(select * from tl_transactionentry as allocc where alloc.instrumentId=allocc.sourceAllocationId" 
			 + " and transactionType=\"RECLAIM\" and unix_timestamp(allocationDate) < " + (starting/1000) + ");";
			
			ResultSet rs = s.executeQuery(sql);

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
		createConnection();
		return null;
	}
	
	static String getUserType(String s)
	{
		try
		{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select origin from users where newuserId = \"" + s + "\";");
			if (rs.next()) 
			{
				String type = rs.getString("origin");				
				if (type.equals("CALL_CENTRE"))
					return type;
				else
					return "WEB";
			}
		} catch(Exception ex)
		{ex.printStackTrace();}
		
		return "WEB";
	}
	
	static String getUserScheme(String s)
	{
		try{
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select scheme from accs where newUserId = \"" + s + "\";");
		 if (rs.next()) 
			return rs.getString("scheme");				
		}catch(Exception ex)
		{ex.printStackTrace();}
		return "BASIC";
	}
	
	static HashMap<String, Object> initializeifUser(String s)
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
			
			
			//note that now not only are we including user transactions but any financial transaction
			//this is achieved by checking that the source instrument role is NOT ANY
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select creationTimestamp from tl_transactionentry" 
					+ " where userId=\""+s+"\" and not sourceInstrumentRole=\"ANY\" and transactionEntryStatus=\"SUCCESSFUL\"" 
					+ " and creationTimestamp < " + initialTime + " order by creationTimestamp desc limit 1;");

			long lastTX = 0;

			boolean active = false;
			
			if (rs.next()) {
				lastTX = rs.getLong("creationTimestamp");
				active = true;
			}
			
			//user creation
				rs = st.executeQuery("select timestamp from log_audit where targetUser = \"" + s + "\" and"
						+ " auditableEvent=\"USER_ACCT_CREATION\" and timestamp < "
					    + initialTime + " order by timestamp desc limit 1;");

				if (rs.next())
				{
					long temp = rs.getLong("timestamp");
					if (temp > lastTX)
						lastTX = temp;			
				}
				
			long lastBr = -1;
			
			rs = st.executeQuery("select timestamp from log_audit where targetUser = \""+s+"\"" + 
					    " and auditableEvent=\"USER_ACCT_BREAKAGE\" and timestamp < "
					    + initialTime + " order by timestamp desc limit 1;");
			if (rs.next())
				lastBr = rs.getLong("timestamp");
			
			
			long lastDM = -1;
			//last dormancy
			rs = st.executeQuery("select timestamp from log_audit where targetUser = \"" + s + "\" and"
					+ " auditableEvent=\"USER_ACCT_DORMANT\"  and timestamp < "
					    + initialTime + " order by timestamp desc limit 1;");
			if (rs.next()) 
				lastDM = rs.getLong("timestamp");
			
			//suspend
			long lastSus = -1;
			rs = st.executeQuery("select timestamp from log_audit where targetUser = \"" + s + "\" and"
					+ " auditableEvent=\"USER_ACCT_SUSPEND\"  and timestamp < "
					    + initialTime + " order by timestamp desc limit 1;");
			if (rs.next()) 
				lastSus = rs.getLong("timestamp");
			
			//unsuspend
			long lastUnSus = -1;
			rs = st.executeQuery("select timestamp from log_audit where targetUser = \"" + s + "\" and"
					+ " auditableEvent=\"USER_ACCT_UNSUSPEND\"  and timestamp < "
					    + initialTime + " order by timestamp desc limit 1;");
			if (rs.next()) 
				lastUnSus = rs.getLong("timestamp");
			
			//locked
			long lastLock = -1;
			rs = st.executeQuery("select timestamp from log_audit where targetUser = \"" + s + "\" and"
					+ " auditableEvent=\"USER_ACCT_LOCK\"  and timestamp < "
					    + initialTime + " order by timestamp desc limit 1;");
			if (rs.next()) 
				lastLock = rs.getLong("timestamp");
			
			//unlocked
			long lastUnLock = -1;
			rs = st.executeQuery("select timestamp from log_audit where targetUser = \"" + s + "\" and"
					+ " auditableEvent=\"USER_ACCT_UNLOCK\"  and timestamp < "
					    + initialTime + " order by timestamp desc limit 1;");
			if (rs.next()) 
				lastUnLock = rs.getLong("timestamp");
		
			list.put("scheme", SC.getUserScheme(s));

			list.put("origin", getUserType(s));
					
			list.put("passStart", lastTX);
			list.put("idp", lastTX);			
			list.put("bp", lastTX);

			if (lastDM > lastTX)//dormant
				list.put("STATE>dormancy", "dorm");
				
			if (lastBr > lastTX)	
				list.put("STATE>breakage", "break");
			
			if (lastSus > lastLock && lastSus > lastUnSus)
				list.put("STATE>user_lc", "suspended");
			else if (lastLock > lastSus && lastLock > lastUnLock)
				list.put("STATE>user_lc", "locked");
			else if (active)
				list.put("STATE>user_lc", "active");
			else
				list.put("STATE>user_lc", "created");
			
			
			st = conn.createStatement();
			
			String sql = "select count(distinct sourceAllocationId) as num from tl_transactionEntry as alloc where " +
			" userId = \""+s+"\" and DTYPE = \"FreezeTXEntry\" and creationTimestamp < " + initialTime
	 + " and not exists(select * from tl_transactionentry as allocc where alloc.sourceAllocationId=allocc.sourceAllocationId" 
	 + " and DTYPE=\"ThawTXEntry\" and alloc.creationTimestamp > allocc.creationTimestamp and alloc.creationTimestamp < " + initialTime + ");";
	
			
			rs = st.executeQuery(sql);

			if (rs.next())
			{
				list.put("frozenInstruments", rs.getInt("num"));
			}

			
			list.put("lastFreeze", lastDM);
			list.put("drp", lastDM);
			
			//for now assume that feeExpected is false... 
			//we cannot correctly check it anyway because of the issue of remote balance (actual and available)
			list.put("feeExpected", false);
			
			rs = st.executeQuery("select message from log_audit where targetUser = \"" + s 
					+ "\" and auditableEvent=\"USER_CHANGE_RIGHTS\" and timestamp < "
					+ SC.initialTime + " order by timestamp;");
			
			for (String right : processMessage(rs))
				list.put(right, true);

		} catch (Exception ex)
		{ex.printStackTrace();}

		return list;
	}

	static HashMap<String, Object> initializeifInstrument(String i, String s)
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
			ResultSet rs = st.executeQuery("select type,currency from users_alloc" 
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
			
			
			rs1 = st1.executeQuery("select balanceAfterAmount from aaaa"
			   + " where allocationId = \""+i+"\" and balanceType=\"ACTUAL\""
			   + " and timestamp < " + initialTime
			   + " order by timestamp desc limit 1;");
			
			if (rs1.next()){
				list.put("balance",rs1.getDouble("balanceAfterAmount"));
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

	static HashSet<String> processMessage(ResultSet rs)
	{

		HashSet<String> hs = new HashSet<String>();
		try{
		while (rs.next())
		{
			String message = rs.getString("message");
			if (message.contains("grant") && message.contains("right"))
				hs.addAll(rightsList(message));
			else if (message.contains("Set") && message.contains("right"))
				hs = rightsList(message);
			else if (message.contains("revoke") && message.contains("right"))
				hs.removeAll(rightsList(message));
		}
		}catch(Exception ex)
		{ex.printStackTrace();}
		return hs;
	}
	
	static HashSet<String> rightsList(String list)
	{
		HashSet<String> hs = new HashSet<String>();
		
		String array[] = list.split("\\W");//"[ ,()\\[\\]]");
		
		for (String s: array)
			if (s.indexOf("USER_") == 0)
				hs.add(s);
		
		return hs;
	}
	
	static boolean passedDays(long start,long now,int days)
	{//an allowance of 5 days
		return (((now-start)/(1000*60*60*24))>(days + 5));
	}
	
	static ArrayList balanceEntries(String user, String TransactionID)
	{
		ArrayList list = new ArrayList();
		try{
		Statement st = conn.createStatement();
		//possibly need ordering on timestamp
//		ResultSet rs = st.executeQuery("select allocationId, balanceAdjustmentAmount, balanceAfterAmount " +
//				"from users_trans_be,users_trans_ie " +
//				"where users_trans_be.instrumentEntryId = users_trans_ie.instrumentEntryId " +
//				"and users_trans_be.instrumentEntryId in " +
//				"(select instrumentEntryId from users_trans_ie " +
//				"where transactionEntryId = \"" + TransactionID + "\") " +
//				"order by users_trans_be.creationTimestamp;");
				
		ResultSet rs = st.executeQuery("select * from aaaa where transactionEntryId=\""+TransactionID+"\";");
				
		while (rs.next())
		{
			HashMap hm = new HashMap();
			hm.put("user", user);
			hm.put("instrument", rs.getString("allocationId"));
			hm.put("adjustment", rs.getDouble("balanceAdjustmentAmount"));
			hm.put("balance", rs.getDouble("balanceAfterAmount"));
			hm.put("type", rs.getString("balanceType"));
			list.add(hm);
		}
		}catch(Exception ex)
		{ex.printStackTrace();}
		return list;
	}
	
	static boolean retrieveState()
	{//if true the initialization takes place
		try{
		createConnection();
		Statement s = globalClass._conn.createStatement();
		ResultSet rs = s.executeQuery("select * from _cls_" + propName + "0;");
		return !rs.next();
		}catch(Exception ex){}
		return true;
	}
	
}