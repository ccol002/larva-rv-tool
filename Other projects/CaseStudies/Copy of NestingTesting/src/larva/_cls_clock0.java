package larva;


import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_clock0 {

public static PrintWriter pw; 
public static Channel d;
public static Clock c;

static{
try{
pw = new PrintWriter("C:\\Documents and Settings\\Colombo.LAPTOP\\Desktop\\projects\\NestingTesting\\src\\larva\\\\output_clock.txt");
d =new Channel ();
c =new Clock ();
c.register(3000);
c.reset();
}catch(Exception ex)
{ex.printStackTrace();}
}

public static HashMap<_cls_clock0,_cls_clock0> _cls_clock0_instances = new HashMap<_cls_clock0,_cls_clock0>();

_cls_clock0 parent; //to remain null - this class does not have a parent!
public String from;

 public int count =0 ;
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_clock0() {
}

public static _cls_clock0 _get_cls_clock0_inst() {
_cls_clock0 _inst = new _cls_clock0();
if (_cls_clock0_instances.containsKey(_inst))
{
_cls_clock0 tmp = _cls_clock0_instances.get(_inst);
 return _cls_clock0_instances.get(_inst);
}
else
{
 _cls_clock0_instances.put(_inst,_inst);
 return _inst;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_clock0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
_performLogic_test2(_info, _event);
_performLogic_test1(_info, _event);
}

int _state_id_test2 = 4;

public void _performLogic_test2(String _info, int... _event) {

_cls_clock0.pw.println("AUTOMATON::> test2("+") STATE::>"+ _string_test2(_state_id_test2, 0));
_cls_clock0.pw.flush();

if (0==1){}
else if (_state_id_test2==3){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*clockC5*/)) && (count >5 )){
_cls_clock0.pw.println ("Reached Normal State!! 2");

		_state_id_test2 = 2;//moving to state normal
		_goto_test2(_info);
		}
		else if ((_occurredEvent(_event,0/*clockC5*/))){
		c.reset ();
count ++;

		_state_id_test2 = 3;//moving to state starting
		_goto_test2(_info);
		}
}
else if (_state_id_test2==4){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*channeld*/)) && (from.equals ("test1"))){
		c.reset();
		_state_id_test2 = 3;//moving to state starting
		_goto_test2(_info);
		}
}
}

public void _goto_test2(String _info){
_cls_clock0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_test2(_state_id_test2, 1));
_cls_clock0.pw.flush();
}

public String _string_test2(int _state_id, int _mode){
switch(_state_id){
case 3: if (_mode == 0) return "starting"; else return "starting";
case 2: if (_mode == 0) return "normal"; else return "normal";
case 4: if (_mode == 0) return "beforestarting"; else return "beforestarting";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_test1 = 1;

public void _performLogic_test1(String _info, int... _event) {

_cls_clock0.pw.println("AUTOMATON::> test1("+") STATE::>"+ _string_test1(_state_id_test1, 0));
_cls_clock0.pw.flush();

if (0==1){}
else if (_state_id_test1==1){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*clockC5*/)) && (count >5 )){
		d.send ("test1");
_cls_clock0.pw.println ("Reached Normal State!! 1");

		_state_id_test1 = 0;//moving to state normal
		_goto_test1(_info);
		}
		else if ((_occurredEvent(_event,0/*clockC5*/))){
		c.reset ();
count ++;

		_state_id_test1 = 1;//moving to state starting
		_goto_test1(_info);
		}
}
}

public void _goto_test1(String _info){
_cls_clock0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_test1(_state_id_test1, 1));
_cls_clock0.pw.flush();
}

public String _string_test1(int _state_id, int _mode){
switch(_state_id){
case 1: if (_mode == 0) return "starting"; else return "starting";
case 0: if (_mode == 0) return "normal"; else return "normal";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}

public static void initialize() {}
}