package larva;


import sniffer.*;
import larva.*;
import java.net.*;

import java.util.HashMap;
import java.io.PrintWriter;

public class _cls_cs_retries0 implements _callable{

public static PrintWriter pw; 
public static _cls_cs_retries0 root;

public static HashMap<_cls_cs_retries0,_cls_cs_retries0> _cls_cs_retries0_instances = new HashMap<_cls_cs_retries0,_cls_cs_retries0>();
static{
try{
pw = new PrintWriter("C:\\Users\\University User\\Desktop\\projects\\packetMonitor\\src\\\\output_cs_retries.txt");

root = new _cls_cs_retries0();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_cs_retries0 parent; //to remain null - this class does not have a parent!
int no_automata = 0;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_cs_retries0() {
}

public static _cls_cs_retries0 _get_cls_cs_retries0_inst() {
 return root;
}

public boolean equals(Object o) {
 if ((o instanceof _cls_cs_retries0))
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

for (_cls_cs_retries1 _inst : _cls_cs_retries1._cls_cs_retries1_instances.keySet())
if (true){
_inst._call(_info, _event); 
_inst._call_all_filtered(_info, _event);
}
}

public static void _call_all(String _info, int... _event){

for (_cls_cs_retries0 _inst : _cls_cs_retries0_instances.keySet())
_inst._call(_info, _event);
}

public void _killThis(){
if (--no_automata <= 0){
 _cls_cs_retries0_instances.remove(this);
}
}


public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}