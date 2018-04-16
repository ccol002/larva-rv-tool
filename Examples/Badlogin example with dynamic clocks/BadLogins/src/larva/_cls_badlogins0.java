package larva;


import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_badlogins0 implements _callable{

public static PrintWriter pw; 
public static _cls_badlogins0 root;

public static LinkedHashMap<_cls_badlogins0,_cls_badlogins0> _cls_badlogins0_instances = new LinkedHashMap<_cls_badlogins0,_cls_badlogins0>();
static{
try{
RunningClock.start();
pw = new PrintWriter("/Volumes/ACMSAC 2017/BadLogins/src//output_badlogins.txt");

root = new _cls_badlogins0();
_cls_badlogins0_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_badlogins0 parent; //to remain null - this class does not have a parent!
int no_automata = 1;
public Clock c = new Clock(this,"c");
 public int count =0 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_badlogins0() {
}

public void initialisation() {
   c.reset();
}

public static _cls_badlogins0 _get_cls_badlogins0_inst() { synchronized(_cls_badlogins0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_badlogins0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 1;
}

public void _call(String _info, int... _event){
synchronized(_cls_badlogins0_instances){
_performLogic_badlogins(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_badlogins0[] a = new _cls_badlogins0[1];
synchronized(_cls_badlogins0_instances){
a = _cls_badlogins0_instances.keySet().toArray(a);}
for (_cls_badlogins0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_badlogins0_instances){
_cls_badlogins0_instances.remove(this);}
synchronized(c){
c.off();
c._inst = null;
c = null;}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_badlogins = 1;

public void _performLogic_badlogins(String _info, int... _event) {

_cls_badlogins0.pw.println("[badlogins]AUTOMATON::> badlogins("+") STATE::>"+ _string_badlogins(_state_id_badlogins, 0));
_cls_badlogins0.pw.flush();

if (0==1){}
else if (_state_id_badlogins==1){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*cAT5*/))){
		_cls_badlogins0.pw .println ("Seems to work :)");

		_state_id_badlogins = 1;//moving to state starting
		_goto_badlogins(_info);
		}
		else if ((_occurredEvent(_event,0/*badlogin*/))){
		c .registerDynamically (1000l ,System .currentTimeMillis ());

		_state_id_badlogins = 1;//moving to state starting
		_goto_badlogins(_info);
		}
}
}

public void _goto_badlogins(String _info){
 String state_format = _string_badlogins(_state_id_badlogins, 1);
_cls_badlogins0.pw.println("[badlogins]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + state_format);
_cls_badlogins0.pw.flush();
}

public String _string_badlogins(int _state_id, int _mode){
switch(_state_id){
case 1: if (_mode == 0) return "starting"; else return "starting";
case 0: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionbadlogins().toString()+" ";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}