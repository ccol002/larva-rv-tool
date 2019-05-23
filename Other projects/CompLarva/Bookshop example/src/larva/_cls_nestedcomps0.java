package larva;


import code.*;

import java.util.LinkedHashMap;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Stack;

public class _cls_nestedcomps0 implements _callable{

public static PrintWriter pw; 
public static _cls_nestedcomps0 root;
public static Channel c_transaction_start = new Channel();
public static Channel c_transaction_done = new Channel();
public static Channel c_purchase_start = new Channel();
public static Channel c_purchase_done = new Channel();

public static LinkedHashMap<_cls_nestedcomps0,_cls_nestedcomps0> _cls_nestedcomps0_instances = new LinkedHashMap<_cls_nestedcomps0,_cls_nestedcomps0>();
public static HashMap<Integer,User> u= new HashMap<Integer,User>();
public static HashMap<Integer,Order> o= new HashMap<Integer,Order>();
public static Stack<Integer> _comps = new Stack<Integer>();
public static Integer sid = 0;
int no_automata = 2;
static{
try{
RunningClock.start();
pw = new PrintWriter("C:\\Users\\Christian\\Desktop\\projects\\Bookshop example\\src//output_nestedcomps.txt");

root = new _cls_nestedcomps0();
_cls_nestedcomps0_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_nestedcomps0 parent; //to remain null - this class does not have a parent!

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_nestedcomps0() {
}

public void initialisation() {
}

public static _cls_nestedcomps0 _get_cls_nestedcomps0_inst() { synchronized(_cls_nestedcomps0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_nestedcomps0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_nestedcomps0_instances){
_performLogic_transaction(_info, _event);
_performLogic_purchase(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_nestedcomps0[] a = new _cls_nestedcomps0[1];
synchronized(_cls_nestedcomps0_instances){
a = _cls_nestedcomps0_instances.keySet().toArray(a);}
for (_cls_nestedcomps0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_nestedcomps0_instances){
_cls_nestedcomps0_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_transaction = -2;

public void _performLogic_transaction(String _info, int... _event) {

_cls_nestedcomps0.pw.println("[transaction]AUTOMATON::> transaction("+") STATE::>"+ _string_transaction(_state_id_transaction, 0));
_cls_nestedcomps0.pw.flush();

if (0==1){}
else if (_state_id_transaction==2){
		if (1==0){}
		else if ((_occurredEvent(_event,10/*cancel*/))){
		_state_id_transaction = 1;//moving to state E
_comps.push(0);
_comps.push(sid++);
 while (_comps.size()>0){
   Integer _dat = _comps.pop();
   Integer _act = _comps.pop();
   if (_act == -1){
    	_state_id_transaction = _dat; //deviating
     break;
   }
   else if (_act == -2){
     //end of compensation for this scope reached
     break;
   } 
   switch (_act) {
 case 0:Store .notifyOperator ();
break;
 case 1:break;
 }}
 c_transaction_done.send();
		_goto_transaction(_info);
		}
}
else if (_state_id_transaction==-2){
		if (1==0){}
		else if ((_occurredEvent(_event,15/*c_purchase_done*/))){
		_state_id_transaction = 2;//moving to state S
_comps.push(1);
_comps.push(sid++);
		_goto_transaction(_info);
		}
}
}

public void _goto_transaction(String _info){
_cls_nestedcomps0.pw.println("[transaction]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_transaction(_state_id_transaction, 1));
_cls_nestedcomps0.pw.flush();
}

public String _string_transaction(int _state_id, int _mode){
switch(_state_id){
case 1: if (_mode == 0) return "E"; else return "!!!SYSTEM REACHED BAD STATE!!! E "+new _BadStateExceptionnestedcomps().toString()+" ";
case 0: if (_mode == 0) return "A"; else return "(((SYSTEM REACHED AN ACCEPTED STATE)))  A";
case 2: if (_mode == 0) return "S"; else return "S";
case -2: if (_mode == 0) return "_S"; else return "_S";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_purchase = 6;

public void _performLogic_purchase(String _info, int... _event) {

_cls_nestedcomps0.pw.println("[purchase]AUTOMATON::> purchase("+") STATE::>"+ _string_purchase(_state_id_purchase, 0));
_cls_nestedcomps0.pw.flush();

if (0==1){}
else if (_state_id_purchase==6){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*decStock*/))){
		_state_id_purchase = 6;//moving to state S
_comps.push(2);
_comps.push(sid++);
		_goto_purchase(_info);
		}
		else if ((_occurredEvent(_event,2/*recStock*/))){
		_state_id_purchase = 6;//moving to state S
_comps.push(3);
_comps.push(sid++);
		_goto_purchase(_info);
		}
		else if ((_occurredEvent(_event,4/*pay*/))){
		_state_id_purchase = 6;//moving to state S
_comps.push(4);
_comps.push(sid++);
		_goto_purchase(_info);
		}
		else if ((_occurredEvent(_event,6/*recPay*/))){
		_state_id_purchase = 6;//moving to state S
_comps.push(5);
_comps.push(sid++);
		_goto_purchase(_info);
		}
		else if ((_occurredEvent(_event,8/*pack*/))){
		_state_id_purchase = 4;//moving to state A
_comps.push(6);
_comps.push(sid++);
 while (_comps.size()>0){
   Integer _dat = _comps.pop();
   Integer _act = _comps.pop();
   if (_act == -2){
    	//stopping discard due to nesting
     break;
   }
 }
 c_purchase_done.send();
		_goto_purchase(_info);
		}
		else if ((_occurredEvent(_event,10/*cancel*/))){
		_state_id_purchase = 5;//moving to state E
_comps.push(7);
_comps.push(sid++);
 while (_comps.size()>0){
   Integer _dat = _comps.pop();
   Integer _act = _comps.pop();
   if (_act == -1){
    	_state_id_purchase = _dat; //deviating
     break;
   }
   else if (_act == -2){
     //end of compensation for this scope reached
     break;
   } 
   switch (_act) {
 case 2:Store .increaseStock ();
break;
 case 3:o.get(_dat) .stockSentBackToStore ();
break;
 case 4:u.get(_dat) .receiveMoney ();
break;
 case 5:Bank .refundMoney ();
break;
 case 6:o.get(_dat) .unpackOrder ();
break;
 case 7:break;
 }}
 c_purchase_done.send();
		_goto_purchase(_info);
		}
}
}

public void _goto_purchase(String _info){
_cls_nestedcomps0.pw.println("[purchase]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_purchase(_state_id_purchase, 1));
_cls_nestedcomps0.pw.flush();
}

public String _string_purchase(int _state_id, int _mode){
switch(_state_id){
case 5: if (_mode == 0) return "E"; else return "!!!SYSTEM REACHED BAD STATE!!! E "+new _BadStateExceptionnestedcomps().toString()+" ";
case 4: if (_mode == 0) return "A"; else return "(((SYSTEM REACHED AN ACCEPTED STATE)))  A";
case 6: if (_mode == 0) return "S"; else return "S";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}