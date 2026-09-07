package larva;


import nesting.User;
import nesting.Account;
import nesting.Transaction;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_bank0 implements _callable{

public static PrintWriter pw; 
public static _cls_bank0 root;

public static LinkedHashMap<_cls_bank0,_cls_bank0> _cls_bank0_instances;

_cls_bank0 parent; //to remain null - this class does not have a parent!
int no_automata;

int _state_id_users;
 public int userCnt =0 ;

public static void initialize(){
//note that this initialisation does not include user-defined declarations in the Variables section


_cls_bank0_instances = new LinkedHashMap<_cls_bank0,_cls_bank0>();
try{
root = new _cls_bank0();
_cls_bank0_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_bank0() {
}

public void initialisation() {
no_automata = 1;
//initialise automata
_state_id_users = 3;

System .out .println ("Started!!!");

}

public static _cls_bank0 _get_cls_bank0_inst() { synchronized(_cls_bank0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_bank0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 1;
}

public void _call(String _info, int... _event){
synchronized(_cls_bank0_instances){
_performLogic_users(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){

_cls_bank1[] a1 = new _cls_bank1[1];
synchronized(_cls_bank1._cls_bank1_instances){
a1 = _cls_bank1._cls_bank1_instances.keySet().toArray(a1);}
for (_cls_bank1 _inst : a1)
if (_inst != null){
_inst._call(_info, _event); 
_inst._call_all_filtered(_info, _event);
}
}

public static void _call_all(String _info, int... _event){

_cls_bank0[] a = new _cls_bank0[1];
synchronized(_cls_bank0_instances){
a = _cls_bank0_instances.keySet().toArray(a);}
for (_cls_bank0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_bank0_instances){
_cls_bank0_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}


public void _performLogic_users(String _info, int... _event) {

if (0==1){}
else if (_state_id_users==3){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*addUser*/))){
		userCnt ++;

		_state_id_users = 2;//moving to state ok

		_goto_users(_info);
		}
		else if ((_occurredEvent(_event,2/*deleteUser*/))){
		
		_state_id_users = 1;//moving to state baddelete

		_goto_users(_info);
		}
		else if ((_occurredEvent(_event,4/*allUsers*/))){
		
		_state_id_users = 3;//moving to state start
System .out .println ("Started!!!");

		_goto_users(_info);
		}
}
else if (_state_id_users==2){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*addUser*/)) && (userCnt >5 )){
		
		_state_id_users = 0;//moving to state toomany

		_goto_users(_info);
		}
		else if ((_occurredEvent(_event,0/*addUser*/))){
		userCnt ++;

		_state_id_users = 2;//moving to state ok

		_goto_users(_info);
		}
		else if ((_occurredEvent(_event,2/*deleteUser*/)) && (userCnt ==1 )){
		userCnt --;

		_state_id_users = 3;//moving to state start
System .out .println ("Started!!!");

		_goto_users(_info);
		}
		else if ((_occurredEvent(_event,2/*deleteUser*/))){
		userCnt --;

		_state_id_users = 2;//moving to state ok

		_goto_users(_info);
		}
		else if ((_occurredEvent(_event,4/*allUsers*/))){
		
		_state_id_users = 2;//moving to state ok

		_goto_users(_info);
		}
}
}

public void _goto_users(String _info){
 String state_format = _string_users(_state_id_users, 1);
 if (state_format.startsWith("!!!SYSTEM REACHED BAD STATE!!!")) {
   System.out.println("[users]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + state_format);
}
}

public String _string_users(int _state_id, int _mode){
switch(_state_id){
case 0: if (_mode == 0) return "toomany"; else return "!!!SYSTEM REACHED BAD STATE!!! toomany "+new _BadStateExceptionbank().toString()+" ";
case 3: if (_mode == 0) return "start"; else return "start";
case 1: if (_mode == 0) return "baddelete"; else return "!!!SYSTEM REACHED BAD STATE!!! baddelete "+new _BadStateExceptionbank().toString()+" ";
case 2: if (_mode == 0) return "ok"; else return "ok";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}