package larva;



import benchmark.*;


import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_benchmark1 implements _callable{

public static LinkedHashMap<_cls_benchmark1,_cls_benchmark1> _cls_benchmark1_instances = new LinkedHashMap<_cls_benchmark1,_cls_benchmark1>();

_cls_benchmark0 parent;
public static User u1;
public User u;
int no_automata = 1;
 public int count =0 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_benchmark1( User u) {
parent = _cls_benchmark0._get_cls_benchmark0_inst();
this.u = u;
}

public void initialisation() {
}

public static _cls_benchmark1 _get_cls_benchmark1_inst( User u) { synchronized(_cls_benchmark1_instances){
_cls_benchmark1 _inst = new _cls_benchmark1( u);
if (_cls_benchmark1_instances.containsKey(_inst))
{
_cls_benchmark1 tmp = _cls_benchmark1_instances.get(_inst);
 return _cls_benchmark1_instances.get(_inst);
}
else
{
 _inst.initialisation();
 _cls_benchmark1_instances.put(_inst,_inst);
 return _inst;
}
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_benchmark1)
 && (u == null || u.equals(((_cls_benchmark1)o).u))
 && (parent == null || parent.equals(((_cls_benchmark1)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_benchmark1_instances){
_performLogic_lessThan5(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){

_cls_benchmark2[] a2 = new _cls_benchmark2[1];
synchronized(_cls_benchmark2._cls_benchmark2_instances){
a2 = _cls_benchmark2._cls_benchmark2_instances.keySet().toArray(a2);}
for (_cls_benchmark2 _inst : a2)
if (_inst != null
 && (u == null || u.equals(_inst.parent.u))){
_inst._call(_info, _event); 
_inst._call_all_filtered(_info, _event);
}
}

public static void _call_all(String _info, int... _event){

_cls_benchmark1[] a = new _cls_benchmark1[1];
synchronized(_cls_benchmark1_instances){
a = _cls_benchmark1_instances.keySet().toArray(a);}
for (_cls_benchmark1 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_benchmark1_instances){
_cls_benchmark1_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_lessThan5 = 1;

public void _performLogic_lessThan5(String _info, int... _event) {

_cls_benchmark0.pw.println("[lessThan5]AUTOMATON::> lessThan5("+u + " " + ") STATE::>"+ _string_lessThan5(_state_id_lessThan5, 0));
_cls_benchmark0.pw.flush();

if (0==1){}
else if (_state_id_lessThan5==1){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*addTransaction*/)) && (count >5 )){
		_cls_benchmark0.pw .println ("More than 5 transactions added to User: "+u );

		_state_id_lessThan5 = 0;//moving to state bad
		_goto_lessThan5(_info);
		}
		else if ((_occurredEvent(_event,0/*addTransaction*/))){
		count ++;

		_state_id_lessThan5 = 1;//moving to state starting
		_goto_lessThan5(_info);
		}
}
}

public void _goto_lessThan5(String _info){
_cls_benchmark0.pw.println("[lessThan5]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_lessThan5(_state_id_lessThan5, 1));
_cls_benchmark0.pw.flush();
}

public String _string_lessThan5(int _state_id, int _mode){
switch(_state_id){
case 1: if (_mode == 0) return "starting"; else return "starting";
case 0: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionbenchmark().toString()+" ";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}