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

public class _cls_Auth0 implements _callable{

public static PrintWriter pw; 
public static _cls_Auth0 root;

public static HashMap<_cls_Auth0,_cls_Auth0> _cls_Auth0_instances = new HashMap<_cls_Auth0,_cls_Auth0>();
static{
try{
pw = new PrintWriter("C:\\Users\\University User\\Desktop\\projects\\NestingTesting\\src\\\\output_Auth.txt");

root = new _cls_Auth0();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_Auth0 parent; //to remain null - this class does not have a parent!
int no_automata = 0;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_Auth0() {
}

public static _cls_Auth0 _get_cls_Auth0_inst() {
 return root;
}

public boolean equals(Object o) {
 if ((o instanceof _cls_Auth0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
}

public void _call_all_filtered(String _info, int... _event){

for (_cls_Auth1 _inst : _cls_Auth1._cls_Auth1_instances.keySet())
if (true){
_inst._call(_info, _event); 
_inst._call_all_filtered(_info, _event);
}
}

public static void _call_all(String _info, int... _event){

for (_cls_Auth0 _inst : _cls_Auth0_instances.keySet())
_inst._call(_info, _event);
}

public void _killThis(){
if (--no_automata <= 0){
 _cls_Auth0_instances.remove(this);
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