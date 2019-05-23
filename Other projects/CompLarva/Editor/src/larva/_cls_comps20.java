package larva;


import main.*;

import java.util.LinkedHashMap;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Stack;

public class _cls_comps20 implements _callable{

public static PrintWriter pw; 
public static _cls_comps20 root;
public static Channel c_undoone_start = new Channel();
public static Channel c_undoone_done = new Channel();

public static LinkedHashMap<_cls_comps20,_cls_comps20> _cls_comps20_instances = new LinkedHashMap<_cls_comps20,_cls_comps20>();
public static HashMap<Integer,String> s= new HashMap<Integer,String>();
public static HashMap<Integer,Editor> ed= new HashMap<Integer,Editor>();
public static Stack<Integer> _comps = new Stack<Integer>();
public static Integer sid = 0;
int no_automata = 1;
static{
try{
RunningClock.start();
pw = new PrintWriter("C:\\Users\\Christian\\Desktop\\projects\\Editor\\src//output_comps2.txt");

root = new _cls_comps20();
_cls_comps20_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_comps20 parent; //to remain null - this class does not have a parent!

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_comps20() {
}

public void initialisation() {
}

public static _cls_comps20 _get_cls_comps20_inst() { synchronized(_cls_comps20_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_comps20))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_comps20_instances){
_performLogic_undoone(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_comps20[] a = new _cls_comps20[1];
synchronized(_cls_comps20_instances){
a = _cls_comps20_instances.keySet().toArray(a);}
for (_cls_comps20 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_comps20_instances){
_cls_comps20_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_undoone = 1;

public void _performLogic_undoone(String _info, int... _event) {

_cls_comps20.pw.println("[undoone]AUTOMATON::> undoone("+") STATE::>"+ _string_undoone(_state_id_undoone, 0));
_cls_comps20.pw.flush();

if (0==1){}
else if (_state_id_undoone==1){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*insert2*/))){
		_state_id_undoone = 1;//moving to state S
_comps.push(0);
_comps.push(sid++);
		_goto_undoone(_info);
		}
		else if ((_occurredEvent(_event,6/*delete2*/))){
		_state_id_undoone = 1;//moving to state S
_comps.push(1);
_comps.push(sid++);
		_goto_undoone(_info);
		}
		else if ((_occurredEvent(_event,0/*insert*/))){
		_state_id_undoone = 1;//moving to state S
_comps.push(2);
_comps.push(sid++);
_comps.push(-1);
_comps.push(1);
		_goto_undoone(_info);
		}
		else if ((_occurredEvent(_event,2/*delete*/))){
		_state_id_undoone = 1;//moving to state S
_comps.push(3);
_comps.push(sid++);
_comps.push(-1);
_comps.push(1);
		_goto_undoone(_info);
		}
		else if ((_occurredEvent(_event,12/*undoone*/))){
		_state_id_undoone = 0;//moving to state E
_comps.push(4);
_comps.push(sid++);
 while (_comps.size()>0){
   Integer _dat = _comps.pop();
   Integer _act = _comps.pop();
   if (_act == -1){
    	_state_id_undoone = _dat; //deviating
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
 case 4:break;
 }}
 c_undoone_done.send();
		_goto_undoone(_info);
		}
}
}

public void _goto_undoone(String _info){
_cls_comps20.pw.println("[undoone]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_undoone(_state_id_undoone, 1));
_cls_comps20.pw.flush();
}

public String _string_undoone(int _state_id, int _mode){
switch(_state_id){
case 0: if (_mode == 0) return "E"; else return "!!!SYSTEM REACHED BAD STATE!!! E "+new _BadStateExceptioncomps2().toString()+" ";
case 1: if (_mode == 0) return "S"; else return "S";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}