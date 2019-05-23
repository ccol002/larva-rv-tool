package aspects;

import code.Account;
import java.util.Stack;

import larva.*;
public aspect _asp_comps0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_comps0.initialize();
}
}
before ( double amount,Account a) : (call(* Account.withdraw(..)) && target(a) && args(amount) && !cflow(adviceexecution())) {

synchronized(_asp_comps0.lock){

_cls_comps0 _cls_inst = _cls_comps0._get_cls_comps0_inst();
_cls_inst.amount = amount;
_cls_inst.a = a;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*withdraw*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*withdraw*/);
}
}
before () : (call(* *.cancel(..)) && !cflow(adviceexecution())) {

synchronized(_asp_comps0.lock){

_cls_comps0 _cls_inst = _cls_comps0._get_cls_comps0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*cancel*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*cancel*/);
}
}
before ( double amount,Account a) : (call(* Account.deposit(..)) && target(a) && args(amount) && !cflow(adviceexecution())) {

synchronized(_asp_comps0.lock){

_cls_comps0 _cls_inst = _cls_comps0._get_cls_comps0_inst();
_cls_inst.amount = amount;
_cls_inst.a = a;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*deposit*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*deposit*/);
}
}
}