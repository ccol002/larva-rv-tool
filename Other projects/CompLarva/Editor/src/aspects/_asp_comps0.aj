package aspects;

import main.*;

import larva.*;
import java.util.HashMap;
import java.util.Stack;
public aspect _asp_comps0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_comps0.initialize();
}
}
before ( Editor ed) : (call(* Editor.delete(..)) && target(ed) && !cflow(adviceexecution())) {

synchronized(_asp_comps0.lock){
String s;
s =ed .get (ed .length ()-1 ,ed .length ());

_cls_comps0 _cls_inst = _cls_comps0._get_cls_comps0_inst();
_cls_inst.ed.put(_cls_comps0.sid,ed);
_cls_inst.s.put(_cls_comps0.sid,s);
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*delete*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*delete*/);
}
}
before ( Editor ed) : (call(* Editor.save(..)) && target(ed) && !cflow(adviceexecution())) {

synchronized(_asp_comps0.lock){

_cls_comps0 _cls_inst = _cls_comps0._get_cls_comps0_inst();
_cls_inst.ed.put(_cls_comps0.sid,ed);
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*save*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*save*/);
}
}
before ( String s,Editor ed) : (call(* Editor.insert(..)) && target(ed) && args(s) && !cflow(adviceexecution())) {

synchronized(_asp_comps0.lock){

_cls_comps0 _cls_inst = _cls_comps0._get_cls_comps0_inst();
_cls_inst.s.put(_cls_comps0.sid,s);
_cls_inst.ed.put(_cls_comps0.sid,ed);
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*insert*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*insert*/);
}
}
before ( Editor ed) : (call(* Editor.undoOne(..)) && target(ed) && !cflow(adviceexecution())) {

synchronized(_asp_comps0.lock){

_cls_comps0 _cls_inst = _cls_comps0._get_cls_comps0_inst();
_cls_inst.ed.put(_cls_comps0.sid,ed);
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*undoone*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*undoone*/);
}
}
before ( Editor ed) : (call(* Editor.undo(..)) && target(ed) && !cflow(adviceexecution())) {

synchronized(_asp_comps0.lock){

_cls_comps0 _cls_inst = _cls_comps0._get_cls_comps0_inst();
_cls_inst.ed.put(_cls_comps0.sid,ed);
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*undo*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*undo*/);
}
}
}