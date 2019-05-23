package aspects;

import Events.Template;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import larva.*;
public aspect _asp_project2 {
//note that in the current implementation all events are called in the declared context
 //and not in the contexts below...
before ( long timestamp,Template t,String name) : (call(* Template.call(..)) && target(t) && args(name,timestamp) && !cflow(adviceexecution())  && if (t .params .containsKey ("user" )&& t .params .containsKey ("instrument" ))) {

try{

synchronized(_asp_project0.lock){

RunningClock.updateNow(timestamp);
String i;
String s;
s =t .params .get ("user" ).toString ();
i =t .params .get ("instrument" ).toString ();

_cls_project2 _cls_inst = _cls_project2._get_cls_project2_inst(false,i,s);
_cls_inst.timestamp = timestamp;
_cls_inst.t = t;
_cls_inst.name = name;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 14/*all2*/);
 _cls_inst.updateInDB();
}

}catch(Exception ex) {ex.printStackTrace();}
}
before ( Object inp,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_project0.balUpdateCH))) && args(inp)) {

try{

synchronized(_asp_project0.lock){
String i;
String s;
long timestamp;
double adjustment;
s =((HashMap )inp ).get ("user" ).toString ();
i =((HashMap )inp ).get ("instrument" ).toString ();
timestamp =(Long )((HashMap )inp ).get ("timestamp" );
adjustment =(Double )((HashMap )inp ).get ("adjustment" );

_cls_project2 _cls_inst = _cls_project2._get_cls_project2_inst(false,i,s);
_cls_inst.inp = inp;
_cls_project2.timestamp = timestamp;
_cls_project2.adjustment = adjustment;
 //note that this does not call the instances in the context...
 _cls_inst._call(thisJoinPoint.getSignature().toString(), 18/*balUpdate*/);
 _cls_inst.updateInDB();
}

}catch(Exception ex) {ex.printStackTrace();}
}
before ( Object inp,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_project0.balCheckCH))) && args(inp)) {

try{

synchronized(_asp_project0.lock){
String i;
String s;
long timestamp;
String txId;
double expBalance;
s =((HashMap )inp ).get ("user" ).toString ();
i =((HashMap )inp ).get ("instrument" ).toString ();
txId =((HashMap )inp ).get ("txId" ).toString ();
timestamp =(Long )((HashMap )inp ).get ("timestamp" );
expBalance =(Double )((HashMap )inp ).get ("balance" );

_cls_project2 _cls_inst = _cls_project2._get_cls_project2_inst(false,i,s);
_cls_inst.inp = inp;
_cls_project2.timestamp = timestamp;
_cls_project2.txId = txId;
_cls_project2.expBalance = expBalance;
 //note that this does not call the instances in the context...
 _cls_inst._call(thisJoinPoint.getSignature().toString(), 16/*balCheck*/);
 _cls_inst.updateInDB();
}

}catch(Exception ex) {ex.printStackTrace();}
}
}