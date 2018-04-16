package larva;


import larva.*;
import java.net.InetAddress;

import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_cs_portscan1 implements _callable{
public static Channel distinctPort = new Channel();

public static HashMap<_cls_cs_portscan1,_cls_cs_portscan1> _cls_cs_portscan1_instances = new HashMap<_cls_cs_portscan1,_cls_cs_portscan1>();

_cls_cs_portscan0 parent;
public static int port;
public static Object ip2;
public static InetAddress ip1;
public static boolean rPckt;
public InetAddress ip;
int no_automata = 2;
 public int port3 =-1 ;
 public int port2 =-1 ;
 public int port1 =-1 ;
public Clock c2 = new Clock(this);
public Clock c3 = new Clock(this);

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_cs_portscan1( InetAddress ip) {
parent = _cls_cs_portscan0._get_cls_cs_portscan0_inst();
c2.register(5000);
c3.register(5000);
this.ip = ip;
}

public static _cls_cs_portscan1 _get_cls_cs_portscan1_inst( InetAddress ip) {
_cls_cs_portscan1 _inst = new _cls_cs_portscan1( ip);
if (_cls_cs_portscan1_instances.containsKey(_inst))
{
_cls_cs_portscan1 tmp = _cls_cs_portscan1_instances.get(_inst);
 return _cls_cs_portscan1_instances.get(_inst);
}
else
{
_inst.c2.restart();
_inst.c3.restart();_inst._performLogic_ceform("event on creation", 8);
 _cls_cs_portscan1_instances.put(_inst,_inst);
 return _inst;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_cs_portscan1)
 && (ip == null || ip.equals(((_cls_cs_portscan1)o).ip))
 && (parent == null || parent.equals(((_cls_cs_portscan1)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
_performLogic_ports(_info, _event);
_performLogic_ceform(_info, _event);
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){
	_cls_cs_portscan1[] a = new _cls_cs_portscan1[1];
for (_cls_cs_portscan1 _inst : _cls_cs_portscan1_instances.keySet().toArray(a))
_inst._call(_info, _event);
}

public void _killThis(){
if (--no_automata <= 0){
synchronized(c2){
c2.off();
c2._inst = null;
c2 = null;}
synchronized(c3){
c3.off();
c3._inst = null;
c3 = null;}
 _cls_cs_portscan1_instances.remove(this);
}
}

int _state_id_ports = 2;

public void _performLogic_ports(String _info, int... _event) {

_cls_cs_portscan0.pw.println("AUTOMATON::> ports("+ip + " " + ") STATE::>"+ _string_ports(_state_id_ports, 0));
_cls_cs_portscan0.pw.flush();

if (0==1){}
else if (_state_id_ports==0){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*receive*/)) && (port1 !=port &&port2 !=port &&port3 !=port )){
		port2 =port ;
distinctPort .send (ip );

		_state_id_ports = 1;//moving to state normal2
		_goto_ports(_info);
		}
}
else if (_state_id_ports==1){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*receive*/)) && (port1 !=port &&port2 !=port &&port3 !=port )){
		port3 =port ;
distinctPort .send (ip );

		_state_id_ports = 2;//moving to state start
		_goto_ports(_info);
		}
}
else if (_state_id_ports==2){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*receive*/)) && (port1 !=port &&port2 !=port &&port3 !=port )){
		port1 =port ;
distinctPort .send (ip );

		_state_id_ports = 0;//moving to state normal1
		_goto_ports(_info);
		}
}
}

public void _goto_ports(String _info){
_cls_cs_portscan0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_ports(_state_id_ports, 1));
_cls_cs_portscan0.pw.flush();
}

public String _string_ports(int _state_id, int _mode){
switch(_state_id){
case 0: if (_mode == 0) return "normal1"; else return "normal1";
case 1: if (_mode == 0) return "normal2"; else return "normal2";
case 2: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_ceform = 12;

public void _performLogic_ceform(String _info, int... _event) {

_cls_cs_portscan0.pw.println("AUTOMATON::> ceform("+ip + " " + ") STATE::>"+ _string_ceform(_state_id_ceform, 0));
_cls_cs_portscan0.pw.flush();

if (0==1){}
else if (_state_id_ceform==10){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!((c2 .compareToMillis ((long )5000 )<0 ||rPckt ))&&!((c2 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		
		_state_id_ceform = 8;//moving to state loc0i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!((c2 .compareToMillis ((long )5000 )<0 ||rPckt ))&&(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		c3 .reset ();

		_state_id_ceform = 9;//moving to state loc0i2i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&!(rPckt )&&!((c2 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		
		_state_id_ceform = 10;//moving to state loc0i1i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&rPckt &&!((c2 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		c2 .reset ();

		_state_id_ceform = 10;//moving to state loc0i1i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&!(rPckt )&&(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		c3 .reset ();

		_state_id_ceform = 11;//moving to state loc0i1i2i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&rPckt &&(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		c2 .reset ();
c3 .reset ();

		_state_id_ceform = 11;//moving to state loc0i1i2i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/))){
		
		_state_id_ceform = 3;//moving to state unhandled
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==9){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!(rPckt )&&!(c3 .compareToMillis ((long )5000 )<0 )&&!((c3 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		
		_state_id_ceform = 8;//moving to state loc0i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!(rPckt )&&!(c3 .compareToMillis ((long )5000 )<0 )&&(c3 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		
		_state_id_ceform = 4;//moving to state loc0i3i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!(rPckt )&&c3 .compareToMillis ((long )5000 )<0 &&!((c3 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		
		_state_id_ceform = 9;//moving to state loc0i2i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!(rPckt )&&c3 .compareToMillis ((long )5000 )<0 &&(c3 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		
		_state_id_ceform = 5;//moving to state loc0i2i3i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((rPckt &&!(c3 .compareToMillis ((long )5000 )<0 )&&!((c3 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		c2 .reset ();

		_state_id_ceform = 10;//moving to state loc0i1i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((rPckt &&!(c3 .compareToMillis ((long )5000 )<0 )&&(c3 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		c2 .reset ();

		_state_id_ceform = 6;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((rPckt &&c3 .compareToMillis ((long )5000 )<0 &&!((c3 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		c2 .reset ();

		_state_id_ceform = 11;//moving to state loc0i1i2i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((rPckt &&c3 .compareToMillis ((long )5000 )<0 &&(c3 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		c2 .reset ();

		_state_id_ceform = 7;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/))){
		
		_state_id_ceform = 3;//moving to state unhandled
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==7){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!((c2 .compareToMillis ((long )5000 )<0 ||rPckt ))&&!((c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))))){
		
		_state_id_ceform = 4;//moving to state loc0i3i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!((c2 .compareToMillis ((long )5000 )<0 ||rPckt ))&&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&!((c2 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		
		_state_id_ceform = 5;//moving to state loc0i2i3i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!((c2 .compareToMillis ((long )5000 )<0 ||rPckt ))&&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		c3 .reset ();

		_state_id_ceform = 5;//moving to state loc0i2i3i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&!(rPckt )&&!((c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))))){
		
		_state_id_ceform = 6;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&rPckt &&!((c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))))){
		c2 .reset ();

		_state_id_ceform = 6;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&!(rPckt )&&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&!((c2 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		
		_state_id_ceform = 7;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&!(rPckt )&&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		c3 .reset ();

		_state_id_ceform = 7;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&rPckt &&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&!((c2 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		c2 .reset ();

		_state_id_ceform = 7;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&rPckt &&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		c2 .reset ();
c3 .reset ();

		_state_id_ceform = 7;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/))){
		
		_state_id_ceform = 3;//moving to state unhandled
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==11){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!((c2 .compareToMillis ((long )5000 )<0 ||rPckt ))&&!((c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))&&!((c3 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		
		_state_id_ceform = 8;//moving to state loc0i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!((c2 .compareToMillis ((long )5000 )<0 ||rPckt ))&&!((c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))&&(c3 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		
		_state_id_ceform = 4;//moving to state loc0i3i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!((c2 .compareToMillis ((long )5000 )<0 ||rPckt ))&&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&!((c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&!((c3 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		
		_state_id_ceform = 9;//moving to state loc0i2i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!((c2 .compareToMillis ((long )5000 )<0 ||rPckt ))&&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&(c2 .compareToMillis ((long )5000 )<0 &&rPckt )&&!((c3 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		c3 .reset ();

		_state_id_ceform = 9;//moving to state loc0i2i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!((c2 .compareToMillis ((long )5000 )<0 ||rPckt ))&&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&!((c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&(c3 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		
		_state_id_ceform = 5;//moving to state loc0i2i3i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!((c2 .compareToMillis ((long )5000 )<0 ||rPckt ))&&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&(c2 .compareToMillis ((long )5000 )<0 &&rPckt )&&(c3 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		c3 .reset ();

		_state_id_ceform = 5;//moving to state loc0i2i3i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&!(rPckt )&&!((c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))&&!((c3 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		
		_state_id_ceform = 10;//moving to state loc0i1i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&rPckt &&!((c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))&&!((c3 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		c2 .reset ();

		_state_id_ceform = 10;//moving to state loc0i1i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&!(rPckt )&&!((c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))&&(c3 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		
		_state_id_ceform = 6;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&rPckt &&!((c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))&&(c3 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		c2 .reset ();

		_state_id_ceform = 6;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&!(rPckt )&&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&!((c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&!((c3 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		
		_state_id_ceform = 11;//moving to state loc0i1i2i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&!(rPckt )&&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&(c2 .compareToMillis ((long )5000 )<0 &&rPckt )&&!((c3 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		c3 .reset ();

		_state_id_ceform = 11;//moving to state loc0i1i2i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&rPckt &&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&!((c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&!((c3 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		c2 .reset ();

		_state_id_ceform = 11;//moving to state loc0i1i2i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&rPckt &&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&(c2 .compareToMillis ((long )5000 )<0 &&rPckt )&&!((c3 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		c2 .reset ();
c3 .reset ();

		_state_id_ceform = 11;//moving to state loc0i1i2i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&!(rPckt )&&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&!((c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&(c3 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		
		_state_id_ceform = 7;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&!(rPckt )&&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&(c2 .compareToMillis ((long )5000 )<0 &&rPckt )&&(c3 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		c3 .reset ();

		_state_id_ceform = 7;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&rPckt &&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&!((c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&(c3 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		c2 .reset ();

		_state_id_ceform = 7;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&rPckt &&(c3 .compareToMillis ((long )5000 )<0 ||(c2 .compareToMillis ((long )5000 )<0 &&rPckt ))&&(c2 .compareToMillis ((long )5000 )<0 &&rPckt )&&(c3 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		c2 .reset ();
c3 .reset ();

		_state_id_ceform = 7;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/))){
		
		_state_id_ceform = 3;//moving to state unhandled
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==8){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all*/)) && (!(rPckt ))){
		
		_state_id_ceform = 8;//moving to state loc0i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (rPckt )){
		c2 .reset ();

		_state_id_ceform = 10;//moving to state loc0i1i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/))){
		
		_state_id_ceform = 3;//moving to state unhandled
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==12){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all*/)) && (true )){
		
		_state_id_ceform = 8;//moving to state loc0i
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==5){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!(rPckt )&&!(c3 .compareToMillis ((long )5000 )<0 )))){
		
		_state_id_ceform = 4;//moving to state loc0i3i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!(rPckt )&&c3 .compareToMillis ((long )5000 )<0 ))){
		
		_state_id_ceform = 5;//moving to state loc0i2i3i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((rPckt &&!(c3 .compareToMillis ((long )5000 )<0 )))){
		c2 .reset ();

		_state_id_ceform = 6;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((rPckt &&c3 .compareToMillis ((long )5000 )<0 ))){
		c2 .reset ();

		_state_id_ceform = 7;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/))){
		
		_state_id_ceform = 3;//moving to state unhandled
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==6){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!((c2 .compareToMillis ((long )5000 )<0 ||rPckt ))&&!((c2 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		
		_state_id_ceform = 4;//moving to state loc0i3i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && ((!((c2 .compareToMillis ((long )5000 )<0 ||rPckt ))&&(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		c3 .reset ();

		_state_id_ceform = 5;//moving to state loc0i2i3i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&!(rPckt )&&!((c2 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		
		_state_id_ceform = 6;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&rPckt &&!((c2 .compareToMillis ((long )5000 )<0 &&rPckt ))))){
		c2 .reset ();

		_state_id_ceform = 6;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&!(rPckt )&&(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		c3 .reset ();

		_state_id_ceform = 7;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (((c2 .compareToMillis ((long )5000 )<0 ||rPckt )&&rPckt &&(c2 .compareToMillis ((long )5000 )<0 &&rPckt )))){
		c2 .reset ();
c3 .reset ();

		_state_id_ceform = 7;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/))){
		
		_state_id_ceform = 3;//moving to state unhandled
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==4){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all*/)) && (!(rPckt ))){
		
		_state_id_ceform = 4;//moving to state loc0i3i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/)) && (rPckt )){
		c2 .reset ();

		_state_id_ceform = 6;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,8/*all*/))){
		
		_state_id_ceform = 3;//moving to state unhandled
		_goto_ceform(_info);
		}
}
}

public void _goto_ceform(String _info){
_cls_cs_portscan0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_ceform(_state_id_ceform, 1));
_cls_cs_portscan0.pw.flush();
}

public String _string_ceform(int _state_id, int _mode){
switch(_state_id){
case 10: if (_mode == 0) return "loc0i1i1l"; else return "loc0i1i1l";
case 9: if (_mode == 0) return "loc0i2i2l"; else return "loc0i2i2l";
case 7: if (_mode == 0) return "loc0i1i2i3i1l2l"; else return "!!!SYSTEM REACHED BAD STATE!!! loc0i1i2i3i1l2l "+new _BadStateExceptioncs_portscan().toString()+" ";
case 11: if (_mode == 0) return "loc0i1i2i1l2l"; else return "loc0i1i2i1l2l";
case 8: if (_mode == 0) return "loc0i"; else return "loc0i";
case 12: if (_mode == 0) return "start"; else return "start";
case 5: if (_mode == 0) return "loc0i2i3i2l"; else return "!!!SYSTEM REACHED BAD STATE!!! loc0i2i3i2l "+new _BadStateExceptioncs_portscan().toString()+" ";
case 3: if (_mode == 0) return "unhandled"; else return "!!!SYSTEM REACHED BAD STATE!!! unhandled "+new _BadStateExceptioncs_portscan().toString()+" ";
case 6: if (_mode == 0) return "loc0i1i3i1l"; else return "!!!SYSTEM REACHED BAD STATE!!! loc0i1i3i1l "+new _BadStateExceptioncs_portscan().toString()+" ";
case 4: if (_mode == 0) return "loc0i3i"; else return "!!!SYSTEM REACHED BAD STATE!!! loc0i3i "+new _BadStateExceptioncs_portscan().toString()+" ";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}