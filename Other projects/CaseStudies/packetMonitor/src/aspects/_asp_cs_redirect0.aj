package aspects;

import larva.*;
import sniffer.*;

import larva.*;
public aspect _asp_cs_redirect0 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_cs_redirect0.initialize();
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c) && (if (millis == 1000)) && !cflow(adviceexecution())) {
boolean redirectMSG;
redirectMSG =false ;

_cls_cs_redirect0.redirectMSG = redirectMSG;
_c._inst._call(thisJoinPoint.getSignature().toString(), 4/*c2AT1_0*/, 6/*all*/);
}
before () : (call(* *.receiveRedirectICMP(..)) && args(*) && !cflow(adviceexecution())) {
boolean redirectMSG;
redirectMSG =true ;

_cls_cs_redirect0 _cls_inst = _cls_cs_redirect0._get_cls_cs_redirect0_inst();
_cls_cs_redirect0.redirectMSG = redirectMSG;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*redirect*/, 6/*all*/);
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c) && (if (millis == 1000)) && !cflow(adviceexecution())) {
boolean redirectMSG;
redirectMSG =false ;

_cls_cs_redirect0.redirectMSG = redirectMSG;
synchronized(_c)
{
	if (_c != null && _c._inst != null)
_c._inst._call(thisJoinPoint.getSignature().toString(), 2/*c3AT1_0*/, 6/*all*/);
}
}
}