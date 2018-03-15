package larva;

public aspect _asp_clock0 {

before (long millis) : (call(* ClockEvent.event(long)) && args(millis) 
		&& !cflow(adviceexecution()) && if(millis==3000)) {

_cls_clock0 _cls_inst = _cls_clock0._get_cls_clock0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*clockC5*/);
}
before ( String from) : (call(* ChannelEvent.event(..)) && args(from) && !cflow(adviceexecution())) {

_cls_clock0 _cls_inst = _cls_clock0._get_cls_clock0_inst();
_cls_inst.from = from;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*channeld*/);
}

}