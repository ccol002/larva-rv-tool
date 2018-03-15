package larva;


import java.util.HashMap;

public class _cls_properties20 {

public static HashMap<_cls_properties20,_cls_properties20> _cls_properties20_instances = new HashMap<_cls_properties20,_cls_properties20>();

_cls_properties20 parent; //to remain null - this class does not have a parent!
 public boolean logged =false ;
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_properties20() {
}

public static _cls_properties20 _get_cls_properties20_inst() {
_cls_properties20 _inst = new _cls_properties20();
if (_cls_properties20_instances.containsKey(_inst))
{
 return _cls_properties20_instances.get(_inst);
}
else
{
 _cls_properties20_instances.put(_inst,_inst);
 return _inst;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_properties20))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
_performLogic_badAccess(_info, _event);
}

int _state_id_badAccess = 2;

public void _performLogic_badAccess(String _info, int... _event) {

System.out.println("AUTOMATON::> badAccess("+") STATE::>"+ _string_badAccess(_state_id_badAccess, 0));

if (0==1){}
else if (_state_id_badAccess==2){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*i*/))){
		logged =true ;

		_state_id_badAccess = 2;//moving to state start

		_goto_badAccess(_info);
		}
		else if ((_occurredEvent(_event,6/*o*/))){
		logged =false ;

		_state_id_badAccess = 2;//moving to state start

		_goto_badAccess(_info);
		}
		else if ((_occurredEvent(_event,0/*w*/)) && (!logged )){
		
		_state_id_badAccess = 0;//moving to state bad_write

		_goto_badAccess(_info);
		}
		else if ((_occurredEvent(_event,2/*r*/)) && (!logged )){
		
		_state_id_badAccess = 1;//moving to state bad_read

		_goto_badAccess(_info);
		}
}
}

public void _goto_badAccess(String _info){
System.out.println("MOVED ON METHODCALL: "+ _info +"() TO STATE::> " + _string_badAccess(_state_id_badAccess, 1));
}

public String _string_badAccess(int _state_id, int _mode){
switch(_state_id){
case 1: if (_mode == 0) return "bad_read"; else return "!!!SYSTEM REACHED BAD STATE!!!  bad_read";
case 2: if (_mode == 0) return "start"; else return "start";
case 0: if (_mode == 0) return "bad_write"; else return "!!!SYSTEM REACHED BAD STATE!!!  bad_write";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}