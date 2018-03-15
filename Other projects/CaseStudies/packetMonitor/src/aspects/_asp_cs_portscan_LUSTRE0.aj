package aspects;
import sniffer.*;
import larva.*;
public aspect _asp_cs_portscan_LUSTRE0 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_cs_portscan_LUSTRE0.initialize();
}
}
before () : (call(* *.initialize(..)) && !cflow(adviceexecution())) {
int port;
boolean _b;
boolean receive;
_b =true ;
receive =false ;
port =-1 ;

_cls_cs_portscan_LUSTRE0 _cls_inst = _cls_cs_portscan_LUSTRE0._get_cls_cs_portscan_LUSTRE0_inst();
_cls_cs_portscan_LUSTRE0.port = port;
_cls_cs_portscan_LUSTRE0._b = _b;
_cls_cs_portscan_LUSTRE0.receive = receive;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*initializationEvent*/, 4/*periodicEvent*/);
}
before ( int port) : (call(* *.packetReceived(..)) && args(*,port) && !cflow(adviceexecution())) {
boolean _b;
boolean receive;
_b =false ;
receive =true ;

_cls_cs_portscan_LUSTRE0 _cls_inst = _cls_cs_portscan_LUSTRE0._get_cls_cs_portscan_LUSTRE0_inst();
_cls_inst.port = port;
_cls_cs_portscan_LUSTRE0._b = _b;
_cls_cs_portscan_LUSTRE0.receive = receive;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*event*/, 4/*periodicEvent*/);
}
}