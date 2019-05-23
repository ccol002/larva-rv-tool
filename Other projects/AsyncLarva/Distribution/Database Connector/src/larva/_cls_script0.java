package larva;


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

import events.Template;

public class _cls_script0 implements _callable{
public long pk = 0;

public static PrintWriter _pw; 
public static _cls_script0 root;
public static String _url;
 public static Connection _conn;
public static long pks = 0;
static{
  _cls_script0.initialize(); 
}

_cls_script0 parent; //to remain null - this class does not have a parent!
public static long timestamp;
public static Template t;
public static String name;
int no_automata = 1;

public long getPk(){return pk;}

public static void forceClassLoad(){}

public static void initialize(){ 
 Statement stat = null;
 Statement stat1 = null;
 ResultSet rs = null;
 try {
java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(" (yyyy-MM-dd) [HH mm ss S]");
java.util.Date date = new java.util.Date();
String datetime = dateFormat.format(date);dateFormat = new java.text.SimpleDateFormat(" yyyy-MM-dd HH:mm:ss:S");
_pw = new PrintWriter("/Users/christiancolombo/Dropbox/My Docs/Code/Our tools/AsyncLarva/Distribution/Database Connector/src/larvaOutput/_output_script" + datetime + ".txt");

_pw.println("[LARVA] Monitoring started at " + dateFormat.format(date));
_pw.flush();
 _url = "jdbc:mysql://localhost:3306/monitor";
 Class.forName("com.mysql.jdbc.Driver");
 _conn = DriverManager.getConnection(_url, "root","");
 if ((SC.init )) {
 stat = _cls_script0._conn.createStatement();
  stat.execute("drop table if exists _clocks;");
stat.execute("create table _clocks (id bigint(20) primary key,name varchar(255),class_name varchar(255),enabled bool,inst bigint(20),paused bool,thison bool,_starting bigint(20),durationPaused bigint(20),whenPaused bigint(20),ev0 bigint(20),ev1 bigint(20),ev2 bigint(20),ev3 bigint(20),ev4 bigint(20));");
  stat1 = _cls_script0._conn.createStatement();
  stat1.execute("drop table if exists _cls_script0;");
stat1.execute("create table _cls_script0 (_id bigint(20) primary key,_state_id_property_name int(10),_no_automata int(10));");
root = new _cls_script0();
  root.initialisation();
root.specialInitialization(SC.initializeifMain());
  root.insertInDB();
}//of initialization condition 
 else {
   stat1 = _cls_script0._conn.createStatement();
   rs = stat1.executeQuery("select max(_id) as cnt from _cls_script0;");
   if (rs.next())
      pks = rs.getInt("cnt") + 1;
   rs = stat1.executeQuery("select max(id) as cnt from _clocks;");
   if (rs.next())
      Clock.pks = rs.getInt("cnt") + 1;
  }
 } catch (Exception ex) { ex.printStackTrace();} 
 finally {
 	if (rs != null) {
         try {
             rs.close();
         } catch (SQLException sqlEx) { } // ignore
         rs = null;
     }
     if (stat != null) {
         try {
             stat.close();
         } catch (SQLException sqlEx) { } // ignore
         stat = null;
     }
    if (stat1 != null) {
         try {
             stat1.close();
         } catch (SQLException sqlEx) { } // ignore
         stat1 = null;
     }
 }
}

public void updateInDB() { 
Statement stat = null;
try {
  stat = _cls_script0._conn.createStatement();
 stat.execute("update _cls_script0 set  _state_id_property_name = \"" + _state_id_property_name + "\", _no_automata = \"" + no_automata + "\" where _id = " + pk + ";"); 
 if (stat != null) {
     try {
         stat.close();
     } catch (SQLException sqlEx) { } // ignore
     stat = null;
 }
 } catch (Exception ex) {
 ex.printStackTrace();
}
 finally {			
 if (stat != null) {
 try {
     stat.close();
 } catch (SQLException sqlEx) { } // ignore
 stat = null;
 }
 }
}

public void insertInDB() { Statement stat = null;
try {
  stat = _cls_script0._conn.createStatement();
 stat.execute("insert into _cls_script0 values (\"" + pk + "\",\"" + _state_id_property_name + "\",\"" + no_automata + "\");"); 
 if (stat != null) {
    try {
        stat.close();
    } catch (SQLException sqlEx) { } // ignore
    stat = null;
 }
 } catch (Exception ex) {
 ex.printStackTrace();
}
finally {	
if (stat != null) {
try {
    stat.close();
} catch (SQLException sqlEx) { } // ignore
stat = null;
}
}
}

public static _cls_script0 loadFromDB(  ) { 
Statement stat = null;
ResultSet rs = null;
try {
  stat = _cls_script0._conn.createStatement();
  rs = stat.executeQuery("select * from _cls_script0;");
 if (rs.next()) {
 _cls_script0 temp = new _cls_script0();
 temp.pk = rs.getLong("_id");
 temp._state_id_property_name = rs.getInt("_state_id_property_name");
 temp.no_automata = rs.getInt("_no_automata");
 if (rs != null) {
 try {
     rs.close();
 } catch (SQLException sqlEx) { } // ignore
  rs = null;
 }
 if (stat != null) {
      try {
          stat.close();
      } catch (SQLException sqlEx) { } // ignore
       stat = null;
  }
 return temp;
} else return null;
 } catch (Exception ex) {
 ex.printStackTrace();
}
finally {
if (rs != null) {
try {
    rs.close();
} catch (SQLException sqlEx) { } // ignore
rs = null;
}
if (stat != null) {
try {
    stat.close();
} catch (SQLException sqlEx) { } // ignore
stat = null;
}
}
return null;
}

public static _cls_script0 loadByPkFromDB(long pk) { 
Statement stat = null;
ResultSet rs = null;
try {
  stat = _cls_script0._conn.createStatement();
  rs = stat.executeQuery("select * from _cls_script0 where _id=" + pk + ";");
 if (rs.next()) {
 _cls_script0 temp = new _cls_script0();
 temp.pk = rs.getLong("_id");
 temp._state_id_property_name = rs.getInt("_state_id_property_name");
 temp.no_automata = rs.getInt("_no_automata");
 if (rs != null) {
 try {
     rs.close();
 } catch (SQLException sqlEx) { } // ignore
  rs = null;
 }
 if (stat != null) {
      try {
          stat.close();
      } catch (SQLException sqlEx) { } // ignore
       stat = null;
  }
 return temp;
} else return null;
 } catch (Exception ex) {
 ex.printStackTrace();
}
finally {	
if (rs != null) {
  try {
       rs.close();
    } catch (SQLException sqlEx) { } // ignore
      rs = null;
}
if (stat != null) {
     try {
        stat.close();
     } catch (SQLException sqlEx) { } // ignore
     stat = null;
   }
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
     if (logic.equals("property_name")){
          if (1==0){}
             else if (stateName.equals("start")) f.set(this, 3);
             else if (stateName.equals("its_working")) f.set(this, 0);
             else if (stateName.equals("login_detected")) f.set(this, 2);
             else if (stateName.equals(",")) f.set(this, 1);
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

public _cls_script0(){}

public void initialisation() {
}

public static void printStackTrace(String prefix) {
  Exception ex = new Exception();
  String sEx = prefix == null ? "" : prefix;
  for (int i = 0; i < ex.getStackTrace().length; i++)
    sEx += ex.getStackTrace()[i];
  _cls_script0._pw.println(sEx);
  _cls_script0._pw.flush();
}

public static _cls_script0 _get_cls_script0_inst(boolean _init) {
 if (root == null) 
   root = loadFromDB();
 return root; 
}

public boolean equals(Object o) {
 if ((o instanceof _cls_script0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
_performLogic_property_name(_info, _event);
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){
}

public int _state_id_property_name = 3;

public void _performLogic_property_name(String _info, int... _event) {

_cls_script0._pw.println("[property_name]AUTOMATON::> property_name("+") STATE::>"+ _string_property_name(_state_id_property_name, 0));
_cls_script0._pw.flush();

if (0==1){}
else if (_state_id_property_name==3){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*event_name*/))){
		_cls_script0._pw.println("[property_name]" + "Login for user "+t .params .get ("user_id")+" has been intercepted!");

		_state_id_property_name = 0;//moving to state its_working
		_goto_property_name(_info);
		}
}
else if (_state_id_property_name==0){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*login*/))){
		_cls_script0._pw.println("[property_name]" + "Login for user "+t .params .get ("user_id")+" has been intercepted!");

		_state_id_property_name = 2;//moving to state login_detected
		_goto_property_name(_info);
		}
}
}

public void _goto_property_name(String _info){
_cls_script0._pw.println("[property_name]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_property_name(_state_id_property_name, 1));
_cls_script0._pw.flush();
}

public String _string_property_name(int _state_id, int _mode){
switch(_state_id){
case 3: if (_mode == 0) return "start"; else return "start";
case 0: if (_mode == 0) return "its_working"; else return "its_working";
case 2: if (_mode == 0) return "login_detected"; else return "login_detected";
case 1: if (_mode == 0) return ","; else return ",";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}