package aspects;

import sniffer.*;
import larva.*;
import java.net.*;
import java.util.HashMap;

import larva.*;
public aspect _asp_cs_patterns1 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_cs_patterns1.initialize();
}
}
before ( Object ip1,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_cs_patterns1.ipchannel))) && args(ip1) && !cflow(adviceexecution())) {
InetAddress ip;
ip =(InetAddress )ip1 ;

_cls_cs_patterns1 _cls_inst = _cls_cs_patterns1._get_cls_cs_patterns1_inst( ip);
_cls_inst.ip1 = ip1;
_cls_cs_patterns1._call_all(thisJoinPoint.getSignature().toString(), 6/*ipEvent*/);
_cls_cs_patterns2._call_all(thisJoinPoint.getSignature().toString(), 6/*ipEvent*/);
}
before ( Object ip1,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_cs_patterns1.resetAll))) && args(ip1) && !cflow(adviceexecution())) {
InetAddress ip;
ip =(InetAddress )ip1 ;

_cls_cs_patterns1 _cls_inst = _cls_cs_patterns1._get_cls_cs_patterns1_inst( ip);
_cls_inst.ip1 = ip1;
_cls_cs_patterns1._call_all(thisJoinPoint.getSignature().toString(), 8/*resetEvent*/);
_cls_cs_patterns2._call_all(thisJoinPoint.getSignature().toString(), 8/*resetEvent*/);
}
}