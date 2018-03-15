package larva;


import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_channelexample1 implements _callable{
public static Channel gl = new Channel();

public static HashMap<_cls_channelexample1,_cls_channelexample1> _cls_channelexample1_instances = new HashMap<_cls_channelexample1,_cls_channelexample1>();

_cls_channelexample0 parent;
public static User u1;
public User u;
int no_automata = 2;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_channelexample1( User u) {
parent = _cls_channelexample0._get_cls_channelexample0_inst();
this.u = u;
}

public static _cls_channelexample1 _get_cls_channelexample1_inst( User u) {
_cls_channelexample1 _inst = new _cls_channelexample1( u);
if (_cls_channelexample1_instances.containsKey(_inst))
{
_cls_channelexample1 tmp = _cls_channelexample1_instances.get(_inst);
 return _cls_channelexample1_instances.get(_inst);
}
else
{
 _cls_channelexample1_instances.put(_inst,_inst);
 return _inst;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_channelexample1)
 && (u == null || u.equals(((_cls_channelexample1)o).u))
 && (parent == null || parent.equals(((_cls_channelexample1)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
_performLogic_two(_info, _event);
_performLogic_one(_info, _event);
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

for (_cls_channelexample1 _inst : _cls_channelexample1_instances.keySet())
_inst._call(_info, _event);
}

public void _killThis(){
if (--no_automata <= 0){
 _cls_channelexample1_instances.remove(this);
}
}

int _state_id_two = 7;

public void _performLogic_two(String _info, int... _event) {

_cls_channelexample0.pw.println("AUTOMATON::> two("+u + " " + ") STATE::>"+ _string_two(_state_id_two, 0));
_cls_channelexample0.pw.flush();

if (0==1){}
else if (_state_id_two==5){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*pressOK*/)) && (checkPassword ())){
		gl.send (u );

		_state_id_two = 6;//moving to state goodlogin
		_goto_two(_info);
		}
		else if ((_occurredEvent(_event,2/*pressOK*/))){
		
		_state_id_two = 7;//moving to state loadsite
		_goto_two(_info);
		}
}
else if (_state_id_two==7){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*pressOK*/)) && (checkUserName ())){
		
		_state_id_two = 5;//moving to state promptPW
		_goto_two(_info);
		}
}
}

public void _goto_two(String _info){
_cls_channelexample0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_two(_state_id_two, 1));
_cls_channelexample0.pw.flush();
}

public String _string_two(int _state_id, int _mode){
switch(_state_id){
case 6: if (_mode == 0) return "goodlogin"; else return "goodlogin";
case 5: if (_mode == 0) return "promptPW"; else return "promptPW";
case 7: if (_mode == 0) return "loadsite"; else return "loadsite";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_one = 4;

public void _performLogic_one(String _info, int... _event) {

_cls_channelexample0.pw.println("AUTOMATON::> one("+u + " " + ") STATE::>"+ _string_one(_state_id_one, 0));
_cls_channelexample0.pw.flush();

if (0==1){}
else if (_state_id_one==4){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*goodlogin*/))){
		
		_state_id_one = 3;//moving to state loggedin
		_goto_one(_info);
		}
		else if ((_occurredEvent(_event,4/*badlogin*/))){
		
		_state_id_one = 1;//moving to state loggedout2
		_goto_one(_info);
		}
}
else if (_state_id_one==1){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*goodlogin*/))){
		
		_state_id_one = 3;//moving to state loggedin
		_goto_one(_info);
		}
		else if ((_occurredEvent(_event,4/*badlogin*/))){
		
		_state_id_one = 2;//moving to state loggedout3
		_goto_one(_info);
		}
}
else if (_state_id_one==2){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*goodlogin*/))){
		
		_state_id_one = 3;//moving to state loggedin
		_goto_one(_info);
		}
		else if ((_occurredEvent(_event,4/*badlogin*/))){
		
		_state_id_one = 0;//moving to state badlogins
		_goto_one(_info);
		}
}
}

public void _goto_one(String _info){
_cls_channelexample0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_one(_state_id_one, 1));
_cls_channelexample0.pw.flush();
}

public String _string_one(int _state_id, int _mode){
switch(_state_id){
case 3: if (_mode == 0) return "loggedin"; else return "loggedin";
case 0: if (_mode == 0) return "badlogins"; else return "!!!SYSTEM REACHED BAD STATE!!! badlogins "+new _BadStateExceptionchannelexample().toString()+" ";
case 4: if (_mode == 0) return "loggedout1"; else return "loggedout1";
case 1: if (_mode == 0) return "loggedout2"; else return "loggedout2";
case 2: if (_mode == 0) return "loggedout3"; else return "loggedout3";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}

boolean checkUserName(){return true;}

boolean checkPassword(){return true;}
}