package aspects;

import larva.*;
public aspect _asp_clocktest0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_clocktest0.initialize();
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c)  && (if (_c.name.equals("b"))) && (if (millis == 500)) && !cflow(adviceexecution())) {

synchronized(_asp_clocktest0.lock){

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 0/*b_at_1*/);
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*b_at_1*/);
}
}
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c)  && (if (_c.name.equals("c"))) && (if (millis == 500)) && !cflow(adviceexecution())) {

synchronized(_asp_clocktest0.lock){

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 2/*c_at_1*/);
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*c_at_1*/);
}
}
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c)  && (if (_c.name.equals("e"))) && (if (millis == 5000)) && !cflow(adviceexecution())) {

synchronized(_asp_clocktest0.lock){

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 6/*e_at_1*/);
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*e_at_1*/);
}
}
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c)  && (if (_c.name.equals("d"))) && (if (millis == 500)) && !cflow(adviceexecution())) {

synchronized(_asp_clocktest0.lock){

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 4/*d_at_1*/);
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*d_at_1*/);
}
}
}
}
}