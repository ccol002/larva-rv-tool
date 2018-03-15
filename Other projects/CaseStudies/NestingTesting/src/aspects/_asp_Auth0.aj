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
public aspect _asp_Auth0 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_Auth0.initialize();
}
}
}