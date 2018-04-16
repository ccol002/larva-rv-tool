package aspects;

import larva.*;
public aspect _asp_channelexample0 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_channelexample0.initialize();
}
}
}