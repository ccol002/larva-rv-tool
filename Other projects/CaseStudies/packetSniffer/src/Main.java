import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;


//Jpcap captures and sends packets independently from the host protocols (e.g., TCP/IP). 
//This means that Jpcap does not (cannot) block, filter or manipulate the traffic generated 
//by other programs on the same machine: it simply "sniffs" the packets that transit on the wire. 
//Therefore, it does not provide the appropriate support for applications like traffic shapers, 
//QoS schedulers and personal firewalls.


public class Main {

	public static void main(String[] args)
	{
		try{
		
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();

		//for each network interface
		for (int i = 0; i < devices.length; i++) {
		  //print out its name and description
		  System.out.println(i+": "+devices[i].name + "(" + devices[i].description+")");

		  //print out its datalink name and description
		  System.out.println(" datalink: "+devices[i].datalink_name + "(" + devices[i].datalink_description+")");

		  //print out its MAC address
		  System.out.print(" MAC address:");
		  for (byte b : devices[i].mac_address)
		    System.out.print(Integer.toHexString(b&0xff) + ":");
		  System.out.println();

		  //print out its IP address, subnet mask and broadcast address
		  for (NetworkInterfaceAddress a : devices[i].addresses)
		    System.out.println(" address:"+a.address + " " + a.subnet + " "+ a.broadcast);
		}
		
		
		JpcapCaptor captor=JpcapCaptor.openDevice(devices[2], 65535, true, 20);
		
		captor.setPacketReadTimeout(2000);
		captor.setNonBlockingMode(false);
		
		//captor.processPacket(1000,new PacketPrinter());
		captor.loopPacket(500000,new PacketPrinter());

		captor.close();
		
		
		}catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
//		CaptureTool ct = new CaptureTool();
//		
//		
//		
//		ct.setDevice(device)
//		
//		CaptureConsole cc = new CaptureConsole(ct);
//	
//		System.out.println(cc.command_capture(args));
	}
}
