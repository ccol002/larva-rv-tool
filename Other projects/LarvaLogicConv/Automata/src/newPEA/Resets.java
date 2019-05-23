package newPEA;

import java.util.ArrayList;

public class Resets {

	public ArrayList<Clock> clocks = new ArrayList<Clock>();
	public ArrayList<Counter> counters = new ArrayList<Counter>();
	
	public String toString()
	{
		return clocks.toString() + counters.toString();
	}

	public String toLARVA() {
		String string = "";
		for (Clock c : clocks)
			string += c.name + ".reset();";
		for (Clock c : clocks)
			string += c.name + "=0;";
		return string;
	}
}
