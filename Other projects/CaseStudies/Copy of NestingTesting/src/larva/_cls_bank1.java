package larva;


import java.util.HashMap;

import nesting.User;

public class _cls_bank1 {

public static HashMap<_cls_bank1,_cls_bank1> _cls_bank1_instances = new HashMap<_cls_bank1,_cls_bank1>();

_cls_bank0 parent;
public int id;
public User u1;
public User u;
 public int accountCnt =0 ;
public boolean _checkUser_u0 = false;
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_bank1( User u) {
parent = _cls_bank0._get_cls_bank0_inst();
this.u = u;
}

public static _cls_bank1 _get_cls_bank1_inst( User u) {
_cls_bank1 _inst = new _cls_bank1( u);
if (_cls_bank1_instances.containsKey(_inst))
{
_cls_bank1 tmp = _cls_bank1_instances.get(_inst);
if ( tmp._checkUser_u0 && !tmp.checkUser(u, tmp.u))
  _cls_bank0.pw.println(" Invariant Check Failed on Variable u!!: " + new _BadStateExceptionbank().toString());
  _cls_bank0.pw.flush();
tmp.u = u;
 return _cls_bank1_instances.get(_inst);
}
else
{
 _cls_bank1_instances.put(_inst,_inst);
 return _inst;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_bank1)
 && (u == null || u.equals(((_cls_bank1)o).u))
 && (parent == null || parent.equals(((_cls_bank1)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
_performLogic_account(_info, _event);
}

int _state_id_account = 5;

public void _performLogic_account(String _info, int... _event) {

_cls_bank0.pw.println("AUTOMATON::> account("+u+" "+") STATE::>"+ _string_account(_state_id_account, 0));
_cls_bank0.pw.flush();

if (0==1){}
else if (_state_id_account==5){
		if (1==0){}
		else if ((_occurredEvent(_event,5/*addAccount*/)) && (accountCnt >5 )){
		
		_state_id_account = 4;//moving to state toomany

		_goto_account(_info);
		}
		else if ((_occurredEvent(_event,5/*addAccount*/))){
		accountCnt ++;
System.out.println ("I have access to USER :  "+u +" and to usercnt:  "+parent.userCnt );

		_state_id_account = 5;//moving to state start

_checkUser_u0 = true;
		_goto_account(_info);
		}
		else if ((_occurredEvent(_event,7/*deleteAccount*/))){
		accountCnt --;

		_state_id_account = 5;//moving to state start

		_goto_account(_info);
		}
		else if ((_occurredEvent(_event,9/*allAccounts*/))){
		
		_state_id_account = 5;//moving to state start

		_goto_account(_info);
		}
}
}

public void _goto_account(String _info){
_cls_bank0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_account(_state_id_account, 1));
_cls_bank0.pw.flush();
}

public String _string_account(int _state_id, int _mode){
switch(_state_id){
case 5: if (_mode == 0) return "start"; else return "start";
case 4: if (_mode == 0) return "toomany"; else return "!!!SYSTEM REACHED BAD STATE!!! toomany "+new _BadStateExceptionbank().toString()+" ";
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