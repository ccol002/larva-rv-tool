package larva;

import sniffer.*;
import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_cs_redirect_LUSTRE0 implements _callable{

public static PrintWriter pw; 
public static _cls_cs_redirect_LUSTRE0 root;

public static HashMap<_cls_cs_redirect_LUSTRE0,_cls_cs_redirect_LUSTRE0> _cls_cs_redirect_LUSTRE0_instances = new HashMap<_cls_cs_redirect_LUSTRE0,_cls_cs_redirect_LUSTRE0>();
static{
try{
pw = new PrintWriter("C:\\Users\\University User\\Desktop\\projects\\packetMonitor\\src\\\\output_cs_redirect_LUSTRE.txt");

root = new _cls_cs_redirect_LUSTRE0();
_cls_cs_redirect_LUSTRE0_instances.put(root, root);
root._clock.restart();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_cs_redirect_LUSTRE0 parent; //to remain null - this class does not have a parent!
public static boolean redirect;
public static boolean _b;
int no_automata = 1;
 public long pre_3 ;
 public long pre_2 ;
 public long _rt_clock ;
 public long pre_1 ;
 public long time ;
 public boolean pre_0 ;
 public long cnt ;
public Clock _clock = new Clock(this);
 public boolean violated ;
 public boolean pre_6 ;
 public long pre_5 ;
 public long pre_4 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_cs_redirect_LUSTRE0() {
}

public static _cls_cs_redirect_LUSTRE0 _get_cls_cs_redirect_LUSTRE0_inst() { synchronized(_cls_cs_redirect_LUSTRE0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_cs_redirect_LUSTRE0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_cs_redirect_LUSTRE0_instances){
_performLogic_redirectMSG(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_cs_redirect_LUSTRE0[] a = new _cls_cs_redirect_LUSTRE0[1];
synchronized(_cls_cs_redirect_LUSTRE0_instances){
a = _cls_cs_redirect_LUSTRE0_instances.keySet().toArray(a);}
for (_cls_cs_redirect_LUSTRE0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_cs_redirect_LUSTRE0_instances){
_cls_cs_redirect_LUSTRE0_instances.remove(this);}
synchronized(_clock){
_clock.off();
_clock._inst = null;
_clock = null;}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_redirectMSG = 1;

public void _performLogic_redirectMSG(String _info, int... _event) {

_cls_cs_redirect_LUSTRE0.pw.println("AUTOMATON::> redirectMSG("+") STATE::>"+ _string_redirectMSG(_state_id_redirectMSG, 0));
_cls_cs_redirect_LUSTRE0.pw.flush();

if (0==1){}
else if (_state_id_redirectMSG==1){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*initializationEvent*/))){
		pre_0 =false ;
pre_1 =0 ;
pre_2 =0 ;
pre_3 =0 ;
pre_4 =0 ;
pre_5 =0 ;
pre_6 =false ;

		_state_id_redirectMSG = 0;//moving to state lustre
		_goto_redirectMSG(_info);
		}
}
else if (_state_id_redirectMSG==0){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*periodicEvent*/))){
		_rt_clock =_clock .current_long ();
time =(pre_0 )?(pre_1 ):((pre_2 ==0 )?(_rt_clock ):(pre_3 ));
cnt =(redirect &&_rt_clock -time <2000 )?(pre_4 +1 ):((_rt_clock -time <2000 )?(pre_5 ):((redirect )?(1 ):(0 )));
violated =(cnt >=3 )?(true ):(pre_6 );
pre_0 =redirect ;
pre_1 =_rt_clock ;
pre_2 =cnt ;
pre_3 =time ;
pre_4 =cnt ;
pre_5 =cnt ;
pre_6 =violated ;
_cls_cs_redirect_LUSTRE0.pw .println (" _rt_clock: "+_rt_clock +" "+" redirect: "+redirect +" "+" output: "+violated +" ");

		_state_id_redirectMSG = 0;//moving to state lustre
		_goto_redirectMSG(_info);
		}
}
}

public void _goto_redirectMSG(String _info){
_cls_cs_redirect_LUSTRE0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_redirectMSG(_state_id_redirectMSG, 1));
_cls_cs_redirect_LUSTRE0.pw.flush();
}

public String _string_redirectMSG(int _state_id, int _mode){
switch(_state_id){
case 1: if (_mode == 0) return "initialization"; else return "initialization";
case 0: if (_mode == 0) return "lustre"; else return "lustre";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}