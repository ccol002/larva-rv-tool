package larva;



import benchmark.*;


import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_benchmarkInv2 implements _callable{

public static HashMap<_cls_benchmarkInv2,_cls_benchmarkInv2> _cls_benchmarkInv2_instances = new HashMap<_cls_benchmarkInv2,_cls_benchmarkInv2>();

_cls_benchmarkInv1 parent;
public double amount;
public Transaction t1;
public boolean result;
public Transaction t;
int no_automata = 2;
public Clock c = new Clock(this);
 public int retries =0 ;
public boolean amountInv_enb = false;
public Double amountInv_temp;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_benchmarkInv2( Transaction t,User u) {
parent = _cls_benchmarkInv1._get_cls_benchmarkInv1_inst( u);
c.registerCycle(2000);
this.t = t;
}

public static _cls_benchmarkInv2 _get_cls_benchmarkInv2_inst( Transaction t,User u) {
_cls_benchmarkInv2 _inst = new _cls_benchmarkInv2( t,u);
if (_cls_benchmarkInv2_instances.containsKey(_inst))
{
_cls_benchmarkInv2 tmp = _cls_benchmarkInv2_instances.get(_inst);
if ( tmp.amountInv_enb && !tmp.amountInv_temp.equals(t.getAmount() )){
  _cls_benchmarkInv0.pw.println(" Invariant Check: amountInv Failed: t.getAmount() !!: " + new _BadStateExceptionbenchmarkInv().toString());
  _cls_benchmarkInv0.pw.flush();
tmp.amountInv_temp = t.getAmount() ;
}
 return _cls_benchmarkInv2_instances.get(_inst);
}
else
{
_inst.c.reset();
 _cls_benchmarkInv2_instances.put(_inst,_inst);
 return _inst;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_benchmarkInv2)
 && (t == null || t.equals(((_cls_benchmarkInv2)o).t))
 && (parent == null || parent.equals(((_cls_benchmarkInv2)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
_performLogic_settingAmount(_info, _event);
_performLogic_retryTimeoutandExceptionRetry(_info, _event);
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

for (_cls_benchmarkInv2 _inst : _cls_benchmarkInv2_instances.keySet())
_inst._call(_info, _event);
}

public void _killThis(){
if (--no_automata <= 0){
c.off();
c._inst = null;
c = null;
 _cls_benchmarkInv2_instances.remove(this);
}
}

int _state_id_settingAmount = 3;

public void _performLogic_settingAmount(String _info, int... _event) {

_cls_benchmarkInv0.pw.println("AUTOMATON::> settingAmount("+t + " " + ") STATE::>"+ _string_settingAmount(_state_id_settingAmount, 0));
_cls_benchmarkInv0.pw.flush();

if (0==1){}
else if (_state_id_settingAmount==3){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*setAmount*/))){
		
		_state_id_settingAmount = 3;//moving to state starting
amountInv_enb = true;amountInv_temp = t.getAmount() ;
		_goto_settingAmount(_info);
		}
		else if ((_occurredEvent(_event,6/*transaction*/))){
		
		_state_id_settingAmount = 3;//moving to state starting
		_goto_settingAmount(_info);
		}
}
}

public void _goto_settingAmount(String _info){
_cls_benchmarkInv0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_settingAmount(_state_id_settingAmount, 1));
_cls_benchmarkInv0.pw.flush();
}

public String _string_settingAmount(int _state_id, int _mode){
switch(_state_id){
case 2: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionbenchmarkInv().toString()+" ";
case 3: if (_mode == 0) return "starting"; else return "starting";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_retryTimeoutandExceptionRetry = 8;

public void _performLogic_retryTimeoutandExceptionRetry(String _info, int... _event) {

_cls_benchmarkInv0.pw.println("AUTOMATON::> retryTimeoutandExceptionRetry("+t + " " + ") STATE::>"+ _string_retryTimeoutandExceptionRetry(_state_id_retryTimeoutandExceptionRetry, 0));
_cls_benchmarkInv0.pw.flush();

if (0==1){}
else if (_state_id_retryTimeoutandExceptionRetry==5){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*execute*/))){
		
		_state_id_retryTimeoutandExceptionRetry = 6;//moving to state bad
_killThis ();

		_goto_retryTimeoutandExceptionRetry(_info);
		}
		else if ((_occurredEvent(_event,10/*executeExc*/))){
		
		_state_id_retryTimeoutandExceptionRetry = 6;//moving to state bad
_killThis ();

		_goto_retryTimeoutandExceptionRetry(_info);
		}
}
else if (_state_id_retryTimeoutandExceptionRetry==8){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*execute*/)) && (!result )){
		retries ++;
c.reset ();

		_state_id_retryTimeoutandExceptionRetry = 7;//moving to state normal
		_goto_retryTimeoutandExceptionRetry(_info);
		}
		else if ((_occurredEvent(_event,8/*execute*/)) && (result )){
		
		_state_id_retryTimeoutandExceptionRetry = 4;//moving to state ok
_killThis ();

		_goto_retryTimeoutandExceptionRetry(_info);
		}
		else if ((_occurredEvent(_event,10/*executeExc*/))){
		
		_state_id_retryTimeoutandExceptionRetry = 5;//moving to state exception
		_goto_retryTimeoutandExceptionRetry(_info);
		}
}
else if (_state_id_retryTimeoutandExceptionRetry==7){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*execute*/)) && (result )){
		
		_state_id_retryTimeoutandExceptionRetry = 4;//moving to state ok
_killThis ();

		_goto_retryTimeoutandExceptionRetry(_info);
		}
		else if ((_occurredEvent(_event,8/*execute*/)) && (retries ==4 )){
		
		_state_id_retryTimeoutandExceptionRetry = 4;//moving to state ok
_killThis ();

		_goto_retryTimeoutandExceptionRetry(_info);
		}
		else if ((_occurredEvent(_event,8/*execute*/)) && (!result )){
		retries ++;

		_state_id_retryTimeoutandExceptionRetry = 7;//moving to state normal
		_goto_retryTimeoutandExceptionRetry(_info);
		}
		else if ((_occurredEvent(_event,2/*clock*/)) && (c.compareTo ((retries )*2 )>=0 &&retries <5 )){
		
		_state_id_retryTimeoutandExceptionRetry = 6;//moving to state bad
_killThis ();

		_goto_retryTimeoutandExceptionRetry(_info);
		}
		else if ((_occurredEvent(_event,10/*executeExc*/))){
		
		_state_id_retryTimeoutandExceptionRetry = 5;//moving to state exception
		_goto_retryTimeoutandExceptionRetry(_info);
		}
}
}

public void _goto_retryTimeoutandExceptionRetry(String _info){
_cls_benchmarkInv0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_retryTimeoutandExceptionRetry(_state_id_retryTimeoutandExceptionRetry, 1));
_cls_benchmarkInv0.pw.flush();
}

public String _string_retryTimeoutandExceptionRetry(int _state_id, int _mode){
switch(_state_id){
case 6: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionbenchmarkInv().toString()+" ";
case 5: if (_mode == 0) return "exception"; else return "(((SYSTEM REACHED AN ACCEPTED STATE)))  exception";
case 8: if (_mode == 0) return "starting"; else return "starting";
case 4: if (_mode == 0) return "ok"; else return "(((SYSTEM REACHED AN ACCEPTED STATE)))  ok";
case 7: if (_mode == 0) return "normal"; else return "normal";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}