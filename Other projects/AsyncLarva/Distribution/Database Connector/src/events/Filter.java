package events;

import java.sql.ResultSet;
import java.util.HashMap;

public class Filter {

	public final static int EQUALS = 0;
	public final static int CONTAINS = 1;
	public final static int NONE = 2;
	public final static int NOT_EQUALS = 3;
	
	String field;//the actual field name in the database table being queried
	String name;//a label used to refer to the field in the context of the monitor
	int type = EQUALS;
	Object parameter;//the parameter of the query
	
	public Filter(String name, String field, int type, Object parameter)
	{
		this.name = name;
		this.field = field;
		this.type = type;
		this.parameter = parameter;
	}
	
	public Filter(String field, int type, Object parameter)
	{
		this(field, field, type, parameter);
	}
	
	
	public Filter(String name, String field)
	{
		this.name = name;
		this.field = field;
		this.type = NONE;
	}
	
	//the name used will be the same as the field name 
	public Filter(String field)
	{
		this(field, field);
	}
	
	public String getSQL()
	{
		switch (type){
		case 0:  return field + "='" + parameter + "'";
		case 1:  return field + " like '%" + parameter + "%'";
		case 2:  return null;
		case 3:  return "not " + field + "='" + parameter + "'";
		default: return null;
		}
	}
	
	public boolean test(ResultSet rs, HashMap<String, Object> params)throws Exception
	{	
		Object actual = rs.getObject(field);
		
		if (actual == null) return false;
		
		params.put(name, actual);
		
		switch (type){
		case 0:  return parameter.equals(actual);
		case 1:  return actual.toString().contains(parameter.toString());
		case 2:  return true;
		case 3:  return !parameter.equals(actual);
		default: return false;
		}
	}
	
	
}
