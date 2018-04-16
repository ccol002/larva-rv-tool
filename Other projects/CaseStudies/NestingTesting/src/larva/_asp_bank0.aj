package larva;

import nesting.User;
import nesting.Account;
import nesting.Transaction;

public aspect _asp_bank0 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_bank0.initialize();
}
}



before () : (call(* *.deleteUser(..)) && !cflow(adviceexecution())) {

_cls_bank0 _cls_inst = _cls_bank0._get_cls_bank0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*deleteUser*/, 4/*allUsers*/);
}
before () : (call(* *.addUser(..)) && !cflow(adviceexecution())) {

_cls_bank0 _cls_inst = _cls_bank0._get_cls_bank0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*addUser*/, 4/*allUsers*/);
}
}