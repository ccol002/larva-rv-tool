package larva;



import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_channels0 implements _callable{

public static PrintWriter pw; 
public static _cls_channels0 root;
public static Channel ch;

public static LinkedHashMap<_cls_channels0,_cls_channels0> _cls_channels0_instances;

_cls_channels0 parent; //to remain null - this class does not have a parent!
public static String from;
int no_automata;

int _state_id_counter;
int _state_id_listener;
 public int count =0 ;

public static void initialize(){
//note that this initialisation does not include user-defined declarations in the Variables section

ch = new Channel();

_cls_channels0_instances = new LinkedHashMap<_cls_channels0,_cls_channels0>();
try{
pw = new PrintWriter("{{OUTPUT_DIR}}/output_channels.txt");

root = new _cls_channels0();
_cls_channels0_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_channels0() {
}

public void initialisation() {
no_automata = 2;
//initialise automata
_state_id_counter = 1;


_state_id_listener = 3;


}

public static _cls_channels0 _get_cls_channels0_inst() { synchronized(_cls_channels0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_channels0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 1;
}

public void _call(String _info, int... _event){
synchronized(_cls_channels0_instances){
_performLogic_counter(_info, _event);
_performLogic_listener(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_channels0[] a = new _cls_channels0[1];
synchronized(_cls_channels0_instances){
a = _cls_channels0_instances.keySet().toArray(a);}
for (_cls_channels0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_channels0_instances){
_cls_channels0_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}


public void _performLogic_counter(String _info, int... _event) {

if (0==1){}
else if (_state_id_counter==1){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*increment*/)) && (count >2 )){
		ch .send ("threshold");

		_state_id_counter = 0;//moving to state bad

		_goto_counter(_info);
		}
		else if ((_occurredEvent(_event,0/*increment*/))){
		count ++;

		_state_id_counter = 1;//moving to state start

		_goto_counter(_info);
		}
}
}

public void _goto_counter(String _info){
 String state_format = _string_counter(_state_id_counter, 1);
 if (state_format.startsWith("!!!SYSTEM REACHED BAD STATE!!!")) {
_cls_channels0.pw.println("[counter]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + state_format);
_cls_channels0.pw.flush();
}
}

public String _string_counter(int _state_id, int _mode){
switch(_state_id){
case 0: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionchannels().toString()+" ";
case 1: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public void _performLogic_listener(String _info, int... _event) {

if (0==1){}
else if (_state_id_listener==3){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*notified*/))){
		_cls_channels0.pw .println ("Got: "+from );

		_state_id_listener = 2;//moving to state gotit

		_goto_listener(_info);
		}
}
}

public void _goto_listener(String _info){
 String state_format = _string_listener(_state_id_listener, 1);
 if (state_format.startsWith("!!!SYSTEM REACHED BAD STATE!!!")) {
_cls_channels0.pw.println("[listener]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + state_format);
_cls_channels0.pw.flush();
}
}

public String _string_listener(int _state_id, int _mode){
switch(_state_id){
case 3: if (_mode == 0) return "waiting"; else return "waiting";
case 2: if (_mode == 0) return "gotit"; else return "gotit";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}