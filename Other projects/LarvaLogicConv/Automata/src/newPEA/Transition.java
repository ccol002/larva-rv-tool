package newPEA;

public class Transition {

	public Location source, dest;
	public Resets resets;
	public Invariant guard;
	public Increments incs;
	
	public String toString()
	{
		return  source + "--" + guard + "," + resets + "," + incs + "-->" + dest;
	}

//	public String getEvents() {
//		return dest.getEvents();
//	}
	
	public Bool getCondition() throws Exception {
		Conjunction conj = new Conjunction();
		conj.conjunction.add(guard);
		conj.conjunction.add(dest.getCondition(resets,incs));
		conj.simplify();
		return conj;
	}
	
	public String getResets() {
		return resets.toLARVA();
	}
	
	public String getIncrements() {
		return incs.toLARVA();
	}
}
