package larva;


import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_props30 {

public static PrintWriter pw; 
static{
try{
pw = new PrintWriter("C:\\Users\\University User\\Desktop\\aspectJ\\NestingTesting\\src\\larva\\\\output_props3.txt");
}catch(Exception ex)
{ex.printStackTrace();}
}

public static HashMap<_cls_props30,_cls_props30> _cls_props30_instances = new HashMap<_cls_props30,_cls_props30>();

_cls_props30 parent; //to remain null - this class does not have a parent!
 public boolean logged =false ;
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_props30() {
}

public static _cls_props30 _get_cls_props30_inst() {
_cls_props30 _inst = new _cls_props30();
if (_cls_props30_instances.containsKey(_inst))
{
 return _cls_props30_instances.get(_inst);
}
else
{
 _cls_props30_instances.put(_inst,_inst);
 return _inst;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_props30))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
}
public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}