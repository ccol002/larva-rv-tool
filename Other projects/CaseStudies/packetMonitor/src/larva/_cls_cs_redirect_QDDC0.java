package larva;

import sniffer.*;
import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_cs_redirect_QDDC0 implements _callable{

public static PrintWriter pw; 
public static _cls_cs_redirect_QDDC0 root;

public static HashMap<_cls_cs_redirect_QDDC0,_cls_cs_redirect_QDDC0> _cls_cs_redirect_QDDC0_instances = new HashMap<_cls_cs_redirect_QDDC0,_cls_cs_redirect_QDDC0>();
static{
try{
pw = new PrintWriter("C:\\Users\\University User\\Desktop\\projects\\packetMonitor\\src\\\\output_cs_redirect_QDDC.txt");

root = new _cls_cs_redirect_QDDC0();
_cls_cs_redirect_QDDC0_instances.put(root, root);
root._clock.restart();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_cs_redirect_QDDC0 parent; //to remain null - this class does not have a parent!
public static boolean redirect;
public static boolean _b;
int no_automata = 1;
 public boolean strict_after_56 ;
 public boolean pre_19_26 ;
 public long pre_13_72 ;
 public boolean pre_14_125 ;
 public boolean boolg_20_64 ;
 public boolean p_15_86 ;
 public long since_71 ;
 public boolean then_10_104 ;
 public boolean never_p_103 ;
 public boolean point_9_105 ;
 public boolean interval_17_80 ;
 public boolean pre_1_127 ;
 public long pre_12_142 ;
 public boolean len_23_29 ;
 public boolean point_14_65 ;
 public boolean after_89 ;
 public long since_51 ;
 public long pre_12_32 ;
 public boolean strict_after_145 ;
 public boolean pre_1_57 ;
 public boolean len_18_91 ;
 public long pre_11_114 ;
 public boolean first_119 ;
 public boolean len_12_70 ;
 public boolean pre_2_78 ;
 public boolean always_since_46 ;
 public boolean first_100 ;
 public boolean begin_8_106 ;
 public boolean pre_1_83 ;
 public boolean never_p_122 ;
 public long pre_11_143 ;
 public boolean boolg_31_23 ;
 public long pre_13_52 ;
 public boolean after_48 ;
 public boolean interval_28_39 ;
 public boolean strict_after_76 ;
 public long _rt_clock ;
 public boolean pre_10_96 ;
 public boolean pre_3_47 ;
 public boolean pre_17_67 ;
public Clock _clock = new Clock(this);
 public long pre_12_113 ;
 public boolean begin_24_25 ;
 public boolean _p ;
 public boolean pre_8_121 ;
 public boolean pre_2_118 ;
 public boolean pre_9_101 ;
 public boolean point_4_134 ;
 public boolean pre_2_84 ;
 public boolean pre_0_90 ;
 public boolean pre_1_146 ;
 public boolean strict_after_82 ;
 public boolean pre_10_34 ;
 public boolean p_26_45 ;
 public long pre_11_33 ;
 public boolean never_p_62 ;
 public long pre_12_53 ;
 public boolean p_11_68 ;
 public boolean pre_1_77 ;
 public boolean p_11_69 ;
 public long since_30 ;
 public boolean pre_2_99 ;
 public boolean p_27_44 ;
 public boolean begin_13_66 ;
 public boolean pre_15_136 ;
 public boolean pre_18_81 ;
 public boolean strict_after_116 ;
 public boolean always_since_87 ;
 public long pre_13_141 ;
 public boolean pre_20_40 ;
 public boolean pre_9_120 ;
 public boolean pre_2_147 ;
 public long pre_12_73 ;
 public boolean pre_2_37 ;
 public boolean after_132 ;
 public long pre_13_31 ;
 public boolean p_0_138 ;
 public boolean begin_3_135 ;
 public boolean pre_1_98 ;
 public boolean pre_8_102 ;
 public boolean pre_10_75 ;
 public long since_140 ;
 public boolean p_0_137 ;
 public boolean strict_after_97 ;
 public boolean p_6_108 ;
 public boolean always_since_130 ;
 public boolean pre_8_61 ;
 public boolean p_6_109 ;
 public long pre_11_54 ;
 public boolean boolg_30_38 ;
 public boolean then_21_63 ;
 public boolean pre_3_131 ;
 public boolean p_0_129 ;
 public boolean pre_0_133 ;
 public boolean pre_10_144 ;
 public boolean int_plus_5_123 ;
 public long pre_13_93 ;
 public boolean boolg_19_79 ;
 public boolean strict_after_35 ;
 public boolean pre_1_42 ;
 public boolean interval_1_124 ;
 public boolean pre_10_115 ;
 public long pre_12_94 ;
 public boolean pre_2_58 ;
 public boolean pre_2_128 ;
 public boolean pre_1_117 ;
 public boolean pre_0_49 ;
 public boolean p_16_85 ;
 public boolean pre_10_55 ;
 public boolean pre_3_88 ;
 public long pre_11_95 ;
 public long pre_11_74 ;
 public boolean pre_1_36 ;
 public boolean strict_after_126 ;
 public boolean point_25_24 ;
 public boolean strict_after_41 ;
 public boolean p_22_27 ;
 public boolean pre_16_107 ;
 public boolean pre_2_43 ;
 public boolean len_7_110 ;
 public boolean p_22_28 ;
 public long since_92 ;
 public boolean len_2_139 ;
 public boolean first_59 ;
 public long since_111 ;
 public boolean pre_9_60 ;
 public long pre_13_112 ;
 public boolean len_29_50 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_cs_redirect_QDDC0() {
}

public static _cls_cs_redirect_QDDC0 _get_cls_cs_redirect_QDDC0_inst() { synchronized(_cls_cs_redirect_QDDC0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_cs_redirect_QDDC0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_cs_redirect_QDDC0_instances){
_performLogic_then_32(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_cs_redirect_QDDC0[] a = new _cls_cs_redirect_QDDC0[1];
synchronized(_cls_cs_redirect_QDDC0_instances){
a = _cls_cs_redirect_QDDC0_instances.keySet().toArray(a);}
for (_cls_cs_redirect_QDDC0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_cs_redirect_QDDC0_instances){
_cls_cs_redirect_QDDC0_instances.remove(this);}
synchronized(_clock){
_clock.off();
_clock._inst = null;
_clock = null;}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_then_32 = 1;

public void _performLogic_then_32(String _info, int... _event) {

_cls_cs_redirect_QDDC0.pw.println("AUTOMATON::> then_32("+") STATE::>"+ _string_then_32(_state_id_then_32, 0));
_cls_cs_redirect_QDDC0.pw.flush();

if (0==1){}
else if (_state_id_then_32==1){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*initializationEvent*/))){
		pre_19_26 =false ;
pre_10_34 =false ;
pre_11_33 =0 ;
pre_12_32 =0 ;
pre_13_31 =0 ;
pre_1_36 =false ;
pre_2_37 =false ;
pre_1_42 =false ;
pre_2_43 =false ;
pre_0_49 =false ;
pre_10_55 =false ;
pre_11_54 =0 ;
pre_12_53 =0 ;
pre_13_52 =0 ;
pre_1_57 =false ;
pre_2_58 =false ;
pre_8_61 =false ;
pre_9_60 =false ;
pre_17_67 =false ;
pre_10_75 =false ;
pre_11_74 =0 ;
pre_12_73 =0 ;
pre_13_72 =0 ;
pre_1_77 =false ;
pre_2_78 =false ;
pre_1_83 =false ;
pre_2_84 =false ;
pre_0_90 =false ;
pre_10_96 =false ;
pre_11_95 =0 ;
pre_12_94 =0 ;
pre_13_93 =0 ;
pre_1_98 =false ;
pre_2_99 =false ;
pre_8_102 =false ;
pre_9_101 =false ;
pre_16_107 =false ;
pre_10_115 =false ;
pre_11_114 =0 ;
pre_12_113 =0 ;
pre_13_112 =0 ;
pre_1_117 =false ;
pre_2_118 =false ;
pre_8_121 =false ;
pre_9_120 =false ;
pre_1_127 =false ;
pre_2_128 =false ;
pre_0_133 =false ;
pre_15_136 =false ;
pre_10_144 =false ;
pre_11_143 =0 ;
pre_12_142 =0 ;
pre_13_141 =0 ;
pre_1_146 =false ;
pre_2_147 =false ;

		_state_id_then_32 = 0;//moving to state lustre
		_goto_then_32(_info);
		}
}
else if (_state_id_then_32==0){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*periodicEvent*/))){
		_rt_clock =_clock .current_long ();
p_0_138 =true ;
p_0_137 =true ;
p_0_129 =true ;
strict_after_56 =(_b )?(false ):((pre_1_57 )?(true ):(pre_2_58 ));
p_15_86 =redirect ;
p_6_108 =redirect ;
p_6_109 =redirect ;
strict_after_35 =(_b )?(false ):((pre_1_36 )?(true ):(pre_2_37 ));
after_48 =(_b )?(true ):(pre_0_49 );
strict_after_41 =(_b )?(false ):((pre_1_42 )?(true ):(pre_2_43 ));
p_26_45 =redirect ;
never_p_62 =(_b )?(true ):((pre_8_61 )?(false ):(pre_9_60 ));
p_22_27 =redirect ;
p_11_68 =redirect ;
p_11_69 =redirect ;
p_22_28 =redirect ;
since_51 =(_b )?(0 ):((strict_after_56 &&pre_10_55 )?(pre_11_54 +_rt_clock -pre_12_53 ):(pre_13_52 ));
interval_28_39 =strict_after_41 &&pre_20_40 ;
begin_24_25 =(_b &&!(p_22_27 ))?(false ):((_b &&p_22_28 )?(true ):(pre_19_26 ));
p_16_85 =!(p_15_86 );
since_30 =(_b )?(0 ):((strict_after_35 &&pre_10_34 )?(pre_11_33 +_rt_clock -pre_12_32 ):(pre_13_31 ));
p_27_44 =!(p_26_45 );
len_23_29 =since_30 <=0 ;
always_since_46 =(_b )?(p_27_44 ):((after_48 )?(p_27_44 &&pre_3_47 ):(true ));
len_29_50 =since_51 <2000.0 ;
boolg_30_38 =interval_28_39 &&len_29_50 ;
point_25_24 =begin_24_25 &&len_23_29 ;
boolg_31_23 =point_25_24 ||boolg_30_38 ;
first_59 =!(boolg_31_23 )&&never_p_62 ;
never_p_103 =(first_59 )?(true ):((pre_8_102 )?(false ):(pre_9_101 ));
strict_after_97 =(first_59 )?(false ):((pre_1_98 )?(true ):(pre_2_99 ));
after_89 =(first_59 )?(true ):(pre_0_90 );
strict_after_76 =(first_59 )?(false ):((pre_1_77 )?(true ):(pre_2_78 ));
strict_after_82 =(first_59 )?(false ):((pre_1_83 )?(true ):(pre_2_84 ));
begin_13_66 =(first_59 &&!(p_11_68 ))?(false ):((first_59 &&p_11_69 )?(true ):(pre_17_67 ));
always_since_87 =(first_59 )?(p_16_85 ):((after_89 )?(p_16_85 &&pre_3_88 ):(true ));
since_71 =(first_59 )?(0 ):((strict_after_76 &&pre_10_75 )?(pre_11_74 +_rt_clock -pre_12_73 ):(pre_13_72 ));
interval_17_80 =strict_after_82 &&pre_18_81 ;
since_92 =(first_59 )?(0 ):((strict_after_97 &&pre_10_96 )?(pre_11_95 +_rt_clock -pre_12_94 ):(pre_13_93 ));
len_18_91 =since_92 <2000.0 ;
len_12_70 =since_71 <=0 ;
point_14_65 =begin_13_66 &&len_12_70 ;
boolg_19_79 =interval_17_80 &&len_18_91 ;
boolg_20_64 =point_14_65 ||boolg_19_79 ;
first_100 =!(boolg_20_64 )&&never_p_103 ;
strict_after_116 =(first_100 )?(false ):((pre_1_117 )?(true ):(pre_2_118 ));
begin_8_106 =(first_100 &&!(p_6_108 ))?(false ):((first_100 &&p_6_109 )?(true ):(pre_16_107 ));
never_p_122 =(first_100 )?(true ):((pre_8_121 )?(false ):(pre_9_120 ));
since_111 =(first_100 )?(0 ):((strict_after_116 &&pre_10_115 )?(pre_11_114 +_rt_clock -pre_12_113 ):(pre_13_112 ));
len_7_110 =since_111 <=0 ;
point_9_105 =begin_8_106 &&len_7_110 ;
first_119 =!(point_9_105 )&&never_p_122 ;
after_132 =(first_119 )?(true ):(pre_0_133 );
begin_3_135 =(first_119 &&!(p_0_137 ))?(false ):((first_119 &&p_0_138 )?(true ):(pre_15_136 ));
strict_after_145 =(first_119 )?(false ):((pre_1_146 )?(true ):(pre_2_147 ));
strict_after_126 =(first_119 )?(false ):((pre_1_127 )?(true ):(pre_2_128 ));
since_140 =(first_119 )?(0 ):((strict_after_145 &&pre_10_144 )?(pre_11_143 +_rt_clock -pre_12_142 ):(pre_13_141 ));
always_since_130 =(first_119 )?(p_0_129 ):((after_132 )?(p_0_129 &&pre_3_131 ):(true ));
interval_1_124 =strict_after_126 &&pre_14_125 ;
len_2_139 =since_140 <=0 ;
point_4_134 =begin_3_135 &&len_2_139 ;
int_plus_5_123 =interval_1_124 ||point_4_134 ;
then_10_104 =int_plus_5_123 ;
then_21_63 =then_10_104 ;
_p =then_21_63 ;
pre_19_26 =begin_24_25 ;
pre_10_34 =true ;
pre_11_33 =since_30 ;
pre_12_32 =_rt_clock ;
pre_13_31 =since_30 ;
pre_1_36 =_b ;
pre_2_37 =strict_after_35 ;
pre_20_40 =always_since_46 ;
pre_1_42 =_b ;
pre_2_43 =strict_after_41 ;
pre_3_47 =always_since_46 ;
pre_0_49 =after_48 ;
pre_10_55 =true ;
pre_11_54 =since_51 ;
pre_12_53 =_rt_clock ;
pre_13_52 =since_51 ;
pre_1_57 =_b ;
pre_2_58 =strict_after_56 ;
pre_8_61 =!(boolg_31_23 );
pre_9_60 =never_p_62 ;
pre_17_67 =begin_13_66 ;
pre_10_75 =true ;
pre_11_74 =since_71 ;
pre_12_73 =_rt_clock ;
pre_13_72 =since_71 ;
pre_1_77 =first_59 ;
pre_2_78 =strict_after_76 ;
pre_18_81 =always_since_87 ;
pre_1_83 =first_59 ;
pre_2_84 =strict_after_82 ;
pre_3_88 =always_since_87 ;
pre_0_90 =after_89 ;
pre_10_96 =true ;
pre_11_95 =since_92 ;
pre_12_94 =_rt_clock ;
pre_13_93 =since_92 ;
pre_1_98 =first_59 ;
pre_2_99 =strict_after_97 ;
pre_8_102 =!(boolg_20_64 );
pre_9_101 =never_p_103 ;
pre_16_107 =begin_8_106 ;
pre_10_115 =true ;
pre_11_114 =since_111 ;
pre_12_113 =_rt_clock ;
pre_13_112 =since_111 ;
pre_1_117 =first_100 ;
pre_2_118 =strict_after_116 ;
pre_8_121 =!(point_9_105 );
pre_9_120 =never_p_122 ;
pre_14_125 =always_since_130 ;
pre_1_127 =first_119 ;
pre_2_128 =strict_after_126 ;
pre_3_131 =always_since_130 ;
pre_0_133 =after_132 ;
pre_15_136 =begin_3_135 ;
pre_10_144 =true ;
pre_11_143 =since_140 ;
pre_12_142 =_rt_clock ;
pre_13_141 =since_140 ;
pre_1_146 =first_119 ;
pre_2_147 =strict_after_145 ;
_cls_cs_redirect_QDDC0.pw .println (" _b: "+_b +" "+" _rt_clock: "+_rt_clock +" "+" redirect: "+redirect +" "+" output: "+_p +" ");

		_state_id_then_32 = 0;//moving to state lustre
		_goto_then_32(_info);
		}
}
}

public void _goto_then_32(String _info){
_cls_cs_redirect_QDDC0.pw.println("MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_then_32(_state_id_then_32, 1));
_cls_cs_redirect_QDDC0.pw.flush();
}

public String _string_then_32(int _state_id, int _mode){
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