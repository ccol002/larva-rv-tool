package larva;

import main.*;
import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_minepump0 implements _callable{

public static PrintWriter pw; 
public static _cls_minepump0 root;

public static LinkedHashMap<_cls_minepump0,_cls_minepump0> _cls_minepump0_instances;

_cls_minepump0 parent; //to remain null - this class does not have a parent!
public static DH2O __1;
public static Main __0;
public static Main __3;
public static boolean _b;
public static ALARM __2;
public static boolean D;
public static boolean Alarm;
int no_automata;

int _state_id_then_19;
public Clock _clock;
 public boolean always_since_68 ;
 public boolean never_p_29 ;
 public boolean pre_1_64 ;
 public boolean boolg_8_60 ;
 public boolean pre_2_65 ;
 public boolean pre_3_69 ;
 public boolean then_16_30 ;
 public boolean pre_16_33 ;
 public boolean strict_after_85 ;
 public boolean pre_10_84 ;
 public boolean first_56 ;
 public long pre_11_83 ;
 public boolean p_11_46 ;
 public boolean p_11_45 ;
 public boolean pre_1_54 ;
 public long pre_12_82 ;
 public boolean pre_2_55 ;
 public long pre_13_81 ;
 public long since_48 ;
 public boolean pre_0_41 ;
 public boolean pre_8_58 ;
 public boolean interval_2_61 ;
 public boolean len_13_47 ;
 public boolean pre_9_57 ;
 public boolean age_18_18 ;
 public boolean after_40 ;
 public boolean pre_15_74 ;
 public boolean interval_10_32 ;
 public boolean strict_after_63 ;
 public boolean always_since_38 ;
 public boolean p_9_37 ;
 public boolean pre_1_35 ;
 public boolean pre_2_36 ;
 public boolean never_p_59 ;
 public boolean pre_3_39 ;
 public boolean pre_14_62 ;
 public boolean strict_after_53 ;
 public boolean pre_10_52 ;
 public boolean pre_0_25 ;
 public boolean first_26 ;
 public long pre_11_51 ;
 public boolean pre_1_86 ;
 public boolean p_3_78 ;
 public long pre_12_50 ;
 public boolean p_3_76 ;
 public boolean pre_2_87 ;
 public long since_80 ;
 public boolean p_4_77 ;
 public boolean after_24 ;
 public boolean p_4_75 ;
 public boolean boolg_14_42 ;
 public boolean pre_4_23 ;
 public boolean p_0_67 ;
 public long pre_5_22 ;
 public boolean p_17_19 ;
 public boolean p_1_66 ;
 public boolean boolg_7_72 ;
 public boolean pre_8_28 ;
 public long pre_13_49 ;
 public boolean pre_0_71 ;
 public boolean pre_9_27 ;
 public boolean len_6_79 ;
 public boolean begin_12_43 ;
 public long _rt_clock ;
 public boolean after_70 ;
 public boolean boolg_15_31 ;
 public boolean strict_after_34 ;
 public boolean begin_5_73 ;
 public long temptime_21 ;
 public boolean pre_17_44 ;
 public boolean _p ;
 public long age_20 ;

public static void initialize(){
//note that this initialisation does not include user-defined declarations in the Variables section


_cls_minepump0_instances = new LinkedHashMap<_cls_minepump0,_cls_minepump0>();
try{
root = new _cls_minepump0();
_cls_minepump0_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_minepump0() {
_clock = new Clock(this,"_clock");
}

public void initialisation() {
no_automata = 1;
//initialise automata
_state_id_then_19 = 1;
   _clock.reset();
}

public static _cls_minepump0 _get_cls_minepump0_inst() { synchronized(_cls_minepump0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_minepump0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 1;
}

public void _call(String _info, int... _event){
synchronized(_cls_minepump0_instances){
_performLogic_then_19(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_minepump0[] a = new _cls_minepump0[1];
synchronized(_cls_minepump0_instances){
a = _cls_minepump0_instances.keySet().toArray(a);}
for (_cls_minepump0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_minepump0_instances){
_cls_minepump0_instances.remove(this);}
synchronized(_clock){
_clock.off();
_clock._inst = null;
_clock = null;}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}


public void _performLogic_then_19(String _info, int... _event) {

System.out.println("[then_19]AUTOMATON::> then_19("+") STATE::>"+ _string_then_19(_state_id_then_19, 0));

if (0==1){}
else if (_state_id_then_19==1){
		if (1==0){}
		else if ((_occurredEvent(_event,6/*initializationEvent*/))){
		pre_4_23 =false ;
pre_5_22 =0 ;
pre_0_25 =false ;
pre_8_28 =false ;
pre_9_27 =false ;
pre_1_35 =false ;
pre_2_36 =false ;
pre_0_41 =false ;
pre_17_44 =false ;
pre_10_52 =false ;
pre_11_51 =0 ;
pre_12_50 =0 ;
pre_13_49 =0 ;
pre_1_54 =false ;
pre_2_55 =false ;
pre_8_58 =false ;
pre_9_57 =false ;
pre_1_64 =false ;
pre_2_65 =false ;
pre_0_71 =false ;
pre_15_74 =false ;
pre_10_84 =false ;
pre_11_83 =0 ;
pre_12_82 =0 ;
pre_13_81 =0 ;
pre_1_86 =false ;
pre_2_87 =false ;

		_state_id_then_19 = 0;//moving to state lustre
		_goto_then_19(_info);
		}
}
else if (_state_id_then_19==0){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*periodicEvent*/))){
		_rt_clock =_clock .current_long ();
after_24 =(_b )?(true ):(pre_0_25 );
p_11_45 =Alarm ;
p_11_46 =Alarm ;
p_3_76 =D ;
never_p_29 =(_b )?(true ):((pre_8_28 )?(false ):(pre_9_27 ));
p_9_37 =Alarm ;
p_3_78 =D ;
p_0_67 =D ;
p_17_19 =D ;
p_4_77 =!(p_3_78 );
p_4_75 =!(p_3_76 );
p_1_66 =!(p_0_67 );
temptime_21 =(!(after_24 &&pre_4_23 )||_b )?(_rt_clock ):(pre_5_22 );
age_20 =_rt_clock -temptime_21 ;
age_18_18 =age_20 <1000.0 ;
first_26 =!(age_18_18 )&&never_p_29 ;
never_p_59 =(first_26 )?(true ):((pre_8_58 )?(false ):(pre_9_57 ));
strict_after_53 =(first_26 )?(false ):((pre_1_54 )?(true ):(pre_2_55 ));
begin_12_43 =(first_26 &&!(p_11_45 ))?(false ):((first_26 &&p_11_46 )?(true ):(pre_17_44 ));
after_40 =(first_26 )?(true ):(pre_0_41 );
strict_after_34 =(first_26 )?(false ):((pre_1_35 )?(true ):(pre_2_36 ));
since_48 =(first_26 )?(0 ):((strict_after_53 &&pre_10_52 )?(pre_11_51 +_rt_clock -pre_12_50 ):(pre_13_49 ));
interval_10_32 =strict_after_34 &&pre_16_33 ;
always_since_38 =(first_26 )?(p_9_37 ):((after_40 )?(p_9_37 &&pre_3_39 ):(true ));
len_13_47 =since_48 <=0.0 ;
boolg_14_42 =begin_12_43 &&len_13_47 ;
boolg_15_31 =interval_10_32 ||boolg_14_42 ;
first_56 =!(boolg_15_31 )&&never_p_59 ;
strict_after_85 =(first_56 )?(false ):((pre_1_86 )?(true ):(pre_2_87 ));
strict_after_63 =(first_56 )?(false ):((pre_1_64 )?(true ):(pre_2_65 ));
begin_5_73 =(first_56 &&!(p_4_75 ))?(false ):((first_56 &&p_4_77 )?(true ):(pre_15_74 ));
after_70 =(first_56 )?(true ):(pre_0_71 );
always_since_68 =(first_56 )?(p_1_66 ):((after_70 )?(p_1_66 &&pre_3_69 ):(true ));
interval_2_61 =strict_after_63 &&pre_14_62 ;
since_80 =(first_56 )?(0 ):((strict_after_85 &&pre_10_84 )?(pre_11_83 +_rt_clock -pre_12_82 ):(pre_13_81 ));
len_6_79 =since_80 <=0.0 ;
boolg_7_72 =begin_5_73 &&len_6_79 ;
boolg_8_60 =interval_2_61 ||boolg_7_72 ;
then_16_30 =boolg_8_60 ;
_p =then_16_30 ;
pre_4_23 =p_17_19 ;
pre_5_22 =temptime_21 ;
pre_0_25 =after_24 ;
pre_8_28 =!(age_18_18 );
pre_9_27 =never_p_29 ;
pre_16_33 =always_since_38 ;
pre_1_35 =first_26 ;
pre_2_36 =strict_after_34 ;
pre_3_39 =always_since_38 ;
pre_0_41 =after_40 ;
pre_17_44 =begin_12_43 ;
pre_10_52 =true ;
pre_11_51 =since_48 ;
pre_12_50 =_rt_clock ;
pre_13_49 =since_48 ;
pre_1_54 =first_26 ;
pre_2_55 =strict_after_53 ;
pre_8_58 =!(boolg_15_31 );
pre_9_57 =never_p_59 ;
pre_14_62 =always_since_68 ;
pre_1_64 =first_56 ;
pre_2_65 =strict_after_63 ;
pre_3_69 =always_since_68 ;
pre_0_71 =after_70 ;
pre_15_74 =begin_5_73 ;
pre_10_84 =true ;
pre_11_83 =since_80 ;
pre_12_82 =_rt_clock ;
pre_13_81 =since_80 ;
pre_1_86 =first_56 ;
pre_2_87 =strict_after_85 ;
System .out .println (" _b: "+_b +" "+" _rt_clock: "+_rt_clock +" "+" D: "+D +" "+" Alarm: "+Alarm +" "+" output: "+_p +" ");

		_state_id_then_19 = 0;//moving to state lustre
		_goto_then_19(_info);
		}
}
}

public void _goto_then_19(String _info){
 String state_format = _string_then_19(_state_id_then_19, 1);
   System.out.println("[then_19]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + state_format);
}

public String _string_then_19(int _state_id, int _mode){
switch(_state_id){
case 1: if (_mode == 0) return "initialization"; else return "initialization";
case 0: if (_mode == 0) return "lustre"; else return "lustre";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}