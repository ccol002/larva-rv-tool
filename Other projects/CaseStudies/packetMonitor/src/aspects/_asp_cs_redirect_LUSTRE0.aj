package aspects;
import sniffer.*;
import larva.*;
public aspect _asp_cs_redirect_LUSTRE0 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_cs_redirect_LUSTRE0.initialize();
}
}
before () : (call(* *.receiveRedirectICMP(..)) && args(*) && !cflow(adviceexecution())) {
boolean redirect;
boolean _b;
_b =false ;
redirect =true ;

_cls_cs_redirect_LUSTRE0 _cls_inst = _cls_cs_redirect_LUSTRE0._get_cls_cs_redirect_LUSTRE0_inst();
_cls_cs_redirect_LUSTRE0.redirect = redirect;
_cls_cs_redirect_LUSTRE0._b = _b;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*periodicEvent*/);
}
before () : (call(* *.initialize(..)) && !cflow(adviceexecution())) {
boolean _b;
boolean redirect;
_b =true ;
redirect =true ;

_cls_cs_redirect_LUSTRE0 _cls_inst = _cls_cs_redirect_LUSTRE0._get_cls_cs_redirect_LUSTRE0_inst();
_cls_cs_redirect_LUSTRE0._b = _b;
_cls_cs_redirect_LUSTRE0.redirect = redirect;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*initializationEvent*/, 4/*periodicEvent*/);
}
before () : (call(* *.packetReceived(..)) && args(*,*) && !cflow(adviceexecution())) {
boolean _b;
boolean redirect;
_b =false ;
redirect =false ;

_cls_cs_redirect_LUSTRE0 _cls_inst = _cls_cs_redirect_LUSTRE0._get_cls_cs_redirect_LUSTRE0_inst();
_cls_cs_redirect_LUSTRE0._b = _b;
_cls_cs_redirect_LUSTRE0.redirect = redirect;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*event*/, 4/*periodicEvent*/);
}
}