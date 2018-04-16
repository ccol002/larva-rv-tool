package larva;


import larva.*;
import sniffer.*;
import java.net.*;

import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_cs_tcphandshake1 implements _callable{

public static HashMap<_cls_cs_tcphandshake1,_cls_cs_tcphandshake1> _cls_cs_tcphandshake1_instances = new HashMap<_cls_cs_tcphandshake1,_cls_cs_tcphandshake1>();

_cls_cs_tcphandshake0 parent;
public static boolean rSYN;
public static int dst_port;
public static boolean sSYNACK;
public static InetAddress dst;
public static InetAddress src;
public static int src_port;
public static boolean rACK;
public InetAddress ip;
public InetAddress ip2;
public Integer port1;
public Integer port2;
int no_automata = 1;
public Clock c4 = new Clock(this);

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_cs_tcphandshake1( InetAddress ip,InetAddress ip2,Integer port1,Integer port2) {
parent = _cls_cs_tcphandshake0._get_cls_cs_tcphandshake0_inst();
c4.register(1000);
this.ip = ip;
this.ip2 = ip2;
this.port1 = port1;
this.port2 = port2;
}

public static _cls_cs_tcphandshake1 _get_cls_cs_tcphandshake1_inst( InetAddress ip,InetAddress ip2,Integer port1,Integer port2) {
_cls_cs_tcphandshake1 _inst = new _cls_cs_tcphandshake1( ip,ip2,port1,port2);
if (_cls_cs_tcphandshake1_instances.containsKey(_inst))
{
_cls_cs_tcphandshake1 tmp = _cls_cs_tcphandshake1_instances.get(_inst);
 return _cls_cs_tcphandshake1_instances.get(_inst);
}
else
{
_inst.c4.restart();
_inst._performLogic_ceform("event on creation", 8);
 _cls_cs_tcphandshake1_instances.put(_inst,_inst);
 return _inst;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_cs_tcphandshake1)
 && (ip == null || ip.equals(((_cls_cs_tcphandshake1)o).ip))
 && (ip2 == null || ip2.equals(((_cls_cs_tcphandshake1)o).ip2))
 && (port1 == null || port1.equals(((_cls_cs_tcphandshake1)o).port1))
 && (port2 == null || port2.equals(((_cls_cs_tcphandshake1)o).port2))
 && (parent == null || parent.equals(((_cls_cs_tcphandshake1)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
_performLogic_ceform(_info, _event);
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

for (_cls_cs_tcphandshake1 _inst : _cls_cs_tcphandshake1_instances.keySet())
_inst._call(_info, _event);
}

public void _killThis(){
if (--no_automata <= 0){
synchronized(c4){
c4.off();
c4._inst = null;
c4 = null;}
synchronized(_cls_cs_tcphandshake1_instances){
 _cls_cs_tcphandshake1_instances.remove(this);}
}
}

int _state_id_ceform = 5;

public void _performLogic_ceform(String _info, int... _event) {

_cls_cs_tcphandshake0.pw.println("AUTOMATON::> ceform("+ip + " " + ip2 + " " + port1 + " " + port2 + " " + ") STATE::>"+ _string_ceform(_state_id_ceform, 0));
_cls_cs_tcphandshake0.pw.flush();

if (0==1){}
else if (_state_id_ceform==2){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all*/)) && (!(rSYN ))){
		
		_state_id_ceform = 2;//moving to state loc0i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (rSYN )){
		
		_state_id_ceform = 3;//moving to state loc0i1i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/))){
		
		_state_id_ceform = 0;//moving to state unhandled
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==4){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all*/)) && (!(rACK ))){
		
		_state_id_ceform = 4;//moving to state loc0i1i2i3i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (rACK )){
		
		_state_id_ceform = 1;//moving to state loc0i1i2i3i4i
_cls_cs_retries1.chSuccess.send (ip );
_killThis ();

		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/))){
		
		_state_id_ceform = 0;//moving to state unhandled
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==3){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all*/)) && (!(sSYNACK ))){
		
		_state_id_ceform = 3;//moving to state loc0i1i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (sSYNACK )){
		
		_state_id_ceform = 4;//moving to state loc0i1i2i3i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/))){
		
		_state_id_ceform = 0;//moving to state unhandled
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==5){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all*/)) && (true )){
		
		_state_id_ceform = 2;//moving to state loc0i
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==1){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all*/)) && (true )){
		
		_state_id_ceform = 1;//moving to state loc0i1i2i3i4i
_cls_cs_retries1.chSuccess.send (ip );
_killThis ();

		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/))){
		
		_state_id_ceform = 0;//moving to state unhandled
		_goto_ceform(_info);
		}
}
}

public void _goto_ceform(String _info){
_cls_cs_tcphandshake0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_ceform(_state_id_ceform, 1));
_cls_cs_tcphandshake0.pw.flush();
}

public String _string_ceform(int _state_id, int _mode){
switch(_state_id){
case 2: if (_mode == 0) return "loc0i"; else return "loc0i";
case 5: if (_mode == 0) return "start"; else return "start";
case 4: if (_mode == 0) return "loc0i1i2i3i"; else return "loc0i1i2i3i";
case 3: if (_mode == 0) return "loc0i1i"; else return "loc0i1i";
case 0: if (_mode == 0) return "unhandled"; else return "!!!SYSTEM REACHED BAD STATE!!! unhandled "+new _BadStateExceptioncs_tcphandshake().toString()+" ";
case 1: if (_mode == 0) return "loc0i1i2i3i4i"; else return "!!!SYSTEM REACHED BAD STATE!!! loc0i1i2i3i4i "+new _BadStateExceptioncs_tcphandshake().toString()+" ";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}