package aspects;
import main.*;
import larva.*;
public aspect _asp_minepump0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_minepump0.initialize();
}
}
before ( Main __3) : (call(* Main.initialize(..)) && target(__3) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_minepump0.lock){

_cls_minepump0 _cls_inst = _cls_minepump0._get_cls_minepump0_inst();
_cls_inst.__3 = __3;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*initializationEvent*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*initializationEvent*/);
}
}
before ( Main __0) : (call(* Main.start(..)) && target(__0) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_minepump0.lock){
boolean _b;
boolean D;
boolean Alarm;
_b =true ;
D =true ;
Alarm =false ;

_cls_minepump0 _cls_inst = _cls_minepump0._get_cls_minepump0_inst();
_cls_inst.__0 = __0;
_cls_minepump0._b = _b;
_cls_minepump0.D = D;
_cls_minepump0.Alarm = Alarm;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*periodicEvent*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*periodicEvent*/);
}
}
before ( ALARM __2) : (call(* ALARM.on(..)) && target(__2) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_minepump0.lock){
boolean _b;
boolean D;
boolean Alarm;
_b =false ;
D =DH2O .status ;
Alarm =ALARM .status ;

_cls_minepump0 _cls_inst = _cls_minepump0._get_cls_minepump0_inst();
_cls_inst.__2 = __2;
_cls_minepump0._b = _b;
_cls_minepump0.D = D;
_cls_minepump0.Alarm = Alarm;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*Alarm_event*/, 8/*periodicEvent*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*Alarm_event*/, 8/*periodicEvent*/);
}
}
before ( DH2O __1) : (call(* DH2O.on(..)) && target(__1) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_minepump0.lock){
boolean _b;
boolean D;
boolean Alarm;
_b =false ;
D =DH2O .status ;
Alarm =ALARM .status ;

_cls_minepump0 _cls_inst = _cls_minepump0._get_cls_minepump0_inst();
_cls_inst.__1 = __1;
_cls_minepump0._b = _b;
_cls_minepump0.D = D;
_cls_minepump0.Alarm = Alarm;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*D_event*/, 8/*periodicEvent*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*D_event*/, 8/*periodicEvent*/);
}
}
}