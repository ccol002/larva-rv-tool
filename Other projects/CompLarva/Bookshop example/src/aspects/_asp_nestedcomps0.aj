package aspects;

import code.*;

import larva.*;
import java.util.HashMap;
import java.util.Stack;
public aspect _asp_nestedcomps0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_nestedcomps0.initialize();
}
}
after () returning () : (call(* *.decreaseStock(..)) && !cflow(adviceexecution())) {

synchronized(_asp_nestedcomps0.lock){

_cls_nestedcomps0 _cls_inst = _cls_nestedcomps0._get_cls_nestedcomps0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*decStock*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*decStock*/);
}
}
after ( Order o) returning () : (call(* Order.stockReceivedFromStore(..)) && target(o) && !cflow(adviceexecution())) {

synchronized(_asp_nestedcomps0.lock){

_cls_nestedcomps0 _cls_inst = _cls_nestedcomps0._get_cls_nestedcomps0_inst();
_cls_inst.o.put(_cls_nestedcomps0.sid,o);
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*recStock*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*recStock*/);
}
}
after () returning () : (call(* *.cancelOrder(..)) && !cflow(adviceexecution())) {

synchronized(_asp_nestedcomps0.lock){

_cls_nestedcomps0 _cls_inst = _cls_nestedcomps0._get_cls_nestedcomps0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 10/*cancel*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 10/*cancel*/);
}
}
after ( User u) returning () : (call(* User.payMoney(..)) && target(u) && !cflow(adviceexecution())) {

synchronized(_asp_nestedcomps0.lock){

_cls_nestedcomps0 _cls_inst = _cls_nestedcomps0._get_cls_nestedcomps0_inst();
_cls_inst.u.put(_cls_nestedcomps0.sid,u);
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*pay*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*pay*/);
}
}
after () returning () : (call(* *.receiveMoney(..)) && !cflow(adviceexecution())) {

synchronized(_asp_nestedcomps0.lock){

_cls_nestedcomps0 _cls_inst = _cls_nestedcomps0._get_cls_nestedcomps0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*recPay*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*recPay*/);
}
}
after ( Order o) returning () : (call(* Order.packOrder(..)) && target(o) && !cflow(adviceexecution())) {

synchronized(_asp_nestedcomps0.lock){

_cls_nestedcomps0 _cls_inst = _cls_nestedcomps0._get_cls_nestedcomps0_inst();
_cls_inst.o.put(_cls_nestedcomps0.sid,o);
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*pack*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*pack*/);
}
}
before ( Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_nestedcomps0.c_purchase_done)))) {

synchronized(_asp_nestedcomps0.lock){

_cls_nestedcomps0 _cls_inst = _cls_nestedcomps0._get_cls_nestedcomps0_inst();
_cls_nestedcomps0._call_all(thisJoinPoint.getSignature().toString(), 15/*c_purchase_done*/);
}
}
before ( Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_nestedcomps0.c_purchase_start)))) {

synchronized(_asp_nestedcomps0.lock){

_cls_nestedcomps0 _cls_inst = _cls_nestedcomps0._get_cls_nestedcomps0_inst();
_cls_nestedcomps0._call_all(thisJoinPoint.getSignature().toString(), 13/*c_purchase_start*/);
}
}
}