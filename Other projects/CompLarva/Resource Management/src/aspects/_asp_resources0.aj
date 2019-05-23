package aspects;

import code.main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import larva.*;
import java.util.HashMap;
import java.util.Stack;
public aspect _asp_resources0 {

public static Object lock = new Object();

public static Integer sid = 0;

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_resources0.initialize();
}
}
after () returning () : (execution(* *.*(..)) && !cflow(adviceexecution())) {

synchronized(_asp_resources0.lock){

_cls_resources0 _cls_inst = _cls_resources0._get_cls_resources0_inst();
_asp_resources0.sid++;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*methodexit*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*methodexit*/);
}
}
after () throwing () : (call(* *.*(..)) && !cflow(adviceexecution())) {

synchronized(_asp_resources0.lock){

_cls_resources0 _cls_inst = _cls_resources0._get_cls_resources0_inst();
_asp_resources0.sid++;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*methodexit*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*methodexit*/);
}
}
after () returning (File f) : (call(*.new(..)) && args(*) && !cflow(adviceexecution())) {

synchronized(_asp_resources0.lock){

_cls_resources0 _cls_inst = _cls_resources0._get_cls_resources0_inst();
_asp_resources0.sid++;
_cls_inst.f.put(_asp_resources0.sid,f);
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*newfile*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*newfile*/);
}
}
after () returning (FileReader fr) : (call(*.new(..)) && args(*) && !cflow(adviceexecution())) {

synchronized(_asp_resources0.lock){

_cls_resources0 _cls_inst = _cls_resources0._get_cls_resources0_inst();
_asp_resources0.sid++;
_cls_inst.fr.put(_asp_resources0.sid,fr);
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*newfr*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*newfr*/);
}
}
before () : (execution(* *.*(..)) && !cflow(adviceexecution())) {

synchronized(_asp_resources0.lock){

_cls_resources0 _cls_inst = _cls_resources0._get_cls_resources0_inst();
_asp_resources0.sid++;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*methodentry*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*methodentry*/);
}
}
after () returning (BufferedReader br) : (call(*.new(..)) && args(*) && !cflow(adviceexecution())) {

synchronized(_asp_resources0.lock){

_cls_resources0 _cls_inst = _cls_resources0._get_cls_resources0_inst();
_asp_resources0.sid++;
_cls_inst.br.put(_asp_resources0.sid,br);
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*newbr*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*newbr*/);
}
}
}