package larva;


import larva.*;
import sniffer.*;

import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_cs_redirect0 implements _callable{

public static PrintWriter pw; 
public static _cls_cs_redirect0 root;

public static HashMap<_cls_cs_redirect0,_cls_cs_redirect0> _cls_cs_redirect0_instances = new HashMap<_cls_cs_redirect0,_cls_cs_redirect0>();
static{
try{
pw = new PrintWriter("C:\\Users\\University User\\Desktop\\projects\\packetMonitor\\src\\\\output_cs_redirect.txt");

root = new _cls_cs_redirect0();
root.c2.restart();
root.c3.restart();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_cs_redirect0 parent; //to remain null - this class does not have a parent!
public static boolean redirectMSG;
int no_automata = 1;
public Clock c2 = new Clock(this);
public Clock c3 = new Clock(this);

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_cs_redirect0() {
c2.register(1000);
c3.register(1000);
}

public static _cls_cs_redirect0 _get_cls_cs_redirect0_inst() {
 return root;
}

public boolean equals(Object o) {
 if ((o instanceof _cls_cs_redirect0))
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

for (_cls_cs_redirect0 _inst : _cls_cs_redirect0_instances.keySet())
_inst._call(_info, _event);
}

public void _killThis(){
if (--no_automata <= 0){
	synchronized (c2){
c2.off();
c2._inst = null;
c2 = null;}
	synchronized (c3){
c3.off();
c3._inst = null;
c3 = null;}
 _cls_cs_redirect0_instances.remove(this);
}
}

int _state_id_ceform = 9;

public void _performLogic_ceform(String _info, int... _event) {

_cls_cs_redirect0.pw.println("AUTOMATON::> ceform("+") STATE::>"+ _string_ceform(_state_id_ceform, 0));
_cls_cs_redirect0.pw.flush();

if (0==1){}
else if (_state_id_ceform==7){
		if (1==0){}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG ))&&!((c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		
		_state_id_ceform = 5;//moving to state loc0i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG ))&&(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		c3.reset ();

		_state_id_ceform = 6;//moving to state loc0i2i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&!(redirectMSG )&&!((c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		
		_state_id_ceform = 7;//moving to state loc0i1i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&redirectMSG &&!((c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		c2.reset ();

		_state_id_ceform = 7;//moving to state loc0i1i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&!(redirectMSG )&&(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		c3.reset ();

		_state_id_ceform = 8;//moving to state loc0i1i2i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&redirectMSG &&(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		c2.reset ();
c3.reset ();

		_state_id_ceform = 8;//moving to state loc0i1i2i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/))){
		
		_state_id_ceform = 0;//moving to state unhandled
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==6){
		if (1==0){}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!(redirectMSG )&&!(c3.compareToMillis ((long )1000.0 )<0 )&&!((c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		
		_state_id_ceform = 5;//moving to state loc0i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!(redirectMSG )&&!(c3.compareToMillis ((long )1000.0 )<0 )&&(c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		
		_state_id_ceform = 1;//moving to state loc0i3i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!(redirectMSG )&&c3.compareToMillis ((long )1000.0 )<0 &&!((c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		
		_state_id_ceform = 6;//moving to state loc0i2i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!(redirectMSG )&&c3.compareToMillis ((long )1000.0 )<0 &&(c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		
		_state_id_ceform = 2;//moving to state loc0i2i3i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((redirectMSG &&!(c3.compareToMillis ((long )1000.0 )<0 )&&!((c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		c2.reset ();

		_state_id_ceform = 7;//moving to state loc0i1i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((redirectMSG &&!(c3.compareToMillis ((long )1000.0 )<0 )&&(c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		c2.reset ();

		_state_id_ceform = 3;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((redirectMSG &&c3.compareToMillis ((long )1000.0 )<0 &&!((c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		c2.reset ();

		_state_id_ceform = 8;//moving to state loc0i1i2i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((redirectMSG &&c3.compareToMillis ((long )1000.0 )<0 &&(c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		c2.reset ();

		_state_id_ceform = 4;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/))){
		
		_state_id_ceform = 0;//moving to state unhandled
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==4){
		if (1==0){}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG ))&&!((c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))))){
		
		_state_id_ceform = 1;//moving to state loc0i3i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG ))&&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&!((c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		
		_state_id_ceform = 2;//moving to state loc0i2i3i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG ))&&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		c3.reset ();

		_state_id_ceform = 2;//moving to state loc0i2i3i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&!(redirectMSG )&&!((c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))))){
		
		_state_id_ceform = 3;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&redirectMSG &&!((c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))))){
		c2.reset ();

		_state_id_ceform = 3;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&!(redirectMSG )&&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&!((c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		
		_state_id_ceform = 4;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&!(redirectMSG )&&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		c3.reset ();

		_state_id_ceform = 4;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&redirectMSG &&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&!((c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		c2.reset ();

		_state_id_ceform = 4;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&redirectMSG &&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		c2.reset ();
c3.reset ();

		_state_id_ceform = 4;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/))){
		
		_state_id_ceform = 0;//moving to state unhandled
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==8){
		if (1==0){}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG ))&&!((c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))&&!((c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		
		_state_id_ceform = 5;//moving to state loc0i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG ))&&!((c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))&&(c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		
		_state_id_ceform = 1;//moving to state loc0i3i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG ))&&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&!((c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&!((c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		
		_state_id_ceform = 6;//moving to state loc0i2i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG ))&&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )&&!((c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		c3.reset ();

		_state_id_ceform = 6;//moving to state loc0i2i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG ))&&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&!((c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&(c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		
		_state_id_ceform = 2;//moving to state loc0i2i3i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG ))&&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )&&(c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		c3.reset ();

		_state_id_ceform = 2;//moving to state loc0i2i3i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&!(redirectMSG )&&!((c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))&&!((c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		
		_state_id_ceform = 7;//moving to state loc0i1i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&redirectMSG &&!((c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))&&!((c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		c2.reset ();

		_state_id_ceform = 7;//moving to state loc0i1i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&!(redirectMSG )&&!((c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))&&(c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		
		_state_id_ceform = 3;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&redirectMSG &&!((c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))&&(c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		c2.reset ();

		_state_id_ceform = 3;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&!(redirectMSG )&&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&!((c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&!((c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		
		_state_id_ceform = 8;//moving to state loc0i1i2i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&!(redirectMSG )&&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )&&!((c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		c3.reset ();

		_state_id_ceform = 8;//moving to state loc0i1i2i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&redirectMSG &&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&!((c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&!((c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		c2.reset ();

		_state_id_ceform = 8;//moving to state loc0i1i2i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&redirectMSG &&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )&&!((c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		c2.reset ();
c3.reset ();

		_state_id_ceform = 8;//moving to state loc0i1i2i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&!(redirectMSG )&&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&!((c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&(c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		
		_state_id_ceform = 4;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&!(redirectMSG )&&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )&&(c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		c3.reset ();

		_state_id_ceform = 4;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&redirectMSG &&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&!((c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&(c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		c2.reset ();

		_state_id_ceform = 4;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&redirectMSG &&(c3.compareToMillis ((long )1000.0 )<0 ||(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))&&(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )&&(c3.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		c2.reset ();
c3.reset ();

		_state_id_ceform = 4;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/))){
		
		_state_id_ceform = 0;//moving to state unhandled
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==5){
		if (1==0){}
		else if ((_occurredEvent(_event,6/*all*/)) && (!(redirectMSG ))){
		
		_state_id_ceform = 5;//moving to state loc0i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (redirectMSG )){
		c2.reset ();

		_state_id_ceform = 7;//moving to state loc0i1i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/))){
		
		_state_id_ceform = 0;//moving to state unhandled
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==9){
		if (1==0){}
		else if ((_occurredEvent(_event,6/*all*/)) && (true )){
		
		_state_id_ceform = 5;//moving to state loc0i
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==2){
		if (1==0){}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!(redirectMSG )&&!(c3.compareToMillis ((long )1000.0 )<0 )))){
		
		_state_id_ceform = 1;//moving to state loc0i3i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!(redirectMSG )&&c3.compareToMillis ((long )1000.0 )<0 ))){
		
		_state_id_ceform = 2;//moving to state loc0i2i3i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((redirectMSG &&!(c3.compareToMillis ((long )1000.0 )<0 )))){
		c2.reset ();

		_state_id_ceform = 3;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((redirectMSG &&c3.compareToMillis ((long )1000.0 )<0 ))){
		c2.reset ();

		_state_id_ceform = 4;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/))){
		
		_state_id_ceform = 0;//moving to state unhandled
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==3){
		if (1==0){}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG ))&&!((c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		
		_state_id_ceform = 1;//moving to state loc0i3i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && ((!((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG ))&&(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		c3.reset ();

		_state_id_ceform = 2;//moving to state loc0i2i3i2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&!(redirectMSG )&&!((c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		
		_state_id_ceform = 3;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&redirectMSG &&!((c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG ))))){
		c2.reset ();

		_state_id_ceform = 3;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&!(redirectMSG )&&(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		c3.reset ();

		_state_id_ceform = 4;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (((c2.compareToMillis ((long )1000.0 )<0 ||redirectMSG )&&redirectMSG &&(c2.compareToMillis ((long )1000.0 )<0 &&redirectMSG )))){
		c2.reset ();
c3.reset ();

		_state_id_ceform = 4;//moving to state loc0i1i2i3i1l2l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/))){
		
		_state_id_ceform = 0;//moving to state unhandled
		_goto_ceform(_info);
		}
}
else if (_state_id_ceform==1){
		if (1==0){}
		else if ((_occurredEvent(_event,6/*all*/)) && (!(redirectMSG ))){
		
		_state_id_ceform = 1;//moving to state loc0i3i
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/)) && (redirectMSG )){
		c2.reset ();

		_state_id_ceform = 3;//moving to state loc0i1i3i1l
		_goto_ceform(_info);
		}
		else if ((_occurredEvent(_event,6/*all*/))){
		
		_state_id_ceform = 0;//moving to state unhandled
		_goto_ceform(_info);
		}
}
}

public void _goto_ceform(String _info){
_cls_cs_redirect0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_ceform(_state_id_ceform, 1));
_cls_cs_redirect0.pw.flush();
}

public String _string_ceform(int _state_id, int _mode){
switch(_state_id){
case 7: if (_mode == 0) return "loc0i1i1l"; else return "loc0i1i1l";
case 6: if (_mode == 0) return "loc0i2i2l"; else return "loc0i2i2l";
case 4: if (_mode == 0) return "loc0i1i2i3i1l2l"; else return "!!!SYSTEM REACHED BAD STATE!!! loc0i1i2i3i1l2l "+new _BadStateExceptioncs_redirect().toString()+" ";
case 8: if (_mode == 0) return "loc0i1i2i1l2l"; else return "loc0i1i2i1l2l";
case 5: if (_mode == 0) return "loc0i"; else return "loc0i";
case 9: if (_mode == 0) return "start"; else return "start";
case 2: if (_mode == 0) return "loc0i2i3i2l"; else return "!!!SYSTEM REACHED BAD STATE!!! loc0i2i3i2l "+new _BadStateExceptioncs_redirect().toString()+" ";
case 0: if (_mode == 0) return "unhandled"; else return "!!!SYSTEM REACHED BAD STATE!!! unhandled "+new _BadStateExceptioncs_redirect().toString()+" ";
case 3: if (_mode == 0) return "loc0i1i3i1l"; else return "!!!SYSTEM REACHED BAD STATE!!! loc0i1i3i1l "+new _BadStateExceptioncs_redirect().toString()+" ";
case 1: if (_mode == 0) return "loc0i3i"; else return "!!!SYSTEM REACHED BAD STATE!!! loc0i3i "+new _BadStateExceptioncs_redirect().toString()+" ";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}