package larva;


import sniffer.*;
import larva.*;
import java.net.*;
import java.util.HashMap;

import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_cs_patterns1 implements _callable{
public static Channel resetAll = new Channel();
public static Channel ipchannel = new Channel();

public static HashMap<_cls_cs_patterns1,_cls_cs_patterns1> _cls_cs_patterns1_instances = new HashMap<_cls_cs_patterns1,_cls_cs_patterns1>();

_cls_cs_patterns0 parent;
public static Object ip1;
public InetAddress ip;
int no_automata = 1;
 public int pendingConns =0 ;
 public int openConns =0 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_cs_patterns1( InetAddress ip) {
parent = _cls_cs_patterns0._get_cls_cs_patterns0_inst();
this.ip = ip;
}

public static _cls_cs_patterns1 _get_cls_cs_patterns1_inst( InetAddress ip) { synchronized(_cls_cs_patterns1_instances){
_cls_cs_patterns1 _inst = new _cls_cs_patterns1( ip);
if (_cls_cs_patterns1_instances.containsKey(_inst))
{
_cls_cs_patterns1 tmp = _cls_cs_patterns1_instances.get(_inst);
 return _cls_cs_patterns1_instances.get(_inst);
}
else
{
 _cls_cs_patterns1_instances.put(_inst,_inst);
 return _inst;
}
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_cs_patterns1)
 && (ip == null || ip.equals(((_cls_cs_patterns1)o).ip))
 && (parent == null || parent.equals(((_cls_cs_patterns1)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_cs_patterns1_instances){
_performLogic_ipss(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){

_cls_cs_patterns2[] a = new _cls_cs_patterns2[1];
synchronized(_cls_cs_patterns2._cls_cs_patterns2_instances){
a = _cls_cs_patterns2._cls_cs_patterns2_instances.keySet().toArray(a);}
for (_cls_cs_patterns2 _inst : a)
if (_inst != null
 && (ip == null || ip.equals(_inst.parent.ip))){
_inst._call(_info, _event); 
_inst._call_all_filtered(_info, _event);
}
}

public static void _call_all(String _info, int... _event){

_cls_cs_patterns1[] a = new _cls_cs_patterns1[1];
synchronized(_cls_cs_patterns1_instances){
a = _cls_cs_patterns1_instances.keySet().toArray(a);}
for (_cls_cs_patterns1 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_cs_patterns1_instances){
_cls_cs_patterns1_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_ipss = 5;

public void _performLogic_ipss(String _info, int... _event) {

_cls_cs_patterns0.pw.println("AUTOMATON::> ipss("+ip + " " + ") STATE::>"+ _string_ipss(_state_id_ipss, 0));
_cls_cs_patterns0.pw.flush();

if (0==1){}
else if (_state_id_ipss==5){
		if (1==0){}
		else if ((_occurredEvent(_event,6/*ipEvent*/))){
		parent.ips ++;

		_state_id_ipss = 4;//moving to state normal
_cls_cs_patterns0.pw .println ("ips: "+parent.ips +" pendingConns: "+pendingConns );

		_goto_ipss(_info);
		}
}
else if (_state_id_ipss==4){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*attackEvent*/)) && (pendingConns >=(parent.pending /(double )parent.ips ))){
		parent.pending -=pendingConns ;
parent.open -=openConns ;
_cls_cs_patterns0.pw .println ("Problematic IP:"+ip +" pendingConns: "+pendingConns );
Main .blocker .blockIP (ip );
resetAll .send (ip );

		_state_id_ipss = 3;//moving to state ready
parent.ips --;
_killThis ();

		_goto_ipss(_info);
		}
		else if ((_occurredEvent(_event,6/*ipEvent*/)) && ((openConns +pendingConns )==0 )){
		
		_state_id_ipss = 3;//moving to state ready
parent.ips --;
_killThis ();

		_goto_ipss(_info);
		}
}
}

public void _goto_ipss(String _info){
_cls_cs_patterns0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_ipss(_state_id_ipss, 1));
_cls_cs_patterns0.pw.flush();
}

public String _string_ipss(int _state_id, int _mode){
switch(_state_id){
case 5: if (_mode == 0) return "start"; else return "start";
case 4: if (_mode == 0) return "normal"; else return "normal";
case 3: if (_mode == 0) return "ready"; else return "(((SYSTEM REACHED AN ACCEPTED STATE)))  ready";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}