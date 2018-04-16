package aspects;

import larva.*;
public aspect _asp_channelexample1 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_channelexample1.initialize();
}
}
before ( User u1) : (call(* *.pressedOK(..)) && args(u1) && !cflow(adviceexecution())) {
User u;
u =u1 ;

_cls_channelexample1 _cls_inst = _cls_channelexample1._get_cls_channelexample1_inst( u);
_cls_inst.u1 = u1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*pressOK*/);
}
before ( User u1,Channel _c) : (call(* Channel.receive(String)) && target(_c) && (if (_c.equals(_cls_channelexample1.gl))) && args(u1) && !cflow(adviceexecution())) {
User u;
u =u1 ;

_cls_channelexample1 _cls_inst = _cls_channelexample1._get_cls_channelexample1_inst( u);
_cls_inst.u1 = u1;
_cls_channelexample1._call_all(thisJoinPoint.getSignature().toString(), 0/*goodlogin*/);
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*goodlogin*/);
}
before ( User u1) : (call(* *.loginTry(..)) && args(u1) && !cflow(adviceexecution())) {
User u;
u =u1 ;

_cls_channelexample1 _cls_inst = _cls_channelexample1._get_cls_channelexample1_inst( u);
_cls_inst.u1 = u1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*badlogin*/);
}
}