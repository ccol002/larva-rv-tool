package aspects;

import sniffer.*;
import larva.*;
import java.net.*;
import java.util.HashMap;

import larva.*;
public aspect _asp_cs_patterns0 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_cs_patterns0.initialize();
}
}
before ( Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_cs_patterns0.attackchannel))) && !cflow(adviceexecution())) {

_cls_cs_patterns0 _cls_inst = _cls_cs_patterns0._get_cls_cs_patterns0_inst();
_cls_cs_patterns0._call_all(thisJoinPoint.getSignature().toString(), 4/*attackEvent*/);
_cls_cs_patterns1._call_all(thisJoinPoint.getSignature().toString(), 4/*attackEvent*/);
_cls_cs_patterns2._call_all(thisJoinPoint.getSignature().toString(), 4/*attackEvent*/);
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c) && (if (millis == 4000)) && !cflow(adviceexecution())) {

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 2/*clockAT5*/);
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*clockAT5*/);
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*clockAT5*/);
}
}
}
before ( Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_cs_patterns0.checkchannel))) && !cflow(adviceexecution())) {

_cls_cs_patterns0 _cls_inst = _cls_cs_patterns0._get_cls_cs_patterns0_inst();
_cls_cs_patterns0._call_all(thisJoinPoint.getSignature().toString(), 0/*newRequest*/);
_cls_cs_patterns1._call_all(thisJoinPoint.getSignature().toString(), 0/*newRequest*/);
_cls_cs_patterns2._call_all(thisJoinPoint.getSignature().toString(), 0/*newRequest*/);
}
}