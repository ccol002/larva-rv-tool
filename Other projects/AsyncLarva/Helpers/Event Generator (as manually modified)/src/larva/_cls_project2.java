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

public class _cls_project2 implements _callable{
public long pk = 0;
public static long pks = 0;

_cls_project1 parent;
public static long timestamp;
public static Object inp;
public static String txId;
public static Template t;
public static double adjustment;
public static String name;
public static double expBalance;
public String i;
int no_automata = 3;
public double balance =0 ;
public String currency =null ;
public String type =null ;
public boolean defaultInstr =false ;
public boolean dormancy_freeze =false ;

public long getPk(){return pk;}

public static void forceClassLoad(){}

public static void initialize(){ 
 try {
 if ((SC.init )) {
  Statement stat1 = _cls_project0._conn.createStatement();
  stat1.execute("drop table if exists _cls_project2;");
stat1.execute("create table _cls_project2 (_id bigint(20) primary key,_pid bigint(20),_i varchar(255),_balance double(12,2),_currency varchar(255),_type varchar(255),_defaultInstr bool,_dormancy_freeze bool,_state_id_instrument_lc int(10),_state_id_balance_check int(10),_state_id_balance_update int(10),_no_automata int(10));");

try { 
 stat1.execute("CREATE INDEX _i_index ON _cls_project2(_i);");
 } catch(Exception ex) {}

 ArrayList al = SC.initiallyInstruments ();

 if (al != null && al.size() > 0) {

 for (int _i =0; _i < ((ArrayList)al.get(0)).size(); _i++)
  _get_cls_project2_inst(true, (String)((ArrayList)al.get(1)).get(_i),(String)((ArrayList)al.get(0)).get(_i));
}
}//of initialization condition 
 else {
   Statement stat1 = _cls_project0._conn.createStatement();
   ResultSet rs = stat1.executeQuery("select max(_id) as cnt from _cls_project2;");
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
 stat.execute("update _cls_project2 set _balance = \"" + balance + "\",_currency = \"" + currency + "\",_type = \"" + type + "\",_defaultInstr = \"" + ((defaultInstr)?(1):(0)) + "\",_dormancy_freeze = \"" + ((dormancy_freeze)?(1):(0)) + "\", _state_id_instrument_lc = \"" + _state_id_instrument_lc + "\", _state_id_balance_check = \"" + _state_id_balance_check + "\", _state_id_balance_update = \"" + _state_id_balance_update + "\", _no_automata = \"" + no_automata + "\" where _i = \"" + i + "\" ;"); 
  parent.updateInDB();
 } catch (Exception ex) {
 ex.printStackTrace();
}
}

public void insertInDB() { 
try {
 Statement stat = _cls_project0._conn.createStatement();
 stat.execute("insert into _cls_project2 values (\"" + pk + "\",\"" + parent.pk + "\",\"" + i + "\",\"" + balance + "\",\"" + currency + "\",\"" + type + "\",\"" + ((defaultInstr)?(1):(0)) + "\",\"" + ((dormancy_freeze)?(1):(0)) + "\",\"" + _state_id_instrument_lc + "\",\"" + _state_id_balance_check + "\",\"" + _state_id_balance_update + "\",\"" + no_automata + "\");"); 
 } catch (Exception ex) {
 ex.printStackTrace();
}
}

public static _cls_project2 loadFromDB( String i,String s ) { 
try {
 Statement stat = _cls_project0._conn.createStatement();
 ResultSet rs = stat.executeQuery("select * from _cls_project2 where _i = \"" + i + "\" ;");
 if (rs.next()) {
 _cls_project2 temp = new _cls_project2();
 temp.i = i;
 temp.pk = rs.getLong("_id");
 temp.balance = rs.getDouble("_balance");
 temp.currency = rs.getString("_currency");
 temp.type = rs.getString("_type");
 temp.defaultInstr = rs.getBoolean("_defaultInstr");
 temp.dormancy_freeze = rs.getBoolean("_dormancy_freeze");
 temp._state_id_instrument_lc = rs.getInt("_state_id_instrument_lc");
 temp._state_id_balance_check = rs.getInt("_state_id_balance_check");
 temp._state_id_balance_update = rs.getInt("_state_id_balance_update");
 temp.no_automata = rs.getInt("_no_automata");
 return temp;
} else return null;
 } catch (Exception ex) {
 ex.printStackTrace();
}
return null;
}

public static _cls_project2 loadByPkFromDB(long pk) { 
try {
 Statement stat = _cls_project0._conn.createStatement();
 ResultSet rs = stat.executeQuery("select * from _cls_project2 where _id=" + pk + ";");
 if (rs.next()) {
 _cls_project2 temp = new _cls_project2();
 temp.parent = _cls_project1.loadByPkFromDB(rs.getLong("_pik"));
 temp.i = rs.getString("_i");
 temp.pk = rs.getLong("_id");
 temp.balance = rs.getDouble("_balance");
 temp.currency = rs.getString("_currency");
 temp.type = rs.getString("_type");
 temp.defaultInstr = rs.getBoolean("_defaultInstr");
 temp.dormancy_freeze = rs.getBoolean("_dormancy_freeze");
 temp._state_id_instrument_lc = rs.getInt("_state_id_instrument_lc");
 temp._state_id_balance_check = rs.getInt("_state_id_balance_check");
 temp._state_id_balance_update = rs.getInt("_state_id_balance_update");
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
     if (logic.equals("instrument_lc")){
          if (1==0){}
             else if (stateName.equals("unassigned")) f.set(this, 38);
             else if (stateName.equals("noright")) f.set(this, 34);
             else if (stateName.equals("fallow_assigned")) f.set(this, 37);
             else if (stateName.equals("restricted")) f.set(this, 36);
             else if (stateName.equals("assigned")) f.set(this, 35);
          }
     if (logic.equals("balance_check")){
          if (1==0){}
             else if (stateName.equals("start")) f.set(this, 40);
             else if (stateName.equals("unexpectedBalance")) f.set(this, 39);
          }
     if (logic.equals("balance_update")){
          if (1==0){}
             else if (stateName.equals("start")) f.set(this, 41);
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

public _cls_project2(){}

public _cls_project2( String i,String s) {
try {
 pk = pks++; 
this.i = i;
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
  _cls_project0._pw.println(sEx);
  _cls_project0._pw.flush();
}

public static _cls_project2 _get_cls_project2_inst(boolean _init,String i,String s) {
_cls_project2 _inst = loadFromDB( i,s);
if (_inst != null)
{//object loaded from DB
  _inst.parent = _cls_project1._get_cls_project1_inst(_init,s);
  if (_inst.no_automata == 0)
  { printStackTrace("No more running automata.\r\n"); }
}
else
{
  _inst = new _cls_project2(i,s);
  if (_init) _inst.specialInitialization(SC.initializeifInstrument( i,s));
  _inst.parent = _cls_project1._get_cls_project1_inst(_init,s);
  _inst.insertInDB();
}
 return _inst;
}

public boolean equals(Object o) {
 if ((o instanceof _cls_project2)
 && (i == null || i.equals(((_cls_project2)o).i))
 && (parent == null || parent.equals(((_cls_project2)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
_performLogic_instrument_lc(_info, _event);
_performLogic_balance_check(_info, _event);
_performLogic_balance_update(_info, _event);
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){
}

public int _state_id_instrument_lc = 38;

public void _performLogic_instrument_lc(String _info, int... _event) {
_cls_project0._pw.flush();

if (0==1){}
else if (_state_id_instrument_lc==38){
		if (1==0){}
		else if ((_occurredEvent(_event,14/*all2*/)) && (t .is ("Assign")&&(parent.USER_CREATE_VCC ||!t .params .get ("type").equals ("VCC")||!parent.origin .equals ("WEB")))){
		type =t .params .get ("type").toString ();
if (t .params .get ("parent.currency")!=null )parent.currency =t .params .get ("parent.currency").toString ();

		_state_id_instrument_lc = 35;//moving to state assigned
		_goto_instrument_lc(_info);
		}
		else if ((_occurredEvent(_event,14/*all2*/)) && (t .is ("Assign"))){
		_cls_project0._pw.println("[instrument_lc]" + "Adding VCC without necessary rights!! user  "+parent.s +" instrument: "+i +" "+timestamp );

		_state_id_instrument_lc = 34;//moving to state noright
		_goto_instrument_lc(_info);
		}
}
else if (_state_id_instrument_lc==36){
		if (1==0){}
		else if ((_occurredEvent(_event,14/*all2*/)) && (t .is ("Unrestrict"))){
		parent.frozenInstruments --;
if (!t .params .get ("originalFreezeOrigin").equals ("DORMANCY_FREEZE")){parent.nonDormfrozenInstruments --;
dormancy_freeze =false ;
}
		_state_id_instrument_lc = 35;//moving to state assigned
		_goto_instrument_lc(_info);
		}
		else if ((_occurredEvent(_event,14/*all2*/)) && (t .is ("Fallow")||(t .is ("DelInstr")&&(parent.USER_DELETE_VCC ||!t .params .get ("type").equals ("VCC"))))){
		parent.frozenInstruments --;
if (dormancy_freeze )parent.nonDormfrozenInstruments --;

		_state_id_instrument_lc = 37;//moving to state fallow_assigned
		_goto_instrument_lc(_info);
		}
}
else if (_state_id_instrument_lc==35){
		if (1==0){}
		else if ((_occurredEvent(_event,14/*all2*/)) && (t .is ("Restrict"))){
		parent.frozenInstruments ++;
if (!t .params .get ("freezeOrigin").equals ("DORMANCY_FREEZE")){parent.nonDormfrozenInstruments ++;
dormancy_freeze =true ;
}
		_state_id_instrument_lc = 36;//moving to state restricted
		_goto_instrument_lc(_info);
		}
		else if ((_occurredEvent(_event,14/*all2*/)) && (t .is ("Fallow")||(t .is ("DelInstr")&&(parent.USER_DELETE_VCC ||!t .params .get ("type").equals ("VCC"))))){
		
		_state_id_instrument_lc = 37;//moving to state fallow_assigned
		_goto_instrument_lc(_info);
		}
}
}

public void _goto_instrument_lc(String _info){
}

public String _string_instrument_lc(int _state_id, int _mode){
switch(_state_id){
case 38: if (_mode == 0) return "unassigned"; else return "unassigned";
case 34: if (_mode == 0) return "noright"; else return "!!!SYSTEM REACHED BAD STATE!!! noright "+new _BadStateExceptionproject().toString()+" ";
case 37: if (_mode == 0) return "fallow_assigned"; else return "fallow_assigned";
case 36: if (_mode == 0) return "restricted"; else return "restricted";
case 35: if (_mode == 0) return "assigned"; else return "assigned";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
public int _state_id_balance_check = 40;

public void _performLogic_balance_check(String _info, int... _event) {
_cls_project0._pw.flush();

if (0==1){}
else if (_state_id_balance_check==40){
		if (1==0){}
		else if ((_occurredEvent(_event,16/*balCheck*/)) && (expBalance ==balance )){
		
		_state_id_balance_check = 40;//moving to state start
		_goto_balance_check(_info);
		}
		else if ((_occurredEvent(_event,16/*balCheck*/))){
		_cls_project0._pw.println("[balance_check]" + "Expected balance: "+expBalance +" found(at monitor): "+balance +"\r\n user: "+parent.s +" allocation: "+i +" at "+timestamp +" TX ID: "+txId +"\r\n origin: "+parent.origin +" scheme: "+parent.scheme +" type: "+type +"\r\n dormancy_freeze: "+dormancy_freeze +" defaultInstr: "+defaultInstr );

		_state_id_balance_check = 39;//moving to state unexpectedBalance
		_goto_balance_check(_info);
		}
}
}

public void _goto_balance_check(String _info){
}

public String _string_balance_check(int _state_id, int _mode){
switch(_state_id){
case 40: if (_mode == 0) return "start"; else return "start";
case 39: if (_mode == 0) return "unexpectedBalance"; else return "!!!SYSTEM REACHED BAD STATE!!! unexpectedBalance "+new _BadStateExceptionproject().toString()+" ";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
public int _state_id_balance_update = 41;

public void _performLogic_balance_update(String _info, int... _event) {
_cls_project0._pw.flush();

if (0==1){}
else if (_state_id_balance_update==41){
		if (1==0){}
		else if ((_occurredEvent(_event,18/*balUpdate*/))){
		balance +=adjustment ;

		_state_id_balance_update = 41;//moving to state start
		_goto_balance_update(_info);
		}
}
}

public void _goto_balance_update(String _info){
}

public String _string_balance_update(int _state_id, int _mode){
switch(_state_id){
case 41: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}