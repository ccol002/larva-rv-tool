package larva;


import benchmark.*;


public aspect _asp_benchmarkInv1 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_benchmarkInv1.initialize();
}
}

before ( User u1) : (call(* User.addTransaction(..)) && target(u1) && !cflow(adviceexecution())) {
User u;
u =u1 ;

_cls_benchmarkInv1 _cls_inst = _cls_benchmarkInv1._get_cls_benchmarkInv1_inst( u);
_cls_inst.u1 = u1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*addTransaction*/);
}
}