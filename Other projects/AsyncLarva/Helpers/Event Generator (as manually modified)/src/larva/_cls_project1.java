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

public class _cls_project1 implements _callable{
public long pk = 0;
public static long pks = 0;

_cls_project0 parent;
public static HashMap limits;
public static double amount;
public static long timestamp;
public static String s1;
public static Template t;
public static String loadtype;
public static String name;
public static String currency;
public String s;
int no_automata = 11;
public boolean USER_DEPOSIT_OCT =false ;
public boolean USER_LOGIN =false ;
public boolean USER_CHANGE_LOST_PASSWORD =false ;
public boolean USER_ADD_BANK_CARD =false ;
public boolean USER_ADD_BANK_PAYOUT_ACCOUNT =false ;
public boolean USER_CREATE_VCC =false ;
public boolean USER_VIEW_VCC =false ;
public boolean USER_LOAD_VCC =false ;
public boolean USER_DELETE_VCC =false ;
public boolean USER_VIEW_TRANSACTION_HISTORY =false ;
public boolean USER_LOCK_ACCOUNT =false ;
public boolean USER_TRANSFER =false ;
public boolean USER_TRANSFER_TO_VCC =false ;
public boolean USER_TRANSFER_TO_PLASTIC =false ;
public boolean USER_BANK_PAYOUT =false ;
public boolean USER_BANK_PAYIN =false ;
public boolean USER_VIRTUAL_PAYOUT =false ;
public boolean USER_PAYOUT_TO_BANKCARD =false ;
public boolean USER_APPLY_FOR_PLASTIC =false ;
public boolean USER_APPLY_FOR_PLASTIC_MASTERCARD =false ;
public boolean USER_APPLY_FOR_PLASTIC_VISA =false ;
public boolean USER_DEPOSIT_MERCHANT_INCOMING_FUNDS =false ;
public Clock idp;
public Clock drp;
public Clock bp;
public long passStart =RunningClock .now ;
public long lastFreeze =-1 ;
public boolean feeExpected =false ;
public int nonDormfrozenInstruments =0 ;
public int frozenInstruments =0 ;
public boolean suspended =false ;
public boolean closed =false ;
public boolean locked =false ;
public boolean frozen =false ;
public String origin ="WEB" ;
public String scheme ="BASIC" ;
public double expectedSettlement =0 ;
public double totalBalance =0 ;
public long timeExpired =0 ;
public long timeBreakageExpired =0 ;
public int loadCountDay =0 ;
public long loadTimestampDaya =-1 ;
public long loadTimestampDayc =-1 ;
public double loadAmountDay =0 ;
public int loadCountMonth =0 ;
public long loadTimestampMontha =-1 ;
public long loadTimestampMonthc =-1 ;
public double loadAmountMonth =0 ;

public long getPk(){return pk;}

public static void forceClassLoad(){}

public static void initialize(){ 
 try {
 if ((SC.init )) {
  Statement stat1 = _cls_project0._conn.createStatement();
  stat1.execute("drop table if exists _cls_project1;");
stat1.execute("create table _cls_project1 (_id bigint(20) primary key,_s varchar(255),_USER_DEPOSIT_OCT bool,_USER_LOGIN bool,_USER_CHANGE_LOST_PASSWORD bool,_USER_ADD_BANK_CARD bool,_USER_ADD_BANK_PAYOUT_ACCOUNT bool,_USER_CREATE_VCC bool,_USER_VIEW_VCC bool,_USER_LOAD_VCC bool,_USER_DELETE_VCC bool,_USER_VIEW_TRANSACTION_HISTORY bool,_USER_LOCK_ACCOUNT bool,_USER_TRANSFER bool,_USER_TRANSFER_TO_VCC bool,_USER_TRANSFER_TO_PLASTIC bool,_USER_BANK_PAYOUT bool,_USER_BANK_PAYIN bool,_USER_VIRTUAL_PAYOUT bool,_USER_PAYOUT_TO_BANKCARD bool,_USER_APPLY_FOR_PLASTIC bool,_USER_APPLY_FOR_PLASTIC_MASTERCARD bool,_USER_APPLY_FOR_PLASTIC_VISA bool,_USER_DEPOSIT_MERCHANT_INCOMING_FUNDS bool,_idp bigint(20),_drp bigint(20),_bp bigint(20),_passStart bigint(20),_lastFreeze bigint(20),_feeExpected bool,_nonDormfrozenInstruments int(10),_frozenInstruments int(10),_suspended bool,_closed bool,_locked bool,_frozen bool,_origin varchar(255),_scheme varchar(255),_expectedSettlement double(12,2),_totalBalance double(12,2),_timeExpired bigint(20),_timeBreakageExpired bigint(20),_loadCountDay int(10),_loadTimestampDaya bigint(20),_loadTimestampDayc bigint(20),_loadAmountDay double(12,2),_loadCountMonth int(10),_loadTimestampMontha bigint(20),_loadTimestampMonthc bigint(20),_loadAmountMonth double(12,2),_state_id_load_tx int(10),_state_id_load_count int(10),_state_id_load_amount int(10),_state_id_transfer_tx int(10),_state_id_rights_management int(10),_state_id_scheme_change int(10),_state_id_dormancy int(10),_state_id_breakage int(10),_state_id_user_lc int(10),_state_id_purchase int(10),_state_id_fund_transfers int(10),_no_automata int(10));");

try { 
 stat1.execute("CREATE INDEX _s_index ON _cls_project1(_s);");
 } catch(Exception ex) {}

 ArrayList al = SC.initiallyUsers ();

 if (al != null && al.size() > 0) {

 for (int _i =0; _i < ((ArrayList)al.get(0)).size(); _i++)
  _get_cls_project1_inst(true, (String)((ArrayList)al.get(0)).get(_i));
}
}//of initialization condition 
 else {
   Statement stat1 = _cls_project0._conn.createStatement();
   ResultSet rs = stat1.executeQuery("select max(_id) as cnt from _cls_project1;");
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
 stat.execute("update _cls_project1 set _USER_DEPOSIT_OCT = \"" + ((USER_DEPOSIT_OCT)?(1):(0)) + "\",_USER_LOGIN = \"" + ((USER_LOGIN)?(1):(0)) + "\",_USER_CHANGE_LOST_PASSWORD = \"" + ((USER_CHANGE_LOST_PASSWORD)?(1):(0)) + "\",_USER_ADD_BANK_CARD = \"" + ((USER_ADD_BANK_CARD)?(1):(0)) + "\",_USER_ADD_BANK_PAYOUT_ACCOUNT = \"" + ((USER_ADD_BANK_PAYOUT_ACCOUNT)?(1):(0)) + "\",_USER_CREATE_VCC = \"" + ((USER_CREATE_VCC)?(1):(0)) + "\",_USER_VIEW_VCC = \"" + ((USER_VIEW_VCC)?(1):(0)) + "\",_USER_LOAD_VCC = \"" + ((USER_LOAD_VCC)?(1):(0)) + "\",_USER_DELETE_VCC = \"" + ((USER_DELETE_VCC)?(1):(0)) + "\",_USER_VIEW_TRANSACTION_HISTORY = \"" + ((USER_VIEW_TRANSACTION_HISTORY)?(1):(0)) + "\",_USER_LOCK_ACCOUNT = \"" + ((USER_LOCK_ACCOUNT)?(1):(0)) + "\",_USER_TRANSFER = \"" + ((USER_TRANSFER)?(1):(0)) + "\",_USER_TRANSFER_TO_VCC = \"" + ((USER_TRANSFER_TO_VCC)?(1):(0)) + "\",_USER_TRANSFER_TO_PLASTIC = \"" + ((USER_TRANSFER_TO_PLASTIC)?(1):(0)) + "\",_USER_BANK_PAYOUT = \"" + ((USER_BANK_PAYOUT)?(1):(0)) + "\",_USER_BANK_PAYIN = \"" + ((USER_BANK_PAYIN)?(1):(0)) + "\",_USER_VIRTUAL_PAYOUT = \"" + ((USER_VIRTUAL_PAYOUT)?(1):(0)) + "\",_USER_PAYOUT_TO_BANKCARD = \"" + ((USER_PAYOUT_TO_BANKCARD)?(1):(0)) + "\",_USER_APPLY_FOR_PLASTIC = \"" + ((USER_APPLY_FOR_PLASTIC)?(1):(0)) + "\",_USER_APPLY_FOR_PLASTIC_MASTERCARD = \"" + ((USER_APPLY_FOR_PLASTIC_MASTERCARD)?(1):(0)) + "\",_USER_APPLY_FOR_PLASTIC_VISA = \"" + ((USER_APPLY_FOR_PLASTIC_VISA)?(1):(0)) + "\",_USER_DEPOSIT_MERCHANT_INCOMING_FUNDS = \"" + ((USER_DEPOSIT_MERCHANT_INCOMING_FUNDS)?(1):(0)) + "\",_passStart = \"" + passStart + "\",_lastFreeze = \"" + lastFreeze + "\",_feeExpected = \"" + ((feeExpected)?(1):(0)) + "\",_nonDormfrozenInstruments = \"" + nonDormfrozenInstruments + "\",_frozenInstruments = \"" + frozenInstruments + "\",_suspended = \"" + ((suspended)?(1):(0)) + "\",_closed = \"" + ((closed)?(1):(0)) + "\",_locked = \"" + ((locked)?(1):(0)) + "\",_frozen = \"" + ((frozen)?(1):(0)) + "\",_origin = \"" + origin + "\",_scheme = \"" + scheme + "\",_expectedSettlement = \"" + expectedSettlement + "\",_totalBalance = \"" + totalBalance + "\",_timeExpired = \"" + timeExpired + "\",_timeBreakageExpired = \"" + timeBreakageExpired + "\",_loadCountDay = \"" + loadCountDay + "\",_loadTimestampDaya = \"" + loadTimestampDaya + "\",_loadTimestampDayc = \"" + loadTimestampDayc + "\",_loadAmountDay = \"" + loadAmountDay + "\",_loadCountMonth = \"" + loadCountMonth + "\",_loadTimestampMontha = \"" + loadTimestampMontha + "\",_loadTimestampMonthc = \"" + loadTimestampMonthc + "\",_loadAmountMonth = \"" + loadAmountMonth + "\", _state_id_load_tx = \"" + _state_id_load_tx + "\", _state_id_load_count = \"" + _state_id_load_count + "\", _state_id_load_amount = \"" + _state_id_load_amount + "\", _state_id_transfer_tx = \"" + _state_id_transfer_tx + "\", _state_id_rights_management = \"" + _state_id_rights_management + "\", _state_id_scheme_change = \"" + _state_id_scheme_change + "\", _state_id_dormancy = \"" + _state_id_dormancy + "\", _state_id_breakage = \"" + _state_id_breakage + "\", _state_id_user_lc = \"" + _state_id_user_lc + "\", _state_id_purchase = \"" + _state_id_purchase + "\", _state_id_fund_transfers = \"" + _state_id_fund_transfers + "\", _no_automata = \"" + no_automata + "\" where _s = \"" + s + "\" ;"); 
idp.updateInDB(false);
drp.updateInDB(false);
bp.updateInDB(false);
  parent.updateInDB();
 } catch (Exception ex) {
 ex.printStackTrace();
}
}

public void insertInDB() { 
try {
 Statement stat = _cls_project0._conn.createStatement();
 stat.execute("insert into _cls_project1 values (\"" + pk + "\",\"" + s + "\",\"" + ((USER_DEPOSIT_OCT)?(1):(0)) + "\",\"" + ((USER_LOGIN)?(1):(0)) + "\",\"" + ((USER_CHANGE_LOST_PASSWORD)?(1):(0)) + "\",\"" + ((USER_ADD_BANK_CARD)?(1):(0)) + "\",\"" + ((USER_ADD_BANK_PAYOUT_ACCOUNT)?(1):(0)) + "\",\"" + ((USER_CREATE_VCC)?(1):(0)) + "\",\"" + ((USER_VIEW_VCC)?(1):(0)) + "\",\"" + ((USER_LOAD_VCC)?(1):(0)) + "\",\"" + ((USER_DELETE_VCC)?(1):(0)) + "\",\"" + ((USER_VIEW_TRANSACTION_HISTORY)?(1):(0)) + "\",\"" + ((USER_LOCK_ACCOUNT)?(1):(0)) + "\",\"" + ((USER_TRANSFER)?(1):(0)) + "\",\"" + ((USER_TRANSFER_TO_VCC)?(1):(0)) + "\",\"" + ((USER_TRANSFER_TO_PLASTIC)?(1):(0)) + "\",\"" + ((USER_BANK_PAYOUT)?(1):(0)) + "\",\"" + ((USER_BANK_PAYIN)?(1):(0)) + "\",\"" + ((USER_VIRTUAL_PAYOUT)?(1):(0)) + "\",\"" + ((USER_PAYOUT_TO_BANKCARD)?(1):(0)) + "\",\"" + ((USER_APPLY_FOR_PLASTIC)?(1):(0)) + "\",\"" + ((USER_APPLY_FOR_PLASTIC_MASTERCARD)?(1):(0)) + "\",\"" + ((USER_APPLY_FOR_PLASTIC_VISA)?(1):(0)) + "\",\"" + ((USER_DEPOSIT_MERCHANT_INCOMING_FUNDS)?(1):(0)) + "\",\"" + idp.pk + "\",\"" + drp.pk + "\",\"" + bp.pk + "\",\"" + passStart + "\",\"" + lastFreeze + "\",\"" + ((feeExpected)?(1):(0)) + "\",\"" + nonDormfrozenInstruments + "\",\"" + frozenInstruments + "\",\"" + ((suspended)?(1):(0)) + "\",\"" + ((closed)?(1):(0)) + "\",\"" + ((locked)?(1):(0)) + "\",\"" + ((frozen)?(1):(0)) + "\",\"" + origin + "\",\"" + scheme + "\",\"" + expectedSettlement + "\",\"" + totalBalance + "\",\"" + timeExpired + "\",\"" + timeBreakageExpired + "\",\"" + loadCountDay + "\",\"" + loadTimestampDaya + "\",\"" + loadTimestampDayc + "\",\"" + loadAmountDay + "\",\"" + loadCountMonth + "\",\"" + loadTimestampMontha + "\",\"" + loadTimestampMonthc + "\",\"" + loadAmountMonth + "\",\"" + _state_id_load_tx + "\",\"" + _state_id_load_count + "\",\"" + _state_id_load_amount + "\",\"" + _state_id_transfer_tx + "\",\"" + _state_id_rights_management + "\",\"" + _state_id_scheme_change + "\",\"" + _state_id_dormancy + "\",\"" + _state_id_breakage + "\",\"" + _state_id_user_lc + "\",\"" + _state_id_purchase + "\",\"" + _state_id_fund_transfers + "\",\"" + no_automata + "\");"); 
idp.insertInDB();
drp.insertInDB();
bp.insertInDB();
 } catch (Exception ex) {
 ex.printStackTrace();
}
}

public static _cls_project1 loadFromDB( String s ) { 
try {
 Statement stat = _cls_project0._conn.createStatement();
 ResultSet rs = stat.executeQuery("select * from _cls_project1 where _s = \"" + s + "\" ;");
 if (rs.next()) {
 _cls_project1 temp = new _cls_project1();
 temp.s = s;
 temp.pk = rs.getLong("_id");
 temp.USER_DEPOSIT_OCT = rs.getBoolean("_USER_DEPOSIT_OCT");
 temp.USER_LOGIN = rs.getBoolean("_USER_LOGIN");
 temp.USER_CHANGE_LOST_PASSWORD = rs.getBoolean("_USER_CHANGE_LOST_PASSWORD");
 temp.USER_ADD_BANK_CARD = rs.getBoolean("_USER_ADD_BANK_CARD");
 temp.USER_ADD_BANK_PAYOUT_ACCOUNT = rs.getBoolean("_USER_ADD_BANK_PAYOUT_ACCOUNT");
 temp.USER_CREATE_VCC = rs.getBoolean("_USER_CREATE_VCC");
 temp.USER_VIEW_VCC = rs.getBoolean("_USER_VIEW_VCC");
 temp.USER_LOAD_VCC = rs.getBoolean("_USER_LOAD_VCC");
 temp.USER_DELETE_VCC = rs.getBoolean("_USER_DELETE_VCC");
 temp.USER_VIEW_TRANSACTION_HISTORY = rs.getBoolean("_USER_VIEW_TRANSACTION_HISTORY");
 temp.USER_LOCK_ACCOUNT = rs.getBoolean("_USER_LOCK_ACCOUNT");
 temp.USER_TRANSFER = rs.getBoolean("_USER_TRANSFER");
 temp.USER_TRANSFER_TO_VCC = rs.getBoolean("_USER_TRANSFER_TO_VCC");
 temp.USER_TRANSFER_TO_PLASTIC = rs.getBoolean("_USER_TRANSFER_TO_PLASTIC");
 temp.USER_BANK_PAYOUT = rs.getBoolean("_USER_BANK_PAYOUT");
 temp.USER_BANK_PAYIN = rs.getBoolean("_USER_BANK_PAYIN");
 temp.USER_VIRTUAL_PAYOUT = rs.getBoolean("_USER_VIRTUAL_PAYOUT");
 temp.USER_PAYOUT_TO_BANKCARD = rs.getBoolean("_USER_PAYOUT_TO_BANKCARD");
 temp.USER_APPLY_FOR_PLASTIC = rs.getBoolean("_USER_APPLY_FOR_PLASTIC");
 temp.USER_APPLY_FOR_PLASTIC_MASTERCARD = rs.getBoolean("_USER_APPLY_FOR_PLASTIC_MASTERCARD");
 temp.USER_APPLY_FOR_PLASTIC_VISA = rs.getBoolean("_USER_APPLY_FOR_PLASTIC_VISA");
 temp.USER_DEPOSIT_MERCHANT_INCOMING_FUNDS = rs.getBoolean("_USER_DEPOSIT_MERCHANT_INCOMING_FUNDS");
 temp.idp = Clock.loadFromDB(rs.getLong("_idp"), false );
 temp.idp._inst = temp;
 temp.drp = Clock.loadFromDB(rs.getLong("_drp"), false );
 temp.drp._inst = temp;
 temp.bp = Clock.loadFromDB(rs.getLong("_bp"), false );
 temp.bp._inst = temp;
 temp.passStart = rs.getLong("_passStart");
 temp.lastFreeze = rs.getLong("_lastFreeze");
 temp.feeExpected = rs.getBoolean("_feeExpected");
 temp.nonDormfrozenInstruments = rs.getInt("_nonDormfrozenInstruments");
 temp.frozenInstruments = rs.getInt("_frozenInstruments");
 temp.suspended = rs.getBoolean("_suspended");
 temp.closed = rs.getBoolean("_closed");
 temp.locked = rs.getBoolean("_locked");
 temp.frozen = rs.getBoolean("_frozen");
 temp.origin = rs.getString("_origin");
 temp.scheme = rs.getString("_scheme");
 temp.expectedSettlement = rs.getDouble("_expectedSettlement");
 temp.totalBalance = rs.getDouble("_totalBalance");
 temp.timeExpired = rs.getLong("_timeExpired");
 temp.timeBreakageExpired = rs.getLong("_timeBreakageExpired");
 temp.loadCountDay = rs.getInt("_loadCountDay");
 temp.loadTimestampDaya = rs.getLong("_loadTimestampDaya");
 temp.loadTimestampDayc = rs.getLong("_loadTimestampDayc");
 temp.loadAmountDay = rs.getDouble("_loadAmountDay");
 temp.loadCountMonth = rs.getInt("_loadCountMonth");
 temp.loadTimestampMontha = rs.getLong("_loadTimestampMontha");
 temp.loadTimestampMonthc = rs.getLong("_loadTimestampMonthc");
 temp.loadAmountMonth = rs.getDouble("_loadAmountMonth");
 temp._state_id_load_tx = rs.getInt("_state_id_load_tx");
 temp._state_id_load_count = rs.getInt("_state_id_load_count");
 temp._state_id_load_amount = rs.getInt("_state_id_load_amount");
 temp._state_id_transfer_tx = rs.getInt("_state_id_transfer_tx");
 temp._state_id_rights_management = rs.getInt("_state_id_rights_management");
 temp._state_id_scheme_change = rs.getInt("_state_id_scheme_change");
 temp._state_id_dormancy = rs.getInt("_state_id_dormancy");
 temp._state_id_breakage = rs.getInt("_state_id_breakage");
 temp._state_id_user_lc = rs.getInt("_state_id_user_lc");
 temp._state_id_purchase = rs.getInt("_state_id_purchase");
 temp._state_id_fund_transfers = rs.getInt("_state_id_fund_transfers");
 temp.no_automata = rs.getInt("_no_automata");
 return temp;
} else return null;
 } catch (Exception ex) {
 ex.printStackTrace();
}
return null;
}

public static _cls_project1 loadByPkFromDB(long pk) { 
try {
 Statement stat = _cls_project0._conn.createStatement();
 ResultSet rs = stat.executeQuery("select * from _cls_project1 where _id=" + pk + ";");
 if (rs.next()) {
 _cls_project1 temp = new _cls_project1();
 temp.parent = _cls_project0._get_cls_project0_inst(false);
 temp.s = rs.getString("_s");
 temp.pk = rs.getLong("_id");
 temp.USER_DEPOSIT_OCT = rs.getBoolean("_USER_DEPOSIT_OCT");
 temp.USER_LOGIN = rs.getBoolean("_USER_LOGIN");
 temp.USER_CHANGE_LOST_PASSWORD = rs.getBoolean("_USER_CHANGE_LOST_PASSWORD");
 temp.USER_ADD_BANK_CARD = rs.getBoolean("_USER_ADD_BANK_CARD");
 temp.USER_ADD_BANK_PAYOUT_ACCOUNT = rs.getBoolean("_USER_ADD_BANK_PAYOUT_ACCOUNT");
 temp.USER_CREATE_VCC = rs.getBoolean("_USER_CREATE_VCC");
 temp.USER_VIEW_VCC = rs.getBoolean("_USER_VIEW_VCC");
 temp.USER_LOAD_VCC = rs.getBoolean("_USER_LOAD_VCC");
 temp.USER_DELETE_VCC = rs.getBoolean("_USER_DELETE_VCC");
 temp.USER_VIEW_TRANSACTION_HISTORY = rs.getBoolean("_USER_VIEW_TRANSACTION_HISTORY");
 temp.USER_LOCK_ACCOUNT = rs.getBoolean("_USER_LOCK_ACCOUNT");
 temp.USER_TRANSFER = rs.getBoolean("_USER_TRANSFER");
 temp.USER_TRANSFER_TO_VCC = rs.getBoolean("_USER_TRANSFER_TO_VCC");
 temp.USER_TRANSFER_TO_PLASTIC = rs.getBoolean("_USER_TRANSFER_TO_PLASTIC");
 temp.USER_BANK_PAYOUT = rs.getBoolean("_USER_BANK_PAYOUT");
 temp.USER_BANK_PAYIN = rs.getBoolean("_USER_BANK_PAYIN");
 temp.USER_VIRTUAL_PAYOUT = rs.getBoolean("_USER_VIRTUAL_PAYOUT");
 temp.USER_PAYOUT_TO_BANKCARD = rs.getBoolean("_USER_PAYOUT_TO_BANKCARD");
 temp.USER_APPLY_FOR_PLASTIC = rs.getBoolean("_USER_APPLY_FOR_PLASTIC");
 temp.USER_APPLY_FOR_PLASTIC_MASTERCARD = rs.getBoolean("_USER_APPLY_FOR_PLASTIC_MASTERCARD");
 temp.USER_APPLY_FOR_PLASTIC_VISA = rs.getBoolean("_USER_APPLY_FOR_PLASTIC_VISA");
 temp.USER_DEPOSIT_MERCHANT_INCOMING_FUNDS = rs.getBoolean("_USER_DEPOSIT_MERCHANT_INCOMING_FUNDS");
 temp.idp = Clock.loadFromDB(rs.getLong("_idp"), false );
 temp.idp._inst = temp;
 temp.drp = Clock.loadFromDB(rs.getLong("_drp"), false );
 temp.drp._inst = temp;
 temp.bp = Clock.loadFromDB(rs.getLong("_bp"), false );
 temp.bp._inst = temp;
 temp.passStart = rs.getLong("_passStart");
 temp.lastFreeze = rs.getLong("_lastFreeze");
 temp.feeExpected = rs.getBoolean("_feeExpected");
 temp.nonDormfrozenInstruments = rs.getInt("_nonDormfrozenInstruments");
 temp.frozenInstruments = rs.getInt("_frozenInstruments");
 temp.suspended = rs.getBoolean("_suspended");
 temp.closed = rs.getBoolean("_closed");
 temp.locked = rs.getBoolean("_locked");
 temp.frozen = rs.getBoolean("_frozen");
 temp.origin = rs.getString("_origin");
 temp.scheme = rs.getString("_scheme");
 temp.expectedSettlement = rs.getDouble("_expectedSettlement");
 temp.totalBalance = rs.getDouble("_totalBalance");
 temp.timeExpired = rs.getLong("_timeExpired");
 temp.timeBreakageExpired = rs.getLong("_timeBreakageExpired");
 temp.loadCountDay = rs.getInt("_loadCountDay");
 temp.loadTimestampDaya = rs.getLong("_loadTimestampDaya");
 temp.loadTimestampDayc = rs.getLong("_loadTimestampDayc");
 temp.loadAmountDay = rs.getDouble("_loadAmountDay");
 temp.loadCountMonth = rs.getInt("_loadCountMonth");
 temp.loadTimestampMontha = rs.getLong("_loadTimestampMontha");
 temp.loadTimestampMonthc = rs.getLong("_loadTimestampMonthc");
 temp.loadAmountMonth = rs.getDouble("_loadAmountMonth");
 temp._state_id_load_tx = rs.getInt("_state_id_load_tx");
 temp._state_id_load_count = rs.getInt("_state_id_load_count");
 temp._state_id_load_amount = rs.getInt("_state_id_load_amount");
 temp._state_id_transfer_tx = rs.getInt("_state_id_transfer_tx");
 temp._state_id_rights_management = rs.getInt("_state_id_rights_management");
 temp._state_id_scheme_change = rs.getInt("_state_id_scheme_change");
 temp._state_id_dormancy = rs.getInt("_state_id_dormancy");
 temp._state_id_breakage = rs.getInt("_state_id_breakage");
 temp._state_id_user_lc = rs.getInt("_state_id_user_lc");
 temp._state_id_purchase = rs.getInt("_state_id_purchase");
 temp._state_id_fund_transfers = rs.getInt("_state_id_fund_transfers");
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
     if (logic.equals("load_tx")){
          if (1==0){}
             else if (stateName.equals("norights")) f.set(this, 1);
             else if (stateName.equals("start")) f.set(this, 3);
             else if (stateName.equals("mismatch")) f.set(this, 2);
          }
     if (logic.equals("load_count")){
          if (1==0){}
             else if (stateName.equals("countExceeded")) f.set(this, 4);
             else if (stateName.equals("start")) f.set(this, 5);
          }
     if (logic.equals("load_amount")){
          if (1==0){}
             else if (stateName.equals("start")) f.set(this, 7);
             else if (stateName.equals("amountExceeded")) f.set(this, 6);
          }
     if (logic.equals("transfer_tx")){
          if (1==0){}
             else if (stateName.equals("norights")) f.set(this, 8);
             else if (stateName.equals("start")) f.set(this, 9);
          }
     if (logic.equals("rights_management")){
          if (1==0){}
             else if (stateName.equals("start")) f.set(this, 10);
          }
     if (logic.equals("scheme_change")){
          if (1==0){}
             else if (stateName.equals("start")) f.set(this, 11);
          }
     if (logic.equals("dormancy")){
          if (1==0){}
             else if (stateName.equals("unexpectedTX")) f.set(this, 12);
             else if (stateName.equals("nondorm")) f.set(this, 18);
             else if (stateName.equals("unexpectedDorm")) f.set(this, 15);
             else if (stateName.equals("earlyDorm")) f.set(this, 14);
             else if (stateName.equals("dorm")) f.set(this, 16);
             else if (stateName.equals("expiredDorm")) f.set(this, 13);
             else if (stateName.equals("thawed")) f.set(this, 17);
          }
     if (logic.equals("breakage")){
          if (1==0){}
             else if (stateName.equals("unexpectedBreak")) f.set(this, 22);
             else if (stateName.equals("unexpectedTX")) f.set(this, 19);
             else if (stateName.equals("break")) f.set(this, 23);
             else if (stateName.equals("nonbreak")) f.set(this, 24);
             else if (stateName.equals("earlyBreak")) f.set(this, 21);
             else if (stateName.equals("expiredBreak")) f.set(this, 20);
          }
     if (logic.equals("user_lc")){
          if (1==0){}
             else if (stateName.equals("unexpectedTX")) f.set(this, 25);
             else if (stateName.equals("start")) f.set(this, 30);
             else if (stateName.equals("created")) f.set(this, 26);
             else if (stateName.equals("active")) f.set(this, 27);
             else if (stateName.equals("locked")) f.set(this, 29);
             else if (stateName.equals("suspended")) f.set(this, 28);
          }
     if (logic.equals("purchase")){
          if (1==0){}
             else if (stateName.equals("unexpected")) f.set(this, 31);
             else if (stateName.equals("start")) f.set(this, 32);
          }
     if (logic.equals("fund_transfers")){
          if (1==0){}
             else if (stateName.equals("start")) f.set(this, 33);
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

public _cls_project1(){}

public _cls_project1( String s) {
try {
 pk = pks++; 
idp = new Clock(this,"idp");
idp.register(15811200000l);
drp = new Clock(this,"drp");
drp.register(2851200000l);
bp = new Clock(this,"bp");
bp.register(31363200000l);
this.s = s;
  initialisation();
} catch (Exception ex) {ex.printStackTrace();} 
}

public void initialisation() {
   idp.reset(RunningClock.now);
   drp.reset(RunningClock.now);
   bp.reset(RunningClock.now);
}

public static void printStackTrace(String prefix) {
  Exception ex = new Exception();
  String sEx = prefix == null ? "" : prefix;
  for (int i = 0; i < ex.getStackTrace().length; i++)
    sEx += ex.getStackTrace()[i];
  _cls_project0._pw.println(sEx);
  _cls_project0._pw.flush();
}

public static _cls_project1 _get_cls_project1_inst(boolean _init,String s) {
_cls_project1 _inst = loadFromDB( s);
if (_inst != null)
{//object loaded from DB
  _inst.parent = _cls_project0._get_cls_project0_inst(_init);
  if (_inst.no_automata == 0)
  { printStackTrace("No more running automata.\r\n"); }
}
else
{
  _inst = new _cls_project1(s);
  _inst.idp.reset(RunningClock.now);
  _inst.drp.reset(RunningClock.now);
  _inst.bp.reset(RunningClock.now);
  if (_init) _inst.specialInitialization(SC.initializeifUser( s));
  _inst.parent = _cls_project0._get_cls_project0_inst(_init);
  _inst.insertInDB();
}
 return _inst;
}

public boolean equals(Object o) {
 if ((o instanceof _cls_project1)
 && (s == null || s.equals(((_cls_project1)o).s))
 && (parent == null || parent.equals(((_cls_project1)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
_performLogic_load_tx(_info, _event);
_performLogic_load_count(_info, _event);
_performLogic_load_amount(_info, _event);
_performLogic_transfer_tx(_info, _event);
_performLogic_rights_management(_info, _event);
_performLogic_scheme_change(_info, _event);
_performLogic_dormancy(_info, _event);
_performLogic_breakage(_info, _event);
_performLogic_user_lc(_info, _event);
_performLogic_purchase(_info, _event);
_performLogic_fund_transfers(_info, _event);
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){
}

public int _state_id_load_tx = 3;

public void _performLogic_load_tx(String _info, int... _event) {
_cls_project0._pw.flush();

if (0==1){}
else if (_state_id_load_tx==3){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && ((t .is ("NormTX")&&t .params .get ("DTYPE").equals ("LoadTXEntry")&&USER_LOAD_VCC &&origin .equals ("WEB"))||(t .is ("CallCentreLoadTX")&&origin .equals ("CALL_CENTRE")))){
		ArrayList list =SC .balanceEntries (s ,(String )t .params .get ("transactionEntryId"));
for (int i =0 ;
i <list .size ();
i ++)if (((HashMap )list .get (i )).get ("type").equals ("ACTUAL")){parent.balUpdateCH .send (list .get (i ));
parent.balCheckCH .send (list .get (i ));
}
		_state_id_load_tx = 3;//moving to state start
		_goto_load_tx(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && ((t .is ("CallCentreLoadTX")&&!origin .equals ("CALL_CENTRE")))){
		_cls_project0._pw.println("[load_tx]" + "Mismatch in transaction type and user origin!! "+origin +" user  "+s +" "+timestamp );

		_state_id_load_tx = 2;//moving to state mismatch
		_goto_load_tx(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && ((t .is ("NormTX")&&t .params .get ("DTYPE").equals ("LoadTXEntry")&&!USER_LOAD_VCC &&origin .equals ("WEB"))||(t .is ("CallCentreLoadTX")&&!USER_LOAD_VCC &&origin .equals ("CALL_CENTRE")))){
		_cls_project0._pw.println("[load_tx]" + "Loading without necessary rights!! "+origin +" user  "+s +" "+timestamp );

		_state_id_load_tx = 1;//moving to state norights
		_goto_load_tx(_info);
		}
}
}

public void _goto_load_tx(String _info){
}

public String _string_load_tx(int _state_id, int _mode){
switch(_state_id){
case 1: if (_mode == 0) return "norights"; else return "!!!SYSTEM REACHED BAD STATE!!! norights "+new _BadStateExceptionproject().toString()+" ";
case 3: if (_mode == 0) return "start"; else return "start";
case 2: if (_mode == 0) return "mismatch"; else return "!!!SYSTEM REACHED BAD STATE!!! mismatch "+new _BadStateExceptionproject().toString()+" ";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
public int _state_id_load_count = 5;

public void _performLogic_load_count(String _info, int... _event) {
_cls_project0._pw.flush();

if (0==1){}
else if (_state_id_load_count==5){
		if (1==0){}
		else if ((_occurredEvent(_event,10/*loadevent*/)) && ((((SC .loadLimits (limits ,s ,currency ,scheme ,origin ,loadtype )&&loadCountDay +1 >(Integer )limits .get ("dayCount"))&&(loadTimestampDayc ==-1 ||timestamp /parent.day -loadTimestampDayc /parent.day <1 ))||((loadCountMonth +1 >(Integer )limits .get ("monthCount"))&&(loadTimestampMonthc ==-1 ||timestamp /parent.month -loadTimestampMonthc /parent.month <1 ))))){
		_cls_project0._pw.println("[load_count]" + "LOAD count exceeded user: "+s +" tx id: "+t .params .get ("transactionEntryId")+"\r\n loaded "+(loadCountDay +1 )+" times in a day... limit: "+limits .get ("dayCount")+"\r\n loaded "+(loadCountMonth +1 )+" times in a month... limit: "+limits .get ("monthCount")+"\r\n origin: "+origin +" scheme: "+scheme +" time: "+timestamp );

		_state_id_load_count = 4;//moving to state countExceeded
		_goto_load_count(_info);
		}
		else if ((_occurredEvent(_event,10/*loadevent*/))){
		if (loadTimestampDayc ==-1 ||timestamp /parent.day -loadTimestampDayc /parent.day >=1 ){loadCountDay =1 ;
loadTimestampDayc =timestamp ;
}else loadCountDay ++;
if (loadTimestampMonthc ==-1 ||timestamp /parent.month -loadTimestampMonthc /parent.month >=1 ){loadCountMonth =1 ;
loadTimestampMonthc =timestamp ;
}else loadCountMonth ++;

		_state_id_load_count = 5;//moving to state start
		_goto_load_count(_info);
		}
}
}

public void _goto_load_count(String _info){
}

public String _string_load_count(int _state_id, int _mode){
switch(_state_id){
case 4: if (_mode == 0) return "countExceeded"; else return "!!!SYSTEM REACHED BAD STATE!!! countExceeded "+new _BadStateExceptionproject().toString()+" ";
case 5: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
public int _state_id_load_amount = 7;

public void _performLogic_load_amount(String _info, int... _event) {
_cls_project0._pw.flush();

if (0==1){}
else if (_state_id_load_amount==7){
		if (1==0){}
		else if ((_occurredEvent(_event,10/*loadevent*/)) && ((((SC .loadLimits (limits ,s ,currency ,scheme ,origin ,loadtype )&&loadAmountDay +amount >(Double )limits .get ("dayAmount"))&&(loadTimestampDaya ==-1 ||timestamp /parent.day -loadTimestampDaya /parent.day <1 ))||(loadAmountMonth +amount >(Double )limits .get ("monthAmount")&&(loadTimestampMontha ==-1 ||timestamp /parent.month -loadTimestampMontha /parent.month <1 ))))){
		_cls_project0._pw.println("[load_amount]" + "LOAD amount exceeded user: "+s +" tx id: "+t .params .get ("transactionEntryId")+"\r\n loaded "+currency +" "+(loadAmountDay +amount )+" in a day... limit: "+limits .get ("dayAmount")+"\r\n loaded "+currency +" "+(loadAmountMonth +amount )+" in a month... limit: "+limits .get ("monthAmount")+"\r\n origin: "+origin +" scheme: "+scheme +" time: "+timestamp );

		_state_id_load_amount = 6;//moving to state amountExceeded
		_goto_load_amount(_info);
		}
		else if ((_occurredEvent(_event,10/*loadevent*/))){
		if (loadTimestampDaya ==-1 ||timestamp /parent.day -loadTimestampDaya /parent.day >=1 ){loadTimestampDaya =timestamp ;
loadAmountDay =amount ;
}else loadAmountDay +=amount ;
if (loadTimestampMontha ==-1 ||timestamp /parent.month -loadTimestampMontha /parent.month >=1 ){loadTimestampMontha =timestamp ;
loadAmountMonth =amount ;
}else loadAmountMonth +=amount ;

		_state_id_load_amount = 7;//moving to state start
		_goto_load_amount(_info);
		}
}
}

public void _goto_load_amount(String _info){
}

public String _string_load_amount(int _state_id, int _mode){
switch(_state_id){
case 7: if (_mode == 0) return "start"; else return "start";
case 6: if (_mode == 0) return "amountExceeded"; else return "!!!SYSTEM REACHED BAD STATE!!! amountExceeded "+new _BadStateExceptionproject().toString()+" ";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
public int _state_id_transfer_tx = 9;

public void _performLogic_transfer_tx(String _info, int... _event) {
_cls_project0._pw.flush();

if (0==1){}
else if (_state_id_transfer_tx==9){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("NormTX")&&t .params .get ("DTYPE").equals ("TransferTXEntry")&&(USER_TRANSFER ||USER_TRANSFER_TO_VCC ||USER_TRANSFER_TO_PLASTIC ))){
		ArrayList list =SC .balanceEntries (s ,(String )t .params .get ("transactionEntryId"));
for (int i =0 ;
i <list .size ();
i ++)if (((HashMap )list .get (i )).get ("type").equals ("ACTUAL")){parent.balUpdateCH .send (list .get (i ));
parent.balCheckCH .send (list .get (i ));
}
		_state_id_transfer_tx = 9;//moving to state start
		_goto_transfer_tx(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("NormTX")&&t .params .get ("DTYPE").equals ("TransferTXEntry"))){
		_cls_project0._pw.println("[transfer_tx]" + "Transferring without necessary rights!! user  "+s +" "+timestamp );

		_state_id_transfer_tx = 8;//moving to state norights
		_goto_transfer_tx(_info);
		}
}
}

public void _goto_transfer_tx(String _info){
}

public String _string_transfer_tx(int _state_id, int _mode){
switch(_state_id){
case 8: if (_mode == 0) return "norights"; else return "!!!SYSTEM REACHED BAD STATE!!! norights "+new _BadStateExceptionproject().toString()+" ";
case 9: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
public int _state_id_rights_management = 10;

public void _performLogic_rights_management(String _info, int... _event) {
_cls_project0._pw.flush();

if (0==1){}
else if (_state_id_rights_management==10){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("ChangeRights"))){
		try {String message =(String )t .params .get ("message");
Class c =this .getClass ();
if (message .contains ("grant")&&message .contains ("right"))for (String s :SC .rightsList (message )){Field f =c .getField (s );
f .set (this ,true );
}else if (message .contains ("Set")&&message .contains ("right")){USER_DEPOSIT_OCT =false ;
USER_LOGIN =false ;
USER_CHANGE_LOST_PASSWORD =false ;
USER_ADD_BANK_CARD =false ;
USER_ADD_BANK_PAYOUT_ACCOUNT =false ;
USER_CREATE_VCC =false ;
USER_VIEW_VCC =false ;
USER_LOAD_VCC =false ;
USER_DELETE_VCC =false ;
USER_VIEW_TRANSACTION_HISTORY =false ;
USER_LOCK_ACCOUNT =false ;
USER_TRANSFER =false ;
USER_TRANSFER_TO_VCC =false ;
USER_TRANSFER_TO_PLASTIC =false ;
USER_BANK_PAYOUT =false ;
USER_BANK_PAYIN =false ;
USER_VIRTUAL_PAYOUT =false ;
USER_PAYOUT_TO_BANKCARD =false ;
USER_APPLY_FOR_PLASTIC =false ;
USER_APPLY_FOR_PLASTIC_MASTERCARD =false ;
USER_APPLY_FOR_PLASTIC_VISA =false ;
for (String s :SC .rightsList (message )){Field f =c .getField (s );
f .set (this ,true );
}}else if (message .contains ("revoke")&&message .contains ("right"))for (String s :SC .rightsList (message )){Field f =c .getField (s );
f .set (this ,false );
}}catch (Exception ex ){ex .printStackTrace ();
}
		_state_id_rights_management = 10;//moving to state start
		_goto_rights_management(_info);
		}
}
}

public void _goto_rights_management(String _info){
}

public String _string_rights_management(int _state_id, int _mode){
switch(_state_id){
case 10: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
public int _state_id_scheme_change = 11;

public void _performLogic_scheme_change(String _info, int... _event) {
_cls_project0._pw.flush();

if (0==1){}
else if (_state_id_scheme_change==11){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("ChangeScheme"))){
		
		_state_id_scheme_change = 11;//moving to state start
		_goto_scheme_change(_info);
		}
}
}

public void _goto_scheme_change(String _info){
}

public String _string_scheme_change(int _state_id, int _mode){
switch(_state_id){
case 11: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
public int _state_id_dormancy = 18;

public void _performLogic_dormancy(String _info, int... _event) {
_cls_project0._pw.flush();

if (0==1){}
else if (_state_id_dormancy==18){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("NormTX"))){
		passStart =timestamp ;
idp .reset (timestamp );
drp .reset (timestamp );

		_state_id_dormancy = 18;//moving to state nondorm
		_goto_dormancy(_info);
		}
		else if ((_occurredEvent(_event,12/*validTX*/))){
		passStart =RunningClock .now ;
idp .reset (RunningClock .now );
drp .reset (RunningClock .now );

		_state_id_dormancy = 18;//moving to state nondorm
		_goto_dormancy(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("DormTX")&&(idp .current (timestamp )>=parent.idp_limit ||drp .current (timestamp )>=parent.drp_limit )&&!origin .equals ("CALL_CENTRE")&&(!suspended ||!parent.ignore_suspended_dorm )&&(!locked ||!parent.ignore_blocked_dorm )&&(nonDormfrozenInstruments ==0 ||!parent.ignore_frozen_dorm )&&(USER_LOAD_VCC ||!parent.ignore_blocked_dorm ))){
		drp .reset (timestamp );
feeExpected =true ;
lastFreeze =timestamp ;

		_state_id_dormancy = 16;//moving to state dorm
		_goto_dormancy(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("DormTX")&&idp .current (timestamp )<parent.idp_limit )){
		_cls_project0._pw.println("[dormancy]" + "Early Dormancy at "+timestamp +" user "+s );

		_state_id_dormancy = 14;//moving to state earlyDorm
		_goto_dormancy(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("DormTX"))){
		_cls_project0._pw.println("[dormancy]" + "Unexpected Dormancy at "+timestamp +" user "+s );

		_state_id_dormancy = 15;//moving to state unexpectedDorm
		_goto_dormancy(_info);
		}
		else if ((_occurredEvent(_event,2/*cATidp*/)) && (!origin .equals ("CALL_CENTRE")&&(!suspended ||!parent.ignore_suspended_dorm )&&(!locked ||!parent.ignore_blocked_dorm )&&(nonDormfrozenInstruments ==0 ||!parent.ignore_frozen_dorm )&&(USER_LOAD_VCC ||!parent.ignore_blocked_dorm )&&(timestamp >SC .initialTime )&&!closed )){
		timeExpired =timestamp -parent.leeway ;
_cls_project0._pw.println("[dormancy]" + "Expired Dormancy! User: "+s +" "+passStart +"->"+timeExpired );

		_state_id_dormancy = 13;//moving to state expiredDorm
		_goto_dormancy(_info);
		}
}
else if (_state_id_dormancy==16){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("DormFeeTX"))){
		feeExpected =false ;
ArrayList list =SC .balanceEntries (s ,(String )t .params .get ("transactionEntryId"));
for (int i =0 ;
i <list .size ();
i ++)if (((HashMap )list .get (i )).get ("type").equals ("ACTUAL")){Double actual =(Double )((HashMap )list .get (i )).get ("adjustment");
if (-amount !=actual )_cls_project0._pw.println("[dormancy]" + "Expected adjustment ("+amount +") does NOT match actual ("+actual +")! + user "+s +" at "+timestamp );
parent.balUpdateCH .send (list .get (i ));
parent.balCheckCH .send (list .get (i ));
}
		_state_id_dormancy = 16;//moving to state dorm
		_goto_dormancy(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("NormTX"))){
		_cls_project0._pw.println("[dormancy]" + "Unexpected transaction while dormant!! User "+s +" at "+timestamp );

		_state_id_dormancy = 12;//moving to state unexpectedTX
		_goto_dormancy(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("UnDormTX"))){
		passStart =timestamp ;
idp .reset (timestamp );
bp .reset (timestamp );
if (feeExpected ){_cls_project0._pw.println("[dormancy]" + "Fee Expected!! Not Found! Check Credit! User "+s );
feeExpected =false ;
}
		_state_id_dormancy = 17;//moving to state thawed
		_goto_dormancy(_info);
		}
}
else if (_state_id_dormancy==13){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("DormTX")&&drp .current (timestamp )>=parent.drp_limit )){
		drp .reset (timestamp );
lastFreeze =timestamp ;
_cls_project0._pw.println("[dormancy]" + "Dormancy done!! User "+s +" at "+timestamp +" "+((timestamp -timeExpired )/(double )(1000 *60 *60 ))+" hours late");
feeExpected =true ;

		_state_id_dormancy = 16;//moving to state dorm
		_goto_dormancy(_info);
		}
}
else if (_state_id_dormancy==17){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("DormTX")&&(drp .current (timestamp )>=parent.drp_limit )&&!origin .equals ("CALL_CENTRE")&&(!suspended ||!parent.ignore_suspended_dorm )&&(!locked ||!parent.ignore_blocked_dorm )&&(nonDormfrozenInstruments ==0 ||!parent.ignore_frozen_dorm )&&(USER_LOAD_VCC ||!parent.ignore_blocked_dorm ))){
		drp .reset (timestamp );
feeExpected =true ;
lastFreeze =timestamp ;

		_state_id_dormancy = 16;//moving to state dorm
		_goto_dormancy(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("NormTX"))){
		passStart =timestamp ;
idp .reset (timestamp );
drp .reset (timestamp );

		_state_id_dormancy = 18;//moving to state nondorm
		_goto_dormancy(_info);
		}
		else if ((_occurredEvent(_event,12/*validTX*/))){
		passStart =RunningClock .now ;
idp .reset (RunningClock .now );
drp .reset (RunningClock .now );

		_state_id_dormancy = 18;//moving to state nondorm
		_goto_dormancy(_info);
		}
		else if ((_occurredEvent(_event,4/*cATdrp*/)) && (!origin .equals ("CALL_CENTRE")&&(!suspended ||!parent.ignore_suspended_dorm )&&(!locked ||!parent.ignore_blocked_dorm )&&(nonDormfrozenInstruments ==0 ||!parent.ignore_frozen_dorm )&&(USER_LOAD_VCC ||!parent.ignore_blocked_dorm )&&(timestamp >SC .initialTime )&&!closed )){
		timeExpired =timestamp -parent.leeway ;
_cls_project0._pw.println("[dormancy]" + "Expired re-Dormancy! User: "+s +" "+passStart +"->"+timeExpired );

		_state_id_dormancy = 13;//moving to state expiredDorm
		_goto_dormancy(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("DormTX")&&idp .current (timestamp )<parent.idp_limit )){
		_cls_project0._pw.println("[dormancy]" + "Early Dormancy at "+timestamp +" user "+s );

		_state_id_dormancy = 14;//moving to state earlyDorm
		_goto_dormancy(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("DormTX"))){
		_cls_project0._pw.println("[dormancy]" + "Unexpected Dormancy at "+timestamp +" user "+s );
_cls_project0._pw.println("[dormancy]" + "{}origin "+origin +" suspended "+suspended +" locked "+locked );
_cls_project0._pw.println("[dormancy]" + "{}ignore_suspended_dorm "+parent.ignore_suspended_dorm +" ignore_blocked_dorm "+parent.ignore_blocked_dorm +" nonDormfrozenInstruments "+nonDormfrozenInstruments );
_cls_project0._pw.println("[dormancy]" + "{} USER_LOAD_VCC "+USER_LOAD_VCC +" ignore_blocked_dorm "+parent.ignore_blocked_dorm +" ignore_frozen_dorm "+parent.ignore_frozen_dorm );

		_state_id_dormancy = 15;//moving to state unexpectedDorm
		_goto_dormancy(_info);
		}
}
}

public void _goto_dormancy(String _info){
}

public String _string_dormancy(int _state_id, int _mode){
switch(_state_id){
case 12: if (_mode == 0) return "unexpectedTX"; else return "!!!SYSTEM REACHED BAD STATE!!! unexpectedTX "+new _BadStateExceptionproject().toString()+" ";
case 18: if (_mode == 0) return "nondorm"; else return "nondorm";
case 15: if (_mode == 0) return "unexpectedDorm"; else return "!!!SYSTEM REACHED BAD STATE!!! unexpectedDorm "+new _BadStateExceptionproject().toString()+" ";
case 14: if (_mode == 0) return "earlyDorm"; else return "!!!SYSTEM REACHED BAD STATE!!! earlyDorm "+new _BadStateExceptionproject().toString()+" ";
case 16: if (_mode == 0) return "dorm"; else return "dorm";
case 13: if (_mode == 0) return "expiredDorm"; else return "!!!SYSTEM REACHED BAD STATE!!! expiredDorm "+new _BadStateExceptionproject().toString()+" ";
case 17: if (_mode == 0) return "thawed"; else return "thawed";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
public int _state_id_breakage = 24;

public void _performLogic_breakage(String _info, int... _event) {
_cls_project0._pw.flush();

if (0==1){}
else if (_state_id_breakage==23){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("ReclaimFeeTX"))){
		ArrayList list =SC .balanceEntries (s ,(String )t .params .get ("transactionEntryId"));
for (int i =0 ;
i <list .size ();
i ++)if (((HashMap )list .get (i )).get ("type").equals ("ACTUAL")){Double actual =(Double )((HashMap )list .get (i )).get ("adjustment");
if (-amount !=actual )_cls_project0._pw.println("[breakage]" + "Expected adjustment ("+amount +") does NOT match actual ("+actual +")! user "+s );
parent.balUpdateCH .send (list .get (i ));
parent.balCheckCH .send (list .get (i ));
}
		_state_id_breakage = 23;//moving to state break
		_goto_breakage(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("NormTX"))){
		
		_state_id_breakage = 19;//moving to state unexpectedTX
		_goto_breakage(_info);
		}
}
else if (_state_id_breakage==24){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("NormTX"))){
		passStart =timestamp ;
bp .reset (timestamp );

		_state_id_breakage = 24;//moving to state nonbreak
		_goto_breakage(_info);
		}
		else if ((_occurredEvent(_event,12/*validTX*/))){
		passStart =RunningClock .now ;
bp .reset (RunningClock .now );

		_state_id_breakage = 24;//moving to state nonbreak
		_goto_breakage(_info);
		}
		else if ((_occurredEvent(_event,6/*cATbp*/)) && (!origin .equals ("CALL_CENTRE")&&(!suspended ||!parent.ignore_suspended_break )&&(!locked ||!parent.ignore_blocked_break )&&(nonDormfrozenInstruments ==0 ||!parent.ignore_frozen_break )&&(USER_LOAD_VCC ||!parent.ignore_blocked_break )&&(timestamp >SC .initialTime ))){
		timeBreakageExpired =timestamp -parent.leeway ;
_cls_project0._pw.println("[breakage]" + "Expired Breakage! user "+s +" "+passStart +"->"+timestamp );

		_state_id_breakage = 20;//moving to state expiredBreak
		_goto_breakage(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("BreakTX")&&bp .current (timestamp )>=parent.bp_limit &&!origin .equals ("CALL_CENTRE")&&(!suspended ||!parent.ignore_suspended_break )&&(!locked ||!parent.ignore_blocked_break )&&(nonDormfrozenInstruments ==0 ||!parent.ignore_frozen_break )&&(USER_LOAD_VCC ||!parent.ignore_blocked_break ))){
		
		_state_id_breakage = 23;//moving to state break
		_goto_breakage(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("BreakTX")&&bp .current (timestamp )<parent.bp_limit )){
		_cls_project0._pw.println("[breakage]" + "Early Breakage at "+timestamp +" user "+s );

		_state_id_breakage = 21;//moving to state earlyBreak
		_goto_breakage(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("BreakTX"))){
		_cls_project0._pw.println("[breakage]" + "Unexpected Breakage at "+timestamp +" user "+s );
_cls_project0._pw.println("[breakage]" + "{}origin "+origin +" suspended "+suspended +" locked "+locked );
_cls_project0._pw.println("[breakage]" + "{}ignore_suspended_break "+parent.ignore_suspended_break +" ignore_blocked_break "+parent.ignore_blocked_break +" nonDormfrozenInstruments "+nonDormfrozenInstruments );
_cls_project0._pw.println("[breakage]" + "{} USER_LOAD_VCC "+USER_LOAD_VCC +" ignore_blocked_break "+parent.ignore_blocked_break +" ignore_frozen_break "+parent.ignore_frozen_break );

		_state_id_breakage = 22;//moving to state unexpectedBreak
		_goto_breakage(_info);
		}
}
else if (_state_id_breakage==20){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("BreakTX")&&bp .current (timestamp )>=parent.bp_limit )){
		_cls_project0._pw.println("[breakage]" + "Breakage done!! user "+s +" at "+((timestamp -timeBreakageExpired )/(double )(1000 *60 *60 ))+" hours late");

		_state_id_breakage = 23;//moving to state break
		_goto_breakage(_info);
		}
}
}

public void _goto_breakage(String _info){
}

public String _string_breakage(int _state_id, int _mode){
switch(_state_id){
case 22: if (_mode == 0) return "unexpectedBreak"; else return "!!!SYSTEM REACHED BAD STATE!!! unexpectedBreak "+new _BadStateExceptionproject().toString()+" ";
case 19: if (_mode == 0) return "unexpectedTX"; else return "!!!SYSTEM REACHED BAD STATE!!! unexpectedTX "+new _BadStateExceptionproject().toString()+" ";
case 23: if (_mode == 0) return "break"; else return "break";
case 24: if (_mode == 0) return "nonbreak"; else return "nonbreak";
case 21: if (_mode == 0) return "earlyBreak"; else return "!!!SYSTEM REACHED BAD STATE!!! earlyBreak "+new _BadStateExceptionproject().toString()+" ";
case 20: if (_mode == 0) return "expiredBreak"; else return "!!!SYSTEM REACHED BAD STATE!!! expiredBreak "+new _BadStateExceptionproject().toString()+" ";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
public int _state_id_user_lc = 30;

public void _performLogic_user_lc(String _info, int... _event) {
_cls_project0._pw.flush();

if (0==1){}
else if (_state_id_user_lc==26){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("NormTX"))){
		
		_state_id_user_lc = 25;//moving to state unexpectedTX
		_goto_user_lc(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("Activation"))){
		
		_state_id_user_lc = 27;//moving to state active
		_goto_user_lc(_info);
		}
}
else if (_state_id_user_lc==30){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("Creation"))){
		origin =SC .getUserType (s );
scheme =SC .getUserScheme (s );

		_state_id_user_lc = 26;//moving to state created
		_goto_user_lc(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("NormTX"))){
		
		_state_id_user_lc = 25;//moving to state unexpectedTX
		_goto_user_lc(_info);
		}
}
else if (_state_id_user_lc==27){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("AcctLock"))){
		locked =true ;

		_state_id_user_lc = 29;//moving to state locked
		_goto_user_lc(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("AcctSuspend"))){
		suspended =true ;

		_state_id_user_lc = 28;//moving to state suspended
		_goto_user_lc(_info);
		}
}
else if (_state_id_user_lc==29){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("AcctUnSuspend"))){
		locked =false ;

		_state_id_user_lc = 27;//moving to state active
		_goto_user_lc(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("NormTX"))){
		
		_state_id_user_lc = 25;//moving to state unexpectedTX
		_goto_user_lc(_info);
		}
}
else if (_state_id_user_lc==28){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("AcctUnSuspend"))){
		suspended =false ;

		_state_id_user_lc = 27;//moving to state active
		_goto_user_lc(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("NormTX"))){
		
		_state_id_user_lc = 25;//moving to state unexpectedTX
		_goto_user_lc(_info);
		}
}
}

public void _goto_user_lc(String _info){
}

public String _string_user_lc(int _state_id, int _mode){
switch(_state_id){
case 25: if (_mode == 0) return "unexpectedTX"; else return "!!!SYSTEM REACHED BAD STATE!!! unexpectedTX "+new _BadStateExceptionproject().toString()+" ";
case 30: if (_mode == 0) return "start"; else return "start";
case 26: if (_mode == 0) return "created"; else return "created";
case 27: if (_mode == 0) return "active"; else return "active";
case 29: if (_mode == 0) return "locked"; else return "locked";
case 28: if (_mode == 0) return "suspended"; else return "suspended";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
public int _state_id_purchase = 32;

public void _performLogic_purchase(String _info, int... _event) {
_cls_project0._pw.flush();

if (0==1){}
else if (_state_id_purchase==32){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("PurchaseTX"))){
		parent.validTXCH .send (s );
expectedSettlement +=amount ;

		_state_id_purchase = 32;//moving to state start
		_goto_purchase(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("PurchaseVoidTX")&&expectedSettlement >=amount )){
		parent.validTXCH .send (s );
expectedSettlement -=amount ;

		_state_id_purchase = 32;//moving to state start
		_goto_purchase(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("PurchaseVoidTX"))){
		parent.validTXCH .send (s );

		_state_id_purchase = 31;//moving to state unexpected
		_goto_purchase(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("PurchaseCancTX")&&expectedSettlement >=amount )){
		parent.validTXCH .send (s );
expectedSettlement -=amount ;

		_state_id_purchase = 32;//moving to state start
		_goto_purchase(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("PurchaseCancTX"))){
		parent.validTXCH .send (s );

		_state_id_purchase = 31;//moving to state unexpected
		_goto_purchase(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("PurchaseSettleTX"))){
		parent.validTXCH .send (s );
expectedSettlement -=amount ;
ArrayList list =SC .balanceEntries (s ,(String )t .params .get ("transactionEntryId"));
for (int i =0 ;
i <list .size ();
i ++)if (((HashMap )list .get (i )).get ("type").equals ("ACTUAL")){parent.balUpdateCH .send (list .get (i ));
parent.balCheckCH .send (list .get (i ));
}
		_state_id_purchase = 32;//moving to state start
		_goto_purchase(_info);
		}
}
}

public void _goto_purchase(String _info){
}

public String _string_purchase(int _state_id, int _mode){
switch(_state_id){
case 31: if (_mode == 0) return "unexpected"; else return "!!!SYSTEM REACHED BAD STATE!!! unexpected "+new _BadStateExceptionproject().toString()+" ";
case 32: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
public int _state_id_fund_transfers = 33;

public void _performLogic_fund_transfers(String _info, int... _event) {
_cls_project0._pw.flush();

if (0==1){}
else if (_state_id_fund_transfers==33){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("MoneyToUserTX"))){
		parent.validTXCH .send (s );
ArrayList list =SC .balanceEntries (s ,(String )t .params .get ("transactionEntryId"));
for (int i =0 ;
i <list .size ();
i ++)if (((HashMap )list .get (i )).get ("type").equals ("ACTUAL")){parent.balUpdateCH .send (list .get (i ));
parent.balCheckCH .send (list .get (i ));
}
		_state_id_fund_transfers = 33;//moving to state start
		_goto_fund_transfers(_info);
		}
		else if ((_occurredEvent(_event,8/*all1*/)) && (t .is ("MoneyFromUserTX"))){
		parent.validTXCH .send (s );
ArrayList list =SC .balanceEntries (s ,(String )t .params .get ("transactionEntryId"));
for (int i =0 ;
i <list .size ();
i ++)if (((HashMap )list .get (i )).get ("type").equals ("ACTUAL")){parent.balUpdateCH .send (list .get (i ));
parent.balCheckCH .send (list .get (i ));
}
		_state_id_fund_transfers = 33;//moving to state start
		_goto_fund_transfers(_info);
		}
}
}

public void _goto_fund_transfers(String _info){
}

public String _string_fund_transfers(int _state_id, int _mode){
switch(_state_id){
case 33: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}