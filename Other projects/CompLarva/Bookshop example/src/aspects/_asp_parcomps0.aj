package aspects;

import code.*;

import larva.*;
import java.util.HashMap;
import java.util.Stack;
public aspect _asp_parcomps0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_parcomps0.initialize();
}
}
after () returning () : (call(* *.decreaseStock(..)) && !cflow(adviceexecution())) {

synchronized(_asp_parcomps0.lock){

_cls_parcomps0 _cls_inst = _cls_parcomps0._get_cls_parcomps0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*decStock*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*decStock*/);
}
}
after ( Order o) returning () : (call(* Order.stockReceivedFromStore(..)) && target(o) && !cflow(adviceexecution())) {

synchronized(_asp_parcomps0.lock){

_cls_parcomps0 _cls_inst = _cls_parcomps0._get_cls_parcomps0_inst();
_cls_inst.o.put(_cls_parcomps0.sid,o);
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*recStock*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*recStock*/);
}
}
after () returning () : (call(* *.cancelOrder(..)) && !cflow(adviceexecution())) {

synchronized(_asp_parcomps0.lock){

_cls_parcomps0 _cls_inst = _cls_parcomps0._get_cls_parcomps0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 14/*cancel*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 14/*cancel*/);
}
}
after ( User u) returning () : (execution(* User.run(..)) && target(u) && !cflow(adviceexecution())) {

synchronized(_asp_parcomps0.lock){

_cls_parcomps0 _cls_inst = _cls_parcomps0._get_cls_parcomps0_inst();
_cls_inst.u.put(_cls_parcomps0.sid,u);
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*pay*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*pay*/);
}
}
before ( Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_parcomps0.c_purchase_start)))) {

synchronized(_asp_parcomps0.lock){

_cls_parcomps0 _cls_inst = _cls_parcomps0._get_cls_parcomps0_inst();
_cls_parcomps0._call_all(thisJoinPoint.getSignature().toString(), 0/*c_purchase_start*/);
}
}
after () returning () : (call(* *.receiveMoney(..)) && !cflow(adviceexecution())) {

synchronized(_asp_parcomps0.lock){

_cls_parcomps0 _cls_inst = _cls_parcomps0._get_cls_parcomps0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 10/*recPay*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 10/*recPay*/);
}
}
after ( Order o) returning () : (execution(* Order.run(..)) && target(o) && !cflow(adviceexecution())) {

synchronized(_asp_parcomps0.lock){

_cls_parcomps0 _cls_inst = _cls_parcomps0._get_cls_parcomps0_inst();
_cls_inst.o.put(_cls_parcomps0.sid,o);
_cls_inst._call(thisJoinPoint.getSignature().toString(), 12/*pack*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 12/*pack*/);
}
}
before ( Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_parcomps0.c_purchase_done)))) {

synchronized(_asp_parcomps0.lock){

_cls_parcomps0 _cls_inst = _cls_parcomps0._get_cls_parcomps0_inst();
_cls_parcomps0._call_all(thisJoinPoint.getSignature().toString(), 2/*c_purchase_done*/);
}
}
}