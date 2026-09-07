package aspects;


import benchmark.*;


import larva.*;
public aspect _asp_benchmark_invariants0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_benchmark_invariants0.initialize();
}
}
}