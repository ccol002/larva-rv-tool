package transactionsystem;

public class UserSession {
	protected Integer sid;
	protected String log;
	protected Integer owner;
	static public Integer countLog = 0;
	
	public UserSession(Integer uid, Integer sid) {
		this.sid = sid;
		owner = uid;
		log = "";
	}

	public Integer getId() { return sid; }
	public Integer getOwner() { return owner; }
	public String getLog() { return log; }
	public void openSession() {
	}
	public void log(String l) 
	{ 
		log+=l+"\n"; 
		countLog++;
	}
	public void closeSession() 
	{
	}
	
	
}
