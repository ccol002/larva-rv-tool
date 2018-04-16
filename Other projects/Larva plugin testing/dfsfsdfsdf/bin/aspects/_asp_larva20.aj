package aspects;

import java.util.Iterator;
import java.util.Collection;

import larva.*;
public aspect _asp_larva20 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_larva20.initialize();
}
}
before ( Iterator i2) : (call(* Iterator.*(..)) && target(i2) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_larva20.lock){

_cls_larva20 _cls_inst = _cls_larva20._get_cls_larva20_inst();
_cls_inst.i2 = i2;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*access*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*access*/);
}
}
}