package larva;

import sniffer.*;
import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_cs_portscan_LUSTRE0 implements _callable{

public static PrintWriter pw; 
public static _cls_cs_portscan_LUSTRE0 root;

public static HashMap<_cls_cs_portscan_LUSTRE0,_cls_cs_portscan_LUSTRE0> _cls_cs_portscan_LUSTRE0_instances = new HashMap<_cls_cs_portscan_LUSTRE0,_cls_cs_portscan_LUSTRE0>();
static{
try{
pw = new PrintWriter("C:\\Users\\University User\\Desktop\\projects\\packetMonitor\\src\\\\output_cs_portscan_LUSTRE.txt");

root = new _cls_cs_portscan_LUSTRE0();
_cls_cs_portscan_LUSTRE0_instances.put(root, root);
root._clock.restart();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_cs_portscan_LUSTRE0 parent; //to remain null - this class does not have a parent!
public static int port;
public static boolean _b;
public static boolean receive;
int no_automata = 1;
 public long time_36 ;
 public long pre_16_38 ;
 public long pre_7_23 ;
 public boolean portscan_34 ;
 public boolean pre_11_43 ;
 public long port1_24 ;
 public long pre_6_25 ;
 public boolean pre_17_37 ;
 public long pre_12_42 ;
 public long port2_22 ;
 public long _rt_clock ;
public Clock _clock = new Clock(this);
 public long port3_21 ;
 public long pre_9_19 ;
 public long cnt_35 ;
 public long pre_3_28 ;
 public long pre_14_40 ;
 public boolean uniqueport_18 ;
 public long pre_8_20 ;
 public long cnt_33 ;
 public long pre_1_30 ;
 public long pre_13_41 ;
 public long pre_10_32 ;
 public long pre_2_29 ;
 public long pre_4_27 ;
 public long pre_5_26 ;
 public boolean violated ;
 public long pre_0_31 ;
 public long pre_15_39 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_cs_portscan_LUSTRE0() {
}

public static _cls_cs_portscan_LUSTRE0 _get_cls_cs_portscan_LUSTRE0_inst() { synchronized(_cls_cs_portscan_LUSTRE0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_cs_portscan_LUSTRE0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_cs_portscan_LUSTRE0_instances){
_performLogic_main(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_cs_portscan_LUSTRE0[] a = new _cls_cs_portscan_LUSTRE0[1];
synchronized(_cls_cs_portscan_LUSTRE0_instances){
a = _cls_cs_portscan_LUSTRE0_instances.keySet().toArray(a);}
for (_cls_cs_portscan_LUSTRE0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_cs_portscan_LUSTRE0_instances){
_cls_cs_portscan_LUSTRE0_instances.remove(this);}
synchronized(_clock){
_clock.off();
_clock._inst = null;
_clock = null;}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_main = 1;

public void _performLogic_main(String _info, int... _event) {

_cls_cs_portscan_LUSTRE0.pw.println("AUTOMATON::> main("+") STATE::>"+ _string_main(_state_id_main, 0));
_cls_cs_portscan_LUSTRE0.pw.flush();

if (0==1){}
else if (_state_id_main==1){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*initializationEvent*/))){
		pre_4_27 =1 ;
pre_6_25 =1 ;
pre_8_20 =1 ;
pre_9_19 =0 ;
pre_10_32 =0 ;
pre_11_43 =false ;
pre_12_42 =0 ;
pre_13_41 =0 ;
pre_14_40 =0 ;
pre_15_39 =0 ;
pre_16_38 =0 ;
pre_17_37 =false ;

		_state_id_main = 0;//moving to state lustre
		_goto_main(_info);
		}
}
else if (_state_id_main==0){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*periodicEvent*/))){
		_rt_clock =_clock .current_long ();
time_36 =(pre_11_43 )?(pre_12_42 ):((pre_13_41 ==0 )?(_rt_clock ):(pre_14_40 ));
uniqueport_18 =(receive &&port !=pre_0_31 &&port !=pre_1_30 &&port !=pre_2_29 )?(true ):(false );
cnt_35 =(uniqueport_18 &&_rt_clock -time_36 <2000 )?(pre_15_39 +1 ):((_rt_clock -time_36 <2000 )?(pre_16_38 ):((uniqueport_18 )?(1 ):(0 )));
port1_24 =(uniqueport_18 &&pre_3_28 ==0 )?(port ):(-(pre_4_27 ));
cnt_33 =(uniqueport_18 )?(pre_9_19 +1 %3 ):(pre_10_32 );
port2_22 =(uniqueport_18 &&pre_5_26 ==1 )?(port ):(-(pre_6_25 ));
port3_21 =(uniqueport_18 &&pre_7_23 ==2 )?(port ):(-(pre_8_20 ));
portscan_34 =(cnt_35 >=3 )?(true ):(pre_17_37 );
violated =portscan_34 ;
pre_0_31 =port1_24 ;
pre_1_30 =port2_22 ;
pre_2_29 =port3_21 ;
pre_3_28 =cnt_33 ;
pre_4_27 =port1_24 ;
pre_5_26 =cnt_33 ;
pre_6_25 =port2_22 ;
pre_7_23 =cnt_33 ;
pre_8_20 =port3_21 ;
pre_9_19 =cnt_33 ;
pre_10_32 =cnt_33 ;
pre_11_43 =uniqueport_18 ;
pre_12_42 =_rt_clock ;
pre_13_41 =cnt_35 ;
pre_14_40 =time_36 ;
pre_15_39 =cnt_35 ;
pre_16_38 =cnt_35 ;
pre_17_37 =portscan_34 ;
_cls_cs_portscan_LUSTRE0.pw .println (" _rt_clock: "+_rt_clock +" "+" receive: "+receive +" "+" port: "+port +" "+" output: "+violated +" ");

		_state_id_main = 0;//moving to state lustre
		_goto_main(_info);
		}
}
}

public void _goto_main(String _info){
_cls_cs_portscan_LUSTRE0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_main(_state_id_main, 1));
_cls_cs_portscan_LUSTRE0.pw.flush();
}

public String _string_main(int _state_id, int _mode){
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