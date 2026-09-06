package aspects;


import larva.*;
public aspect _asp_channels0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_channels0.initialize();
}
}
before ( String from,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_channels0.ch))) && args(from)) {

synchronized(_asp_channels0.lock){

_cls_channels0 _cls_inst = _cls_channels0._get_cls_channels0_inst();
_cls_inst.from = from;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*notified*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*notified*/);
}
}
before () : (call(* *.increment(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_channels0.lock){

_cls_channels0 _cls_inst = _cls_channels0._get_cls_channels0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*increment*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*increment*/);
}
}
}