package larva;


import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_clocks0 implements _callable{

public static PrintWriter pw; 
public static _cls_clocks0 root;
public static Channel d = new Channel();

public static LinkedHashMap<_cls_clocks0,_cls_clocks0> _cls_clocks0_instances = new LinkedHashMap<_cls_clocks0,_cls_clocks0>();
static{
try{
RunningClock.start();
pw = new PrintWriter("C:\\Users\\University User\\Desktop\\projects\\BadLogin\\src\\\\output_clocks.txt");

root = new _cls_clocks0();
_cls_clocks0_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_clocks0 parent; //to remain null - this class does not have a parent!
public static String from;
int no_automata = 2;
public Clock c = new Clock(this,"c");
 public int count =0 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_clocks0() {
c.register(1000l);
}

public void initialisation() {
   c.reset();
}

public static _cls_clocks0 _get_cls_clocks0_inst() { synchronized(_cls_clocks0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_clocks0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_clocks0_instances){
_performLogic_test1(_info, _event);
_performLogic_test2(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_clocks0[] a = new _cls_clocks0[1];
synchronized(_cls_clocks0_instances){
a = _cls_clocks0_instances.keySet().toArray(a);}
for (_cls_clocks0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_clocks0_instances){
_cls_clocks0_instances.remove(this);}
synchronized(c){
c.off();
c._inst = null;
c = null;}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_test1 = 1;

public void _performLogic_test1(String _info, int... _event) {

_cls_clocks0.pw.println("[test1]AUTOMATON::> test1("+") STATE::>"+ _string_test1(_state_id_test1, 0));
_cls_clocks0.pw.flush();

if (0==1){}
else if (_state_id_test1==1){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*clockC5*/)) && (count >5 )){
		d .send ("test1");
_cls_clocks0.pw .println ("Reached Normal State!! 1");
_cls_clocks0.pw .println (System .currentTimeMillis ());

		_state_id_test1 = 0;//moving to state normal
		_goto_test1(_info);
		}
		else if ((_occurredEvent(_event,0/*clockC5*/))){
		c .reset ();
count ++;
_cls_clocks0.pw .println (System .currentTimeMillis ());

		_state_id_test1 = 1;//moving to state starting
		_goto_test1(_info);
		}
}
}

public void _goto_test1(String _info){
_cls_clocks0.pw.println("[test1]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_test1(_state_id_test1, 1));
_cls_clocks0.pw.flush();
}

public String _string_test1(int _state_id, int _mode){
switch(_state_id){
case 0: if (_mode == 0) return "normal"; else return "normal";
case 1: if (_mode == 0) return "starting"; else return "starting";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_test2 = 4;

public void _performLogic_test2(String _info, int... _event) {

_cls_clocks0.pw.println("[test2]AUTOMATON::> test2("+") STATE::>"+ _string_test2(_state_id_test2, 0));
_cls_clocks0.pw.flush();

if (0==1){}
else if (_state_id_test2==3){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*clockC5*/)) && (count >5 )){
		_cls_clocks0.pw .println ("Reached Normal State!! 2");
_cls_clocks0.pw .println (System .currentTimeMillis ());

		_state_id_test2 = 2;//moving to state normal
		_goto_test2(_info);
		}
		else if ((_occurredEvent(_event,0/*clockC5*/))){
		c .reset ();
count ++;
_cls_clocks0.pw .println (System .currentTimeMillis ());

		_state_id_test2 = 3;//moving to state starting
		_goto_test2(_info);
		}
}
else if (_state_id_test2==4){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*channeld*/)) && (from .equals ("test1"))){
		c .reset ();
_cls_clocks0.pw .println (System .currentTimeMillis ());

		_state_id_test2 = 3;//moving to state starting
		_goto_test2(_info);
		}
}
}

public void _goto_test2(String _info){
_cls_clocks0.pw.println("[test2]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_test2(_state_id_test2, 1));
_cls_clocks0.pw.flush();
}

public String _string_test2(int _state_id, int _mode){
switch(_state_id){
case 2: if (_mode == 0) return "normal"; else return "normal";
case 3: if (_mode == 0) return "starting"; else return "starting";
case 4: if (_mode == 0) return "beforestarting"; else return "beforestarting";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}