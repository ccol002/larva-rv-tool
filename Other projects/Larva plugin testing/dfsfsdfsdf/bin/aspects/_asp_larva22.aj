package aspects;

import java.util.Iterator;
import java.util.Collection;

import larva.*;
public aspect _asp_larva22 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_larva22.initialize();
}
}
after ( Collection c1) returning (Iterator i1) : (call(* Collection+.iterator(..)) && target(c1) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*)) && if (Thread .holdsLock (c ))) {

synchronized(_asp_larva20.lock){
Iterator i;
Collection c;
c =c1 ;
i =i1 ;

_cls_larva22 _cls_inst = _cls_larva22._get_cls_larva22_inst( i,c);
_cls_inst.c1 = c1;
_cls_inst.i1 = i1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*syncAccess*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*syncAccess*/);
}
}
}