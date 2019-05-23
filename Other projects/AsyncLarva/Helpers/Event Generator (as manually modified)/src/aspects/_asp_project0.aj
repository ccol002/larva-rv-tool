package aspects;

import Events.Template;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import larva.*;
public aspect _asp_project0 {

public static Object lock = new Object();
//note that in the current implementation all events are called in the declared context
 //and not in the contexts below...

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_project0.forceClassLoad();
}
}
before ( long timestamp,Template t,String name) : (call(* Template.call(..)) && target(t) && args(name,timestamp) && !cflow(adviceexecution())  && if (!t .params .containsKey ("user" ))) {

try{

synchronized(_asp_project0.lock){

RunningClock.updateNow(timestamp);

_cls_project0 _cls_inst = _cls_project0._get_cls_project0_inst(false);
_cls_inst.timestamp = timestamp;
_cls_inst.t = t;
_cls_inst.name = name;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*all0*/);
 _cls_inst.updateInDB();
}

}catch(Exception ex) {ex.printStackTrace();}
}
}