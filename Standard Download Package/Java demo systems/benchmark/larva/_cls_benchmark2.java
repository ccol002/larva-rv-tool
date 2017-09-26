package larva;



import benchmark.*;


import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_benchmark2 implements _callable{

public static LinkedHashMap<_cls_benchmark2,_cls_benchmark2> _cls_benchmark2_instances = new LinkedHashMap<_cls_benchmark2,_cls_benchmark2>();

_cls_benchmark1 parent;
public static double amount;
public static boolean result;
public static Transaction t1;
public Transaction t;
int no_automata = 2;
public Clock c = new Clock(this,"c");
 public int retries =0 ;
 public double ourAmount =-1 ;
public boolean getAmount_enb = false;
public Double getAmount_temp;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_benchmark2( Transaction t,User u) {
parent = _cls_benchmark1._get_cls_benchmark1_inst( u);
c.register(2000l);
this.t = t;
}

public void initialisation() {
   c.reset();
}

public static _cls_benchmark2 _get_cls_benchmark2_inst( Transaction t,User u) { synchronized(_cls_benchmark2_instances){
_cls_benchmark2 _inst = new _cls_benchmark2( t,u);
if (_cls_benchmark2_instances.containsKey(_inst))
{
_cls_benchmark2 tmp = _cls_benchmark2_instances.get(_inst);
if ( tmp.getAmount_enb && !tmp.getAmount_temp.equals(t.getAmount() )){
  _cls_benchmark0.pw.println(" Invariant Check: getAmount Failed: t.getAmount() !!: " + new _BadStateExceptionbenchmark().toString());
  _cls_benchmark0.pw.flush();
tmp.getAmount_temp = t.getAmount() ;
}
 return _cls_benchmark2_instances.get(_inst);
}
else
{
 _inst.initialisation();
 _cls_benchmark2_instances.put(_inst,_inst);
 return _inst;
}
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_benchmark2)
 && (t == null || t.equals(((_cls_benchmark2)o).t))
 && (parent == null || parent.equals(((_cls_benchmark2)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_benchmark2_instances){
_performLogic_settingAmount(_info, _event);
_performLogic_retryTimeoutandExceptionRetry(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_benchmark2[] a = new _cls_benchmark2[1];
synchronized(_cls_benchmark2_instances){
a = _cls_benchmark2_instances.keySet().toArray(a);}
for (_cls_benchmark2 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_benchmark2_instances){
_cls_benchmark2_instances.remove(this);}
synchronized(c){
c.off();
c._inst = null;
c = null;}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_settingAmount = 3;

public void _performLogic_settingAmount(String _info, int... _event) {

_cls_benchmark0.pw.println("[settingAmount]AUTOMATON::> settingAmount("+t + " " + ") STATE::>"+ _string_settingAmount(_state_id_settingAmount, 0));
_cls_benchmark0.pw.flush();

if (0==1){}
else if (_state_id_settingAmount==3){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*setAmount*/)) && (ourAmount ==-1 )){
		ourAmount =amount ;

		_state_id_settingAmount = 3;//moving to state starting
getAmount_enb = true;getAmount_temp = t.getAmount() ;
		_goto_settingAmount(_info);
		}
		else if ((_occurredEvent(_event,6/*transaction*/))){
		
		_state_id_settingAmount = 3;//moving to state starting
		_goto_settingAmount(_info);
		}
}
}

public void _goto_settingAmount(String _info){
_cls_benchmark0.pw.println("[settingAmount]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_settingAmount(_state_id_settingAmount, 1));
_cls_benchmark0.pw.flush();
}

public String _string_settingAmount(int _state_id, int _mode){
switch(_state_id){
case 3: if (_mode == 0) return "starting"; else return "starting";
case 2: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionbenchmark().toString()+" ";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_retryTimeoutandExceptionRetry = 8;

public void _performLogic_retryTimeoutandExceptionRetry(String _info, int... _event) {

_cls_benchmark0.pw.println("[retryTimeoutandExceptionRetry]AUTOMATON::> retryTimeoutandExceptionRetry("+t + " " + ") STATE::>"+ _string_retryTimeoutandExceptionRetry(_state_id_retryTimeoutandExceptionRetry, 0));
_cls_benchmark0.pw.flush();

if (0==1){}
else if (_state_id_retryTimeoutandExceptionRetry==5){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*execute*/))){
		
		_state_id_retryTimeoutandExceptionRetry = 6;//moving to state bad
		_goto_retryTimeoutandExceptionRetry(_info);
		}
		else if ((_occurredEvent(_event,10/*executeExc*/))){
		
		_state_id_retryTimeoutandExceptionRetry = 6;//moving to state bad
		_goto_retryTimeoutandExceptionRetry(_info);
		}
}
else if (_state_id_retryTimeoutandExceptionRetry==7){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*execute*/)) && (result )){
		
		_state_id_retryTimeoutandExceptionRetry = 4;//moving to state ok
		_goto_retryTimeoutandExceptionRetry(_info);
		}
		else if ((_occurredEvent(_event,8/*execute*/)) && (retries ==4 )){
		
		_state_id_retryTimeoutandExceptionRetry = 4;//moving to state ok
		_goto_retryTimeoutandExceptionRetry(_info);
		}
		else if ((_occurredEvent(_event,8/*execute*/)) && (!result )){
		retries ++;

		_state_id_retryTimeoutandExceptionRetry = 7;//moving to state normal
		_goto_retryTimeoutandExceptionRetry(_info);
		}
		else if ((_occurredEvent(_event,2/*clock*/)) && (c .compareTo ((retries )*2 )>=0 &&retries <5 )){
		c .reset ();

		_state_id_retryTimeoutandExceptionRetry = 6;//moving to state bad
		_goto_retryTimeoutandExceptionRetry(_info);
		}
		else if ((_occurredEvent(_event,10/*executeExc*/))){
		
		_state_id_retryTimeoutandExceptionRetry = 5;//moving to state exception
		_goto_retryTimeoutandExceptionRetry(_info);
		}
}
else if (_state_id_retryTimeoutandExceptionRetry==8){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*execute*/)) && (!result )){
		retries ++;
c .reset ();

		_state_id_retryTimeoutandExceptionRetry = 7;//moving to state normal
		_goto_retryTimeoutandExceptionRetry(_info);
		}
		else if ((_occurredEvent(_event,8/*execute*/)) && (result )){
		
		_state_id_retryTimeoutandExceptionRetry = 4;//moving to state ok
		_goto_retryTimeoutandExceptionRetry(_info);
		}
		else if ((_occurredEvent(_event,10/*executeExc*/))){
		
		_state_id_retryTimeoutandExceptionRetry = 5;//moving to state exception
		_goto_retryTimeoutandExceptionRetry(_info);
		}
}
}

public void _goto_retryTimeoutandExceptionRetry(String _info){
_cls_benchmark0.pw.println("[retryTimeoutandExceptionRetry]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_retryTimeoutandExceptionRetry(_state_id_retryTimeoutandExceptionRetry, 1));
_cls_benchmark0.pw.flush();
}

public String _string_retryTimeoutandExceptionRetry(int _state_id, int _mode){
switch(_state_id){
case 7: if (_mode == 0) return "normal"; else return "normal";
case 5: if (_mode == 0) return "exception"; else return "(((SYSTEM REACHED AN ACCEPTED STATE)))  exception";
case 8: if (_mode == 0) return "starting"; else return "starting";
case 4: if (_mode == 0) return "ok"; else return "(((SYSTEM REACHED AN ACCEPTED STATE)))  ok";
case 6: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionbenchmark().toString()+" ";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}