package larva; 

import Events.Template;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;

import larva.SC;
import larva._cls_project0;
import Events.EventGenerator;

public class SC {

	public static String propName = "project";
	public static _cls_project0 globalClass;
	public static String database = EventGenerator.database;//"new20";
	public static long initialTime = EventGenerator.starttime;//1230000000000l;//1218900000000l;//0l;//1218900000000l;//1230000000000l;

	//the time dormancy was run for the first time was 1228900000000
	// 16 / 12 / 2009
	
	static String url;
	static Connection conn; 
	static boolean initialized = false;

	public static boolean init = new SC().loadInit();

	static {
		createConnection();
	}

	boolean loadInit()
	{
		try{
		String className = this.getClass().getName()+ ".class";
		className = className.substring(className.indexOf(".")+1);
		String path = this.getClass().getResource(className).getPath().replaceAll("%20", " ");
		
		path = path.substring(0, path.lastIndexOf('/'));
		
		Properties prop = new Properties();
		File f = new File(path + "\\initialization.txt");
		prop.load(new FileReader(f));
	
		if (prop.containsKey("initialization"))
			init = prop.getProperty("initialization").equals("true");
		
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return init;
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
			
//			String sql = "select distinct targetUser from log_audit as logg where auditableEvent=\"USER_ACCT_CREATION\" and timestamp < " + starting +
//			" and not exists(select targetUser from log_audit as loggg where logg.targetUser=loggg.targetUser" + 
//			" and auditableEvent=\"USER_ACCT_BREAKAGE\" and timestamp < " + starting + ");";
			
			String sql = "select newuserId from users;";
				
			ResultSet rs = s.executeQuery(sql);

			ArrayList al = new ArrayList();
			while (rs.next())
				al.add(rs.getString("newUserId"));

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
			
			String sql = "select id,userId from users_alloc as alloc where userGroup=\"USER\"" +
					" and unix_timestamp(allocationDate) < " + (starting/1000) + " ;";
			
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
	
	static void listput(HashMap<String, Object> list, String name, Object o)
	{
		if (!list.containsKey(name))
			list.put(name, o);
		else
		{
			list.remove(name);
			listput(list, name, o);
		}
	}
	
	static HashMap<String, Object> initializeProperties()
	{
		HashMap<String, Object> list = new LinkedHashMap<String, Object>();
		try{
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select message from props where timestamp < \"" + initialTime + "\" order by timestamp;");
		while (rs.next())
		{
			String message = rs.getString("message");

			if (message.contains("Ignore") && message.contains("Blocked") && message.contains("Dormancy")) 
				listput(list, "ignore_blocked_dorm", (message.contains("true")));
			else if (message.contains("Ignore") && message.contains("Suspended") && message.contains("Dormancy")) 
				listput(list, "ignore_suspended_dorm", (message.contains("true")));
			else if (message.contains("Ignore") && message.contains("Frozen") && message.contains("Dormancy"))
				listput(list, "ignore_frozen_dorm", (message.contains("true")));
			else if (message.contains("Ignore") && message.contains("Blocked") && message.contains("Breakage")) 
				listput(list, "ignore_blocked_break", (message.contains("true")));
			else if (message.contains("Ignore") && message.contains("Suspended") && message.contains("Breakage")) 
				listput(list, "ignore_suspended_break", (message.contains("true")));
			else if (message.contains("Ignore") && message.contains("Frozen") && message.contains("Breakage")) 
				listput(list, "ignore_frozen_break", (message.contains("true")));
			else if (message.contains("Ignore") && message.contains("Blocked") && message.contains("Warning")) 
				listput(list, "ignore_blocked_warn", (message.contains("true")));
			else if (message.contains("Ignore") && message.contains("Suspended") && message.contains("Warning")) 
				listput(list, "ignore_suspended_warn", (message.contains("true")));
			else if (message.contains("Ignore") && message.contains("Frozen") && message.contains("Warning")) 
				listput(list, "ignore_frozen_warn", (message.contains("true")));
			else System.out.println("Warning!! A property changed could not be understood!! " + message);
		}
		}
		catch(Exception ex)
		{ex.printStackTrace();}
		return list;
	}
	
	static HashMap<String, Object> initializeifMain(){
		createConnection();
		return initializeProperties();
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
						+ " auditableEvent=\"USER_ACCT_CREATION\"" +
								" and not message like \"%failed%\"" +
								" and timestamp < "
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
					+ " auditableEvent=\"USER_ACCT_LOCK\"" +
					" and message like \"%uccess%\"" +
							"  and timestamp < "
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
				
			
			if (lastBr <= lastTX)
			{
				list.put("passStart", lastTX);
				list.put("idp", lastTX);			
				list.put("bp", lastTX);
			}
			
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
			else if (lastBr > lastTX)	
				list.put("STATE>user_lc", "closed");
			else
				list.put("STATE>user_lc", "created");
			
			
			st = conn.createStatement();
			
			String sql = "select count(distinct sourceAllocationId) as num from tl_transactionEntry as alloc where " +
			" userId = \""+s+"\" and DTYPE = \"FreezeTXEntry\" and freezeOrigin= \"DORMANCY_FREEZE\" and creationTimestamp < " + initialTime
			+ " and not exists(select * from tl_transactionentry as allocc where alloc.sourceAllocationId=allocc.sourceAllocationId" 
			+ " and DTYPE=\"ThawTXEntry\" and alloc.creationTimestamp > allocc.creationTimestamp and alloc.creationTimestamp < " + initialTime + ");";
	
			
			rs = st.executeQuery(sql);

			if (rs.next())
			{
				list.put("nonDormfrozenInstruments", rs.getInt("num"));
			}

			
			list.put("lastFreeze", lastDM);
			list.put("drp", lastDM);
			
			//for now assume that feeExpected is false... 
			//we cannot correctly check it anyway because of the issue of remote balance (actual and available)
			list.put("feeExpected", false);
			
			rs = st.executeQuery("select message from log_audit where targetUser = \"" + s 
					+ "\" and auditableEvent=\"USER_CHANGE_RIGHTS\" and timestamp < "
					+ SC.initialTime + " order by timestamp;");
			
			HashSet<String> rightslist = processMessage(rs);
			
			if (rightslist.size() > 0)
			{
				for (String right : rightslist)
					list.put(right, true);
			}
			else
			{
				rs = st.executeQuery("select userRight from rights where newUserId = \"" + s + "\";");
				while (rs.next())
					list.put(rs.getString("userRight"), true);
			}
			


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
			ResultSet rs1 = st1.executeQuery("select creationTimestamp,DTYPE,freezeOrigin from tl_transactionEntry" +
					" where sourceAllocationId = \""+i+"\"" + 
			    " and (DTYPE=\"FreezeTXEntry\" or DTYPE=\"ThawTXEntry\" or DTYPE=\"ReclaimTXEntry\")and creationTimestamp < "
			    + initialTime + " order by creationTimestamp desc limit 1;");
			
			String type = "";
			String freezeOrigin = "";
			if (rs1.next()){
		    	type = rs1.getString("DTYPE");
		    	freezeOrigin = rs1.getString("freezeOrigin");
			}
			
			
			rs1 = st1.executeQuery("select balanceAfterAmount from aaaa"
			   + " where allocationId = \""+i+"\" and balanceType=\"ACTUAL\""
			   + " and timestamp < " + initialTime
			   + " order by timestamp desc limit 1;");
			
			if (rs1.next()){
				list.put("balance",rs1.getDouble("balanceAfterAmount"));
				}
			else if (!type.equals("ReclaimTXEntry"))
			{
				System.out.println(" user " + s + " alloc " + i);
			}
			
			//set the state
			list.put("STATE>instrument_lc", "assigned");
			if (type.equals("FreezeTXEntry"))
			{
				list.put("STATE>instrument_lc", "restricted");
				list.put("dormancy_freeze", true);
			}
			else if (type.equals("ReclaimTXEntry"))
				list.put("STATE>instrument_lc", "fallow_assigned");
				
			

		} catch (Exception ex)
		{ex.printStackTrace();}

		return list;

	}

	public static boolean loadLimits(HashMap<String, Object> limits, String user, String currency, String scheme, String origin, String type)
	{
		int monthCount = Integer.MAX_VALUE, dayCount = Integer.MAX_VALUE;
		double monthAmount = Double.MAX_VALUE, dayAmount = Double.MAX_VALUE;
		
		try{ 
		Statement st1 = conn.createStatement();
		ResultSet rs1 = st1.executeQuery("SELECT cs.setId,cpd1.stringValue as 'AuthorDimension'" +
				", prps.integerValue,prps.decimalValue,prps.stringValue" +
				" FROM propsvalue prps," +
				" propsset cs" +
				" LEFT OUTER JOIN propscontext cpd1 ON cs.setId = cpd1.setId AND cpd1.dimensionName='AuthorDimension'" +
				" LEFT OUTER JOIN propscontext cpd2 ON cs.setId = cpd2.setId AND cpd2.dimensionName='AuthorGroupDimension'" +
				" LEFT OUTER JOIN propscontext cpd3 ON cs.setId = cpd3.setId AND cpd3.dimensionName='TransactionTypeDimension'" +
				" LEFT OUTER JOIN propscontext cpd4 ON cs.setId = cpd4.setId AND cpd4.dimensionName='CurrencyCodeDimension'" +
				" LEFT OUTER JOIN propscontext cpd5 ON cs.setId = cpd5.setId AND cpd5.dimensionName='TransactionDirectionDimension'" +
				" LEFT OUTER JOIN propscontext cpd6 ON cs.setId = cpd6.setId AND cpd6.dimensionName='AccountSchemeDimension'" +
				" LEFT OUTER JOIN propscontext cpd7 ON cs.setId = cpd7.setId AND cpd7.dimensionName='UserOriginDimension'" +
				" LEFT OUTER JOIN propscontext cpd0 ON cs.setId = cpd0.setId" +
				" AND cpd0.dimensionName!='AuthorDimension' AND cpd0.dimensionName!='AuthorGroupDimension'" +
				" AND cpd0.dimensionName!='TransactionTypeDimension' AND cpd0.dimensionName!='CurrencyCodeDimension'" +
				" AND cpd0.dimensionName!='TransactionDirectionDimension' AND cpd0.dimensionName!='AccountSchemeDimension'" +
				" AND cpd0.dimensionName!='UserOriginDimension'" +
				" WHERE cs.propertyId='SOA.TRANSACTION' AND cpd0.dimensionName IS NULL" +
				" AND (cpd1.stringValue='USER|" + user + "' OR cpd1.stringValue = \" !current\" OR cpd1.stringValue IS NULL)" +
				" AND (cpd2.stringValue='USER'  OR cpd2.stringValue IS NULL) " +
				" AND (cpd3.stringValue='" + type + "'  OR cpd3.stringValue IS NULL)" +
				" AND (cpd4.stringValue='" + currency + "'   OR cpd4.stringValue IS NULL)" +
				" AND (cpd5.stringValue='IN'    OR cpd5.stringValue IS NULL)" +
				" AND (cpd6.stringValue='" + scheme + "' OR cpd6.stringValue IS NULL)" +
				" AND (cpd7.stringValue='" + origin + "'   OR cpd7.stringValue IS NULL)" +
				" and prps.setId=cs.setId" +
				" ORDER BY cpd1.stringValue DESC, cpd2.stringValue DESC, cpd3.stringValue DESC, cpd4.stringValue DESC, cpd5.stringValue DESC," +
				" cpd6.stringValue DESC, cpd7.stringValue DESC;");
		
		
		String specificDegreeDc = null;
		boolean initializedDc   = false;
		String specificDegreeDa = null;
		boolean initializedDa   = false;
		String specificDegreeMc = null;
		boolean initializedMc   = false;
		String specificDegreeMa = null;
		boolean initializedMa   = false;
		
		while (rs1.next())
		{
			String period   = rs1.getString("stringValue");
			String specific = rs1.getString("AuthorDimension");
			int count       = rs1.getInt("integerValue");
			double amount   = rs1.getDouble("decimalValue");
			
			if (count != 0 && period.equals("MONTH"))
			{
				if (!initializedMc
					|| (specific != null && specific.equals(" !current") && specificDegreeMc == null)
					|| (specific != null && specific.contains("USER") && !specificDegreeMc.contains("USER")))
				{
					initializedMc = true;
					specificDegreeMc = specific;
					monthCount = count;
				}
			}
			else if (count != 0 && period.equals("DAY"))
			{
				if (!initializedDc
					|| (specific != null && specific.equals(" !current") && specificDegreeDc == null)
					|| (specific != null && specific.contains("USER") && !specificDegreeDc.contains("USER")))
				{
					initializedMc = true;
					specificDegreeDc = specific;
					dayCount = count;
				}
			}
			if (amount != 0 && period.equals("MONTH"))
			{
				if (!initializedMa
					|| (specific != null && specific.equals(" !current") && specificDegreeMc == null)
					|| (specific != null && specific.contains("USER") && !specificDegreeMc.contains("USER")))
				{
					initializedMa = true;
					specificDegreeMa = specific;
					monthAmount = amount;
				}
			}
			else if (amount != 0 && period.equals("DAY"))
			{
				if (!initializedDa
					|| (specific != null && specific.equals(" !current") && specificDegreeDa == null)
					|| (specific != null && specific.contains("USER") && !specificDegreeDa.contains("USER")))
				{
					initializedDa = true;
					specificDegreeDa = specific;
					dayAmount = amount;
				}
			}
		}
		} catch (Exception ex)
		{ex.printStackTrace();}

		limits.clear();
		limits.put("monthCount", monthCount);
		limits.put("dayCount", dayCount);
		limits.put("monthAmount", monthAmount);
		limits.put("dayAmount", dayAmount);
		
		return true;
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
			
			if (TransactionID == null || TransactionID.equals(""))
				throw new Exception("No transaction Id specified!!!");
			
			
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
			hm.put("timestamp", rs.getLong("timestamp"));
			hm.put("txId", TransactionID);
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
	
	static String _txID = "";
	static String _currency = "";
	static Double _amount = -1.0;
	
	public static String getLoadCurrency(String txID)
	{
		try{
		if (txID.equals(_txID))
			return _currency;
		else
		{
			_txID = txID;
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select  balancesCurrency,actualAdjustmentAmount from users_trans_ie" +
					" where instrumentSink = \"DESTINATION\" and transactionEntryId = \"" + txID + "\";");
			if (rs.next())
			{
				_currency = rs.getString("balancesCurrency");
				_amount = rs.getDouble("actualAdjustmentAmount");
				return _currency;
			}
			else
			{
				return null;
			}
		}
		}catch(Exception ex)
		{ex.printStackTrace();}
		return null;
	}
	
	public static Double getLoadAmount(String txID)
	{
		try{
	
		if (txID.equals(_txID))
			return _amount;
		else
		{
			_txID = txID;
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select  balancesCurrency,actualAdjustmentAmount from users_trans_ie" +
					" where instrumentSink = \"DESTINATION\" and transactionEntryId = \"" + txID + "\";");
			if (rs.next())
			{
				_currency = rs.getString("balancesCurrency");
				_amount = rs.getDouble("actualAdjustmentAmount");
				return _amount;
			}
			else
			{
				return null;
			}
		}
		}catch(Exception ex)
		{ex.printStackTrace();}
		return null;
	}
}
