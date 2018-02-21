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
before () : (call(* *.badlogin(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_badlogins0.lock){

_cls_badlogins0 _cls_inst = _cls_badlogins0._get_cls_badlogins0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 35/*badlogin*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 35/*badlogin*/);
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c)  && (if (_c.name.equals("c"))) && (if (millis == 5000)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_badlogins0.lock){

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 37/*cAT5*/);
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 37/*cAT5*/);
}
}
}
}
}