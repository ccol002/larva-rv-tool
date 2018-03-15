package larva;

import nesting.User;
import nesting.Account;
import nesting.Transaction;

public aspect _asp_bank2 {
before ( Account a1) : (call(* Account.deleteTransaction(..)) && target(a1) && !cflow(adviceexecution())) {
Account a;
User u;
a =a1 ;
u =a1 .owner ;

_cls_bank2 _cls_inst = _cls_bank2._get_cls_bank2_inst( a,u);
_cls_inst.a1 = a1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 12/*deleteTransaction*/, 14/*allTransactions*/);
}

before ( Account a1) : (call(* Account.addTransaction(..)) && target(a1) && !cflow(adviceexecution())) {
Account a;
User u;
a =a1 ;
u =a1 .owner ;

_cls_bank2 _cls_inst = _cls_bank2._get_cls_bank2_inst( a,u);
_cls_inst.a1 = a1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 10/*addTransaction*/, 14/*allTransactions*/);
}


}