package PEA;

import java.util.ArrayList;

public class Resets {

	public ArrayList<Clock> clocks = new ArrayList<Clock>();
	
	public String toString()
	{
		if (clocks.size() > 0)
			return clocks.toString();
		else 
			return "";
	}

	public String toLARVA() {
		String string = "";
		for (Clock c : clocks)
			string += c.name + ".reset();";
		return string;
	}
}
