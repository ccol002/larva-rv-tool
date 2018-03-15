package larva;


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



import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_Auth1 implements _callable{

public static HashMap<_cls_Auth1,_cls_Auth1> _cls_Auth1_instances = new HashMap<_cls_Auth1,_cls_Auth1>();

_cls_Auth0 parent;
public static TransactionInserter __3;
public static MerchantInfoDefaultLoader __2;
public static ProcessorLoader __1;
public static AuthKeyVerifier __0;
public static ExceptionChecker __7;
public static BacklogInserter __6;
public static TSMRunnableObject tsm1;
public static PMSender __5;
public static PassThroughInserter __4;
public static MISender __9;
public static ResponseInserter __8;
public TSMRunnableObject tsm;
int no_automata = 2;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_Auth1( TSMRunnableObject tsm) {
parent = _cls_Auth0._get_cls_Auth0_inst();
this.tsm = tsm;
}

public static _cls_Auth1 _get_cls_Auth1_inst( TSMRunnableObject tsm) {
_cls_Auth1 _inst = new _cls_Auth1( tsm);
if (_cls_Auth1_instances.containsKey(_inst))
{
_cls_Auth1 tmp = _cls_Auth1_instances.get(_inst);
 return _cls_Auth1_instances.get(_inst);
}
else
{
 _cls_Auth1_instances.put(_inst,_inst);
 return _inst;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_Auth1)
 && ( equateTSM(tsm, ((_cls_Auth1)o).tsm))
 && (parent == null || parent.equals(((_cls_Auth1)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
_performLogic_auth2(_info, _event);
_performLogic_auth1(_info, _event);
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

for (_cls_Auth1 _inst : _cls_Auth1_instances.keySet())
_inst._call(_info, _event);
}

public void _killThis(){
if (--no_automata <= 0){
 _cls_Auth1_instances.remove(this);
}
}

int _state_id_auth2 = 19;

public void _performLogic_auth2(String _info, int... _event) {

_cls_Auth0.pw.println("AUTOMATON::> auth2("+toStringTSM(tsm) + " " + ") STATE::>"+ _string_auth2(_state_id_auth2, 0));
_cls_Auth0.pw.flush();

if (0==1){}
else if (_state_id_auth2==14){
		if (1==0){}
		else if ((_occurredEvent(_event,14/*backlog*/))){
		
		_state_id_auth2 = 15;//moving to state backlog
		_goto_auth2(_info);
		}
		else if ((_occurredEvent(_event,22/*all*/))){
		
		_state_id_auth2 = 13;//moving to state bad_state
		_goto_auth2(_info);
		}
}
else if (_state_id_auth2==15){
		if (1==0){}
		else if ((_occurredEvent(_event,16/*excCheck*/))){
		
		_state_id_auth2 = 18;//moving to state exc_checker
		_goto_auth2(_info);
		}
		else if ((_occurredEvent(_event,22/*all*/))){
		
		_state_id_auth2 = 13;//moving to state bad_state
		_goto_auth2(_info);
		}
}
else if (_state_id_auth2==19){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*proc*/)) && (tsm.getTransaction ().getType ()==TransactionType.CCAuth &&tsm.getTransaction ().getCurrentStateID ()>0 )){
		
		_state_id_auth2 = 14;//moving to state after_pm
		_goto_auth2(_info);
		}
}
else if (_state_id_auth2==18){
		if (1==0){}
		else if ((_occurredEvent(_event,18/*respIns*/))){
		
		_state_id_auth2 = 17;//moving to state resp_inserter
		_goto_auth2(_info);
		}
		else if ((_occurredEvent(_event,22/*all*/))){
		
		_state_id_auth2 = 13;//moving to state bad_state
		_goto_auth2(_info);
		}
}
else if (_state_id_auth2==17){
		if (1==0){}
		else if ((_occurredEvent(_event,20/*MISend*/))){
		
		_state_id_auth2 = 12;//moving to state ready_two
_cls_Auth0.pw.println ("TSM with transaction id: "+tsm.getTransaction ().getGeneratedTransactionId ()+" Ready from second part as well!");

		_goto_auth2(_info);
		}
		else if ((_occurredEvent(_event,22/*all*/))){
		
		_state_id_auth2 = 13;//moving to state bad_state
		_goto_auth2(_info);
		}
}
}

public void _goto_auth2(String _info){
_cls_Auth0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_auth2(_state_id_auth2, 1));
_cls_Auth0.pw.flush();
}

public String _string_auth2(int _state_id, int _mode){
switch(_state_id){
case 14: if (_mode == 0) return "after_pm"; else return "after_pm";
case 12: if (_mode == 0) return "ready_two"; else return "(((SYSTEM REACHED AN ACCEPTED STATE)))  ready_two";
case 15: if (_mode == 0) return "backlog"; else return "backlog";
case 19: if (_mode == 0) return "start"; else return "start";
case 13: if (_mode == 0) return "bad_state"; else return "!!!SYSTEM REACHED BAD STATE!!! bad_state "+new _BadStateExceptionAuth().toString()+" ";
case 18: if (_mode == 0) return "exc_checker"; else return "exc_checker";
case 17: if (_mode == 0) return "resp_inserter"; else return "resp_inserter";
case 16: if (_mode == 0) return "pass_through"; else return "pass_through";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_auth1 = 11;

public void _performLogic_auth1(String _info, int... _event) {

_cls_Auth0.pw.println("AUTOMATON::> auth1("+toStringTSM(tsm) + " " + ") STATE::>"+ _string_auth1(_state_id_auth1, 0));
_cls_Auth0.pw.flush();

if (0==1){}
else if (_state_id_auth1==11){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*proc*/)) && (tsm.getTransaction ().getType ()==TransactionType.CCAuth &&tsm.getTransaction ().getCurrentStateID ()==0 )){
		
		_state_id_auth1 = 2;//moving to state startAuth
		_goto_auth1(_info);
		}
}
else if (_state_id_auth1==6){
		if (1==0){}
		else if ((_occurredEvent(_event,10/*passThrough*/))){
		
		_state_id_auth1 = 8;//moving to state pass_through
		_goto_auth1(_info);
		}
		else if ((_occurredEvent(_event,22/*all*/))){
		
		_state_id_auth1 = 1;//moving to state bad_state
		_goto_auth1(_info);
		}
}
else if (_state_id_auth1==3){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*procLoader*/))){
		
		_state_id_auth1 = 4;//moving to state process_loader
		_goto_auth1(_info);
		}
		else if ((_occurredEvent(_event,22/*all*/))){
		
		_state_id_auth1 = 1;//moving to state bad_state
		_goto_auth1(_info);
		}
}
else if (_state_id_auth1==5){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*transIns*/))){
		
		_state_id_auth1 = 6;//moving to state trans_inserter
		_goto_auth1(_info);
		}
		else if ((_occurredEvent(_event,22/*all*/))){
		
		_state_id_auth1 = 1;//moving to state bad_state
		_goto_auth1(_info);
		}
}
else if (_state_id_auth1==8){
		if (1==0){}
		else if ((_occurredEvent(_event,12/*pmSender*/))){
		
		_state_id_auth1 = 0;//moving to state ready_one
_cls_Auth0.pw.println ("TSM with transaction id: "+tsm.getTransaction ().getGeneratedTransactionId ()+" Ready sending to PM!");

		_goto_auth1(_info);
		}
		else if ((_occurredEvent(_event,22/*all*/))){
		
		_state_id_auth1 = 1;//moving to state bad_state
		_goto_auth1(_info);
		}
}
else if (_state_id_auth1==2){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*verify*/))){
		
		_state_id_auth1 = 3;//moving to state auth_key_ver
		_goto_auth1(_info);
		}
		else if ((_occurredEvent(_event,22/*all*/))){
		
		_state_id_auth1 = 1;//moving to state bad_state
		_goto_auth1(_info);
		}
}
else if (_state_id_auth1==4){
		if (1==0){}
		else if ((_occurredEvent(_event,6/*merLoader*/))){
		
		_state_id_auth1 = 5;//moving to state merchant_loader
		_goto_auth1(_info);
		}
		else if ((_occurredEvent(_event,22/*all*/))){
		
		_state_id_auth1 = 1;//moving to state bad_state
		_goto_auth1(_info);
		}
}
}

public void _goto_auth1(String _info){
_cls_Auth0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_auth1(_state_id_auth1, 1));
_cls_Auth0.pw.flush();
}

public String _string_auth1(int _state_id, int _mode){
switch(_state_id){
case 0: if (_mode == 0) return "ready_one"; else return "(((SYSTEM REACHED AN ACCEPTED STATE)))  ready_one";
case 7: if (_mode == 0) return "backlog"; else return "backlog";
case 11: if (_mode == 0) return "start"; else return "start";
case 1: if (_mode == 0) return "bad_state"; else return "!!!SYSTEM REACHED BAD STATE!!! bad_state "+new _BadStateExceptionAuth().toString()+" ";
case 6: if (_mode == 0) return "trans_inserter"; else return "trans_inserter";
case 3: if (_mode == 0) return "auth_key_ver"; else return "auth_key_ver";
case 10: if (_mode == 0) return "resp_inserter"; else return "resp_inserter";
case 5: if (_mode == 0) return "merchant_loader"; else return "merchant_loader";
case 9: if (_mode == 0) return "mi_send"; else return "mi_send";
case 8: if (_mode == 0) return "pass_through"; else return "pass_through";
case 2: if (_mode == 0) return "startAuth"; else return "startAuth";
case 4: if (_mode == 0) return "process_loader"; else return "process_loader";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}










static boolean equateTSM(TSMRunnableObject tsm, TSMRunnableObject tsm2)
{
if (tsm.getTransaction().getGeneratedTransactionId() == 0)
return tsm.equals(tsm2);
else {
if (tsm.getTransaction().getGeneratedTransactionId()
== tsm2.getTransaction().getGeneratedTransactionId())
return true;
else
return false;
}
}













static boolean checkAssets(TSMRunnableObject tsm, TSMRunnableObject tsm2)
{
if (tsm.getTransaction() instanceof CCTransaction)
{
_cls_Auth0.pw.println("Amount being compared: " + ((CCTransaction)tsm.getTransaction()).getCurrency().getAmount());
return ((CCTransaction)tsm.getTransaction()).getCurrency().getAmount() == ((CCTransaction)tsm2.getTransaction()).getCurrency().getAmount();
}
else if (tsm.getTransaction() instanceof ACHTransaction)
{
_cls_Auth0.pw.println("Amount being compared: " + ((ACHTransaction)tsm.getTransaction()).getCurrency().getAmount());
return ((ACHTransaction)tsm.getTransaction()).getCurrency().getAmount() == ((ACHTransaction)tsm2.getTransaction()).getCurrency().getAmount();

}
return true;
}

static String toStringTSM(TSMRunnableObject tsm)
{
return new Long(tsm.getTransaction().getGeneratedTransactionId()).toString();
}
}