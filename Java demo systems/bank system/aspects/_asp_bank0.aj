package aspects;

import nesting.User;
import nesting.Account;
import nesting.Transaction;

import larva.*;
public aspect _asp_bank0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_bank0.initialize();
}
}
before () : (call(* *.deleteUser(..)) && !cflow(adviceexecution())) {

synchronized(_asp_bank0.lock){

_cls_bank0 _cls_inst = _cls_bank0._get_cls_bank0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*deleteUser*/, 4/*allUsers*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*deleteUser*/, 4/*allUsers*/);
}
}
before () : (call(* *.addUser(..)) && !cflow(adviceexecution())) {

synchronized(_asp_bank0.lock){

_cls_bank0 _cls_inst = _cls_bank0._get_cls_bank0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*addUser*/, 4/*allUsers*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*addUser*/, 4/*allUsers*/);
}
}
}