package larva;


import code.Account;

import java.util.LinkedHashMap;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Stack;
import aspects._asp_comps20;

public class _cls_comps20 implements _callable{

public static PrintWriter pw; 
public static _cls_comps20 root;

public static LinkedHashMap<_cls_comps20,_cls_comps20> _cls_comps20_instances = new LinkedHashMap<_cls_comps20,_cls_comps20>();
static{
try{
RunningClock.start();
pw = new PrintWriter("C:\\Users\\Christian\\Desktop\\projects\\Financial Transactions\\src//output_comps2.txt");

root = new _cls_comps20();
_cls_comps20_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_comps20 parent; //to remain null - this class does not have a parent!
public static HashMap<Integer,Double> amount= new HashMap<Integer,Double>();
public static HashMap<Integer,Account> a= new HashMap<Integer,Account>();
public Stack<Integer> _comps = new Stack<Integer>();
int no_automata = 1;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_comps20() {
}

public void initialisation() {

_cls_comps20.pw .println ("Starting State");

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
_performLogic_transfer(_info, _event);
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

int _state_id_transfer = 3;

public void _performLogic_transfer(String _info, int... _event) {

_cls_comps20.pw.println("[transfer]AUTOMATON::> transfer("+") STATE::>"+ _string_transfer(_state_id_transfer, 0));
_cls_comps20.pw.flush();

if (0==1){}
else if (_state_id_transfer==2){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*cancel*/))){
		_state_id_transfer = 0;//moving to state E
_cls_comps20.pw .println ("Error State");

 while (_comps.size()>0){
   Integer _dat = _comps.pop();
   Integer _act = _comps.pop();
   switch (_act) {
 case 2:break;
 case 1:a.get(_dat) .withdraw (amount.get(_dat) );
break;
 case 0:a.get(_dat) .deposit (amount.get(_dat) );
break;
 }}
		_goto_transfer(_info);
		}
}
else if (_state_id_transfer==1){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*deposit*/))){
		_state_id_transfer = 2;//moving to state D
_cls_comps20.pw .println ("Deposit State");

_comps.push(1);
_comps.push(_asp_comps20.sid);
		_goto_transfer(_info);
		}
}
else if (_state_id_transfer==3){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*withdraw*/))){
		_state_id_transfer = 1;//moving to state W
_cls_comps20.pw .println ("Withdraw State");

_comps.push(0);
_comps.push(_asp_comps20.sid);
		_goto_transfer(_info);
		}
}
}

public void _goto_transfer(String _info){
_cls_comps20.pw.println("[transfer]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_transfer(_state_id_transfer, 1));
_cls_comps20.pw.flush();
}

public String _string_transfer(int _state_id, int _mode){
switch(_state_id){
case 2: if (_mode == 0) return "D"; else return "D";
case 0: if (_mode == 0) return "E"; else return "!!!SYSTEM REACHED BAD STATE!!! E "+new _BadStateExceptioncomps2().toString()+" ";
case 1: if (_mode == 0) return "W"; else return "W";
case 3: if (_mode == 0) return "S"; else return "S";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}