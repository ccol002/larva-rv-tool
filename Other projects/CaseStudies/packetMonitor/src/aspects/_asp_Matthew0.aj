package aspects;

import larva.*;
public aspect _asp_Matthew0 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_Matthew0.initialize();
}
}
before ( String username) : (call(* *.logIn(..)) && args(username) && !cflow(adviceexecution())) {

_cls_Matthew0 _cls_inst = _cls_Matthew0._get_cls_Matthew0_inst();
_cls_inst.username = username;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*loginAttempt*/);
}
before () : (call(* *.logOut(..)) && !cflow(adviceexecution())) {

_cls_Matthew0 _cls_inst = _cls_Matthew0._get_cls_Matthew0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*loggedOut*/);
}
}