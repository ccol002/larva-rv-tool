import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.nio.charset.Charset;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;


//Jpcap captures and sends packets independently from the host protocols (e.g., TCP/IP). 
//This means that Jpcap does not (cannot) block, filter or manipulate the traffic generated 
//by other programs on the same machine: it simply "sniffs" the packets that transit on the wire. 
//Therefore, it does not provide the appropriate support for applications like traffic shapers, 
//QoS schedulers and personal firewalls.


public class Main {

	public static void main(String[] args)
	{
		
		System.out.println(Charset.availableCharsets());
		
		try{
		
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();

		JpcapSender sender = JpcapSender.openDevice(devices[0]);
				
//		JpcapCaptor captor = JpcapCaptor.openDevice(devices[0], 65535, false, 20);
//		
//		captor.setPacketReadTimeout(2000);
//		captor.setNonBlockingMode(false);
//		 
//		//captor.processPacket(1000,new PacketPrinter());
//		captor.loopPacket(5000,new PacketPrinter());
//
//		captor.close();
		
//		TCPPacket tcp = new TCPPacket(55555,8080,0,0,false,false,false,false,true,false,false,false,65535,0);
//		tcp.dst_ip = Inet4Address.getByAddress(new byte[]{(byte)195,(byte)158,89,(byte)177});
//		tcp.src_ip = Inet4Address.getByAddress(new byte[]{(byte)192,(byte)168,17,(byte)245});
//		tcp.hop_limit=128;
//		tcp.dont_frag = true;
//		tcp.protocol = 6;
//			
//		sender.sendPacket(tcp);
		
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("c:\\pakkettPING.abc"));
		Packet p = (Packet)ois.readObject();
		
//		((TCPPacket)(p)).syn = true;
//		((TCPPacket)(p)).ack = false;
//		
//		for (int i = 20000; i < 65535; i++)
//		{
//			((TCPPacket)(p)).src_port = i;
//			System.out.println("Sending: " + p);


//		((ICMPPacket)(p)).type = 5;//redirect
//		((ICMPPacket)(p)).code = 1;//host redirect		
//		((ICMPPacket)(p)).redir_ip = Inet4Address.getByAddress(new byte[]{(byte)192,(byte)168,17,(byte)254});//should use
//		((ICMPPacket)(p)).router_ip = new InetAddress[]{Inet4Address.getByAddress(new byte[]{(byte)192,(byte)168,17,(byte)254})};//to reach what
//		((ICMPPacket)(p)).caplen = 0;
//		((ICMPPacket)(p)).checksum = 0;
//		((ICMPPacket)(p)).len = 0;
		
		
		((IPPacket)(p)).src_ip = Inet4Address.getByAddress(new byte[]{(byte)192,(byte)168,17,(byte)245});
		((IPPacket)(p)).dst_ip = Inet4Address.getByAddress(new byte[]{(byte)192,(byte)168,(byte)17,(byte)96});
	//	((TCPPacket)(p)).dst_port = 55555;
		
		for (int i = 0; i < 1000; i++)
			sender.sendPacket(p);
	//	}
		
		
		
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
