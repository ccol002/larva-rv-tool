package aspects;


import benchmark.*;


import larva.*;
public aspect _asp_benchmark0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_benchmark0.initialize();
}
}
}