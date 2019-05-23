//package newPEA;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Set;
//
//public class PowerSet {
//
//	public ArrayList<Integer> in = new ArrayList<Integer>();
//	public ArrayList<Integer> wait = new ArrayList<Integer>();
//	public ArrayList<Integer> gteq = new ArrayList<Integer>();
//	public ArrayList<Integer> less = new ArrayList<Integer>();
//	
//	public boolean equals(Object ps)
//	{
//		if (!(ps instanceof PowerSet))
//			return false;
//		else
//			return ((PowerSet)ps).toString().equals(this.toString());
//	}
//	
//	public String toString()
//	{
//		String string = "{";
//		for (Integer i : in)
//			string += i + ",";
//		for (Integer i : wait)
//			string += i + ">,";
//		for (Integer i : gteq)
//			string += i + ">=,";
//		for (Integer i : less)
//			string += i + "<,";
//		if(string.length() > 1)
//			string  = string.substring(0, string.length()-1);
//		return string + "}";
//	}
//
//	public String toLarva() {
//		String string = "loc";
//		for (Integer i : in)
//			string += i + "i";
//		for (Integer i : wait)
//			string += i + "w";
//		for (Integer i : gteq)
//			string += i + "g";
//		for (Integer i : less)
//			string += i + "l";
//		return string;
//	}
//}
