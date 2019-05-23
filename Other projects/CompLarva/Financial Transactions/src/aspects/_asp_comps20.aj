package aspects;

import code.Account;

import larva.*;
import java.util.HashMap;
import java.util.Stack;
public aspect _asp_comps20 {

public static Object lock = new Object();

public static Integer sid = 0;

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_comps20.initialize();
}
}
before ( Double amount,Account a) : (call(* Account.withdraw(..)) && target(a) && args(amount) && !cflow(adviceexecution())) {

synchronized(_asp_comps20.lock){

_cls_comps20 _cls_inst = _cls_comps20._get_cls_comps20_inst();
_asp_comps20.sid++;
_cls_inst.amount.put(_asp_comps20.sid,amount);
_cls_inst.a.put(_asp_comps20.sid,a);
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*withdraw*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*withdraw*/);
}
}
before () : (call(* *.cancel(..)) && !cflow(adviceexecution())) {

synchronized(_asp_comps20.lock){

_cls_comps20 _cls_inst = _cls_comps20._get_cls_comps20_inst();
_asp_comps20.sid++;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*cancel*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*cancel*/);
}
}
before ( Double amount,Account a) : (call(* Account.deposit(..)) && target(a) && args(amount) && !cflow(adviceexecution())) {

synchronized(_asp_comps20.lock){

_cls_comps20 _cls_inst = _cls_comps20._get_cls_comps20_inst();
_asp_comps20.sid++;
_cls_inst.amount.put(_asp_comps20.sid,amount);
_cls_inst.a.put(_asp_comps20.sid,a);
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*deposit*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*deposit*/);
}
}
}