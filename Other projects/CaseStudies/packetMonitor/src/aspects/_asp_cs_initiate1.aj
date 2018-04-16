package aspects;

import sniffer.*;
import java.net.InetAddress;

import larva.*;
public aspect _asp_cs_initiate1 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_cs_initiate1.initialize();
}
}
before ( int dst_port,InetAddress dst,InetAddress src,int src_port) : (call(* *.sendSYN(..)) && args(src,dst,src_port,dst_port) && !cflow(adviceexecution())) {
InetAddress ip;
InetAddress ip2;
Integer port1;
Integer port2;
boolean rSYN;
boolean sSYN;
rSYN =false ;
sSYN =true ;
ip =src ;
ip2 =dst ;
port1 =src_port ;
port2 =dst_port ;

_cls_cs_initiate1 _cls_inst = _cls_cs_initiate1._get_cls_cs_initiate1_inst( ip,ip2,port1,port2);
_cls_inst.dst_port = dst_port;
_cls_inst.dst = dst;
_cls_inst.src = src;
_cls_inst.src_port = src_port;
_cls_cs_initiate1.rSYN = rSYN;
_cls_cs_initiate1.sSYN = sSYN;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*sendSYN*/, 4/*all*/);
}
before ( int dst_port,InetAddress dst,InetAddress src,int src_port) : (call(* *.receiveSYN(..)) && args(src,dst,src_port,dst_port) && !cflow(adviceexecution())) {
InetAddress ip;
InetAddress ip2;
Integer port1;
Integer port2;
boolean rSYN;
boolean sSYN;
rSYN =true ;
sSYN =false ;
ip =src ;
ip2 =dst ;
port1 =src_port ;
port2 =dst_port ;

_cls_cs_initiate1 _cls_inst = _cls_cs_initiate1._get_cls_cs_initiate1_inst( ip,ip2,port1,port2);
_cls_inst.dst_port = dst_port;
_cls_inst.dst = dst;
_cls_inst.src = src;
_cls_inst.src_port = src_port;
_cls_cs_initiate1.rSYN = rSYN;
_cls_cs_initiate1.sSYN = sSYN;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*receiveSYN*/, 4/*all*/);
}
}