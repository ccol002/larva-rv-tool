package aspects;

import larva.*;
import sniffer.*;
import java.net.*;

import larva.*;
public aspect _asp_cs_tcphandshake1 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_cs_tcphandshake1.initialize();
}
}
before ( int dst_port,InetAddress dst,InetAddress src,int src_port) : (call(* *.receiveSYN(..)) && args(src,dst,src_port,dst_port) && !cflow(adviceexecution())) {
InetAddress ip;
InetAddress ip2;
Integer port1;
Integer port2;
boolean rSYN;
boolean sSYNACK;
boolean rACK;
rSYN =true ;
sSYNACK =false ;
rACK =false ;
ip =src ;
ip2 =dst ;
port1 =src_port ;
port2 =dst_port ;

_cls_cs_tcphandshake1 _cls_inst = _cls_cs_tcphandshake1._get_cls_cs_tcphandshake1_inst( ip,ip2,port1,port2);
_cls_inst.dst_port = dst_port;
_cls_inst.dst = dst;
_cls_inst.src = src;
_cls_inst.src_port = src_port;
_cls_cs_tcphandshake1.rSYN = rSYN;
_cls_cs_tcphandshake1.sSYNACK = sSYNACK;
_cls_cs_tcphandshake1.rACK = rACK;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*receiveSYN*/, 8/*all*/);
}
before ( int dst_port,InetAddress dst,InetAddress src,int src_port) : (call(* *.sendSYNACK(..)) && args(src,dst,src_port,dst_port) && !cflow(adviceexecution())) {
InetAddress ip;
InetAddress ip2;
Integer port1;
Integer port2;
boolean rSYN;
boolean sSYNACK;
boolean rACK;
rSYN =false ;
sSYNACK =true ;
rACK =false ;
ip2 =src ;
ip =dst ;
port2 =src_port ;
port1 =dst_port ;

_cls_cs_tcphandshake1 _cls_inst = _cls_cs_tcphandshake1._get_cls_cs_tcphandshake1_inst( ip,ip2,port1,port2);
_cls_inst.dst_port = dst_port;
_cls_inst.dst = dst;
_cls_inst.src = src;
_cls_inst.src_port = src_port;
_cls_cs_tcphandshake1.rSYN = rSYN;
_cls_cs_tcphandshake1.sSYNACK = sSYNACK;
_cls_cs_tcphandshake1.rACK = rACK;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*sendSYNACK*/, 8/*all*/);
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c) && (if (millis == 1000)) && !cflow(adviceexecution())) {
InetAddress ip;
InetAddress ip2;
Integer port1;
Integer port2;
boolean rSYN;
boolean sSYNACK;
boolean rACK;
ip =null ;
ip2 =null ;
port1 =null ;
port2 =null ;
rSYN =false ;
sSYNACK =false ;
rACK =false ;

_cls_cs_tcphandshake1.rSYN = rSYN;
_cls_cs_tcphandshake1.sSYNACK = sSYNACK;
_cls_cs_tcphandshake1.rACK = rACK;
synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 6/*c4AT1*/, 8/*all*/);
}
}
}
before ( int dst_port,InetAddress dst,InetAddress src,int src_port) : (call(* *.receiveACK(..)) && args(src,dst,src_port,dst_port) && !cflow(adviceexecution())) {
InetAddress ip;
InetAddress ip2;
Integer port1;
Integer port2;
boolean rSYN;
boolean sSYNACK;
boolean rACK;
rSYN =false ;
sSYNACK =false ;
rACK =true ;
ip =src ;
ip2 =dst ;
port1 =src_port ;
port2 =dst_port ;

_cls_cs_tcphandshake1 _cls_inst = _cls_cs_tcphandshake1._get_cls_cs_tcphandshake1_inst( ip,ip2,port1,port2);
_cls_inst.dst_port = dst_port;
_cls_inst.dst = dst;
_cls_inst.src = src;
_cls_inst.src_port = src_port;
_cls_cs_tcphandshake1.rSYN = rSYN;
_cls_cs_tcphandshake1.sSYNACK = sSYNACK;
_cls_cs_tcphandshake1.rACK = rACK;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*receiveACK*/, 8/*all*/);
}
}