package aspects;

import larva.*;
import sniffer.*;
import java.net.*;

import larva.*;
public aspect _asp_cs_tcphandshake0 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_cs_tcphandshake0.initialize();
}
}
}