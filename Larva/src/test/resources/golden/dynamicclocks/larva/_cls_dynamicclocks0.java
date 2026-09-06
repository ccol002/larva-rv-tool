package larva;



import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_dynamicclocks0 implements _callable{

public static PrintWriter pw; 
public static _cls_dynamicclocks0 root;

public static LinkedHashMap<_cls_dynamicclocks0,_cls_dynamicclocks0> _cls_dynamicclocks0_instances;

_cls_dynamicclocks0 parent; //to remain null - this class does not have a parent!
int no_automata;

int _state_id_test;
public Clock c;
 public int count =0 ;

public static void initialize(){
//note that this initialisation does not include user-defined declarations in the Variables section


_cls_dynamicclocks0_instances = new LinkedHashMap<_cls_dynamicclocks0,_cls_dynamicclocks0>();
try{
pw = new PrintWriter("{{OUTPUT_DIR}}/output_dynamicclocks.txt");

root = new _cls_dynamicclocks0();
_cls_dynamicclocks0_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_dynamicclocks0() {
c = new Clock(this,"c");
}

public void initialisation() {
no_automata = 1;
//initialise automata
_state_id_test = 1;


   c.reset();
}

public static _cls_dynamicclocks0 _get_cls_dynamicclocks0_inst() { synchronized(_cls_dynamicclocks0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_dynamicclocks0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 1;
}

public void _call(String _info, int... _event){
synchronized(_cls_dynamicclocks0_instances){
_performLogic_test(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_dynamicclocks0[] a = new _cls_dynamicclocks0[1];
synchronized(_cls_dynamicclocks0_instances){
a = _cls_dynamicclocks0_instances.keySet().toArray(a);}
for (_cls_dynamicclocks0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_dynamicclocks0_instances){
_cls_dynamicclocks0_instances.remove(this);}
synchronized(c){
c.off();
c._inst = null;
c = null;}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}


public void _performLogic_test(String _info, int... _event) {

if (0==1){}
else if (_state_id_test==1){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*tick*/)) && (count >3 )){
		
		_state_id_test = 0;//moving to state bad

		_goto_test(_info);
		}
		else if ((_occurredEvent(_event,2/*event*/))){
		count ++;
c .registerDynamically (500l );

		_state_id_test = 1;//moving to state start

		_goto_test(_info);
		}
}
}

public void _goto_test(String _info){
 String state_format = _string_test(_state_id_test, 1);
 if (state_format.startsWith("!!!SYSTEM REACHED BAD STATE!!!")) {
_cls_dynamicclocks0.pw.println("[test]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + state_format);
_cls_dynamicclocks0.pw.flush();
}
}

public String _string_test(int _state_id, int _mode){
switch(_state_id){
case 0: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptiondynamicclocks().toString()+" ";
case 1: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}