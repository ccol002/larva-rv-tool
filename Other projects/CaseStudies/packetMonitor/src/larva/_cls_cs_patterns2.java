package larva;


import sniffer.*;
import larva.*;
import java.net.*;
import java.util.HashMap;

import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_cs_patterns2 implements _callable{

public static HashMap<_cls_cs_patterns2,_cls_cs_patterns2> _cls_cs_patterns2_instances = new HashMap<_cls_cs_patterns2,_cls_cs_patterns2>();

_cls_cs_patterns1 parent;
public static int dst_port;
public static InetAddress dst;
public static InetAddress src;
public static int src_port;
public Integer port;
public Integer port2;
int no_automata = 1;
public Clock c = new Clock(this);

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_cs_patterns2( Integer port,Integer port2,InetAddress ip) {
parent = _cls_cs_patterns1._get_cls_cs_patterns1_inst( ip);
c.register(5000);
c.register(150000);
this.port = port;
this.port2 = port2;
}

public static _cls_cs_patterns2 _get_cls_cs_patterns2_inst( Integer port,Integer port2,InetAddress ip) { synchronized(_cls_cs_patterns2_instances){
_cls_cs_patterns2 _inst = new _cls_cs_patterns2( port,port2,ip);
if (_cls_cs_patterns2_instances.containsKey(_inst))
{
_cls_cs_patterns2 tmp = _cls_cs_patterns2_instances.get(_inst);
 return _cls_cs_patterns2_instances.get(_inst);
}
else
{
_inst.c.restart();
 _cls_cs_patterns2_instances.put(_inst,_inst);
 return _inst;
}
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_cs_patterns2)
 && (port == null || port.equals(((_cls_cs_patterns2)o).port))
 && (port2 == null || port2.equals(((_cls_cs_patterns2)o).port2))
 && (parent == null || parent.equals(((_cls_cs_patterns2)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_cs_patterns2_instances){
_performLogic_connections(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_cs_patterns2[] a = new _cls_cs_patterns2[1];
synchronized(_cls_cs_patterns2_instances){
a = _cls_cs_patterns2_instances.keySet().toArray(a);}
for (_cls_cs_patterns2 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_cs_patterns2_instances){
_cls_cs_patterns2_instances.remove(this);}
synchronized(c){
c.off();
c._inst = null;
c = null;}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_connections = 11;

public void _performLogic_connections(String _info, int... _event) {

_cls_cs_patterns0.pw.println("AUTOMATON::> connections("+port + " " + port2 + " " + ") STATE::>"+ _string_connections(_state_id_connections, 0));
_cls_cs_patterns0.pw.flush();

if (0==1){}
else if (_state_id_connections==11){
		if (1==0){}
		else if ((_occurredEvent(_event,10/*receiveSYN*/))){
		parent.parent.pending ++;
parent.pendingConns ++;
parent.parent.checkchannel .send ();
parent.ipchannel .send (parent.ip );
c .reset ();

		_state_id_connections = 8;//moving to state pending
		_goto_connections(_info);
		}
}
else if (_state_id_connections==10){
		if (1==0){}
		else if ((_occurredEvent(_event,24/*fin*/))){
		parent.parent.open --;
parent.openConns --;

		_state_id_connections = 7;//moving to state ended
_killThis ();

		_goto_connections(_info);
		}
		else if ((_occurredEvent(_event,22/*cAT0_150*/))){
		_cls_cs_patterns0.pw .println ("connection considered closed automatically");
parent.parent.open --;
parent.openConns --;

		_state_id_connections = 7;//moving to state ended
_killThis ();

		_goto_connections(_info);
		}
}
else if (_state_id_connections==8){
		if (1==0){}
		else if ((_occurredEvent(_event,20/*cAT0_1*/))){
		parent.parent.pending --;
parent.pendingConns --;
Main .blocker .reset (parent.ip ,port ,port2 );

		_state_id_connections = 6;//moving to state failed
_killThis ();

		_goto_connections(_info);
		}
		else if ((_occurredEvent(_event,8/*resetEvent*/))){
		parent.parent.pending --;
parent.pendingConns --;
Main .blocker .reset (parent.ip ,port ,port2 );

		_state_id_connections = 6;//moving to state failed
_killThis ();

		_goto_connections(_info);
		}
		else if ((_occurredEvent(_event,12/*sendSYNACK*/))){
		c .reset ();

		_state_id_connections = 9;//moving to state answered
		_goto_connections(_info);
		}
}
else if (_state_id_connections==9){
		if (1==0){}
		else if ((_occurredEvent(_event,14/*receiveACK*/))){
		parent.parent.open ++;
parent.parent.pending --;
parent.openConns ++;
parent.pendingConns --;

		_state_id_connections = 10;//moving to state connected
		_goto_connections(_info);
		}
		else if ((_occurredEvent(_event,20/*cAT0_1*/))){
		parent.parent.open --;
parent.openConns --;
Main .blocker .reset (parent.ip ,port ,port2 );

		_state_id_connections = 6;//moving to state failed
_killThis ();

		_goto_connections(_info);
		}
		else if ((_occurredEvent(_event,8/*resetEvent*/))){
		parent.parent.open --;
parent.openConns --;
Main .blocker .reset (parent.ip ,port ,port2 );

		_state_id_connections = 6;//moving to state failed
_killThis ();

		_goto_connections(_info);
		}
}
}

public void _goto_connections(String _info){
_cls_cs_patterns0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_connections(_state_id_connections, 1));
_cls_cs_patterns0.pw.flush();
}

public String _string_connections(int _state_id, int _mode){
switch(_state_id){
case 11: if (_mode == 0) return "start"; else return "start";
case 10: if (_mode == 0) return "connected"; else return "connected";
case 8: if (_mode == 0) return "pending"; else return "pending";
case 6: if (_mode == 0) return "failed"; else return "(((SYSTEM REACHED AN ACCEPTED STATE)))  failed";
case 7: if (_mode == 0) return "ended"; else return "(((SYSTEM REACHED AN ACCEPTED STATE)))  ended";
case 9: if (_mode == 0) return "answered"; else return "answered";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}