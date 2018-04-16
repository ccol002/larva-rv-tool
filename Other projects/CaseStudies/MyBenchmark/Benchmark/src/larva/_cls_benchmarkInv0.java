package larva;



import benchmark.*;


import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_benchmarkInv0 implements _callable{

public static PrintWriter pw; 

public static HashMap<_cls_benchmarkInv0,_cls_benchmarkInv0> _cls_benchmarkInv0_instances = new HashMap<_cls_benchmarkInv0,_cls_benchmarkInv0>();
static{
try{
pw = new PrintWriter("C:\\Documents and Settings\\Colombo.LAPTOP\\Desktop\\projects\\Benchmark\\src\\larva\\\\output_benchmarkInv.txt");

_get_cls_benchmarkInv0_inst();}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_benchmarkInv0 parent; //to remain null - this class does not have a parent!
int no_automata = 0;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_benchmarkInv0() {
}

public static _cls_benchmarkInv0 _get_cls_benchmarkInv0_inst() {
_cls_benchmarkInv0 _inst = new _cls_benchmarkInv0();
if (_cls_benchmarkInv0_instances.containsKey(_inst))
{
_cls_benchmarkInv0 tmp = _cls_benchmarkInv0_instances.get(_inst);
 return _cls_benchmarkInv0_instances.get(_inst);
}
else
{
 _cls_benchmarkInv0_instances.put(_inst,_inst);
 return _inst;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_benchmarkInv0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
}

public void _call_all_filtered(String _info, int... _event){

for (_cls_benchmarkInv1 _inst : _cls_benchmarkInv1._cls_benchmarkInv1_instances.keySet())
if (true){
_inst._call(_info, _event); 
_inst._call_all_filtered(_info, _event);
}
}

public static void _call_all(String _info, int... _event){

for (_cls_benchmarkInv0 _inst : _cls_benchmarkInv0_instances.keySet())
_inst._call(_info, _event);
}

public void _killThis(){
if (--no_automata <= 0){
 _cls_benchmarkInv0_instances.remove(this);
}
}


public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}