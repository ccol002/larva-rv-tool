package aspects;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import events.Template;
import larva.*;
public aspect _asp_script0 {

public static Object lock = new Object();
//note that in the current implementation all events are called in the declared context
 //and not in the contexts below...

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script0.forceClassLoad();
}
}
before ( long timestamp,Template t,String name) : (call(* Template.call(..)) && target(t) && args(name,timestamp) && !cflow(adviceexecution())  && if (name .equals ("login" ))) {

try{

synchronized(_asp_script0.lock){

RunningClock.updateNow(timestamp);

_cls_script0 _cls_inst = _cls_script0._get_cls_script0_inst(false);
_cls_inst.timestamp = timestamp;
_cls_inst.t = t;
_cls_inst.name = name;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*login*/);
 _cls_inst.updateInDB();
}

}catch(Exception ex) {ex.printStackTrace();}
}
before ( long timestamp,Template t,String name) : (call(* Template.call(..)) && target(t) && args(name,timestamp) && !cflow(adviceexecution())  && if (name .equals ("event_name" ))) {

try{

synchronized(_asp_script0.lock){

RunningClock.updateNow(timestamp);

_cls_script0 _cls_inst = _cls_script0._get_cls_script0_inst(false);
_cls_inst.timestamp = timestamp;
_cls_inst.t = t;
_cls_inst.name = name;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*event_name*/);
 _cls_inst.updateInDB();
}

}catch(Exception ex) {ex.printStackTrace();}
}
}