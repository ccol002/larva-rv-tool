package aspects;

import sniffer.*;
import larva.*;
import java.net.*;

import larva.*;
public aspect _asp_cs_retries0 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_cs_retries0.initialize();
}
}
}