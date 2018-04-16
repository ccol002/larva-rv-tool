package larva;


import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_clocktest0 implements _callable{

public static PrintWriter pw; 
public static _cls_clocktest0 root;

public static LinkedHashMap<_cls_clocktest0,_cls_clocktest0> _cls_clocktest0_instances = new LinkedHashMap<_cls_clocktest0,_cls_clocktest0>();
static{
try{
RunningClock.start();
pw = new PrintWriter("C:\\Documents and Settings\\Colombo.LAPTOP\\Desktop\\projects\\BadLogin\\src\\\\output_clocktest.txt");

root = new _cls_clocktest0();
_cls_clocktest0_instances.put(root, root);
root.b.reset();
root.c.reset();
root.d.reset();
root.e.reset();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_clocktest0 parent; //to remain null - this class does not have a parent!
int no_automata = 4;
public Clock b = new Clock(this,"b");
public Clock c = new Clock(this,"c");
public Clock d = new Clock(this,"d");
public Clock e = new Clock(this,"e");

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_clocktest0() {
b.register(500l);
c.register(500l);
d.register(500l);
e.register(5000l);
}

public static _cls_clocktest0 _get_cls_clocktest0_inst() { synchronized(_cls_clocktest0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_clocktest0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_clocktest0_instances){
_performLogic_test0(_info, _event);
_performLogic_test1(_info, _event);
_performLogic_test2(_info, _event);
_performLogic_test3(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_clocktest0[] a = new _cls_clocktest0[1];
synchronized(_cls_clocktest0_instances){
a = _cls_clocktest0_instances.keySet().toArray(a);}
for (_cls_clocktest0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_clocktest0_instances){
_cls_clocktest0_instances.remove(this);}
synchronized(b){
b.off();
b._inst = null;
b = null;}
synchronized(c){
c.off();
c._inst = null;
c = null;}
synchronized(d){
d.off();
d._inst = null;
d = null;}
synchronized(e){
e.off();
e._inst = null;
e = null;}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_test0 = 0;

public void _performLogic_test0(String _info, int... _event) {

_cls_clocktest0.pw.println("AUTOMATON::> test0("+") STATE::>"+ _string_test0(_state_id_test0, 0));
_cls_clocktest0.pw.flush();

if (0==1){}
else if (_state_id_test0==0){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*b_at_1*/))){
		b .reset ();
_cls_clocktest0.pw .println ("clock b "+System .currentTimeMillis ());

		_state_id_test0 = 0;//moving to state start
		_goto_test0(_info);
		}
}
}

public void _goto_test0(String _info){
_cls_clocktest0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_test0(_state_id_test0, 1));
_cls_clocktest0.pw.flush();
}

public String _string_test0(int _state_id, int _mode){
switch(_state_id){
case 0: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_test1 = 1;

public void _performLogic_test1(String _info, int... _event) {

_cls_clocktest0.pw.println("AUTOMATON::> test1("+") STATE::>"+ _string_test1(_state_id_test1, 0));
_cls_clocktest0.pw.flush();

if (0==1){}
else if (_state_id_test1==1){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*c_at_1*/))){
		c .reset ();
_cls_clocktest0.pw .println ("clock c "+System .currentTimeMillis ());

		_state_id_test1 = 1;//moving to state start
		_goto_test1(_info);
		}
}
}

public void _goto_test1(String _info){
_cls_clocktest0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_test1(_state_id_test1, 1));
_cls_clocktest0.pw.flush();
}

public String _string_test1(int _state_id, int _mode){
switch(_state_id){
case 1: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_test2 = 2;

public void _performLogic_test2(String _info, int... _event) {

_cls_clocktest0.pw.println("AUTOMATON::> test2("+") STATE::>"+ _string_test2(_state_id_test2, 0));
_cls_clocktest0.pw.flush();

if (0==1){}
else if (_state_id_test2==2){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*d_at_1*/))){
		d .reset ();
_cls_clocktest0.pw .println ("clock d "+System .currentTimeMillis ());

		_state_id_test2 = 2;//moving to state start
		_goto_test2(_info);
		}
}
}

public void _goto_test2(String _info){
_cls_clocktest0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_test2(_state_id_test2, 1));
_cls_clocktest0.pw.flush();
}

public String _string_test2(int _state_id, int _mode){
switch(_state_id){
case 2: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_test3 = 3;

public void _performLogic_test3(String _info, int... _event) {

_cls_clocktest0.pw.println("AUTOMATON::> test3("+") STATE::>"+ _string_test3(_state_id_test3, 0));
_cls_clocktest0.pw.flush();

if (0==1){}
else if (_state_id_test3==3){
		if (1==0){}
		else if ((_occurredEvent(_event,6/*e_at_1*/))){
		e .reset ();
_cls_clocktest0.pw .println ("clock e "+System .currentTimeMillis ());

		_state_id_test3 = 3;//moving to state start
		_goto_test3(_info);
		}
}
}

public void _goto_test3(String _info){
_cls_clocktest0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_test3(_state_id_test3, 1));
_cls_clocktest0.pw.flush();
}

public String _string_test3(int _state_id, int _mode){
switch(_state_id){
case 3: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}