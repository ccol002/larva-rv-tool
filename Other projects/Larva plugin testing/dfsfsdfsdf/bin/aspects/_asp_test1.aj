package aspects;


import transactionsystem.UserInfo;


import larva.*;
public aspect _asp_test1 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_test1.initialize();
}
}
before ( UserInfo u1) : (call(* UserInfo.makeGoldUser(..)) && target(u1) && !cflow(adviceexecution())) {

synchronized(_asp_test0.lock){
UserInfo u;
u =u1 ;

_cls_test1 _cls_inst = _cls_test1._get_cls_test1_inst( u);
_cls_inst.u1 = u1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*makeGold*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*makeGold*/);
}
}
}