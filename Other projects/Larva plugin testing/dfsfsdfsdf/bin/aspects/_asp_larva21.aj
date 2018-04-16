package aspects;

import java.util.Iterator;
import java.util.Collection;

import larva.*;
public aspect _asp_larva21 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_larva21.initialize();
}
}
before ( Collection c1) : (call(* Collection+.iterator(..)) && target(c1) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*)) && if (!Thread .holdsLock (c ))) {

synchronized(_asp_larva20.lock){
Collection c;
c =c1 ;

_cls_larva21 _cls_inst = _cls_larva21._get_cls_larva21_inst( c);
_cls_inst.c1 = c1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*asyncAccess*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*asyncAccess*/);
}
}
before ( Collection c1) : (call(* Collection+.synchr*(..)) && target(c1) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_larva20.lock){
Collection c;
c =c1 ;

_cls_larva21 _cls_inst = _cls_larva21._get_cls_larva21_inst( c);
_cls_inst.c1 = c1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*sync*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*sync*/);
}
}
}