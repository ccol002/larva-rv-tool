package larva;


import nesting.User;
import nesting.Account;
import nesting.Transaction;

import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_bank0 implements _callable{

public static PrintWriter pw; 
public static _cls_bank0 root;

public static HashMap<_cls_bank0,_cls_bank0> _cls_bank0_instances = new HashMap<_cls_bank0,_cls_bank0>();
static{
try{
pw = new PrintWriter("C:\\Users\\University User\\Desktop\\projects\\NestingTesting\\src\\\\output_bank.txt");

root = new _cls_bank0();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_bank0 parent; //to remain null - this class does not have a parent!
int no_automata = 1;
 public int userCnt =0 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_bank0() {
}

public static _cls_bank0 _get_cls_bank0_inst() {
 return root;
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

public void _call_all_filtered(String _info, int... _event){

for (_cls_bank1 _inst : _cls_bank1._cls_bank1_instances.keySet())
if (true){
_inst._call(_info, _event); 
_inst._call_all_filtered(_info, _event);
}
}

public static void _call_all(String _info, int... _event){

for (_cls_bank0 _inst : _cls_bank0_instances.keySet())
_inst._call(_info, _event);
}

public void _killThis(){
if (--no_automata <= 0){
 _cls_bank0_instances.remove(this);
}
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
_cls_bank0.pw.println ("Started!!!");

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
_cls_bank0.pw.println ("Started!!!");

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
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}