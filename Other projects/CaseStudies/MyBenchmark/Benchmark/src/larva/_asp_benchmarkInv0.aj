package larva;


import benchmark.*;


public aspect _asp_benchmarkInv0 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_benchmarkInv0.initialize();
}
}
}