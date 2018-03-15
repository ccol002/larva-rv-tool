package aspects;

import larva.*;
public aspect _asp_clocks0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_clocks0.initialize();
}
}
before ( String from,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_clocks0.d))) && args(from)) {

synchronized(_asp_clocks0.lock){

_cls_clocks0 _cls_inst = _cls_clocks0._get_cls_clocks0_inst();
_cls_inst.from = from;
_cls_clocks0._call_all(thisJoinPoint.getSignature().toString(), 2/*channeld*/);
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c)  && (if (_c.name.equals("c"))) && (if (millis == 1000)) && !cflow(adviceexecution())) {

synchronized(_asp_clocks0.lock){

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 0/*clockC5*/);
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*clockC5*/);
}
}
}
}
}