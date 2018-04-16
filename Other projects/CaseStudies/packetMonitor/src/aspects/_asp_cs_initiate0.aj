package aspects;

import sniffer.*;
import java.net.InetAddress;

import larva.*;
public aspect _asp_cs_initiate0 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_cs_initiate0.initialize();
}
}
}