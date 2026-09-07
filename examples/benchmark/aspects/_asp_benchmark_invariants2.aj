package aspects;


import benchmark.*;


import larva.*;
public aspect _asp_benchmark_invariants2 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_benchmark_invariants2.initialize();
}
}
after ( Transaction t1) returning (boolean result) : (call(* Transaction.execute(..)) && target(t1) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_benchmark_invariants0.lock){
Transaction t;
User u;
t =t1 ;
u =t .getOwner ();

_cls_benchmark_invariants2 _cls_inst = _cls_benchmark_invariants2._get_cls_benchmark_invariants2_inst( t,u);
_cls_inst.result = result;
_cls_inst.t1 = t1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*execute*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*execute*/);
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c)  && (if (_c.name.equals("c"))) && (if (millis == 2000)) && !cflow(adviceexecution())) {

synchronized(_asp_benchmark_invariants0.lock){
Transaction t;
User u;
t =null ;
u =null ;

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 2/*clock*/);
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*clock*/);
}
}
}
}
after ( Transaction t1) throwing () : (call(* Transaction.execute(..)) && target(t1) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_benchmark_invariants0.lock){
Transaction t;
User u;
t =t1 ;
u =t .getOwner ();

_cls_benchmark_invariants2 _cls_inst = _cls_benchmark_invariants2._get_cls_benchmark_invariants2_inst( t,u);
_cls_inst.t1 = t1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 10/*executeExc*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 10/*executeExc*/);
}
}
before ( double amount,Transaction t1) : (call(* Transaction.setAmount(..)) && target(t1) && args(amount) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_benchmark_invariants0.lock){
Transaction t;
User u;
t =t1 ;
u =t .getOwner ();

_cls_benchmark_invariants2 _cls_inst = _cls_benchmark_invariants2._get_cls_benchmark_invariants2_inst( t,u);
_cls_inst.amount = amount;
_cls_inst.t1 = t1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*setAmount*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*setAmount*/);
}
}
after ( Transaction t1) returning () : (call(* Transaction.*(..)) && target(t1) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_benchmark_invariants0.lock){
Transaction t;
User u;
t =t1 ;
u =t .getOwner ();

_cls_benchmark_invariants2 _cls_inst = _cls_benchmark_invariants2._get_cls_benchmark_invariants2_inst( t,u);
_cls_inst.t1 = t1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*transaction*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*transaction*/);
}
}
}