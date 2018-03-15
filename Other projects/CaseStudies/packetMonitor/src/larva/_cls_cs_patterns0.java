package larva;


import sniffer.*;
import larva.*;
import java.net.*;
import java.util.HashMap;

import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_cs_patterns0 implements _callable{

public static PrintWriter pw; 
public static _cls_cs_patterns0 root;
public static Channel checkchannel = new Channel();
public static Channel attackchannel = new Channel();

public static HashMap<_cls_cs_patterns0,_cls_cs_patterns0> _cls_cs_patterns0_instances = new HashMap<_cls_cs_patterns0,_cls_cs_patterns0>();
static{
try{
pw = new PrintWriter("C:\\Users\\University User\\Desktop\\projects\\packetMonitor\\src\\\\output_cs_patterns.txt");

root = new _cls_cs_patterns0();
_cls_cs_patterns0_instances.put(root, root);
root.clock.restart();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_cs_patterns0 parent; //to remain null - this class does not have a parent!
int no_automata = 1;
 public int open =0 ;
public Clock clock = new Clock(this);
 public int ips =0 ;
 public int pending =0 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_cs_patterns0() {
clock.register(4000);
}

public static _cls_cs_patterns0 _get_cls_cs_patterns0_inst() { synchronized(_cls_cs_patterns0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_cs_patterns0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_cs_patterns0_instances){
_performLogic_check(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){

_cls_cs_patterns1[] a = new _cls_cs_patterns1[1];
synchronized(_cls_cs_patterns1._cls_cs_patterns1_instances){
a = _cls_cs_patterns1._cls_cs_patterns1_instances.keySet().toArray(a);}
for (_cls_cs_patterns1 _inst : a)
if (_inst != null){
_inst._call(_info, _event); 
_inst._call_all_filtered(_info, _event);
}
}

public static void _call_all(String _info, int... _event){

_cls_cs_patterns0[] a = new _cls_cs_patterns0[1];
synchronized(_cls_cs_patterns0_instances){
a = _cls_cs_patterns0_instances.keySet().toArray(a);}
for (_cls_cs_patterns0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_cs_patterns0_instances){
_cls_cs_patterns0_instances.remove(this);}
synchronized(clock){
clock.off();
clock._inst = null;
clock = null;}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_check = 2;

public void _performLogic_check(String _info, int... _event) {

_cls_cs_patterns0.pw.println("AUTOMATON::> check("+") STATE::>"+ _string_check(_state_id_check, 0));
_cls_cs_patterns0.pw.flush();

if (0==1){}
else if (_state_id_check==2){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*newRequest*/)) && (pending >open +10 )){
		clock .reset ();
_cls_cs_patterns0.pw .println ("pending: "+pending +" open: "+open );

		_state_id_check = 0;//moving to state needcheck
		_goto_check(_info);
		}
		else if ((_occurredEvent(_event,0/*newRequest*/))){
		_cls_cs_patterns0.pw .println ("pending: "+pending +" open: "+open );

		_state_id_check = 2;//moving to state start
		_goto_check(_info);
		}
}
else if (_state_id_check==0){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*clockAT5*/)) && (pending >open +10 )){
		_cls_cs_patterns0.pw .println ("pending: "+pending +" open: "+open );
_cls_cs_patterns0.pw .println ("Possible Attack!");
attackchannel .send ();

		_state_id_check = 2;//moving to state start
		_goto_check(_info);
		}
		else if ((_occurredEvent(_event,2/*clockAT5*/))){
		
		_state_id_check = 2;//moving to state start
		_goto_check(_info);
		}
}
}

public void _goto_check(String _info){
_cls_cs_patterns0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_check(_state_id_check, 1));
_cls_cs_patterns0.pw.flush();
}

public String _string_check(int _state_id, int _mode){
switch(_state_id){
case 1: if (_mode == 0) return "docheck"; else return "docheck";
case 2: if (_mode == 0) return "start"; else return "start";
case 0: if (_mode == 0) return "needcheck"; else return "needcheck";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}