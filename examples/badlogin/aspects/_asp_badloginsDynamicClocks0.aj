package aspects;

import larva.*;
public aspect _asp_badloginsDynamicClocks0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_badloginsDynamicClocks0.initialize();
}
}
before () : (call(* *.badlogin(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_badloginsDynamicClocks0.lock){

_cls_badloginsDynamicClocks0 _cls_inst = _cls_badloginsDynamicClocks0._get_cls_badloginsDynamicClocks0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*badlogin*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*badlogin*/);
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c) && !cflow(adviceexecution())) {

synchronized(_asp_badloginsDynamicClocks0.lock){

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 2/*cAT5*/);
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*cAT5*/);
}
}
}
}
}