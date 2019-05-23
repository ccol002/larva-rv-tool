package larva;


import code.main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.LinkedHashMap;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Stack;
import aspects._asp_resources0;

public class _cls_resources0 implements _callable{

public static PrintWriter pw; 
public static _cls_resources0 root;

public static LinkedHashMap<_cls_resources0,_cls_resources0> _cls_resources0_instances = new LinkedHashMap<_cls_resources0,_cls_resources0>();
static{
try{
RunningClock.start();
pw = new PrintWriter("C:\\Users\\University User\\Desktop\\projects\\Resource Management\\src//output_resources.txt");

root = new _cls_resources0();
_cls_resources0_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_resources0 parent; //to remain null - this class does not have a parent!
public static HashMap<Integer,File> f= new HashMap<Integer,File>();
public static HashMap<Integer,FileReader> fr= new HashMap<Integer,FileReader>();
public static HashMap<Integer,BufferedReader> br= new HashMap<Integer,BufferedReader>();
public Stack<Integer> _comps = new Stack<Integer>();
int no_automata = 1;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_resources0() {
}

public void initialisation() {

_cls_resources0.pw .println ("Open detected");

}

public static _cls_resources0 _get_cls_resources0_inst() { synchronized(_cls_resources0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_resources0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_resources0_instances){
_performLogic_transfer(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_resources0[] a = new _cls_resources0[1];
synchronized(_cls_resources0_instances){
a = _cls_resources0_instances.keySet().toArray(a);}
for (_cls_resources0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_resources0_instances){
_cls_resources0_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_transfer = 1;

public void _performLogic_transfer(String _info, int... _event) {

_cls_resources0.pw.println("[transfer]AUTOMATON::> transfer("+") STATE::>"+ _string_transfer(_state_id_transfer, 0));
_cls_resources0.pw.flush();

if (0==1){}
else if (_state_id_transfer==1){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*newfile*/))){
		_state_id_transfer = 1;//moving to state S
_cls_resources0.pw .println ("Open detected");

_comps.push(0);
_comps.push(_asp_resources0.sid);
		_goto_transfer(_info);
		}
		else if ((_occurredEvent(_event,2/*newfr*/))){
		_state_id_transfer = 1;//moving to state S
_cls_resources0.pw .println ("Open detected");

_comps.push(1);
_comps.push(_asp_resources0.sid);
		_goto_transfer(_info);
		}
		else if ((_occurredEvent(_event,4/*newbr*/))){
		_state_id_transfer = 1;//moving to state S
_cls_resources0.pw .println ("Open detected");

_comps.push(2);
_comps.push(_asp_resources0.sid);
		_goto_transfer(_info);
		}
		else if ((_occurredEvent(_event,6/*methodentry*/))){
		_state_id_transfer = 1;//moving to state S
_cls_resources0.pw .println ("Open detected");

_comps.push(3);
_comps.push(_asp_resources0.sid);
_comps.push(-1);
_comps.push(1);
		_goto_transfer(_info);
		}
		else if ((_occurredEvent(_event,8/*methodexit*/))){
		_state_id_transfer = 0;//moving to state E
_cls_resources0.pw .println ("Error State");

 while (_comps.size()>0){
   Integer _dat = _comps.pop();
   Integer _act = _comps.pop();
   if (_act == -1){
    	_state_id_transfer = _dat; //deviating
     break;
   }
   switch (_act) {
 case 0:break;
 case 1:try {fr.get(_dat) .close ();
_cls_resources0.pw .println (fr.get(_dat) +" has been closed automatically");
}catch (Exception e ){e .printStackTrace ();
}break;
 case 2:try {br.get(_dat) .close ();
_cls_resources0.pw .println (br.get(_dat) +" has been closed automatically");
}catch (Exception e ){e .printStackTrace ();
}break;
 case 3:break;
 case 4:break;
 }}
		_goto_transfer(_info);
		}
}
}

public void _goto_transfer(String _info){
_cls_resources0.pw.println("[transfer]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_transfer(_state_id_transfer, 1));
_cls_resources0.pw.flush();
}

public String _string_transfer(int _state_id, int _mode){
switch(_state_id){
case 0: if (_mode == 0) return "E"; else return "!!!SYSTEM REACHED BAD STATE!!! E "+new _BadStateExceptionresources().toString()+" ";
case 1: if (_mode == 0) return "S"; else return "S";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}