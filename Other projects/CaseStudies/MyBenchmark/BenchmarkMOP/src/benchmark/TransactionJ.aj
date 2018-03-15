/*+ Monitor aspect for +*/
package benchmark;
import java.util.*;

public aspect TransactionJ {
	double Transaction.amount1 = 0;
	double Transaction.oldamount = 0;
	int Transaction.TransExc_state = 0;
	pointcut TransExc_exec10(Transaction thisObject, double d) : target(thisObject) &&  call(* Transaction.setAmount(double))&& args(d);
	after (Transaction thisObject, double d) returning: TransExc_exec10(thisObject, d) {
		boolean exec1 = false;
		exec1 = true;
		thisObject.amount1 = d;
		switch (thisObject.TransExc_state) {
			case 0 :
			thisObject.TransExc_state = exec1 ? 1 : -1;
			break;
		}
		if (thisObject.TransExc_state == 1){
			if (thisObject.amount1 != thisObject.oldamount && thisObject.oldamount != 0)
			System.out.println("Cannot change thisObject.amount!!!");
			else
			thisObject.oldamount = thisObject.amount1;
		}
	}
}