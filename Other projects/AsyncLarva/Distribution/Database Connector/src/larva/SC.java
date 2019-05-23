package larva; 

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;

import events.EventGenerator;
import events.Template;
import larva._cls_script0;

public class SC {

	public static String propName = "project";
	public static _cls_script0 globalClass;

	
	public static long initialTime = EventGenerator.starttime;
	static String url = EventGenerator.url;
	static String un = EventGenerator.un;
	static String pw = EventGenerator.pw;
	static Connection conn; 
	static boolean initialized = false;

	public static boolean init = new SC().loadInit();

	static {
		createConnection();
	}

	//not sure why I had opted for a non-static method...
	//probably to give time for other classes to get loaded
	boolean loadInit()
	{
		
		
			

//in a real environment one would want to remember whether initialisation has already occurred or not 
//by storing some information in a file

//		try{
//		String className = this.getClass().getName()+ ".class";
//		className = className.substring(className.indexOf(".")+1);
//		String path = this.getClass().getResource(className).getPath().replaceAll("%20", " ");
//		
//		path = path.substring(0, path.lastIndexOf('/'));
//		
//		Properties prop = new Properties();
//		File f = new File(path + "/initialization.txt");
//		prop.load(new FileReader(f));
//	
//		if (prop.containsKey("initialization"))
//			init = prop.getProperty("initialization").equals("true");
//		
//		}catch(Exception ex)
//		{
//			ex.printStackTrace();
//		}
		
		return true;
	}
	
	static void createConnection()
	{
		if (!initialized) {
			try { 
				//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  
				//url = "jdbc:odbc:" + database;
				Class.forName("com.mysql.jdbc.Driver");  
								
				conn = DriverManager.getConnection(url,un,pw);
				initialized = true;
			} 
			catch (Exception ex) 
			{ ex.printStackTrace(); }
			}
	}
	
	public static void cleanup()
	{
		try {
			conn.close();
		} catch (Exception Ex) { } // ignore
		
		try {
			_cls_script0._conn.close();
		} catch (Exception Ex) { } // ignore
		
	}
	
	//returns an ArrayList containing an ArrayList of users
	//NOTE: it is an ArrayList of ArrayLists for cases where the Foreach refers to multi-object instances
	static ArrayList initiallyUsers()
	{
		System.out.println(">> Creating Initial List of Users...");
		try{
			long starting = 0;  
			Statement s = conn.createStatement();
			
			String sql = "select userId from users;";
				
			ResultSet rs = s.executeQuery(sql);

			ArrayList al = new ArrayList();
			while (rs.next())
				al.add(rs.getString("userId"));

			ArrayList als = new ArrayList();
			als.add(al);
			return als;

		}catch(Exception ex)
		{ex.printStackTrace();}
		return null;
	}

		
	//retrieve any information which is useful to initialise monitors
	//particularly is the monitor configuration depends on some system
	// configuration, eg: changes logged in a database table	
	static HashMap<String, Object> initializeifMain()
	{
		createConnection();
		
		HashMap<String, Object> list = new LinkedHashMap<String, Object>();
		return list;
	}
	
	//retrieves key-value pairs for the particular instance
	static HashMap<String, Object> initializeifUser(String s)
	{
		System.out.println(">> Initializing for user " + s);
		HashMap<String, Object> list = new HashMap<String, Object>();

		return list;
	}

	
}
