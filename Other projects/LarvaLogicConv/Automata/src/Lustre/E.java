package Lustre;

public class E {
	public enum Type {INT,BOOL,REAL}
	public enum Io{IN,OUT,LOCAL}
	
	public static String typeToJava(Type type)
	{
		if (type == E.Type.BOOL)
			return "boolean";
		else if (type == E.Type.INT)
			return "long";
		else //if (type == E.Type.BOOL)
			return "double";
	}
	
	public static String string(Type type)
	{
		if (type == E.Type.BOOL)
			return "bool";
		else if (type == E.Type.INT)
			return "int";
		else //if (type == E.Type.BOOL)
			return "real";
	}
}
