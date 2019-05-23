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

public class _cls_script1 implements _callable{
public long pk = 0;
public static long pks = 0;

_cls_script0 parent;
public String s;
int no_automata = 1;

public long getPk(){return pk;}

public static void forceClassLoad(){}

public static void initialize(){ 
 Statement stat = null;
 Statement stat1 = null;
 ResultSet rs = null;
 try {
 if ((SC.init )) {
  stat1 = _cls_script0._conn.createStatement();
  stat1.execute("drop table if exists _cls_script1;");
stat1.execute("create table _cls_script1 (_id bigint(20) primary key,_s varchar(255),_state_id_property_name int(10),_no_automata int(10));");

try { 
 stat1.execute("CREATE INDEX _s_index ON _cls_script1(_s);");
 } catch(Exception ex) {}

 ArrayList al = SC.initiallyUsers ();

 if (al != null && al.size() > 0) {

 for (int _i =0; _i < ((ArrayList)al.get(0)).size(); _i++)
  _get_cls_script1_inst(true, (String)((ArrayList)al.get(0)).get(_i));
}
}//of initialization condition 
 else {
   stat1 = _cls_script0._conn.createStatement();
   rs = stat1.executeQuery("select max(_id) as cnt from _cls_script1;");
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
 stat.execute("update _cls_script1 set  _state_id_property_name = \"" + _state_id_property_name + "\", _no_automata = \"" + no_automata + "\" where _s = \"" + s + "\" ;"); 
 if (stat != null) {
     try {
         stat.close();
     } catch (SQLException sqlEx) { } // ignore
     stat = null;
 }
  parent.updateInDB();
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
 stat.execute("insert into _cls_script1 values (\"" + pk + "\",\"" + s + "\",\"" + _state_id_property_name + "\",\"" + no_automata + "\");"); 
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

public static _cls_script1 loadFromDB( String s ) { 
Statement stat = null;
ResultSet rs = null;
try {
  stat = _cls_script0._conn.createStatement();
  rs = stat.executeQuery("select * from _cls_script1 where _s = \"" + s + "\" ;");
 if (rs.next()) {
 _cls_script1 temp = new _cls_script1();
 temp.s = s;
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

public static _cls_script1 loadByPkFromDB(long pk) { 
Statement stat = null;
ResultSet rs = null;
try {
  stat = _cls_script0._conn.createStatement();
  rs = stat.executeQuery("select * from _cls_script1 where _id=" + pk + ";");
 if (rs.next()) {
 _cls_script1 temp = new _cls_script1();
 temp.parent = _cls_script0._get_cls_script0_inst(false);
 temp.s = rs.getString("_s");
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
             else if (stateName.equals("start")) f.set(this, 1);
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

public _cls_script1(){}

public _cls_script1( String s) {
try {
 pk = pks++; 
this.s = s;
  initialisation();
} catch (Exception ex) {ex.printStackTrace();} 
}

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

public static _cls_script1 _get_cls_script1_inst(boolean _init,String s) {
_cls_script1 _inst = loadFromDB( s);
if (_inst != null)
{//object loaded from DB
  _inst.parent = _cls_script0._get_cls_script0_inst(_init);
  if (_inst.no_automata == 0)
  { printStackTrace("No more running automata.\r\n"); }
}
else
{
  _inst = new _cls_script1(s);
  if (_init) _inst.specialInitialization(SC.initializeifUser( s));
  _inst.parent = _cls_script0._get_cls_script0_inst(_init);
  _inst.insertInDB();
}
 return _inst;
}

public boolean equals(Object o) {
 if ((o instanceof _cls_script1)
 && (s == null || s.equals(((_cls_script1)o).s))
 && (parent == null || parent.equals(((_cls_script1)o).parent)))
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

public int _state_id_property_name = 1;

public void _performLogic_property_name(String _info, int... _event) {
_cls_script0._pw.flush();

if (0==1){}
}

public void _goto_property_name(String _info){
}

public String _string_property_name(int _state_id, int _mode){
switch(_state_id){
case 1: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}