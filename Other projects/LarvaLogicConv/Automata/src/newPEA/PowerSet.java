package newPEA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PowerSet {

	public ArrayList<Integer> in = new ArrayList<Integer>();
	public ArrayList<Integer> wait = new ArrayList<Integer>();
	public ArrayList<Integer> gteq = new ArrayList<Integer>();
	public ArrayList<Integer> less = new ArrayList<Integer>();

	public ArrayList<Integer> wait2 = new ArrayList<Integer>();
	public ArrayList<Integer> gteq2 = new ArrayList<Integer>();
	public ArrayList<Integer> less2 = new ArrayList<Integer>();
	
	public boolean equals(Object ps)
	{
		if (!(ps instanceof PowerSet))
			return false;
		else
			return ((PowerSet)ps).toString().equals(this.toString());
	}
	
	public String toString()
	{
		String string = "{";
		for (Integer i : in)
			string += i + ",";
		for (Integer i : wait)
			string += i + ">,";
		for (Integer i : gteq)
			string += i + ">=,";
		for (Integer i : less)
			string += i + "<,";
		for (Integer i : wait2)
			string += i + ">',";
		for (Integer i : gteq2)
			string += i + ">=',";
		for (Integer i : less2)
			string += i + "<',";
		if(string.length() > 1)
			string  = string.substring(0, string.length()-1);
		return string + "}";
	}

	public String toLarva() {
		String string = "loc";
		for (Integer i : in)
			string += i + "i";
		for (Integer i : wait)
			string += i + "w";
		for (Integer i : gteq)
			string += i + "g";
		for (Integer i : less)
			string += i + "l";
		for (Integer i : wait2)
			string += i + "ww";
		for (Integer i : gteq2)
			string += i + "gg";
		for (Integer i : less2)
			string += i + "ll";
		return string;
	}

	public boolean isValid()
	{
		if (wait.containsAll(gteq) && wait2.containsAll(gteq2))
			return true;
		else
			return false;
	}
}
