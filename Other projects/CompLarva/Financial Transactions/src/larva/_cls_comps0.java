package larva;


import code.Account;
import java.util.Stack;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_comps0 implements _callable{

public static PrintWriter pw; 
public static _cls_comps0 root;

public static LinkedHashMap<_cls_comps0,_cls_comps0> _cls_comps0_instances = new LinkedHashMap<_cls_comps0,_cls_comps0>();
static{
try{
RunningClock.start();
pw = new PrintWriter("C:\\Users\\University User\\Desktop\\projects\\Financial Transactions\\src//output_comps.txt");

root = new _cls_comps0();
_cls_comps0_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_comps0 parent; //to remain null - this class does not have a parent!
public static double amount;
public static Account a;
int no_automata = 1;
 public Stack <Integer >compensations =new Stack <Integer >();
 public double deposit_amount ,withdraw_amount ;
 public Account deposit_account ,withdraw_account ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_comps0() {
}

public void initialisation() {

_cls_comps0.pw .println ("Starting State");

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
_performLogic_transfer(_info, _event);
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

int _state_id_transfer = 3;

public void _performLogic_transfer(String _info, int... _event) {

_cls_comps0.pw.println("[transfer]AUTOMATON::> transfer("+") STATE::>"+ _string_transfer(_state_id_transfer, 0));
_cls_comps0.pw.flush();

if (0==1){}
else if (_state_id_transfer==2){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*cancel*/))){
		
		_state_id_transfer = 0;//moving to state E
_cls_comps0.pw .println ("Error State");
while (compensations .size ()>0 ){Integer i =compensations .pop ();
switch (i ){case 1 :deposit_account .withdraw (deposit_amount );
break ;
case 2 :withdraw_account .deposit (withdraw_amount );
}}
		_goto_transfer(_info);
		}
}
else if (_state_id_transfer==1){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*deposit*/))){
		compensations .push (1 );
deposit_account =a ;
deposit_amount =amount ;

		_state_id_transfer = 2;//moving to state D
_cls_comps0.pw .println ("Deposit State");

		_goto_transfer(_info);
		}
}
else if (_state_id_transfer==3){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*withdraw*/))){
		compensations .push (2 );
withdraw_account =a ;
withdraw_amount =amount ;

		_state_id_transfer = 1;//moving to state W
_cls_comps0.pw .println ("Withdraw State");

		_goto_transfer(_info);
		}
}
}

public void _goto_transfer(String _info){
_cls_comps0.pw.println("[transfer]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_transfer(_state_id_transfer, 1));
_cls_comps0.pw.flush();
}

public String _string_transfer(int _state_id, int _mode){
switch(_state_id){
case 2: if (_mode == 0) return "D"; else return "D";
case 0: if (_mode == 0) return "E"; else return "!!!SYSTEM REACHED BAD STATE!!! E "+new _BadStateExceptioncomps().toString()+" ";
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