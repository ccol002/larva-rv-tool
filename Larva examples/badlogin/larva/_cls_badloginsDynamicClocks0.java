package larva;


import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_badloginsDynamicClocks0 implements _callable{

public static PrintWriter pw; 
public static _cls_badloginsDynamicClocks0 root;

public static LinkedHashMap<_cls_badloginsDynamicClocks0,_cls_badloginsDynamicClocks0> _cls_badloginsDynamicClocks0_instances;

_cls_badloginsDynamicClocks0 parent; //to remain null - this class does not have a parent!
int no_automata;

int _state_id_badlogins;
public Clock c;
 public int count =0 ;

public static void initialize(){
//note that this initialisation does not include user-defined declarations in the Variables section


_cls_badloginsDynamicClocks0_instances = new LinkedHashMap<_cls_badloginsDynamicClocks0,_cls_badloginsDynamicClocks0>();
try{
root = new _cls_badloginsDynamicClocks0();
_cls_badloginsDynamicClocks0_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_badloginsDynamicClocks0() {
c = new Clock(this,"c");
}

public void initialisation() {
no_automata = 1;
//initialise automata
_state_id_badlogins = 1;
   c.reset();
}

public static _cls_badloginsDynamicClocks0 _get_cls_badloginsDynamicClocks0_inst() { synchronized(_cls_badloginsDynamicClocks0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_badloginsDynamicClocks0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 1;
}

public void _call(String _info, int... _event){
synchronized(_cls_badloginsDynamicClocks0_instances){
_performLogic_badlogins(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_badloginsDynamicClocks0[] a = new _cls_badloginsDynamicClocks0[1];
synchronized(_cls_badloginsDynamicClocks0_instances){
a = _cls_badloginsDynamicClocks0_instances.keySet().toArray(a);}
for (_cls_badloginsDynamicClocks0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_badloginsDynamicClocks0_instances){
_cls_badloginsDynamicClocks0_instances.remove(this);}
synchronized(c){
c.off();
c._inst = null;
c = null;}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}


public void _performLogic_badlogins(String _info, int... _event) {

if (0==1){}
else if (_state_id_badlogins==1){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*cAT5*/))){
		System .out .println ("Seems to work :)");
c .reset ();

		_state_id_badlogins = 1;//moving to state starting
		_goto_badlogins(_info);
		}
		else if ((_occurredEvent(_event,0/*badlogin*/))){
		c .registerDynamically (1000l );

		_state_id_badlogins = 1;//moving to state starting
		_goto_badlogins(_info);
		}
}
}

public void _goto_badlogins(String _info){
 String state_format = _string_badlogins(_state_id_badlogins, 1);
 if (state_format.startsWith("!!!SYSTEM REACHED BAD STATE!!!")) {
   System.out.println("[badlogins]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + state_format);
}
}

public String _string_badlogins(int _state_id, int _mode){
switch(_state_id){
case 0: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionbadloginsDynamicClocks().toString()+" ";
case 1: if (_mode == 0) return "starting"; else return "starting";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}