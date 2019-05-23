package larva;


import Events.Template;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import java.util.LinkedHashMap;
import java.util.HashMap;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.io.PrintWriter;
import java.sql.*;

public class _cls_project0 implements _callable{
public long pk = 0;

public static PrintWriter _pw; 
public static _cls_project0 root;
public static String _url;
 public static Connection _conn;
public static long pks = 0;
public static Channel balCheckCH = new Channel();
public static Channel balUpdateCH = new Channel();
public static Channel validTXCH = new Channel();
static{
  _cls_project0.initialize();
  _cls_project1.initialize();
  _cls_project2.initialize(); 
}

_cls_project0 parent; //to remain null - this class does not have a parent!
public static long timestamp;
public static Template t;
public static String name;
int no_automata = 1;
public int idp_limit =180 *24 *60 *60 ;
public int drp_limit =30 *24 *60 *60 ;
public int bwp_limit =330 *24 *60 *60 ;
public int bp_limit =360 *24 *60 *60 ;
public int leeway =3 *24 *60 *60 *1000 ;
public boolean ignore_blocked_dorm =true ;
public boolean ignore_frozen_dorm =true ;
public boolean ignore_suspended_dorm =true ;
public boolean ignore_blocked_break =true ;
public boolean ignore_frozen_break =true ;
public boolean ignore_suspended_break =true ;
public boolean ignore_blocked_warn =true ;
public boolean ignore_frozen_warn =true ;
public boolean ignore_suspended_warn =true ;
public double dormancy_fee =10 ;
public int day =24 *60 *60 *1000 ;
public int month =30 *24 *60 *60 *1000 ;

public long getPk(){return pk;}

public static void forceClassLoad(){}

public static void initialize(){ 
 try {
java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(" (yyyy-MM-dd) [HH mm ss S]");
java.util.Date date = new java.util.Date();
String datetime = dateFormat.format(date);dateFormat = new java.text.SimpleDateFormat(" yyyy-MM-dd HH:mm:ss:S");
_pw = new PrintWriter("C:\\Documents and Settings\\ixaris\\Desktop\\projects3\\Event Generator\\outputs\\\\_output_project" + datetime + ".txt");

_pw.println("[LARVA] Monitoring started at " + dateFormat.format(date));
_pw.flush();
 _url = "jdbc:odbc:test";
 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
 _conn = DriverManager.getConnection(_url);
 if ((SC.init )) {
 Statement stat = _cls_project0._conn.createStatement();
  stat.execute("drop table if exists _clocks;");
stat.execute("create table _clocks (id bigint(20) primary key,name varchar(255),class_name varchar(255),enabled bool,inst bigint(20),paused bool,thison bool,_starting bigint(20),durationPaused bigint(20),whenPaused bigint(20),ev0 bigint(20),ev1 bigint(20),ev2 bigint(20),ev3 bigint(20),ev4 bigint(20));");
  Statement stat1 = _cls_project0._conn.createStatement();
  stat1.execute("drop table if exists _cls_project0;");
stat1.execute("create table _cls_project0 (_id bigint(20) primary key,_idp_limit int(10),_drp_limit int(10),_bwp_limit int(10),_bp_limit int(10),_leeway int(10),_ignore_blocked_dorm bool,_ignore_frozen_dorm bool,_ignore_suspended_dorm bool,_ignore_blocked_break bool,_ignore_frozen_break bool,_ignore_suspended_break bool,_ignore_blocked_warn bool,_ignore_frozen_warn bool,_ignore_suspended_warn bool,_dormancy_fee double(12,2),_day int(10),_month int(10),_state_id_properties int(10),_no_automata int(10));");
root = new _cls_project0();
  root.initialisation();
root.specialInitialization(SC.initializeifMain());
  root.insertInDB();
}//of initialization condition 
 else {
   Statement stat1 = _cls_project0._conn.createStatement();
   ResultSet rs = stat1.executeQuery("select max(_id) as cnt from _cls_project0;");
   if (rs.next())
      pks = rs.getInt("cnt") + 1;
   rs = stat1.executeQuery("select max(id) as cnt from _clocks;");
   if (rs.next())
      Clock.pks = rs.getInt("cnt") + 1;
  }
 } catch (Exception ex) { ex.printStackTrace();} 
}

public void updateInDB() { 
try {
 Statement stat = _cls_project0._conn.createStatement();
 stat.execute("update _cls_project0 set _idp_limit = \"" + idp_limit + "\",_drp_limit = \"" + drp_limit + "\",_bwp_limit = \"" + bwp_limit + "\",_bp_limit = \"" + bp_limit + "\",_leeway = \"" + leeway + "\",_ignore_blocked_dorm = \"" + ((ignore_blocked_dorm)?(1):(0)) + "\",_ignore_frozen_dorm = \"" + ((ignore_frozen_dorm)?(1):(0)) + "\",_ignore_suspended_dorm = \"" + ((ignore_suspended_dorm)?(1):(0)) + "\",_ignore_blocked_break = \"" + ((ignore_blocked_break)?(1):(0)) + "\",_ignore_frozen_break = \"" + ((ignore_frozen_break)?(1):(0)) + "\",_ignore_suspended_break = \"" + ((ignore_suspended_break)?(1):(0)) + "\",_ignore_blocked_warn = \"" + ((ignore_blocked_warn)?(1):(0)) + "\",_ignore_frozen_warn = \"" + ((ignore_frozen_warn)?(1):(0)) + "\",_ignore_suspended_warn = \"" + ((ignore_suspended_warn)?(1):(0)) + "\",_dormancy_fee = \"" + dormancy_fee + "\",_day = \"" + day + "\",_month = \"" + month + "\", _state_id_properties = \"" + _state_id_properties + "\", _no_automata = \"" + no_automata + "\" where _id = " + pk + ";"); 
 } catch (Exception ex) {
 ex.printStackTrace();
}
}

public void insertInDB() { 
try {
 Statement stat = _cls_project0._conn.createStatement();
 stat.execute("insert into _cls_project0 values (\"" + pk + "\",\"" + idp_limit + "\",\"" + drp_limit + "\",\"" + bwp_limit + "\",\"" + bp_limit + "\",\"" + leeway + "\",\"" + ((ignore_blocked_dorm)?(1):(0)) + "\",\"" + ((ignore_frozen_dorm)?(1):(0)) + "\",\"" + ((ignore_suspended_dorm)?(1):(0)) + "\",\"" + ((ignore_blocked_break)?(1):(0)) + "\",\"" + ((ignore_frozen_break)?(1):(0)) + "\",\"" + ((ignore_suspended_break)?(1):(0)) + "\",\"" + ((ignore_blocked_warn)?(1):(0)) + "\",\"" + ((ignore_frozen_warn)?(1):(0)) + "\",\"" + ((ignore_suspended_warn)?(1):(0)) + "\",\"" + dormancy_fee + "\",\"" + day + "\",\"" + month + "\",\"" + _state_id_properties + "\",\"" + no_automata + "\");"); 
 } catch (Exception ex) {
 ex.printStackTrace();
}
}

public static _cls_project0 loadFromDB(  ) { 
try {
 Statement stat = _cls_project0._conn.createStatement();
 ResultSet rs = stat.executeQuery("select * from _cls_project0;");
 if (rs.next()) {
 _cls_project0 temp = new _cls_project0();
 temp.pk = rs.getLong("_id");
 temp.idp_limit = rs.getInt("_idp_limit");
 temp.drp_limit = rs.getInt("_drp_limit");
 temp.bwp_limit = rs.getInt("_bwp_limit");
 temp.bp_limit = rs.getInt("_bp_limit");
 temp.leeway = rs.getInt("_leeway");
 temp.ignore_blocked_dorm = rs.getBoolean("_ignore_blocked_dorm");
 temp.ignore_frozen_dorm = rs.getBoolean("_ignore_frozen_dorm");
 temp.ignore_suspended_dorm = rs.getBoolean("_ignore_suspended_dorm");
 temp.ignore_blocked_break = rs.getBoolean("_ignore_blocked_break");
 temp.ignore_frozen_break = rs.getBoolean("_ignore_frozen_break");
 temp.ignore_suspended_break = rs.getBoolean("_ignore_suspended_break");
 temp.ignore_blocked_warn = rs.getBoolean("_ignore_blocked_warn");
 temp.ignore_frozen_warn = rs.getBoolean("_ignore_frozen_warn");
 temp.ignore_suspended_warn = rs.getBoolean("_ignore_suspended_warn");
 temp.dormancy_fee = rs.getDouble("_dormancy_fee");
 temp.day = rs.getInt("_day");
 temp.month = rs.getInt("_month");
 temp._state_id_properties = rs.getInt("_state_id_properties");
 temp.no_automata = rs.getInt("_no_automata");
 return temp;
} else return null;
 } catch (Exception ex) {
 ex.printStackTrace();
}
return null;
}

public static _cls_project0 loadByPkFromDB(long pk) { 
try {
 Statement stat = _cls_project0._conn.createStatement();
 ResultSet rs = stat.executeQuery("select * from _cls_project0 where _id=" + pk + ";");
 if (rs.next()) {
 _cls_project0 temp = new _cls_project0();
 temp.pk = rs.getLong("_id");
 temp.idp_limit = rs.getInt("_idp_limit");
 temp.drp_limit = rs.getInt("_drp_limit");
 temp.bwp_limit = rs.getInt("_bwp_limit");
 temp.bp_limit = rs.getInt("_bp_limit");
 temp.leeway = rs.getInt("_leeway");
 temp.ignore_blocked_dorm = rs.getBoolean("_ignore_blocked_dorm");
 temp.ignore_frozen_dorm = rs.getBoolean("_ignore_frozen_dorm");
 temp.ignore_suspended_dorm = rs.getBoolean("_ignore_suspended_dorm");
 temp.ignore_blocked_break = rs.getBoolean("_ignore_blocked_break");
 temp.ignore_frozen_break = rs.getBoolean("_ignore_frozen_break");
 temp.ignore_suspended_break = rs.getBoolean("_ignore_suspended_break");
 temp.ignore_blocked_warn = rs.getBoolean("_ignore_blocked_warn");
 temp.ignore_frozen_warn = rs.getBoolean("_ignore_frozen_warn");
 temp.ignore_suspended_warn = rs.getBoolean("_ignore_suspended_warn");
 temp.dormancy_fee = rs.getDouble("_dormancy_fee");
 temp.day = rs.getInt("_day");
 temp.month = rs.getInt("_month");
 temp._state_id_properties = rs.getInt("_state_id_properties");
 temp.no_automata = rs.getInt("_no_automata");
 return temp;
} else return null;
 } catch (Exception ex) {
 ex.printStackTrace();
}
return null;
}

public void specialInitialization(HashMap<String,Object> m)
{
if (m == null) return;
try {
  Class c = this.getClass();
  for (String s:m.keySet())
  {
    if (s.indexOf("STATE>")==0){
      String logic = s.substring(6,s.length());
      Field f = c.getField("_state_id_" + logic);
      String stateName = (String)m.get(s);
     if (logic.equals("properties")){
          if (1==0){}
             else if (stateName.equals("start")) f.set(this, 0);
          }
    }
    else {
	   Field f = c.getField(s);
	   if (f.getType().equals(Clock.class))
	   {
        Method method = f.getType().getMethod("reset", long.class);
	     method.invoke(f.get(this), m.get(s));
	   }
	   else
	 	 f.set(this, m.get(s));
      }
   }
} catch(Exception ex)
{ex.printStackTrace();}
}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_project0(){}

public void initialisation() {
}

public static void printStackTrace(String prefix) {
  Exception ex = new Exception();
  String sEx = prefix == null ? "" : prefix;
  for (int i = 0; i < ex.getStackTrace().length; i++)
    sEx += ex.getStackTrace()[i];
  _cls_project0._pw.println(sEx);
  _cls_project0._pw.flush();
}

public static _cls_project0 _get_cls_project0_inst(boolean _init) {
 if (root == null) 
   root = loadFromDB();
 return root; 
}

public boolean equals(Object o) {
 if ((o instanceof _cls_project0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
_performLogic_properties(_info, _event);
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){
}

public int _state_id_properties = 0;

public void _performLogic_properties(String _info, int... _event) {
_cls_project0._pw.flush();

if (0==1){}
else if (_state_id_properties==0){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*all0*/)) && (t .is ("PropChange"))){
		String message =(String )t .params .get ("message");
if (message .contains ("Ignore")&&message .contains ("Blocked")&&message .contains ("Dormancy"))ignore_blocked_dorm =(message .contains ("true"));
else if (message .contains ("Ignore")&&message .contains ("Suspended")&&message .contains ("Dormancy"))ignore_suspended_dorm =(message .contains ("true"));
else if (message .contains ("Ignore")&&message .contains ("Frozen")&&message .contains ("Dormancy"))ignore_frozen_dorm =(message .contains ("true"));
else if (message .contains ("Ignore")&&message .contains ("Blocked")&&message .contains ("Breakage"))ignore_blocked_break =(message .contains ("true"));
else if (message .contains ("Ignore")&&message .contains ("Suspended")&&message .contains ("Breakage"))ignore_suspended_break =(message .contains ("true"));
else if (message .contains ("Ignore")&&message .contains ("Frozen")&&message .contains ("Breakage"))ignore_frozen_break =(message .contains ("true"));
else if (message .contains ("Ignore")&&message .contains ("Blocked")&&message .contains ("Warning"))ignore_blocked_warn =(message .contains ("true"));
else if (message .contains ("Ignore")&&message .contains ("Suspended")&&message .contains ("Warning"))ignore_suspended_warn =(message .contains ("true"));
else if (message .contains ("Ignore")&&message .contains ("Frozen")&&message .contains ("Warning"))ignore_frozen_warn =(message .contains ("true"));
else _cls_project0._pw.println("[properties]" + "Warning!! A property changed could not be understood!! "+message );

		_state_id_properties = 0;//moving to state start
		_goto_properties(_info);
		}
}
}

public void _goto_properties(String _info){
}

public String _string_properties(int _state_id, int _mode){
switch(_state_id){
case 0: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}