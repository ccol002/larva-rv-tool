package aspects;

import com.ccbill.tgs.tsm.TSMRunnableObject;
import com.ccbill.tgs.tsm.states.AuthKeyVerifier;
import com.ccbill.tgs.tsm.states.ProcessorLoader;
import com.ccbill.tgs.tsm.states.MerchantInfoDefaultLoader;
import com.ccbill.tgs.tsm.states.TransactionInserter;
import com.ccbill.tgs.tsm.states.PassThroughInserter;
import com.ccbill.tgs.tsm.states.PMSender;
import com.ccbill.tgs.tsm.states.BacklogInserter;
import com.ccbill.tgs.tsm.states.ExceptionChecker;
import com.ccbill.tgs.tsm.states.ResponseInserter;
import com.ccbill.tgs.tsm.states.AuthCapturer;
import com.ccbill.tgs.tsm.states.MISender;
import com.ccbill.tgs.transactions.structure.Transaction;
import com.ccbill.tgs.transactions.structure.Transaction.TransactionType;


import com.ccbill.tgs.transactions.structure.ACHTransaction;
import com.ccbill.tgs.transactions.structure.CCTransaction;



import larva.*;
public aspect _asp_Auth1 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_Auth1.initialize();
}
}
before ( TSMRunnableObject tsm1,ResponseInserter __8) : (call(* ResponseInserter.save(..)) && target(__8) && args(tsm1) && !cflow(adviceexecution())) {
TSMRunnableObject tsm;
tsm =tsm1 ;

_cls_Auth1 _cls_inst = _cls_Auth1._get_cls_Auth1_inst( tsm);
_cls_inst.tsm1 = tsm1;
_cls_inst.__8 = __8;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 18/*respIns*/, 22/*all*/);
}
before ( TSMRunnableObject tsm1,PMSender __5) : (call(* PMSender.send(..)) && target(__5) && args(tsm1) && !cflow(adviceexecution())) {
TSMRunnableObject tsm;
tsm =tsm1 ;

_cls_Auth1 _cls_inst = _cls_Auth1._get_cls_Auth1_inst( tsm);
_cls_inst.tsm1 = tsm1;
_cls_inst.__5 = __5;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 12/*pmSender*/, 22/*all*/);
}
before ( AuthKeyVerifier __0,TSMRunnableObject tsm1) : (call(* AuthKeyVerifier.verifyAuthKey(..)) && target(__0) && args(tsm1) && !cflow(adviceexecution())) {
TSMRunnableObject tsm;
tsm =tsm1 ;

_cls_Auth1 _cls_inst = _cls_Auth1._get_cls_Auth1_inst( tsm);
_cls_inst.__0 = __0;
_cls_inst.tsm1 = tsm1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*verify*/, 22/*all*/);
}
before ( ExceptionChecker __7,TSMRunnableObject tsm1) : (call(* ExceptionChecker.check(..)) && target(__7) && args(tsm1) && !cflow(adviceexecution())) {
TSMRunnableObject tsm;
tsm =tsm1 ;

_cls_Auth1 _cls_inst = _cls_Auth1._get_cls_Auth1_inst( tsm);
_cls_inst.__7 = __7;
_cls_inst.tsm1 = tsm1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 16/*excCheck*/, 22/*all*/);
}
before ( TSMRunnableObject tsm1,BacklogInserter __6) : (call(* BacklogInserter.backlogProcessor(..)) && target(__6) && args(tsm1) && !cflow(adviceexecution())) {
TSMRunnableObject tsm;
tsm =tsm1 ;

_cls_Auth1 _cls_inst = _cls_Auth1._get_cls_Auth1_inst( tsm);
_cls_inst.tsm1 = tsm1;
_cls_inst.__6 = __6;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 14/*backlog*/, 22/*all*/);
}
before ( TSMRunnableObject tsm1) : (call(* TSMRunnableObject.processTransaction(..)) && target(tsm1) && !cflow(adviceexecution())) {
TSMRunnableObject tsm;
tsm =tsm1 ;

_cls_Auth1 _cls_inst = _cls_Auth1._get_cls_Auth1_inst( tsm);
_cls_inst.tsm1 = tsm1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*proc*/, 22/*all*/);
}
before ( TransactionInserter __3,TSMRunnableObject tsm1) : (call(* TransactionInserter.save(..)) && target(__3) && args(tsm1) && !cflow(adviceexecution())) {
TSMRunnableObject tsm;
tsm =tsm1 ;

_cls_Auth1 _cls_inst = _cls_Auth1._get_cls_Auth1_inst( tsm);
_cls_inst.__3 = __3;
_cls_inst.tsm1 = tsm1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*transIns*/, 22/*all*/);
}
before ( TSMRunnableObject tsm1,PassThroughInserter __4) : (call(* PassThroughInserter.save(..)) && target(__4) && args(tsm1) && !cflow(adviceexecution())) {
TSMRunnableObject tsm;
tsm =tsm1 ;

_cls_Auth1 _cls_inst = _cls_Auth1._get_cls_Auth1_inst( tsm);
_cls_inst.tsm1 = tsm1;
_cls_inst.__4 = __4;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 10/*passThrough*/, 22/*all*/);
}
before ( TSMRunnableObject tsm1,MISender __9) : (call(* MISender.send(..)) && target(__9) && args(tsm1) && !cflow(adviceexecution())) {
TSMRunnableObject tsm;
tsm =tsm1 ;

_cls_Auth1 _cls_inst = _cls_Auth1._get_cls_Auth1_inst( tsm);
_cls_inst.tsm1 = tsm1;
_cls_inst.__9 = __9;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 20/*MISend*/, 22/*all*/);
}
before ( ProcessorLoader __1,TSMRunnableObject tsm1) : (call(* ProcessorLoader.load(..)) && target(__1) && args(tsm1) && !cflow(adviceexecution())) {
TSMRunnableObject tsm;
tsm =tsm1 ;

_cls_Auth1 _cls_inst = _cls_Auth1._get_cls_Auth1_inst( tsm);
_cls_inst.__1 = __1;
_cls_inst.tsm1 = tsm1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*procLoader*/, 22/*all*/);
}
before ( MerchantInfoDefaultLoader __2,TSMRunnableObject tsm1) : (call(* MerchantInfoDefaultLoader.load(..)) && target(__2) && args(tsm1) && !cflow(adviceexecution())) {
TSMRunnableObject tsm;
tsm =tsm1 ;

_cls_Auth1 _cls_inst = _cls_Auth1._get_cls_Auth1_inst( tsm);
_cls_inst.__2 = __2;
_cls_inst.tsm1 = tsm1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*merLoader*/, 22/*all*/);
}
}