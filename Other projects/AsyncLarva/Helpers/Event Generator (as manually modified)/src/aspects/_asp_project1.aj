package aspects;

import Events.Template;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import larva.*;
public aspect _asp_project1 {
//note that in the current implementation all events are called in the declared context
 //and not in the contexts below...
before ( long timestamp,Template t,String name) : (call(* Template.call(..)) && target(t) && args(name,timestamp) && !cflow(adviceexecution())  && if (t .params .containsKey ("user" ))) {

try{

synchronized(_asp_project0.lock){

RunningClock.updateNow(timestamp);
String s;
double amount;
String currency;
s =t .params .get ("user" ).toString ();
currency =(t .params .containsKey ("transactionCurrency" ))?(String )t .params .get ("transactionCurrency" ):null ;
amount =(t .params .containsKey ("transactionAmount" ))?((BigDecimal )t .params .get ("transactionAmount" )).doubleValue ():0 ;

_cls_project1 _cls_inst = _cls_project1._get_cls_project1_inst(false,s);
_cls_inst.timestamp = timestamp;
_cls_inst.t = t;
_cls_inst.name = name;
_cls_project1.amount = amount;
_cls_project1.currency = currency;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*all1*/);
 _cls_inst.updateInDB();
}

}catch(Exception ex) {ex.printStackTrace();}
}
before ( String s1,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_project0.validTXCH))) && args(s1)) {

try{

synchronized(_asp_project0.lock){
String s;
s =s1 ;

_cls_project1 _cls_inst = _cls_project1._get_cls_project1_inst(false,s);
_cls_inst.s1 = s1;
 //note that this does not call the instances in the context...
 _cls_inst._call(thisJoinPoint.getSignature().toString(), 12/*validTX*/);
 _cls_inst.updateInDB();
}

}catch(Exception ex) {ex.printStackTrace();}
}
before ( long timestamp,Template t,String name) : (call(* Template.call(..)) && target(t) && args(name,timestamp) && !cflow(adviceexecution())  && if ((t .params .containsKey ("user" )&& t .is ("NormTX" )&& t .params .get ("DTYPE" ).equals ("LoadTXEntry" ))||(t .is ("CallCentreLoadTX" )))) {

try{

synchronized(_asp_project0.lock){

RunningClock.updateNow(timestamp);
String s;
double amount;
HashMap limits;
String loadtype;
String currency;
s =t .params .get ("user" ).toString ();
currency =SC .getLoadCurrency ((String )t .params .get ("transactionEntryId" ));
amount =SC .getLoadAmount ((String )t .params .get ("transactionEntryId" ));
limits =new HashMap ();
loadtype =(t .is ("CallCentreLoadTX" ))?"CALLCENTRE_LOAD" :"LOAD" ;

_cls_project1 _cls_inst = _cls_project1._get_cls_project1_inst(false,s);
_cls_inst.timestamp = timestamp;
_cls_inst.t = t;
_cls_inst.name = name;
_cls_project1.amount = amount;
_cls_project1.limits = limits;
_cls_project1.loadtype = loadtype;
_cls_project1.currency = currency;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 10/*loadevent*/);
 _cls_inst.updateInDB();
}

}catch(Exception ex) {ex.printStackTrace();}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c)  && (if (_c.name.equals("drp"))) && (if (millis == 2851200000l))) {

try{

synchronized(_asp_project0.lock){
String s;
long timestamp;
s =null ;
timestamp =RunningClock .eventTime ;

_cls_project1.timestamp = timestamp;
synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 4/*cATdrp*/);
 //Note that the code below does not work... the instances in the contexts below are not called!
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*cATdrp*/);
}
}
 _c.updateInDB(true);
}

}catch(Exception ex) {ex.printStackTrace();}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c)  && (if (_c.name.equals("idp"))) && (if (millis == 15811200000l))) {

try{

synchronized(_asp_project0.lock){
String s;
long timestamp;
s =null ;
timestamp =RunningClock .eventTime ;

_cls_project1.timestamp = timestamp;
synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 2/*cATidp*/);
 //Note that the code below does not work... the instances in the contexts below are not called!
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*cATidp*/);
}
}
 _c.updateInDB(true);
}

}catch(Exception ex) {ex.printStackTrace();}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c)  && (if (_c.name.equals("bp"))) && (if (millis == 31363200000l))) {

try{

synchronized(_asp_project0.lock){
String s;
long timestamp;
s =null ;
timestamp =RunningClock .eventTime ;

_cls_project1.timestamp = timestamp;
synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 6/*cATbp*/);
 //Note that the code below does not work... the instances in the contexts below are not called!
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*cATbp*/);
}
}
 _c.updateInDB(true);
}

}catch(Exception ex) {ex.printStackTrace();}
}
}