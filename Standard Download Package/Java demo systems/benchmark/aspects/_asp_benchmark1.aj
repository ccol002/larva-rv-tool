package aspects;


import benchmark.*;


import larva.*;
public aspect _asp_benchmark1 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_benchmark1.initialize();
}
}
before ( User u1) : (call(* User.addTransaction(..)) && target(u1) && !cflow(adviceexecution())) {

synchronized(_asp_benchmark0.lock){
User u;
u =u1 ;

_cls_benchmark1 _cls_inst = _cls_benchmark1._get_cls_benchmark1_inst( u);
_cls_inst.u1 = u1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*addTransaction*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*addTransaction*/);
}
}
}