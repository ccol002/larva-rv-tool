package larva;


import benchmark.*;


public aspect _asp_benchmarkInv2 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_benchmarkInv2.initialize();
}
}
after ( Transaction t1) returning () : (call(* Transaction.*(..)) && target(t1) && !cflow(adviceexecution())) {
Transaction t;
User u;
t =t1 ;
u =t .getOwner ();

_cls_benchmarkInv2 _cls_inst = _cls_benchmarkInv2._get_cls_benchmarkInv2_inst( t,u);
_cls_inst.t1 = t1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*transaction*/);
}
after ( Transaction t1) throwing () : (call(* Transaction.execute(..)) && target(t1) && !cflow(adviceexecution())) {
Transaction t;
User u;
t =t1 ;
u =t .getOwner ();

_cls_benchmarkInv2 _cls_inst = _cls_benchmarkInv2._get_cls_benchmarkInv2_inst( t,u);
_cls_inst.t1 = t1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 10/*executeExc*/);
}

before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c) && (if (millis % 2000==0)) && !cflow(adviceexecution())) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 2/*clock*/);
}
after ( Transaction t1) returning (boolean result) : (call(* Transaction.execute(..)) && target(t1) && !cflow(adviceexecution())) {
Transaction t;
User u;
t =t1 ;
u =t .getOwner ();

_cls_benchmarkInv2 _cls_inst = _cls_benchmarkInv2._get_cls_benchmarkInv2_inst( t,u);
_cls_inst.t1 = t1;
_cls_inst.result = result;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*execute*/);
}
before ( double amount,Transaction t1) : (call(* Transaction.setAmount(..)) && target(t1) && args(amount) && !cflow(adviceexecution())) {
Transaction t;
User u;
t =t1 ;
u =t .getOwner ();

_cls_benchmarkInv2 _cls_inst = _cls_benchmarkInv2._get_cls_benchmarkInv2_inst( t,u);
_cls_inst.amount = amount;
_cls_inst.t1 = t1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*setAmount*/);
}




}