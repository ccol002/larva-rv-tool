package aspects;

import larva.*;
public aspect _asp_tutorial_part10 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_tutorial_part10.initialize();
}
}
before () : (call(* *.badlogin(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_tutorial_part10.lock){

_cls_tutorial_part10 _cls_inst = _cls_tutorial_part10._get_cls_tutorial_part10_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 80/*badlogin*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 80/*badlogin*/);
}
}
before () : (call(* *.goodlogin(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_tutorial_part10.lock){

_cls_tutorial_part10 _cls_inst = _cls_tutorial_part10._get_cls_tutorial_part10_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 82/*goodlogin*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 82/*goodlogin*/);
}
}
}