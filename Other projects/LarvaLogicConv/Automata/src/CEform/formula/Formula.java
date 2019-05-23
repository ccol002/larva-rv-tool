package CEform.formula;

import java.io.PrintWriter;
import java.util.ArrayList;
import parsing.ParseException;
import parsing.Token;
import parsing.Tokenizer;
import CEform.CEform;
import CEform.events.Event;
import CEform.phase.Phase;
import PEA.BiImplication;
import PEA.Bool;
import PEA.BoolOp;
import PEA.Clock;
import PEA.ClockInv;
import PEA.Conjunction;
import PEA.Disjunction;
import PEA.EventDisj;
import PEA.Invariant;
import PEA.Location;
import PEA.Operator;
import PEA.PowerSet;
import PEA.Resets;
import PEA.Transition;
import PEA.Operator.Op;

public class Formula extends CEform{
	
	public ArrayList<CEform> chop = new ArrayList<CEform>();
	public Unary unary; 
	ArrayList<Clock> clocks = new ArrayList<Clock>();
	ArrayList<Location> locs = new ArrayList<Location>();
	
	ArrayList<Phase> trace = new ArrayList<Phase>();
	
	public int parse(ArrayList<Token> tokens, int cnt) throws ParseException 
	{
		if (tokens.get(cnt).is("not"))
		{
			unary = new Unary();
			cnt = unary.parse(tokens, cnt);
		}
		else throw new ParseException("not Expected: " + Tokenizer.debugShow(tokens, cnt));
		
		
		int brackets = 0;
		while (tokens.get(cnt).is("("))
		{
			brackets++;
			cnt++;
		}
		
		
		Phase p = new Phase();
		cnt = p.parse(tokens, cnt);
		chop.add(p);
		
		while (cnt < tokens.size() && tokens.get(cnt).is(";"))
		{
			cnt++;
			
			int brackets2 = 0;
			while (tokens.get(cnt).is("("))
			{
				brackets2++;
				cnt++;
			}
			
			if (cnt<tokens.size() && (tokens.get(cnt).is("change") || tokens.get(cnt).is("invchange")))
			{
				Event event = new Event();
				cnt = event.parse(tokens, cnt);
				chop.add(event);
			}
			else
			{
				Phase phase = new Phase();
				cnt= phase.parse(tokens, cnt);
				chop.add(phase);
			}
			
			
			while (brackets2 > 0 && tokens.get(cnt).is(")"))
			{
				brackets2--;
				cnt++;
			}
			if (brackets2 != 0) throw new ParseException(") Expected: " + Tokenizer.debugShow(tokens, cnt));
			
		}
	
		
		//checking that the last phase is TRUE
		
//		CEform ceform = chop.get(chop.size()-1);
//		
//		if (ceform instanceof Phase)
//		{
//			Phase p2 = (Phase)ceform;
//			
//			if (!(p2.conjunction.size() == 1 && p2.conjunction.get(0) instanceof BoolValue))
//				
//				throw new ParseException("True Expected: " + Tokenizer.debugShow(tokens, cnt));
//		}
//		else
//			throw new ParseException("True Expected: " + Tokenizer.debugShow(tokens, cnt));
//		
		
		while (brackets > 0 && tokens.get(cnt).is(")"))
		{
			brackets--;
			cnt++;
		}
		if (brackets != 0) throw new ParseException(") Expected: " + Tokenizer.debugShow(tokens, cnt));
		
		
		return cnt;
	}

	public String toString()
	{
		String string = "";
		for (CEform f: chop)
			string += ";" + f; 
		return "not(" + string.substring(1)+")";
	}

	public void fillInTrace()
	{
		EventDisj entryEvents = new EventDisj();
		int i = 1;
		
		for (CEform form: chop)
		{
			if (form instanceof Event)
			{
				ArrayList<PEA.Event> eventlist = new ArrayList<PEA.Event>();
				((Event)form).getEvents(eventlist);
				entryEvents.disjunction = eventlist;
			}
			else
			{
				if (((Phase)form).bounded)
				{
					Clock c = new Clock();
					c.name = "c" + i;
					((Phase)form).clock = c;
					clocks.add(c);
				}
				((Phase)form).entryEvents = entryEvents;
				i++;
				trace.add((Phase)form);
				entryEvents = new EventDisj();
			}
		}
	}
	
	public Formula prefix(int n)
	{
		Formula temp = new Formula();
		for(int i = 0; i < n; i++)
			temp.trace.add(this.trace.get(i));
		return temp;
	}
	
	public Invariant complete(Location loc, int i)
	{		
		Invariant invariant = new Invariant();
		
		if (i < 0)
		{//this should not make any difference since it is used in enter and 
		//we never need to enter the first phase (it is handled by the initial transitions
			invariant.expression = new PEA.BoolValue(false);
			return invariant;
		}
		
		PEA.BoolValue bv = new PEA.BoolValue();
		bv.value = new Boolean(loc.powerSet.in.contains(i));
		Bool bool1 = new Bool();
		bool1.lhs = bv;

		if (loc.powerSet.in.contains(i))
		{
			BoolOp bo = new BoolOp(BoolOp.Op.and);
			bool1.op = bo;

			if (loc.powerSet.wait.contains(i))
			{
				if (loc.powerSet.gteq.contains(i))
				{
					ClockInv ci = new ClockInv();
					ci.c = trace.get(i).clock;
					ci.operator = new Operator(Operator.Op.gteq);
					ci.bound = trace.get(i).bound;
					bool1.rhs = ci;
				}
				else
				{
					PEA.BoolValue bv2 = new PEA.BoolValue();
					bv2.value = false;
					bool1.rhs = bv2;
				}
			}
			else
			{
				if (loc.powerSet.less.contains(i))
				{
					ClockInv ci = new ClockInv();
					ci.c = trace.get(i).clock;
					ci.operator = new Operator(Operator.Op.ls);
					ci.bound = trace.get(i).bound;
					bool1.rhs = ci;
				}
				else
				{
					PEA.BoolValue bv2 = new PEA.BoolValue();
					bv2.value = true;
					bool1.rhs = bv2;
				}
			}
		}
		
		Bool bool2 = new Bool();
		PEA.BoolValue bv3 = new PEA.BoolValue();
		bv3.value = i>0 && ((Phase)trace.get(i)).allowEmpty;
		bool2.lhs = bv3;
		
	
			BoolOp bo2 = new BoolOp(BoolOp.Op.and);
			bool2.op = bo2;

			//if (i > 0)
			{
				Bool bool3 = new Bool();
				bool2.rhs = bool3;
				bool3.lhs = complete(loc,i-1).expression;

				bool3.op = new BoolOp(BoolOp.Op.and);

				bool3.rhs = trace.get(i).entryEvents;
			}
			//else
			//	bool2.rhs = trace.get(i).entryEvents;
		
		
		Bool bool = new Bool();
		bool.lhs = bool1;
		bool.rhs = bool2;
		bool.op = new BoolOp(BoolOp.Op.or);
		
		invariant.expression = bool;
		
		return invariant;
	}
	
	public boolean canSeep(Location loc, int i)
	{
		if (i<1)
			return false;
		return (((loc.powerSet.in.contains(i-1) && !loc.powerSet.wait.contains(i-1)))
					&& trace.get(i).entryEvents.disjunction.size()==0);		
	}
	
	public Invariant keep(Location loc, int i)
	{
		Invariant inv = new Invariant();
		Conjunction conj = new Conjunction();
		
		PEA.BoolValue bv = new PEA.BoolValue();
		bv.value = loc.powerSet.in.contains(i);
		conj.conjunction.add(bv);
		
		for (String s : trace.get(i).forbidden)
		{
			PEA.Event ev = new PEA.Event();
			ev.name = s;
			ev.unary = new PEA.Unary();
			conj.conjunction.add(ev);
		}
		
//		if (i < trace.size()-1)
			conj.conjunction.add(trace.get(i).inv.clone());//succloc.invariant.clone());//
//		else
//			conj.conjunction.add(new PEA.BoolValue(true));
		
		if (trace.get(i).upperbound && !canSeep(loc, i))
		{
			ClockInv ci = new ClockInv();
			ci.c = trace.get(i).clock;
			ci.operator = new Operator(Operator.Op.ls);
			ci.bound = trace.get(i).bound;
			conj.conjunction.add(ci);
		}
		//else add(true);//no difference to conjunction
		
		
		inv.expression = conj;
		return inv;
	}
	
	public Invariant enter(Location loc, int i)
	{
		Invariant inv = new Invariant();
		Conjunction conj = new Conjunction();

		conj.conjunction.add(complete(loc, i-1).expression);

		conj.conjunction.add(trace.get(i).entryEvents);
		
//		if (i < trace.size()-1)
		conj.conjunction.add(trace.get(i).inv.clone());//succloc.invariant.clone());//
//		else
//			conj.conjunction.add(new PEA.BoolValue(true));
//				
		inv.expression = conj;
		return inv;
	}
	
	public Invariant seep(Location loc, int i)
	{
		Invariant inv = new Invariant();
		Conjunction conj = new Conjunction();
		
		PEA.BoolValue bv = new PEA.BoolValue();
		bv.value = canSeep(loc, i);
		conj.conjunction.add(bv);

//		if (i < trace.size()-1)
			conj.conjunction.add(trace.get(i).inv.clone());//succloc.invariant.clone());//
//		else
//			conj.conjunction.add(new PEA.BoolValue(true));
				
		inv.expression = conj;
		return inv;
	}
	
	public Invariant guard(Location loc,ArrayList<Clock> clocks,Location succloc)throws Exception
	{
//		if (loc.equals(succloc) && loc.toString().equals("{0,1,1<}"))
//		{
//			System.out.println("ffffffffff");
//		}
		
		Invariant invariant  = new Invariant();
		Conjunction conj = new Conjunction();
		invariant.expression = conj;
		
		 for(int i = 0; i < trace.size(); i++)
		 {
			 BiImplication bi = new BiImplication();
			 PEA.BoolValue bv = new PEA.BoolValue();
			 bv.value = succloc.powerSet.in.contains(i);
			 bi.lhs = bv;
			 
			 Disjunction dis = new Disjunction();
			 Invariant kp = keep(loc, i);
			 Invariant ent = enter(loc, i);
			 ent.simplify();
			 Invariant sp = seep(succloc, i);
			 dis.disjunction.add(kp.expression);
			 dis.disjunction.add(ent.expression);
			 dis.disjunction.add(sp.expression);
			 
			 bi.rhs = dis;
			 conj.conjunction.add(bi);
			 
			 if (succloc.powerSet.in.contains(i) && !trace.get(i).upperbound && trace.get(i).bounded)
			 {
				 PEA.BoolValue bv2 = new PEA.BoolValue();
				 bv2.value = clocks.contains(trace.get(i).clock);
				 BiImplication bi2 = new BiImplication();
				 bi2.lhs = bv2;
				 Bool bool = keep(loc, i).expression;
				 bool.unary = new PEA.Unary();
				 bi2.rhs = bool;
				 conj.conjunction.add(bi2);
				 ///////////////////////////////////////////////////////
				 
				 PEA.BoolValue bv3 = new PEA.BoolValue();
				 bv3.value = succloc.powerSet.wait.contains(i);
				 BiImplication bi3 = new BiImplication();
				 bi3.lhs = bv3;
				 
				 PEA.BoolValue bv4 = new PEA.BoolValue();
				 bv4.value = loc.powerSet.wait.contains(i);
				 
				 PEA.BoolValue bv5 = new PEA.BoolValue();
				 bv5.value = clocks.contains(trace.get(i).clock);
				 
				 ClockInv ci = new ClockInv();
					ci.c = trace.get(i).clock;
					ci.operator = new Operator(Operator.Op.ls);
					ci.bound = trace.get(i).bound;
					
				Bool bool3 = new Bool();
				bool3.op = new BoolOp(BoolOp.Op.and);
				bool3.lhs = bv4;
				bool3.rhs = ci;
					
				Bool bool2 = new Bool();
				bool2.op = new BoolOp(BoolOp.Op.or);
				bool2.lhs = bv5;
				bool2.rhs = bool3;
				bi3.rhs = bool2;
				 
				conj.conjunction.add(bi3);
				///////////////////////////////////////////////
				
				BiImplication bi4 = new BiImplication();
				PEA.BoolValue bv6 = new PEA.BoolValue();
				bv6.value = succloc.powerSet.gteq.contains(i);
				bi4.lhs = bv6;
				
				Bool bool4 = new Bool();
				bi4.rhs = bool4;
				
				if (clocks.contains(trace.get(i).clock))
				{
					PEA.BoolValue bv7 = new PEA.BoolValue();
					bv7.value = trace.get(i).timeop.op.equals(Op.gteq);
					bool4.lhs = bv7;
					
					bool4.op = new BoolOp(BoolOp.Op.and);
					
					bool4.rhs = enter(loc, i).expression;
				}
				else
				{
					PEA.BoolValue bv7 = new PEA.BoolValue();
					bv7.value = loc.powerSet.gteq.contains(i) && succloc.powerSet.wait.contains(i);
					bool4.lhs = bv7;					
				}
				
				conj.conjunction.add(bi4);
				
				PEA.BoolValue bv8 = new PEA.BoolValue();
				bv8.value = !succloc.powerSet.less.contains(i);
				
				conj.conjunction.add(bv8);
			 }
			 else if (succloc.powerSet.in.contains(i) && trace.get(i).upperbound
					 && !canSeep(succloc, i))
			 {
				 BiImplication bi2 = new BiImplication();
				 bi2.lhs = new PEA.BoolValue(clocks.contains(trace.get(i).clock));
				 
				 Bool bool2 = new Bool();
				 bool2.lhs = enter(loc, i).expression;
				 bool2.op = new BoolOp(BoolOp.Op.or);
				 bool2.rhs = new PEA.BoolValue(canSeep(loc, i));
				 bi2.rhs = bool2;
				 conj.conjunction.add(bi2);
				 /////////////////////////////////////
				 
				 Bool bool3 = new Bool();
				 bool3.lhs = new PEA.BoolValue(!succloc.powerSet.wait.contains(i));
				 bool3.op = new BoolOp(BoolOp.Op.and);
				 bool3.rhs = new PEA.BoolValue(!succloc.powerSet.gteq.contains(i));
				 conj.conjunction.add(bool3);
				 /////////////////////////////////////
				 
				 BiImplication bi3 = new BiImplication();
				 bi3.lhs = new PEA.BoolValue(succloc.powerSet.less.contains(i));
				 
				 if (clocks.contains(trace.get(i).clock)) 
				 { 
					 Bool bool4 = new Bool();
					 bool4.lhs = new PEA.BoolValue(trace.get(i).timeop.op.equals(Op.ls));
					 bool4.op = new BoolOp(BoolOp.Op.or);
					 Bool bool5 = new Bool();
					 bool5.unary = new PEA.Unary();
					 bool5.lhs = enter(loc, i).expression;
					 bool5.simplify();
					 bool4.rhs = bool5;
					 bool4.simplify();
					 bi3.rhs = bool4;
				 }
				 else
				 {
					 bi3.rhs = new PEA.BoolValue(loc.powerSet.less.contains(i));
				 }
				 conj.conjunction.add(bi3);
			 }
			 else
			 {
				 conj.conjunction.add(new PEA.BoolValue(!clocks.contains(trace.get(i).clock)));
				 conj.conjunction.add(new PEA.BoolValue(!succloc.powerSet.wait.contains(i)));
				 conj.conjunction.add(new PEA.BoolValue(!succloc.powerSet.gteq.contains(i)));
				 conj.conjunction.add(new PEA.BoolValue(!succloc.powerSet.less.contains(i)));
			 }
		 }
		 return invariant;
	}
	
	public ArrayList<Integer> intersection(ArrayList<Integer> hs, ArrayList<Integer> hs2)
	{
		ArrayList<Integer> intersect = new ArrayList<Integer>();
		for (int i : hs)
			if (hs2.contains(i))
				intersect.add(i);
		return intersect;
	}
	
	public ArrayList<Integer> minus(ArrayList<Integer> hs, ArrayList<Integer> hs2)
	{
		ArrayList<Integer> minused = new ArrayList<Integer>();
		for (int i : hs)
			if (!hs2.contains(i))
				minused.add(i);
		return minused;
	}
	
	public ArrayList<Integer> lowerbound()
	{
		ArrayList<Integer> lb = new ArrayList<Integer>();
		for (int i = 0; i < trace.size(); i++)
			if (trace.get(i).bounded && !trace.get(i).upperbound)
				lb.add(i);
		return lb;
	}
	
	public Invariant init(Location loc)
	{
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(0);
		
		ArrayList<Integer> tempGteq = new ArrayList<Integer>();
		
		for (int i : loc.powerSet.wait)
		{
			boolean check = true;
			for (int j = 0; j <= i-1; j++)
			{
				if (!trace.get(j).allowEmpty)
				{
					check = false;
					break;
				}
			}
			if (check && trace.get(i).timeop.op.equals(Operator.Op.gteq))
				tempGteq.add(i);
		}
		
		Invariant inv = new Invariant();
		if (loc.powerSet.wait.equals(intersection(loc.powerSet.in, lowerbound()))
				&& (trace.get(0).timeop.op.equals(Operator.Op.ls) && loc.powerSet.less.equals(intersection(loc.powerSet.in, temp)) 
						|| (!trace.get(0).timeop.op.equals(Operator.Op.ls) && loc.powerSet.less.size()==0))
				&& (loc.powerSet.gteq.equals(tempGteq)))
		{
			Conjunction conj = new Conjunction();
			
			for (int i=0; i < trace.size(); i++)
			{
				BiImplication bi = new BiImplication();
				bi.lhs = new PEA.BoolValue(loc.powerSet.in.contains(i));
				Bool bool = new Bool();
				bool.lhs = trace.get(i).inv.clone();
				bool.op = new BoolOp(BoolOp.Op.and);
				bool.rhs = new PEA.BoolValue(i==0 || canSeep(loc, i));
				bi.rhs = bool; 
				conj.conjunction.add(bi);
			}
			
			inv.expression = conj;
		}
		else
		{
			inv.expression = new PEA.BoolValue(false);
		}		
			
		return inv;
	}
		
	public ArrayList<ArrayList<Clock>> powerclocks(ArrayList<Clock> inp)
	{
		ArrayList<ArrayList<Clock>> out = new ArrayList<ArrayList<Clock>>();
		
		for (int i = 0; i < Math.pow(2,inp.size()); i++)
		{
				ArrayList<Clock> obj = new ArrayList<Clock>();
				String binary = Integer.toBinaryString(i);
				for (int j = 0; j < binary.length(); j++)
					if (binary.charAt(j)=='1')
						obj.add(inp.get(j + inp.size()-binary.length()));
				out.add(obj);
		}		
		return out;
	}
	
	public ArrayList<ArrayList<Integer>> power(ArrayList<Integer> inp)
	{
		ArrayList<ArrayList<Integer>> out = new ArrayList<ArrayList<Integer>>();
		
		for (int i = 0; i < Math.pow(2,inp.size()); i++)
		{
				ArrayList<Integer> obj = new ArrayList<Integer>();
				String binary = Integer.toBinaryString(i);
				for (int j = 0; j < binary.length(); j++)
					if (binary.charAt(j)=='1')
						obj.add(inp.get(j + inp.size()-binary.length()));
				out.add(obj);
		}		
		return out;
	}
	
	public ArrayList<PowerSet> generate()
	{
		ArrayList<PowerSet> powerSet = new ArrayList<PowerSet>();
		
		ArrayList<Integer> in = new ArrayList<Integer>();
		for (int i = 0; i < trace.size(); i++)
			in.add(i);
		
		ArrayList<ArrayList<Integer>> ins = power(in);
		
		for (ArrayList<Integer> list : ins)
		{
			ArrayList<Integer> wait = new ArrayList<Integer>();
			ArrayList<Integer> gteq = new ArrayList<Integer>();
			for (Integer i : list)
				if (trace.get(i).bounded && !trace.get(i).upperbound)
				{
					wait.add(i);
					gteq.add(i);
				}

			ArrayList<Integer> less = new ArrayList<Integer>();
			for (Integer i : list)
				if (trace.get(i).upperbound
						&& (!minus(list,wait).contains(i-1)
								|| trace.get(i).entryEvents.disjunction.size()!= 0))
				{
					less.add(i);
				}
			
			ArrayList<ArrayList<Integer>> waits = power(wait);
			ArrayList<ArrayList<Integer>> gteqs = power(gteq);
			ArrayList<ArrayList<Integer>> lesses = power(less);
			
			for (ArrayList<Integer> list2 : waits)
				for (ArrayList<Integer> list3 : gteqs)
					for (ArrayList<Integer> list4 : lesses)
					{
						PowerSet ps = new PowerSet();
						ps.gteq = list3;
						ps.in = list;
						ps.less = list4;
						ps.wait = list2;
						if (ps.isValid())
							powerSet.add(ps);
					}
		}
		return powerSet;
	}
	
	public Invariant getInvariant(Location loc) throws Exception
	{
		Invariant inv = new Invariant();
		Conjunction conj = new Conjunction();
		conj.conjunction.add(new PEA.BoolValue(true));
		for (int i : loc.powerSet.in)
			conj.conjunction.add(trace.get(i).inv.clone());
		for (int i = 0; i < trace.size(); i++)
			if (!loc.powerSet.in.contains(i) && canSeep(loc, i))
			{
				Bool b = (Bool)trace.get(i).inv.clone();
				if (b.unary == null)
					b.unary = new PEA.Unary();
				else
					b.unary = null;
				conj.conjunction.add(b);
			}
		conj.simplify();
		inv.expression = conj;
		return inv;
	}
	
	public Invariant getClockInvariant(Location loc)
	{
		Invariant inv = new Invariant();
		Conjunction conj = new Conjunction();
		
		for (int i : loc.powerSet.in)
			if (loc.powerSet.wait.contains(i) || (trace.get(i).upperbound && !canSeep(loc, i)))
			{
				ClockInv ci = new ClockInv();
				ci.c = trace.get(i).clock;
				ci.bound = trace.get(i).bound;
				ci.operator = new Operator(Operator.Op.lseq);
				conj.conjunction.add(ci);
			}
		if (conj.conjunction.size()==0)
			conj.conjunction.add(new PEA.BoolValue(true));
		inv.expression = conj;
		return inv;
	}
	
	public void removeExtra()
	{
		ArrayList<Location> temp = new ArrayList<Location>();
		temp = (ArrayList<Location>)locs.clone();
		while (temp.size() != 0)
		{
			for (int i = 0; i < temp.size(); i++)
			{
				if (temp.get(i).initial != null && !temp.get(i).reached)
				{
					temp.get(i).reached = true;
					for (Transition t : temp.get(i).outgoing)
					{
						temp.add(t.dest);
						t.dest.reached = true;
					}
					temp.remove(i);
					i--;
				}
				else if (temp.get(i).reached)
				{
					for (Transition t : temp.get(i).outgoing)
						if (!t.dest.reached)
						{
							temp.add(t.dest);
							t.dest.reached = true;
						}
					temp.remove(i);
					i--;
				}
				else
				{
					temp.remove(i);
					i--;
				}
			}
		}
	}
	
	public String createAutomaton()throws Exception
	{
		fillInTrace();
//		String string = "";
//		
//		int index = 0;
//		int noPhases = chop.size();
//		
		//initialize
		for (PowerSet ps : generate())
		{
			Location loc = new Location();
			loc.powerSet = ps;
			locs.add(loc);
		}
		
		ArrayList<ArrayList<Clock>> clocklist = new ArrayList<ArrayList<Clock>>();
		clocklist = powerclocks(clocks);
		
		for (Location loc1 : locs)
		{
			loc1.invariant = getInvariant(loc1);
			loc1.clockInv = getClockInvariant(loc1);
			
			Invariant initial = init(loc1);
			initial.simplify();
			if (!initial.evaluated() || initial.evaluation)
				loc1.initial = initial;		
		}
		
		for (Location loc1 : locs)
			for (Location loc2 : locs)
				for (ArrayList<Clock> clks: clocklist)
			{
				Invariant guard = guard(loc2, clks, loc1);
				guard.simplify();
				if (!guard.evaluated() || (guard.evaluated() && guard.evaluation))	
				{
					Resets r = new Resets();
					r.clocks = clks;
					Transition t = new Transition();
					t.guard = guard; 
					t.resets = r;
					t.source = loc2;
					t.dest = loc1;
					loc1.incoming.add(t);
					loc2.outgoing.add(t);
				}
			}
		removeExtra();
		outputDiagram();
		return toAutomatonString();
	}

	public String toAutomatonString()
	{
		String string = "";
		for (Location loc : locs)
			if (loc.reached)
				string += loc.toAutomatonString() + "\r\n\r\n";
		return string;
	}

	public void outputDiagram()//String outputDir) 
	{		
		StringBuilder sb = new StringBuilder();

		sb.append("digraph _logic_PEA {"+
			"\r\nrankdir=LR;");// +
			//"\r\nsize=\""+(8+states.all.size())+","+(5+states.all.size())+"\"");

		sb.append("\r\nnode [shape = circle];");
		for (Location l : locs)
			if (l.reached)
				sb.append(" \"" + l.toNode() + "\"");
		sb.append(";");
		
		sb.append("\r\nnode [shape = point]; _s_s;");
			
		for (Location l : locs)
			if (l.reached)
			{
				if (l.initial != null)
					sb.append("\r\n_s_s -> \"" + l.toNode() + "\" [ label = \"" + l.initial + "\"];");
				for (Transition t: l.incoming)
					if (t.source.reached)
				{
					sb.append("\r\n\""+t.source.toNode() + "\" -> \"" + t.dest.toNode());
					sb.append("\" [ label = \""+ t.guard);
					String resets = t.resets.toString();
					if (resets.length()>0)
						sb.append("," + resets);
					sb.append("\"];");
				}
			}
		
		sb.append("\r\n}");
		
		try{
			PrintWriter pw1 = new PrintWriter("_diag_PEA.txt");
			pw1.write(sb.toString());
			pw1.close();
			Runtime.getRuntime().exec("\"C:\\Program Files\\Graphviz2.16\\bin\\dot.exe\"  -Tgif -o\"_logic_PEA_diag.gif\" -Kdot \"_diag_PEA.txt\"");
			
		}
		catch(Exception ex)
		{
			System.out.println("Diagram was not successfully generated! " +
					"Make sure Graphviz is installed in the default location!" +
					"...or else provide a \"-g\" commandline argument");
		}
	}	
	
	public String toLarva()throws Exception
	{
		StringBuilder sb = new StringBuilder();
		sb.append("IMPORTS {import main.*;}" +
				"\r\nGLOBAL {" +
		
		"\r\nVARIABLES {");
		for (Clock c : clocks)
			sb.append("\r\nClock " + c + ";");
		ArrayList<String> variables = new ArrayList<String>();
		for (Location l : locs)
			for (String v : l.getVariables())
				if (!variables.contains(v))
				{
					variables.add(v);
				//	sb.append("\r\nboolean " + v + ";");
				}
		sb.append("\r\n}" +
		
		"\r\nEVENTS {");
		
		String allEvents = "all(";
		String allEvents2 = ") = {";
		
//		sb.append("\r\nchangeOfAlarm(boolean Alarm ,boolean D) = {ALARM *.on()} where { D = DH2O.status; Alarm = ALARM.status; }");
//		sb.append("\r\nchangeOfD(boolean D, boolean Alarm) = {DH2O *.on()} where { D = DH2O.status; Alarm = ALARM.status; }");
		
		sb.append("\r\nchangeOfA(boolean A ,boolean B) = {A *.on()} where { B = B.status; A = A.status; }");
		sb.append("\r\nchangeOfB(boolean B, boolean A) = {B *.on()} where { B = B.status; A = A.status; }");
		
		
		for (String s : variables)
		{
//			sb.append("\r\nchangeOf" + s + "(boolean "+s+") = {***.***()} where { "+s+" = true; }");
			allEvents += s + ",";
			allEvents2 += "changeOf" + s + "|"; 
		}
		if (variables.size() > 0)
		{
			allEvents = allEvents.substring(0, allEvents.length()-1);
			allEvents2 = allEvents2.substring(0, allEvents2.length()-1);
		}
		
		sb.append("\r\ninitialize() = {Main *.initialize()}");
		
		
		ArrayList<String> clockevents = new ArrayList<String>();
		for (Location l : locs)
		{
			for (ClockInv ci : l.getClocks())
			{
				String num = String.valueOf(((Double)ci.bound/1000));
				String name = ci.c + "AT" + num.replace('.', '_');
				String ev = "\r\n" + name + "() = {" + ci.c + "@" + num + "}";
				if (!clockevents.contains(ev))
				{
					sb.append(ev);
					clockevents.add(ev);
					allEvents2 += "|" + name;
				}
			}
//			l.getEvents();
//			if (l.eventName.length() > 0)
//				sb.append("\r\n" + l.eventName + "() = {" + l.events + "}");
		}
		

		sb.append("\r\n" + allEvents + allEvents2 + "}");
		
		
		sb.append("\r\n}");
		sb.append("\r\nPROPERTY ceform {" +
				"\r\nSTATES {" +
				"\r\nBAD { unhandled");
		
		for (Location l : locs)
			if (l.reached && l.powerSet.in.contains(trace.size()-1)
					&& !l.powerSet.wait.contains(trace.size()-1)
					 && !l.powerSet.less.contains(trace.size()-1))
				sb.append("\r\n " + l.powerSet.toLarva());
				
		sb.append(" }" +
			"\r\nNORMAL {");
		
		for (Location l : locs)
			if (l.reached && (!l.powerSet.in.contains(trace.size()-1)
					|| l.powerSet.wait.contains(trace.size()-1)
					 || l.powerSet.less.contains(trace.size()-1)))
				sb.append("\r\n " + l.powerSet.toLarva());
		
		sb.append("\r\n}" +
				"\r\nSTARTING { start }" +
				"\r\n}" +
				"\r\nTRANSITIONS {");
				
		for (Location l : locs)
		if (l.reached)
		{
			if (l.initial != null)
			{
				sb.append("\r\nstart -> " + l.powerSet.toLarva() + " [all\\");
				Conjunction conj = new Conjunction();
				conj.conjunction.add(l.initial);
				conj.conjunction.add(l.getCondition(null));
				conj.simplify();
				sb.append(conj.toLARVA());
			//	sb.append("\\System.out.println(\"D:\"+D+\" A:\" +Alarm);]");
				sb.append("]");
			}
			for (Transition t : l.outgoing)
			{
//				if (t.guard.evaluated() && t.guard.evaluation)
//				{
//					if (!t.source.equals(t.dest))
//						throw new Exception("I dont know how to handle a transition without events!!!" + t);
//				}
//				else
		//		if (t.getEvents().length() > 0)
				{
					sb.append("\r\n" + t.source.powerSet.toLarva() + " -> "
							+  t.dest.powerSet.toLarva() + " [all"// + t.getEvents()
							+ "\\" + t.getCondition().toLARVA() + "\\" + t.getResets());
					//sb.append("System.out.println(\"D:\"+D+\" A:\" +Alarm);");
//					if (l.powerSet.in.contains(trace.size()-1))
//						sb.append("System.out.println(\"THE PROPERTY BEING VERIFIED WAS VIOLATED!!!\");");
					sb.append("]");
				}
			}
	//		if (l.getEvents().length() > 0)
			{
				sb.append("\r\n" + l.powerSet.toLarva() + " -> unhandled [all"); //+ l.getEvents());
		//		sb.append("\\\\System.out.println(\"D:\"+D+\" A:\" +Alarm);");
				sb.append("]");
				
//						+ "\\!(" + l.getCondition() + ")\\System.out.println(\"BAD STATE REACHED!!!\");");
				//sb.append("]");
			}
		}
		
		
		
		sb.append("\r\n}" +
				"\r\n}" +
				"\r\n}%%GLOBAL");
		return sb.toString();
	}

	public boolean isSDTP()throws ParseException
	{
		boolean chopSDTP = true;
		
		for (CEform p : chop)
			if (!p.isSDTP())
			{
				chopSDTP = false;
				break;
			}
		
		boolean chopSUTP = true;
		
		for (CEform p : chop)
			if (!p.isSUTP())
			{
				chopSUTP = false;
				break;
			}
		
		if (unary != null)
			return chopSUTP;
		else
			return chopSDTP;
	}
	
	public boolean isSUTP()throws ParseException
	{
		boolean chopSUTP = true;
		
		for (CEform p : chop)
			if (!p.isSUTP())
			{
				chopSUTP = false;
				break;
			}
		
		boolean chopSDTP = true;
		
		for (CEform p : chop)
			if (!p.isSDTP())
			{
				chopSDTP = false;
				break;
			}
		
		if (unary != null)
			return chopSDTP;
		else
			return chopSUTP;
	}
}
