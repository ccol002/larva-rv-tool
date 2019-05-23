package editMe;



import static events.EventGenerator.*;
import events.Filter;
import events.Template;

public class EditMe {

	
	public static void configuration()
	{		
		
		//can be set by -s switch
		starttime = 0l;
		//can be set by -e switch
		endtime = 9999999999999l;
		//can be set by -url switch
		url = "jdbc:mysql://localhost:3306/audit";
		//can be set by -un switch
		un = "root";
		//can be set by -pw switch
		pw = "";
		
			
		
		//register database events that are visible from the specification
		
		//filters on the same field are disjuncted while filters on different fields are conjuncted
		registerDBEvent(new Template("login", "login_log", "audit", "login_time", Template.DATETIME_TIMESTAMP)
						.addFilter(new Filter("user_id"))
						.addFilter(new Filter("login",Filter.NOT_EQUALS,"0"))
					   );
		
		//SQL can be used to directly identify relevant events 
		registerDBEvent(new Template("event_name", "SELECT * FROM login_log", "login_time", Template.DATETIME_TIMESTAMP)
						.addFilter(new Filter("user_id"))
					   );
	}
}
