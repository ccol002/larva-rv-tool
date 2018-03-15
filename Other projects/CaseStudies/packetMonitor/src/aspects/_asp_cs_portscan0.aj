package aspects;

import larva.*;
import java.net.InetAddress;

import larva.*;
public aspect _asp_cs_portscan0 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_cs_portscan0.initialize();
}
}
}