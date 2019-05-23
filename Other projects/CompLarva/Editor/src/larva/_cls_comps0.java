package larva;


import main.*;

import java.util.LinkedHashMap;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Stack;

public class _cls_comps0 implements _callable{

public static PrintWriter pw; 
public static _cls_comps0 root;
public static Channel c_undo_start = new Channel();
public static Channel c_undo_done = new Channel();

public static LinkedHashMap<_cls_comps0,_cls_comps0> _cls_comps0_instances = new LinkedHashMap<_cls_comps0,_cls_comps0>();
public static HashMap<Integer,String> s= new HashMap<Integer,String>();
public static HashMap<Integer,Editor> ed= new HashMap<Integer,Editor>();
public static Stack<Integer> _comps = new Stack<Integer>();
public static Integer sid = 0;
int no_automata = 1;
static{
try{
RunningClock.start();
pw = new PrintWriter("C:\\Users\\Christian\\Desktop\\projects\\Editor\\src//output_comps.txt");

root = new _cls_comps0();
_cls_comps0_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_comps0 parent; //to remain null - this class does not have a parent!

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_comps0() {
}

public void initialisation() {
}

public static _cls_comps0 _get_cls_comps0_inst() { synchronized(_cls_comps0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_comps0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_comps0_instances){
_performLogic_undo(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_comps0[] a = new _cls_comps0[1];
synchronized(_cls_comps0_instances){
a = _cls_comps0_instances.keySet().toArray(a);}
for (_cls_comps0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_comps0_instances){
_cls_comps0_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_undo = 1;

public void _performLogic_undo(String _info, int... _event) {

_cls_comps0.pw.println("[undo]AUTOMATON::> undo("+") STATE::>"+ _string_undo(_state_id_undo, 0));
_cls_comps0.pw.flush();

if (0==1){}
else if (_state_id_undo==1){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*insert*/))){
		_state_id_undo = 1;//moving to state S
_comps.push(0);
_comps.push(sid++);
		_goto_undo(_info);
		}
		else if ((_occurredEvent(_event,2/*delete*/))){
		_state_id_undo = 1;//moving to state S
_comps.push(1);
_comps.push(sid++);
		_goto_undo(_info);
		}
		else if ((_occurredEvent(_event,4/*save*/))){
		_state_id_undo = 1;//moving to state S
_comps.push(2);
_comps.push(sid++);
_comps.push(-1);
_comps.push(1);
		_goto_undo(_info);
		}
		else if ((_occurredEvent(_event,6/*undo*/))){
		_state_id_undo = 0;//moving to state E
_comps.push(3);
_comps.push(sid++);
 while (_comps.size()>0){
   Integer _dat = _comps.pop();
   Integer _act = _comps.pop();
   if (_act == -1){
    	_state_id_undo = _dat; //deviating
     break;
   }
   else if (_act == -2){
     //end of compensation for this scope reached
     break;
   } 
   switch (_act) {
 case 0:ed.get(_dat) .delete ();
break;
 case 1:ed.get(_dat) .insert (s.get(_dat) );
break;
 case 2:break;
 case 3:break;
 }}
 c_undo_done.send();
		_goto_undo(_info);
		}
}
}

public void _goto_undo(String _info){
_cls_comps0.pw.println("[undo]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_undo(_state_id_undo, 1));
_cls_comps0.pw.flush();
}

public String _string_undo(int _state_id, int _mode){
switch(_state_id){
case 0: if (_mode == 0) return "E"; else return "!!!SYSTEM REACHED BAD STATE!!! E "+new _BadStateExceptioncomps().toString()+" ";
case 1: if (_mode == 0) return "S"; else return "S";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}