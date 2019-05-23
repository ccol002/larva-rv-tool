package newPEA;

import java.util.ArrayList;

public class Increments {

	ArrayList<Counter> counters = new ArrayList<Counter>();
	
	public String toString ()
	{
		return counters.toString();
	}
	
	public String toLARVA()
	{
		String string = "";
		for (Counter c : counters)
			string += c + "++;";
		return string;
	}
}
