package aspects;


import benchmark.*;


import larva.*;
public aspect _asp_benchmark_invariants1 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_benchmark_invariants1.initialize();
}
}
before ( User u1) : (call(* User.addTransaction(..)) && target(u1) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_benchmark_invariants0.lock){
User u;
u =u1 ;

_cls_benchmark_invariants1 _cls_inst = _cls_benchmark_invariants1._get_cls_benchmark_invariants1_inst( u);
_cls_inst.u1 = u1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*addTransaction*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*addTransaction*/);
}
}
}