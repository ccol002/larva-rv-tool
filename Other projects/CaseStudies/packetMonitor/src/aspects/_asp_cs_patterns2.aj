package aspects;

import sniffer.*;
import larva.*;
import java.net.*;
import java.util.HashMap;

import larva.*;
public aspect _asp_cs_patterns2 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_cs_patterns2.initialize();
}
}
before ( int dst_port,InetAddress dst,InetAddress src,int src_port) : (call(* *.receiveFIN(..)) && args(src,dst,src_port,dst_port) && !cflow(adviceexecution())) {
Integer port;
Integer port2;
InetAddress ip;
ip =dst ;
port =dst_port ;
port2 =src_port ;

_cls_cs_patterns2 _cls_inst = _cls_cs_patterns2._get_cls_cs_patterns2_inst( port,port2,ip);
_cls_inst.dst_port = dst_port;
_cls_inst.dst = dst;
_cls_inst.src = src;
_cls_inst.src_port = src_port;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 18/*sendFIN*/, 24/*fin*/);
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c) && (if (millis == 5000)) && !cflow(adviceexecution())) {
Integer port;
Integer port2;
InetAddress ip;
port =null ;
port2 =null ;
ip =null ;

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 20/*cAT0_1*/);
}
}
}
before ( int dst_port,InetAddress dst,InetAddress src,int src_port) : (call(* *.receiveSYN(..)) && args(src,dst,src_port,dst_port) && !cflow(adviceexecution())) {
Integer port;
Integer port2;
InetAddress ip;
ip =src ;
port =src_port ;
port2 =dst_port ;

_cls_cs_patterns2 _cls_inst = _cls_cs_patterns2._get_cls_cs_patterns2_inst( port,port2,ip);
_cls_inst.dst_port = dst_port;
_cls_inst.dst = dst;
_cls_inst.src = src;
_cls_inst.src_port = src_port;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 10/*receiveSYN*/);
}
before ( int dst_port,InetAddress dst,InetAddress src,int src_port) : (call(* *.receiveFIN(..)) && args(src,dst,src_port,dst_port) && !cflow(adviceexecution())) {
Integer port;
Integer port2;
InetAddress ip;
ip =src ;
port =src_port ;
port2 =dst_port ;

_cls_cs_patterns2 _cls_inst = _cls_cs_patterns2._get_cls_cs_patterns2_inst( port,port2,ip);
_cls_inst.dst_port = dst_port;
_cls_inst.dst = dst;
_cls_inst.src = src;
_cls_inst.src_port = src_port;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 16/*receiveFIN*/, 24/*fin*/);
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c) && (if (millis == 150000)) && !cflow(adviceexecution())) {
Integer port;
Integer port2;
InetAddress ip;
port =null ;
port2 =null ;
ip =null ;

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 22/*cAT0_150*/);
}
}
}
before ( int dst_port,InetAddress dst,InetAddress src,int src_port) : (call(* *.sendSYNACK(..)) && args(src,dst,src_port,dst_port) && !cflow(adviceexecution())) {
Integer port;
Integer port2;
InetAddress ip;
ip =dst ;
port =dst_port ;
port2 =src_port ;

_cls_cs_patterns2 _cls_inst = _cls_cs_patterns2._get_cls_cs_patterns2_inst( port,port2,ip);
_cls_inst.dst_port = dst_port;
_cls_inst.dst = dst;
_cls_inst.src = src;
_cls_inst.src_port = src_port;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 12/*sendSYNACK*/);
}
before ( int dst_port,InetAddress dst,InetAddress src,int src_port) : (call(* *.receiveACK(..)) && args(src,dst,src_port,dst_port) && !cflow(adviceexecution())) {
Integer port;
Integer port2;
InetAddress ip;
ip =src ;
port =src_port ;
port2 =dst_port ;

_cls_cs_patterns2 _cls_inst = _cls_cs_patterns2._get_cls_cs_patterns2_inst( port,port2,ip);
_cls_inst.dst_port = dst_port;
_cls_inst.dst = dst;
_cls_inst.src = src;
_cls_inst.src_port = src_port;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 14/*receiveACK*/);
}
}