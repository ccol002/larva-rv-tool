package aspects;

import larva.*;
public aspect _asp_badlogins0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_badlogins0.initialize();
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c) && !cflow(adviceexecution())) {

synchronized(_asp_badlogins0.lock){

_cls_badlogins0 _cls_inst = _cls_badlogins0._get_cls_badlogins0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*cAT5*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*cAT5*/);
}
}
before () : (call(* *.badlogin(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_badlogins0.lock){

_cls_badlogins0 _cls_inst = _cls_badlogins0._get_cls_badlogins0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*badlogin*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*badlogin*/);
}
}
}