import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.ICMPPacket;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

class PacketPrinter implements PacketReceiver {
	
	JpcapSender sender;
	
	public PacketPrinter()
	{
		try{
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		sender = JpcapSender.openDevice(devices[0]);
		}
		catch(Exception ex){ex.printStackTrace();}
	}
  //this method is called every time Jpcap captures a packet
  public void receivePacket(jpcap.packet.Packet packet) {
    //just print out a captured packet
	  
	 // if (packet instanceof TCPPacket)
	  if (packet instanceof ICMPPacket)
	  {
		  try{
		  InetAddress addr = Inet4Address.getByAddress(new byte[]{(byte)195,(byte)158,89,(byte)177});//.getByName("localhost");//
		  //System.out.println(addr);
		
		  TCPPacket tcp = (TCPPacket)(packet);
		  if (tcp.dst_ip.equals(addr))
		  {
			 
			  
//			  ObjectOutputStream output;
//			  try // open file
//			  {
//				  output = new ObjectOutputStream(new FileOutputStream("c:\\pakkett.abc"));
//				  output.writeObject(tcp);
//				  output.close();
//			   } // end try
//			   catch ( IOException ioException )
//			   {System.err.println( "Error opening file." );}
			   
			  
			  
//			  //System.out.println("WASALNA:" + packet);
//			  if (tcp.src_port == 8080 && !tcp.syn && tcp.hop_limit != 123)
//			  {
//				  System.out.println(packet);
//				  System.out.println(new String(tcp.data));
//
//				  String s = "HTTP/1.1 200 OK" +
//				  		"\r\nServer: Apache-Coyote/1.1" +
//				  		"\r\nETag: W/\"32-1219154972656\"" +
//				  		"\r\nLast-Modified: Tue, 19 Aug 2008 14:09:32 GMT" +
//				  		"\r\nContent-Type: text/html" +
//				  		"\r\nContent-Length: 32" +
//				  		"\r\nDate: Wed, 20 Aug 2008 14:19:31 GMT" +
//				  		"\r\nConnection: close" +
//				  		"\r\nhello!!!";
//					  
//					  tcp.data = s.getBytes();
//					  
////				  tcp.fin = true;
////				  tcp.psh = false;
////				  tcp.offset = 0;
//				  tcp.hop_limit = 123;
//				  sender.sendPacket(tcp);
//			  }
//			  else
//			  {
//				  System.out.println(packet);
//				  System.out.println(new String(tcp.data));
//			  }
		  }
		  else if (tcp.dst_ip.equals(addr))
		  {
			  System.out.println(packet);
			  System.out.println(new String(tcp.data));
		  
		  }
		  }catch(Exception ex)
		  {ex.printStackTrace();}
	  }
  }
}