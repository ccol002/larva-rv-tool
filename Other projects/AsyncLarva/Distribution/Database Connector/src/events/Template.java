package events;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.LinkedHashMap;

/*
 * This class identifies a table in a database
 *  and contains a number of filters on fields of that table 
 *  
 *  consequently, it can provide an SQL equivalent of the filters to be executed on the table
 *  and communicate the retrieved rows to the monitor
 *  
 *  The equivalent of this class in monitoring terms is an event
 *  while filters put conditions on the parameters of that event 
 *  and makes parameters available to the monitor
 */

public class Template {

	//types of timestamp
	public static final int LONG_TIMESTAMP = 0;
	public static final int DATETIME_TIMESTAMP = 1;
		
	String name;//the name of the event for monitoring purposes
	String table;
	String database;
	String timeStampField;//the name of the timestamp field in the table
	int timestampMode;
	
	private String sqlQuery;
	private LinkedHashMap<String,HashSet<Filter>> filters = new LinkedHashMap<String,HashSet<Filter>>();
	public LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>(); 
	
	//this class suppost 2 modes: either specify an sql and filter it
	//or let the class generate the sql with implicit filter (using the where clause)
	//this depends on which (public) constructor is being used
	
	private Template(String name, String timeStampField, int timeStampMode)
	{
		this.name = name;
		this.timeStampField = timeStampField;
		this.timestampMode = timeStampMode;
		addFilter(new Filter(timeStampField));//timestamp field is always retrieved
	}
	
	public Template(String name, String table, String database, String timeStampField, int timeStampMode)
	{
		this(name, timeStampField, timeStampMode);
		this.table = table;
		this.database = database;
	}
	
	//NOTE that when passing SQL, any filters are applied a posteriori, over the resultset
	// this is probably less efficient than the other approach
	public Template (String name, String sql, String timeStampField, int timeStampMode)
	{
		this(name, timeStampField, timeStampMode);
		this.sqlQuery = sql;
	}
	
	//test if the name of this "event" is equivalent to the parameter
	public boolean is (String name)
	{
		return this.name.equals(name);
	}
	
	//adds a filter... 
	//or in more practical terms, a condition on a parameter to the event
	/*
	 * Filters having the same field name will be OR-ed in the WHERE clause of the SQL statement
	 * while filters having a different field name will be AND-ed in the WHERE clause of the SQL statement
	 */
	public Template addFilter(Filter f)
	{
		if (filters.containsKey(f.field))
		{
			filters.get(f.field).add(f);
		}
		else
		{
			HashSet<Filter> hs = new HashSet<Filter>();
			hs.add(f);
			filters.put(f.field, hs);
		}
		
		return this;
	}
	
	//Joining the filters of the same name with ORs
	public String fromHashSet(HashSet<Filter> hs)
	{
		String s = "";
		for (Filter f : hs)
		{
			String t = f.getSQL();
			if (t != null)
				s += t + " or ";
		}
		if (s.length() == 0)
			return null;
		else
			return "(" + s.substring(0, s.length()-4) + ")";
	}
	
	//form SQL statement based on the attributes of the object
	public String getSQL()
	{
		if (sqlQuery != null)
			return sqlQuery;
		else
		{
			StringBuilder sb = new StringBuilder();
			sb.append("select ");
			for (String f : filters.keySet())
				sb.append(f + ",");
			sb.delete(sb.length()-1, sb.length());
			sb.append(" from " + table);
			//sb.append(" from " + database + "." + table);

			//conjunct all filter disjunctions
			StringBuilder sb2 = new StringBuilder();
			String s;
			for (HashSet<Filter> hs : filters.values())
				if ((s = fromHashSet(hs)) != null)
					sb2.append(s + " and ");
			

			if (sb2.length() > 0)
				sb.append(" where " + sb2.delete(sb2.length()-5, sb2.length()));

			if (timeStampField != null && timeStampField.length() > 0)
			{
				if (sb2.length()> 0) 
					sb.append(" and ");
				else
					sb.append(" where ");
				if (timestampMode == Template.DATETIME_TIMESTAMP)
					sb.append("unix_timestamp(");		
				sb.append(timeStampField);
				if (timestampMode == Template.DATETIME_TIMESTAMP)
					sb.append(")*1000");
				sb.append(" >= " + EventGenerator.starttime);
				
				sb.append(" and ");
				
				if (timestampMode == Template.DATETIME_TIMESTAMP)
					sb.append("unix_timestamp(");
				sb.append(timeStampField);
				if (timestampMode == Template.DATETIME_TIMESTAMP)
					sb.append(")*1000");
				sb.append(" < " + EventGenerator.endtime);
				
				
				
				sb.append(" order by " + timeStampField + ";");
			}
			return sb.toString();
		}
	}
	
	//method used to interface with monitor
	public void call(String name, long time){}
	
	//extract relevant parameters from the result set
	//and tests whether the current row passes through the filters if this has not be done through the SQL 
	public boolean pass(ResultSet rs)throws Exception
	{
		if (table != null)//depends which constructor was used
			//i.e. SQL was generated by class
		{
			//loop over filter names
			for (String f: filters.keySet())
			{
				//get label from first filter in the hashset of this field
				String label = filters.get(f).iterator().next().name;
				
				//if field is called message, assume it should be returned as string
//				if (f.equals("message"))
//					params.put(label, rs.getString(f));
//				else
//commented out as this seems to be very specific
				{
					Object o = rs.getObject(f);
					if (o != null)
						params.put(label, o);
				}
			}
			return true;//exact sql used
		}
		else //when SQL has been supplied by user, 
			//check whether each row satisfies the filters
		{
			boolean ok = true;
	
			for (String f_name : filters.keySet())
			{
				
				boolean satisfied = false;
				for (Filter f : filters.get(f_name))
					satisfied |= f.test(rs, params);
				ok &= satisfied;
				
				if (!ok) break;
			}
			
			return ok;
		}
	}
	
	

	
}
