package aspects;


import transactionsystem.UserInfo;


import larva.*;
public aspect _asp_test0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_test0.initialize();
}
}
}