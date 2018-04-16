package larva;


import nesting.User;
import nesting.Account;
import nesting.Transaction;

import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_bank0 {

public static PrintWriter pw; 
static{
try{
pw = new PrintWriter("C:\\Users\\University User\\Desktop\\aspectJ\\NestingTesting\\src\\larva\\\\output_bank.txt");
}catch(Exception ex)
{ex.printStackTrace();}
}

public static HashMap<_cls_bank0,_cls_bank0> _cls_bank0_instances = new HashMap<_cls_bank0,_cls_bank0>();

_cls_bank0 parent; //to remain null - this class does not have a parent!
 public int userCnt =0 ;
public boolean _checkUser_u0 = false;
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_bank0() {
}

public static _cls_bank0 _get_cls_bank0_inst() {
_cls_bank0 _inst = new _cls_bank0();
if (_cls_bank0_instances.containsKey(_inst))
{
_cls_bank0 tmp = _cls_bank0_instances.get(_inst);
 return _cls_bank0_instances.get(_inst);
}
else
{
 _cls_bank0_instances.put(_inst,_inst);
 return _inst;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_bank0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
_performLogic_users(_info, _event);
}

int _state_id_users = 3;

public void _performLogic_users(String _info, int... _event) {

_cls_bank0.pw.println("AUTOMATON::> users("+") STATE::>"+ _string_users(_state_id_users, 0));
_cls_bank0.pw.flush();

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
_cls_bank0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_users(_state_id_users, 1));
_cls_bank0.pw.flush();
}

public String _string_users(int _state_id, int _mode){
switch(_state_id){
case 3: if (_mode == 0) return "start"; else return "start";
case 1: if (_mode == 0) return "baddelete"; else return "!!!SYSTEM REACHED BAD STATE!!! baddelete "+new _BadStateExceptionbank().toString()+" ";
case 2: if (_mode == 0) return "ok"; else return "ok";
case 0: if (_mode == 0) return "toomany"; else return "!!!SYSTEM REACHED BAD STATE!!! toomany "+new _BadStateExceptionbank().toString()+" ";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}

boolean checkUser(User u, User u1)
{
return u.accounts.size() == u1.accounts.size();
}
}