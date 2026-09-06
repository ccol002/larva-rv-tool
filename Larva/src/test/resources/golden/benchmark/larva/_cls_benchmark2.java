package larva;



import benchmark.*;


import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_benchmark2 implements _callable{

public static LinkedHashMap<_cls_benchmark2,_cls_benchmark2> _cls_benchmark2_instances;

_cls_benchmark1 parent;
public static boolean result;
public static double amount;
public static Transaction t1;
public Transaction t;
int no_automata;

int _state_id_settingAmount;
int _state_id_retryTimeoutandExceptionRetry;
public Clock c;
 public int retries =0 ;
 public double ourAmount =-1 ;

public static void initialize(){
//note that this initialisation does not include user-defined declarations in the Variables section


_cls_benchmark2_instances = new LinkedHashMap<_cls_benchmark2,_cls_benchmark2>();
}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_benchmark2( Transaction t,User u) {
parent = _cls_benchmark1._get_cls_benchmark1_inst( u);
c = new Clock(this,"c");
c.register(2000l);
this.t = t;
}

public void initialisation() {
no_automata = 2;
//initialise automata
_state_id_settingAmount = 3;
_state_id_retryTimeoutandExceptionRetry = 8;
   c.reset();
}

public static _cls_benchmark2 _get_cls_benchmark2_inst( Transaction t,User u) { synchronized(_cls_benchmark2_instances){
_cls_benchmark2 _inst = new _cls_benchmark2( t,u);
if (_cls_benchmark2_instances.containsKey(_inst))
{
_cls_benchmark2 tmp = _cls_benchmark2_instances.get(_inst);
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
return (t==null?1:t.hashCode()) *(parent==null?1:parent.hashCode()) *1;
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


public void _performLogic_settingAmount(String _info, int... _event) {

if (0==1){}
else if (_state_id_settingAmount==3){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*setAmount*/)) && (ourAmount ==-1 )){
		ourAmount =amount ;

		_state_id_settingAmount = 3;//moving to state starting
		_goto_settingAmount(_info);
		}
		else if ((_occurredEvent(_event,6/*transaction*/))){
		
		_state_id_settingAmount = 3;//moving to state starting
		_goto_settingAmount(_info);
		}
}
}

public void _goto_settingAmount(String _info){
 String state_format = _string_settingAmount(_state_id_settingAmount, 1);
 if (state_format.startsWith("!!!SYSTEM REACHED BAD STATE!!!")) {
_cls_benchmark0.pw.println("[settingAmount]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + state_format);
_cls_benchmark0.pw.flush();
}
}

public String _string_settingAmount(int _state_id, int _mode){
switch(_state_id){
case 2: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionbenchmark().toString()+" ";
case 3: if (_mode == 0) return "starting"; else return "starting";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public void _performLogic_retryTimeoutandExceptionRetry(String _info, int... _event) {

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
           _killThis(); //discard this automaton since an accepting state has been reached
		}
		else if ((_occurredEvent(_event,8/*execute*/)) && (retries ==4 )){
		
		_state_id_retryTimeoutandExceptionRetry = 4;//moving to state ok
		_goto_retryTimeoutandExceptionRetry(_info);
           _killThis(); //discard this automaton since an accepting state has been reached
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
           _killThis(); //discard this automaton since an accepting state has been reached
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
           _killThis(); //discard this automaton since an accepting state has been reached
		}
		else if ((_occurredEvent(_event,10/*executeExc*/))){
		
		_state_id_retryTimeoutandExceptionRetry = 5;//moving to state exception
		_goto_retryTimeoutandExceptionRetry(_info);
           _killThis(); //discard this automaton since an accepting state has been reached
		}
}
}

public void _goto_retryTimeoutandExceptionRetry(String _info){
 String state_format = _string_retryTimeoutandExceptionRetry(_state_id_retryTimeoutandExceptionRetry, 1);
 if (state_format.startsWith("!!!SYSTEM REACHED BAD STATE!!!")) {
_cls_benchmark0.pw.println("[retryTimeoutandExceptionRetry]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + state_format);
_cls_benchmark0.pw.flush();
}
}

public String _string_retryTimeoutandExceptionRetry(int _state_id, int _mode){
switch(_state_id){
case 5: if (_mode == 0) return "exception"; else return "(((SYSTEM REACHED AN ACCEPTED STATE)))  exception";
case 7: if (_mode == 0) return "normal"; else return "normal";
case 6: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionbenchmark().toString()+" ";
case 4: if (_mode == 0) return "ok"; else return "(((SYSTEM REACHED AN ACCEPTED STATE)))  ok";
case 8: if (_mode == 0) return "starting"; else return "starting";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}