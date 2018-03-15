package larva;


import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_Matthew0 implements _callable{

public static PrintWriter pw; 
public static _cls_Matthew0 root;

public static HashMap<_cls_Matthew0,_cls_Matthew0> _cls_Matthew0_instances = new HashMap<_cls_Matthew0,_cls_Matthew0>();
static{
try{
pw = new PrintWriter("C:\\Documents and Settings\\Colombo.LAPTOP\\Desktop\\projects\\packetMonitor\\src\\\\output_Matthew.txt");

root = new _cls_Matthew0();
_cls_Matthew0_instances.put(root, root);
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_Matthew0 parent; //to remain null - this class does not have a parent!
public static String username;
int no_automata = 1;
 public int counter =0 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_Matthew0() {
}

public static _cls_Matthew0 _get_cls_Matthew0_inst() { synchronized(_cls_Matthew0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_Matthew0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_Matthew0_instances){
_performLogic_test1(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_Matthew0[] a = new _cls_Matthew0[1];
synchronized(_cls_Matthew0_instances){
a = _cls_Matthew0_instances.keySet().toArray(a);}
for (_cls_Matthew0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_Matthew0_instances){
_cls_Matthew0_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_test1 = 1;

public void _performLogic_test1(String _info, int... _event) {

_cls_Matthew0.pw.println("AUTOMATON::> test1("+") STATE::>"+ _string_test1(_state_id_test1, 0));
_cls_Matthew0.pw.flush();

if (0==1){}
else if (_state_id_test1==1){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*loginAttempt*/))){
		_cls_Matthew0.pw .println ("Login Attempted");

		_state_id_test1 = 0;//moving to state logged_in
		_goto_test1(_info);
		}
}
}

public void _goto_test1(String _info){
_cls_Matthew0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_test1(_state_id_test1, 1));
_cls_Matthew0.pw.flush();
}

public String _string_test1(int _state_id, int _mode){
switch(_state_id){
case 0: if (_mode == 0) return "logged_in"; else return "(((SYSTEM REACHED AN ACCEPTED STATE)))  logged_in";
case 1: if (_mode == 0) return "logged_out"; else return "logged_out";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}