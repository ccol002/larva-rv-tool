package larva;


import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_tutorial_part10 implements _callable{

public static PrintWriter pw; 
public static _cls_tutorial_part10 root;

public static LinkedHashMap<_cls_tutorial_part10,_cls_tutorial_part10> _cls_tutorial_part10_instances = new LinkedHashMap<_cls_tutorial_part10,_cls_tutorial_part10>();
static{
try{
RunningClock.start();
pw = new PrintWriter("/Volumes/ACMSAC 2017/BadLogins/output_tutorial_part1.txt");

root = new _cls_tutorial_part10();
_cls_tutorial_part10_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_tutorial_part10 parent; //to remain null - this class does not have a parent!
int no_automata = 1;
 public int count =0 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_tutorial_part10() {
}

public void initialisation() {
}

public static _cls_tutorial_part10 _get_cls_tutorial_part10_inst() { synchronized(_cls_tutorial_part10_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_tutorial_part10))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_tutorial_part10_instances){
_performLogic_badlogins(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_tutorial_part10[] a = new _cls_tutorial_part10[1];
synchronized(_cls_tutorial_part10_instances){
a = _cls_tutorial_part10_instances.keySet().toArray(a);}
for (_cls_tutorial_part10 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_tutorial_part10_instances){
_cls_tutorial_part10_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_badlogins = 47;

public void _performLogic_badlogins(String _info, int... _event) {

_cls_tutorial_part10.pw.println("[badlogins]AUTOMATON::> badlogins("+") STATE::>"+ _string_badlogins(_state_id_badlogins, 0));
_cls_tutorial_part10.pw.flush();

if (0==1){}
else if (_state_id_badlogins==47){
		if (1==0){}
		else if ((_occurredEvent(_event,80/*badlogin*/)) && (count >3 )){
		_cls_tutorial_part10.pw .println ("More than 3 bad logins!!!");

		_state_id_badlogins = 46;//moving to state bad
		_goto_badlogins(_info);
		}
		else if ((_occurredEvent(_event,80/*badlogin*/))){
		count ++;

		_state_id_badlogins = 47;//moving to state starting
		_goto_badlogins(_info);
		}
		else if ((_occurredEvent(_event,82/*goodlogin*/))){
		count =0 ;

		_state_id_badlogins = 47;//moving to state starting
		_goto_badlogins(_info);
		}
}
}

public void _goto_badlogins(String _info){
_cls_tutorial_part10.pw.println("[badlogins]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_badlogins(_state_id_badlogins, 1));
_cls_tutorial_part10.pw.flush();
}

public String _string_badlogins(int _state_id, int _mode){
switch(_state_id){
case 46: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptiontutorial_part1().toString()+" ";
case 47: if (_mode == 0) return "starting"; else return "starting";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}