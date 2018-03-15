package benchmark;
import java.util.ArrayList;
import java.util.*;

public aspect MonitorAspect {
	private int User.count = 0;
	private int User.Transcount_state = 0;
	public int User.hashCode() { return Transcount_state;
}
public boolean User.equals(Object o) {
	if (! (o instanceof User)) return false;
	return Transcount_state == ((User)o).Transcount_state;};
	
	
	pointcut Transcount_add0(User thisObject) : target(thisObject) &&  call(* *.addTransaction());
	after (User thisObject) returning: Transcount_add0(thisObject) {
		boolean add = false;
		add = true;
		thisObject.count++;
		switch (thisObject.Transcount_state) {
			case 0 :
			thisObject.Transcount_state = add ? 1 : -1;
			break;
			case 1 :
			thisObject.Transcount_state = add ? 1 : -1;
			break;
			default:
			thisObject.Transcount_state = -1;
			}
			if (thisObject.Transcount_state == 1){
				if (thisObject.count < 5)
				System.out.println("ok!");
				else
				System.out.println("qbadtek!");
			}
			}
		}



