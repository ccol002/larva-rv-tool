package Events;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class Template {

	public static final int LONG_TIMESTAMP = 0;
	public static final int DATETIME_TIMESTAMP = 1;
		
	String name;
	String table;
	String database;
	String timeStampField;
	public int timestampMode = 0;
	
	private String sqlQuery;
	private LinkedHashMap<String,HashSet<Filter>> filters = new LinkedHashMap<String,HashSet<Filter>>();
	public LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>(); 
	
	//this class suppost 2 modes: either specify an sql and filter it
	//or let the class generate the sql with implicit filter (using the where clause)
	//this depends on which constructor is being used
	
	
//	public Template(String name, String table, String database, String timeStampField)
//	{
//		this(name, name, table, database, timeStampField);
//	}
//	
//	public Template (String name, String sql, String timeStampField)
//	{
//		this(name, name, sql, timeStampField);
//	}
	
	public Template(String name, String table, String database, String timeStampField)
	{
		this.name = name;
		this.table = table;
		this.database = database;
		this.timeStampField = timeStampField;
		addFilter(new Filter(timeStampField));
	}
	
	public Template (String name, String sql, String timeStampField)
	{
		this.name = name;
		this.sqlQuery = sql;
		this.timeStampField = timeStampField;
		addFilter(new Filter(timeStampField));
	}
	
	public boolean is (String name)
	{
		return this.name.equals(name);
	}
	
	public void addFilter(Filter f)
	{
		//automatically replaces old value
		//filters.put(f.field, f);
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
		
	}
	
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
				if (timestampMode == this.DATETIME_TIMESTAMP)
					sb.append("unix_timestamp(");
				sb.append(timeStampField + " >= " + EventGenerator.starttime);
				if (timestampMode == this.DATETIME_TIMESTAMP)
					sb.append(")*1000");
				
				sb.append(" and ");
				
				if (timestampMode == this.DATETIME_TIMESTAMP)
					sb.append("unix_timestamp(");
				sb.append(timeStampField + " < " + EventGenerator.endtime);
				if (timestampMode == this.DATETIME_TIMESTAMP)
					sb.append(")*1000");
				
				
				sb.append(" order by " + timeStampField + ";");
			}
			return sb.toString();
		}
	}
	
	public void call(String name, long time){}
	
	public boolean pass(ResultSet rs)throws Exception
	{
		if (table != null)//depends which constructor was used
		{
			for (String f: filters.keySet())
			{
				String label = "";
				for (Filter filter: filters.get(f))
				{
					label = filter.name;
					break;
				}
				
				if (f.equals("message"))
					params.put(label, rs.getString(f));
				else
				{
					Object o = rs.getObject(f);
					if (o != null)
						params.put(label, o);
				}
			}
			return true;//exact sql used
		}
		else
		{
			boolean ok = true;
			int i = 0;
			while (ok && i < filters.size())
			{
				boolean satisfied = false;
				for (Filter f : filters.get(i))
					satisfied |= f.test(rs, params);
				ok &= satisfied;
				i++;
			}
			return ok;
		}
	}
	
	

	
}
