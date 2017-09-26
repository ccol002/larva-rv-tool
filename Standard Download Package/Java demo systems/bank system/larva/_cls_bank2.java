package larva;


import nesting.User;
import nesting.Account;
import nesting.Transaction;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_bank2 implements _callable{

public static LinkedHashMap<_cls_bank2,_cls_bank2> _cls_bank2_instances = new LinkedHashMap<_cls_bank2,_cls_bank2>();

_cls_bank1 parent;
public static Account a1;
public Account a;
int no_automata = 1;
 public int transactionCnt =0 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_bank2( Account a,User u) {
parent = _cls_bank1._get_cls_bank1_inst( u);
this.a = a;
}

public void initialisation() {


}

public static _cls_bank2 _get_cls_bank2_inst( Account a,User u) { synchronized(_cls_bank2_instances){
_cls_bank2 _inst = new _cls_bank2( a,u);
if (_cls_bank2_instances.containsKey(_inst))
{
_cls_bank2 tmp = _cls_bank2_instances.get(_inst);
 return _cls_bank2_instances.get(_inst);
}
else
{
 _inst.initialisation();
 _cls_bank2_instances.put(_inst,_inst);
 return _inst;
}
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_bank2)
 && (a == null || a.equals(((_cls_bank2)o).a))
 && (parent == null || parent.equals(((_cls_bank2)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_bank2_instances){
_performLogic_transaction(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_bank2[] a = new _cls_bank2[1];
synchronized(_cls_bank2_instances){
a = _cls_bank2_instances.keySet().toArray(a);}
for (_cls_bank2 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_bank2_instances){
_cls_bank2_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_transaction = 7;

public void _performLogic_transaction(String _info, int... _event) {

_cls_bank0.pw.println("[transaction]AUTOMATON::> transaction("+a + " " + ") STATE::>"+ _string_transaction(_state_id_transaction, 0));
_cls_bank0.pw.flush();

if (0==1){}
else if (_state_id_transaction==7){
		if (1==0){}
		else if ((_occurredEvent(_event,10/*addTransaction*/)) && (transactionCnt >5 )){
		
		_state_id_transaction = 6;//moving to state toomany
_cls_bank0.pw .println (parent.u );

		_goto_transaction(_info);
		}
		else if ((_occurredEvent(_event,10/*addTransaction*/))){
		transactionCnt ++;
_cls_bank0.pw .println ("I still have access to USER : "+parent.u +" and to usercnt: "+parent.parent.userCnt +" and to accountcnt: "+parent.accountCnt +" and to transactioncnt: "+transactionCnt );

		_state_id_transaction = 7;//moving to state start

		_goto_transaction(_info);
		}
		else if ((_occurredEvent(_event,12/*deleteTransaction*/))){
		transactionCnt --;

		_state_id_transaction = 7;//moving to state start

		_goto_transaction(_info);
		}
		else if ((_occurredEvent(_event,14/*allTransactions*/))){
		
		_state_id_transaction = 7;//moving to state start

		_goto_transaction(_info);
		}
}
}

public void _goto_transaction(String _info){
_cls_bank0.pw.println("[transaction]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_transaction(_state_id_transaction, 1));
_cls_bank0.pw.flush();
}

public String _string_transaction(int _state_id, int _mode){
switch(_state_id){
case 7: if (_mode == 0) return "start"; else return "start";
case 6: if (_mode == 0) return "toomany"; else return "!!!SYSTEM REACHED BAD STATE!!! toomany "+new _BadStateExceptionbank().toString()+" ";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}