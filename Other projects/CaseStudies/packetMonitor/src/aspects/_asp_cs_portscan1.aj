package aspects;

import larva.*;
import java.net.InetAddress;

import larva.*;
public aspect _asp_cs_portscan1 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_cs_portscan1.initialize();
}
}
before ( int port,InetAddress ip1) : (call(* *.packetReceived(..)) && args(ip1,port) && !cflow(adviceexecution())) {
InetAddress ip;
ip =ip1 ;

_cls_cs_portscan1 _cls_inst = _cls_cs_portscan1._get_cls_cs_portscan1_inst( ip);
_cls_inst.port = port;
_cls_inst.ip1 = ip1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*receive*/);
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c) && (if (millis == 5000)) && !cflow(adviceexecution())) {
InetAddress ip;
boolean rPckt;
ip =null ;
rPckt =false ;

_cls_cs_portscan1.rPckt = rPckt;
synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 4/*c3AT0_01*/, 8/*all*/);
}
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c) && (if (millis == 5000)) && !cflow(adviceexecution())) {
InetAddress ip;
boolean rPckt;
ip =null ;
rPckt =false ;

_cls_cs_portscan1.rPckt = rPckt;
synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 6/*c2AT0_01*/, 8/*all*/);
}
}
}
before ( Object ip2,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_cs_portscan1.distinctPort))) && args(ip2) && !cflow(adviceexecution())) {
InetAddress ip;
boolean rPckt;
rPckt =true ;
ip =(InetAddress )ip2 ;

_cls_cs_portscan1 _cls_inst = _cls_cs_portscan1._get_cls_cs_portscan1_inst( ip);
_cls_inst.ip2 = ip2;
_cls_cs_portscan1.rPckt = rPckt;
_cls_cs_portscan1._call_all(thisJoinPoint.getSignature().toString(), 2/*distinct*/, 8/*all*/);
}
}